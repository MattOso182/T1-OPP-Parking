package ec.edu.espe.parkingLot.model;

import java.util.Date;

public class SecurityGuard extends User {

    private String name;
    private String shift;
    private String phoneNumber;
    private boolean isOnDuty; 
    
    private ParkingControlSystem controlSystem;
    private EntryExitRecord entryExitSystem;
    private ResidentManager residentManager; 
    private VisitorManager visitorManager; 

    public SecurityGuard(String userID, String name, String shift, String phoneNumber, 
                         ParkingControlSystem controlSystem, EntryExitRecord entryExitSystem, 
                         ResidentManager residentManager, VisitorManager visitorManager) {
        super(userID);
        this.name = name;
        this.shift = shift;
        this.phoneNumber = phoneNumber;
        this.isOnDuty = false;
        this.controlSystem = controlSystem;
        this.entryExitSystem = entryExitSystem;
        this.residentManager = residentManager;
        this.visitorManager = visitorManager;
    }
    
    public boolean verifyAuthorization(String vehiclePlateOrID) {
        System.out.print("   -> Verificando: " + vehiclePlateOrID + "... ");
        
        Resident resident = residentManager.findResidentByVehiclePlate(vehiclePlateOrID);
        if (resident != null && resident.hasActiveRental()) {
            System.out.println("Autorizado (Residente ACTIVO).");
            return true;
        }
        
        if (visitorManager != null && visitorManager.isVisitorAuthorized(vehiclePlateOrID)) {
             System.out.println("Autorizado (Visitante con PASE ACTIVO).");
             return true;
        }

        System.out.println("NO Autorizado.");
        return false;
    }
    
    public void registerEntry(String vehiclePlate, Date time) {
        if (!isOnDuty) {
            System.out.println("Guard " + this.name + " esta fuera de servicio. Entrada denegada.");
            return; 
        }

        if (verifyAuthorization(vehiclePlate)) { 
            if (controlSystem.registerEntry(vehiclePlate)) { 
                if (entryExitSystem != null) {
                    entryExitSystem.registerEntry(vehiclePlate, time);
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
            System.out.println("Salida registrada para: " + vehiclePlate);
        } else {
            System.out.println("Salida fallida. Vehiculo no encontrado.");
        }
    }

    public String getGuardID() { 
        return getUserID();
    }
    public void setGuardID(String guardID) { 
        setUserID(guardID);
    } 
    
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public String getShift() { 
        return shift;
    }
    public void setShift(String shift) { 
        this.shift = shift; 
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
        System.out.println("Guard " + this.name + " status changed to: " + (isOnDuty ? "ON DUTY" : "OFF DUTY"));
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