package services;

import httpObjects.DateTransferEnvelope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import persistence.CodeGenerator;
import persistence.DateRange;
import persistence.PushNotification;
import persistence.Staff;
import persistence.Task;
import persistence.Transfer;
import persistence.Vehicle;
import persistence.Visitor;
import results.TransferResult;
import results.VisitorResult;
import services.interfaces.TransferServiceLocal;
import util.HtmlEmailSender;
import util.MailContentBuilder;
import util.Tools;

@Stateless
@LocalBean
public class TransferService implements TransferServiceLocal {

	@Resource(name = "java:jboss/mail/postfix")
	private Session session;

	@PersistenceContext
	private EntityManager em;

	private String getLastCodTransfer() {
		Transfer transfer = null;
		try {
			transfer = em
					.createQuery("SELECT t FROM Transfer t ORDER BY t.id DESC",
							Transfer.class).setMaxResults(1).getSingleResult();
			return transfer.getCodeTransfer();
		} catch (Exception e) {
			return "A000";
		}

	}

	private PushNotification createNotification(Integer userId, String title,
			String message) {
		PushNotification pushNotification = new PushNotification();
		pushNotification.setUserId(userId);
		pushNotification.setTitle(title);
		pushNotification.setMessage(message);
		pushNotification.setDcr(new Date());
		pushNotification.setShowed(false);
		// Notify the observers about the new push notification
		// notificationEvent.fire(pushNotification);
		return pushNotification;
	}

	@Override
	public List<Transfer> findAllTransfers() {
		return em
				.createQuery(
						"SELECT t FROM Transfer t ORDER BY t.timeDepartureEstimated DESC",
						Transfer.class).getResultList();
	}

	@Override
	public void addTransfer(Transfer transfer) {
		CodeGenerator codeGenerator = new CodeGenerator();
		transfer.setCodeTransfer(codeGenerator.getNext(getLastCodTransfer()));
		transfer.setStatus("pending");

		if (transfer.getCpo() == null) {
			Staff defaultCpo = em.find(Staff.class, 130);
			if (defaultCpo != null)
				transfer.setCpo(defaultCpo);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default CPO not found");
		}

		if (transfer.getDriver() == null) {
			Staff defaultDriver = em.find(Staff.class, 130);
			if (defaultDriver != null)
				transfer.setDriver(defaultDriver);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default DRIVER not found");
		}

		if (transfer.getCoordinator1() == null) {
			Staff defaultCoordinator1 = em.find(Staff.class, 130);
			if (defaultCoordinator1 != null)
				transfer.setCoordinator1(defaultCoordinator1);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default COORDINATOR1 not found");
		}

		if (transfer.getCoordinator2() == null) {
			Staff defaultCoordinator2 = em.find(Staff.class, 130);
			if (defaultCoordinator2 != null)
				transfer.setCoordinator2(defaultCoordinator2);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default COORDINATOR2 not found");
		}
		if (transfer.getVehicle() == null) {
			Vehicle defaultVehicle = em.find(Vehicle.class, 6);
			if (defaultVehicle != null)
				transfer.setVehicle(defaultVehicle);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default Vehicule not found");
		}

		if (transfer.getTask() == null) {
			Task defaultTask = em.find(Task.class, 4);
			if (defaultTask != null)
				transfer.setTask(defaultTask);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default Task not found");
		}

		transfer.setDcr(new Date());
		// create new notification for the cpo, driver, and coordinators of the
		// transfer
		if (transfer.getTask() != null
				&& transfer.getTask().getActivated() != null
				&& transfer.getTask().getActivated() == true) {

			List<Visitor> visitors = transfer.getVisitors();

			if (visitors != null && visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {
						em.persist(createNotification(visitor.getId(),
								"Scheduled Transfer", "You have new task"));
					}
				}
			}

			if (transfer.getCpo() != null
					&& transfer.getCpo().getToken() != null) {
				em.persist(createNotification(
						transfer.getCpo().getId(),
						"Scheduled Transfer",
						"You are designated as CPO for the task "
								+ transfer.getCodeTransfer()));
			}

			if (transfer.getDriver() != null
					&& transfer.getDriver().getToken() != null) {

				em.persist(createNotification(transfer.getDriver().getId(),
						"Scheduled Transfer",
						"Transfer " + transfer.getCodeTransfer()
								+ " was added to your task schedule"));
			}
			if (transfer.getCoordinator1() != null
					&& transfer.getCoordinator1().getToken() != null) {

				em.persist(createNotification(transfer.getCoordinator1()
						.getId(), "Scheduled Transfer",
						"Transfer " + transfer.getCodeTransfer()
								+ " was added to your task schedule"));
			}

			if (transfer.getCoordinator2() != null
					&& transfer.getCoordinator2().getToken() != null) {

				em.persist(createNotification(transfer.getCoordinator2()
						.getId(), "Scheduled Transfer",
						"Transfer " + transfer.getCodeTransfer()
								+ " was added to your task schedule"));
			}
		}

