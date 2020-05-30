package gr.uoi.cs.mye30;

import static gr.uoi.cs.mye30.TestUtils.getResourceFile;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryCodesOptionParserTests {
	private boolean exited;

	@Test
	void noArgsGiven() throws IOException {
		withArguments(new String[] {});
		assertTrue(exited);
	}

	@Test
	void moreThan1ArgumentGiven() throws IOException {
		withArguments("-f", "hello.txt", "-c", "GRC");
		assertTrue(exited);
	}

	@Test
	void notFamiliarArgumentGiven() throws IOException {
		withArguments("-g", "hello.txt");
		assertTrue(exited);
	}

	@Test
	void singleCountryCode() throws IOException {
		CountryCodesOptionParser parser = withArguments("-c", "grc"); // upper case check as well
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC"), parser.getCountryCodes());
	}

	@Test
	void unableToModify() throws IOException {
		CountryCodesOptionParser parser = withArguments("-c", "grc"); // upper case check as well
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC"), parser.getCountryCodes());
		assertThrows(UnsupportedOperationException.class, () -> parser.getCountryCodes().add("something"));
		assertThrows(UnsupportedOperationException.class, () -> parser.getCountryCodes().clear());
	}

	@Test
	void multipleCountryCodes() throws IOException {
		CountryCodesOptionParser parser = withArguments("-c", "GRC,ALB  "); // trim check too
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC", "ALB"), parser.getCountryCodes());
	}

	@Test
	void fileNotExist() {
		assertThrows(IOException.class, () -> withArguments("-f", "C:/something"));
	}

	@Test
	void fromFile() throws IOException {
		CountryCodesOptionParser parser = withArguments("-f", getResourceFile("country_codes_test").getAbsolutePath());
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC", "ALB", "ESP"), parser.getCountryCodes());
	}

	private CountryCodesOptionParser withArguments(String... args) throws IOException {
		return new CountryCodesOptionParser(args, () -> exited = true, new SilentHelpFormatter());
	}

	@BeforeEach
	public void beforeEach() {
		exited = false;
	}

	private static class SilentHelpFormatter extends HelpFormatter {
		@Override
		public void printHelp(String cmdLineSyntax, Options options) {

		}
	}
}
