package gr.uoi.cs.mye30;

import java.io.IOException;

import org.apache.commons.cli.HelpFormatter;

public class Main {
	public static void main(String[] args) throws IOException {
		CountryCodesOptionParser countryCodesParser = new CountryCodesOptionParser(args, () -> System.exit(0),
				new HelpFormatter());
	}
}
