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

    private static final Pattern LICENSE_PLATE_PATTERN
            = Pattern.compile("^[A-Z]{3}-\\d{4}$");

    public VehicleExitController() {
        this.mongoConnection = new MongoConnectionEntrances();
        this.collection = mongoConnection.getCollection(COLLECTION_NAME);
    }

    private boolean validateLicensePlateFormat(String licensePlate) {
        boolean isValid = LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();

        if (!isValid) {
            System.err.println("Formato de placa inválido: " + licensePlate);
            System.err.println("El formato debe ser: ABC-1234 (3 letras, guion, 4 números)");
        }

        return isValid;
    }

    public boolean isVehicleParked(String licensePlate) {
        try {
            if (!validateLicensePlateFormat(licensePlate)) {
                return false;
            }

            Bson filter = Filters.and(
                    Filters.eq("licensePlate", licensePlate),
                    Filters.eq("status", "PARKED")
            );

            Document vehicle = collection.find(filter).first();
            return vehicle != null;

        } catch (Exception e) {
            System.err.println("Error verificando vehículo estacionado: " + e.getMessage());
            return false;
        }
    }

    public boolean registerExit(String licensePlate) {
        try {
            if (!validateLicensePlateFormat(licensePlate)) {
                System.err.println("La placa " + licensePlate + " tiene formato inválido");
                return false;
            }

            Date exitTime = new Date();

            Bson filter = Filters.and(
                    Filters.eq("licensePlate", licensePlate),
                    Filters.eq("status", "PARKED")
            );

            Bson updates = Updates.combine(
                    Updates.set("exitTime", exitTime),
                    Updates.set("status", "EXITED")
            );

            UpdateResult result = collection.updateOne(filter, updates);

            if (result.getMatchedCount() == 0) {
                System.err.println("Error: No se encontró ningún vehículo con placa " + licensePlate + " en estado 'PARKED'.");
                return false;
            }

            mongoConnection.closeConnection();
            return true;

        } catch (Exception e) {
            System.err.println("Error al registrar la salida del vehículo: " + e.getMessage());
            mongoConnection.closeConnection();
            return false;
        }
    }
}
