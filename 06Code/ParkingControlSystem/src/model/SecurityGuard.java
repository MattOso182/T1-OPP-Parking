package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.Date;
import parkingcontrolsystem.library.SecurityGuardLibrary;
import parkingcontrolsystem.library.VehicleLibrary;

public class SecurityGuard extends User {

    private String name;
    private String shift;
    private String phoneNumber;
    private boolean isOnDuty;

    private SecurityGuardLibrary libraryGuard;
    private ParkingControlSystem controlSystem;
    private EntryExitRecord entryExitSystem;
    private ResidentManager residentManager;
    private VisitorManager visitorManager;

    private VehicleLibrary createVehicleLibraryWrapper(String plate) {
        return new VehicleLibrary(
                plate,
                "", "", "",
                "", "", "",
                0.0,
                false
        );
    }

    public SecurityGuard(String userID, String name, String shift, String phoneNumber,
            ParkingControlSystem controlSystem, EntryExitRecord entryExitSystem,
            ResidentManager residentManager, VisitorManager visitorManager) {
        super(userID);
        this.name = name;
        this.shift = shift;
        this.phoneNumber = phoneNumber;
        this.isOnDuty = false;

        this.libraryGuard = new SecurityGuardLibrary(userID, name, shift);

        this.controlSystem = controlSystem;
        this.entryExitSystem = entryExitSystem;
        this.residentManager = residentManager;
        this.visitorManager = visitorManager;
    }

    public boolean verifyAuthorization(String vehiclePlateOrID) {
        System.out.print("    -> Verificando: " + vehiclePlateOrID + "... ");

        Resident resident = residentManager.findResidentByVehiclePlate(vehiclePlateOrID);
        if (resident != null) {
            boolean hasParking = resident.getUserType() == UserType.WITH_PARKING;
            boolean hasRental = resident.hasActiveRental();

            if (hasParking) {
                System.out.println("Autorizado (Residente WITH_PARKING).");
                return true;
            } else if (hasRental) {
                System.out.println("Autorizado (Residente ROTATING con RENTAL).");
                return true;
            } else {
                System.out.println("NO Autorizado (Residente ROTATING sin rental activo).");
                return false;
            }
        }

        if (visitorManager != null && visitorManager.isVisitorAuthorized(vehiclePlateOrID)) {
            System.out.println("Autorizado (Visitante con PASE ACTIVO).");
            return true;
        }

        System.out.println("NO Autorizado (No encontrado en el sistema).");
        return false;
    }

    public void registerEntry(String vehiclePlate, Date time) {
        if (!isOnDuty) {
            System.out.println("Guardia " + this.name + " esta fuera de servicio. Entrada denegada.");
            return;
        }

        if (verifyAuthorization(vehiclePlate)) {
            if (controlSystem.registerEntry(vehiclePlate)) {
                if (entryExitSystem != null) {
                    entryExitSystem.registerEntry(vehiclePlate, time);
                }

                if (libraryGuard != null) {
                    libraryGuard.registerEntry(createVehicleLibraryWrapper(vehiclePlate));
                }

                System.out.println("Entrada registrada para: " + vehiclePlate);
            } else {
                System.out.println("Entrada fallida. Estacionamiento lleno.");
            }
        } else {
            System.out.println("Entrada denegada: Vehiculo/ID no autorizado.");
        }
    }

    public void registerExit(String vehiclePlate, Date time) {
        if (!isOnDuty) {
            System.out.println("Guard " + this.name + " esta fuera de servicio. Salida denegada.");
            return;
        }

        if (entryExitSystem != null) {
            entryExitSystem.registerExit(vehiclePlate, time);
        }

        if (controlSystem.registerExit(vehiclePlate)) {
            if (libraryGuard != null) {
                libraryGuard.registerExit(createVehicleLibraryWrapper(vehiclePlate));
            }

            System.out.println("Salida registrada para: " + vehiclePlate);
        } else {
            System.out.println("Salida fallida. Vehiculo no encontrado.");
        }
    }

    public String getGuardID() {
        return libraryGuard.getId();
    }

    public void setGuardID(String guardID) {
        setUserID(guardID);
        libraryGuard.setId(guardID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        libraryGuard.setName(name);
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
        libraryGuard.setShift(shift);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOnDuty() {
        return isOnDuty;
    }

    public void setOnDuty(boolean isOnDuty) {
        this.isOnDuty = isOnDuty;
        System.out.println("Guardia " + this.name + " El estado cambio a: " + (isOnDuty ? "EN SERVICIO" : "FUERA DE SERVICIO"));
    }

    public ParkingControlSystem getControlSystem() {
        return controlSystem;
    }

    public EntryExitRecord getEntryExitSystem() {
        return entryExitSystem;
    }

    public ResidentManager getResidentManager() {
        return residentManager;
    }

    public VisitorManager getVisitorManager() {
        return visitorManager;
    }
}
