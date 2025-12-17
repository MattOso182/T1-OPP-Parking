package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */

import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResidentController {

    private MongoCollection<Document> collection;

    public ResidentController() {
        MongoDatabase database = MongoDBConnection.getConnection();
        if (database != null) {
            collection = database.getCollection(findResidentCollection(database));
        }
    }

    private String findResidentCollection(MongoDatabase database) {
        List<String> collections = database.listCollectionNames().into(new ArrayList<>());
        String[] possibleNames = {"residents", "Residents", "resident", "Resident"};
        for (String name : possibleNames) {
            if (collections.contains(name)) {
                return name;
            }
        }
        return "residents";
    }

    private String cleanField(String field) {
        if (field == null) return "";
        field = field.trim();
        while (field.endsWith(",") || field.endsWith("/")) {
            field = field.substring(0, field.length() - 1).trim();
        }
        return field;
    }

    public Resident searchResident(String input) {
        input = cleanField(input);

        for (Document mainDoc : collection.find()) {
            if (mainDoc.containsKey("residents")) {
                for (Document r : mainDoc.getList("residents", Document.class)) {
                    if (input.equalsIgnoreCase(cleanField(r.getString("residentID")))
                            || input.equalsIgnoreCase(cleanField(r.getString("name")))) {
                        return convertDocumentToResident(r);
                    }
                }
            } else {
                if (input.equalsIgnoreCase(cleanField(mainDoc.getString("residentID")))
                        || input.equalsIgnoreCase(cleanField(mainDoc.getString("name")))) {
                    return convertDocumentToResident(mainDoc);
                }
            }
        }
        return null;
    }

    public Resident searchResidentById(String residentId) {
        return searchResident(residentId);
    }

    private Resident convertDocumentToResident(Document doc) {
        Resident r = new Resident();
        r.setResidentID(cleanField(doc.getString("residentID")));
        r.setName(cleanField(doc.getString("name")));
        r.setApartmentNumber(cleanField(doc.getString("apartmentNumber")));
        r.setEmail(cleanField(doc.getString("email")));
        r.setPhone(cleanField(doc.getString("phone")));
        r.setUserType(cleanField(doc.getString("userType")));
        r.setAssignedParkingSpace(cleanField(doc.getString("assignedParkingSpace")));

        if (doc.containsKey("vehicles")) {
            List<Vehicle> vehicles = new ArrayList<>();
            for (Document v : doc.getList("vehicles", Document.class)) {
                Vehicle ve = new Vehicle();
                ve.setPlate(cleanField(v.getString("plate")));
                ve.setColor(cleanField(v.getString("color")));
                ve.setModel(cleanField(v.getString("model")));
                ve.setOwnerId(cleanField(v.getString("ownerId")));
                ve.setParked(v.getBoolean("isParked", false));
                vehicles.add(ve);
            }
            r.setVehicles(vehicles);
        }

        if (doc.containsKey("currentRental")) {
            r.setCurrentRental(convertDocumentToRental(doc.get("currentRental", Document.class)));
        }

        return r;
    }

    private Rental convertDocumentToRental(Document doc) {
        Rental r = new Rental();
        r.setSpaceId(cleanField(doc.getString("spaceId")));
        r.setPaymentStatus(cleanField(doc.getString("paymentStatus")));
        r.setActive(doc.getBoolean("isActive", false));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (doc.get("startDate") instanceof String)
                r.setStartDate(sdf.parse(doc.getString("startDate")));
            if (doc.get("endDate") instanceof String)
                r.setEndDate(sdf.parse(doc.getString("endDate")));
        } catch (Exception ignored) {}

        return r;
    }

    public boolean activateRentalWithSpace(String residentId, int months, String spaceId) {
        residentId = cleanField(residentId);
        spaceId = cleanField(spaceId);

        Resident resident = searchResidentById(residentId);
        if (resident == null) return false;

        if (resident.getAssignedParkingSpace() != null && !resident.getAssignedParkingSpace().isEmpty()) {
            throw new IllegalStateException("Resident already has assigned parking space");
        }

        MongoDatabase db = MongoDBConnection.getConnection();
        MongoCollection<Document> spaceCollection = db.getCollection("ParkingSpaces");

        Document space = spaceCollection.find(new Document("spaceId", spaceId)).first();
        if (space == null || space.getBoolean("isOccupied", false)) {
            throw new IllegalStateException("Parking space not available");
        }

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.MONTH, months);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Document rentalDoc = new Document("startDate", sdf.format(today))
                .append("endDate", sdf.format(cal.getTime()))
                .append("spaceId", spaceId)
                .append("paymentStatus", "PENDING")
                .append("isActive", true);

        collection.updateOne(
                new Document("residents.residentID", residentId),
                new Document("$set",
                        new Document("residents.$.currentRental", rentalDoc)
                                .append("residents.$.assignedParkingSpace", spaceId))
        );

        collection.updateOne(
                new Document("residentID", residentId),
                new Document("$set",
                        new Document("currentRental", rentalDoc)
                                .append("assignedParkingSpace", spaceId))
        );

        spaceCollection.updateOne(
                new Document("spaceId", spaceId),
                new Document("$set", new Document("isOccupied", true))
        );

        return true;
    }

    public boolean cancelRental(String residentId) {
        Resident resident = searchResidentById(residentId);
        if (resident == null || resident.getAssignedParkingSpace() == null) return false;

        MongoDatabase db = MongoDBConnection.getConnection();
        MongoCollection<Document> spaceCollection = db.getCollection("ParkingSpaces");

        spaceCollection.updateOne(
                new Document("spaceId", resident.getAssignedParkingSpace()),
                new Document("$set", new Document("isOccupied", false))
        );

        collection.updateOne(
                new Document("residentID", residentId),
                new Document("$set",
                        new Document("currentRental.isActive", false)
                                .append("currentRental.paymentStatus", "RENTAL_CANCELED")
                                .append("assignedParkingSpace", ""))
        );

        return true;
    }

    public boolean renewRentalWithSpace(String residentId, int months, String spaceId) {
        cancelRental(residentId);
        return activateRentalWithSpace(residentId, months, spaceId);
    }

    public boolean renewRentalFromToday(String residentId, int months, String spaceId) {
        return renewRentalWithSpace(residentId, months, spaceId);
    }

    public boolean updatePaymentStatusOnly(String residentId, String status) {
        UpdateResult result = collection.updateOne(
                new Document("residentID", residentId),
                new Document("$set", new Document("currentRental.paymentStatus", status))
        );
        return result.getModifiedCount() > 0;
    }
    public List<Resident> getAllResidents() {
        List<Resident> residents = new ArrayList<>();

        for (Document mainDoc : collection.find()) {

            if (mainDoc.containsKey("residents")) {
                List<Document> residentDocs = mainDoc.getList("residents", Document.class);
                for (Document rDoc : residentDocs) {
                    residents.add(convertDocumentToResident(rDoc));
                }

            } else {
                residents.add(convertDocumentToResident(mainDoc));
            }
        }

        return residents;
    }
}