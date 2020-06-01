package gr.uoi.cs.mye30.entity;

public class Country {
	private int id;
	private String name;
	private String code;
	private String region;
	private String incomeGroup;
	private String specialNotes;

	public Country(int id, String name, String code, String region, String incomeGroup, String specialNotes) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.region = region;
		this.incomeGroup = incomeGroup;
		this.specialNotes = specialNotes;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
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
		return "Country [id=" + id + ", name=" + name + ", code=" + code + ", region=" + region + ", regionGroup="
				+ incomeGroup + ", specialNotes=" + specialNotes + "]";
	}

}
