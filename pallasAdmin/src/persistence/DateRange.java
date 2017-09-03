package persistence;

import java.io.Serializable;
import java.util.Date;

public class DateRange implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date dateMin;
	private Date dateMax;

	public Date getDateMin() {
		return dateMin;
	}

	public void setDateMin(Date dateMin) {
		this.dateMin = dateMin;
	}

	public Date getDateMax() {
		return dateMax;
	}

	public void setDateMax(Date dateMax) {
		this.dateMax = dateMax;
	}
}
