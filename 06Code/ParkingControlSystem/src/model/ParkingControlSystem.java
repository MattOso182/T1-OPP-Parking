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
            System.out.println("El sistema no esta activo");
            return false;
        }

        ParkingSpaceLibrary availableSpace = parkingLot.findAvailableSpace();
        if (availableSpace != null) {

            String userType = "Residente"; 

            if (residentManager != null) {
                Resident resident = residentManager.findResidentByVehiclePlate(plate);
                if (resident != null) {
                    userType = "Residente";
                }
            }

            if (userType.equals("Residente") && visitorManager != null && visitorManager.isVisitorAuthorized(plate)) {
                userType = "Visitante"; 
            }

            availableSpace.assignSpace("Asignado automaticamente", userType, plate);
            librarySystem.registerEntry(plate);

            updateSpaceDefinitionStatus(availableSpace.getSpaceId(), true);

            System.out.println("Vehiculo " + plate + " registrado en el espacio " + availableSpace.getSpaceId() + " como " + userType);
            return true;
        }
        System.out.println("No hay espacios disponibles para el vehiculo " + plate);
        return false;
    }

    private void updateSpaceDefinitionStatus(String spaceId, boolean occupied) {
        if (parkingLot != null) {
            for (ParkingSpaceLibrary librarySpace : parkingLot.getSpaceList()) {
                if (librarySpace.getSpaceId().equals(spaceId)) {
                    
                    System.out.println("SPACEDEFINITION ACTUALIZADO PARA: " + spaceId + " - "
                                + (occupied ? "OCUPADO" : "DISPONIBLE"));
                    break;
                }
            }
        }
    }

    public boolean registerExit(String plate) {
        if (!librarySystem.isActive()) {
            System.out.println("El sistema no esta activo");
            return false;
        }

        for (ParkingSpaceLibrary space : parkingLot.getLibraryParkingLot().getParkingSpaces()) {
            if (space.isOccupied() && plate.equals(space.getVehiclePlate())) {
                space.freeSpace();
                librarySystem.registerExit(plate);
                System.out.println("Vehiculo " + plate + " salio del espacio " + space.getSpaceId());
                return true;
            }
        }
        System.out.println("Vehiculo " + plate + " no encontrado en el estacionamiento");
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