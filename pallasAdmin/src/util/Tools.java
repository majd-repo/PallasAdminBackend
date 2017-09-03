package util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import persistence.Transfer;

public class Tools {

	public static JsonObject ParseTransferToTransferResult(Transfer transfer) {

		JsonObjectBuilder object = Json
				.createObjectBuilder()
				.add("id", transfer.getId())
				.add("dateTransfert",
						transfer.getDateTransfer() == null ? "" : transfer
								.getDateTransfer().toString())
				.add("fromPoint",
						transfer.getFromPoint() == null ? "" : transfer
								.getFromPoint())
				.add("destinationPoint",
						transfer.getDestinationPoint() == null ? "" : transfer
								.getDestinationPoint())
				.add("flightCode",
						transfer.getFlightCode() == null ? "" : transfer
								.getFlightCode())
				.add("driverFirstName",
						transfer.getDriver().getFirstName() == null ? ""
								: transfer.getDriver().getFirstName())
				.add("driverLastName",
						transfer.getDriver().getLastName() == null ? ""
								: transfer.getDriver().getLastName())
				.add("driverPhoneNumber",
						transfer.getDriver().getPhoneNumber() == null ? ""
								: transfer.getDriver().getPhoneNumber())
				.add("driverEmail",
						transfer.getDriver().getEmail() == null ? "" : transfer
								.getDriver().getEmail())
				.add("driverPicture",
						transfer.getDriver().getPicture() == null ? ""
								: transfer.getDriver().getPicture())
				.add("cpoFirstName",
						transfer.getCpo().getFirstName() == null ? ""
								: transfer.getCpo().getFirstName())
				.add("cpoLastName",
						transfer.getCpo().getLastName() == null ? "" : transfer
								.getCpo().getLastName())
				.add("cpoPhoneNumber",
						transfer.getCpo().getPhoneNumber() == null ? ""
								: transfer.getCpo().getPhoneNumber())
				.add("cpoEmail",
						transfer.getCpo().getEmail() == null ? "" : transfer
								.getCpo().getEmail())
				.add("cpoPicture",
						transfer.getCpo().getPicture() == null ? "" : transfer
								.getCpo().getPicture())
				.add("coordinator1FirstName",
						transfer.getCoordinator1().getFirstName() == null ? ""
								: transfer.getCoordinator1().getFirstName())
				.add("coordinator1LastName",
						transfer.getCoordinator1().getLastName() == null ? ""
								: transfer.getCoordinator1().getLastName())
				.add("coordinator1PhoneNumber",
						transfer.getCoordinator1().getPhoneNumber() == null ? ""
								: transfer.getCoordinator1().getPhoneNumber())
				.add("coordinator1Email",
						transfer.getCoordinator1().getEmail() == null ? ""
								: transfer.getCoordinator1().getEmail())
				.add("coordinator1Picture",
						transfer.getCoordinator1().getPicture() == null ? ""
								: transfer.getCoordinator1().getPicture())
				.add("coordinator2FirstName",
						transfer.getCoordinator2().getFirstName() == null ? ""
								: transfer.getCoordinator2().getFirstName())
				.add("coordinator2LastName",
						transfer.getCoordinator2().getLastName() == null ? ""
								: transfer.getCoordinator2().getLastName())
				.add("coordinator2PhoneNumber",
						transfer.getCoordinator2().getPhoneNumber() == null ? ""
								: transfer.getCoordinator2().getPhoneNumber())
				.add("coordinator2Email",
						transfer.getCoordinator2().getEmail() == null ? ""
								: transfer.getCoordinator2().getEmail())
				.add("coordinator2Picture",
						transfer.getCoordinator2().getPicture() == null ? ""
								: transfer.getCoordinator2().getPicture())
				.add("timeDepartureEstimated",
						transfer.getTimeDepartureEstimated() == null ? ""
								: transfer.getTimeDepartureEstimated()
										.toString())
				.add("timeArrivalEstimated",
						transfer.getTimeArrivalEstimated() == null ? ""
								: transfer.getTimeArrivalEstimated().toString())
				.add("timeDeparture",
						transfer.getTimeDeparture() == null ? "" : transfer
								.getTimeDeparture().toString())
				.add("timeArrival",
						transfer.getTimeArrival() == null ? "" : transfer
								.getTimeArrival().toString())
				.add("status",
						transfer.getStatus() == null ? "" : transfer
								.getStatus())
				.add("vehiculeMake",
						transfer.getVehicle().getMake() == null ? "" : transfer
								.getVehicle().getMake())
				.add("vehiculeModel",
						transfer.getVehicle().getModel() == null ? ""
								: transfer.getVehicle().getModel())
				.add("vehiculePlate",
						transfer.getVehicle().getPlate() == null ? ""
								: transfer.getVehicle().getPlate());
		return object.build();
		/*
		 * .add("address", factory.createObjectBuilder() .add("streetAddress",
		 * "21 2nd Street") .add("city", "New York") .add("state", "NY")
		 * .add("postalCode", "10021"))
		 */

		/*
		 * TransferResult transferResult = new TransferResult(transfer.getId(),
		 * transfer.getDateTransfer(), transfer.getFromPoint(),
		 * transfer.getDestinationPoint(), transfer.getFlightCode(),
		 * transfer.getDriver().getFirstName(), transfer.getDriver()
		 * .getLastName(), transfer.getDriver().getPhoneNumber(),
		 * transfer.getDriver().getEmail(), transfer.getDriver() .getPicture(),
		 * transfer.getCpo().getFirstName(), transfer.getCpo().getLastName(),
		 * transfer.getCpo() .getPhoneNumber(), transfer.getCpo().getEmail(),
		 * transfer.getCpo().getPicture(), transfer.getCoordinator1()
		 * .getFirstName(), transfer.getCoordinator1() .getLastName(),
		 * transfer.getCoordinator1() .getPhoneNumber(),
		 * transfer.getCoordinator1() .getEmail(),
		 * transfer.getCoordinator1().getPicture(),
		 * transfer.getCoordinator2().getFirstName(), transfer
		 * .getCoordinator2().getLastName(), transfer
		 * .getCoordinator2().getPhoneNumber(), transfer
		 * .getCoordinator2().getEmail(), transfer
		 * .getCoordinator2().getPicture(),
		 * transfer.getTimeDepartureEstimated(),
		 * transfer.getTimeArrivalEstimated(), transfer.getTimeDeparture(),
		 * transfer.getTimeArrival(), transfer.getStatus(),
		 * transfer.getVehicle().getMake(), transfer .getVehicle().getModel(),
		 * transfer.getVehicle() .getPlate(),
		 * transfer.getVehicle().getPicture(), transfer.getCodeTransfer(),
		 * transfer.getCategory());
		 */
		// return object;
	}
}
