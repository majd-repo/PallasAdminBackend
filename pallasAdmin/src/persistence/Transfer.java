package persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Transfer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String codeTransfer;
	private String fromPoint;
	private String destinationPoint;
	private String flightCode;

	private Date timeDepartureEstimated;
	private Date timeArrivalEstimated;
	private Date timeDeparture;
	private Date timeArrival;

	private Date dateTransfer;
	private Vehicle vehicle;
	private Staff driver;
	private Staff cpo;
	private Staff coordinator1;
	private Staff coordinator2;
	private List<Visitor> visitors;
	private String status;
	private Integer nbHoursArrival;
	private Integer nbMinutesArrival;
	private Task task;
	private Date dcr;
	private Date dmj;
	private String category;

	public Transfer() {
	}

	public Transfer(String fromPoint, String destinationPoint,
			String flightCode, Date timeDepartureEstimated,
			Date timeArrivalEstimated, Date timeDeparture, Date timeArrival,
			Date dateTransfer, Vehicle vehicle, Staff driver, Staff cpo,
			Staff coordinator1, Staff coordinator2) {
		super();
		this.fromPoint = fromPoint;
		this.destinationPoint = destinationPoint;
		this.flightCode = flightCode;
		this.timeDepartureEstimated = timeDepartureEstimated;
		this.timeArrivalEstimated = timeArrivalEstimated;
		this.timeDeparture = timeDeparture;
		this.timeArrival = timeArrival;
		this.dateTransfer = dateTransfer;
		this.vehicle = vehicle;
		this.driver = driver;
		this.cpo = cpo;
		this.setCoordinator1(coordinator1);
		this.setCoordinator2(coordinator2);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTransfer() {
		return codeTransfer;
	}

	public void setCodeTransfer(String codeTransfer) {
		this.codeTransfer = codeTransfer;
	}

	public String getFromPoint() {
		return fromPoint;
	}

	public void setFromPoint(String fromPoint) {
		this.fromPoint = fromPoint;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public Date getTimeDepartureEstimated() {
		return timeDepartureEstimated;
	}

	public void setTimeDepartureEstimated(Date timeDepartureEstimated) {
		this.timeDepartureEstimated = timeDepartureEstimated;
	}

	public Date getTimeArrivalEstimated() {
		return timeArrivalEstimated;
	}

	public void setTimeArrivalEstimated(Date timeArrivalEstimated) {
		this.timeArrivalEstimated = timeArrivalEstimated;
	}

	public Date getTimeDeparture() {
		return timeDeparture;
	}

	public void setTimeDeparture(Date timeDeparture) {
		this.timeDeparture = timeDeparture;
	}

	public Date getTimeArrival() {
		return timeArrival;
	}

	public void setTimeArrival(Date timeArrival) {
		this.timeArrival = timeArrival;
	}

	public Date getDateTransfer() {
		return dateTransfer;
	}

	public void setDateTransfer(Date dateTransfer) {
		this.dateTransfer = dateTransfer;
	}

	@OneToOne
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@OneToOne
	public Staff getDriver() {
		return driver;
	}

	public void setDriver(Staff driver) {
		this.driver = driver;
	}

	@OneToOne
	public Staff getCpo() {
		return cpo;
	}

	public void setCpo(Staff cpo) {
		this.cpo = cpo;
	}

	public String getDestinationPoint() {
		return destinationPoint;
	}

	public void setDestinationPoint(String destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "transfer_visitor")
	public List<Visitor> getVisitors() {
		return visitors;
	}

	public void setVisitors(List<Visitor> visitors) {
		this.visitors = visitors;
	}

	@OneToOne
	public Staff getCoordinator1() {
		return coordinator1;
	}

	public void setCoordinator1(Staff coordinator1) {
		this.coordinator1 = coordinator1;
	}

	@OneToOne
	public Staff getCoordinator2() {
		return coordinator2;
	}

	public void setCoordinator2(Staff coordinator2) {
		this.coordinator2 = coordinator2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNbHoursArrival() {
		return nbHoursArrival;
	}

	public void setNbHoursArrival(Integer nbHoursArrival) {
		this.nbHoursArrival = nbHoursArrival;
	}

	public Integer getNbMinutesArrival() {
		return nbMinutesArrival;
	}

	public void setNbMinutesArrival(Integer nbMinutesArrival) {
		this.nbMinutesArrival = nbMinutesArrival;
	}

	@OneToOne
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
