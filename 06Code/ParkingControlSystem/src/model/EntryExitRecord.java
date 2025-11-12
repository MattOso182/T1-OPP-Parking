package model;

/**
 * * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.Date;
import parkingcontrolsystem.library.EntryExitRecordLibrary;

public class EntryExitRecord {

    private EntryExitRecordLibrary libraryRecord;
    
    private final String recordID; 

    public EntryExitRecord() {
        this.recordID = "REC-" + System.currentTimeMillis();
        
        this.libraryRecord = new EntryExitRecordLibrary(
            this.recordID, 
            "",     
            null,  
            null,   
            "",     
            ""      
        );
    }
    
    public void registerEntry(String vehiclePlate, Date entryTime) {
        this.libraryRecord.registerEntry(vehiclePlate, entryTime);
        
        System.out.println("   [EER]: Registro de entrada CREADO/ACTUALIZADO para " + vehiclePlate + " a las " + entryTime);
    }

    public void registerExit(String vehiclePlate, Date exitTime) {
        if (getVehiclePlate() != null && getVehiclePlate().equals(vehiclePlate) && getExitTime() == null) {
            
            this.libraryRecord.registerExit(exitTime);
            
            System.out.println("   [EER]: Registro de salida ACTUALIZADO para " + vehiclePlate + " a las " + exitTime);
        } else {
            System.out.println("   [EER]: ERROR: No se encontró un registro activo para la salida de " + vehiclePlate);
        }
    }

    
    public double calculateDuration() {
        return this.libraryRecord.calculateDuration() / 60.0;
    }

    public boolean verifyRentalStatus() {
        return true; 
    }

    public String getRecordInfo() {
        return this.libraryRecord.getRecordInfo();
    }
    
    public String getRecordID() {
        return this.recordID;
    }
    
    public String getVehiclePlate() {
        return this.libraryRecord.getVehiclePlate();
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.libraryRecord.setVehiclePlate(vehiclePlate);
    }

    public Date getEntryTime() {
        return this.libraryRecord.getEntryTime();
    }

    public void setEntryTime(Date entryTime) {
        this.libraryRecord.setEntryTime(entryTime);
    }

    public Date getExitTime() {
        return this.libraryRecord.getExitTime();
    }

    public void setExitTime(Date exitTime) {
        this.libraryRecord.setExitTime(exitTime);
    }

    public String getParkingSpaceID() {
        return this.libraryRecord.getParkingSpaceID();
    }

    public void setParkingSpaceID(String parkingSpaceID) {
        this.libraryRecord.setParkingSpaceID(parkingSpaceID);
    }

    public String getOperatorID() {
        return this.libraryRecord.getOperatorID();
    }

    public void setOperatorID(String operatorID) {
        this.libraryRecord.setOperatorID(operatorID);
    }
}