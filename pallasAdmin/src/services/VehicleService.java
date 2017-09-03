package services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistence.Transfer;
import persistence.Vehicle;
import services.interfaces.VehicleServiceLocal;

@Stateless
@LocalBean
public class VehicleService implements VehicleServiceLocal {
	private String NO_VEHICULE_IMG = null;
	private String IMG_URL = null;

	@PersistenceContext
	private EntityManager em;

	public VehicleService() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"config.properties");
			// load a properties file
			prop.load(input);
			NO_VEHICULE_IMG = prop.getProperty("NO_VEHICULE_IMG");
			IMG_URL = prop.getProperty("IMG_URL");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Vehicle> findAllVehicles() {

		return em.createQuery("select v from Vehicle v WHERE v.fetchable=true",
				Vehicle.class).getResultList();
	}

	@Override
	public void addVehicle(Vehicle vehicle) {
		vehicle.setPicture(NO_VEHICULE_IMG);
		vehicle.setFetchable(true);
		vehicle.setDcr(new Date());
		em.persist(vehicle);
	}

	@Override
	public List<Vehicle> findVehiclesByBrand(String brand) {

		return em
				.createQuery(
						"select v from Vehicle v where v.brand=:param AND v.fetchable=true",
						Vehicle.class).setParameter("param", brand)
				.getResultList();
	}

	@Override
	public Vehicle findVehicleByPlate(String plate) {
		return em
				.createQuery(
						"select v from Vehicle v where v.plate=:param AND v.fetchable=true",
						Vehicle.class).setParameter("param", plate)
				.getSingleResult();

	}

	@Override
	public void removeVehicle(Integer id) {
		List<Transfer> transfers = em
				.createQuery(
						"SELECT t FROM Transfer t WHERE t.vehicle.id = :param",
						Transfer.class).setParameter("param", id)
				.getResultList();
		for (Transfer transfer : transfers) {
			em.remove(em.merge(em.find(Transfer.class, transfer.getId())));
		}
		em.remove(em.merge(em.find(Vehicle.class, id)));

	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		vehicle.setDmj(new Date());
		em.merge(vehicle);

	}

	@Override
	public void setPicture(Integer entityId, String fileName) {

		Vehicle vehicle = em.find(Vehicle.class, entityId);
		vehicle.setPicture(IMG_URL + fileName);
		vehicle.setDmj(new Date());
		em.merge(vehicle);

	}

}
