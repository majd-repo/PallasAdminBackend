package rest;

import httpObjects.DateTransferEnvelope;
import httpObjects.TokenEnvelope;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import persistence.DateRange;
import persistence.Transfer;
import persistence.User;
import results.TransferResult;
import results.VisitorResult;
import security.AuthenticatedUser;
import security.Role;
import security.Secured;
import services.interfaces.TransferServiceLocal;

@RequestScoped
@Path("/transfers")
public class TransferResource {

	@Inject
	private TransferServiceLocal transferServiceLocal;

	// inject the authenticated user for some uses ..
	@Inject
	@AuthenticatedUser
	User authenticatedUser;

	@Secured({ Role.VISITOR, Role.AGENT })
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject doFindTransferById(@PathParam("id") Integer id) {
		return transferServiceLocal.findTransferById(id);

	}

	@Secured({ Role.AGENT })
	@Path("visitorsOfTransferId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VisitorResult> doFindVisitorsOfTransferId(
			@PathParam("id") Integer id) {
		return transferServiceLocal.findVisitorsByTransferId(id);

	}

	@Secured({ Role.AGENT })
	@Path("visitorsOfLastTransferByAgentToken")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<VisitorResult> doFindVisitorsOfLastTransferByAgentToken(
			TokenEnvelope tokenEnvelope) {
		return transferServiceLocal
				.findVisitorsOfLastTransferByAgentToken(tokenEnvelope
						.getToken());
	}

	@Secured({ Role.AGENT })
	@Path("setDepartureTime")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Transfer doSetDepartureTime(DateTransferEnvelope envelope) {
		return transferServiceLocal.setDepartureTime(envelope);
	}

	@Secured({ Role.AGENT })
	@Path("setArrivalTime")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doSetArrivalTime(DateTransferEnvelope envelope) {
		transferServiceLocal.setArrivalTime(envelope);
	}

	@Secured({ Role.AGENT })
	@Path("setReadyToGo")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Transfer doSetReadyToGo(DateTransferEnvelope envelope) {
		return transferServiceLocal.setReadyToGo(envelope);
	}

	@Secured({ Role.AGENT })
	@Path("newTransfersByAgentToken")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<JsonObject> doFindNewTransfersByAgentToken(
			TokenEnvelope tokenEnvelope) {
		return transferServiceLocal.findNewTransfersByAgentToken(tokenEnvelope
				.getToken());
	}

	@Secured({ Role.AGENT })
	@Path("oldTransfersByAgentToken")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<JsonObject> doFindOldTransfersByAgentToken(
			TokenEnvelope tokenEnvelope) {
		return transferServiceLocal.findOldTransfersByAgentToken(tokenEnvelope
				.getToken());
	}

	@Secured({ Role.VISITOR })
	@Path("newTransfersByVisitorToken")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<TransferResult> doFindNewTransfersByVisitorToken(
			TokenEnvelope tokenEnvelope) {
		return transferServiceLocal.findTransfersByVisitorToken(
				tokenEnvelope.getToken(), "new");
	}

	@Secured({ Role.VISITOR })
	@Path("oldTransfersByVisitorToken")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<TransferResult> doFindOldTransfersByVisitorToken(
			TokenEnvelope tokenEnvelope) {
		return transferServiceLocal.findTransfersByVisitorToken(
				tokenEnvelope.getToken(), "old");
	}

	@Secured({ Role.VISITOR })
	@Path("findLastActiveTransferByVisitorToken")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject doFindLastActiveTransferByToken(
			TokenEnvelope tokenEnvelope) {
		return transferServiceLocal
				.findLastActiveTransferByVisitorToken(tokenEnvelope.getToken());
	}

	@Secured({ Role.AGENT })
	@Path("findLastActiveTransferByAgentToken")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject doFindLastActiveTransferByAgentToken(
			TokenEnvelope tokenEnvelope) {
		return transferServiceLocal
				.findLastActiveTransferByAgentToken(tokenEnvelope.getToken());
	}

	@Secured({ Role.ADMIN })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transfer> doFindAllTransfers() {
		return transferServiceLocal.findAllTransfers();
	}

	@Secured({ Role.ADMIN })
	@Path("today")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transfer> doFindAllTransfersOfToday(DateRange dateRange) {
		return transferServiceLocal.findAllTransferOfToday(dateRange);
	}

	@Secured({ Role.ADMIN })
	@Path("tomorrow")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transfer> doFindAllTransfersOfTomorrow(DateRange dateRange) {
		return transferServiceLocal.findAllTransferOfTomorrow(dateRange);
	}

	@Secured({ Role.ADMIN })
	@Path("weekahead")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transfer> doFindAllTransfersOfWeekAhead(DateRange dateRange) {
		return transferServiceLocal.findAllTransferOfWeekAhead(dateRange);
	}

	@Secured({ Role.ADMIN })
	@Path("searchByDateMinMax")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transfer> doFindAllTransfersByDateMinMax(DateRange dateRange) {
		return transferServiceLocal.findAllTransferByDateMinMax(dateRange);
	}

	@Secured({ Role.ADMIN })
	@GET
	@Path("discardTransfer/{transferId}")
	public void doAddVisitorToTransfer(
			@PathParam("transferId") Integer transferId) {
		transferServiceLocal.discardTransfer(transferId);
	}

	@Secured({ Role.ADMIN })
	@GET
	@Path("couple/{transferId}/{visitorId}")
	public void doAddVisitorToTransfer(
			@PathParam("transferId") Integer transferId,
			@PathParam("visitorId") Integer visitorId) {
		transferServiceLocal.addVisitorToTransfer(transferId, visitorId);
	}

	@Secured({ Role.ADMIN })
	@GET
	@Path("uncouple/{transferId}/{visitorId}")
	public void doRemoveVisitorFromTransfer(
			@PathParam("transferId") Integer transferId,
			@PathParam("visitorId") Integer visitorId) {
		transferServiceLocal.removeVisitorFromTransfer(transferId, visitorId);
	}

	@Secured({ Role.ADMIN })
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doAddTransfert(Transfer transfer) {
		transferServiceLocal.addTransfer(transfer);
	}

	@Secured({ Role.ADMIN })
	@PUT
	@Path("{id}")
	public void doUpdateTransfert(Transfer transfer) {
		transferServiceLocal.updateTransfer(transfer);
	}

	@Secured({ Role.ADMIN })
	@DELETE
	@Path("{id}")
	public void doRemoveTransfert(@PathParam("id") Integer id) {
		transferServiceLocal.removeTransfer(id);
	}
}