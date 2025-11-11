package model;

/**
 *
 * @author T.A.P(The Art of Programming)
 */
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ResidentManager {
private List<Resident> residents;
    private AtomicInteger rentalIdCounter;
    
    public ResidentManager() {
        this.residents = new ArrayList<>();
        this.rentalIdCounter = new AtomicInteger(1);
    }
    
    
    public boolean addResident(Resident resident) {
        for (Resident r : residents) {
            if (r.getResidentID().equals(resident.getResidentID())) {
                System.out.println("Resident with ID " + resident.getResidentID() + " already exists");
                return false;
            }
        }
        residents.add(resident);
        System.out.println("Resident " + resident.getName() + " added successfully");
        return true;
    }
    
    public Resident findResidentById(String residentID) {
        for (Resident resident : residents) {
            if (resident.getResidentID().equals(residentID)) {
                return resident;
            }
        }
        return null;
    }
    
    public Resident findResidentByVehiclePlate(String plate) {
        for (Resident resident : residents) {
            if (resident.findVehicleByPlate(plate) != null) {
                return resident;
            }
        }
        return null;
    }
    
    public boolean removeResident(String residentID) {
        Resident resident = findResidentById(residentID);
        if (resident != null) {
            residents.remove(resident);
            System.out.println("Resident " + residentID + " removed successfully");
            return true;
        }
        System.out.println("Resident " + residentID + " not found");
        return false;
    }
    
    public Rental createRentalForResident(String residentId, String spaceId, int months, double monthlyPrice) {
        Resident resident = findResidentById(residentId);
        if (resident == null || resident.getUserType() != UserType.ROTATING) {
            System.out.println("Invalid resident or resident is not ROTATING type");
            return null;
        }
        
        if (resident.hasActiveRental()) {
            System.out.println("Resident already has an active rental");
            return null;
        }
        
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, months);
        Date endDate = calendar.getTime();
        
        String rentalId = "RENT-" + rentalIdCounter.getAndIncrement();
        Rental newRental = new Rental(rentalId, residentId, spaceId, startDate, endDate, monthlyPrice);
       
        resident.setCurrentRental(newRental);
        System.out.println("New rental created: " + rentalId + " for resident: " + resident.getName());
        return newRental;
    }
    
    public boolean cancelRentalForResident(String residentId) {
        Resident resident = findResidentById(residentId);
        if (resident == null || !resident.hasActiveRental()) {
            System.out.println("Resident not found or has no active rental");
            return false;
        }
        
        boolean cancelled = resident.getCurrentRental().cancelRental();
        if (cancelled) {
            resident.setCurrentRental(null); 
            System.out.println("Rental cancelled for resident: " + resident.getName());
        }
        return cancelled;
    }
    
    public boolean renewRentalForResident(String residentId, int additionalMonths) {
        Resident resident = findResidentById(residentId);
        if (resident == null || !resident.hasActiveRental()) {
            System.out.println("Resident not found or has no active rental");
            return false;
        }
       
        return resident.getCurrentRental().renewRental(additionalMonths);
    }
    
    public boolean processPaymentForRental(String residentId) {
        Resident resident = findResidentById(residentId);
        if (resident == null || !resident.hasActiveRental()) {
            System.out.println("Resident not found or has no active rental");
            return false;
        }
       
        return resident.getCurrentRental().processPayment();
    }
    
    public List<Resident> getAllResidents() {
        return new ArrayList<>(residents);
    }
    
    public List<Resident> getResidentsWithParking() {
        List<Resident> result = new ArrayList<>();
        for (Resident resident : residents) {
            if (resident.getUserType() == UserType.WITH_PARKING) {
                result.add(resident);
            }
        }
        return result;
    }
    
    public List<Resident> getRotatingResidents() {
        List<Resident> result = new ArrayList<>();
        for (Resident resident : residents) {
            if (resident.getUserType() == UserType.ROTATING) {
                result.add(resident);
            }
        }
        return result;
    }
    
    public List<Resident> getRotatingResidentsWithRental() {
        List<Resident> result = new ArrayList<>();
        for (Resident resident : residents) {
            if (resident.getUserType() == UserType.ROTATING && resident.hasActiveRental()) {
                result.add(resident);
            }
        }
        return result;
    }
    
    public List<Rental> getAllActiveRentals() {
        List<Rental> activeRentals = new ArrayList<>();
        for (Resident resident : residents) {
            if (resident.hasActiveRental()) {
                activeRentals.add(resident.getCurrentRental());
            }
        }
        return activeRentals;
    }
    
    public List<Rental> getExpiredRentals() {
        List<Rental> expired = new ArrayList<>();
        for (Resident resident : residents) {
            if (resident.hasActiveRental() && resident.getCurrentRental().isExpired()) {
                expired.add(resident.getCurrentRental());
            }
        }
        return expired;
    }
    
    public boolean authorizeVisitor(String residentId, String visitorId) {
        Resident resident = findResidentById(residentId);
        return resident != null && resident.authorizeVisitor(visitorId);
    }
    
    public boolean removeAuthorizedVisitor(String residentId, String visitorId) {
        Resident resident = findResidentById(residentId);
        return resident != null && resident.removeAuthorizedVisitor(visitorId);
    }
    
    public boolean addVehicleToResident(String residentId, Vehicle vehicle) {
        Resident resident = findResidentById(residentId);
        return resident != null && resident.addVehicle(vehicle);
    }
    
    public boolean removeVehicleFromResident(String residentId, String plate) {
        Resident resident = findResidentById(residentId);
        return resident != null && resident.removeVehicle(plate);
    }
}
