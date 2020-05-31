package gr.uoi.cs.mye30.record;

public interface RecordCreator<T> {

	T create(String[] values);
}
