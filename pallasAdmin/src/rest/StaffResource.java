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

import persistence.Staff;
import security.Role;
import security.Secured;
import services.interfaces.StaffServiceLocal;

@Secured({ Role.ADMIN })
@RequestScoped
@Path("staff")
public class StaffResource {

	@Inject
	private StaffServiceLocal staffServiceLocal;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Staff> doFindAll() {
		return staffServiceLocal.findAll();
	}

	@GET
	@Path("activateStaff/{id}/{active}")
	public void doActivateById(@PathParam("id") Integer id,
			@PathParam("active") Boolean active) {
		staffServiceLocal.activateStaff(id, active);
	}

	@GET
	@Path("drivers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Staff> doFindAllDrivers() {
		return staffServiceLocal.findAllDrivers();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Staff doFindStaffById(@PathParam("id") Integer id) {
		return staffServiceLocal.findStaffById(id);
	}

	@GET
	@Path("cpos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Staff> doFindAllCPOs() {
		return staffServiceLocal.findAllCPOs();
	}

	@GET
	@Path("coordinators")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Staff> doFindAllCoordinators() {
		return staffServiceLocal.findAllCoordinators();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doAdd(Staff staff) {
		staffServiceLocal.add(staff);
	}

	@PUT
	@Path("{id}")
	public void doSave(Staff staff) {
		staffServiceLocal.save(staff);
	}

	@DELETE
	@Path("{id}")
	public void doDelete(@PathParam("id") Integer id) {
		staffServiceLocal.delete(id);
	}

	@POST
	@Path("setPicture")
	public void doSetPicture(PictureEnvelope envelope) {
		staffServiceLocal.setPicture(envelope.getId(), envelope.getFileName());
	}
}
