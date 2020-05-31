package gr.uoi.cs.mye30.parser;

public interface RecordParser<T> {

	T create(String[] values);

	int numberOfLinesToSkip();
}
