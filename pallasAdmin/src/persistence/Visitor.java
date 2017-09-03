package persistence;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Visitor extends User {

	private static final long serialVersionUID = 1L;
	private String passport;
	private String citizenship;
	private String gender;
	private Company company;
	private Category category;

	public Visitor() {
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	@OneToOne
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@OneToOne
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}