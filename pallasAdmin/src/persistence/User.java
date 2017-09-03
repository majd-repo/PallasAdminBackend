package persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String login;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String picture;
	private String token;
	private Long lastTokenUpdate;
	private String address;
	private Integer postalCode;
	private String country;
	private String city;
	private String phoneNumber;
	private UserOption userOption;
	private String role;
	private Boolean fetchable;
	private Boolean keepmeconnected;
	private Date dcr;
	private Date dmj;
	private Boolean active;

	public User() {

	}

	public User(String email, String firstName, String lastName,
			String country, String city, String phoneNumber, String picture) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.picture = picture;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getLastTokenUpdate() {
		return lastTokenUpdate;
	}

	public void setLastTokenUpdate(Long lastTokenCall) {
		this.lastTokenUpdate = lastTokenCall;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@OneToOne
	public UserOption getUserOption() {
		return userOption;
	}

	public void setUserOption(UserOption userOption) {
		this.userOption = userOption;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getFetchable() {
		return fetchable;
	}

	public void setFetchable(Boolean fetchable) {
		this.fetchable = fetchable;
	}

	public Boolean getKeepmeconnected() {
		return keepmeconnected;
	}

	public void setKeepmeconnected(Boolean keepmeconnected) {
		this.keepmeconnected = keepmeconnected;
	}

	public Date getDcr() {
		return dcr;
	}

	public void setDcr(Date dcr) {
		this.dcr = dcr;
	}

	public Date getDmj() {
		return dmj;
	}

	public void setDmj(Date dmj) {
		this.dmj = dmj;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
