package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import ec.edu.espe.parkinglotgui.model.Zone;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class ZoneController {

    private final MongoCollection<Document> collection;

    public ZoneController() {
        collection = MongoDBConnection
                .getConnection()
                .getCollection("Zones");
    }

    private int getNextId() {
        Document last = collection.find()
                .sort(new Document("id", -1))
                .first();

        return (last == null) ? 1 : last.getInteger("id") + 1;
    }

    public boolean createZone(String type, int capacity) {
        if (capacity <= 0) return false;

        int id = getNextId();

        Document zone = new Document()
                .append("id", id)
                .append("type", type)
                .append("capacity", capacity);

        collection.insertOne(zone);
        return true;
    }

    public List<Zone> getAllZones() {
        List<Zone> zones = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            zones.add(new Zone(
                    doc.getInteger("id"),
                    doc.getString("type"),
                    doc.getInteger("capacity")
            ));
        }
        cursor.close();
        return zones;
    }

    public boolean updateZone(int id, String type, int capacity) {
        Document update = new Document("$set",
                new Document("type", type)
                        .append("capacity", capacity)
        );

        return collection.updateOne(
                new Document("id", id),
                update
        ).getModifiedCount() > 0;
    }

    public boolean deleteZone(int id) {
        return collection.deleteOne(
                new Document("id", id)
        ).getDeletedCount() > 0;
    }
}