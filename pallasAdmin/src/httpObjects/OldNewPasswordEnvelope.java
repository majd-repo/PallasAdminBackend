package httpObjects;

import java.io.Serializable;

public class OldNewPasswordEnvelope implements Serializable {
	private static final long serialVersionUID = 1L;
	private String token;
	private String oldpassword;
	private String newpassword;
	private Boolean keepmeconnected;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public Boolean getKeepmeconnected() {
		return keepmeconnected;
	}

	public void setKeepmeconnected(Boolean keepmeconnected) {
		this.keepmeconnected = keepmeconnected;
	}
}
