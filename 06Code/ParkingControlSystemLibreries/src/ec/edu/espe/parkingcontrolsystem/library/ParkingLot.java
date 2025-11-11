package ec.edu.espe.parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    
    private String name;                      
    private List<ParkingSpace> parkingSpaces; 

  
    public ParkingLot() {
        parkingSpaces = new ArrayList<>();
    }

    
    public ParkingLot(String name) {
        this.name = name;
        this.parkingSpaces = new ArrayList<>();
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    

  
    public void addParkingSpace(ParkingSpace space) {
        parkingSpaces.add(space);
    }

 
    public ParkingSpace findSpaceById(String spaceId) {
        for (ParkingSpace space : parkingSpaces) {
            if (space.getSpaceId().equalsIgnoreCase(spaceId)) {
                return space;
            }
        }
        return null;
    }

   
    public boolean assignSpace(String spaceId, String assignedTo, String residentType, String vehiclePlate) {
        ParkingSpace space = findSpaceById(spaceId);
        if (space != null && !space.isOccupied()) {
            space.assignSpace(assignedTo, residentType, vehiclePlate);
            return true;
        }
        return false;
    }

  
    public boolean freeSpace(String spaceId) {
        ParkingSpace space = findSpaceById(spaceId);
        if (space != null && space.isOccupied()) {
            space.freeSpace();
            return true;
        }
        return false;
    }

  
    public int countAvailableSpaces() {
        int count = 0;
        for (ParkingSpace space : parkingSpaces) {
            if (!space.isOccupied()) {
                count++;
            }
        }
        return count;
    }

 
    public int countOccupiedSpaces() {
        int count = 0;
        for (ParkingSpace space : parkingSpaces) {
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
        for (ParkingSpace space : parkingSpaces) {
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