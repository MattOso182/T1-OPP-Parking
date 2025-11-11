package model;

/**
 *
 * @author Gabriel
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private int totalSpaces;
    private int availableSpaces;
    private String lotId;
    private List<ParkingSpace> spaceList;
    
    public ParkingLot(String lotId) {
        this.lotId = lotId;
        this.spaceList = new ArrayList<>();
        loadFromJson();
    }
    
    private void loadFromJson() {
        try {
            JsonDataManager jsonManager = new JsonDataManager();
            ParkingStructure structure = jsonManager.loadParkingData();
            
            if (structure != null) {
                this.totalSpaces = structure.getTotalSpaces();
                this.availableSpaces = 0;
                
                for (BuildingBlock block : structure.getBlocks()) {
                    for (ParkingZone zone : block.getSections()) {
                        for (SpaceDefinition spaceDef : zone.getSpaces()) {
                            String location = block.getBlockName() + " - " + zone.getSection();
                            ParkingSpace space = new ParkingSpace(
                                spaceDef.getSpaceId(),
                                location,
                                spaceDef.getType(),
                                spaceDef.isAvailableForRent()
                            );

                            if (spaceDef.isOccupied()) {
                                space.assignSpace("PREVIOUSLY_OCCUPIED");
                            }
                            
                            spaceList.add(space);
                            
                            if (!spaceDef.isOccupied()) {
                                availableSpaces++;
                            }
                        }
                    }
                }
                System.out.println("Successfully loaded " + spaceList.size() + " spaces from JSON");
            } else {
                System.out.println("Failed to load parking data from JSON");
            }
        } catch (Exception e) {
            System.out.println("Error loading from JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void saveToJson() {
        try {
            JsonDataManager jsonManager = new JsonDataManager();
            
            ParkingStructure currentStructure = jsonManager.loadParkingData();
            
            if (currentStructure != null) {
                updateStructureWithCurrentStatus(currentStructure);
                
                jsonManager.saveParkingData(currentStructure.getBlocks());
                System.out.println("Parking data saved successfully to JSON");
            }
        } catch (Exception e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void updateStructureWithCurrentStatus(ParkingStructure structure) {
        Map<String, ParkingSpace> currentSpacesMap = new HashMap<>();
        for (ParkingSpace space : spaceList) {
            currentSpacesMap.put(space.getSpaceId(), space);
        }
        
        for (BuildingBlock block : structure.getBlocks()) {
            for (ParkingZone zone : block.getSections()) {
                for (SpaceDefinition spaceDef : zone.getSpaces()) {
                    ParkingSpace currentSpace = currentSpacesMap.get(spaceDef.getSpaceId());
                    if (currentSpace != null) {
                        spaceDef.setOccupied(currentSpace.isOccupied());
                    }
                }
            }
        }
    }
    
    public String getOccupancyReport() {
        int occupied = totalSpaces - availableSpaces;
        double occupancyRate = totalSpaces > 0 ? (occupied * 100.0 / totalSpaces) : 0;
        
        return "Occupancy Report - Lot: " + lotId +
               "\nTotal spaces: " + totalSpaces +
               "\nAvailable spaces: " + availableSpaces +
               "\nOccupied spaces: " + occupied +
               "\nOccupancy rate: " + String.format("%.1f", occupancyRate) + "%";
    }
    
    public int calculateAvailableSpaces() {
        availableSpaces = 0;
        for (ParkingSpace space : spaceList) {
            if (!space.isOccupied()) {
                availableSpaces++;
            }
        }
        return availableSpaces;
    }
    
    public ParkingSpace findAvailableSpace() {
        for (ParkingSpace space : spaceList) {
            if (!space.isOccupied()) {
                return space;
            }
        }
        return null;
    }
    
    public ParkingSpace findAvailableSpaceByType(String type) {
        for (ParkingSpace space : spaceList) {
            if (!space.isOccupied() && space.getType().equals(type)) {
                return space;
            }
        }
        return null;
    }
    
    public void updateSpaceStatus(String spaceId, String status) {
        for (ParkingSpace space : spaceList) {
            if (space.getSpaceId().equals(spaceId)) {
                if (status.equals("AVAILABLE")) {
                    space.releaseSpace();
                }
                calculateAvailableSpaces();
                break;
            }
        }
        saveToJson(); 
    }
    
    // Getters and Setters
    public int getTotalSpaces() { return totalSpaces; }
    public int getAvailableSpaces() { return availableSpaces; }
    public String getLotId() { return lotId; }
    public List<ParkingSpace> getSpaceList() { return spaceList; }
}
