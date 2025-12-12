package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
import java.util.ArrayList;
import java.util.List;

public class Resident {
    private String residentID;
    private String name;
    private String apartmentNumber;
    private String email;
    private String phone;
    private String userType;
    private String assignedParkingSpace;
    private List<Vehicle> vehicles;
    private List<String> authorizedVisitors;
    private Rental currentRental;

    public enum UserType {
        WITH_PARKING,
        ROTATING
    }

    public Resident() {
        this.vehicles = new ArrayList<>();
        this.authorizedVisitors = new ArrayList<>();
    }

    public Resident(String residentID, String name, String apartmentNumber,
            String email, String phone, String assignedParkingSpace) {
        this.residentID = residentID;
        this.name = name;
        this.apartmentNumber = apartmentNumber;
        this.email = email;
        this.phone = phone;
        this.userType = "WITH_PARKING";
        this.assignedParkingSpace = assignedParkingSpace;
        this.vehicles = new ArrayList<>();
        this.authorizedVisitors = new ArrayList<>();
        this.currentRental = null;
    }

    public Resident(String residentID, String name, String apartmentNumber,
            String email, String phone) {
        this.residentID = residentID;
        this.name = name;
        this.apartmentNumber = apartmentNumber;
        this.email = email;
        this.phone = phone;
        this.userType = "ROTATING";
        this.assignedParkingSpace = null;
        this.vehicles = new ArrayList<>();
        this.authorizedVisitors = new ArrayList<>();
        this.currentRental = null;
    }

    public boolean isPermanent() {
        return "WITH_PARKING".equals(userType);
    }

    public boolean isRotating() {
        return "ROTATING".equals(userType);
    }

    public boolean hasActiveRental() {
        return currentRental != null && currentRental.isActive();
    }

    public String getRentedSpace() {
        return hasActiveRental() ? currentRental.getSpaceId() : null;
    }

    public String getResidentID() {
        return residentID;
    }

    public void setResidentID(String residentID) {
        this.residentID = residentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAssignedParkingSpace() {
        return assignedParkingSpace;
    }

    public void setAssignedParkingSpace(String assignedParkingSpace) {
        this.assignedParkingSpace = assignedParkingSpace;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<String> getAuthorizedVisitors() {
        return authorizedVisitors;
    }

    public void setAuthorizedVisitors(List<String> authorizedVisitors) {
        this.authorizedVisitors = authorizedVisitors;
    }

    public Rental getCurrentRental() {
        return currentRental;
    }

    public void setCurrentRental(Rental currentRental) {
        this.currentRental = currentRental;
    }

    public String getBasicInfo() {
        return name + " - " + (isPermanent() ? "PERMANENT" : "ROTATING")
                + " - Apt: " + apartmentNumber;
    }
    
    public String getFullInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== RESIDENT INFORMATION ===\n");
        info.append("ID: ").append(residentID).append("\n");
        info.append("Name: ").append(name).append("\n");
        info.append("Apartment: ").append(apartmentNumber).append("\n");
        info.append("Type: ").append(userType).append("\n");
        info.append("Email: ").append(email).append("\n");
        info.append("Phone: ").append(phone).append("\n");
        info.append("Assigned Space: ").append(assignedParkingSpace != null ? assignedParkingSpace : "None").append("\n");

        if (currentRental != null) {
            info.append("Active Rental: Yes\n");
            info.append("Rented Space: ").append(currentRental.getSpaceId()).append("\n");
        }

        return info.toString();
    }
}