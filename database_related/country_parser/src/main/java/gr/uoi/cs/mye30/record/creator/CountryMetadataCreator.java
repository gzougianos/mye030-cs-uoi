package gr.uoi.cs.mye30.record.creator;

import gr.uoi.cs.mye30.record.CountryMetadata;
import gr.uoi.cs.mye30.record.RecordCreator;

public class CountryMetadataCreator implements RecordCreator<CountryMetadata> {

	@Override
	public CountryMetadata create(String[] values) {
		System.out.println(values.length);
		return new CountryMetadata(values[0], values[4], values[1], values[2], values[3]);
	}

}
