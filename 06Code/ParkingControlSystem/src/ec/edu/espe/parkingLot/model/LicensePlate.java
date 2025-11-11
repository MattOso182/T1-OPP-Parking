package ec.edu.espe.parkingLot.model;

import java.util.Date;

public class LicensePlate {
    
    private String plateNumber;         
    private Date registrationDate;      
    private String province;            
    private String vehicleType;         

    public LicensePlate(String plateNumber, Date registrationDate, String province, String vehicleType) {
        this.plateNumber = plateNumber;
        this.registrationDate = registrationDate;
        this.province = province;
        this.vehicleType = vehicleType;
    }

    public boolean validateFormat() {
        if (plateNumber == null || plateNumber.length() < 6 || plateNumber.length() > 8) {
            System.out.println("Validation for " + plateNumber + ": FAILED (Length)");
            return false;
        }
        
        boolean isValid = plateNumber.matches("[A-Z]{2,3}[- ]?[0-9]{3,4}");
        
        System.out.println("Validation for " + plateNumber + ": " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }

    public void linkToUser(String userID) {
        System.out.println("License plate " + plateNumber + " linked to User ID: " + userID);
    }
    
    public String getPlateInfo() {
        return String.format("Plate: %s | Province: %s | Type: %s | Reg Date: %s",
            plateNumber, province, vehicleType, registrationDate);
    }

    public String getPlateNumber() {
        return plateNumber;
    }
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}