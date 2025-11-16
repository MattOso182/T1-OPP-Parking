
package parkingcontrolsystem.library;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

import java.util.Date;

public class VisitorLibrary extends UserLibrary {
    private String visitorID;
    private String nameVisitor;
    private String vehiclePlate;
    private Date entryTime;
    private Date exitTime;

   
    public VisitorLibrary() {
    }

    
    public VisitorLibrary(String visitorID, String userID, String nameVisitor, String vehiclePlate, Date entryTime, Date exitTime) {
        super(userID);
        this.visitorID = visitorID;
        this.nameVisitor = nameVisitor;
        this.vehiclePlate = vehiclePlate;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    
    public String getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(String visitorID) {
        this.visitorID = visitorID;
    }

    public String getName() {
        return nameVisitor;
    }

    public void setName(String name) {
        this.nameVisitor = name;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    
    public boolean registerVisit() {
        
        if (vehiclePlate != null && nameVisitor != null) {
            System.out.println("Visitor " + nameVisitor + " registered with plate " + vehiclePlate);
            return true;
        }
        return false;
    }

    public boolean assignTemporarySpot() {
        
        System.out.println("Temporary parking spot assigned to visitor " + nameVisitor);
        return true;
    }

    public void exitParking() {
        this.exitTime = new Date();
        System.out.println("Visitor " + nameVisitor + " exited the parking lot at " + exitTime);
    }

    public boolean verifyIdentity() {
        
        return nameVisitor != null && vehiclePlate != null;
    }

    public String getVisitorInfo() {
        return "Visitor ID: " + visitorID +
               "\nName: " + nameVisitor +
               "\nVehicle Plate: " + vehiclePlate +
               "\nEntry Time: " + entryTime +
               "\nExit Time: " + exitTime;
    }
}
