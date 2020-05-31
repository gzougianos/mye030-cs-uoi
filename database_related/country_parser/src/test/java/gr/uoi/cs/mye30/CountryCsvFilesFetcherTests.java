package gr.uoi.cs.mye30;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

class CountryCsvFilesFetcherTests {

	@Test
	void test() throws IOException {
		URL testZipURL = TestUtils.getResourceFileURL("test.zip");
		CountryCsvFilesFetcher fetcher = new CountryCsvFilesFetcher(testZipURL);
		assertEquals("years", firstLineOf(fetcher.getYearValuesFile()));
		assertEquals("indicators", firstLineOf(fetcher.getIndicatorsMetadataFile()));
		assertEquals("country", firstLineOf(fetcher.getCountryMetadataFile()));
	}

	private String firstLineOf(File file) throws IOException {
		return Files.readAllLines(file.toPath()).get(0);
	}

}
