package gr.uoi.cs.mye30.parser;

import java.util.HashMap;
import java.util.Map;

import gr.uoi.cs.mye30.record.YearsRecord;

public class YearsRecordParser implements RecordParser<YearsRecord> {

	@Override
	public YearsRecord create(String[] values) {
		String countryName = values[0];
		String countryCode = values[1];
		String indicatorName = values[2];
		String indicatorCode = values[3];

		Map<Integer, Double> yearValues = new HashMap<>();
		for (int i = 4; i < values.length; i++) {
			if (values[i].isEmpty())
				continue;

			int year = 1956 + i;
			double value = Double.parseDouble(values[i]);
			yearValues.put(year, value);
		}
		return new YearsRecord(countryName, countryCode, indicatorName, indicatorCode, yearValues);
	}

	@Override
	public int numberOfLinesToSkip() {
		return 5;
	}

}
