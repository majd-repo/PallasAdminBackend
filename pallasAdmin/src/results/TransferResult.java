package results;

import java.util.Date;

public class TransferResult {
	private Integer id;
	private Date dateTransfer;
	private String fromPoint;
	private String destinationPoint;
	private String flightCode;

	private String driverFirstName;
	private String driverLastName;
	private String driverPhoneNumber;
	private String driverEmail;
	private String driverPicture;

	private String cpoFirstName;
	private String cpoLastName;
	private String cpoPhoneNumber;
	private String cpoEmail;
	private String cpoPicture;

	private String coordinator1FirstName;
	private String coordinator1LastName;
	private String coordinator1PhoneNumber;
	private String coordinator1Email;
	private String coordinator1Picture;

	private String coordinator2FirstName;
	private String coordinator2LastName;
	private String coordinator2PhoneNumber;
	private String coordinator2Email;
	private String coordinator2Picture;

	private Date timeDepartureEstimated;
	private Date timeArrivalEstimated;
	private Date timeDeparture;
	private Date timeArrival;

	private String status;

	private String vehiculeMake;
	private String vehiculeModel;
	private String vehiculePlate;
	private String vehiculePicture;

	private String codeTransfer;
	private String categoryName;

	public TransferResult(Integer id, Date dateTransfer, String fromPoint,
			String destinationPoint, String flightCode, String driverFirstName,
			String driverLastName, String driverPhoneNumber,
			String driverEmail, String driverPicture, String cpoFirstName,
			String cpoLastName, String cpoPhoneNumber, String cpoEmail,
			String cpoPicture, String coordinator1FirstName,
			String coordinator1LastName, String coordinator1PhoneNumber,
			String coordinator1Email, String coordinator1Picture,
			String coordinator2FirstName, String coordinator2LastName,
			String coordinator2PhoneNumber, String coordinator2Email,
			String coordinator2Picture, Date timeDepartureEstimated,
			Date timeArrivalEstimated, Date timeDeparture, Date timeArrival,
			String status, String vehiculeMake, String vehiculeModel,
			String vehiculePlate, String vehiculePicture, String codeTransfer,
			String categoryName) {
		super();
		this.id = id;
		this.dateTransfer = dateTransfer;
		this.fromPoint = fromPoint;
		this.destinationPoint = destinationPoint;
		this.flightCode = flightCode;
		this.driverFirstName = driverFirstName;
		this.driverLastName = driverLastName;
		this.driverPhoneNumber = driverPhoneNumber;
		this.driverEmail = driverEmail;
		this.driverPicture = driverPicture;
		this.cpoFirstName = cpoFirstName;
		this.cpoLastName = cpoLastName;
		this.cpoPhoneNumber = cpoPhoneNumber;
		this.cpoEmail = cpoEmail;
		this.cpoPicture = cpoPicture;
		this.coordinator1FirstName = coordinator1FirstName;
		this.coordinator1LastName = coordinator1LastName;
		this.coordinator1PhoneNumber = coordinator1PhoneNumber;
		this.coordinator1Email = coordinator1Email;
		this.coordinator1Picture = coordinator1Picture;
		this.coordinator2FirstName = coordinator2FirstName;
		this.coordinator2LastName = coordinator2LastName;
		this.coordinator2PhoneNumber = coordinator2PhoneNumber;
		this.coordinator2Email = coordinator2Email;
		this.coordinator2Picture = coordinator2Picture;
		this.timeDepartureEstimated = timeDepartureEstimated;
		this.timeArrivalEstimated = timeArrivalEstimated;
		this.timeDeparture = timeDeparture;
		this.timeArrival = timeArrival;
		this.status = status;
		this.vehiculeMake = vehiculeMake;
		this.vehiculeModel = vehiculeModel;
		this.vehiculePlate = vehiculePlate;
		this.vehiculePicture = vehiculePicture;
		this.codeTransfer = codeTransfer;
		this.categoryName = categoryName;
	}

	public TransferResult() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateTransfer() {
		return dateTransfer;
	}

	public void setDateTransfer(Date dateTransfer) {
		this.dateTransfer = dateTransfer;
	}

	public String getFromPoint() {
		return fromPoint;
	}

	public void setFromPoint(String fromPoint) {
		this.fromPoint = fromPoint;
	}

	public String getDestinationPoint() {
		return destinationPoint;
	}

