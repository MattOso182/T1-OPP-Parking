package ec.edu.espe.parkingLot.model;

/**
 *
 * @author T.A.P(The Art of Programming)
 */

import java.util.List;

public class BuildingBlock {
     private String blockName;
    private String blockCode;
    private List<ParkingZone> sections;
    
    public BuildingBlock() {}
    
    public BuildingBlock(String blockName, String blockCode, List<ParkingZone> sections) {
        this.blockName = blockName;
        this.blockCode = blockCode;
        this.sections = sections;
    }
    
    // Getters and Setters
    public String getBlockName() { return blockName; }
    public void setBlockName(String blockName) { this.blockName = blockName; }
    
    public String getBlockCode() { return blockCode; }
    public void setBlockCode(String blockCode) { this.blockCode = blockCode; }
    
    public List<ParkingZone> getSections() { return sections; }
    public void setSections(List<ParkingZone> sections) { this.sections = sections; }
}
