package httpObjects;

import java.io.Serializable;

public class MobileLoginEnvelope implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Boolean keepmeconnected;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getKeepmeconnected() {
		return keepmeconnected;
	}

	public void setKeepmeconnected(Boolean keepmeconnected) {
		this.keepmeconnected = keepmeconnected;
	}
}
