package parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import java.util.ArrayList;
import java.util.List;

public class ParkingLotLibrary {

    
    private String name;                      
    private List<ParkingSpaceLibrary> parkingSpaces; 

  
    public ParkingLotLibrary() {
        parkingSpaces = new ArrayList<>();
    }

    
    public ParkingLotLibrary(String name) {
        this.name = name;
        this.parkingSpaces = new ArrayList<>();
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingSpaceLibrary> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpaceLibrary> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    

  
    public void addParkingSpace(ParkingSpaceLibrary space) {
        parkingSpaces.add(space);
    }

 
    public ParkingSpaceLibrary findSpaceById(String spaceId) {
        for (ParkingSpaceLibrary space : parkingSpaces) {
            if (space.getSpaceId().equalsIgnoreCase(spaceId)) {
                return space;
            }
        }
        return null;
    }

   
    public boolean assignSpace(String spaceId, String assignedTo, String residentType, String vehiclePlate) {
        ParkingSpaceLibrary space = findSpaceById(spaceId);
        if (space != null && !space.isOccupied()) {
            space.assignSpace(assignedTo, residentType, vehiclePlate);
            return true;
        }
        return false;
    }

  
    public boolean freeSpace(String spaceId) {
        ParkingSpaceLibrary space = findSpaceById(spaceId);
        if (space != null && space.isOccupied()) {
            space.freeSpace();
            return true;
        }
        return false;
    }

  
    public int countAvailableSpaces() {
        int count = 0;
        for (ParkingSpaceLibrary space : parkingSpaces) {
            if (!space.isOccupied()) {
                count++;
            }
        }
        return count;
    }

 
    public int countOccupiedSpaces() {
        int count = 0;
        for (ParkingSpaceLibrary space : parkingSpaces) {
            if (space.isOccupied()) {
                count++;
            }
        }
        return count;
    }

  
    public String getParkingLotStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADO DEL PARQUEADERO: ").append(name).append(" ===\n");
        sb.append("Total de espacios: ").append(parkingSpaces.size()).append("\n");
        sb.append("Ocupados: ").append(countOccupiedSpaces()).append("\n");
        sb.append("Disponibles: ").append(countAvailableSpaces()).append("\n\n");
        for (ParkingSpaceLibrary space : parkingSpaces) {
            sb.append(space.getSpaceInfo()).append("\n-----------------------\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "name='" + name + '\'' +
                ", totalSpaces=" + parkingSpaces.size() +
                '}';
    }
}