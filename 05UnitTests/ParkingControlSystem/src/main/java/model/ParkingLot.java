package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

import parkingcontrolsystem.library.ParkingLotLibrary;
import parkingcontrolsystem.library.ParkingSpaceLibrary;
import java.util.*;
import utils.*;

public class ParkingLot {

    private String lotId;
    private ParkingLotLibrary libraryParkingLot;
    private Map<String, SpaceDefinition> spaceDefinitionsMap; 

    public ParkingLot(String lotId) {
        this.lotId = lotId;
        this.libraryParkingLot = new ParkingLotLibrary(lotId);
        this.spaceDefinitionsMap = new HashMap<>(); 
        loadAndMergeJsonData();
    }

    
    private void loadAndMergeJsonData() {
        try {
            JsonDataManager jsonManager = new JsonDataManager();

            
            ParkingStructure structure = jsonManager.loadParkingData();
            if (structure == null) {
                System.out.println("No se pudo cargar parking_data.json");
                return;
            }

           
            List<Resident> residents = jsonManager.loadResidentsData();
            if (residents == null) {
                System.out.println("No se pudo cargar residents_data.json");
                return;
            }

           
            Set<String> occupiedSpaces = new HashSet<>();
            for (Resident r : residents) {
                if (r.getAssignedParkingSpace() != null && !r.getAssignedParkingSpace().isEmpty()) {
                    occupiedSpaces.add(r.getAssignedParkingSpace());
                }
            }

            spaceDefinitionsMap.clear();
            
            for (BuildingBlock block : structure.getBlocks()) {
                for (ParkingZone zone : block.getSections()) {
                    for (SpaceDefinition spaceDef : zone.getSpaces()) {

                        boolean ocupado = occupiedSpaces.contains(spaceDef.getSpaceId());
                        spaceDef.setOccupied(ocupado);

                        spaceDefinitionsMap.put(spaceDef.getSpaceId(), spaceDef);

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

            System.out.println("Datos de parqueadero y residentes combinados correctamente.");
            System.out.println("Total de espacios cargados: " + libraryParkingLot.getParkingSpaces().size());

        } catch (Exception e) {
            System.out.println("Error al combinar JSON: " + e.getMessage());
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
            }
        } catch (Exception e) {
            System.out.println("Error guardando JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

 
    private void updateStructureWithCurrentStatus(ParkingStructure structure) {
        for (BuildingBlock block : structure.getBlocks()) {
            for (ParkingZone zone : block.getSections()) {
                for (SpaceDefinition spaceDef : zone.getSpaces()) {
                    if (spaceDefinitionsMap.containsKey(spaceDef.getSpaceId())) {
                        SpaceDefinition currentDef = spaceDefinitionsMap.get(spaceDef.getSpaceId());
                        spaceDef.setOccupied(currentDef.isOccupied());
                    }
                }
            }
        }
    }

    public void syncSpaceStatus(String spaceId, boolean occupied) {
        for (ParkingSpaceLibrary librarySpace : libraryParkingLot.getParkingSpaces()) {
            if (librarySpace.getSpaceId().equals(spaceId)) {
                if (occupied && !librarySpace.isOccupied()) {
                    librarySpace.assignSpace("System", "Resident", "TEMP");
                } else if (!occupied && librarySpace.isOccupied()) {
                    librarySpace.freeSpace();
                }
                break;
            }
        }
        
        if (spaceDefinitionsMap.containsKey(spaceId)) {
            SpaceDefinition spaceDef = spaceDefinitionsMap.get(spaceId);
            spaceDef.setOccupied(occupied);
        }
        
        saveToJson();
    }
    
    public boolean assignSpaceToVehicle(String spaceId, String vehiclePlate, String userType) {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (space.getSpaceId().equals(spaceId) && !space.isOccupied()) {
                space.assignSpace("Auto asignado", userType, vehiclePlate);
                
                syncSpaceStatus(spaceId, true);
                
                System.out.println("Espacio " + spaceId + " asignado a " + vehiclePlate);
                return true;
            }
        }
        System.out.println("No se pudo asignar espacio " + spaceId);
        return false;
    }
    
    public boolean freeSpaceAndSync(String spaceId) {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (space.getSpaceId().equals(spaceId) && space.isOccupied()) {
                space.freeSpace();
                
                syncSpaceStatus(spaceId, false);
                
                System.out.println("Espacio " + spaceId + " liberado");
                return true;
            }
        }
        return false;
    }

   
    public void showSpacesStatus() {
        System.out.println("\n=== ESTADO ACTUAL DE LOS ESPACIOS ===");
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            String status = space.isOccupied() ? "Ocupado por " + space.getVehiclePlate() : "Libre";
            System.out.println("Espacio: " + space.getSpaceId() + " | Estado: " + status);
        }
    }

    public void showDetailedSpacesStatus() {
        System.out.println("\n=== ESTADO DETALLADO DE ESPACIOS ===");
        int ocupados = 0;
        int disponibles = 0;
        
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (space.isOccupied()) {
                ocupados++;
                System.out.println(space.getSpaceId() + " - OCUPADO por: " + 
                                 (space.getVehiclePlate() != null ? space.getVehiclePlate() : "Desconocido"));
            } else {
                disponibles++;
                System.out.println(space.getSpaceId() + " - DISPONIBLE");
            }
        }
        
        System.out.println("=== RESUMEN: " + ocupados + " ocupados, " + disponibles + " disponibles ===");
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
        if (status.equalsIgnoreCase("AVAILABLE")) {
            freeSpaceAndSync(spaceId);
        } else if (status.equalsIgnoreCase("OCCUPIED")) {
            assignSpaceToVehicle(spaceId, "TEMP_PLATE", "Visitor");
        }
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
    public ParkingSpaceLibrary findSpaceByVehicle(String vehiclePlate) {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (space.isOccupied() && vehiclePlate.equals(space.getVehiclePlate())) {
                return space;
            }
        }
        return null;
    }
    public boolean spaceExists(String spaceId) {
        for (ParkingSpaceLibrary space : libraryParkingLot.getParkingSpaces()) {
            if (space.getSpaceId().equals(spaceId)) {
                return true;
            }
        }
        return false;
    }
}
