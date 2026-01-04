package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.utils.MongoConnectionEntrances;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.util.ArrayList;
import org.bson.Document;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class VehicleEntryController {

    private static final String ENTRANCES_COLLECTION = "Entrances";
    private final MongoCollection<Document> entrancesCollection;
    private final MongoConnectionEntrances mongoConnectionEntrances;

    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{4}$");

    public VehicleEntryController() {
        this.mongoConnectionEntrances = new MongoConnectionEntrances();
        this.entrancesCollection = mongoConnectionEntrances.getCollection(ENTRANCES_COLLECTION);
    }

    private boolean validateLicensePlateFormat(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }

    public boolean registerEntry(String licensePlate, String assignedSpace) {
        try {
            if (!validateLicensePlateFormat(licensePlate)) return false;

            if (isVehicleParked(licensePlate)) return false;

            ParkingSpaceController spaceController = new ParkingSpaceController();

            boolean updated = spaceController.updateSpaceOccupation(assignedSpace, true);

            if (!updated) {
                System.err.println("No se pudo actualizar el estado del espacio en la DB.");
                return false;
            }

            Document entryRecord = new Document("licensePlate", licensePlate)
                    .append("spaceId", assignedSpace)
                    .append("entryTime", new Date())
                    .append("status", "PARKED");

            entrancesCollection.insertOne(entryRecord);
            
            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        } finally {
            mongoConnectionEntrances.closeConnection();
        }
    }

    public boolean isVehicleParked(String licensePlate) {
        Document activeEntry = entrancesCollection.find(
                new Document("licensePlate", licensePlate).append("status", "PARKED")
        ).first();
        return activeEntry != null;
    }
    
    public ArrayList<Vehicle> getAllVehicles() {
    ArrayList<Vehicle> vehicles = new ArrayList<>();

    try {
       
        MongoCollection<Document> collection =
                MongoDBConnection.getConnection().getCollection("Vehicles");

       
        MongoCursor<Document> cursor = collection.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            Vehicle vehicle = new Vehicle();

            vehicle.setOwnerId(doc.getString("ownerId"));
            vehicle.setOwnerName(doc.getString("ownerName"));
            vehicle.setPlate(doc.getString("plate"));
            vehicle.setColor(doc.getString("color"));
            vehicle.setModel(doc.getString("model"));
            vehicle.setParked(doc.getBoolean("parked", false));

            vehicles.add(vehicle);
        }

        cursor.close();

    } catch (Exception e) {
        System.err.println("Error retrieving vehicles: " + e.getMessage());
    }

        return vehicles;
    }

}