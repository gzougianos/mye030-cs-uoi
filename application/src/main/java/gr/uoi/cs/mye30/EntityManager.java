package gr.uoi.cs.mye30;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import gr.uoi.cs.mye30.entity.Country;
import gr.uoi.cs.mye30.entity.Indicator;
import gr.uoi.cs.mye30.entity.YearValue;

public interface EntityManager {

	void loadEntities() throws SQLException;

	Collection<Indicator> getIndicators();

	Collection<Country> getCountries();

	List<YearValue> getYearValues(Collection<Country> countries, Collection<Indicator> indicators, int yearLow,
			int yearHigh) throws SQLException;
}
