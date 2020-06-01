package gr.uoi.cs.mye30.entity;

public class Indicator {
	private int id;
	private String name;
	private String code;
	private String sourceNote;
	private String sourceOrganization;

	public Indicator(int id, String name, String code, String sourceNote, String sourceOrganization) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.sourceNote = sourceNote;
		this.sourceOrganization = sourceOrganization;
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

	public String getSourceNote() {
		return sourceNote;
	}

	public String getSourceOrganization() {
		return sourceOrganization;
	}

	@Override
	public String toString() {
		return "Indicator [id=" + id + ", name=" + name + ", code=" + code + ", sourceNote=" + sourceNote
				+ ", sourceOrganization=" + sourceOrganization + "]";
	}

}
