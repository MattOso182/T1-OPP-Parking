package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.util.ArrayList;
import org.bson.Document;
import java.util.Date;
import java.util.regex.Pattern;

public class VehicleEntryController {

    private static final String ENTRANCES_COLLECTION = "Entrances";
    private final MongoCollection<Document> entrancesCollection;

    private static final Pattern LICENSE_PLATE_PATTERN =
            Pattern.compile("^[A-Z]{3}-\\d{4}$");

    public VehicleEntryController() {
        entrancesCollection = MongoDBConnection
                .getConnection()
                .getCollection(ENTRANCES_COLLECTION);
    }

    private boolean validateLicensePlateFormat(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }

    public boolean registerEntry(String licensePlate, String assignedSpace) {
        if (!validateLicensePlateFormat(licensePlate)) return false;
        if (isVehicleParked(licensePlate)) return false;

        ParkingSpaceController spaceController = new ParkingSpaceController();
        if (!spaceController.updateSpaceOccupation(assignedSpace, true)) {
            return false;
        }

        Document entry = new Document("licensePlate", licensePlate)
                .append("spaceId", assignedSpace)
                .append("entryTime", new Date())
                .append("status", "PARKED");

        entrancesCollection.insertOne(entry);
        return true;
    }

    public boolean isVehicleParked(String licensePlate) {
        return entrancesCollection.find(
                new Document("licensePlate", licensePlate)
                        .append("status", "PARKED")
        ).first() != null;
    }

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        MongoCollection<Document> collection =
                MongoDBConnection.getConnection().getCollection("Vehicles");

        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Vehicle v = new Vehicle();

            v.setOwnerId(doc.getString("ownerId"));
            v.setOwnerName(doc.getString("ownerName"));
            v.setPlate(doc.getString("plate"));
            v.setColor(doc.getString("color"));
            v.setModel(doc.getString("model"));
            v.setParked(doc.getBoolean("parked", false));

            vehicles.add(v);
        }
        cursor.close();
        return vehicles;
    }
}