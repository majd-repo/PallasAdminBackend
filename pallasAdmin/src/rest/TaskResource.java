package rest;

import httpObjects.TaskCodeEnvelope;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import persistence.Task;
import security.Role;
import security.Secured;
import services.interfaces.TaskServiceLocal;

//@Secured({ Role.ADMIN })
@RequestScoped
@Path("task")
public class TaskResource {

	@Inject
	private TaskServiceLocal taskServiceLocal;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> doFindAll() {
		return taskServiceLocal.findAll();
	}

	@GET
	@Path("active")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> doFindActive() {
		return taskServiceLocal.findActiveTasks();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doAddTask(TaskCodeEnvelope envelope) {
		taskServiceLocal.addTask(envelope.getCompanyid(), envelope.getNbDays(),
				envelope.getNbMonths());
	}

	@GET
	@Path("activate/{taskId}/{activated}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void doActivate(@PathParam("taskId") Integer taskId,
			@PathParam("activated") Boolean activated) {
		taskServiceLocal.activate(taskId, activated);
	}

	@Path("countInactivated")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Integer doCountActivatedTaskes() {
		return taskServiceLocal.countInactivatedTaskes();
	}

}
