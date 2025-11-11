package model;

/**
 *
 * @author Gabriel
 */
public class ParkingSpace {
    private String spaceId;
    private boolean isOccupied;
    private String assignedTo;
    private String location;
    private String type;
    private boolean isAvailableForRent;
    
    public ParkingSpace(String spaceId, String location, String type, boolean isAvailableForRent) {
        this.spaceId = spaceId;
        this.location = location;
        this.type = type;
        this.isAvailableForRent = isAvailableForRent;
        this.isOccupied = false;
        this.assignedTo = "";
    }
    
    public void assignSpace(String vehiclePlate) {
        this.isOccupied = true;
        this.assignedTo = vehiclePlate;
    }
    
    public void releaseSpace() {
        this.isOccupied = false;
        this.assignedTo = "";
    }
    
    // Getters and Setters
    public boolean isOccupied() { return isOccupied; }
    public String getSpaceId() { return spaceId; }
    public String getAssignedTo() { return assignedTo; }
    public String getLocation() { return location; }
    public String getType() { return type; }
    public boolean isAvailableForRent() { return isAvailableForRent; }
    
    public String getSpaceInfo() {
        return "Space: " + spaceId + 
               " - Location: " + location + 
               " - Type: " + type +
               " - Status: " + (isOccupied ? "OCCUPIED by " + assignedTo : "AVAILABLE") +
               " - For Rent: " + (isAvailableForRent ? "YES" : "NO");
    }
}
