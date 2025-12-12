package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.mongodb.client.MongoDatabase;
import java.text.SimpleDateFormat;

public class ResidentController {

    private MongoCollection<Document> collection;

    public ResidentController() {
        try {
            MongoDatabase database = MongoDBConnection.getConnection();
            if (database != null) {
                String collectionName = findResidentCollection(database);
                collection = database.getCollection(collectionName);
            } else {
                System.err.println("No se pudo obtener conexión a la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Error inicializando ResidentController: " + e.getMessage());
            e.printStackTrace();
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

        return !collections.isEmpty() ? collections.get(0) : "residents";
    }

    public Resident searchResident(String searchInput) {
        if (searchInput == null || searchInput.trim().isEmpty()) {
            return null;
        }

        searchInput = searchInput.trim();

        try {
            Resident resident = findInResidentsArray(searchInput);
            return resident;

        } catch (Exception e) {
            System.err.println("Search error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Resident searchResidentById(String residentId) {
        if (residentId == null || residentId.trim().isEmpty()) {
            return null;
        }

        residentId = residentId.trim();

        try {
            for (Document mainDoc : collection.find()) {
                if (mainDoc.containsKey("residents")) {
                    List<Document> residentsArray = mainDoc.getList("residents", Document.class);

                    for (Document residentDoc : residentsArray) {
                        String currentId = residentDoc.getString("residentID");
                        if (currentId != null && cleanField(currentId).equalsIgnoreCase(cleanField(residentId))) {
                            return convertDocumentToResident(residentDoc);
                        }
                    }
                } else {
                    String currentId = mainDoc.getString("residentID");
                    if (currentId != null && cleanField(currentId).equalsIgnoreCase(cleanField(residentId))) {
                        return convertDocumentToResident(mainDoc);
                    }
                }
            }

            return null;

        } catch (Exception e) {
            System.err.println("Error searching by ID: " + e.getMessage());
            return null;
        }
    }

    private Resident findInResidentsArray(String searchInput) {
        try {
            String searchClean = cleanField(searchInput).toLowerCase();

            for (Document mainDoc : collection.find()) {
                if (mainDoc.containsKey("residents")) {
                    List<Document> residentsArray = mainDoc.getList("residents", Document.class);

                    for (Document residentDoc : residentsArray) {
                        String residentID = residentDoc.getString("residentID");
                        if (residentID != null && cleanField(residentID).toLowerCase().contains(searchClean)) {
                            return convertDocumentToResident(residentDoc);
                        }

                        String name = residentDoc.getString("name");
                        if (name != null && cleanField(name).toLowerCase().contains(searchClean)) {
                            return convertDocumentToResident(residentDoc);
                        }
                    }
                } else {
                    String residentID = mainDoc.getString("residentID");
                    if (residentID != null && cleanField(residentID).toLowerCase().contains(searchClean)) {
                        return convertDocumentToResident(mainDoc);
                    }
                }
            }

            return null;

        } catch (Exception e) {
            System.err.println("error searching by Array: " + e.getMessage());
            return null;
        }
    }

    private Resident convertDocumentToResident(Document doc) {
        Resident resident = new Resident();

        try {
            resident.setResidentID(cleanField(doc.getString("residentID")));
            resident.setName(cleanField(doc.getString("name")));
            resident.setApartmentNumber(cleanField(doc.getString("apartmentNumber")));
            resident.setEmail(cleanField(doc.getString("email")));
            resident.setPhone(cleanField(doc.getString("phone")));

            String userType = cleanField(doc.getString("userType"));
            resident.setUserType(userType);

            String parkingSpace = cleanField(doc.getString("assignedParkingSpace"));
            if (parkingSpace != null && parkingSpace.endsWith("/")) {
                parkingSpace = parkingSpace.substring(0, parkingSpace.length() - 1);
            }
            resident.setAssignedParkingSpace(parkingSpace);

            if (doc.containsKey("vehicles")) {
                try {
                    List<Document> vehiclesDoc = doc.getList("vehicles", Document.class);
                    if (vehiclesDoc != null && !vehiclesDoc.isEmpty()) {
                        List<Vehicle> vehicles = new ArrayList<>();

                        for (Document vehicleDoc : vehiclesDoc) {
                            Vehicle vehicle = new Vehicle();

                            if (vehicleDoc.containsKey("plate")) {
                                vehicle.setPlate(cleanField(vehicleDoc.getString("plate")));
                            } else if (vehicleDoc.containsKey("licensePlate")) {
                                vehicle.setPlate(cleanField(vehicleDoc.getString("licensePlate")));
                            }

                            if (vehicleDoc.containsKey("color")) {
                                vehicle.setColor(cleanField(vehicleDoc.getString("color")));
                            }

                            if (vehicleDoc.containsKey("model")) {
                                vehicle.setModel(cleanField(vehicleDoc.getString("model")));
                            }

                            if (vehicleDoc.containsKey("ownerId")) {
                                vehicle.setOwnerId(cleanField(vehicleDoc.getString("ownerId")));
                            }

                            if (vehicleDoc.containsKey("isParked")) {
                                vehicle.setParked(vehicleDoc.getBoolean("isParked"));
                            }

                            vehicles.add(vehicle);
                        }
                        resident.setVehicles(vehicles);
                    }
                } catch (Exception e) {
                    System.err.println("Error with vehicle: " + e.getMessage());
                }
            }

            if (doc.containsKey("authorizedVisitors")) {
                try {
                    Object visitorsObj = doc.get("authorizedVisitors");
                    List<String> visitorIds = new ArrayList<>();

                    if (visitorsObj instanceof List) {
                        List<?> visitorsList = (List<?>) visitorsObj;

                        for (Object visitor : visitorsList) {
                            if (visitor instanceof Document) {
                                Document visitorDoc = (Document) visitor;
                                if (visitorDoc.containsKey("visitorID")) {
                                    visitorIds.add(cleanField(visitorDoc.getString("visitorID")));
                                } else if (visitorDoc.containsKey("name")) {
                                    visitorIds.add(cleanField(visitorDoc.getString("name")));
                                }
                            } else if (visitor instanceof String) {
                                visitorIds.add(cleanField((String) visitor));
                            }
                        }
                    }

                    resident.setAuthorizedVisitors(visitorIds);

                } catch (Exception e) {
                    System.err.println("Error with visitor: " + e.getMessage());
                }
            }

            if (doc.containsKey("currentRental")) {
                try {
                    Document rentalDoc = doc.get("currentRental", Document.class);
                    if (rentalDoc != null) {
                        Rental rental = convertDocumentToRental(rentalDoc);
                        resident.setCurrentRental(rental);
                    }
                } catch (Exception e) {
                    System.err.println("Error with renta: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("Error converting to resident: " + e.getMessage());
            e.printStackTrace();
        }

        return resident;
    }

    private Rental convertDocumentToRental(Document doc) {
        try {
            Rental rental = new Rental();

            if (doc.containsKey("rentalId")) {
                rental.setRentalId(cleanField(doc.getString("rentalId")));
            }

            if (doc.containsKey("residentId")) {
                rental.setResidentId(cleanField(doc.getString("residentId")));
            }

            if (doc.containsKey("spaceId")) {
                rental.setSpaceId(cleanField(doc.getString("spaceId")));
            }

            if (doc.containsKey("monthlyPrice")) {
                Object priceObj = doc.get("monthlyPrice");
                if (priceObj instanceof Number) {
                    rental.setMonthlyPrice(((Number) priceObj).doubleValue());
                }
            }

            if (doc.containsKey("isActive")) {
                rental.setActive(doc.getBoolean("isActive"));
            }

            if (doc.containsKey("paymentStatus")) {
                rental.setPaymentStatus(cleanField(doc.getString("paymentStatus")));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (doc.containsKey("startDate")) {
                Object startDateObj = doc.get("startDate");
                if (startDateObj instanceof Date) {
                    rental.setStartDate((Date) startDateObj);
                } else if (startDateObj instanceof String) {
                    try {
                        rental.setStartDate(sdf.parse((String) startDateObj));
                    } catch (Exception e) {
                        System.err.println("Error parseando startDate: " + e.getMessage());
                    }
                }
            }

            if (doc.containsKey("endDate")) {
                Object endDateObj = doc.get("endDate");
                if (endDateObj instanceof Date) {
                    rental.setEndDate((Date) endDateObj);
                } else if (endDateObj instanceof String) {
                    try {
                        rental.setEndDate(sdf.parse((String) endDateObj));
                    } catch (Exception e) {
                        System.err.println("Error parseando endDate: " + e.getMessage());
                    }
                }
            }

            return rental;

        } catch (Exception e) {
            System.err.println("Error converting to Rental: " + e.getMessage());
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

    public List<Resident> getAllResidents() {
        List<Resident> allResidents = new ArrayList<>();

        try {
            for (Document mainDoc : collection.find()) {
                if (mainDoc.containsKey("residents")) {
                    List<Document> residentsArray = mainDoc.getList("residents", Document.class);
                    for (Document residentDoc : residentsArray) {
                        allResidents.add(convertDocumentToResident(residentDoc));
                    }
                } else {
                    allResidents.add(convertDocumentToResident(mainDoc));
                }
            }

        } catch (Exception e) {
            System.err.println("Error getting residents: " + e.getMessage());
        }

        return allResidents;
    }

    public boolean residentExists(String residentId) {
        try {
            Resident resident = searchResidentById(residentId);
            return resident != null;
        } catch (Exception e) {
            return false;
        }
    }
}
