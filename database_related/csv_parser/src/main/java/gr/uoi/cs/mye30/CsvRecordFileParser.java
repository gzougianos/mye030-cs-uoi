package gr.uoi.cs.mye30;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import gr.uoi.cs.mye30.record.RecordCreator;

public class CsvRecordFileParser {
	private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\"(.*?)\",");

	private CsvRecordFileParser() {
	}

	public static <T> List<T> parseRecords(File csvFile, RecordCreator<T> recordCreator, int linesToSkip)
			throws IOException {
		List<String> allLines = Files.readAllLines(csvFile.toPath());
		IntStream.range(0, linesToSkip).forEach(i -> allLines.remove(0));

		//@formatter:off
		return allLines.stream()
				.map(CsvRecordFileParser::split)
				.map(recordCreator::create)
				.collect(Collectors.toList());
		//@formatter:on
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
