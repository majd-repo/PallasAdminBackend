package httpObjects;

import java.io.Serializable;

public class TaskCodeEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer companyid;
	private String nbDays;
	private String nbMonths;

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public String getNbDays() {
		return nbDays;
	}

	public void setNbDays(String nbDays) {
		this.nbDays = nbDays;
	}

	public String getNbMonths() {
		return nbMonths;
	}

	public void setNbMonths(String nbMonths) {
		this.nbMonths = nbMonths;
	}

}