	public void setDestinationPoint(String destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverPhoneNumber() {
		return driverPhoneNumber;
	}

	public void setDriverPhoneNumber(String driverPhoneNumber) {
		this.driverPhoneNumber = driverPhoneNumber;
	}

	public String getDriverEmail() {
		return driverEmail;
	}

	public void setDriverEmail(String driverEmail) {
		this.driverEmail = driverEmail;
	}

	public String getCpoFirstName() {
		return cpoFirstName;
	}

	public void setCpoFirstName(String cpoFirstName) {
		this.cpoFirstName = cpoFirstName;
	}

	public String getCpoLastName() {
		return cpoLastName;
	}

	public void setCpoLastName(String cpoLastName) {
		this.cpoLastName = cpoLastName;
	}

	public String getCpoPhoneNumber() {
		return cpoPhoneNumber;
	}

	public void setCpoPhoneNumber(String cpoPhoneNumber) {
		this.cpoPhoneNumber = cpoPhoneNumber;
	}

	public String getCpoEmail() {
		return cpoEmail;
	}

	public void setCpoEmail(String cpoEmail) {
		this.cpoEmail = cpoEmail;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getCpoPicture() {
		return cpoPicture;
	}

	public void setCpoPicture(String cpoPicture) {
		this.cpoPicture = cpoPicture;
	}

	public String getDriverPicture() {
		return driverPicture;
	}

	public void setDriverPicture(String driverPicture) {
		this.driverPicture = driverPicture;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVehiculeMake() {
		return vehiculeMake;
	}

	public void setVehiculeMake(String vehiculeMake) {
		this.vehiculeMake = vehiculeMake;
	}

	public String getVehiculeModel() {
		return vehiculeModel;
	}

	public void setVehiculeModel(String vehiculeModel) {
		this.vehiculeModel = vehiculeModel;
	}

	public String getVehiculePlate() {
		return vehiculePlate;
	}

	public void setVehiculePlate(String vehiculePlate) {
		this.vehiculePlate = vehiculePlate;
	}

	public String getVehiculePicture() {
		return vehiculePicture;
	}

	public void setVehiculePicture(String vehiculePicture) {
		this.vehiculePicture = vehiculePicture;
	}

	public String getCoordinator1FirstName() {
		return coordinator1FirstName;
	}

	public void setCoordinator1FirstName(String coordinator1FirstName) {
		this.coordinator1FirstName = coordinator1FirstName;
	}

	public String getCoordinator1LastName() {
		return coordinator1LastName;
	}

	public void setCoordinator1LastName(String coordinator1LastName) {
		this.coordinator1LastName = coordinator1LastName;
	}

	public String getCoordinator1PhoneNumber() {
		return coordinator1PhoneNumber;
	}

	public void setCoordinator1PhoneNumber(String coordinator1PhoneNumber) {
		this.coordinator1PhoneNumber = coordinator1PhoneNumber;
	}

	public String getCoordinator1Email() {
		return coordinator1Email;
	}

	public void setCoordinator1Email(String coordinator1Email) {
		this.coordinator1Email = coordinator1Email;
	}

	public String getCoordinator1Picture() {
		return coordinator1Picture;
	}

	public void setCoordinator1Picture(String coordinator1Picture) {
		this.coordinator1Picture = coordinator1Picture;
	}

	public String getCoordinator2FirstName() {
		return coordinator2FirstName;
	}

	public void setCoordinator2FirstName(String coordinator2FirstName) {
		this.coordinator2FirstName = coordinator2FirstName;
	}

	public String getCoordinator2LastName() {
		return coordinator2LastName;
	}

	public void setCoordinator2LastName(String coordinator2LastName) {
		this.coordinator2LastName = coordinator2LastName;
	}

	public String getCoordinator2PhoneNumber() {
		return coordinator2PhoneNumber;
	}

	public void setCoordinator2PhoneNumber(String coordinator2PhoneNumber) {
		this.coordinator2PhoneNumber = coordinator2PhoneNumber;
	}

	public String getCoordinator2Email() {
		return coordinator2Email;
	}

	public void setCoordinator2Email(String coordinator2Email) {
		this.coordinator2Email = coordinator2Email;
	}

	public String getCoordinator2Picture() {
		return coordinator2Picture;
	}

	public void setCoordinator2Picture(String coordinator2Picture) {
		this.coordinator2Picture = coordinator2Picture;
	}

	public String getCodeTransfer() {
		return codeTransfer;
	}

	public void setCodeTransfer(String codeTransfer) {
		this.codeTransfer = codeTransfer;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
