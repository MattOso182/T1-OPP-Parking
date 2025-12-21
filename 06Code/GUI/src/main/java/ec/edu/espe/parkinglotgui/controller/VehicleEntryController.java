package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Emily Calle, T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.utils.MongoConnectionEntrances;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date;
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
}