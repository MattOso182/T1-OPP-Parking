package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Date;
import java.util.regex.Pattern;

public class VehicleExitController {

    private static final Pattern LICENSE_PLATE_PATTERN =
            Pattern.compile("^[A-Z]{3}-\\d{4}$");

    private final MongoCollection<Document> collection;

    public VehicleExitController() {
        collection = MongoDBConnection
                .getConnection()
                .getCollection("Entrances");
    }

    private boolean validateLicensePlateFormat(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }

    public boolean isVehicleParked(String licensePlate) {
        if (!validateLicensePlateFormat(licensePlate)) return false;

        Bson filter = Filters.and(
                Filters.eq("licensePlate", licensePlate),
                Filters.eq("status", "PARKED")
        );

        return collection.find(filter).first() != null;
    }

    public boolean registerExit(String licensePlate) {
        if (!validateLicensePlateFormat(licensePlate)) return false;

        Document activeEntry = collection.find(
                Filters.and(
                        Filters.eq("licensePlate", licensePlate),
                        Filters.eq("status", "PARKED")
                )
        ).first();

        if (activeEntry == null) return false;

        String spaceId = activeEntry.getString("spaceId");

        if (spaceId != null && !spaceId.isEmpty()) {
            ParkingSpaceController spaceController = new ParkingSpaceController();
            spaceController.freeParkingSpace(spaceId);
        }

        Bson updates = Updates.combine(
                Updates.set("exitTime", new Date()),
                Updates.set("status", "EXITED")
        );

        UpdateResult result = collection.updateOne(
                Filters.and(
                        Filters.eq("licensePlate", licensePlate),
                        Filters.eq("status", "PARKED")
                ),
                updates
        );

        return result.getMatchedCount() > 0;
    }

    public java.util.List<Document> getParkedVehicles() {
        java.util.List<Document> parkedVehicles = new java.util.ArrayList<>();
        collection.find(Filters.eq("status", "PARKED")).into(parkedVehicles);
        return parkedVehicles;
    }
}