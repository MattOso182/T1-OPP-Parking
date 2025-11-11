package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

import java.util.Date;

public class Visitor {
    private String visitorID;
    private String name;
    private Date entryTime;
    private Date exitTime;
    private boolean isWaiting;
    private boolean hasTemporaryPass;
    
    public Visitor(String visitorID, String name) {
        this.visitorID = visitorID;
        this.name = name;
        this.isWaiting = false;
        this.hasTemporaryPass = false;
    }
   
    public boolean registerWait() {
        if (!isWaiting) {
            isWaiting = true;
            System.out.println("Visitor " + name + " is now waiting for authorization");
            return true;
        }
        System.out.println("Visitor " + name + " is already waiting");
        return false;
    }
    
    public boolean assignTemporaryPass() {
        if (!hasTemporaryPass) {
            hasTemporaryPass = true;
            this.entryTime = new Date();
            System.out.println("Temporary pass assigned to visitor: " + name);
            return true;
        }
        System.out.println("Visitor " + name + " already has a temporary pass");
        return false;
    }
    
    public void recordParking() {
        this.exitTime = new Date();
        hasTemporaryPass = false;
        System.out.println("Parking recorded for visitor: " + name);
    }
    
    public boolean verifyIdentity() {
        boolean isValid = visitorID != null && !visitorID.isEmpty() && name != null && !name.isEmpty();
        System.out.println("Identity verification for " + name + ": " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }
    
    public boolean requestEntry() {
        System.out.println("\n--- SOLICITUD DE INGRESO ---");
        System.out.println("Visitante: " + name + " (ID: " + visitorID + ")");
        
        if (!verifyIdentity()) {
            System.out.println("SOLICITUD RECHAZADA: Identidad no válida");
            return false;
        }

        if (!registerWait()) {
            return false;
        }
        
        System.out.println("Visitante en espera de autorización...");
        System.out.println("(La validación de autorización se hace en ResidentManager)");
        return false;
    }
    
    public boolean recordExit() {
        if (hasTemporaryPass && exitTime == null) {
            this.exitTime = new Date();
            this.hasTemporaryPass = false;
            System.out.println(" Egreso registrado para visitante: " + name);
            if (entryTime != null) {
                long stayTime = (exitTime.getTime() - entryTime.getTime()) / (1000 * 60);
                System.out.println("  Tiempo de estadía: " + stayTime + " minutos");
            }
            return true;
        }
        System.out.println("No se puede registrar egreso: Visitante no tiene pase activo");
        return false;
    }
    
    // Getters and Setters
    public String getVisitorID() { return visitorID; }
    public String getName() { return name; }
    public Date getEntryTime() { return entryTime; }
    public Date getExitTime() { return exitTime; }
    public boolean isWaiting() { return isWaiting; }
    public boolean hasTemporaryPass() { return hasTemporaryPass; }
    
    public String getVisitorInfo() {
        return "Visitor ID: " + visitorID +
               "\nName: " + name +
               "\nStatus: " + (hasTemporaryPass ? "ACTIVE" : "INACTIVE") +
               "\nEntry Time: " + (entryTime != null ? entryTime : "Not entered") +
               "\nExit Time: " + (exitTime != null ? exitTime : "Not exited");
    }
}
