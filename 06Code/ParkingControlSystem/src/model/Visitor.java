package model;

/**
 * * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.Date;
import parkingcontrolsystem.library.VisitorLibrary;

public class Visitor extends User {

    private VisitorLibrary libraryVisitor;

    private boolean hasPass;

    public Visitor(String visitorID, String userID, String name, String vehiclePlate, Date entryTime, Date exitTime) {
        super(userID);

        this.libraryVisitor = new VisitorLibrary(
                visitorID, userID, name, vehiclePlate,
                entryTime,
                exitTime
        );
        this.hasPass = false;
    }

    public boolean verifyIdentity() {
        return this.libraryVisitor.verifyIdentity();
    }

    public boolean assignTemporaryPass() {
        if (this.libraryVisitor.registerVisit() && this.libraryVisitor.assignTemporarySpot()) {
            this.hasPass = true;
            setEntryTime(new Date());
            System.out.println("MODEL: Pase temporal y registro de entrada concedidos.");
            return true;
        }
        return false;
    }

    public boolean recordExit() {
        if (this.hasPass) {
            this.libraryVisitor.exitParking();
            this.hasPass = false;
            return true;
        }
        return false;
    }

    public boolean hasTemporaryPass() {
        return this.hasPass && this.libraryVisitor.getEntryTime() != null && this.libraryVisitor.getExitTime() == null;
    }

    public String getVisitorID() {
        return this.libraryVisitor.getVisitorID();
    }

    public void setVisitorID(String visitorID) {
        this.libraryVisitor.setVisitorID(visitorID);
    }

    public String getName() {
        return this.libraryVisitor.getName();
    }

    public void setName(String name) {
        this.libraryVisitor.setName(name);
    }

    public String getVehiclePlate() {
        return this.libraryVisitor.getVehiclePlate();
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.libraryVisitor.setVehiclePlate(vehiclePlate);
    }

    public void setEntryTime(Date entryTime) {
        this.libraryVisitor.setEntryTime(entryTime);
    }

    public Date getEntryTime() {
        return this.libraryVisitor.getEntryTime();
    }

    public Date getExitTime() {
        return this.libraryVisitor.getExitTime();
    }

    public String getVisitorInfo() {
        return this.libraryVisitor.getVisitorInfo() + "\nPass Active: " + this.hasPass;
    }
}
