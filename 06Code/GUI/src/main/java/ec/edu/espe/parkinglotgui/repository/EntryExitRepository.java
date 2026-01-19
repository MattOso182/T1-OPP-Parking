package ec.edu.espe.parkinglotgui.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

public class EntryExitRepository {
    private MongoCollection<Document> collection;

    public EntryExitRepository() {
        MongoDatabase db = MongoDBConnection.getConnection();
        if (db != null) {
            this.collection = db.getCollection("Entrances");
        }
    }

    public List<Document> findAll() {
        List<Document> records = new ArrayList<>();
        if (collection != null) {
            for (Document doc : collection.find()) {
                records.add(doc);
            }
        }
        return records;
    }
public Document findParkedVehicle(String licensePlate) {
    return collection.find(new Document("licensePlate", licensePlate)
            .append("status", "PARKED")).first();
}

public void saveEntry(Document entry) {
    collection.insertOne(entry);
}

public void updateStatus(String licensePlate, String newStatus, Date exitTime) {
    collection.updateOne(
        new Document("licensePlate", licensePlate).append("status", "PARKED"),
        new Document("$set", new Document("status", newStatus).append("exitTime", exitTime))
    );
}

public boolean isVehicleParked(String licensePlate) {
    return findParkedVehicle(licensePlate) != null;
}

}