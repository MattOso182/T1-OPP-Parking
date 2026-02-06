package ec.edu.espe.parkinglotgui.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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
import org.bson.conversions.Bson;

public class ResidentRepository {

    private MongoCollection<Document> collection;

    public ResidentRepository() {
        MongoDatabase database = MongoDBConnection.getConnection();
        if (database != null) {
            this.collection = database.getCollection("Residents");
        }
    }

    public Resident convertDocumentToResident(Document doc) {
        try {
            if (doc == null) {
                return null;
            }

            System.out.println("Convirtiendo documento a Resident: " + doc.getString("residentID"));

            Resident r = new Resident();
            r.setResidentID(cleanField(doc.getString("residentID")));
            r.setName(cleanField(doc.getString("name")));
            r.setApartmentNumber(cleanField(doc.getString("apartmentNumber")));
            r.setEmail(cleanField(doc.getString("email")));
            r.setPhone(cleanField(doc.getString("phone")));
            r.setUserType(cleanField(doc.getString("userType")));

            if (doc.containsKey("vehicles")) {
                List<Vehicle> vehicles = new ArrayList<>();
                List<Document> vehicleDocs = doc.getList("vehicles", Document.class);
                if (vehicleDocs != null) {
                    for (Document v : vehicleDocs) {
                        Vehicle ve = new Vehicle();
                        ve.setPlate(cleanField(v.getString("plate")));
                        ve.setColor(cleanField(v.getString("color")));
                        ve.setModel(cleanField(v.getString("model")));
                        ve.setOwnerId(cleanField(v.getString("ownerId")));
                        ve.setParked(v.getBoolean("isParked", false));
                        vehicles.add(ve);
                    }
                }
                r.setVehicles(vehicles);
            }
            
            if (doc.containsKey("currentRental")) {
                Document rentalDoc = doc.get("currentRental", Document.class);
                Rental rental = convertDocumentToRental(rentalDoc);
                r.setCurrentRental(rental);

                if (rental != null && rental.getSpaceId() != null && !rental.getSpaceId().isEmpty()) {
                    r.setAssignedParkingSpace(rental.getSpaceId());
                }
            } else {
                if (doc.containsKey("assignedParkingSpace")) {
                    r.setAssignedParkingSpace(cleanField(doc.getString("assignedParkingSpace")));
                }
            }

            if (doc.containsKey("authorizedVisitors")) {
                List<String> authorizedVisitors = doc.getList("authorizedVisitors", String.class);
                if (authorizedVisitors != null) {
                    r.setAuthorizedVisitors(new ArrayList<>(authorizedVisitors));
                }
            }

            return r;

        } catch (Exception e) {
            System.err.println("Error en convertDocumentToResident: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Resident findById(String residentId) {
        try {
            if (residentId == null || residentId.trim().isEmpty()) {
                return null;
            }

            residentId = cleanField(residentId);

            Bson query = Filters.eq("residentID", residentId);
            Document doc = collection.find(query).first();

            if (doc != null) {
                return convertDocumentToResident(doc);
            } else {
                return null;
            }

        } catch (Exception e) {
            System.err.println("Error en findById para ID '" + residentId + "': " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Rental convertDocumentToRental(Document doc) {
        try {
            if (doc == null) {
                return null;
            }

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
                } else if (totalPriceObj instanceof String) {
                    try {
                        r.setTotalPrice(Double.parseDouble((String) totalPriceObj));
                    } catch (NumberFormatException e) {
                        r.setTotalPrice(0.0);
                    }
                }
            }

            if (doc.containsKey("months")) {
                Object monthsObj = doc.get("months");
                if (monthsObj instanceof Number) {
                    r.setMonths(((Number) monthsObj).intValue());
                } else if (monthsObj instanceof String) {
                    try {
                        r.setMonths(Integer.parseInt((String) monthsObj));
                    } catch (NumberFormatException e) {
                        r.setMonths(1);
                    }
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
                } else {
                    r.setStartDate(new Date());
                }

                if (doc.containsKey("endDate")) {
                    Object endDateObj = doc.get("endDate");
                    if (endDateObj instanceof String) {
                        Date endDate = sdf.parse((String) endDateObj);
                        r.setEndDate(endDate);
                    } else if (endDateObj instanceof Date) {
                        r.setEndDate((Date) endDateObj);
                    }
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(r.getStartDate());
                    cal.add(Calendar.MONTH, r.getMonths() > 0 ? r.getMonths() : 1);
                    r.setEndDate(cal.getTime());
                }

            } catch (Exception e) {
                System.err.println("Error al procesar fechas del rental: " + e.getMessage());
                r.setStartDate(new Date());
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 1);
                r.setEndDate(cal.getTime());
            }

            return r;

        } catch (Exception e) {
            System.err.println("Error en convertDocumentToRental: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
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
