package ec.edu.espe.parkinglotgui.model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoConnectionResidents;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class VehicleDAO {

    private final MongoCollection<Document> collection;

    public VehicleDAO() {
        MongoDatabase db = MongoConnectionResidents.getDatabase();
        this.collection = db.getCollection("Vehicles");
    }

    public List<Document> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    public void addVehicle(String residentId, Document vehicle) {
        collection.updateOne(
            new Document("resident.residentId", residentId),
            new Document("$push", new Document("resident.vehicles", vehicle))
        );
    }

    public void deleteVehicle(String residentId, String plate) {
        collection.updateOne(
            new Document("resident.residentId", residentId),
            new Document("$pull",
                new Document("resident.vehicles",
                    new Document("plate", plate)))
        );
    }
}
