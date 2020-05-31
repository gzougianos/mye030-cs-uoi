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

class CommandLineOptionsParserTests {
	private boolean exited;

	@Test
	void noArgsGiven() throws IOException {
		withArguments(new String[] {});
		assertTrue(exited);
	}

	@Test
	void noUrlOptionGiven() throws IOException {
		withArguments("-f", "hello.txt", "-c", "GRC");
		assertTrue(exited);
	}

	@Test
	void allPossibleArgsGiven() throws IOException {
		withArguments("-f", "hello.txt", "-c", "GRC", "-u", "url");
		assertTrue(exited);
	}

	@Test
	void notFamiliarArgumentGiven() throws IOException {
		withArguments("-g", "hello.txt", "-u", "url");
		assertTrue(exited);
	}

	@Test
	void singleCountryCode() throws IOException {
		CommandLineOptionsParser parser = withArguments("-c", "grc", "-u", "url"); // upper case check as well
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC"), parser.getCountryCodes());
		assertEquals("url", parser.getUrl());
	}

	@Test
	void unableToModify() throws IOException {
		CommandLineOptionsParser parser = withArguments("-c", "grc", "-u", "url"); // upper case check as well
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC"), parser.getCountryCodes());
		assertThrows(UnsupportedOperationException.class, () -> parser.getCountryCodes().add("something"));
		assertThrows(UnsupportedOperationException.class, () -> parser.getCountryCodes().clear());
		assertEquals("url", parser.getUrl());
	}

	@Test
	void multipleCountryCodes() throws IOException {
		CommandLineOptionsParser parser = withArguments("-c", "GRC,ALB  ", "-u", "url"); // trim check too
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC", "ALB"), parser.getCountryCodes());
		assertEquals("url", parser.getUrl());
	}

	@Test
	void fileNotExist() {
		assertThrows(IOException.class, () -> withArguments("-f", "C:/something", "-u", "url"));
	}

	@Test
	void fromFile() throws IOException {
		CommandLineOptionsParser parser = withArguments("-f", getResourceFile("country_codes_test").getAbsolutePath(),
				"-u", "url");
		assertFalse(exited);
		assertEquals(Arrays.asList("GRC", "ALB", "ESP"), parser.getCountryCodes());
		assertEquals("url", parser.getUrl());
	}

	private CommandLineOptionsParser withArguments(String... args) throws IOException {
		return new CommandLineOptionsParser(args, () -> exited = true, new SilentHelpFormatter());
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
