package ec.edu.espe.parkinglotgui.model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
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
    public void deleteByOwnerAndPlate(String ownerId, String plate) {
        collection.deleteOne(
                new Document("ownerId", ownerId)
                        .append("plate", plate)
        );
    }

    public void updateVehicle(String ownerId, String oldPlate, Document newData) {
        collection.updateOne(
                new Document("ownerId", ownerId).append("plate", oldPlate),
                new Document("$set", newData)
        );
    }

    public List<Document> findAllSortedByOwner() {
        List<Document> list = new ArrayList<>();
        collection.find().sort(Sorts.ascending("ownerId")).into(list);
        return list;
    }
    public void insert(Document vehicle) {
        collection.insertOne(vehicle);
    }
}