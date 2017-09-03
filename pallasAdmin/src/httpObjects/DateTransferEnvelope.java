package httpObjects;

import java.io.Serializable;
import java.util.Date;

public class DateTransferEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer transferId;
	private Date dateTime;

	public Integer getTransferId() {
		return transferId;
	}

	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
