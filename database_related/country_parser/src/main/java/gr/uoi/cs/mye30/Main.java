package gr.uoi.cs.mye30;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.cli.HelpFormatter;

import gr.uoi.cs.mye30.parser.CountryMetadataRecordParser;
import gr.uoi.cs.mye30.parser.CsvFileParser;
import gr.uoi.cs.mye30.parser.IndicatorMetadataRecordParser;
import gr.uoi.cs.mye30.parser.YearsRecordParser;
import gr.uoi.cs.mye30.record.CountryMetadata;
import gr.uoi.cs.mye30.record.IndicatorMetadata;
import gr.uoi.cs.mye30.record.YearsRecord;

public class Main {
	private static final String URL_BASE = "http://api.worldbank.org/v2/en/country/%s?downloadformat=csv";

	public static void main(String[] args) throws IOException, SQLException {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tH:%1$tM:%1$tS %4$-1s %5$s%6$s%n");
		Logger logger = Logger.getGlobal();
		CommandLineOptions cmdOptions = new CommandLineOptions(args, () -> System.exit(0), new HelpFormatter());
		Database database = new Database(cmdOptions.getUrl());

		List<String> countryCodes = cmdOptions.getCountryCodes();
		for (int i = 0; i < countryCodes.size(); i++) {
			String countryCode = countryCodes.get(i);
			logger.info("------------------------------");
			logger.info("Fetching " + i + " of " + countryCodes.size() + " country codes.");
			if (database.countryExists(countryCode)) {
				logger.warning("Country with code " + countryCode + " already exists hence it will be ignored!");
				continue;
			}

			CountryCsvFilesFetcher fetcher = new CountryCsvFilesFetcher(String.format(URL_BASE, countryCode));

			CountryMetadata countryMetadata = CsvFileParser
					.parseRecords(fetcher.getCountryMetadataFile(), new CountryMetadataRecordParser()).get(0);

			database.storeCountry(countryMetadata);

			List<IndicatorMetadata> indicatorMetadatas = CsvFileParser.parseRecords(fetcher.getIndicatorsMetadataFile(),
					new IndicatorMetadataRecordParser());

			for (IndicatorMetadata indicatorMetadata : indicatorMetadatas) {
				database.storeIndicatorIfNotExists(indicatorMetadata);
			}

			List<YearsRecord> yearsRecords = CsvFileParser.parseRecords(fetcher.getYearValuesFile(),
					new YearsRecordParser());

			logger.info("Storing " + yearsRecords.size() + " years records...");
			for (YearsRecord yearsRecord : yearsRecords) {
				database.storeYearsRecord(yearsRecord);
			}
			logger.info("Years records stored.");

			logger.info("Commiting transaction...");
			database.commit();
			logger.info("Country with code " + countryCode + " commited successfully!");
			logger.info("------------------------------");
		}

	}
}
