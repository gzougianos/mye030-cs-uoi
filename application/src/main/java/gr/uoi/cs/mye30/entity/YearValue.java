package gr.uoi.cs.mye30.entity;

public class YearValue {
	private Country country;
	private Indicator indicator;
	private int year;
	private double value;
	private boolean fake;

	public YearValue(Country country, Indicator indicator, int year, double value, boolean fake) {
		super();
		this.country = country;
		this.indicator = indicator;
		this.year = year;
		this.value = value;
		this.fake = fake;
	}

	public Country getCountry() {
		return country;
	}

	public boolean isFake() {
		return fake;
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
