package httpObjects;

import java.io.Serializable;

public class PictureEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String fileName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
