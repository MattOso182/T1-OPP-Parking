package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */
import org.bson.Document;

public class Zone {

    private int id;
    private String type; 
    private int capacity;
    private int occupied;

    public Zone() {
        this.occupied = 0;
    }

    public Zone(int id, String type, int capacity) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.occupied = 0;
    }

    public static Zone fromDocument(Document doc) {
        Zone zone = new Zone();
        zone.setId(doc.getInteger("id"));
        zone.setType(doc.getString("type"));
        zone.setCapacity(doc.getInteger("capacity"));
        zone.setOccupied(doc.getInteger("occupied", 0));
        return zone;
    }

    public Document toDocument() {
        return new Document("id", id)
                .append("type", type)
                .append("capacity", capacity)
                .append("occupied", occupied);
    }

    public boolean hasAvailableSpace() {
        return occupied < capacity;
    }

    public void occupy() {
        if (hasAvailableSpace()) {
            occupied++;
        }
    }

    public void release() {
        if (occupied > 0) {
            occupied--;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    
}
