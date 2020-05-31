package gr.uoi.cs.mye30.record;

public class IndicatorMetadata {
	private String code;
	private String name;
	private String sourceNote;
	private String sourceOrganization;

	public IndicatorMetadata(String code, String name, String sourceNote, String sourceOrganization) {
		super();
		this.code = code;
		this.name = name;
		this.sourceNote = sourceNote;
		this.sourceOrganization = sourceOrganization;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getSourceNote() {
		return sourceNote;
	}

	public String getSourceOrganization() {
		return sourceOrganization;
	}

	@Override
	public String toString() {
		return "IndicatorMetadata [code=" + code + ", name=" + name + ", sourceNote=" + sourceNote
				+ ", sourceOrganization=" + sourceOrganization + "]";
	}

}
