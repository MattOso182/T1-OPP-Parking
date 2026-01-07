package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.awt.GridLayout;
import org.bson.Document;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        if (field == null) {
            return "";
        }
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

    public boolean activateRentalWithSpace(String residentId, int months, String spaceId) {
        try {
            residentId = cleanField(residentId);
            spaceId = cleanField(spaceId);

            Resident resident = searchResidentById(residentId);
            if (resident == null) {
                return false;
            }

            Rental currentRental = resident.getCurrentRental();
            String currentSpace = currentRental != null ? currentRental.getSpaceId() : null;

            boolean isSameSpace = currentSpace != null && currentSpace.equals(spaceId);

            boolean isCancelledRental = currentRental != null
                    && "RENTAL_CANCELED".equalsIgnoreCase(currentRental.getPaymentStatus());

            ParkingSpaceController spaceController = new ParkingSpaceController();
            Document spaceDetails = spaceController.getSpaceDetails(spaceId);

            if (spaceDetails == null) {
                throw new IllegalStateException("Parking space not found: " + spaceId);
            }

            Boolean isOccupied = spaceDetails.getBoolean("isOccupied");

            if (isOccupied != null && isOccupied && !isSameSpace) {
                throw new IllegalStateException("Parking space not available");
            }

            if (isSameSpace && !isCancelledRental) {
                return updateRentalDates(residentId, months);
            }

            Date startDate;
            if (isCancelledRental) {
                startDate = new Date();
            } else if (currentRental != null && currentRental.getEndDate() != null) {
                startDate = currentRental.getEndDate();
            } else {
                startDate = new Date();
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, months);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Document newRentalDoc = new Document();

            if (currentRental != null && currentRental.getRentalId() != null) {
                newRentalDoc.append("rentalId", currentRental.getRentalId());
            } else {
                String newRentalId = "RENT-" + System.currentTimeMillis();
                newRentalDoc.append("rentalId", newRentalId);
            }

            newRentalDoc.append("residentId", residentId);
            newRentalDoc.append("spaceId", spaceId);
            newRentalDoc.append("startDate", sdf.format(startDate));
            newRentalDoc.append("endDate", sdf.format(cal.getTime()));
            newRentalDoc.append("paymentStatus", "PENDING");
            newRentalDoc.append("isActive", true);

            double totalAmount = 45.00 * months;
            newRentalDoc.append("totalPrice", totalAmount);
            newRentalDoc.append("months", months);

            if (currentRental != null && currentRental.getMonthlyPrice() > 0) {
                newRentalDoc.append("monthlyPrice", currentRental.getMonthlyPrice());
            } else {
                newRentalDoc.append("monthlyPrice", 45.00);
            }

            UpdateResult residentUpdate = collection.updateOne(
                    new Document("residents.residentID", residentId),
                    new Document("$set", new Document("residents.$.currentRental", newRentalDoc))
            );

            spaceController.updateSpaceOccupation(spaceId, true);

            if (currentSpace != null && !currentSpace.isEmpty() && !currentSpace.equals(spaceId)) {
                spaceController.freeParkingSpace(currentSpace);
            }

            return residentUpdate.getModifiedCount() > 0;

        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Error activating rental: " + e.getMessage());
        }
    }

    public boolean cancelRental(String residentId) {
        try {
            residentId = cleanField(residentId);

            Resident resident = searchResidentById(residentId);
            if (resident == null) {
                System.err.println("Residente no encontrado: " + residentId);
                return false;
            }

            if (resident.getCurrentRental() == null) {
                System.err.println("El residente no tiene renta activa");
                return false;
            }

            String currentSpace = resident.getCurrentRental().getSpaceId();

            if (currentSpace != null && !currentSpace.isEmpty()) {
                MongoDatabase db = MongoDBConnection.getConnection();
                MongoCollection<Document> spaceCollection = db.getCollection("ParkingSpaces");

                ParkingSpaceController spaceController = new ParkingSpaceController();
                boolean spaceFreed = spaceController.freeParkingSpace(currentSpace);
            }

            Document filter = new Document("residents.residentID", residentId);
            Document update = new Document("$set",
                    new Document("residents.$.currentRental.paymentStatus", "RENTAL_CANCELED")
                            .append("residents.$.currentRental.isActive", false)
            );

            UpdateResult result = collection.updateOne(filter, update);

            System.out.println("Cancelación - Matched: " + result.getMatchedCount()
                    + ", Modified: " + result.getModifiedCount());

            return result.getModifiedCount() > 0;

        } catch (Exception e) {
            System.err.println("Error cancelando renta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean renewRentalWithSpace(String residentId, int months, String spaceId) {
        try {
            Resident resident = searchResidentById(residentId);
            if (resident == null) {
                return false;
            }

            String currentSpace = null;
            if (resident.getCurrentRental() != null) {
                currentSpace = resident.getCurrentRental().getSpaceId();
            }

            if (currentSpace != null && !currentSpace.isEmpty() && !currentSpace.equals(spaceId)) {
                ParkingSpaceController spaceController = new ParkingSpaceController();
                spaceController.freeParkingSpace(currentSpace);
            }

            return activateRentalWithSpace(residentId, months, spaceId);

        } catch (Exception e) {
            System.err.println("Error in renewRentalWithSpace: " + e.getMessage());
            return false;
        }
    }

    public boolean renewRentalFromToday(String residentId, int months, String spaceId) {
        return renewRentalWithSpace(residentId, months, spaceId);
    }

    public boolean updatePaymentStatusOnly(String residentId, String status) {
        try {
            residentId = cleanField(residentId);
            if (residentId.isEmpty() || status == null || status.isEmpty()) {
                return false;
            }

            if (collection == null) {
                System.err.println("Error: La colección no está inicializada");
                return false;
            }

            UpdateResult result = collection.updateOne(
                    new Document("residents.residentID", residentId),
                    new Document("$set", new Document("residents.$.currentRental.paymentStatus", status))
            );

            return result.getModifiedCount() > 0;

        } catch (com.mongodb.MongoException e) {
            System.err.println("Error de MongoDB al actualizar estado de pago: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error inesperado al actualizar estado de pago: " + e.getMessage());
            return false;
        }
    }

    public List<Resident> getAllResidents() {
        List<Resident> residents = new ArrayList<>();

        for (Document mainDoc : collection.find()) {
            if (mainDoc.containsKey("residents")) {
                for (Document rDoc : mainDoc.getList("residents", Document.class)) {
                    residents.add(convertDocumentToResident(rDoc));
                }
            } else {
                residents.add(convertDocumentToResident(mainDoc));
            }
        }

        return residents;
    }

    public boolean updateRentalDates(String residentId, int additionalMonths) {
        try {
            residentId = cleanField(residentId);

            Resident resident = searchResidentById(residentId);
            if (resident == null || resident.getCurrentRental() == null) {
                return false;
            }

            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.MONTH, additionalMonths);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newEndDate = sdf.format(cal.getTime());

            String newStartDate = sdf.format(today);

            Document filter = new Document("residents.residentID", residentId);
            Document update = new Document("$set",
                    new Document("residents.$.currentRental.endDate", newEndDate)
                            .append("residents.$.currentRental.startDate", newStartDate)
                            .append("residents.$.currentRental.paymentStatus", "PENDING")
            );

            UpdateResult result = collection.updateOne(filter, update);

            return result.getModifiedCount() > 0;

        } catch (Exception e) {
            System.err.println("Error actualizando fechas de renta: " + e.getMessage());
            return false;
        }
    }
    
    public boolean addResident(String name, String apartment, String phone) {
        return addResident(name, apartment, phone, null, false);
    }

    public boolean deleteResident(String residentId) {
        residentId = cleanField(residentId);
        return collection.deleteOne(new Document("residentID", residentId)).getDeletedCount() > 0;
    }
    
    public void editResident(String residentId) {
        residentId = cleanField(residentId);

        Document resident = collection.find(new Document("residentID", residentId)).first();
        if (resident == null) return;

        JTextField nameField = new JTextField(resident.getString("name"));
        JTextField phoneField = new JTextField(resident.getString("phone"));

        String[] apartments = {
            "A-101","A-102","A-103",
            "B-201","B-202","B-203",
            "C-301","C-302","C-303",
            "D-401","D-402","D-403"
        };

        JComboBox<String> aptCombo = new JComboBox<>(apartments);
        aptCombo.setSelectedItem(resident.getString("apartmentNumber"));

        JCheckBox rentalCheck = new JCheckBox("Renta activa");
        rentalCheck.setSelected(resident.getBoolean("activeRental", false));

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);
        panel.add(new JLabel("Apartamento:"));
        panel.add(aptCombo);
        panel.add(new JLabel("Celular:"));
        panel.add(phoneField);
        panel.add(new JLabel("Renta activa:"));
        panel.add(rentalCheck);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Editar Residente",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) return;

        Document update = new Document("$set",
                new Document("name", nameField.getText().trim())
                        .append("apartmentNumber", aptCombo.getSelectedItem())
                        .append("phone", phoneField.getText().trim())
                        .append("activeRental", rentalCheck.isSelected())
        );

        collection.updateOne(new Document("residentID", residentId), update);
    }

    private String generateNextResidentId() {
        int max = 0;

        for (Document doc : collection.find()) {
            if (doc.containsKey("residents")) {
                for (Document r : doc.getList("residents", Document.class)) {
                    String id = r.getString("residentID");
                    if (id != null && id.startsWith("RES-")) {
                        int num = Integer.parseInt(id.replace("RES-", ""));
                        max = Math.max(max, num);
                    }
                }
            } else {
                String id = doc.getString("residentID");
                if (id != null && id.startsWith("RES-")) {
                    int num = Integer.parseInt(id.replace("RES-", ""));
                    max = Math.max(max, num);
                }
            }
        }

        return "RES-" + String.format("%03d", max + 1);
    }

    public boolean addResident(String name, String apartment, String phone, String plate, boolean hasParking) {

        String residentId = generateNextResidentId();

        List<Document> vehicles = new ArrayList<>();

        if (plate != null && !plate.isEmpty()) {
            vehicles.add(new Document()
                    .append("plate", plate)
                    .append("color", "")
                    .append("model", "")
                    .append("ownerId", residentId)
                    .append("isParked", false));
        }

        Document residentDoc = new Document()
                .append("residentID", residentId)
                .append("name", cleanField(name))
                .append("apartmentNumber", cleanField(apartment))
                .append("phone", cleanField(phone))
                .append("vehicles", vehicles);

        if (hasParking) {
            residentDoc.append("currentRental", null);
        }

        collection.insertOne(residentDoc);
        return true;
    }

}