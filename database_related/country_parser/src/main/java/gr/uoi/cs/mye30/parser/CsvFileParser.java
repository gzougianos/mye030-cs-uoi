package gr.uoi.cs.mye30.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvFileParser {
	private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\"(.*?)\",");

	private CsvFileParser() {
	}

	public static <T> List<T> parseRecords(File csvFile, RecordParser<T> recordParser) throws IOException {
		List<String> allLines = Files.readAllLines(csvFile.toPath());
		IntStream.range(0, recordParser.numberOfLinesToSkip()).forEach(i -> allLines.remove(0));

		removeProblematicLinesAndReAddThemNormally(allLines);

		//@formatter:off
		return allLines.stream()
				.map(CsvFileParser::split)
				.map(recordParser::create)
				.collect(Collectors.toList());
		//@formatter:on
	}

	private static void removeProblematicLinesAndReAddThemNormally(List<String> allLines) {
		List<String> problematicLines = allLines.stream().filter(l -> !l.startsWith("\"") || !l.endsWith("\","))
				.collect(Collectors.toList());

		allLines.removeAll(problematicLines);

		StringBuilder sb = new StringBuilder();
		problematicLines.stream().filter(l -> !l.isEmpty()).forEach(l -> {
			if (l.startsWith("\"")) {
				sb.append(System.lineSeparator());
				sb.append(l);
			} else {
				sb.append(l);
			}
		});

		allLines.addAll(Arrays.asList(sb.toString().trim().split(System.lineSeparator())));

		allLines.removeIf(String::isEmpty);
	}

	private static String[] split(String line) {
		Matcher m = SEPARATOR_PATTERN.matcher(line);

		List<String> matches = new LinkedList<>();
		while (m.find()) {
			matches.add(m.group(1));
		}
		return matches.toArray(new String[matches.size()]);
	}

}
