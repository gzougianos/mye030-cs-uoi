package gr.uoi.cs.mye30.entity;

import gr.uoi.cs.mye30.record.IndicatorMetadata;

public class Indicator {
	private int id;
	private IndicatorMetadata metadata;

	public Indicator(int id, IndicatorMetadata metadata) {
		this.id = id;
		this.metadata = metadata;
	}

	public int getId() {
		return id;
	}

	public IndicatorMetadata getMetadata() {
		return metadata;
	}

	@Override
	public String toString() {
		return "Indicator [id=" + id + ", metadata=" + metadata + "]";
	}

}
