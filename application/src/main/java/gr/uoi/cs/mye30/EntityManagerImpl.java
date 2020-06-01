package gr.uoi.cs.mye30;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import gr.uoi.cs.mye30.entity.Country;
import gr.uoi.cs.mye30.entity.Indicator;
import gr.uoi.cs.mye30.entity.YearValue;

public class EntityManagerImpl implements EntityManager {
	private static final Logger logger = Logger.getGlobal();
	private static final String SELECT_INDICATORS = "SELECT * FROM indicators";
	private static final String SELECT_COUNTRIES = "SELECT * FROM countries";
	private static final String SELECT_YEAR_VALUES_TEMPLATE = "SELECT * FROM years WHERE (%s) AND (%s) AND `year` BETWEEN %s AND %s";
	private Map<Integer, Country> countries;
	private Map<Integer, Indicator> indicators;

	public EntityManagerImpl() {
		countries = new HashMap<>();
		indicators = new HashMap<>();
	}

	@Override
	public void loadEntities() throws SQLException {
		loadCountries();
		loadIndicators();
	}

	private void loadCountries() throws SQLException {
		logger.info("Loading existing countries: [" + SELECT_COUNTRIES + "]");
		try (Connection connection = Database.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_COUNTRIES);) {
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt(1);
					String name = rs.getString(2);
					String code = rs.getString(3);
					String region = rs.getString(4);
					String incomeGroup = rs.getString(5);
					String specialNotes = rs.getString(6);
					Country country = new Country(id, name, code, region, incomeGroup, specialNotes);
					countries.put(id, country);
				}
			}
		}
		logger.info("Loaded " + countries.size() + " existing countries.");
	}

	private void loadIndicators() throws SQLException {
		logger.info("Loading existing indicators: [" + SELECT_INDICATORS + "]");
		try (Connection connection = Database.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_INDICATORS);) {
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt(1);
					String name = rs.getString(2);
					String code = rs.getString(3);
					String sourceNote = rs.getString(4);
					String sourceOrganization = rs.getString(5);
					Indicator indicator = new Indicator(id, name, code, sourceNote, sourceOrganization);
					indicators.put(id, indicator);
				}
			}
		}
		logger.info("Loaded " + indicators.size() + " existing indicators.");
	}

	@Override
	public Collection<Indicator> getIndicators() {
		return Collections.unmodifiableCollection(indicators.values());
	}

	@Override
	public Collection<Country> getCountries() {
		return Collections.unmodifiableCollection(countries.values());
	}

	@Override
	public List<YearValue> getYearValues(Collection<Country> countries, Collection<Indicator> indicators, int yearLow,
			int yearHigh) throws SQLException {
		String countriesCondition = createOrConditionForColumn(countries, Country::getId, "country_id");
		String indicatorsCondition = createOrConditionForColumn(indicators, Indicator::getId, "indicator_id");
		final String totalQuery = String.format(SELECT_YEAR_VALUES_TEMPLATE, countriesCondition, indicatorsCondition,
				yearLow, yearHigh);
		logger.info("Year values query: " + totalQuery);

		List<YearValue> yearValues = new ArrayList<>();
		// Initiate with zeros
		countries.forEach(country -> {
			indicators.forEach(indicator -> {
				IntStream.range(yearLow, yearHigh + 1).forEach(year -> {
					yearValues.add(new YearValue(country, indicator, year, 0, true));
				});
			});
		});
		try (Connection connection = Database.getConnection();
				PreparedStatement statement = connection.prepareStatement(totalQuery);) {
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					int countryId = rs.getInt(1);
					int indicatorId = rs.getInt(2);
					int year = rs.getInt(3);
					double value = rs.getDouble(4);
					Indicator indicator = this.indicators.get(indicatorId);
					Country country = this.countries.get(countryId);
					YearValue yearValue = new YearValue(country, indicator, year, value, false);
					yearValues.add(yearValue);
				}
			}
		}

		return Collections.unmodifiableList(yearValues);
	}

	private <T> String createOrConditionForColumn(Collection<T> entities, Function<T, Object> idExtractor,
			String column) {
		StringBuilder sb = new StringBuilder();
		entities.forEach(e -> {
			sb.append(column);
			sb.append(" = ");
			sb.append(idExtractor.apply(e));
			sb.append(" OR ");
		});
		String result = sb.toString();

		// Remove last OR
		return result.substring(0, result.length() - " OR ".length());
	}

}
