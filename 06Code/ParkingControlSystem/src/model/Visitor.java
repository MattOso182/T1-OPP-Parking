package model;

/**
 *
 * @author @ESPE T.A.P(The Art of Programming)
 */
import java.util.Date;

public class Visitor {

    private String visitorID;
    private String name;
    private Date entryTime;
    private Date exitTime;
    private boolean isWaiting;
    private boolean hasTemporaryPass;

    public Visitor(String visitorID, String name) {
        this.visitorID = visitorID;
        this.name = name;
        this.isWaiting = false;
        this.hasTemporaryPass = false;
    }

    public boolean registerWait() {
        if (!isWaiting) {
            isWaiting = true;
            System.out.println("Visitor " + name + " is now waiting for authorization");
            return true;
        }
        return false;
    }

    public boolean assignTemporaryPass() {
        if (!hasTemporaryPass) {
            hasTemporaryPass = true;
            this.entryTime = new Date();
            System.out.println("Temporary pass assigned to visitor: " + name);
            return true;
        }
        return false;
    }

    public void recordParking() {
        this.exitTime = new Date();
        hasTemporaryPass = false;
        System.out.println("Parking recorded for visitor: " + name);
    }

    public boolean verifyIdentity() {
        return visitorID != null && !visitorID.isEmpty() && name != null && !name.isEmpty();
    }

    public boolean requestEntry() {
        System.out.println("\n--- ENTRY REQUEST ---");
        System.out.println("Visitor: " + name + " (ID: " + visitorID + ")");
        if (!verifyIdentity()) {
            System.out.println("REQUEST DENIED: Invalid identity");
            return false;
        }
        if (!registerWait()) {
            return false;
        }
        System.out.println("Visitor waiting for authorization...");
        return true;
    }

    public boolean recordExit() {
        if (hasTemporaryPass && exitTime == null) {
            this.exitTime = new Date();
            this.hasTemporaryPass = false;
            System.out.println("Exit recorded for visitor: " + name);
            if (entryTime != null) {
                long stayTime = (exitTime.getTime() - entryTime.getTime()) / (1000 * 60);
                System.out.println("Stay time: " + stayTime + " minutes");
            }
            return true;
        }
        return false;
    }

    public String getVisitorID() {
        return visitorID;
    }

    public String getName() {
        return name;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public boolean hasTemporaryPass() {
        return hasTemporaryPass;
    }

    public String getVisitorInfo() {
        return "Visitor ID: " + visitorID
                + "\nName: " + name
                + "\nStatus: " + (hasTemporaryPass ? "ACTIVE" : "INACTIVE")
                + "\nEntry Time: " + (entryTime != null ? entryTime : "Not entered")
                + "\nExit Time: " + (exitTime != null ? exitTime : "Not exited");
    }
}
