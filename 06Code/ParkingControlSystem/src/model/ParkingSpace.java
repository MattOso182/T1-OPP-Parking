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
    
    public ParkingSpace(String spaceId, String location) {
        this.spaceId = spaceId;
        this.location = location;
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
    
    public boolean isOccupied() { return isOccupied; }
    public String getSpaceId() { return spaceId; }
    public String getAssignedTo() { return assignedTo; }
    public String getLocation() { return location; }
    
    public String getSpaceInfo() {
        return "Space: " + spaceId + 
               " - Location: " + location + 
               " - Status: " + (isOccupied ? "OCCUPIED by " + assignedTo : "AVAILABLE");
    }
}
