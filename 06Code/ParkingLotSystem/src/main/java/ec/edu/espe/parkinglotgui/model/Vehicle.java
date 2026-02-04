package ec.edu.espe.parkinglotgui.model;

import org.bson.Document;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class Vehicle {
    private String plate;
    private String color;
    private String model;
    private boolean isParked;
    private String ownerId;
    private String ownerName;
    public Vehicle() {
        this.isParked = false;
    }

    public Vehicle(String plate, String color, String model, String ownerId, String ownerName) {
        this.plate = plate;
        this.color = color;
        this.model = model;
        this.ownerId = ownerId;
        this.isParked = false;
        this.ownerName = ownerName;
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
        
         if (doc.containsKey("ownerName")) {
            vehicle.setOwnerName(doc.getString("ownerName"));
        } else {
            vehicle.setOwnerName("");
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
        return isIsParked();
    }

    public void setParked(boolean parked) {
        this.setIsParked(parked);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean registerVehicle() {
        if (validatePlate()) {
            System.out.println("Vehículo " + getPlate() + " registrado exitosamente");
            return true;
        }
        System.out.println("Vehículo no matriculado. Placa no válida.");
        return false;
    }

    public void updateOwner(String newOwnerId) {
        this.setOwnerId(newOwnerId);
        System.out.println("Vehículo " + getPlate() + " propiedad actualizada a: " + newOwnerId);
    }

    public void assignSpot(String spotId) {
        this.setIsParked(true);
        System.out.println("Vehículo " + getPlate() + " asignado a: " + spotId);
    }

    public void releaseSpot() {
        this.setIsParked(false);
        System.out.println("Vehículo " + getPlate() + " liberado del lugar");
    }

    public boolean validatePlate() {
        boolean isValid = getPlate() != null && getPlate().length() >= 6 && getPlate().matches(".*[A-Z0-9].*");
        System.out.println("Validación de placas " + getPlate() + ": " + (isValid ? "VALID" : "INVALID"));
        return isValid;
    }

    public String getVehicleInfo() {
        return "Placa: " + getPlate()
                + "\nColor: " + getColor()
                + "\nModelo: " + getModel()
                + "\nID del Propietario: " + getOwnerId()
                + "\nEstado: " + (isIsParked() ? "Parked" : "Not parked");
    }

    /**
     * @return the isParked
     */
    public boolean isIsParked() {
        return isParked;
    }

    /**
     * @param isParked the isParked to set
     */
    public void setIsParked(boolean isParked) {
        this.isParked = isParked;
    }

    /**
     * @return the ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param ownerName the ownerName to set
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
