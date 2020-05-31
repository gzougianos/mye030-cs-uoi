package gr.uoi.cs.mye30;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.mysql.cj.jdbc.Driver;

import gr.uoi.cs.mye30.entity.Country;
import gr.uoi.cs.mye30.entity.Indicator;
import gr.uoi.cs.mye30.record.CountryMetadata;
import gr.uoi.cs.mye30.record.IndicatorMetadata;
import gr.uoi.cs.mye30.record.YearsRecord;

public class Database {
	private static final String SELECT_INDICATORS = "SELECT * FROM indicators";
	private static final String SELECT_COUNTRIES = "SELECT * FROM countries";
	private static final String INSERT_COUNTRY = "INSERT INTO countries(`name`,`code`,region,income_group,special_notes) VALUES (?,?,?,?,?)";
	private static final String INSERT_INDICATOR = "INSERT INTO indicators(`name`,`code`,source_note,source_organization) VALUES (?,?,?,?)";
	private static final String INSERT_YEAR_VALUE = "INSERT INTO years VALUES(?,?,?,?)";
	private static final Logger logger = Logger.getGlobal();
	private Connection connection;
	private Map<String, Country> countriesByCode;
	private Map<String, Indicator> indicatorsByCode;

	public Database(String url) throws SQLException {
		logger.info("Connecting to " + url + ".");

		new Driver();
		connection = DriverManager.getConnection(url);
		connection.setAutoCommit(false);

		logger.info("Connected.");

		countriesByCode = new HashMap<>();
		indicatorsByCode = new HashMap<>();
		loadCountries();
		loadIndicators();
	}

	private void loadCountries() throws SQLException {
		logger.info("Loading existing countries: [" + SELECT_COUNTRIES + "]");

		try (PreparedStatement statement = connection.prepareStatement(SELECT_COUNTRIES);) {
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt(1);
					String name = rs.getString(2);
					String code = rs.getString(3);
					String region = rs.getString(4);
					String incomeGroup = rs.getString(5);
					String specialNotes = rs.getString(6);
					CountryMetadata metadata = new CountryMetadata(code, name, region, incomeGroup, specialNotes);
					Country country = new Country(id, metadata);
					countriesByCode.put(country.getMetadata().getCode(), country);
				}
			}
		}

		logger.info("Loaded " + countriesByCode.size() + " existing countries.");
	}

	private void loadIndicators() throws SQLException {
		logger.info("Loading existing indicators: [" + SELECT_INDICATORS + "]");
		try (PreparedStatement statement = connection.prepareStatement(SELECT_INDICATORS);) {
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt(1);
					String name = rs.getString(2);
					String code = rs.getString(3);
					String sourceNote = rs.getString(4);
					String sourceOrganization = rs.getString(5);
					IndicatorMetadata metadata = new IndicatorMetadata(code, name, sourceNote, sourceOrganization);
					Indicator indicator = new Indicator(id, metadata);
					indicatorsByCode.put(indicator.getMetadata().getCode(), indicator);
				}
			}
		}
		logger.info("Loaded " + indicatorsByCode.size() + " existing indicators.");
	}

	public void storeCountry(CountryMetadata metadata) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_COUNTRY,
				Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, metadata.getName());
			statement.setString(2, metadata.getCode());
			statement.setString(3, metadata.getRegion());
			statement.setString(4, metadata.getIncomeGroup());
			statement.setString(5, metadata.getSpecialNotes());
			statement.execute();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int id = generatedKeys.getInt(1);
					Country country = new Country(id, metadata);
					countriesByCode.put(country.getMetadata().getCode(), country);
				}
			}
		}
	}

	public void storeIndicatorIfNotExists(IndicatorMetadata metadata) throws SQLException {
		if (indicatorExists(metadata))
			return;

		try (PreparedStatement statement = connection.prepareStatement(INSERT_INDICATOR,
				Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, metadata.getName());
			statement.setString(2, metadata.getCode());
			statement.setString(3, metadata.getSourceNote());
			statement.setString(4, metadata.getSourceOrganization());
			statement.execute();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int id = generatedKeys.getInt(1);
					Indicator indicator = new Indicator(id, metadata);
					indicatorsByCode.put(indicator.getMetadata().getCode(), indicator);
				}
			}
		}
	}

	public void storeYearsRecord(YearsRecord yearsRecord) throws SQLException {
		Country country = countriesByCode.get(yearsRecord.getCountryCode());
		Indicator indicator = indicatorsByCode.get(yearsRecord.getIndicatorCode());
		for (int year : yearsRecord.getYears().keySet()) {
			double value = yearsRecord.getYears().get(year);
			try (PreparedStatement statement = connection.prepareStatement(INSERT_YEAR_VALUE);) {
				statement.setInt(1, country.getId());
				statement.setInt(2, indicator.getId());
				statement.setInt(3, year);
				statement.setDouble(4, value);
				statement.execute();
			}
		}

	}

	private boolean indicatorExists(IndicatorMetadata metadata) {
		return indicatorsByCode.containsKey(metadata.getCode());
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public boolean countryExists(String countryCode) {
		return countriesByCode.containsKey(countryCode);
	}
}
