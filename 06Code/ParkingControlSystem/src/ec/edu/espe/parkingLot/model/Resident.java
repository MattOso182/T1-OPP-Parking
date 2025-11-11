package ec.edu.espe.parkingLot.model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.ArrayList;
import java.util.List;

public class Resident {

    private String residentID;
    private String name;
    private String apartmentNumber;
    private String email;
    private String phone;
    private UserType userType;
    private String assignedParkingSpace; 
    private List<Vehicle> vehicles;
    private List<String> authorizedVisitors;
    private Rental currentRental; 

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
        this.userType = UserType.WITH_PARKING;
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
        this.userType = UserType.ROTATING;
        this.assignedParkingSpace = null;
        this.vehicles = new ArrayList<>();
        this.authorizedVisitors = new ArrayList<>();
        this.currentRental = null;
    }

    public boolean hasActiveRental() {
        return currentRental != null && currentRental.isActive();
    }

    public String getRentedSpace() {
        return hasActiveRental() ? currentRental.getSpaceId() : null;
    }

    public void setCurrentRental(Rental rental) {
        this.currentRental = rental;
    }

    public Rental getCurrentRental() {
        return currentRental;
    }

    public boolean addVehicle(Vehicle vehicle) {
        for (Vehicle v : vehicles) {
            if (v.getPlate().equals(vehicle.getPlate())) {
                System.out.println("Vehicle with plate " + vehicle.getPlate() + " already exists");
                return false;
            }
        }
        vehicles.add(vehicle);
        System.out.println("Vehicle " + vehicle.getPlate() + " added successfully");
        return true;
    }

    public boolean removeVehicle(String plate) {
        boolean removed = vehicles.removeIf(vehicle -> vehicle.getPlate().equals(plate));
        if (removed) {
            System.out.println("Vehicle " + plate + " removed successfully");
        } else {
            System.out.println("Vehicle " + plate + " not found");
        }
        return removed;
    }

    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPlate().equals(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    public boolean authorizeVisitor(String visitorID) {
        if (!authorizedVisitors.contains(visitorID)) {
            authorizedVisitors.add(visitorID);
            System.out.println("Visitor " + visitorID + " authorized successfully");
            return true;
        }
        System.out.println("Visitor " + visitorID + " already authorized");
        return false;
    }

    public boolean removeAuthorizedVisitor(String visitorID) {
        boolean removed = authorizedVisitors.remove(visitorID);
        if (removed) {
            System.out.println("Visitor " + visitorID + " authorization removed");
        } else {
            System.out.println("Visitor " + visitorID + " not found in authorized list");
        }
        return removed;
    }

    public String getResidentID() {
        return residentID;
    }

    public String getName() {
        return name;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getAssignedParkingSpace() {
        return assignedParkingSpace;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<String> getAuthorizedVisitors() {
        return authorizedVisitors;
    }

    public String getCurrentParkingSpace() {
        if (userType == UserType.WITH_PARKING) {
            return assignedParkingSpace;
        } else if (hasActiveRental()) {
            return currentRental.getSpaceId();
        }
        return null;
    }

    public String getResidentInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== RESIDENT INFORMATION ===\n");
        info.append("ID: ").append(residentID).append("\n");
        info.append("Name: ").append(name).append("\n");
        info.append("Apartment: ").append(apartmentNumber).append("\n");
        info.append("Type: ").append(userType).append("\n");
        info.append("Email: ").append(email).append("\n");
        info.append("Phone: ").append(phone).append("\n");

        if (userType == UserType.WITH_PARKING) {
            info.append("Assigned Space: ").append(assignedParkingSpace).append("\n");
        } else {
            info.append("Active Rental: ").append(hasActiveRental() ? "YES" : "NO").append("\n");
            if (hasActiveRental()) {
                info.append("Rented Space: ").append(currentRental.getSpaceId()).append("\n");
                info.append("Rental End: ").append(currentRental.getEndDate()).append("\n");
            }
        }

        info.append("Vehicles: ").append(vehicles.size()).append("\n");
        for (Vehicle vehicle : vehicles) {
            info.append("  - ").append(vehicle.getPlate()).append(" (").append(vehicle.getModel()).append(")\n");
        }

        info.append("Authorized Visitors: ").append(authorizedVisitors.size()).append("\n");

        return info.toString();
    }
}
