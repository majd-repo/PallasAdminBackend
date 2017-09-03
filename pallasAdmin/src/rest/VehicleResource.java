package rest;

import httpObjects.PictureEnvelope;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import persistence.Vehicle;
import security.Role;
import security.Secured;
import services.interfaces.VehicleServiceLocal;

@Secured({ Role.ADMIN })
@RequestScoped
@Path("/vehicles")
public class VehicleResource {

	@Inject
	private VehicleServiceLocal vehicleServiceLocal;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Vehicle> doFindAllVehicles() {
		return vehicleServiceLocal.findAllVehicles();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doAddVehicle(Vehicle vehicle) {
		vehicleServiceLocal.addVehicle(vehicle);
	}

	@PUT
	@Path("{id}")
	public void doUpdateVehicle(Vehicle vehicle) {
		vehicleServiceLocal.updateVehicle(vehicle);
	}

	@DELETE
	@Path("{id}")
	public void doRemoveVehicle(@PathParam("id") Integer id) {
		vehicleServiceLocal.removeVehicle(id);
	}

	@POST
	@Path("setPicture")
	public void doSetPicture(PictureEnvelope envelope) {
		vehicleServiceLocal
				.setPicture(envelope.getId(), envelope.getFileName());
	}
}