package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import persistence.PushNotification;

@Local
public interface PushNotificationServiceLocal {

	public void addNotification(PushNotification notification);

	public List<PushNotification> getAll();

	public List<PushNotification> getAllByUserId(Integer id);

	public void clearAllByUserId(Integer id);

	public void clearByNotiificationId(Integer id);

	public void markAsShowed(Integer id);

	public List<PushNotification> getAllByAgentId(Integer id);

	public List<PushNotification> getAllByVisitorId(Integer id);

}
