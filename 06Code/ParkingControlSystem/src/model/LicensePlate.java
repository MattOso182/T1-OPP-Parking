package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.Date;
import parkingcontrolsystem.library.LicensePlateLibrary;

public class LicensePlate {

    private LicensePlateLibrary libraryPlate;
    
    private Date registrationDate;
    private String vehicleType;

    public LicensePlate(String plateNumber, Date registrationDate, String province, String vehicleType) {
        
        this.libraryPlate = new LicensePlateLibrary(plateNumber, province);
        this.registrationDate = registrationDate;
        this.vehicleType = vehicleType;
    }

    public boolean validateFormat() {
        boolean isValid = this.libraryPlate.isValid(); 

        System.out.println("Validation for " + getPlateNumber() + ": " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }

    public void linkToUser(String userID) {
        System.out.println("License plate " + getPlateNumber() + " linked to User ID: " + userID);
    }

    public String getPlateInfo() {
        return String.format("Plate: %s | Province: %s | Type: %s | Reg Date: %s",
                getPlateNumber(), getProvince(), vehicleType, registrationDate);
    }

    public String getPlateNumber() {
        return this.libraryPlate.getPlateNumber();
    }

    public void setPlateNumber(String plateNumber) {
        this.libraryPlate.setPlateNumber(plateNumber);
    }

    public String getProvince() {
        return this.libraryPlate.getProvinceCode();
    }

    public void setProvince(String province) {
        this.libraryPlate.setProvinceCode(province);
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}