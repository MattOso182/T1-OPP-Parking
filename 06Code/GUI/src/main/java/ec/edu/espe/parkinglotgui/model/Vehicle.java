package ec.edu.espe.parkinglotgui.model;

import org.bson.Document;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
public class Vehicle {
    private String plate;
    private String color;
    private String model;
    private boolean isParked;
    private String ownerId;

    public Vehicle() {
        this.isParked = false;
    }

    public Vehicle(String plate, String color, String model, String ownerId) {
        this.plate = plate;
        this.color = color;
        this.model = model;
        this.ownerId = ownerId;
        this.isParked = false;
    }

    public static Vehicle fromDocument(Document doc) {
        Vehicle vehicle = new Vehicle();
        
        vehicle.setPlate(doc.getString("plate"));
        vehicle.setColor(doc.getString("color"));
        vehicle.setModel(doc.getString("model"));
        
        if (doc.containsKey("ownerId")) {
            vehicle.setOwnerId(doc.getString("ownerId"));
        } else {
            vehicle.setOwnerId("");
        }
        
        if (doc.containsKey("isParked")) {
            vehicle.setParked(doc.getBoolean("isParked"));
        } else {
            vehicle.setParked(false);
        }
        
        return vehicle;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isParked() {
        return isParked;
    }

    public void setParked(boolean parked) {
        this.isParked = parked;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean registerVehicle() {
        if (validatePlate()) {
            System.out.println("Vehículo " + plate + " registrado exitosamente");
            return true;
        }
        System.out.println("Vehículo no matriculado. Placa no válida.");
        return false;
    }

    public void updateOwner(String newOwnerId) {
        this.ownerId = newOwnerId;
        System.out.println("Vehículo " + plate + " propiedad actualizada a: " + newOwnerId);
    }

    public void assignSpot(String spotId) {
        this.isParked = true;
        System.out.println("Vehículo " + plate + " asignado a: " + spotId);
    }

    public void releaseSpot() {
        this.isParked = false;
        System.out.println("Vehículo " + plate + " liberado del lugar");
    }

    public boolean validatePlate() {
        boolean isValid = plate != null && plate.length() >= 6 && plate.matches(".*[A-Z0-9].*");
        System.out.println("Validación de placas " + plate + ": " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }

    public String getVehicleInfo() {
        return "Placa: " + plate
                + "\nColor: " + color
                + "\nModelo: " + model
                + "\nID del Propietario: " + ownerId
                + "\nEstado: " + (isParked ? "Parked" : "Not parked");
    }
}
