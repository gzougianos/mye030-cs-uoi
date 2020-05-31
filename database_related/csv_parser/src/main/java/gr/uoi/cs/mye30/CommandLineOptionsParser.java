package gr.uoi.cs.mye30;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineOptionsParser {
	private static final Logger logger = Logger.getGlobal();
	private static final String COUNTRY_CODE_PATTERN = "[A-Z]{3}";
	//@formatter:off
	private static final Option COUNTRY_CODES_OPTION = Option.builder("c")
			.longOpt("country")
			.hasArgs()
			.valueSeparator(',')
			.required()
			.desc("A country CODE or multiple country CODES to parse.")
			.build();
	
	private static final Option FILE_OPTION  = Option.builder("f")
			.longOpt("file")
			.hasArgs()
			.required()
			.desc("A file that contains country names (each line contains a country name)")
			.build();
	
	private static final Option URL_OPTION  = Option.builder("u")
			.longOpt("url")
			.hasArg()
			.required()
			.desc("The MySQL Url to connect to.")
			.build();
	//@formatter:on
	private List<String> countryCodes;
	private String url;

	public CommandLineOptionsParser(final String[] arguments, final SystemExit systemExit, HelpFormatter helpFormatter)
			throws IOException {
		countryCodes = new ArrayList<String>();

		OptionGroup countriesOptionGroup = new OptionGroup();
		COUNTRY_CODES_OPTION.setRequired(true);
		countriesOptionGroup.addOption(COUNTRY_CODES_OPTION);

		OptionGroup fileOptionGroup = new OptionGroup();
		fileOptionGroup.addOption(FILE_OPTION);

		Options options = new Options();
		options.addOptionGroup(countriesOptionGroup);
		options.addOptionGroup(fileOptionGroup);
		options.addOption(URL_OPTION);

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine commandLine = parser.parse(options, arguments);
			if (commandLine.getOptions().length != 2) {
				throw new ParseException("Exactly one argument is allowed.");
			}
			url = commandLine.getOptionValue("u");
			parseCountryCodes(commandLine);
		} catch (ParseException e) {
			helpFormatter.printHelp("java -jar country_parser.jar -c ALB,GRC", options);
			systemExit.doExit();
		}
	}

	private void parseCountryCodes(CommandLine commandLine) throws IOException {
		String[] countryCodesOption = commandLine.getOptionValues(COUNTRY_CODES_OPTION.getOpt());
		boolean countryCodesOptionGiven = countryCodesOption != null;
		if (countryCodesOptionGiven) {
			//@formatter:off
			countryCodes = Stream.of(countryCodesOption)
					.map(String::trim)
					.map(String::toUpperCase)
					.collect(toList());
			//@formatter:on
		} else {
			File countryCodesFile = new File(commandLine.getOptionValue(FILE_OPTION.getOpt()));
			//@formatter:off
			countryCodes = Files.readAllLines(countryCodesFile.toPath())
					.stream()
					.map(String::trim)
					.map(String::toUpperCase)
					.collect(toList());
			//@formatter:on
		}
		keepOnlyTheValidOnes();
		countryCodes = Collections.unmodifiableList(countryCodes);
	}

	private void keepOnlyTheValidOnes() {
		//@formatter:off
		List<String> nonValid = countryCodes.stream()
				.filter(code -> !code.matches(COUNTRY_CODE_PATTERN))
				.collect(toList());
		//@formatter:on
		if (!nonValid.isEmpty()) {
			nonValid.forEach(code -> logger.warning(code + " is not a valid code hence it will be ignored."));
			countryCodes.removeAll(nonValid);
		}

	}

	public List<String> getCountryCodes() {
		return countryCodes;
	}

	public String getUrl() {
		return url;
	}
}
