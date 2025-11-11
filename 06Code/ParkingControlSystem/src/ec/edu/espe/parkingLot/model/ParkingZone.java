package ec.edu.espe.parkingLot.model;

/**
 *
 * @author T.A.P(The Art of Programming)
 */

import java.util.List;

public class ParkingZone {
     private String section;
    private List<SpaceDefinition> spaces;
    
    public ParkingZone() {}
    
    public ParkingZone(String section, List<SpaceDefinition> spaces) {
        this.section = section;
        this.spaces = spaces;
    }
    
    // Getters and Setters
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    
    public List<SpaceDefinition> getSpaces() { return spaces; }
    public void setSpaces(List<SpaceDefinition> spaces) { this.spaces = spaces; }
}
