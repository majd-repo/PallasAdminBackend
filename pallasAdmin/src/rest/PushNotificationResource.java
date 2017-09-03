package rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import persistence.PushNotification;
import services.interfaces.PushNotificationServiceLocal;

@RequestScoped
@Path("notifications")
public class PushNotificationResource {

	@Inject
	private PushNotificationServiceLocal pushNotificationServiceLocal;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PushNotification> doGetAll() {
		return pushNotificationServiceLocal.getAll();
	}

	@Path("byUserId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PushNotification> doGetAllByUserId(@PathParam("id") Integer id) {
		return pushNotificationServiceLocal.getAllByUserId(id);
	}

	// only AGENT ROLE
	/*
	@Path("byAgentId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PushNotification> doGetAllByAgentId(@PathParam("id") Integer id) {
		return pushNotificationServiceLocal.getAllByAgentId(id);
	}
	 */
	// only Visitor ROLE
	/*
	@Path("byAgentId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PushNotification> doGetAllByVisitorId(@PathParam("id") Integer id) {
		return pushNotificationServiceLocal.getAllByVisitorId(id);
	}
	*/
	
	@Path("clearAllByUserId/{id}")
	@GET
	public void doClearAllById(@PathParam("id") Integer id) {
		pushNotificationServiceLocal.clearAllByUserId(id);
	}

	@Path("clearById/{id}")
	@GET
	public void doClearById(@PathParam("id") Integer id) {
		pushNotificationServiceLocal.clearByNotiificationId(id);
	}

	@Path("markAsShowed/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void doMarkAsShowed(@PathParam("id") Integer id) {
		pushNotificationServiceLocal.markAsShowed(id);
	}
}
