package gr.uoi.cs.mye30.parser;

import static gr.uoi.cs.mye30.TestUtils.getResourceFile;
import static gr.uoi.cs.mye30.parser.CsvFileParser.parseRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import gr.uoi.cs.mye30.record.YearsRecord;

class YearsRecordParserTests {

	@Test
	void test() throws IOException {
		YearsRecordParser parser = new YearsRecordParser();
		List<YearsRecord> records = parseRecords(getResourceFile("years.csv"), parser);
		assertEquals(2, records.size());

		YearsRecord record1 = records.get(0);
		assertEquals("Greece", record1.getCountryName());
		assertEquals("GRC", record1.getCountryCode());
		assertEquals("Presence of peace keepers", record1.getIndicatorName());
		assertEquals("VC.PKP.TOTL.UN", record1.getIndicatorCode());
		assertEquals(2, record1.getYears().size());
		assertEquals(12.25d, record1.getYears().get(1960));
		assertEquals(13.67d, record1.getYears().get(1962));

		YearsRecord record2 = records.get(1);
		assertEquals("Albania", record2.getCountryName());
		assertEquals("ALB", record2.getCountryCode());
		assertEquals("Intentional homicides", record2.getIndicatorName());
		assertEquals("VC.IHR.PSRC.P5", record2.getIndicatorCode());
		assertEquals(1, record2.getYears().size());
		assertEquals(43.25d, record2.getYears().get(1962));
	}

}
