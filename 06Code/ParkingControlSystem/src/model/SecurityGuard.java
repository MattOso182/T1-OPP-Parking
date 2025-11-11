package model;

import java.util.Date;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class SecurityGuard {

    private String guardID;
    private String name;
    private String shift;
    private String phoneNumber;
    private boolean isOnDuty;
    private ParkingControlSystem controlSystem;

    public SecurityGuard(String guardID, String name, String shift, String phoneNumber, ParkingControlSystem controlSystem) {
        this.guardID = guardID;
        this.name = name;
        this.shift = shift;
        this.phoneNumber = phoneNumber;
        this.isOnDuty = false;
        this.controlSystem = controlSystem; 
    }

    public boolean registerEntry(String vehiclePlate, Date time) {
        if (!isOnDuty) {
            return false;
        }
        return controlSystem.registerEntry(vehiclePlate);
    }

    public boolean registerExit(String vehiclePlate, Date time) {
        if (!isOnDuty) {
            return false;
        }
        return controlSystem.registerExit(vehiclePlate);
    }

    public boolean verifyAuthorization(String visitorID) {
        boolean authorized = visitorID != null && !visitorID.trim().isEmpty(); 
        return authorized;
    }

    public String getGuardID() {
        return guardID;
    }

    public void setGuardID(String guardID) {
        this.guardID = guardID;
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

    public void setIsOnDuty(boolean isOnDuty) {
        this.isOnDuty = isOnDuty;
    }

    public ParkingControlSystem getControlSystem() {
        return controlSystem;
    }

    public void setControlSystem(ParkingControlSystem controlSystem) {
        this.controlSystem = controlSystem;
    }
    
   
}