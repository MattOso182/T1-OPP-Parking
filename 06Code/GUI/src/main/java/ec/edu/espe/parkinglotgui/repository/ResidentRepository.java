package ec.edu.espe.parkinglotgui.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection; 
import java.text.SimpleDateFormat;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResidentRepository {
    private MongoCollection<Document> collection;

    public ResidentRepository() {
        MongoDatabase database = MongoDBConnection.getConnection(); 
        if (database != null) {
            this.collection = database.getCollection("residents");
        }
    }
    
    public Resident convertDocumentToResident(Document doc) {
        Resident r = new Resident();
        r.setResidentID(cleanField(doc.getString("residentID")));
        r.setName(cleanField(doc.getString("name")));
        r.setApartmentNumber(cleanField(doc.getString("apartmentNumber")));
        r.setEmail(cleanField(doc.getString("email")));
        r.setPhone(cleanField(doc.getString("phone")));
        r.setUserType(cleanField(doc.getString("userType")));

        if (doc.containsKey("currentRental")) {
            Document rentalDoc = doc.get("currentRental", Document.class);
            if (rentalDoc.containsKey("spaceId")) {
                String spaceId = cleanField(rentalDoc.getString("spaceId"));
                r.setAssignedParkingSpace(spaceId);
            }
        } else {
            r.setAssignedParkingSpace(cleanField(doc.getString("assignedParkingSpace")));
        }

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

    public Resident findById(String residentId) {
        Document doc = collection.find(new Document("residents.residentID", residentId)).first();
        return (doc != null) ? convertDocumentToResident(doc) : null;
    }
    private Rental convertDocumentToRental(Document doc) {
        Rental r = new Rental();

        r.setSpaceId(cleanField(doc.getString("spaceId")));
        r.setPaymentStatus(cleanField(doc.getString("paymentStatus")));
        r.setActive(doc.getBoolean("isActive", false));

        if (doc.containsKey("rentalId")) {
            r.setRentalId(cleanField(doc.getString("rentalId")));
        }

        if (doc.containsKey("residentId")) {
            r.setResidentId(cleanField(doc.getString("residentId")));
        }

        if (doc.containsKey("totalPrice")) {
            Object totalPriceObj = doc.get("totalPrice");
            if (totalPriceObj instanceof Number) {
                r.setTotalPrice(((Number) totalPriceObj).doubleValue());
            }
        }

        if (doc.containsKey("months")) {
            Object monthsObj = doc.get("months");
            if (monthsObj instanceof Number) {
                r.setMonths(((Number) monthsObj).intValue());
            }
        }

        if (doc.containsKey("monthlyPrice")) {
            Object priceObj = doc.get("monthlyPrice");
            if (priceObj instanceof Number) {
                r.setMonthlyPrice(((Number) priceObj).doubleValue());
            } else if (priceObj instanceof String) {
                try {
                    r.setMonthlyPrice(Double.parseDouble((String) priceObj));
                } catch (NumberFormatException e) {
                    r.setMonthlyPrice(45.00);
                }
            } else {
                r.setMonthlyPrice(45.00);
            }
        } else {
            r.setMonthlyPrice(45.00);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if (doc.containsKey("startDate")) {
                Object startDateObj = doc.get("startDate");
                if (startDateObj instanceof String) {
                    Date startDate = sdf.parse((String) startDateObj);
                    r.setStartDate(startDate);
                } else if (startDateObj instanceof Date) {
                    r.setStartDate((Date) startDateObj);
                }
            }

            if (doc.containsKey("endDate")) {
                Object endDateObj = doc.get("endDate");
                if (endDateObj instanceof String) {
                    Date endDate = sdf.parse((String) endDateObj);
                    r.setEndDate(endDate);
                } else if (endDateObj instanceof Date) {
                    r.setEndDate((Date) endDateObj);
                }
            }

        } catch (Exception e) {
            if (r.getStartDate() == null) {
                r.setStartDate(new Date());
            }
            if (r.getEndDate() == null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(r.getStartDate());
                cal.add(Calendar.MONTH, 1);
                r.setEndDate(cal.getTime());
            }
        }

        return r;
    }
    private String cleanField(String field) {
    if (field == null) {
        return "";
    }
    field = field.trim();
    while (field.endsWith(",") || field.endsWith("/")) {
        field = field.substring(0, field.length() - 1).trim();
    }
    return field;
}
}