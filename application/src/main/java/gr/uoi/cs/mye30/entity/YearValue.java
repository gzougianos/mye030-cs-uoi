package gr.uoi.cs.mye30.entity;

public class YearValue {
	private Country country;
	private Indicator indicator;
	private int year;
	private double value;

	public YearValue(Country country, Indicator indicator, int year, double value) {
		super();
		this.country = country;
		this.indicator = indicator;
		this.year = year;
		this.value = value;
	}

	public Country getCountry() {
		return country;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public int getYear() {
		return year;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "YearValue [country=" + country + ", indicator=" + indicator + ", year=" + year + ", value=" + value
				+ "]";
	}

}
