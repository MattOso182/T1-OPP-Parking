package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */
import org.bson.Document;

public class Block {

    private int id;
    private int zoneId;
    private int floorCount;

    public Block() {
    }

    public Block(int id, int zoneId, int floorCount) {
        this.id = id;
        this.zoneId = zoneId;
        this.floorCount = floorCount;
    }

    public Block(int zoneId, int floorCount) {
        this.zoneId = zoneId;
        this.floorCount = floorCount;
    }

    public static Block fromDocument(Document doc) {
        return new Block(
                doc.getInteger("id"),
                doc.getInteger("zoneId"),
                doc.getInteger("floorCount")
        );
    }

    public Document toDocument() {
        return new Document("id", id)
                .append("zoneId", zoneId)
                .append("floorCount", floorCount);
    }

    public int getId() {
        return id;
    }

    public int getZoneId() {
        return zoneId;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public void setFloorCount(int floorCount) {
        this.floorCount = floorCount;
    }
}

