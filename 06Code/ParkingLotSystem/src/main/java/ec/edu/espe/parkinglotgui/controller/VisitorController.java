package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.model.Visitor;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import ec.edu.espe.parkinglotgui.repository.VehicleRepository;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class VisitorController {

    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> residentCollection;

    private static final Pattern PLATE_PATTERN =
            Pattern.compile("^[A-Z]{3}-\\d{4}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");

    public VisitorController() {
        MongoDatabase database = MongoDBConnection.getConnection();
        collection = database.getCollection("Visitors");
        residentCollection = database.getCollection("Residents");
    }

    private boolean isValidVisitorID(String id) {
        return id != null && ID_PATTERN.matcher(id.trim()).matches();
    }

    private boolean isValidPlate(String plate) {
        return plate != null && PLATE_PATTERN.matcher(plate.trim()).matches();
    }

    private boolean residentExists(String residentID) {
        return residentCollection.find(
                Filters.eq("residentID", residentID)
        ).first() != null;
    }

    private boolean visitorIDExists(String visitorID) {
        return collection.find(
                Filters.eq("visitorID", visitorID)
        ).first() != null;
    }

    private boolean plateExists(String plate) {
        return collection.find(
                Filters.eq("vehiclePlate", plate)
        ).first() != null;
    }

    public boolean saveVisitor(Visitor visitor) {
        if (!isValidVisitorID(visitor.getVisitorID())) return false;
        if (visitorIDExists(visitor.getVisitorID())) return false;
        if (!isValidPlate(visitor.getVehiclePlate())) return false;
        if (plateExists(visitor.getVehiclePlate())) return false;
        if (!residentExists(visitor.getResidentID())) return false;

        Document doc = new Document()
                .append("visitorID", visitor.getVisitorID())
                .append("nameVisitor", visitor.getNameVisitor())
                .append("vehiclePlate", visitor.getVehiclePlate())
                .append("residentID", visitor.getResidentID())
                .append("hasPass", visitor.isHasPass());

        collection.insertOne(doc);

        VehicleRepository vehicleRepository = new VehicleRepository();
        vehicleRepository.saveVehicle(
            visitor.getVisitorID(),
            visitor.getNameVisitor(),
            visitor.getVehiclePlate(),
            "Unknown",
            "Unknown",
            true
        );

        return true;
    }

    public boolean updateVisitor(Visitor visitor) {
        Document update = new Document("$set",
                new Document()
                        .append("nameVisitor", visitor.getNameVisitor())
                        .append("vehiclePlate", visitor.getVehiclePlate())
                        .append("residentID", visitor.getResidentID())
                        .append("hasPass", visitor.isHasPass())
        );

        UpdateResult result = collection.updateOne(
                Filters.eq("visitorID", visitor.getVisitorID()),
                update
        );

        return result.getModifiedCount() > 0;
    }

    public boolean deleteVisitor(String visitorID) {
        DeleteResult result = collection.deleteOne(
                Filters.eq("visitorID", visitorID)
        );
        return result.getDeletedCount() > 0;
    }

    public List<Visitor> getAllVisitors() {
        List<Visitor> visitors = new ArrayList<>();

        for (Document doc : collection.find()) {
            Visitor v = new Visitor();
            v.setVisitorID(doc.getString("visitorID"));
            v.setNameVisitor(doc.getString("nameVisitor"));
            v.setVehiclePlate(doc.getString("vehiclePlate"));
            v.setResidentID(doc.getString("residentID"));
            v.setHasPass(doc.getBoolean("hasPass", false));
            visitors.add(v);
        }

        return visitors;
    }
}