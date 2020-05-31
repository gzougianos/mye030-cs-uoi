package gr.uoi.cs.mye30;

import java.io.IOException;

public class Main {
	private static final String URL_BASE = "http://api.worldbank.org/v2/en/country/%s?downloadformat=csv";

	public static void main(String[] args) throws IOException {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tH:%1$tM:%1$tS %4$-1s %5$s%6$s%n");

	}
}
