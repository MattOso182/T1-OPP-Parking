package ec.edu.espe.rentalresidentgui.model;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
public class Visitor {

    private String visitorID;
    private String nameVisitor;
    private String vehiclePlate;
    private String userID;
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
        this.userID = userID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
        info.append("=== VISITOR INFORMATION ===\n");
        info.append("Visitor ID: ").append(visitorID).append("\n");
        info.append("Name: ").append(nameVisitor).append("\n");
        info.append("Vehicle/Date: ").append(vehiclePlate).append("\n");
        info.append("User ID: ").append(userID).append("\n");
        info.append("Has Pass: ").append(hasPass ? "Yes" : "No").append("\n");
        info.append("Status: ").append(libraryVisitorStatus).append("\n");
        return info.toString();
    }

    @Override
    public String toString() {
        return nameVisitor + " (ID: " + visitorID + ")";
    }
}
