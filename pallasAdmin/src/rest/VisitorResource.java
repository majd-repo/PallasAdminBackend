package rest;

import httpObjects.PictureEnvelope;

import java.util.List;

import javax.faces.bean.RequestScoped;
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

import persistence.Visitor;
import security.Role;
import security.Secured;
import services.interfaces.VisitorServiceLocal;

@Secured({ Role.ADMIN })
@RequestScoped
@Path("/visitors")
public class VisitorResource {

	@Inject
	private VisitorServiceLocal visitorServiceLocal;

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Visitor doFindById(@PathParam("id") Integer id) {
		return visitorServiceLocal.findVisitorById(id);
	}

	@GET
	@Path("activateVisitor/{id}/{active}")
	public void doActivateById(@PathParam("id") Integer id,
			@PathParam("active") Boolean active) {
		visitorServiceLocal.activateVisitor(id, active);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Visitor> doFindAllVisitors() {
		return visitorServiceLocal.findAllVisitors();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doAddVisitor(Visitor visitor) {
		visitorServiceLocal.addVisitor(visitor);
	}

	@PUT
	@Path("{id}")
	public void doSaveVisitor(Visitor visitor) {
		visitorServiceLocal.saveVisitor(visitor);
	}

	@DELETE
	@Path("{id}")
	public void doRemoveVisitor(@PathParam("id") Integer id) {
		visitorServiceLocal.removeVisitor(id);
	}

	@POST
	@Path("setPicture")
	public void doSetPicture(PictureEnvelope envelope) {
		visitorServiceLocal
				.setPicture(envelope.getId(), envelope.getFileName());
	}

}
