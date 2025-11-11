package ec.edu.espe.parkingLot.model;

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
