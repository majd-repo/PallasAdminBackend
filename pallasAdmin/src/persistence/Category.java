package persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date dcr;
	private Date dmj;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
