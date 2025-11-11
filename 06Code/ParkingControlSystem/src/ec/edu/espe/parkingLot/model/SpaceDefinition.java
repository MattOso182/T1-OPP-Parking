package ec.edu.espe.parkingLot.model;

/**
 *
 * @author T.A.P(The Art of Programming)
 */
public class SpaceDefinition {
    private String spaceId;
    private String type;
    private boolean isOccupied;
    private boolean isAvailableForRent;
    
    public SpaceDefinition() {}
    
    public SpaceDefinition(String spaceId, String type, boolean isOccupied, boolean isAvailableForRent) {
        this.spaceId = spaceId;
        this.type = type;
        this.isOccupied = isOccupied;
        this.isAvailableForRent = isAvailableForRent;
    }
    
    // Getters and Setters
    public String getSpaceId() { return spaceId; }
    public void setSpaceId(String spaceId) { this.spaceId = spaceId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }
    
    public boolean isAvailableForRent() { return isAvailableForRent; }
    public void setAvailableForRent(boolean availableForRent) { isAvailableForRent = availableForRent; }
}
