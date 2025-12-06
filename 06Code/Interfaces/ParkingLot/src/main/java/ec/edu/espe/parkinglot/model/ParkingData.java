package ec.edu.espe.parkinglot.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingData { 
    
    private List<Vehicle> registeredVehicles; 

    public ParkingData() {
        this.registeredVehicles = new ArrayList<>();
    }

    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle vehicle : registeredVehicles) {
            if (vehicle.getLicensePlate().equals(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    public boolean recordVehicleEntry(Vehicle newVehicle) {
        if (findVehicleByPlate(newVehicle.getLicensePlate()) == null) {
            return registeredVehicles.add(newVehicle);
        }
        return false; 
    }
    
    public boolean updateVehicleType(String plate, String newType) {
        Vehicle vehicleToUpdate = findVehicleByPlate(plate);
        
        if (vehicleToUpdate != null) {
            vehicleToUpdate.setVehicleType(newType); 
            return true; 
        }
        return false; 
    }
    
    public boolean removeVehicle(String plate) {
        return registeredVehicles.removeIf(v -> v.getLicensePlate().equals(plate));
    }
    
    public List<Vehicle> getAllVehicles() {
        return registeredVehicles;
    }
}