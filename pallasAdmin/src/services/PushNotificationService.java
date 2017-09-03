package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistence.PushNotification;
import services.interfaces.PushNotificationServiceLocal;

@Stateless
@LocalBean
public class PushNotificationService implements PushNotificationServiceLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<PushNotification> getAll() {

		return em.createQuery("SELECT p FROM PushNotification p",
				PushNotification.class).getResultList();
	}

	@Override
	public List<PushNotification> getAllByUserId(Integer id) {
		List<PushNotification> notificationsTobeReturn = em
				.createQuery(
						"SELECT p FROM PushNotification p WHERE p.userId = :id AND p.showed = false",
						PushNotification.class).setParameter("id", id)
				.getResultList();

		return notificationsTobeReturn;
	}

	@Override
	public void clearByNotiificationId(Integer id) {
		em.remove(em.find(PushNotification.class, id));
	}

	@Override
	public void addNotification(PushNotification notification) {
		em.persist(notification);

	}

	@Override
	public void clearAllByUserId(Integer id) {
		List<PushNotification> notificationsTobeDeleted = em
				.createQuery(
						"SELECT p FROM PushNotification p WHERE p.userId = :id",
						PushNotification.class).setParameter("id", id)
				.getResultList();
		for (PushNotification pushNotification : notificationsTobeDeleted) {
			em.remove(em.find(PushNotification.class, pushNotification.getId()));
		}

	}

	@Override
	public void markAsShowed(Integer id) {
		PushNotification notification = em.find(PushNotification.class, id);
		notification.setShowed(true);
		em.merge(notification);

	}

	@Override
	public List<PushNotification> getAllByAgentId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PushNotification> getAllByVisitorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
