package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class Visitor {

    private String visitorID;
    private String nameVisitor;
    private String vehiclePlate;
    private String residentID;
    private boolean hasPass;
    private String libraryVisitorStatus; 

    public Visitor() {
        this.hasPass = false;
        this.libraryVisitorStatus = "ACTIVE";
    }

    public Visitor(String visitorID, String nameVisitor, String vehicleDate, String userID) {
        this.visitorID = visitorID;
        this.nameVisitor = nameVisitor;
        this.vehiclePlate = vehicleDate;
        this.residentID = userID;
        this.hasPass = false;
        this.libraryVisitorStatus = "ACTIVE";
    }

    public String getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(String visitorID) {
        this.visitorID = visitorID;
    }

    public String getNameVisitor() {
        return nameVisitor;
    }

    public void setNameVisitor(String nameVisitor) {
        this.nameVisitor = nameVisitor;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getResidentID() {
        return residentID;
    }

    public void setResidentID(String residentID) {
        this.residentID = residentID;
    }

    public boolean isHasPass() {
        return hasPass;
    }

    public void setHasPass(boolean hasPass) {
        this.hasPass = hasPass;
    }

    public String getLibraryVisitorStatus() {
        return libraryVisitorStatus;
    }

    public void setLibraryVisitorStatus(String libraryVisitorStatus) {
        this.libraryVisitorStatus = libraryVisitorStatus;
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== INFORMACIÓN DEL VISITANTE ===\n");
        info.append("ID del Visitante: ").append(visitorID).append("\n");
        info.append("Nombre: ").append(nameVisitor).append("\n");
        info.append("Vehículo/Fecha: ").append(vehiclePlate).append("\n");
        info.append("ID de Usuario: ").append(residentID).append("\n");
        info.append("Tiene pase: ").append(hasPass ? "Sí" : "No").append("\n");
        info.append("Estado: ").append(libraryVisitorStatus).append("\n");
        return info.toString();
    }

    @Override
    public String toString() {
        return nameVisitor + " (ID: " + visitorID + ")";
    }
}
