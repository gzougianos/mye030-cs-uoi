package gr.uoi.cs.mye30.parser;

import static gr.uoi.cs.mye30.TestUtils.getResourceFile;
import static gr.uoi.cs.mye30.parser.CsvFileParser.parseRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import gr.uoi.cs.mye30.parser.CountryMetadataRecordParser;
import gr.uoi.cs.mye30.record.CountryMetadata;

class CountryMetadataRecordParserTests {

	@Test
	void test() throws IOException {
		CountryMetadataRecordParser parser = new CountryMetadataRecordParser();
		List<CountryMetadata> records = parseRecords(getResourceFile("country_metadata_grc.csv"), parser);
		assertEquals(1, records.size());
		CountryMetadata country = records.get(0);
		assertEquals("GRC", country.getCode());
		assertEquals("Greece", country.getName());
		assertEquals("regiona", country.getRegion());
		assertEquals("incomegroup1", country.getIncomeGroup());
		assertEquals("specialnotes", country.getSpecialNotes());
	}

}
