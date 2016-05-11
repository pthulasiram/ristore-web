package org.mdacc.rists.ristore.ws.model;

public class Foundation {
	private String reportId;
	private String sampleId;
	private String mrn;
	private String diagnosis;
	public Foundation(String reportId, String sampleId, String mrn, String diagnosis) {
		super();
		this.reportId = reportId;
		this.sampleId = sampleId;
		this.mrn = mrn;
		this.diagnosis = diagnosis;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	public String getMrn() {
		return mrn;
	}
	public void setMrn(String mrn) {
		this.mrn = mrn;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	
}
