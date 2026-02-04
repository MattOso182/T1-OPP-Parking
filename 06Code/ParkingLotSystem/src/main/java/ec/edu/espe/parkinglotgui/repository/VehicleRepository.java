package ec.edu.espe.parkinglotgui.repository;

import com.mongodb.client.MongoCollection;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.ArrayList;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class VehicleRepository {
    private final MongoCollection<Document> collection;

    public VehicleRepository() {
        this.collection = MongoDBConnection.getConnection().getCollection("Vehicles");
    }

    public ArrayList<Vehicle> findAll() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        for (Document doc : collection.find()) {
            Vehicle v = new Vehicle();
            v.setOwnerId(doc.getString("ownerId"));
            v.setOwnerName(doc.getString("ownerName"));
            v.setPlate(doc.getString("plate"));
            v.setColor(doc.getString("color"));
            v.setModel(doc.getString("model"));
            v.setParked(doc.getBoolean("parked", false));
            vehicles.add(v);
        }
        return vehicles;
    }
    
    public void saveVehicle(String ownerId, String ownerName, String plate, String color, String model, boolean parked) {
        Document doc = new Document()
                .append("ownerId", ownerId)
                .append("ownerName", ownerName)
                .append("plate", plate)
                .append("color", color)
                .append("model", model)
                .append("parked", parked);

        collection.insertOne(doc);
    }
}