package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import persistence.Staff;

@Local
public interface StaffServiceLocal {

	public List<Staff> findAll();

	public List<Staff> findAllDrivers();
	
	public Staff findStaffById(Integer id);

	public List<Staff> findAllCPOs();

	public List<Staff> findAllCoordinators();

	public void add(Staff staff);

	public void save(Staff staff);

	public void delete(Integer id);

	public void setPicture(Integer entityId, String fileName);
	
	public void activateStaff(Integer staffId,Boolean active);
	
	
}
