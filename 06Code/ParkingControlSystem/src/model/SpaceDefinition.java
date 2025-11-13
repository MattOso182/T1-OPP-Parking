package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

public class SpaceDefinition {
    private String spaceId;
    private String type;
    private boolean isOccupied;
    private boolean isAvailableForRent;

    public SpaceDefinition(String spaceId, String type, boolean isOccupied, boolean isAvailableForRent) {
        this.spaceId = spaceId;
        this.type = type;
        this.isOccupied = isOccupied;
        this.isAvailableForRent = isAvailableForRent;
    }

    public String getSpaceId() { return spaceId; }
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { this.isOccupied = occupied; }

    public boolean isAvailableForRent() { return isAvailableForRent; }
   

    @Override
    public String toString() {
        return spaceId + " (" + (isOccupied ? "Ocupado" : "Libre") + ")";
    }

    public String getType() {
    return type;
    }
}
