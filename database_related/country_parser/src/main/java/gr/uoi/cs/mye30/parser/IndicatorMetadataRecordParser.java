package gr.uoi.cs.mye30.parser;

import gr.uoi.cs.mye30.record.IndicatorMetadata;

public class IndicatorMetadataRecordParser implements RecordParser<IndicatorMetadata> {

	@Override
	public IndicatorMetadata create(String[] values) {
		return new IndicatorMetadata(values[0], values[1], values[2], values[3]);
	}

	@Override
	public int numberOfLinesToSkip() {
		return 1;
	}

}
