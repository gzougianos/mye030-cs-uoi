package gr.uoi.cs.mye30.parser;

import java.util.HashMap;
import java.util.Map;

import gr.uoi.cs.mye30.record.YearsRecord;

public class YearsRecordParser implements RecordParser<YearsRecord> {

	@Override
	public YearsRecord create(Map<String, String> values) {
		String countryName = values.get("Country Name");
		String countryCode = values.get("Country Code");
		String indicatorName = values.get("Indicator Name");
		String indicatorCode = values.get("Indicator Code");

		Map<Integer, Double> yearValues = new HashMap<>();
		for (int i = 0; i < values.size(); i++) {
			int year = 1960 + i;
			String valueString = values.get(String.valueOf(year));
			if (valueString == null || valueString.isEmpty())
				continue;

			double value = Double.parseDouble(valueString);
			yearValues.put(year, value);
		}
		return new YearsRecord(countryName, countryCode, indicatorName, indicatorCode, yearValues);
	}

	@Override
	public int numberOfLinesToSkip() {
		return 5;
	}

	@Override
	public int headersLineIndex() {
		return 4;
	}
}