		em.persist(transfer);

	}

	@Override
	public void updateTransfer(Transfer transfer) {

		if (transfer.getCpo() == null) {
			Staff defaultCpo = em.find(Staff.class, 130);
			if (defaultCpo != null)
				transfer.setCpo(defaultCpo);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default CPO not found");
		}

		if (transfer.getDriver() == null) {
			Staff defaultDriver = em.find(Staff.class, 130);
			if (defaultDriver != null)
				transfer.setDriver(defaultDriver);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default DRIVER not found");
		}

		if (transfer.getCoordinator1() == null) {
			Staff defaultCoordinator1 = em.find(Staff.class, 130);
			if (defaultCoordinator1 != null)
				transfer.setCoordinator1(defaultCoordinator1);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default COORDINATOR1 not found");
		}

		if (transfer.getCoordinator2() == null) {
			Staff defaultCoordinator2 = em.find(Staff.class, 130);
			if (defaultCoordinator2 != null)
				transfer.setCoordinator2(defaultCoordinator2);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default COORDINATOR2 not found");
		}
		if (transfer.getVehicle() == null) {
			Vehicle defaultVehicle = em.find(Vehicle.class, 6);
			if (defaultVehicle != null)
				transfer.setVehicle(defaultVehicle);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default Vehicule not found");
		}

		if (transfer.getTask() == null) {
			Task defaultTask = em.find(Task.class, 4);
			if (defaultTask != null)
				transfer.setTask(defaultTask);
			else
				Logger.getLogger(TransferService.class.getName()).log(
						Level.WARNING, "Default Task not found");
		}
		transfer.setDmj(new Date());

		if (transfer.getTask() != null
				&& transfer.getTask().getActivated() != null
				&& transfer.getTask().getActivated() == true) {
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors != null && visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {

						em.persist(createNotification(visitor.getId(),
								"Transfer Updated",
								"Your Transfer " + transfer.getCodeTransfer()
										+ " was updated"));
					}
				}
			}

			Transfer transferToBeUpdated = em.find(Transfer.class,
					transfer.getId());

			// if they are different
			if (transferToBeUpdated.getCpo().getId().intValue() != transfer
					.getCpo().getId().intValue()) {
				// if the new cpo is not the default
				if (transfer.getCpo().getId().intValue() != 130) {
					// notify him
					em.persist(createNotification(transfer.getCpo().getId(),
							"Transfer added",
							"Transfer " + transfer.getCodeTransfer()
									+ " was added to your task schedule"));
				}
				// if the old cpo is not the default
				if (transferToBeUpdated.getCpo().getId().intValue() != 130) {
					// notify him also
					em.persist(createNotification(transferToBeUpdated.getCpo()
							.getId(), "Transfer deleted", "Transfer "
							+ transfer.getCodeTransfer()
							+ " was deleted from your task schedule"));
				}

			}

			// if they are different
			if (transferToBeUpdated.getDriver().getId().intValue() != transfer
					.getDriver().getId().intValue()) {
				// if the new driver is not the default
				if (transfer.getDriver().getId() != 130) {
					// notify him
					em.persist(createNotification(transfer.getDriver().getId(),
							"Transfer added",
							"Transfer " + transfer.getCodeTransfer()
									+ " was added to your task schedule"));
				}
				// if the old driver is not the default
				if (transferToBeUpdated.getDriver().getId() != 130) {
					// notify him also
					em.persist(createNotification(transferToBeUpdated
							.getDriver().getId(), "Transfer deleted",
							"Transfer " + transfer.getCodeTransfer()
									+ " was deleted from your task schedule"));
				}

			}
			// if they are different
			if (transferToBeUpdated.getCoordinator1().getId().intValue() != transfer
					.getCoordinator1().getId().intValue()) {
				// if the new coordinator1 is not the default
				if (transfer.getCoordinator1().getId().intValue() != 130) {
					// notify him
					em.persist(createNotification(transfer.getCoordinator1()
							.getId(), "Transfer added",
							"Transfer " + transfer.getCodeTransfer()
									+ " was added to your task schedule"));
				}
				// if the old coordinator1 is not the default
				if (transferToBeUpdated.getCoordinator1().getId().intValue() != 130) {
					// notify him also
					em.persist(createNotification(transferToBeUpdated
							.getCoordinator1().getId(), "Transfer deleted",
							"Transfer " + transfer.getCodeTransfer()
									+ " was deleted from your task schedule"));
				}

			}

			// if they are different
			if (transferToBeUpdated.getCoordinator2().getId().intValue() != transfer
					.getCoordinator2().getId().intValue()) {
				// if the new coordinator2 is not the default
				if (transfer.getCoordinator2().getId() != 130) {
					// notify him
					em.persist(createNotification(transfer.getCoordinator2()
							.getId(), "Transfer added",
							"Transfer " + transfer.getCodeTransfer()
									+ " was added to your task schedule"));
				}
				// if the old coordinator2 is not the default
				if (transferToBeUpdated.getCoordinator2().getId() != 130) {
					// notify him also
					em.persist(createNotification(transferToBeUpdated
							.getCoordinator2().getId(), "Transfer deleted",
							"Transfer " + transfer.getCodeTransfer()
									+ " was deleted from your task schedule"));
				}

			}
		}
		em.merge(transfer);

	}

	@Override
	public void removeTransfer(Integer id) {
		Transfer transfer = em.find(Transfer.class, id);

		if (transfer != null && transfer.getTask() != null
				&& transfer.getTask().getActivated() != null
				&& transfer.getTask().getActivated() == true) {
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors != null && visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {

						em.persist(createNotification(visitor.getId(),
								"Task Removed", "A task was removed"));
					}
				}
			}
			if (transfer.getCpo() != null
					&& transfer.getCpo().getToken() != null) {

				em.persist(createNotification(transfer.getCpo().getId(),
						"Task Removed", "A task was removed"));
			}

			if (transfer.getDriver() != null
					&& transfer.getDriver().getToken() != null) {

				em.persist(createNotification(transfer.getDriver().getId(),
						"Task Removed", "A task was removed"));
			}
			if (transfer.getCoordinator1() != null
					&& transfer.getCoordinator1().getToken() != null) {

				em.persist(createNotification(transfer.getCoordinator1()
						.getId(), "Task Removed", "A task was removed"));
			}

			if (transfer.getCoordinator2() != null
					&& transfer.getCoordinator2().getToken() != null) {

				em.persist(createNotification(transfer.getCoordinator2()
						.getId(), "Task Removed", "A task was removed"));
			}
		}

		em.remove(em.merge(transfer));

	}

	@Override
	public JsonObject findTransferById(Integer id) {
		return Tools.ParseTransferToTransferResult(em.find(Transfer.class, id));

	}

	@Override
	public void addVisitorToTransfer(Integer transferId, Integer visitorId) {

		Transfer transfer = em.find(Transfer.class, transferId);
		// attach the new visitor before sending notification
		Visitor visitorToBeAttached = em.find(Visitor.class, visitorId);

		if (transfer.getTask() != null
				&& transfer.getTask().getActivated() == true) {

			// notify the new added visitor
			em.persist(createNotification(visitorToBeAttached.getId(),
					"New Task", "Transfer " + transfer.getCodeTransfer()
							+ " was added to your movement schedule"));
			// notify the old visitors
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors != null && visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {

						em.persist(createNotification(visitor.getId(),
								"Visitor attached",
								visitorToBeAttached.getFirstName() + " "
										+ visitorToBeAttached.getLastName()
										+ " was attached to transfer "
										+ transfer.getCodeTransfer()));
					}
				}
			}
			if (transfer.getCpo() != null
					&& transfer.getCpo().getToken() != null) {
				em.persist(createNotification(
						transfer.getCpo().getId(),
						"Visitor attached",
						visitorToBeAttached.getFirstName() + " "
								+ visitorToBeAttached.getLastName()
								+ " was attached to transfer "
								+ transfer.getCodeTransfer()));
			}

			if (transfer.getDriver() != null
					&& transfer.getDriver().getToken() != null) {
				em.persist(createNotification(
						transfer.getDriver().getId(),
						"Visitor attached",
						visitorToBeAttached.getFirstName() + " "
								+ visitorToBeAttached.getLastName()
								+ " was attached to transfer "
								+ transfer.getCodeTransfer()));
			}
			if (transfer.getCoordinator1() != null
					&& transfer.getCoordinator1().getToken() != null) {
				em.persist(createNotification(
						transfer.getCoordinator1().getId(),
						"Visitor attached",
						visitorToBeAttached.getFirstName() + " "
								+ visitorToBeAttached.getLastName()
								+ " was attached to transfer "
								+ transfer.getCodeTransfer()));
			}

			if (transfer.getCoordinator2() != null
					&& transfer.getCoordinator2().getToken() != null) {
				em.persist(createNotification(
						transfer.getCoordinator2().getId(),
						"Visitor attached",
						visitorToBeAttached.getFirstName() + " "
								+ visitorToBeAttached.getLastName()
								+ " was attached to transfer "
								+ transfer.getCodeTransfer()));
			}
		}
		transfer.getVisitors().add(visitorToBeAttached);
		transfer.setDmj(new Date());
		em.merge(transfer);
	}

	@Override
	public void removeVisitorFromTransfer(Integer transferId, Integer visitorId) {
		Transfer transfer = em.find(Transfer.class, transferId);

		Visitor visitorToBeRemoved = em.find(Visitor.class, visitorId);
		transfer.getVisitors().remove(visitorToBeRemoved);
		transfer.setDmj(new Date());
		em.merge(transfer);

		// notify all the visitors before removing the visitor concerned
		if (transfer.getTask() != null
				&& transfer.getTask().getActivated() == true) {
			// notify the detached visitor
			em.persist(createNotification(visitorToBeRemoved.getId(),
					"Transfer removed",
					"Transfer " + transfer.getCodeTransfer()
							+ " was discarded from your movement schedule"));
			// notify the other visitors
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors != null && visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {
						em.persist(createNotification(
								visitor.getId(),
								"Visitor detached",
								"Visitor " + visitorToBeRemoved.getFirstName()
										+ " "
										+ visitorToBeRemoved.getLastName()
										+ " is no more part of Transfer "
										+ transfer.getCodeTransfer()));

					}
				}
			}

			if (transfer.getCpo() != null
					&& transfer.getCpo().getToken() != null) {
				em.persist(createNotification(
						transfer.getCpo().getId(),
						"Visitor detached",
						visitorToBeRemoved.getFirstName() + " "
								+ visitorToBeRemoved.getLastName()
								+ " is no more part of transfer "
								+ transfer.getCodeTransfer()));
			}

			if (transfer.getDriver() != null
					&& transfer.getDriver().getToken() != null) {

				em.persist(createNotification(
						transfer.getDriver().getId(),
						"Visitor detached",
						visitorToBeRemoved.getFirstName() + " "
								+ visitorToBeRemoved.getLastName()
								+ " is no more part of transfer "
								+ transfer.getCodeTransfer()));
			}
			if (transfer.getCoordinator1() != null
					&& transfer.getCoordinator1().getToken() != null) {
				em.persist(createNotification(
						transfer.getCoordinator1().getId(),
						"Visitor detached",
						visitorToBeRemoved.getFirstName() + " "
								+ visitorToBeRemoved.getLastName()
								+ " is no more part of transfer "
								+ transfer.getCodeTransfer()));
			}

			if (transfer.getCoordinator2() != null
					&& transfer.getCoordinator2().getToken() != null) {
				em.persist(createNotification(
						transfer.getCoordinator2().getId(),
						"Visitor detached",
						visitorToBeRemoved.getFirstName() + " "
								+ visitorToBeRemoved.getLastName()
								+ " is no more part of transfer "
								+ transfer.getCodeTransfer()));
			}
		}
		// remove the visitor after notifying him

	}

	@Override
	public List<Transfer> findAllTransferOfToday(DateRange dateRange) {
		List<Transfer> transfers;
		try {
			transfers = em
					.createQuery(
							"SELECT t from Transfer t where t.timeDepartureEstimated BETWEEN :min AND :max",
							Transfer.class)
					.setParameter("min", dateRange.getDateMin())
					.setParameter("max", dateRange.getDateMax())
					.getResultList();
		} catch (NoResultException nre) {
			transfers = null;
		} catch (Exception e) {
			transfers = null;
		}
		return transfers;

	}

	@Override
	public List<Transfer> findAllTransferOfWeekAhead(DateRange dateRange) {
		List<Transfer> transferFiltrated = new ArrayList<>();
		List<Transfer> transfers;
		try {
			transfers = em
					.createQuery(
							"SELECT t from Transfer t where t.timeDepartureEstimated >= :min",
							Transfer.class)
					.setParameter("min", dateRange.getDateMin())
					.getResultList();
			for (Transfer transfer : transfers) {
				if (transfer.getTimeDepartureEstimated().compareTo(
						dateRange.getDateMax()) < 0) {
					transferFiltrated.add(transfer);
				}

			}
		} catch (NoResultException nre) {
			transferFiltrated = null;
		} catch (Exception e) {
			transferFiltrated = null;
		}

		return transferFiltrated;
	}

	@Override
	public List<Transfer> findAllTransferByDateMinMax(DateRange dateRange) {
		List<Transfer> transfers;
		try {
			transfers = em
					.createQuery(
							"SELECT t from Transfer t where t.timeDepartureEstimated BETWEEN :min AND :max",
							Transfer.class)
					.setParameter("min", dateRange.getDateMin())
					.setParameter("max", dateRange.getDateMax())
					.getResultList();
		} catch (NoResultException nre) {
			transfers = null;
		} catch (Exception e) {
			transfers = null;
		}
		return transfers;
	}

	@Override
	public List<Transfer> findAllTransferOfTomorrow(DateRange dateRange) {
		List<Transfer> transfers;
		try {
			transfers = em
					.createQuery(
							"SELECT t from Transfer t where t.timeDepartureEstimated BETWEEN :min AND :max",
							Transfer.class)
					.setParameter("min", dateRange.getDateMin())
					.setParameter("max", dateRange.getDateMax())
					.getResultList();
		} catch (NoResultException nre) {
			transfers = null;
		} catch (Exception e) {
			transfers = null;
		}
		return transfers;
	}

	@Override
	public Transfer setReadyToGo(DateTransferEnvelope envelope) {
		Transfer transfer = null;
		try {
			transfer = em.find(Transfer.class, envelope.getTransferId());
			transfer.setStatus("readytogo");
			transfer.setDmj(new Date());
			em.merge(transfer);
		} catch (NoResultException nre) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
			return null;
		} catch (Exception e) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
			return null;
		}

		if (transfer.getTask() != null
				&& transfer.getTask().getActivated() == true) {
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors != null && visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {
						em.persist(createNotification(visitor.getId(),
								"Ready To Go",
								"Transfer " + transfer.getCodeTransfer()
										+ " is Ready To Go"));
					}
				}
			}
		}
		// notify the coordinator 1 by email
		if (transfer.getCoordinator1() != null
				&& transfer.getCoordinator1().getId().intValue() != 130) {
			try {

				HtmlEmailSender.sendHtmlEmail(session, "Transfert Ready To Go",
						MailContentBuilder.buildAdviseMail(
								transfer.getCoordinator1(),
								"Transfert Ready To Go", "Transfert "
										+ transfer.getCodeTransfer()
										+ " is Ready To Go."), transfer
								.getCoordinator1().getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// notify the coordinator 2 by email
		if (transfer.getCoordinator2() != null
				&& transfer.getCoordinator2().getId().intValue() != 130) {
			try {
				HtmlEmailSender.sendHtmlEmail(session, "Transfert Ready To Go",
						MailContentBuilder.buildAdviseMail(
								transfer.getCoordinator2(),
								"Transfert Ready To Go", "Transfert "
										+ transfer.getCodeTransfer()
										+ " is Ready To Go."), transfer
								.getCoordinator2().getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return transfer;
	}

	@Override
	public Transfer setDepartureTime(DateTransferEnvelope envelope) {
		Transfer transfer = null;

		try {
			transfer = em.find(Transfer.class, envelope.getTransferId());
			transfer.setTimeDeparture(envelope.getDateTime());
			transfer.setStatus("ongoing");
			transfer.setDmj(new Date());
			em.merge(transfer);
		} catch (NoResultException nre) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
		} catch (Exception e) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
		}

		if (transfer.getTask().getActivated() == true) {
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {
						em.persist(createNotification(
								visitor.getId(),
								"Departure",
								"Team on Transfert "
										+ transfer.getCodeTransfer()
										+ " confirms departure."));
					}
				}
			}
		}

		// notify the coordinator 1 by email
		if (transfer.getCoordinator1().getId().intValue() != 130) {
			try {

				HtmlEmailSender.sendHtmlEmail(session, "Transfert Departure",
						MailContentBuilder.buildAdviseMail(
								transfer.getCoordinator1(),
								"Transfert Departure", "Team on Transfert "
										+ transfer.getCodeTransfer()
										+ " confirms departure."), transfer
								.getCoordinator1().getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// notify the coordinator 2 by email
		if (transfer.getCoordinator2().getId().intValue() != 130) {
			try {
				HtmlEmailSender.sendHtmlEmail(session, "Transfert Departure",
						MailContentBuilder.buildAdviseMail(
								transfer.getCoordinator2(),
								"Transfer Departure", "Team on Transfert "
										+ transfer.getCodeTransfer()
										+ " confirms departure."), transfer
								.getCoordinator2().getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return transfer;
	}

	@Override
	public void setArrivalTime(DateTransferEnvelope envelope) {
		Transfer transfer = null;
		try {
			transfer = em.find(Transfer.class, envelope.getTransferId());
			transfer.setTimeArrival(envelope.getDateTime());
			transfer.setStatus("closed");
			transfer.setDmj(new Date());
			em.merge(transfer);
		} catch (NoResultException nre) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
		} catch (Exception e) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
		}

		if (transfer.getTask() != null
				&& transfer.getTask().getActivated() == true) {
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors != null && visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {
						em.persist(createNotification(
								visitor.getId(),
								"Transfert Arrival",
								"Team on Transfert "
										+ transfer.getCodeTransfer()
										+ " confirms safe arrival at destination"));
					}
				}
			}

		}

		// notify the coordinator 1 by email
		if (transfer.getCoordinator1() != null
				&& transfer.getCoordinator1().getId().intValue() != 130) {
			try {

				HtmlEmailSender
						.sendHtmlEmail(
								session,
								"Transfert Arrival",
								MailContentBuilder.buildAdviseMail(
										transfer.getCoordinator1(),
										"Transfer Arrival",
										"Team on Transfert "
												+ transfer.getCodeTransfer()
												+ " confirms safe arrival at destination"),
								transfer.getCoordinator1().getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// notify the coordinator 2 by email
		if (transfer.getCoordinator2() != null
				&& transfer.getCoordinator2().getId().intValue() != 130) {
			try {
				HtmlEmailSender
						.sendHtmlEmail(
								session,
								"Transfer Arrival",
								MailContentBuilder.buildAdviseMail(
										transfer.getCoordinator2(),
										"Transfer Arrival",
										"Team on Transfert "
												+ transfer.getCodeTransfer()
												+ " confirms safe arrival at destination"),
								transfer.getCoordinator2().getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Transfer> findAllTransferByAgentToken(String token) {
		return em
				.createQuery(
						"SELECT t FROM Transfer t where t.coordinator1.token =:param or t.coordinator2.token =:param or t.cpo.token =:param or t.driver.token =:param",
						Transfer.class).setParameter("param", token)
				.getResultList();
	}

	@Override
	public String getLastCodeTransfer() {

		return em
				.createQuery("SELECT t FROM Transfer t ORDER BY t.id DESC",
						Transfer.class).setMaxResults(1).getSingleResult()
				.getCodeTransfer();
	}

	@SuppressWarnings("null")
	@Override
	public List<JsonObject> findOldTransfersByAgentToken(String token) {

		Staff agentByToken = em
				.createQuery("SELECT s FROM Staff s WHERE s.token=:param",
						Staff.class).setParameter("param", token)
				.getSingleResult();
		List<JsonObject> transferResultsByAgentToken = null;// = new
		// ArrayList<TransferResult>();
		List<Transfer> transfers = em.createQuery(
				"SELECT t From Transfer t WHERE t.status = 'closed'",
				Transfer.class).getResultList();
		if (transfers != null && transfers.size() > 0) {
			for (Transfer transfer : transfers) {
				if ((transfer.getDriver() != null && transfer.getDriver()
						.getId().intValue() == agentByToken.getId().intValue())
						|| (transfer.getCpo() != null && transfer.getCpo()
								.getId().intValue() == agentByToken.getId()
								.intValue())
						|| (((transfer.getCoordinator1() != null && transfer
								.getCoordinator1().getId().intValue() == agentByToken
								.getId().intValue()) || (transfer
								.getCoordinator2() != null && transfer
								.getCoordinator2().getId().intValue() == agentByToken
								.getId().intValue())))) {
					// TransferResult transferResult = ;
					transferResultsByAgentToken.add(Tools
							.ParseTransferToTransferResult(transfer));
				}
			}
		}
		return transferResultsByAgentToken;
	}

	@SuppressWarnings("null")
	@Override
	public List<JsonObject> findNewTransfersByAgentToken(String token) {
		Staff agentByToken = em
				.createQuery("SELECT s FROM Staff s WHERE s.token=:param",
						Staff.class).setParameter("param", token)
				.getSingleResult();
		List<JsonObject> transferResultsByAgentToken = null;
		List<Transfer> transfers = em
				.createQuery(
						"SELECT t From Transfer t WHERE t.status = 'pending' OR t.status = 'ongoing' OR t.status = 'readytogo'",
						Transfer.class).getResultList();
		if (transfers != null && transfers.size() > 0) {
			for (Transfer transfer : transfers) {
				if ((transfer.getDriver() != null && transfer.getDriver()
						.getId().intValue() == agentByToken.getId().intValue())
						|| (transfer.getCpo() != null && transfer.getCpo()
								.getId().intValue() == agentByToken.getId()
								.intValue())
						|| (((transfer.getCoordinator1() != null && transfer
								.getCoordinator1().getId().intValue() == agentByToken
								.getId().intValue()) || (transfer
								.getCoordinator2() != null && transfer
								.getCoordinator2().getId().intValue() == agentByToken
								.getId().intValue())))) {

					transferResultsByAgentToken.add(Tools
							.ParseTransferToTransferResult(transfer));
				}
			}
		}
		return transferResultsByAgentToken;
	}

	@Override
	public JsonObject findLastActiveTransferByVisitorToken(String token) {
		List<JsonObject> transferByVisitorToken = new ArrayList<JsonObject>();
		String queryStringTransfers = "";
		// queryStringTransfers =
		// "SELECT new results.TransferResult(t.id, t.dateTransfer, t.fromPoint, t.destinationPoint, t.flightCode, t.driver.firstName, t.driver.lastName,t.driver.phoneNumber, t.driver.email, t.driver.picture, t.cpo.firstName, t.cpo.lastName, t.cpo.phoneNumber, t.cpo.email, t.cpo.picture, t.coordinator1.firstName, t.coordinator1.lastName, t.coordinator1.phoneNumber, t.coordinator1.email, t.coordinator1.picture, t.coordinator2.firstName, t.coordinator2.lastName, t.coordinator2.phoneNumber, t.coordinator2.email, t.coordinator2.picture, t.timeDepartureEstimated, t.timeArrivalEstimated, t.timeDeparture, t.timeArrival, t.status, t.vehicle.make, t.vehicle.model, t.vehicle.plate, t.vehicle.picture, t.codeTransfer, t.category) FROM Transfer t WHERE t.status = 'pending' OR t.status = 'ongoing' OR t.status= 'readytogo' ORDER BY t.timeDepartureEstimated DESC";
		queryStringTransfers = "SELECT t FROM Transfer t WHERE t.status = 'pending' OR t.status = 'ongoing' OR t.status= 'readytogo' ORDER BY t.timeDepartureEstimated DESC";
		String queryStringVisitors = "";
		TypedQuery<Transfer> queryTransfers = em.createQuery(
				queryStringTransfers, Transfer.class);
		List<Transfer> transfers = queryTransfers.getResultList();
		TypedQuery<Visitor> queryVisitors = null;
		List<Visitor> visitors = new ArrayList<Visitor>();
		try {
			if (transfers != null & transfers.size() > 0) {
				for (Transfer transfer : transfers) {
					queryStringVisitors = "SELECT v FROM Transfer t INNER JOIN t.visitors v WHERE t.id = "
							+ transfer.getId();
					queryVisitors = em.createQuery(queryStringVisitors,
							Visitor.class);
					visitors = queryVisitors.getResultList();
					if (visitors != null && visitors.size() > 0) {
						for (Visitor visitor : visitors) {
							if (visitor.getToken() != null
									&& visitor.getToken() != "") {
								if (visitor.getToken().equals(token)) {
									transferByVisitorToken.add(Tools.ParseTransferToTransferResult(transfer));
								}
							}
						}
					}

				}
			}
			return transferByVisitorToken
					.get(transferByVisitorToken.size() - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	@SuppressWarnings("null")
	@Override
	public JsonObject findLastActiveTransferByAgentToken(String token) {

		Staff agentByToken = em
				.createQuery("SELECT s FROM Staff s WHERE s.token=:param",
						Staff.class).setParameter("param", token)
				.getSingleResult();
		JsonObject jsonObject = null;
		List<JsonObject> transferResultsByAgentToken = null;
		List<Transfer> transfers = em
				.createQuery(
						"SELECT t From Transfer t WHERE t.status = 'ongoing' OR t.status = 'pending' OR t.status = 'readytogo' ORDER BY t.timeDepartureEstimated DESC",
						Transfer.class).getResultList();
		if (transfers != null && transfers.size() > 0) {
			for (Transfer transfer : transfers) {

				if ((transfer.getDriver() != null && transfer.getDriver()
						.getId().intValue() == agentByToken.getId().intValue())
						|| (transfer.getCpo() != null && transfer.getCpo()
								.getId().intValue() == agentByToken.getId()
								.intValue())
						|| (((transfer.getCoordinator1() != null && transfer
								.getCoordinator1().getId().intValue() == agentByToken
								.getId().intValue()) || (transfer
								.getCoordinator2() != null && transfer
								.getCoordinator2().getId().intValue() == agentByToken
								.getId().intValue())))) {

					transferResultsByAgentToken.add(Tools
							.ParseTransferToTransferResult(transfer));
				}
			}
		}
		if (transferResultsByAgentToken.size() > 0) {
			return transferResultsByAgentToken.get(transferResultsByAgentToken
					.size() - 1);
		} else {
			return jsonObject;
		}

	}

	@Override
	public List<VisitorResult> findVisitorsOfLastTransferByAgentToken(
			String token) {
		List<VisitorResult> visitorsByAgentToken = new ArrayList<>();
		Staff agentByToken = null;
		try {
			agentByToken = em
					.createQuery("SELECT s FROM Staff s WHERE s.token=:param",
							Staff.class).setParameter("param", token)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}
		List<Transfer> transferResultsByAgentToken = new ArrayList<Transfer>();
		List<Transfer> transfers = null;
		try {
			transfers = em
					.createQuery(
							"SELECT t From Transfer t WHERE t.status = 'ongoing' OR t.status = 'pending' OR t.status= 'readytogo' ORDER BY t.timeDepartureEstimated DESC",
							Transfer.class).getResultList();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}

		for (Transfer transfer : transfers) {
			if ((transfer.getDriver() != null && transfer.getDriver().getId()
					.intValue() == agentByToken.getId().intValue())
					|| (transfer.getCpo() != null && transfer.getCpo().getId()
							.intValue() == agentByToken.getId().intValue())
					|| (((transfer.getCoordinator1() != null && transfer
							.getCoordinator1().getId().intValue() == agentByToken
							.getId().intValue()) || (transfer.getCoordinator2() != null && transfer
							.getCoordinator2().getId().intValue() == agentByToken
							.getId().intValue())))) {
				transferResultsByAgentToken.add(transfer);
			}
		}
		if (transferResultsByAgentToken.size() > 0) {
			for (Visitor visitor : transferResultsByAgentToken.get(
					transferResultsByAgentToken.size() - 1).getVisitors()) {
				VisitorResult visitorResult = ParseVisitorToVisitorResult(visitor);
				visitorsByAgentToken.add(visitorResult);
			}
		}

		return visitorsByAgentToken;
	}

	private VisitorResult ParseVisitorToVisitorResult(Visitor visitor) {

		VisitorResult visitorResult = new VisitorResult(
				visitor.getPhoneNumber(), visitor.getEmail(),
				visitor.getFirstName(), visitor.getLastName(),
				visitor.getPicture());
		return visitorResult;
	}

	@Override
	public void discardTransfer(Integer transferId) {
		Transfer transfer = null;
		try {
			transfer = em.find(Transfer.class, transferId);
			transfer.setStatus("discarded");
			transfer.setDmj(new Date());
		} catch (NoResultException nre) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
		} catch (Exception e) {
			Logger.getLogger(TransferService.class.getName()).log(
					Level.WARNING, "Transfer not found");
		}

		if (transfer.getTask() != null
				&& transfer.getTask().getActivated() == true) {
			List<Visitor> visitors = transfer.getVisitors();
			if (visitors.size() > 0) {
				for (Visitor visitor : visitors) {
					if (visitor.getToken() != null) {
						em.persist(createNotification(visitor.getId(),
								"Task discarded",
								"The task " + transfer.getCodeTransfer()
										+ " was discarded"));
					}
				}
			}

			if (transfer.getCpo() != null
					&& transfer.getCpo().getToken() != null) {
				em.persist(createNotification(transfer.getCpo().getId(),
						"Task discarded",
						"The task " + transfer.getCodeTransfer()
								+ " was discarded"));
			}

			if (transfer.getDriver() != null
					&& transfer.getDriver().getToken() != null) {
				em.persist(createNotification(transfer.getDriver().getId(),
						"Task discarded",
						"The task " + transfer.getCodeTransfer()
								+ " was discarded"));
			}
			if (transfer.getCoordinator1() != null
					&& transfer.getCoordinator1().getToken() != null) {
				em.persist(createNotification(transfer.getCoordinator1()
						.getId(), "Task discarded",
						"The task " + transfer.getCodeTransfer()
								+ " was discarded"));
			}

			if (transfer.getCoordinator2() != null
					&& transfer.getCoordinator2().getToken() != null) {
				em.persist(createNotification(transfer.getCoordinator2()
						.getId(), "Task discarded",
						"The task " + transfer.getCodeTransfer()
								+ " was discarded"));
			}
		}

		em.merge(transfer);

	}

	@Override
	public List<VisitorResult> findVisitorsByTransferId(Integer id) {
		List<VisitorResult> visitorResults = new ArrayList<VisitorResult>();
		Transfer transfer = null;
		try {
			transfer = em.find(Transfer.class, id);
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			return null;
		}

		if (transfer != null && transfer.getVisitors().size() > 0) {
			for (Visitor visitor : transfer.getVisitors()) {
				visitorResults.add(ParseVisitorToVisitorResult(visitor));
			}
			return visitorResults;
		}
		return visitorResults;
	}

	@Override
	public List<TransferResult> findTransfersByVisitorToken(String token,
			String mode) {

		try {
			List<TransferResult> transferByVisitorToken = new ArrayList<TransferResult>();
			String queryStringTransfers = null;
			if (mode.equals("new")) {
				queryStringTransfers = "SELECT new results.TransferResult(t.id, t.dateTransfer, t.fromPoint, t.destinationPoint, t.flightCode, t.driver.firstName, t.driver.lastName,t.driver.phoneNumber, t.driver.email, t.driver.picture, t.cpo.firstName, t.cpo.lastName, t.cpo.phoneNumber, t.cpo.email, t.cpo.picture, t.coordinator1.firstName, t.coordinator1.lastName, t.coordinator1.phoneNumber, t.coordinator1.email, t.coordinator1.picture, t.coordinator2.firstName, t.coordinator2.lastName, t.coordinator2.phoneNumber, t.coordinator2.email, t.coordinator2.picture, t.timeDepartureEstimated, t.timeArrivalEstimated, t.timeDeparture, t.timeArrival, t.status, t.vehicle.make, t.vehicle.model, t.vehicle.plate, t.vehicle.picture, t.codeTransfer, t.category) FROM Transfer t WHERE t.status = 'ongoing' OR t.status= 'pending' OR t.status= 'readytogo'";
			} else {
				queryStringTransfers = "SELECT new results.TransferResult(t.id, t.dateTransfer, t.fromPoint, t.destinationPoint, t.flightCode, t.driver.firstName, t.driver.lastName,t.driver.phoneNumber, t.driver.email, t.driver.picture, t.cpo.firstName, t.cpo.lastName, t.cpo.phoneNumber, t.cpo.email, t.cpo.picture, t.coordinator1.firstName, t.coordinator1.lastName, t.coordinator1.phoneNumber, t.coordinator1.email, t.coordinator1.picture, t.coordinator2.firstName, t.coordinator2.lastName, t.coordinator2.phoneNumber, t.coordinator2.email, t.coordinator2.picture, t.timeDepartureEstimated, t.timeArrivalEstimated, t.timeDeparture, t.timeArrival, t.status, t.vehicle.make, t.vehicle.model, t.vehicle.plate, t.vehicle.picture, t.codeTransfer, t.category) FROM Transfer t WHERE t.status = 'closed'";
			}

			TypedQuery<TransferResult> queryTransfers = em.createQuery(
					queryStringTransfers, TransferResult.class);
			List<TransferResult> transfers = queryTransfers.getResultList();
			TypedQuery<Visitor> queryVisitors = null;
			List<Visitor> visitors = new ArrayList<Visitor>();
			for (TransferResult transfer : transfers) {

				queryVisitors = em.createQuery(
						"SELECT v FROM Transfer t INNER JOIN t.visitors v WHERE t.id = "
								+ transfer.getId(), Visitor.class);
				visitors = queryVisitors.getResultList();
				if (visitors != null && visitors.size() > 0) {
					for (Visitor visitor : visitors) {
						if (visitor.getToken() != null
								&& visitor.getToken() != "") {
							if (visitor.getToken().equals(token)) {
								transferByVisitorToken.add(transfer);
							}
						}
					}
				}

			}
			return transferByVisitorToken;
		} catch (Exception e) {
			return null;
		}
	}

}
