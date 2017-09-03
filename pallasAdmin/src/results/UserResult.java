package results;

import java.io.Serializable;

public class UserResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String picture;
	private Long lastTokenUpdate;
	private Boolean keepmeconnected;
	private String role;
	private String agentLevel;
	private Boolean active;

	public UserResult(Integer id, String login, String password,
			String firstName, String lastName, String email, String picture,
			Long lastTokenUpdate, Boolean keepmeconnected, String role,
			String agentLevel, Boolean active) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.picture = picture;
		this.lastTokenUpdate = lastTokenUpdate;
		this.keepmeconnected = keepmeconnected;
		this.role = role;
		this.agentLevel = agentLevel;
		this.active = active;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Long getLastTokenUpdate() {
		return lastTokenUpdate;
	}

	public void setLastTokenUpdate(Long lastTokenUpdate) {
		this.lastTokenUpdate = lastTokenUpdate;
	}

	public Boolean getKeepmeconnected() {
		return keepmeconnected;
	}

	public void setKeepmeconnected(Boolean keepmeconnected) {
		this.keepmeconnected = keepmeconnected;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
