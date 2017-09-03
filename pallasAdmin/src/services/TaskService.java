package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistence.Company;
import persistence.Task;
import services.interfaces.TaskServiceLocal;

@Stateless
public class TaskService implements TaskServiceLocal {

	@PersistenceContext
	private EntityManager em;

	public TaskService() {

	}

	@Override
	public List<Task> findAll() {

		return em.createQuery("SELECT t from Task t WHERE t.fetchable=true",
				Task.class).getResultList();
	}

	@Override
	public List<Task> findActiveTasks() {

		return em
				.createQuery(
						"SELECT distinct t.task FROM Transfer t WHERE t.status = 'pending' OR t.status = 'ongoing' AND t.fetchable=true",
						Task.class).getResultList();
	}

	@Override
	public void addTask(Integer companyid, String nbDays, String nbMonths) {
		Company company = em
				.createQuery("SELECT c FROM Company c WHERE c.id = :param",
						Company.class).setParameter("param", companyid)
				.getSingleResult();

		Task oldtask = null;
		String id;
		String lastCodeIncremented;
		try {
			oldtask = em
					.createQuery(
							"SELECT t FROM Task t WHERE t.fetchable=true ORDER BY t.id DESC",
							Task.class).setMaxResults(1).getSingleResult();
			id = oldtask.getId().toString();
			lastCodeIncremented = getLastCodeFromTaskCode(oldtask.getCode());

			id = incrementCode(lastCodeIncremented);

		} catch (Exception e) {
			id = "001";

		}

		if (nbMonths.length() == 1)
			nbMonths = "0" + nbMonths;

		if (nbDays.length() == 1)
			nbDays = "0" + nbDays;

		Task task = new Task();
		task.setCode(company.getCode() + "_" + id + "/" + nbMonths + nbDays);
		task.setFetchable(true);
		task.setDcr(new Date());
		em.persist(task);

	}

	private String incrementCode(String lastCodeIncremented) {
		Integer val = Integer.parseInt(lastCodeIncremented);
		if (val < 10) {
			return "00" + ++val;
		} else if (val >= 10 && val < 100) {
			return "0" + ++val;
		}
		return (++val).toString();
	}

	private String getLastCodeFromTaskCode(String code) {

		String result = code
				.substring(code.indexOf("_") + 1, code.indexOf("/"));

		return result;
	}

	@Override
	public void activate(Integer taskId, Boolean activated) {
		Task task = em.find(Task.class, taskId);
		task.setActivated(activated);
		em.merge(task);

	}

	@Override
	public Integer countInactivatedTaskes() {
		List<Task> activatedTasks = em
				.createQuery(
						"SELECT t from Task t WHERE t.fetchable = true and t.activated = false",
						Task.class).getResultList();
		return activatedTasks.size();
	}
}
