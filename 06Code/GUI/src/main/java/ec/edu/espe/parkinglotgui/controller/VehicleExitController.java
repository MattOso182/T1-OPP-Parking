package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Emily Calle, T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.utils.MongoConnectionEntrances;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Date;
import java.util.regex.Pattern;

public class VehicleExitController {

    private static final String COLLECTION_NAME = "Entrances";
    private final MongoCollection<Document> collection;
    private final MongoConnectionEntrances mongoConnection;

    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{4}$");

    public VehicleExitController() {
        this.mongoConnection = new MongoConnectionEntrances();
        this.collection = mongoConnection.getCollection(COLLECTION_NAME);
    }

    private boolean validateLicensePlateFormat(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }

    public boolean isVehicleParked(String licensePlate) {
        try {
            if (!validateLicensePlateFormat(licensePlate)) return false;
            Bson filter = Filters.and(
                    Filters.eq("licensePlate", licensePlate),
                    Filters.eq("status", "PARKED")
            );
            return collection.find(filter).first() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registerExit(String licensePlate) {
        try {
            if (!validateLicensePlateFormat(licensePlate)) return false;

            Document activeEntry = collection.find(
                    Filters.and(
                        Filters.eq("licensePlate", licensePlate),
                        Filters.eq("status", "PARKED")
                    )
            ).first();

            if (activeEntry == null) {
                System.err.println("Vehículo no encontrado o no está PARKED");
                return false;
            }

            String spaceId = activeEntry.getString("spaceId");

            if (spaceId != null && !spaceId.isEmpty()) {
                ParkingSpaceController spaceController = new ParkingSpaceController();
                boolean isSpaceFreed = spaceController.freeParkingSpace(spaceId);
                
                if (!isSpaceFreed) {
                    System.err.println("Advertencia: No se pudo liberar el espacio " + spaceId + " en la DB.");
                }
            }

            Bson filter = Filters.and(
                    Filters.eq("licensePlate", licensePlate),
                    Filters.eq("status", "PARKED")
            );

            Bson updates = Updates.combine(
                    Updates.set("exitTime", new Date()),
                    Updates.set("status", "EXITED")
            );

            UpdateResult result = collection.updateOne(filter, updates);

            return result.getMatchedCount() > 0;

        } catch (Exception e) {
            System.err.println("Error en registerExit: " + e.getMessage());
            return false;
        } finally {
            mongoConnection.closeConnection();
        }
    }
}