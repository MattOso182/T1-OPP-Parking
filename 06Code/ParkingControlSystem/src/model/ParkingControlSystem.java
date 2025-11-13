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
    private ResidentManager residentManager;

    public ParkingControlSystem(String systemId, ParkingLot parkingLot, ResidentManager residentManager) {
        this.parkingLot = parkingLot;
        this.visitorManager = new VisitorManager();
        this.residentManager = residentManager; 
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

            String userType = "Resident";

            if (residentManager != null) {
                Resident resident = residentManager.findResidentByVehiclePlate(plate);
                if (resident != null) {
                    userType = "Resident";
                }
            }

            if (userType.equals("Resident") && visitorManager != null && visitorManager.isVisitorAuthorized(plate)) {
                userType = "Visitor";
            }

            availableSpace.assignSpace("Auto assigned", userType, plate);
            librarySystem.registerEntry(plate);

            updateSpaceDefinitionStatus(availableSpace.getSpaceId(), true);

            System.out.println("Vehicle " + plate + " registered in space " + availableSpace.getSpaceId() + " as " + userType);
            return true;
        }
        System.out.println("No available spaces for vehicle " + plate);
        return false;
    }

    private void updateSpaceDefinitionStatus(String spaceId, boolean occupied) {
        if (parkingLot != null) {
            for (ParkingSpaceLibrary librarySpace : parkingLot.getSpaceList()) {
                if (librarySpace.getSpaceId().equals(spaceId)) {
                    
                    System.out.println("SpaceDefinition actualizado para: " + spaceId + " - "
                            + (occupied ? "OCUPADO" : "DISPONIBLE"));
                    break;
                }
            }
        }
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
