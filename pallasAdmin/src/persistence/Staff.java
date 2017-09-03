package persistence;

import javax.persistence.Entity;

@Entity
public class Staff extends User {

	private static final long serialVersionUID = 1L;
	private String agentLevel;
	private String phoneNumber2;

	public Staff() {
	}

	public Staff(String email, String firstName, String lastName,
			String country, String city, String phoneNumber, String picture,
			String agentLevel) {
		super(email, firstName, lastName, country, city, phoneNumber, picture);
		this.agentLevel = agentLevel;
	}

	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
}
