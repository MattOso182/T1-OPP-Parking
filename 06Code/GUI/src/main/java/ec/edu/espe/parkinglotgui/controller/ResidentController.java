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
import com.mongodb.client.result.UpdateResult;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public boolean renewRentalFromToday(String residentId, int selectedMonths, String spaceId) {
        try {
            if (collection == null) {
                System.err.println("Collection is null");
                return false;
            }

            residentId = cleanField(residentId);
            spaceId = cleanField(spaceId);

            System.out.println("Renovando alquiler desde HOY para: " + residentId);
            System.out.println("   Meses seleccionados: " + selectedMonths);
            System.out.println("   Espacio: " + spaceId);
            System.out.println("   Precio mensual: $120.00");

            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.MONTH, selectedMonths);
            Date newEndDate = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedStartDate = sdf.format(today);
            String formattedEndDate = sdf.format(newEndDate);

            double monthlyPrice = 120.00;
            double totalAmount = monthlyPrice * selectedMonths;

            System.out.println("   Start Date (hoy): " + formattedStartDate);
            System.out.println("   End Date: " + formattedEndDate);
            System.out.println("   Total a pagar: $" + totalAmount);

            Document query1 = new Document("residents.residentID", residentId);
            Document update1 = new Document("$set",
                    new Document("residents.$.currentRental.startDate", formattedStartDate)
                            .append("residents.$.currentRental.endDate", formattedEndDate)
                            .append("residents.$.currentRental.monthlyPrice", totalAmount)
                            .append("residents.$.currentRental.spaceId", spaceId)
                            .append("residents.$.currentRental.paymentStatus", "PENDING")
                            .append("residents.$.currentRental.isActive", true)
            );

            UpdateResult result1 = collection.updateOne(query1, update1);

            if (result1.getModifiedCount() > 0) {
                System.out.println("Alquiler renovado desde hoy exitosamente");
                return true;
            }

            Document query2 = new Document("residentID", residentId);
            Document update2 = new Document("$set",
                    new Document("currentRental.startDate", formattedStartDate)
                            .append("currentRental.endDate", formattedEndDate)
                            .append("currentRental.monthlyPrice", totalAmount)
                            .append("currentRental.spaceId", spaceId)
                            .append("currentRental.paymentStatus", "PENDING")
                            .append("currentRental.isActive", true)
            );

            UpdateResult result2 = collection.updateOne(query2, update2);

            if (result2.getModifiedCount() > 0) {
                System.out.println("Alquiler renovado (documento directo)");
                return true;
            }

            System.err.println("No se pudo renovar el alquiler para: " + residentId);
            return false;

        } catch (Exception e) {
            System.err.println("Error renovando alquiler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePaymentStatusOnly(String residentId, String paymentStatus) {
        try {
            if (collection == null) {
                System.err.println("Collection is null");
                return false;
            }

            residentId = cleanField(residentId);
            System.out.println("Cambiando estado de pago para: " + residentId);
            System.out.println("   Nuevo estado: " + paymentStatus);

            Document query1 = new Document("residents.residentID", residentId);
            Document update1 = new Document("$set",
                    new Document("residents.$.currentRental.paymentStatus", paymentStatus)
            );

            UpdateResult result1 = collection.updateOne(query1, update1);

            if (result1.getModifiedCount() > 0) {
                System.out.println("Estado de pago actualizado a: " + paymentStatus);
                return true;
            }

            Document query2 = new Document("residentID", residentId);
            Document update2 = new Document("$set",
                    new Document("currentRental.paymentStatus", paymentStatus)
            );

            UpdateResult result2 = collection.updateOne(query2, update2);

            if (result2.getModifiedCount() > 0) {
                System.out.println("Estado de pago actualizado (documento directo)");
                return true;
            }

            System.err.println("No se encontró residente con ID: " + residentId);
            return false;

        } catch (Exception e) {
            System.err.println("Error actualizando estado de pago: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean processPaymentWithRenewal(String residentId, int additionalMonths) {
        try {
            if (collection == null) {
                System.err.println(" Collection is null");
                return false;
            }

            residentId = cleanField(residentId);
            System.out.println("Procesando pago con renovación para: " + residentId);
            System.out.println("   Meses adicionales: " + additionalMonths);

            Resident resident = searchResidentById(residentId);
            if (resident == null || resident.getCurrentRental() == null) {
                System.err.println("No se encontró alquiler activo para: " + residentId);
                return false;
            }

            Date currentEndDate = resident.getCurrentRental().getEndDate();
            Date newEndDate;

            Calendar cal = Calendar.getInstance();
            if (currentEndDate != null && currentEndDate.after(new Date())) {
                cal.setTime(currentEndDate);
            } else {
                cal.setTime(new Date());
            }

            cal.add(Calendar.MONTH, additionalMonths);
            newEndDate = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedEndDate = sdf.format(newEndDate);

            System.out.println("   Fecha actual de fin: "
                    + (currentEndDate != null ? sdf.format(currentEndDate) : "Ninguna"));
            System.out.println("   Nueva fecha de fin: " + formattedEndDate);

            Document query1 = new Document("residents.residentID", residentId);
            Document update1 = new Document("$set",
                    new Document("residents.$.currentRental.endDate", formattedEndDate)
                            .append("residents.$.currentRental.isActive", true)
            );

            UpdateResult result1 = collection.updateOne(query1, update1);

            if (result1.getModifiedCount() > 0) {
                System.out.println("Pago con renovación procesado exitosamente");
                return true;
            }

            Document query2 = new Document("residentID", residentId);
            Document update2 = new Document("$set",
                    new Document("currentRental.paymentStatus", "PAID")
                            .append("currentRental.endDate", formattedEndDate)
                            .append("currentRental.isActive", true)
            );

            UpdateResult result2 = collection.updateOne(query2, update2);

            if (result2.getModifiedCount() > 0) {
                System.out.println("Pago con renovación procesado (documento directo)");
                return true;
            }

            System.err.println("No se pudo procesar el pago para: " + residentId);
            return false;

        } catch (Exception e) {
            System.err.println("Error procesando pago con renovación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean renewRentalOnly(String residentId, int selectedMonths) {
        try {
            if (collection == null) {
                System.err.println("Collection is null");
                return false;
            }

            residentId = cleanField(residentId);
            System.out.println("Renovando alquiler para: " + residentId);
            System.out.println("   Meses seleccionados: " + selectedMonths);

            Resident resident = searchResidentById(residentId);
            if (resident == null || resident.getCurrentRental() == null) {
                System.err.println("No se encontró alquiler activo para: " + residentId);
                return false;
            }

            Date startDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);

            cal.add(Calendar.MONTH, selectedMonths);
            Date newEndDate = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedStartDate = sdf.format(startDate);
            String formattedEndDate = sdf.format(newEndDate);

            System.out.println("   Nueva fecha de inicio: " + formattedStartDate);
            System.out.println("   Nueva fecha de fin: " + formattedEndDate);
            System.out.println("   Estado de pago se mantiene: " + resident.getCurrentRental().getPaymentStatus());

            Document query1 = new Document("residents.residentID", residentId);
            Document update1 = new Document("$set",
                    new Document("residents.$.currentRental.startDate", formattedStartDate)
                            .append("residents.$.currentRental.endDate", formattedEndDate)
                            .append("residents.$.currentRental.isActive", true)
            );

            UpdateResult result1 = collection.updateOne(query1, update1);

            if (result1.getModifiedCount() > 0) {
                System.out.println("Alquiler renovado exitosamente");
                return true;
            }

            Document query2 = new Document("residentID", residentId);
            Document update2 = new Document("$set",
                    new Document("currentRental.startDate", formattedStartDate)
                            .append("currentRental.endDate", formattedEndDate)
                            .append("currentRental.isActive", true)
            );

            UpdateResult result2 = collection.updateOne(query2, update2);

            if (result2.getModifiedCount() > 0) {
                System.out.println("Alquiler renovado");
                return true;
            }

            System.err.println("No se pudo renovar el alquiler para: " + residentId);
            return false;

        } catch (Exception e) {
            System.err.println("Error renovando alquiler: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String getPaymentStatus(String residentId) {
        try {
            Resident resident = searchResidentById(residentId);
            if (resident != null && resident.getCurrentRental() != null) {
                return resident.getCurrentRental().getPaymentStatus();
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error obteniendo estado de pago: " + e.getMessage());
            return null;
        }
    }

    public boolean updateSpaceOccupation(String spaceId, boolean isOccupied) {
        try {
            MongoDatabase database = MongoDBConnection.getConnection();
            MongoCollection<Document> collection = database.getCollection("ParkingSpaces");

            Document query = new Document("spaceId", spaceId);
            Document update = new Document("$set",
                    new Document("isOccupied", isOccupied)
            );

            UpdateResult result = collection.updateOne(query, update);

            if (result.getModifiedCount() > 0) {
                System.out.println("Space " + spaceId + " updated. isOccupied: " + isOccupied);
                return true;
            } else {
                System.err.println("Space not found or not updated: " + spaceId);
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error updating space occupation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean renewRentalWithSpace(String residentId, int selectedMonths, String spaceId) {
        try {
            if (collection == null) {
                System.err.println("Collection is null");
                return false;
            }

            residentId = cleanField(residentId);
            spaceId = cleanField(spaceId);

            System.out.println("Renovando alquiler para: " + residentId);
            System.out.println("   Meses seleccionados: " + selectedMonths);
            System.out.println("   Nuevo espacio: " + spaceId);

            Date startDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, selectedMonths);
            Date newEndDate = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedStartDate = sdf.format(startDate);
            String formattedEndDate = sdf.format(newEndDate);

            System.out.println("   Nueva fecha de inicio: " + formattedStartDate);
            System.out.println("   Nueva fecha de fin: " + formattedEndDate);
            // Actualizar con el nuevo espacio
            Document query1 = new Document("residents.residentID", residentId);
            Document update1 = new Document("$set",
                    new Document("residents.$.currentRental.startDate", formattedStartDate)
                            .append("residents.$.currentRental.endDate", formattedEndDate)
                            .append("residents.$.currentRental.spaceId", spaceId)
                            .append("residents.$.currentRental.isActive", true)
            );

            UpdateResult result1 = collection.updateOne(query1, update1);

            if (result1.getModifiedCount() > 0) {
                System.out.println("Alquiler renovado con nuevo espacio exitosamente");
                return true;
            }

            Document query2 = new Document("residentID", residentId);
            Document update2 = new Document("$set",
                    new Document("currentRental.startDate", formattedStartDate)
                            .append("currentRental.endDate", formattedEndDate)
                            .append("currentRental.spaceId", spaceId)
                            .append("currentRental.isActive", true)
            );

            UpdateResult result2 = collection.updateOne(query2, update2);

            if (result2.getModifiedCount() > 0) {
                System.out.println("Alquiler renovado con nuevo espacio");
                return true;
            }

            System.err.println("No se pudo renovar el alquiler para: " + residentId);
            return false;

        } catch (Exception e) {
            System.err.println("Error renovando alquiler con espacio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelRental(String residentId) {
        try {
            if (collection == null) {
                System.err.println("Collection is null");
                return false;
            }

            residentId = cleanField(residentId);
            System.out.println("Cancelando renta para: " + residentId);

            Resident resident = searchResidentById(residentId);
            if (resident == null || resident.getCurrentRental() == null) {
                System.err.println("No se encontró alquiler activo para: " + residentId);
                return false;
            }

            String currentStatus = resident.getCurrentRental().getPaymentStatus();
            System.out.println("Estado actual de pago: " + currentStatus);

            if (!"PAID".equalsIgnoreCase(currentStatus)) {
                System.err.println("No se puede cancelar renta con estado: " + currentStatus);
                return false;
            }

            String spaceId = resident.getCurrentRental().getSpaceId();
            System.out.println("Espacio a liberar: " + spaceId);

            Document query1 = new Document("residents.residentID", residentId);
            Document update1 = new Document("$set",
                    new Document("residents.$.currentRental.paymentStatus", "RENTAL_CANCELED")
                            .append("residents.$.currentRental.isActive", false)
            );

            UpdateResult result1 = collection.updateOne(query1, update1);

            if (result1.getModifiedCount() > 0) {
                System.out.println("Renta cancelada exitosamente para: " + residentId);
                return true;
            }

            Document query2 = new Document("residentID", residentId);
            Document update2 = new Document("$set",
                    new Document("currentRental.paymentStatus", "RENTAL_CANCELED")
                            .append("currentRental.isActive", false)
            );

            UpdateResult result2 = collection.updateOne(query2, update2);

            if (result2.getModifiedCount() > 0) {
                System.out.println("Renta cancelada (documento directo)");
                return true;
            }

            System.err.println("No se pudo cancelar la renta para: " + residentId);
            return false;

        } catch (Exception e) {
            System.err.println("Error cancelando renta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean activateRental(String residentId, int selectedMonths, String spaceId) {
        try {
            if (collection == null) {
                System.err.println("Collection is null");
                return false;
            }

            residentId = cleanField(residentId);
            spaceId = cleanField(spaceId);

            System.out.println("Activando renta para: " + residentId);
            System.out.println("   Meses seleccionados: " + selectedMonths);
            System.out.println("   Espacio: " + spaceId);
            System.out.println("   Precio mensual: $120.00");

            Resident resident = searchResidentById(residentId);
            if (resident == null) {
                System.err.println("No se encontró residente con ID: " + residentId);
                return false;
            }

            if (resident.getCurrentRental() != null) {
                String currentStatus = resident.getCurrentRental().getPaymentStatus();
                if (!"RENTAL_CANCELED".equalsIgnoreCase(currentStatus)) {
                    System.err.println("No se puede activar renta con estado: " + currentStatus);
                    return false;
                }
            }

            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.MONTH, selectedMonths);
            Date newEndDate = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedStartDate = sdf.format(today);
            String formattedEndDate = sdf.format(newEndDate);

            double monthlyPrice = 120.00;
            double totalAmount = monthlyPrice * selectedMonths;

            System.out.println("   Start Date (hoy): " + formattedStartDate);
            System.out.println("   End Date: " + formattedEndDate);
            System.out.println("   Total a pagar: $" + totalAmount);

            Document query1 = new Document("residents.residentID", residentId);
            Document update1 = new Document("$set",
                    new Document("residents.$.currentRental.startDate", formattedStartDate)
                            .append("residents.$.currentRental.endDate", formattedEndDate)
                            .append("residents.$.currentRental.monthlyPrice", totalAmount)
                            .append("residents.$.currentRental.spaceId", spaceId)
                            .append("residents.$.currentRental.paymentStatus", "PENDING")
                            .append("residents.$.currentRental.isActive", true)
            );

            UpdateResult result1 = collection.updateOne(query1, update1);

            if (result1.getModifiedCount() > 0) {
                System.out.println("Renta activada exitosamente");
                return true;
            }

            Document query2 = new Document("residentID", residentId);
            Document update2 = new Document("$set",
                    new Document("currentRental.startDate", formattedStartDate)
                            .append("currentRental.endDate", formattedEndDate)
                            .append("currentRental.monthlyPrice", totalAmount)
                            .append("currentRental.spaceId", spaceId)
                            .append("currentRental.paymentStatus", "PENDING")
                            .append("currentRental.isActive", true)
            );

            UpdateResult result2 = collection.updateOne(query2, update2);

            if (result2.getModifiedCount() > 0) {
                System.out.println("Renta activada (documento directo)");
                return true;
            }

            System.err.println("No se pudo activar la renta para: " + residentId);
            return false;

        } catch (Exception e) {
            System.err.println("Error activando renta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
