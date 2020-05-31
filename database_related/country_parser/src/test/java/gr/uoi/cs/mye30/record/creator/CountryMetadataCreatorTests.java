package gr.uoi.cs.mye30.record.creator;

import static gr.uoi.cs.mye30.CsvRecordFileParser.parseRecords;
import static gr.uoi.cs.mye30.TestUtils.getResourceFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import gr.uoi.cs.mye30.record.CountryMetadata;

class CountryMetadataCreatorTests {

	@Test
	void test() throws IOException {
		CountryMetadataCreator creator = new CountryMetadataCreator();
		List<CountryMetadata> records = parseRecords(getResourceFile("country_metadata_grc.csv"), creator, 1);
		assertEquals(1, records.size());
		CountryMetadata countryMetadata = records.get(0);
		assertEquals("GRC", countryMetadata.getCode());
		assertEquals("Greece", countryMetadata.getName());
		assertEquals("regiona", countryMetadata.getRegion());
		assertEquals("incomegroup1", countryMetadata.getIncomeGroup());
		assertEquals("specialnotes", countryMetadata.getSpecialNotes());
	}

}
