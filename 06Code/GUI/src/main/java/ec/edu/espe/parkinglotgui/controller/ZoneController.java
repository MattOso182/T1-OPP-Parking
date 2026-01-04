package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import ec.edu.espe.parkinglotgui.model.Zone;
import ec.edu.espe.parkinglotgui.utils.MongoConnectionZones;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ZoneController {

    private static final String COLLECTION = "Zones";
    private final MongoCollection<Document> collection;
    private final MongoConnectionZones mongo;

    public ZoneController() {
        mongo = new MongoConnectionZones();
        collection = mongo.getCollection(COLLECTION);
    }

    public int getNextId() {
        Document last = collection.find()
                .sort(new Document("id", -1))
                .first();

        return (last == null) ? 1 : last.getInteger("id") + 1;
    }

    public boolean createZone(String type, int capacity) {
        if (capacity <= 0) return false;

        int id = getNextId();
        Zone zone = new Zone(id, type, capacity);
        collection.insertOne(zone.toDocument());
        return true;
    }

    public List<Zone> getAllZones() {
        List<Zone> zones = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();

        while (cursor.hasNext()) {
            zones.add(Zone.fromDocument(cursor.next()));
        }
        return zones;
    }

    public boolean updateZone(int id, String type, int capacity) {
        Document update = new Document("$set",
                new Document("type", type)
                        .append("capacity", capacity));

        return collection.updateOne(
                new Document("id", id),
                update
        ).getModifiedCount() > 0;
    }

    public boolean deleteZone(int id) {
        return collection.deleteOne(new Document("id", id))
                .getDeletedCount() > 0;
    }
}

