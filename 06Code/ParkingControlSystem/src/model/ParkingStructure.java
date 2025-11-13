package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.List;

public class ParkingStructure {

    private String name;
    private int totalSpaces;
    private int availableForRent;
    private List<BuildingBlock> blocks;

    public ParkingStructure() {
    }

    public ParkingStructure(String name, int totalSpaces, int availableForRent, List<BuildingBlock> blocks) {
        this.name = name;
        this.totalSpaces = totalSpaces;
        this.availableForRent = availableForRent;
        this.blocks = blocks;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public void setTotalSpaces(int totalSpaces) {
        this.totalSpaces = totalSpaces;
    }

    public int getAvailableForRent() {
        return availableForRent;
    }

    public void setAvailableForRent(int availableForRent) {
        this.availableForRent = availableForRent;
    }

    public List<BuildingBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BuildingBlock> blocks) {
        this.blocks = blocks;
    }
}
