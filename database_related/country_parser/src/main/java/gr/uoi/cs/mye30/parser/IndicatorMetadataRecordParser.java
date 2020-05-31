package gr.uoi.cs.mye30.parser;

import java.util.Map;

import gr.uoi.cs.mye30.record.IndicatorMetadata;

public class IndicatorMetadataRecordParser implements RecordParser<IndicatorMetadata> {

	@Override
	public int numberOfLinesToSkip() {
		return 1;
	}

	@Override
	public IndicatorMetadata create(Map<String, String> values) {
		String code = values.get("INDICATOR_CODE");
		String name = values.get("INDICATOR_NAME");
		String sourceNote = values.get("SOURCE_NOTE");
		String sourceOrganization = values.get("SOURCE_ORGANIZATION");
		return new IndicatorMetadata(code, name, sourceNote, sourceOrganization);
	}

}
