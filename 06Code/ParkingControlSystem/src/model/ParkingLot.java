package model;

/**
 *
 * @author Gabriel
 */

import parkingcontrolsystem.library.ParkingLotLibrary;
import parkingcontrolsystem.library.ParkingSpaceLibrary;
import java.util.*;

public class ParkingLot {

    private String lotId;
    private ParkingLotLibrary libraryParkingLot;

    public ParkingLot(String lotId) {
        this.lotId = lotId;
        this.libraryParkingLot = new ParkingLotLibrary(lotId);
        loadAndMergeJsonData();
    }

    
    private void loadAndMergeJsonData() {
        try {
            JsonDataManager jsonManager = new JsonDataManager();

            
            ParkingStructure structure = jsonManager.loadParkingData();
            if (structure == null) {
                System.out.println(" No se pudo cargar parking_data.json");
                return;
            }

           
            List<Resident> residents = jsonManager.loadResidentsData();
            if (residents == null) {
                System.out.println(" No se pudo cargar residents_data.json");
                return;
            }

           
            Set<String> occupiedSpaces = new HashSet<>();
            for (Resident r : residents) {
                if (r.getAssignedParkingSpace() != null && !r.getAssignedParkingSpace().isEmpty()) {
                    occupiedSpaces.add(r.getAssignedParkingSpace());
                }
            }

            
            for (BuildingBlock block : structure.getBlocks()) {
                for (ParkingZone zone : block.getSections()) {
                    for (SpaceDefinition spaceDef : zone.getSpaces()) {

                        boolean ocupado = occupiedSpaces.contains(spaceDef.getSpaceId());
                        spaceDef.setOccupied(ocupado);

                        ParkingSpaceLibrary space = new ParkingSpaceLibrary(
                                spaceDef.getSpaceId(),
                                ocupado,
                                ocupado ? "OCCUPIED" : "AVAILABLE",
                                spaceDef.getType(),
                                ocupado ? "ASSIGNED_TO_RESIDENT" : "NONE"
                        );

                        libraryParkingLot.addParkingSpace(space);
                    }
                }
            }

            System.out.println("✅ Datos de parqueadero y residentes combinados correctamente.");
            System.out.println("🔹 Total de espacios cargados: " + libraryParkingLot.getParkingSpaces().size());

        } catch (Exception e) {
            System.out.println("⚠️ Error al combinar JSON: " + e.getMessage());
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
                System.out.println("💾 Estado actualizado guardado correctamente en JSON.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error guardando JSON: " + e.getMessage());
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

   
    public void showSpacesStatus() {
        System.out.println("\n=== ESTADO ACTUAL DE LOS ESPACIOS ===");
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            System.out.println("Espacio: " + space.getSpaceId()
                    + " | Estado: " + (space.isOccupied() ? "Ocupado" : "Libre"));
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

    public void updateSpaceStatus(String spaceId, String status) {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (space.getSpaceId().equals(spaceId)) {
                if (status.equalsIgnoreCase("AVAILABLE")) {
                    space.freeSpace();
                } else if (status.equalsIgnoreCase("OCCUPIED")) {
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
