package ec.edu.espe.parkingLot.model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class Vehicle {
    private String plate;
    private String color;
    private String model;
    private boolean isParked;
    private String ownerId;
    
    public Vehicle(String plate, String color, String model, String ownerId) {
        this.plate = plate;
        this.color = color;
        this.model = model;
        this.ownerId = ownerId;
        this.isParked = false;
    }
    
    public boolean registerVehicle() {
        if (validatePlate()) {
            System.out.println("Vehicle " + plate + " registered successfully");
            return true;
        }
        System.out.println("Vehicle registration failed: invalid plate");
        return false;
    }
    
    public void updateOwner(String newOwnerId) {
        this.ownerId = newOwnerId;
        System.out.println("Vehicle " + plate + " ownership updated to: " + newOwnerId);
    }
    
    public void assignSpot(String spotId) {
        this.isParked = true;
        System.out.println("Vehicle " + plate + " assigned to spot: " + spotId);
    }
    
    public void releaseSpot() {
        this.isParked = false;
        System.out.println("Vehicle " + plate + " released from spot");
    }
    
    public boolean validatePlate() {
        boolean isValid = plate != null && plate.length() >= 6 && plate.matches(".*[A-Z0-9].*");
        System.out.println("Plate validation for " + plate + ": " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }
    
    // Getters and Setters
    public String getPlate() { return plate; }
    public String getColor() { return color; }
    public String getModel() { return model; }
    public boolean isParked() { return isParked; }
    public String getOwnerId() { return ownerId; }
    
    public String getVehicleInfo() {
        return "Plate: " + plate +
               "\nColor: " + color +
               "\nModel: " + model +
               "\nOwner ID: " + ownerId +
               "\nStatus: " + (isParked ? "PARKED" : "NOT PARKED");
    }
}