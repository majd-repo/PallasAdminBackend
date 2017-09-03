package persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String make;
	private String num;
	private String plate;
	private String model;
	private String color;
	private String picture;
	private Date assurance;
	private Date technicalVisit;
	private String phoneNumber;
	private String email;
	private Boolean fetchable;
	private Date dcr;
	private Date dmj;

	public Vehicle() {

	}

	public Vehicle(String make, String num, String plate, String model,
			String color, String picture, String phoneNumber) {
		this.make = make;
		this.num = num;
		this.plate = plate;
		this.model = model;
		this.color = color;
		this.picture = picture;
		this.phoneNumber = phoneNumber;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getAssurance() {
		return assurance;
	}

	public void setAssurance(Date assurance) {
		this.assurance = assurance;
	}

	public Date getTechnicalVisit() {
		return technicalVisit;
	}

	public void setTechnicalVisit(Date technicalVisit) {
		this.technicalVisit = technicalVisit;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getFetchable() {
		return fetchable;
	}

	public void setFetchable(Boolean fetchable) {
		this.fetchable = fetchable;
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

}
