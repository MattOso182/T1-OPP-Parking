package model;

import java.util.ArrayList;
import java.util.List;

public class VisitorManager {
    private List<Visitor> visitors;
    
    public VisitorManager() {
        this.visitors = new ArrayList<>();
    }
    
    public void addVisitor(Visitor visitor) {
        this.visitors.add(visitor);
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
}