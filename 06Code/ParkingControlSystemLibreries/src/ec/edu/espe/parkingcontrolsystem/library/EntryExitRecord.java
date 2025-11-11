package ec.edu.espe.parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import java.util.Date;

public class EntryExitRecord {

   
    private String recordID;
    private String vehiclePlate;
    private Date entryTime;
    private Date exitTime;
    private String parkingSpaceID;
    private String operatorID;

    
    public EntryExitRecord() {
    }

    public EntryExitRecord(String recordID, String vehiclePlate, Date entryTime, Date exitTime, String parkingSpaceID, String operatorID) {
        this.recordID = recordID;
        this.vehiclePlate = vehiclePlate;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.parkingSpaceID = parkingSpaceID;
        this.operatorID = operatorID;
    }

   

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
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

    public String getParkingSpaceID() {
        return parkingSpaceID;
    }

    public void setParkingSpaceID(String parkingSpaceID) {
        this.parkingSpaceID = parkingSpaceID;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }


    public void registerEntry(String vehiclePlate, Date entryTime) {
        this.vehiclePlate = vehiclePlate;
        this.entryTime = entryTime;
    }


    public void registerExit(Date exitTime) {
        this.exitTime = exitTime;
    }

  
    public double calculateDuration() {
        if (entryTime != null && exitTime != null) {
            long diffMillis = exitTime.getTime() - entryTime.getTime();
            return diffMillis / (1000.0 * 60.0);
        }
        return 0.0;
    }

    
    public boolean verifyRentalStatus() {
        return parkingSpaceID != null && !parkingSpaceID.isEmpty();
    }

   
    public String getRecordInfo() {
        String info = "Record ID: " + recordID
                + "\nVehicle Plate: " + vehiclePlate
                + "\nEntry Time: " + entryTime
                + "\nExit Time: " + exitTime
                + "\nParking Space: " + parkingSpaceID
                + "\nOperator ID: " + operatorID;
        return info;
    }
}