package model;

/**
 *
 * @author Gabriel
 */
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int totalSpaces;
    private int availableSpaces;
    private String lotId;
    private List<ParkingSpace> spaceList;
    
    public ParkingLot(String lotId, int totalSpaces) {
        this.lotId = lotId;
        this.totalSpaces = totalSpaces;
        this.availableSpaces = totalSpaces;
        this.spaceList = new ArrayList<>();
        initializeSpaces();
    }
    
    private void initializeSpaces() {
        for (int i = 1; i <= totalSpaces; i++) {
            spaceList.add(new ParkingSpace("SPACE-" + i, "Zone-" + ((i-1)/10 + 1)));
        }
    }
    
    public String getOccupancyReport() {
        return "Occupancy Report - Lot: " + lotId +
               "\nTotal spaces: " + totalSpaces +
               "\nAvailable spaces: " + availableSpaces +
               "\nOccupied spaces: " + (totalSpaces - availableSpaces) +
               "\nOccupancy rate: " + ((totalSpaces - availableSpaces) * 100 / totalSpaces) + "%";
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
    
    public void updateSpaceStatus(String spaceId, String status) {
        for (ParkingSpace space : spaceList) {
            if (space.getSpaceId().equals(spaceId)) {
                if (status.equals("AVAILABLE")) {
                    space.releaseSpace();
                } else if (status.equals("OCCUPIED")) {
                }
                calculateAvailableSpaces();
                break;
            }
        }
    }
    
    // Getters and Setters
    public int getTotalSpaces() { return totalSpaces; }
    public int getAvailableSpaces() { return availableSpaces; }
    public String getLotId() { return lotId; }
    public List<ParkingSpace> getSpaceList() { return spaceList; }
}
