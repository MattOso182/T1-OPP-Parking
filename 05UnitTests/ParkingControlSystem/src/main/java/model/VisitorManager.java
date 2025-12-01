package model;

/**
 * * * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.ArrayList;
import java.util.List;
import utils.JsonDataManager; 

public class VisitorManager {
    private List<Visitor> visitors;
    private final JsonDataManager dataManager; 

    public VisitorManager(JsonDataManager dataManager) { 
        this.dataManager = dataManager;
        this.visitors = dataManager.loadVisitorsData(); 
        if (this.visitors.isEmpty()) {
             this.visitors = new ArrayList<>();
        }
        System.out.println("VisitorManager: " + this.visitors.size() + " visitantes cargados.");
    }
    
    public void addVisitor(Visitor visitor) {
        if (findVisitorById(visitor.getVisitorID()) == null) {
            this.visitors.add(visitor);
            System.out.println("Visitante " + visitor.getName() + " agregado.");
            saveVisitors(); 
        }
    }

    public void saveVisitors() {
        dataManager.saveVisitorsData(this.visitors);
    }

    public Visitor findVisitorById(String visitorID) {
        for (Visitor v : visitors) {
            if (v.getVisitorID().equals(visitorID)) {
                return v;
            }
        }
        return null;
    }

    public boolean isVisitorAuthorized(String visitorID) {
        Visitor visitor = findVisitorById(visitorID);
        return visitor != null && visitor.hasTemporaryPass();
    }

    public boolean recordVisitorExit(String visitorID) {
        Visitor visitor = findVisitorById(visitorID);
        if (visitor != null) {
            boolean success = visitor.recordExit();
            if (success) {
                saveVisitors(); 
            }
            return success;
        }
        return false;
    }

    public void listAllVisitors() {
        for (Visitor v : visitors) {
            System.out.println(v.getVisitorInfo());
        }
    }

    public List<Visitor> getAllVisitors() {
        return new ArrayList<>(visitors);
    }
}