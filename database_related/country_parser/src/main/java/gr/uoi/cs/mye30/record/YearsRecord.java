package gr.uoi.cs.mye30.record;

import java.util.Map;

public class YearsRecord {
	private String countryName;
	private String countryCode;
	private String indicatorName;
	private String indicatorCode;
	private Map<Integer, Double> years;

	public YearsRecord(String countryName, String countryCode, String indicatorName, String indicatorCode,
			Map<Integer, Double> years) {
		super();
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.indicatorName = indicatorName;
		this.indicatorCode = indicatorCode;
		this.years = years;
	}

	public String getCountryName() {
		return countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public String getIndicatorCode() {
		return indicatorCode;
	}

	public Map<Integer, Double> getYears() {
		return years;
	}

	@Override
	public String toString() {
		return "YearsRecord [countryName=" + countryName + ", countryCode=" + countryCode + ", indicatorName="
				+ indicatorName + ", indicatorCode=" + indicatorCode + ", years=" + years + "]";
	}

}
