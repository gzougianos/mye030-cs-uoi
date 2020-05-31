package gr.uoi.cs.mye30.parser;

import java.util.Map;

public interface RecordParser<T> {

	T create(Map<String, String> values);

	int numberOfLinesToSkip();

	default int headersLineIndex() {
		return 0;
	}
}
