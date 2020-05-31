package gr.uoi.cs.mye30.entity;

import gr.uoi.cs.mye30.record.CountryMetadata;

public class Country {
	private int id;
	private CountryMetadata metadata;

	public Country(int id, CountryMetadata metadata) {
		this.id = id;
		this.metadata = metadata;
	}

	public int getId() {
		return id;
	}

	public CountryMetadata getMetadata() {
		return metadata;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", metadata=" + metadata + "]";
	}

}
