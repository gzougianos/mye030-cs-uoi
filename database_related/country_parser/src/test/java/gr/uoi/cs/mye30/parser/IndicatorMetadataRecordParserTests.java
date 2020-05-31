package gr.uoi.cs.mye30.parser;

import static gr.uoi.cs.mye30.TestUtils.getResourceFile;
import static gr.uoi.cs.mye30.parser.CsvFileParser.parseRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import gr.uoi.cs.mye30.record.IndicatorMetadata;

class IndicatorMetadataRecordParserTests {

	@Test
	void main() throws IOException {
		IndicatorMetadataRecordParser parser = new IndicatorMetadataRecordParser();
		List<IndicatorMetadata> records = parseRecords(getResourceFile("indicator_metadata_grc.csv"), parser);
		assertEquals(2, records.size());

		IndicatorMetadata indicator1 = records.get(0);
		assertEquals("VC.PKP.TOTL.UN", indicator1.getCode());
		assertEquals("Presence of peace", indicator1.getName());
		assertEquals("Presence of peacebuilders.", indicator1.getSourceNote());
		assertEquals("UN Department", indicator1.getSourceOrganization());

		IndicatorMetadata indicator2 = records.get(1);
		assertEquals("VC.PKP.TOTL.GG", indicator2.getCode());
		assertEquals("peace", indicator2.getName());
		assertEquals("Presence.", indicator2.getSourceNote());
		assertEquals("UF Department", indicator2.getSourceOrganization());
	}
}
