package services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistence.Staff;
import persistence.Transfer;
import services.interfaces.StaffServiceLocal;

@Stateless
public class StaffService implements StaffServiceLocal {
	private String NO_USER_IMG = null;
	private String IMG_URL = null;

	public StaffService() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"config.properties");
			// load a properties file
			prop.load(input);
			NO_USER_IMG = prop.getProperty("NO_USER_IMG");
			IMG_URL = prop.getProperty("IMG_URL");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Staff> findAll() {
		return em.createQuery("SELECT s FROM Staff s WHERE s.fetchable=true",
				Staff.class).getResultList();
	}

	@Override
	public List<Staff> findAllDrivers() {
		return em
				.createQuery(
						"SELECT s FROM Staff s where s.agentLevel = 'driver' and s.fetchable=true",
						Staff.class).getResultList();
	}

	@Override
	public List<Staff> findAllCPOs() {
		return em
				.createQuery(
						"SELECT s FROM Staff s where s.agentLevel = 'cpo' and s.fetchable=true",
						Staff.class).getResultList();
	}

	@Override
	public List<Staff> findAllCoordinators() {
		return em
				.createQuery(
						"SELECT s FROM Staff s where s.agentLevel = 'coordinator' and s.fetchable=true",
						Staff.class).getResultList();
	}

	@Override
	public void add(Staff staff) {
		staff.setRole("AGENT");
		staff.setPicture(NO_USER_IMG);
		staff.setFetchable(true);
		staff.setActive(false);
		staff.setDcr(new Date());
		em.persist(staff);
	}

	@Override
	public void save(Staff staff) {
		staff.setDmj(new Date());
		em.merge(staff);
	}

	@Override
	public void delete(Integer id) {
		List<Transfer> transfersByDriver = em
				.createQuery(
						"SELECT t FROM Transfer t WHERE t.driver.id = :param",
						Transfer.class).setParameter("param", id)
				.getResultList();
		if (transfersByDriver.size() > 0) {
			for (Transfer transfer : transfersByDriver) {
				transfer.setDriver(null);
				em.merge(transfer);
			}
		}

		List<Transfer> transfersByCpo = em
				.createQuery(
						"SELECT t FROM Transfer t WHERE t.cpo.id = :param",
						Transfer.class).setParameter("param", id)
				.getResultList();
		if (transfersByCpo.size() > 0) {
			for (Transfer transfer : transfersByCpo) {
				transfer.setCpo(null);
				em.merge(transfer);
			}
		}

		List<Transfer> transfersByCoordinator1 = em
				.createQuery(
						"SELECT t FROM Transfer t WHERE t.coordinator1.id = :param",
						Transfer.class).setParameter("param", id)
				.getResultList();
		if (transfersByCoordinator1.size() > 0) {
			for (Transfer transfer : transfersByCoordinator1) {
				transfer.setCoordinator1(null);
				em.merge(transfer);
			}
		}

		List<Transfer> transfersByCoordinator2 = em
				.createQuery(
						"SELECT t FROM Transfer t WHERE t.coordinator2.id = :param",
						Transfer.class).setParameter("param", id)
				.getResultList();
		if (transfersByCoordinator2.size() > 0) {
			for (Transfer transfer : transfersByCoordinator2) {
				transfer.setCoordinator2(null);
				em.merge(transfer);
			}
		}

		Staff staffFound = em.find(Staff.class, id);
		em.remove(staffFound);

	}

	@Override
	public void setPicture(Integer entityId, String fileName) {
		em.find(Staff.class, entityId).setPicture(IMG_URL + fileName);

	}

	@Override
	public Staff findStaffById(Integer id) {
		return em.find(Staff.class, id);
	}

	@Override
	public void activateStaff(Integer staffId, Boolean active){
		Staff staff = em.find(Staff.class, staffId);
		staff.setActive(active);
		staff.setDmj(new Date());
		em.merge(staff);

	}
}
