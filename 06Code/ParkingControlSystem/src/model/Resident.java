package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.ArrayList;
import java.util.List;

public class Resident {

    private String residentID;
    private String name;
    private String apartmentNumber;
    private String email;
    private String phone;
    private UserType userType;
    private String assignedParkingSpace;
    private List<Vehicle> vehicles;
    private List<String> authorizedVisitors;
    private Rental currentRental;

    public Resident() {
        this.vehicles = new ArrayList<>();
        this.authorizedVisitors = new ArrayList<>();
    }

    public Resident(String residentID, String name, String apartmentNumber,
            String email, String phone, String assignedParkingSpace) {
        this.residentID = residentID;
        this.name = name;
        this.apartmentNumber = apartmentNumber;
        this.email = email;
        this.phone = phone;
        this.userType = UserType.WITH_PARKING;
        this.assignedParkingSpace = assignedParkingSpace;
        this.vehicles = new ArrayList<>();
        this.authorizedVisitors = new ArrayList<>();
        this.currentRental = null;
    }

    public Resident(String residentID, String name, String apartmentNumber,
            String email, String phone) {
        this.residentID = residentID;
        this.name = name;
        this.apartmentNumber = apartmentNumber;
        this.email = email;
        this.phone = phone;
        this.userType = UserType.ROTATING;
        this.assignedParkingSpace = null;
        this.vehicles = new ArrayList<>();
        this.authorizedVisitors = new ArrayList<>();
        this.currentRental = null;
    }

    public boolean hasActiveRental() {
        return currentRental != null && currentRental.isActive();
    }

    public String getRentedSpace() {
        return hasActiveRental() ? currentRental.getSpaceId() : null;
    }

    public void setCurrentRental(Rental rental) {
        this.currentRental = rental;
    }

    public Rental getCurrentRental() {
        return currentRental;
    }

    public boolean addVehicle(Vehicle vehicle) {
        for (Vehicle v : vehicles) {
            if (v.getPlate().equals(vehicle.getPlate())) {
                System.out.println("Vehiculo con placa " + vehicle.getPlate() + " ya existe");
                return false;
            }
        }
        vehicles.add(vehicle);
        System.out.println("Vehiculo " + vehicle.getPlate() + " agregado exitosamente");
        return true;
    }

    public boolean removeVehicle(String plate) {
        boolean removed = vehicles.removeIf(vehicle -> vehicle.getPlate().equals(plate));
        if (removed) {
            System.out.println("Vehiculo " + plate + " eliminado correctamente ");
        } else {
            System.out.println("Vehiculo " + plate + " not encontrado");
        }
        return removed;
    }

    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPlate().equals(plate)) {
                return vehicle;
            }
        }
        return null;
    }

    public boolean authorizeVisitor(String visitorID) {
        if (!authorizedVisitors.contains(visitorID)) {
            authorizedVisitors.add(visitorID);
            System.out.println("Visitante" + visitorID + " autorizado correctamente");
            return true;
        }
        System.out.println("Visitante" + visitorID + " ya autorizado");
        return false;
    }

    public boolean removeAuthorizedVisitor(String visitorID) {
        boolean removed = authorizedVisitors.remove(visitorID);
        if (removed) {
            System.out.println("Autorización del visitante: " + visitorID + " eliminada");
        } else {
            System.out.println("Visitante " + visitorID + " No se encuentra en la lista autorizada");
        }
        return removed;
    }

    public String getResidentID() {
        return residentID;
    }

    public String getName() {
        return name;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getAssignedParkingSpace() {
        return assignedParkingSpace;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<String> getAuthorizedVisitors() {
        return authorizedVisitors;
    }

    public String getCurrentParkingSpace() {
        if (userType == UserType.WITH_PARKING) {
            return assignedParkingSpace;
        } else if (hasActiveRental()) {
            return currentRental.getSpaceId();
        }
        return null;
    }

    public String getResidentInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== RESIDENT INFORMATION ===\n");
        info.append("ID: ").append(residentID).append("\n");
        info.append("Nombre: ").append(name).append("\n");
        info.append("Apartamento: ").append(apartmentNumber).append("\n");
        info.append("Tipo: ").append(userType).append("\n");
        info.append("Email: ").append(email).append("\n");
        info.append("Celular: ").append(phone).append("\n");

        if (userType == UserType.WITH_PARKING) {
            info.append("Espacio asignado: ").append(assignedParkingSpace).append("\n");
        } else {
            info.append("Alquiler activo: ").append(hasActiveRental() ? "SI" : "NO").append("\n");
            if (hasActiveRental()) {
                info.append("REspacio alquilado: ").append(currentRental.getSpaceId()).append("\n");
                info.append("Fin del alquiler:").append(currentRental.getEndDate()).append("\n");
            }
        }

        info.append("Vehiculos: ").append(vehicles.size()).append("\n");
        for (Vehicle vehicle : vehicles) {
            info.append("  - ").append(vehicle.getPlate()).append(" (").append(vehicle.getModel()).append(")\n");
        }

        info.append("Visitantes autorizados: ").append(authorizedVisitors.size()).append("\n");

        return info.toString();

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
