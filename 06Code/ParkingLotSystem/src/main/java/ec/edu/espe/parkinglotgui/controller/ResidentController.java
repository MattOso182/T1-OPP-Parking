package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
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
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

/*
 * @author T.A.P. (The Art of Programming), @ESPE
 */

public class ResidentController {

    private MongoCollection<Document> collection;
    private ResidentRepository repository; 
    private final String COLLECTION_NAME = "Residents"; 

    public ResidentController() {
        this.repository = new ResidentRepository(); 
        MongoDatabase database = MongoDBConnection.getConnection(); 
        if (database != null) {
            collection = database.getCollection(COLLECTION_NAME); 
        }
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
                Filters.eq("residentID", residentId),
                Updates.set("currentRental.paymentStatus", status)
            );
            
            return result.getModifiedCount() > 0;
            
        } catch (Exception e) {
            System.err.println("Error en updatePaymentStatusOnly: " + e.getMessage());
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
            
            Rental currentRental = resident.getCurrentRental();
            if (currentRental == null) {
                return false;
            }
            
            String oldSpace = currentRental.getSpaceId();
            if (oldSpace != null && !oldSpace.equals(spaceId)) {
                new ParkingSpaceController().freeParkingSpace(oldSpace);
            }
            
            return activateRentalWithSpace(residentId, months, spaceId);
            
        } catch (Exception e) {
            System.err.println("Error en renewRentalWithSpace: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean activateRentalWithSpace(String residentId, int months, String spaceId) {
        try {
            residentId = cleanField(residentId);
            spaceId = cleanField(spaceId);
            
            Resident resident = searchResidentById(residentId);
            if (resident == null) {
                return false;
            }
            
            ParkingSpaceController spaceController = new ParkingSpaceController();
            Document spaceDetails = spaceController.getSpaceDetails(spaceId);
            if (spaceDetails == null) {
                return false;
            }
            
            Rental currentRental = resident.getCurrentRental();
            String currentSpace = (currentRental != null) ? currentRental.getSpaceId() : null;
            boolean isSameSpace = currentSpace != null && currentSpace.equals(spaceId);
            
            if (spaceDetails.getBoolean("isOccupied", false) && !isSameSpace) {
                return false;
            }
            
            if (isSameSpace && currentRental != null && "PAID".equals(currentRental.getPaymentStatus())) {
                return updateRentalDates(residentId, months);
            }
            
            ParkingPriceStrategy pricing = new ResidentParkingPrice(); 
            double totalAmount = pricing.calculateTotal(months);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate;
            
            if (currentRental != null && currentRental.getEndDate() != null 
                && currentRental.getEndDate().after(new Date())) {
                startDate = currentRental.getEndDate();
            } else {
                startDate = new Date();
            }
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, months);
            Date endDate = cal.getTime();
            
            Document newRental = new Document()
                .append("rentalId", "RENT-" + System.currentTimeMillis())
                .append("residentId", residentId)
                .append("spaceId", spaceId)
                .append("startDate", sdf.format(startDate))
                .append("endDate", sdf.format(endDate))
                .append("paymentStatus", "PENDING")
                .append("isActive", true)
                .append("totalPrice", totalAmount)
                .append("months", months)
                .append("monthlyPrice", totalAmount / months)
                .append("createdAt", sdf.format(new Date()));
            
            UpdateResult result = collection.updateOne(
                Filters.eq("residentID", residentId),
                Updates.set("currentRental", newRental)
            );
            
            if (result.getModifiedCount() > 0) {
                
                spaceController.updateSpaceOccupation(spaceId, true);
                
                if (currentSpace != null && !currentSpace.equals(spaceId)) {
                    spaceController.freeParkingSpace(currentSpace);
                }
                
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error en activateRentalWithSpace: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelRental(String residentId) {
        try {
            Resident resident = searchResidentById(residentId);
            if (resident == null || resident.getCurrentRental() == null) {
                System.out.println("Residente no tiene renta activa");
                return false;
            }
            
            String spaceId = resident.getCurrentRental().getSpaceId();
            if (spaceId != null) {
                System.out.println("Liberando espacio: " + spaceId);
                new ParkingSpaceController().freeParkingSpace(spaceId);
            }
            
            UpdateResult result = collection.updateOne(
                Filters.eq("residentID", cleanField(residentId)),
                Updates.combine(
                    Updates.set("currentRental.paymentStatus", "RENTAL_CANCELED"),
                    Updates.set("currentRental.isActive", false)
                )
            );
            
            return result.getModifiedCount() > 0;
            
        } catch (Exception e) {
            System.err.println("Error en cancelRental: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Resident> getAllResidents() {
        List<Resident> residents = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                residents.add(repository.convertDocumentToResident(doc));
            }
        } catch (Exception e) {
            System.err.println("Error en getAllResidents: " + e.getMessage());
        }
        return residents;
    }

    public boolean updateRentalDates(String residentId, int addMonths) {
        try {
            Resident res = searchResidentById(residentId);
            if (res == null || res.getCurrentRental() == null) {
                System.out.println("No se puede extender: residente sin renta");
                return false;
            }
            
            Date currentEndDate = res.getCurrentRental().getEndDate();
            if (currentEndDate == null) {
                currentEndDate = new Date();
            }
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentEndDate);
            cal.add(Calendar.MONTH, addMonths);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newEndDate = sdf.format(cal.getTime());
            
            
            UpdateResult result = collection.updateOne(
                Filters.eq("residentID", cleanField(residentId)),
                Updates.combine(
                    Updates.set("currentRental.endDate", newEndDate),
                    Updates.set("currentRental.paymentStatus", "PENDING")
                )
            );
            
            return result.getModifiedCount() > 0;
            
        } catch (Exception e) {
            System.err.println("Error en updateRentalDates: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteResident(String id) {
        try {
            return collection.deleteOne(Filters.eq("residentID", cleanField(id))).getDeletedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error en deleteResident: " + e.getMessage());
            return false;
        }
    }

    public void editResident(String id) {
        try {
            id = cleanField(id);
            Document doc = collection.find(Filters.eq("residentID", id)).first();
            if (doc == null) return;
            
            JTextField name = new JTextField(doc.getString("name"));
            JPanel p = new JPanel(new GridLayout(0, 2));
            p.add(new JLabel("Nombre:")); 
            p.add(name);
            
            if (JOptionPane.showConfirmDialog(null, p, "Editar", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                collection.updateOne(
                    Filters.eq("residentID", id),
                    Updates.set("name", name.getText().trim())
                );
            }
        } catch (Exception e) {
            System.err.println("Error en editResident: " + e.getMessage());
        }
    }

    private String generateNextResidentId() {
        try {
            int max = 0;
            for (Document doc : collection.find()) {
                String id = doc.getString("residentID");
                if (id != null && id.startsWith("RES-")) {
                    try {
                        int num = Integer.parseInt(id.replace("RES-", ""));
                        max = Math.max(max, num);
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return "RES-" + String.format("%03d", max + 1);
        } catch (Exception e) {
            return "RES-001";
        }
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
        try {
            String residentId = generateNextResidentId();

            Document residentDoc = new Document()
                .append("residentID", residentId)
                .append("name", cleanField(name))
                .append("apartmentNumber", cleanField(apartment))
                .append("email", cleanField(email))
                .append("phone", cleanField(phone))
                .append("userType", userType)
                .append("vehicles", vehicles)
                .append("authorizedVisitors", authorizedVisitors)
                .append("createdAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            if ("WITH_PARKING".equals(userType) && parkingSpaceId != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(now);
                cal.add(Calendar.YEAR, 1); 
                
                Document rental = new Document()
                    .append("rentalId", "PERM-" + System.currentTimeMillis())
                    .append("residentId", residentId)
                    .append("spaceId", parkingSpaceId)
                    .append("startDate", sdf.format(now))
                    .append("endDate", sdf.format(cal.getTime()))
                    .append("paymentStatus", "PAID") 
                    .append("isActive", true)
                    .append("totalPrice", 0.0) 
                    .append("months", 12)
                    .append("monthlyPrice", 0.0)
                    .append("createdAt", sdf.format(now));

                residentDoc.append("currentRental", rental);
                new ParkingSpaceController().updateSpaceOccupation(parkingSpaceId, true);
            }

            collection.insertOne(residentDoc);

            VehicleRepository vehicleRepository = new VehicleRepository();
            for (Document v : vehicles) {
                vehicleRepository.saveVehicle(
                    residentId,
                    cleanField(name),
                    v.getString("plate"),
                    v.getString("color"),
                    v.getString("model"),
                    false
                );
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error en addResident: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public void saveResidentVehicle(Resident resident, String plate, String color, String model) {
        try {
            VehicleRepository vehicleRepository = new VehicleRepository();
            vehicleRepository.saveVehicle(
                resident.getResidentID(),
                resident.getName(),
                plate,
                color,
                model,
                true
            );
        } catch (Exception e) {
            System.err.println("Error en saveResidentVehicle: " + e.getMessage());
        }
    }
    
    public boolean updateResidentContactAndType(String residentId, String email, String phone, String userType) {
        try {
            residentId = cleanField(residentId);
            email = cleanField(email);
            phone = cleanField(phone);
            userType = cleanField(userType);

            if (email.isEmpty() || phone.isEmpty()) return false;

            UpdateResult result = collection.updateOne(
                Filters.eq("residentID", residentId),
                Updates.combine(
                    Updates.set("email", email),
                    Updates.set("phone", phone),
                    Updates.set("userType", userType)
                )
            );

            return result.getModifiedCount() > 0;
        } catch (Exception e) {
            System.err.println("Error en updateResidentContactAndType: " + e.getMessage());
            return false;
        }
    }
   
    public boolean residentExists(String residentId) {
        try {
            Document doc = collection.find(Filters.eq("residentID", cleanField(residentId))).first();
            return doc != null;
        } catch (Exception e) {
            System.err.println("Error en residentExists: " + e.getMessage());
            return false;
        }
    }
    
    public Object[][] getResidentsForTable() {
        List<Resident> residents = getAllResidents();
        Object[][] data = new Object[residents.size()][9];

        for (int i = 0; i < residents.size(); i++) {
            Resident r = residents.get(i);

            int vehicleCount = r.getVehicles() == null ? 0 : r.getVehicles().size();
            int visitorCount = r.getAuthorizedVisitors() == null ? 0 : r.getAuthorizedVisitors().size();
            String space = r.hasActiveRental() ? r.getCurrentRental().getSpaceId() : "";

            data[i][0] = r.getResidentID();
            data[i][1] = r.getName();
            data[i][2] = r.getApartmentNumber();
            data[i][3] = r.getEmail();
            data[i][4] = r.getPhone();
            data[i][5] = r.getUserType();
            data[i][6] = space;
            data[i][7] = vehicleCount;
            data[i][8] = visitorCount;
        }

        return data;
    }
    
    public Object[] getResidentRowById(String residentId) {
        Resident r = searchResidentById(residentId);
        if (r == null) return null;

        int vehicleCount = r.getVehicles() == null ? 0 : r.getVehicles().size();
        int visitorCount = r.getAuthorizedVisitors() == null ? 0 : r.getAuthorizedVisitors().size();
        String space = r.hasActiveRental() ? r.getCurrentRental().getSpaceId() : "";

        return new Object[]{
            r.getResidentID(),
            r.getName(),
            r.getApartmentNumber(),
            r.getEmail(),
            r.getPhone(),
            r.getUserType(),
            space,
            vehicleCount,
            visitorCount
        };
    }
    
    public String[] getAllResidentIds() {
        List<Resident> residents = getAllResidents();
        return residents.stream()
                .map(Resident::getResidentID)
                .toArray(String[]::new);
    }
    
    public boolean updateResidentFromForm(String residentId, String email, String phone, String userType) {
        return updateResidentContactAndType(residentId, email, phone, userType);
    }
    
    public Resident getResidentForDelete(String residentId) {
        return searchResidentById(residentId);
    }
    
    public boolean addResidentFromForm(
        String name,
        String apartment,
        String email,
        String phone,
        String userType,
        Object parkingSelection
    ) {
        if (name == null || name.trim().isEmpty()) return false;
        if (apartment == null || apartment.trim().isEmpty()) return false;
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) return false;
        if (phone == null || !phone.matches("^09\\d{8}$")) return false;

        String parkingSpaceId = null;

        if ("WITH_PARKING".equals(userType)) {
            if (parkingSelection == null || parkingSelection.toString().startsWith("Seleccionar")) {
                return false;
            }
            parkingSpaceId = parkingSelection.toString();
        }

        return addResident(
            name,
            apartment,
            email,
            phone,
            new ArrayList<>(),
            new ArrayList<>(),
            userType,
            parkingSpaceId
        );
    }
    
    public boolean authorizeVisitor(String residentId, String visitorId) {
        try {
            if (residentId == null || residentId.isBlank()) {
                return false;
            }

            visitorId = visitorId == null ? "" : visitorId.trim();

            if (!visitorId.matches("^\\d{10}$")) {
                return false;
            }

            Document doc = collection.find(
                Filters.eq("residentID", residentId)
            ).first();

            List<String> visitors;

            if (doc == null) {
                visitors = new ArrayList<>();
            } else {
                visitors = doc.getList(
                    "authorizedVisitors",
                    String.class,
                    new ArrayList<>()
                );
            }

            if (visitors.contains(visitorId)) {
                return false;
            }

            visitors.add(visitorId);

            collection.updateOne(
                Filters.eq("residentID", residentId),
                Updates.set("authorizedVisitors", visitors),
                new UpdateOptions().upsert(true)
            );

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addVehicleToResident(String residentId, String plate, String color, String model) {
        try {
            Resident resident = searchResidentById(residentId);
            if (resident == null) return false;

            plate = plate == null ? "" : plate.trim().toUpperCase();
            color = color == null ? "" : color.trim();
            model = model == null ? "" : model.trim();

            if (!plate.matches("^[A-Z]{3}-\\d{4}$")) return false;
            if (color.isEmpty() || model.isEmpty()) return false;

            List<Document> vehicles = new ArrayList<>();

            if (resident.getVehicles() != null) {
                resident.getVehicles().forEach(v -> {
                    Document d = new Document()
                        .append("plate", v.getPlate())
                        .append("color", v.getColor())
                        .append("model", v.getModel())
                        .append("isParked", v.isParked());
                    vehicles.add(d);
                });
            }

            for (Document v : vehicles) {
                if (plate.equals(v.getString("plate"))) {
                    return false;
                }
            }

            Document vehicle = new Document()
                .append("plate", plate)
                .append("color", color)
                .append("model", model)
                .append("isParked", false);

            vehicles.add(vehicle);

            UpdateResult result = collection.updateOne(
                Filters.eq("residentID", cleanField(residentId)),
                Updates.set("vehicles", vehicles)
            );

            if (result.getModifiedCount() > 0) {
                new VehicleRepository().saveVehicle(
                    residentId,
                    resident.getName(),
                    plate,
                    color,
                    model,
                    false
                );
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}