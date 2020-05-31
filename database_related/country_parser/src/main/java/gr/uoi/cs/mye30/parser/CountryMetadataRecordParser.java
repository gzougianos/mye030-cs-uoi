package gr.uoi.cs.mye30.parser;

import gr.uoi.cs.mye30.record.CountryMetadata;

public class CountryMetadataRecordParser implements RecordParser<CountryMetadata> {

	@Override
	public CountryMetadata create(String[] values) {
		return new CountryMetadata(values[0], values[4], values[1], values[2], values[3]);
	}

	@Override
	public int numberOfLinesToSkip() {
		return 1;
	}

}
