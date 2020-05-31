package gr.uoi.cs.mye30.record;

public class CountryMetadata {
	private String code;
	private String name;
	private String region;
	private String incomeGroup;
	private String specialNotes;

	public CountryMetadata(String code, String name, String region, String incomeGroup, String specialNotes) {
		this.code = code;
		this.name = name;
		this.region = region;
		this.incomeGroup = incomeGroup;
		this.specialNotes = specialNotes;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getRegion() {
		return region;
	}

	public String getIncomeGroup() {
		return incomeGroup;
	}

	public String getSpecialNotes() {
		return specialNotes;
	}

	@Override
	public String toString() {
		return "CountryMetadata [code=" + code + ", name=" + name + ", region=" + region + ", incomeGroup="
				+ incomeGroup + ", specialNotes=" + specialNotes + "]";
	}

}
