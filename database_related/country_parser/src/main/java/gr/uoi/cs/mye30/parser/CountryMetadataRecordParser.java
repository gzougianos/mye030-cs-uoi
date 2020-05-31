package gr.uoi.cs.mye30.parser;

import java.util.Map;

import gr.uoi.cs.mye30.record.CountryMetadata;

public class CountryMetadataRecordParser implements RecordParser<CountryMetadata> {
	@Override
	public CountryMetadata create(Map<String, String> values) {
		String code = values.get("Country Code");
		String name = values.get("TableName");
		String region = values.get("Region");
		String incomeGroup = values.get("IncomeGroup");
		String specialNotes = values.get("SpecialNotes");
		return new CountryMetadata(code, name, region, incomeGroup, specialNotes);
	}

	@Override
	public int numberOfLinesToSkip() {
		return 1;
	}

}
