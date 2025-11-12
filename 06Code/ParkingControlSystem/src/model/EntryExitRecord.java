package model;

import java.util.Date;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class EntryExitRecord {

    private final String recordID;
    private String vehiclePlate;
    private Date entryTime;
    private Date exitTime;
    private String parkingSpaceID;
    private String operatorID;

    public EntryExitRecord() {
        this.recordID = "REC-" + System.currentTimeMillis();
    }

    public void registerEntry(String vehiclePlate, Date entryTime) {
        this.vehiclePlate = vehiclePlate;
        this.entryTime = entryTime;
        System.out.println("     [EER]: Registro de entrada CREADO para " + vehiclePlate + " a las " + entryTime);
    }

    public void registerExit(String vehiclePlate, Date exitTime) {
        if (this.vehiclePlate != null && this.vehiclePlate.equals(vehiclePlate) && this.exitTime == null) {
            this.exitTime = exitTime;
            System.out.println("     [EER]: Registro de salida ACTUALIZADO para " + vehiclePlate + " a las " + exitTime);
        } else {
            System.out.println("     [EER]: ERROR: No se encontró un registro activo para la salida de " + vehiclePlate);
        }
    }

    public double calculateDuration() {
        if (entryTime != null && exitTime != null) {
            long diff = exitTime.getTime() - entryTime.getTime();
            return (double) diff / (1000 * 60 * 60);
        }
        return 0.0;
    }

    public boolean verifyRentalStatus() {
        return true;
    }

    public String getRecordInfo() {
        return String.format("Record ID: %s, Plate: %s, Entry: %s, Exit: %s. Duration: %.2f hours",
                recordID, vehiclePlate, entryTime, exitTime, calculateDuration());
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

}
