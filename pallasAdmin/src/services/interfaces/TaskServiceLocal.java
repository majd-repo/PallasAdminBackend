package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import persistence.Task;

@Local
public interface TaskServiceLocal {

	public List<Task> findAll();

	public List<Task> findActiveTasks();

	public void addTask(Integer companyid, String nbDays, String nbMonths);

	public void activate(Integer taskId, Boolean activated);

	public Integer countInactivatedTaskes();
}
