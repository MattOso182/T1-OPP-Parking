package model;

/**
 *
 * @author Gabriel
 */
import parkingcontrolsystem.library.ParkingLotLibrary;
import parkingcontrolsystem.library.ParkingSpaceLibrary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private String lotId;
    private ParkingLotLibrary libraryParkingLot;

    public ParkingLot(String lotId) {
        this.lotId = lotId;
        this.libraryParkingLot = new ParkingLotLibrary(lotId);
        loadFromJson();
    }

    private void loadFromJson() {
        try {
            JsonDataManager jsonManager = new JsonDataManager();
            ParkingStructure structure = jsonManager.loadParkingData();

            if (structure != null) {
                for (BuildingBlock block : structure.getBlocks()) {
                    for (ParkingZone zone : block.getSections()) {
                        for (SpaceDefinition spaceDef : zone.getSpaces()) {
                            ParkingSpaceLibrary space = new ParkingSpaceLibrary(
                                    spaceDef.getSpaceId(),
                                    spaceDef.isOccupied(),
                                    spaceDef.isOccupied() ? "PREVIOUSLY_OCCUPIED" : null,
                                    "Resident",
                                    spaceDef.isOccupied() ? "PREVIOUS_VEHICLE" : null
                            );

                            libraryParkingLot.addParkingSpace(space);
                        }
                    }
                }
                System.out.println("Successfully loaded " + libraryParkingLot.getParkingSpaces().size() + " spaces from JSON");
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
        Map<String, ParkingSpaceLibrary> currentSpacesMap = new HashMap<>();
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            currentSpacesMap.put(space.getSpaceId(), space);
        }

        for (BuildingBlock block : structure.getBlocks()) {
            for (ParkingZone zone : block.getSections()) {
                for (SpaceDefinition spaceDef : zone.getSpaces()) {
                    ParkingSpaceLibrary currentSpace = currentSpacesMap.get(spaceDef.getSpaceId());
                    if (currentSpace != null) {
                        spaceDef.setOccupied(currentSpace.isOccupied());
                    }
                }
            }
        }
    }

    public String getOccupancyReport() {
        return libraryParkingLot.getParkingLotStatus();
    }

    public int calculateAvailableSpaces() {
        return libraryParkingLot.countAvailableSpaces();
    }

    public ParkingSpaceLibrary findAvailableSpace() {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (!space.isOccupied()) {
                return space;
            }
        }
        return null;
    }

    public ParkingSpaceLibrary findAvailableSpaceByType(String type) {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (!space.isOccupied() && "Resident".equals(type)) { // ✅ Adaptado para tu librería
                return space;
            }
        }
        return null;
    }

    public void updateSpaceStatus(String spaceId, String status) {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (space.getSpaceId().equals(spaceId)) {
                if (status.equals("AVAILABLE")) {
                    space.freeSpace();
                } else if (status.equals("OCCUPIED")) {
                    space.assignSpace("Unknown", "Visitor", "TEMP_PLATE");
                }
                break;
            }
        }
        saveToJson();
    }

    public int getTotalSpaces() {
        return libraryParkingLot.getParkingSpaces().size();
    }

    public int getAvailableSpaces() {
        return libraryParkingLot.countAvailableSpaces();
    }

    public String getLotId() {
        return lotId;
    }

    public List<ParkingSpaceLibrary> getSpaceList() {
        return libraryParkingLot.getParkingSpaces();
    }

    public ParkingLotLibrary getLibraryParkingLot() {
        return libraryParkingLot;
    }
}
