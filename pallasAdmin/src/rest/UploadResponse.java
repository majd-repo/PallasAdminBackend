package rest;

//@XmlRootElement
public class UploadResponse {
	private String url;

	public UploadResponse(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
