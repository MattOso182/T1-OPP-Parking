package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import parkingcontrolsystem.library.ParkingControlSystemLibrary;
import parkingcontrolsystem.library.ParkingSpaceLibrary;

public class ParkingControlSystem {

    private ParkingControlSystemLibrary librarySystem;
    private ParkingLot parkingLot; 
    private VisitorManager visitorManager; 

    public ParkingControlSystem(String systemId, ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.visitorManager = new VisitorManager(); 
        this.librarySystem = new ParkingControlSystemLibrary(systemId, true, 0);
    }

    public boolean startSystem() {
        return librarySystem.startSystem();
    }

    public void stopSystem() {
        librarySystem.stopSystem();
    }

    public boolean registerEntry(String plate) {
        if (!librarySystem.isActive()) {
            System.out.println("System is not active");
            return false;
        }

        ParkingSpaceLibrary availableSpace = parkingLot.findAvailableSpace();
        if (availableSpace != null) {
            availableSpace.assignSpace("Auto assigned", "Visitor", plate);
            librarySystem.registerEntry(plate);
            System.out.println("Vehicle " + plate + " registered in space " + availableSpace.getSpaceId());
            return true;
        }
        System.out.println("No available spaces for vehicle " + plate);
        return false;
    }

    public boolean registerExit(String plate) {
        if (!librarySystem.isActive()) {
            System.out.println("System is not active");
            return false;
        }

        for (ParkingSpaceLibrary space : parkingLot.getLibraryParkingLot().getParkingSpaces()) {
            if (space.isOccupied() && plate.equals(space.getVehiclePlate())) {
                space.freeSpace();
                librarySystem.registerExit(plate);
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
        return librarySystem.generateReport();
    }

    public String getSystemId() {
        return librarySystem.getSystemId();
    }

    public boolean isActive() {
        return librarySystem.isActive();
    }

    public int getTotalVehicles() {
        return librarySystem.getTotalVehicles();
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public boolean checkSystemStatus() {
        return librarySystem.isActive();
    }

    public String getDetailedReport() {
        return librarySystem.generateReport();
    }
}
