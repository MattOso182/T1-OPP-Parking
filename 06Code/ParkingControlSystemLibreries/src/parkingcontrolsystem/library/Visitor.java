
package parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import java.util.Date;

public class Visitor extends User {
    private String visitorID;
    private String name;
    private String vehiclePlate;
    private Date entryTime;
    private Date exitTime;

   
    public Visitor() {
    }

    
    public Visitor(String visitorID, String userID, String name, String vehiclePlate, Date entryTime, Date exitTime) {
        super(userID);
        this.visitorID = visitorID;
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        
        if (vehiclePlate != null && name != null) {
            System.out.println("Visitor " + name + " registered with plate " + vehiclePlate);
            return true;
        }
        return false;
    }

    public boolean assignTemporarySpot() {
        
        System.out.println("Temporary parking spot assigned to visitor " + name);
        return true;
    }

    public void exitParking() {
        this.exitTime = new Date();
        System.out.println("Visitor " + name + " exited the parking lot at " + exitTime);
    }

    public boolean verifyIdentity() {
        
        return name != null && vehiclePlate != null;
    }

    public String getVisitorInfo() {
        return "Visitor ID: " + visitorID +
               "\nName: " + name +
               "\nVehicle Plate: " + vehiclePlate +
               "\nEntry Time: " + entryTime +
               "\nExit Time: " + exitTime;
    }
}
