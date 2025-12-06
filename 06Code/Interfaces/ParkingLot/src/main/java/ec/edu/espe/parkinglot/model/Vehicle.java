package ec.edu.espe.parkinglot.model;

import java.util.Date;

public class Vehicle { 

    private String licensePlate; 
    private String vehicleType;  
    private Date entryTime;      

    public Vehicle(String licensePlate, String vehicleType, Date entryTime) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
    }

    public String getLicensePlate() { 
        return licensePlate; 
    }
    
    public String getVehicleType() { 
        return vehicleType; 
    }
    
    public Date getEntryTime() { 
        return entryTime; 
    }
    public void setVehicleType(String vehicleType) { // ¡AGREGAR ESTO!
        this.vehicleType = vehicleType;
    }
}