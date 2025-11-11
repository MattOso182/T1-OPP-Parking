package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.ArrayList;
import java.util.List;

public class ParkingControlSystem {
    private String systemId;
    private boolean active;
    private int totalVehicles;
    private ParkingLot parkingLot;
    
    public ParkingControlSystem(String systemId, ParkingLot parkingLot) {
        this.systemId = systemId;
        this.parkingLot = parkingLot;
        this.active = false;
        this.totalVehicles = 0;
    }
    
    public boolean startSystem() {
        this.active = true;
        System.out.println("Parking control system started");
        return true;
    }
    
    public void stopSystem() {
        this.active = false;
        System.out.println("Parking control system stopped");
    }
    
    public boolean registerEntry(String plate) {
        if (!active) {
            System.out.println("System is not active");
            return false;
        }
        
        ParkingSpace availableSpace = parkingLot.findAvailableSpace();
        if (availableSpace != null) {
            availableSpace.assignSpace(plate);
            totalVehicles++;
            parkingLot.updateSpaceStatus(availableSpace.getSpaceId(), "OCCUPIED");
            System.out.println("Vehicle " + plate + " registered in space " + availableSpace.getSpaceId());
            return true;
        }
        System.out.println("No available spaces for vehicle " + plate);
        return false;
    }
    
    public boolean registerExit(String plate) {
        if (!active) {
            System.out.println("System is not active");
            return false;
        }
        
        for (ParkingSpace space : parkingLot.getSpaceList()) {
            if (space.isOccupied() && space.getAssignedTo().equals(plate)) {
                space.releaseSpace();
                totalVehicles--;
                parkingLot.updateSpaceStatus(space.getSpaceId(), "AVAILABLE");
                System.out.println("Vehicle " + plate + " exited from space " + space.getSpaceId());
                return true;
            }
        }
        System.out.println("Vehicle " + plate + " not found in the parking lot");
        return false;
    }
    
    public int checkAvailability() {
        return parkingLot.calculateAvailableSpaces();
    }
    
    public String generateReport() {
        return "System Report - " + systemId + 
               "\nStatus: " + (active ? "ACTIVE" : "INACTIVE") +
               "\nVehicles in parking: " + totalVehicles +
               "\nAvailable spaces: " + checkAvailability() +
               "\n" + parkingLot.getOccupancyReport();
    }
    
    // Getters and Setters
    public String getSystemId() { return systemId; }
    public boolean isActive() { return active; }
    public int getTotalVehicles() { return totalVehicles; }
    public ParkingLot getParkingLot() { return parkingLot; }
}