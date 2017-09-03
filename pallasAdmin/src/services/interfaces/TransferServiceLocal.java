package services.interfaces;

import httpObjects.DateTransferEnvelope;

import java.util.List;

import javax.ejb.Local;
import javax.json.JsonObject;

import persistence.DateRange;
import persistence.Transfer;
import results.TransferResult;
import results.VisitorResult;

@Local
public interface TransferServiceLocal {

	public JsonObject findTransferById(Integer id);

	public List<Transfer> findAllTransfers();

	public void addTransfer(Transfer transfer);

	public void updateTransfer(Transfer transfer);

	public void removeTransfer(Integer id);

	public void addVisitorToTransfer(Integer transferId, Integer visitorId);

	public void removeVisitorFromTransfer(Integer transferId, Integer visitorId);

	public List<Transfer> findAllTransferOfToday(DateRange dateRange);

	public List<Transfer> findAllTransferOfTomorrow(DateRange dateRange);

	public List<Transfer> findAllTransferOfWeekAhead(DateRange dateRange);

	public List<Transfer> findAllTransferByDateMinMax(DateRange dateRange);

	public List<Transfer> findAllTransferByAgentToken(String token);

	public Transfer setDepartureTime(DateTransferEnvelope envelope);

	public void setArrivalTime(DateTransferEnvelope envelope);

	public Transfer setReadyToGo(DateTransferEnvelope envelope);

	public String getLastCodeTransfer();

	// public TransferResult findLastActiveTransferByToken(String token);

	public JsonObject findLastActiveTransferByVisitorToken(String token);

	public JsonObject findLastActiveTransferByAgentToken(String token);

	// public List<TransferResult> findNewTransfersByToken(String token);

	// public List<TransferResult> findOldTransfersByToken(String token);

	public List<TransferResult> findTransfersByVisitorToken(String token,
			String mode);

	// public List<TransferResult> findOldTransfersByVisitorToken(String token);
	// public List<TransferResult> findNewTransfersByVisitorToken(String token);

	public List<JsonObject> findOldTransfersByAgentToken(String token);

	public List<JsonObject> findNewTransfersByAgentToken(String token);

	public List<VisitorResult> findVisitorsOfLastTransferByAgentToken(
			String token);

	public void discardTransfer(Integer transferId);

	public List<VisitorResult> findVisitorsByTransferId(Integer id);

}
