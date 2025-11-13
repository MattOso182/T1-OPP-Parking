package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import model.ResidentData;
import model.ResidentDataManager;

public class ResidentManager {

    private List<Resident> residents;
    private AtomicInteger rentalIdCounter;
    private ResidentDataManager dataManager;

    public ResidentManager() {
        this.dataManager = new ResidentDataManager();
        this.residents = new ArrayList<>();
        this.rentalIdCounter = new AtomicInteger(1);
        loadResidentsFromJson();
    }

    private void loadResidentsFromJson() {
        ResidentData residentData = dataManager.loadResidentsData();
        if (residentData != null && residentData.getResidents() != null) {
            this.residents = residentData.getResidents();
            System.out.println("Loaded " + residents.size() + " residents from JSON");

            setupRentalIdCounter();
        } else {
            System.out.println(" No se encontraron datos de residentes, comenzando con una lista vacia");
            this.residents = new ArrayList<>();
        }
    }

    private void setupRentalIdCounter() {
        int maxId = 0;
        for (Resident resident : residents) {
            if (resident.hasActiveRental()) {
                String rentalId = resident.getCurrentRental().getRentalId();
                if (rentalId.startsWith("RENT-")) {
                    try {
                        int id = Integer.parseInt(rentalId.substring(5));
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        this.rentalIdCounter = new AtomicInteger(maxId + 1);
    }

    private void saveResidentsToJson() {
        dataManager.saveResidentsData(residents);
    }

    public boolean addResident(Resident resident) {
        for (Resident r : residents) {
            if (r.getResidentID().equals(resident.getResidentID())) {
                System.out.println("Ya existe un residente con ID: " + resident.getResidentID());
                return false;
            }
        }
        residents.add(resident);
        saveResidentsToJson();
        System.out.println("Residente " + resident.getName() + " agregado correctamente");
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
            saveResidentsToJson();
            System.out.println("Residente " + residentID + " eliminado con exito");
            return true;
        }
        System.out.println("Residente " + residentID + "no encontrado");
        return false;
    }

    public boolean updateResidentInfo(String residentID, String newEmail, String newPhone) {
        Resident resident = findResidentById(residentID);
        if (resident != null) {
            resident.setEmail(newEmail);
            resident.setPhone(newPhone);
            saveResidentsToJson();
            System.out.println("Informacion del residente " + residentID + " actualizada");
            return true;
        }
        System.out.println("No se encontro al residente " + residentID );
        return false;
    }

    public boolean addVehicleToResident(String residentID, Vehicle vehicle) {
        Resident resident = findResidentById(residentID);
        if (resident != null && resident.addVehicle(vehicle)) {
            saveResidentsToJson();
            return true;
        }
        return false;
    }

    public boolean removeVehicleFromResident(String residentID, String plate) {
        Resident resident = findResidentById(residentID);
        if (resident != null && resident.removeVehicle(plate)) {
            saveResidentsToJson();
            return true;
        }
        return false;
    }

    public Vehicle findVehicleByPlate(String plate) {
        for (Resident resident : residents) {
            Vehicle vehicle = resident.findVehicleByPlate(plate);
            if (vehicle != null) {
                return vehicle;
            }
        }
        return null;
    }

    public boolean authorizeVisitor(String residentId, String visitorId) {
        Resident resident = findResidentById(residentId);
        if (resident != null && resident.authorizeVisitor(visitorId)) {
            saveResidentsToJson();
            return true;
        }
        return false;
    }

    public boolean removeAuthorizedVisitor(String residentId, String visitorId) {
        Resident resident = findResidentById(residentId);
        if (resident != null && resident.removeAuthorizedVisitor(visitorId)) {
            saveResidentsToJson();
            return true;
        }
        return false;
    }

    public Rental createRentalForResident(String residentId, String spaceId, int months, double monthlyPrice) {
        Resident resident = findResidentById(residentId);
        if (resident == null || resident.getUserType() != UserType.ROTATING) {
            System.out.println("Residente no valido o residente no es de tipo ROTATORIO");
            return null;
        }

        if (resident.hasActiveRental()) {
            System.out.println("El residente ya tiene un contrato de alquiler activo.");
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, months);
        Date endDate = calendar.getTime();

        String rentalId = "RENT-" + rentalIdCounter.getAndIncrement();
        Rental newRental = new Rental(rentalId, residentId, spaceId, startDate, endDate, monthlyPrice);

        resident.setCurrentRental(newRental);
        saveResidentsToJson();
        System.out.println("Nuevo alquiler creado " + rentalId + " para el residente " + resident.getName());
        return newRental;
    }

    public boolean cancelRentalForResident(String residentId) {
        Resident resident = findResidentById(residentId);
        if (resident == null || !resident.hasActiveRental()) {
            System.out.println("No se encontro al residente o no tiene un alquiler activo.");
            return false;
        }

        boolean cancelled = resident.getCurrentRental().cancelRental();
        if (cancelled) {
            resident.setCurrentRental(null);
            saveResidentsToJson();
            System.out.println("Alquier cancelado por el residente: " + resident.getName());
        }
        return cancelled;
    }

    public boolean renewRentalForResident(String residentId, int additionalMonths) {
        Resident resident = findResidentById(residentId);
        if (resident == null || !resident.hasActiveRental()) {
            System.out.println("No se encontro al residente o no tiene un alquiler activo.");
            return false;
        }

        boolean renewed = resident.getCurrentRental().renewRental(additionalMonths);
        if (renewed) {
            saveResidentsToJson();
            System.out.println("Alquiler renovado para el residente: " + resident.getName());
        }
        return renewed;
    }

    public boolean processPaymentForRental(String residentId) {
        Resident resident = findResidentById(residentId);
        if (resident == null || !resident.hasActiveRental()) {
            System.out.println("No se encontro al residente o no tiene un alquiler activo.");
            return false;
        }

        boolean processed = resident.getCurrentRental().processPayment();
        if (processed) {
            saveResidentsToJson();
            System.out.println("Pago procesado para el residente:" + resident.getName());
        }
        return processed;
    }

    public int getTotalVehicles() {
        int count = 0;
        for (Resident resident : residents) {
            count += resident.getVehicles().size();
        }
        return count;
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

    public String generateResidentsReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== RESIDENTS REPORT ===\n");
        report.append("Total residentes: ").append(residents.size()).append("\n");
        report.append("Con estacionamiento: ").append(getResidentsWithParking().size()).append("\n");
        report.append("Rotante: ").append(getRotatingResidents().size()).append("\n");
        report.append("Aquileres activos: ").append(getRotatingResidentsWithRental().size()).append("\n");
        report.append("Alquileres vencidos ").append(getExpiredRentals().size()).append("\n\n");

        for (Resident resident : residents) {
            report.append(resident.getResidentInfo()).append("\n");
            report.append("----------------------------\n");
        }

        return report.toString();
    }

    public String generateRentalsReport() {
        StringBuilder report = new StringBuilder();
        List<Rental> activeRentals = getAllActiveRentals();

        report.append("=== RENTALS REPORT ===\n");
        report.append("Aquileres activos: ").append(activeRentals.size()).append("\n");
        report.append("Alquileres vencidos: ").append(getExpiredRentals().size()).append("\n\n");

        for (Rental rental : activeRentals) {
            report.append(rental.getRentalInfo()).append("\n");
            report.append("----------------------------\n");
        }

        return report.toString();
    }

    public String generateVehiclesReport() {
        StringBuilder report = new StringBuilder();
        int totalVehicles = 0;

        report.append("=== REPORTE DE VEHICULOS ===\n");

        for (Resident resident : residents) {
            totalVehicles += resident.getVehicles().size();
        }

        report.append("Total de vehiculos: ").append(totalVehicles).append("\n\n");

        for (Resident resident : residents) {
            if (!resident.getVehicles().isEmpty()) {
                report.append("Residente: ").append(resident.getName()).append("\n");
                for (Vehicle vehicle : resident.getVehicles()) {
                    report.append("  - ").append(vehicle.getPlate())
                            .append(" (").append(vehicle.getModel()).append(")\n");
                }
                report.append("\n");
            }
        }

        return report.toString();
    }

    public void refreshData() {
        loadResidentsFromJson();
        System.out.println("Residents data refreshed from JSON");
    }

    public int getTotalResidents() {
        return residents.size();
    }

    public boolean validateVisitorAccess(String visitorId) {
        for (Resident resident : residents) {
            if (resident.getAuthorizedVisitors().contains(visitorId)) {
                System.out.println("Visitante " + visitorId + " autorizado por: " + resident.getName());
                return true;
            }
        }
        System.out.println("Visitante " + visitorId + " NO tiene autorizacion de ningun residente");
        return false;
    }

    public Resident findAuthorizingResident(String visitorId) {
        for (Resident resident : residents) {
            if (resident.getAuthorizedVisitors().contains(visitorId)) {
                return resident;
            }
        }
        return null;
    }

    public boolean processVisitorEntry(Visitor visitor) {
        System.out.println("\n--- PROCESANDO INGRESO DE VISITANTE ---");
        System.out.println("Visitante: " + visitor.getName() + " (ID: " + visitor.getVisitorID() + ")");

        if (!visitor.verifyIdentity()) {
            System.out.println("ACCESO DENEGADO: Identidad no valida");
            return false;
        }

        if (!validateVisitorAccess(visitor.getVisitorID())) {
            System.out.println("ACCESO DENEGADO: No tiene autorizacion de residente");
            return false;
        }

        if (visitor.assignTemporaryPass()) {
            Resident authorizingResident = findAuthorizingResident(visitor.getVisitorID());
            System.out.println("ACCESO PERMITIDO: Visitante autorizado por "
                    + authorizingResident.getName() + " puede ingresar");
            return true;
        }

        return false;
    }
}
