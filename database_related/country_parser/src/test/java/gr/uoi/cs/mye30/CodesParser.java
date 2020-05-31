package gr.uoi.cs.mye30;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodesParser {

	public static void main(String[] args) throws Exception {
		String url = "https://data.worldbank.org/country";
		String content = null;
		URLConnection connection = null;
		connection = new URL(url).openConnection();
		Scanner scanner = new Scanner(connection.getInputStream());
		scanner.useDelimiter("\\Z");
		content = scanner.next();
		scanner.close();

		List<String> urls = new ArrayList<>();
		String baseUrl = "https://data.worldbank.org/country/%s?view=chart";
		String regex = "/country/(.*?)\\?view=chart";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(content);
		while (m.find()) {
			urls.add(String.format(baseUrl, m.group(1)));
		}

		String regex2 = "http://api.worldbank.org/v2/en/country/(.*?)\\?downloadformat=csv";
		Pattern pattern2 = Pattern.compile(regex2);
		for (String urle : urls) {
			connection = new URL(urle).openConnection();
			scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
			Matcher matcher = pattern2.matcher(content);
			while (matcher.find()) {
				System.out.println(matcher.group(1) + " - " + getCountry(urle));
			}
		}
	}

	static String getCountry(String url) {
		String regex = "/country/(.*?)\\?view=chart";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(url);
		while (m.find()) {
			return m.group(1);
		}
		return null;
	}
}
