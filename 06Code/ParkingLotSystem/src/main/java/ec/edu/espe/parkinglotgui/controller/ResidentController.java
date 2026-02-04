package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import ec.edu.espe.parkinglotgui.repository.ResidentRepository; 
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import ec.edu.espe.parkinglotgui.model.ParkingPriceStrategy;
import ec.edu.espe.parkinglotgui.model.ResidentParkingPrice;
import ec.edu.espe.parkinglotgui.repository.VehicleRepository;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection; 
import java.awt.GridLayout;
import org.bson.Document;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
*
 * @author T.A.P. (The Art of Programming), @ESPE
 */

public class ResidentController {

    private MongoCollection<Document> collection;
    private ResidentRepository repository; 

    public ResidentController() {
        this.repository = new ResidentRepository(); 
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
        if (input == null || input.trim().isEmpty()) return null;
        return repository.findById(input.trim()); 
    }

    public Resident searchResidentById(String residentId) {
        return searchResident(residentId);
    }

    public boolean updatePaymentStatusOnly(String residentId, String status) {
        try {
            residentId = cleanField(residentId);
            UpdateResult result = collection.updateOne(
                    new Document("residents.residentID", residentId),
                    new Document("$set", new Document("residents.$.currentRental.paymentStatus", status))
            );
            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean renewRentalWithSpace(String residentId, int months, String spaceId) {
        try {
            Resident resident = searchResidentById(residentId);
            if (resident != null && resident.getCurrentRental() != null) {
                String oldSpace = resident.getCurrentRental().getSpaceId();
                if (oldSpace != null && !oldSpace.equals(spaceId)) {
                    new ParkingSpaceController().freeParkingSpace(oldSpace);
                }
            }
            return activateRentalWithSpace(residentId, months, spaceId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean activateRentalWithSpace(String residentId, int months, String spaceId) {
        try {
            residentId = cleanField(residentId);
            spaceId = cleanField(spaceId);
            Resident resident = searchResidentById(residentId);
            if (resident == null) return false;

            Rental currentRental = resident.getCurrentRental();
            String currentSpace = currentRental != null ? currentRental.getSpaceId() : null;
            boolean isSameSpace = currentSpace != null && currentSpace.equals(spaceId);
            
            ParkingSpaceController spaceController = new ParkingSpaceController();
            Document spaceDetails = spaceController.getSpaceDetails(spaceId);

            if (spaceDetails == null || (spaceDetails.getBoolean("isOccupied", false) && !isSameSpace)) {
                return false;
            }

            if (isSameSpace && currentRental != null && !"RENTAL_CANCELED".equals(currentRental.getPaymentStatus())) {
                return updateRentalDates(residentId, months);
            }

            ParkingPriceStrategy pricing = new ResidentParkingPrice(); 
            double totalAmount = pricing.calculateTotal(months); 

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Date start = (currentRental != null && currentRental.getEndDate() != null) ? currentRental.getEndDate() : new Date();
            cal.setTime(start);
            cal.add(Calendar.MONTH, months);

            Document newRental = new Document("rentalId", "RENT-" + System.currentTimeMillis())
                    .append("residentId", residentId)
                    .append("spaceId", spaceId)
                    .append("startDate", sdf.format(start))
                    .append("endDate", sdf.format(cal.getTime()))
                    .append("paymentStatus", "PENDING")
                    .append("isActive", true)
                    .append("totalPrice", totalAmount)
                    .append("months", months)
                    .append("monthlyPrice", totalAmount / months); 

            UpdateResult res = collection.updateOne(
                    new Document("residents.residentID", residentId),
                    new Document("$set", new Document("residents.$.currentRental", newRental))
            );

            spaceController.updateSpaceOccupation(spaceId, true);
            if (currentSpace != null && !currentSpace.equals(spaceId)) spaceController.freeParkingSpace(currentSpace);
            
            return res.getModifiedCount() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean cancelRental(String residentId) {
        try {
            Resident resident = searchResidentById(residentId);
            if (resident == null || resident.getCurrentRental() == null) return false;
            String spaceId = resident.getCurrentRental().getSpaceId();
            if (spaceId != null) new ParkingSpaceController().freeParkingSpace(spaceId);
            
            return collection.updateOne(
                new Document("residents.residentID", cleanField(residentId)),
                new Document("$set", new Document("residents.$.currentRental.paymentStatus", "RENTAL_CANCELED")
                .append("residents.$.currentRental.isActive", false))
            ).getModifiedCount() > 0;
        } catch (Exception e) { return false; }
    }

    public List<Resident> getAllResidents() {
        List<Resident> residents = new ArrayList<>();
        for (Document doc : collection.find()) {
            List<Document> list = doc.containsKey("residents") ? doc.getList("residents", Document.class) : List.of(doc);
            for (Document rDoc : list) residents.add(repository.convertDocumentToResident(rDoc));
        }
        return residents;
    }

    public boolean updateRentalDates(String residentId, int addMonths) {
        try {
            Resident res = searchResidentById(residentId);
            if (res == null || res.getCurrentRental() == null) return false;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, addMonths);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            return collection.updateOne(new Document("residents.residentID", cleanField(residentId)),
                new Document("$set", new Document("residents.$.currentRental.endDate", sdf.format(cal.getTime()))
                .append("residents.$.currentRental.paymentStatus", "PENDING"))).getModifiedCount() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean deleteResident(String id) {
        return collection.deleteOne(new Document("residentID", cleanField(id))).getDeletedCount() > 0;
    }

    public void editResident(String id) {
        id = cleanField(id);
        Document doc = collection.find(new Document("residentID", id)).first();
        if (doc == null) return;
        JTextField name = new JTextField(doc.getString("name"));
        JPanel p = new JPanel(new GridLayout(0, 2));
        p.add(new JLabel("Nombre:")); p.add(name);
        if (JOptionPane.showConfirmDialog(null, p, "Editar", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            collection.updateOne(new Document("residentID", id), new Document("$set", new Document("name", name.getText().trim())));
        }
    }

    private String generateNextResidentId() {
        int max = 0;
        for (Document doc : collection.find()) {
            List<Document> list = doc.containsKey("residents") ? doc.getList("residents", Document.class) : List.of(doc);
            for (Document r : list) {
                String id = r.getString("residentID");
                if (id != null && id.startsWith("RES-")) max = Math.max(max, Integer.parseInt(id.replace("RES-", "")));
            }
        }
        return "RES-" + String.format("%03d", max + 1);
    }

    public boolean addResident(
        String name,
        String apartment,
        String email,
        String phone,
        List<Document> vehicles,
        List<String> authorizedVisitors,
        String userType,
        String parkingSpaceId
    ) {
        String residentId = generateNextResidentId();

        Document residentDoc = new Document()
                .append("residentID", residentId)
                .append("name", cleanField(name))
                .append("apartmentNumber", cleanField(apartment))
                .append("email", cleanField(email))
                .append("phone", cleanField(phone))
                .append("userType", userType)
                .append("vehicles", vehicles)
                .append("authorizedVisitors", authorizedVisitors);

        if ("WITH_PARKING".equals(userType) && parkingSpaceId != null) {
            Document rental = new Document()
                    .append("spaceId", parkingSpaceId)
                    .append("isActive", true)
                    .append("paymentStatus", "PENDING");

            residentDoc.append("currentRental", rental);
            new ParkingSpaceController().updateSpaceOccupation(parkingSpaceId, true);
        }

        collection.insertOne(residentDoc);

        Resident resident = new Resident();
        resident.setResidentID(residentId);
        resident.setName(cleanField(name));

        VehicleRepository vehicleRepository = new VehicleRepository();

        for (Document v : vehicles) {
            vehicleRepository.saveVehicle(
                    residentId,
                    resident.getName(),
                    v.getString("plate"),
                    v.getString("color"),
                    v.getString("model"),
                    false
            );
        }

        return true;
    }
    
    public void saveResidentVehicle(Resident resident, String plate, String color, String model) {
        VehicleRepository vehicleRepository = new VehicleRepository();

        vehicleRepository.saveVehicle(
            resident.getResidentID(),
            resident.getName(),
            plate,
            color,
            model,
            true
        );
    }
    
    public boolean updateResidentContactAndType(String residentId, String email, String phone, String userType) {
        try {
            residentId = cleanField(residentId);
            email = cleanField(email);
            phone = cleanField(phone);
            userType = cleanField(userType);

            if (email.isEmpty() || phone.isEmpty()) return false;

            UpdateResult result = collection.updateOne(
                new Document("residentID", residentId),
                new Document("$set", new Document()
                    .append("email", email)
                    .append("phone", phone)
                    .append("userType", userType)
                )
            );

            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}