package ec.edu.espe.parkinglot.model;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

public class BuildingBlock {
    private String id;
    private String zoneId;
    private int floorCount;

    public BuildingBlock() {}

    public BuildingBlock(String id, String zoneId, int floorCount) {
        this.id = id;
        this.zoneId = zoneId;
        this.floorCount = floorCount;
    }

    public String getId() { 
        return id; 
    }
    public void setId(String id) { 
        this.id = id; 
    }
    public String getZoneId() {
        return zoneId; 
    }
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId; 
    }
    public int getFloorCount() {
        return floorCount; 
    }
    public void setFloorCount(int floorCount) { 
        this.floorCount = floorCount; 
    }
}

