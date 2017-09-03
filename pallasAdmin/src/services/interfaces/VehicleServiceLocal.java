package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import persistence.Vehicle;

@Local
public interface VehicleServiceLocal {

	public List<Vehicle> findAllVehicles();

	public void addVehicle(Vehicle vehicle);

	public List<Vehicle> findVehiclesByBrand(String brand);

	public Vehicle findVehicleByPlate(String plate);

	public void updateVehicle(Vehicle vehicle);

	public void removeVehicle(Integer id);

	public void setPicture(Integer entityId, String fileName);
}
