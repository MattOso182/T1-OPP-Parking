package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class Vehicle {
    private String plate;
    private String color;
    private String model;
    private boolean isParked;
    private String ownerId;
    
    public Vehicle(String plate, String color, String model, String ownerId) {
        this.plate = plate;
        this.color = color;
        this.model = model;
        this.ownerId = ownerId;
        this.isParked = false;
    }
    
    public boolean registerVehicle() {
        if (validatePlate()) {
            System.out.println("Vehiculo " + plate + " registrado correctamente");
            return true;
        }
        System.out.println("vehiculo no registrado. Placa invalida");
        return false;
    }
    
    public void updateOwner(String newOwnerId) {
        this.ownerId = newOwnerId;
        System.out.println("Vehículo" + plate + " propiedad actualizada a: " + newOwnerId);
    }
    
    public void assignSpot(String spotId) {
        this.isParked = true;
        System.out.println("Vehiculo " + plate + " asignado a: " + spotId);
    }
    
    public void releaseSpot() {
        this.isParked = false;
        System.out.println("Vehiculo" + plate + " liberado del lugar");
    }
    
    public boolean validatePlate() {
        boolean isValid = plate != null && plate.length() >= 6 && plate.matches(".*[A-Z0-9].*");
        System.out.println(" Validacion de placa " + plate + ": " + (isValid ? "VALIDA" : "INVALIDA"));
        return isValid;
    }
    
    // Getters and Setters
    public String getPlate() { return plate; }
    public String getColor() { return color; }
    public String getModel() { return model; }
    public boolean isParked() { return isParked; }
    public String getOwnerId() { return ownerId; }
    
    public String getVehicleInfo() {
        return "Placa: " + plate +
               "\nColor: " + color +
               "\nModelo: " + model +
               "\nOwner ID: " + ownerId +
               "\nStatus: " + (isParked ? "Estacionado" : "No estacionado");
    }
}