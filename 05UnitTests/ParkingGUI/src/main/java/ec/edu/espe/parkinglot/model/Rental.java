package ec.edu.espe.parkinglot.model;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

public class Rental {
    private String id;
    private String residentId;
    private String zoneId;
    private String startDate;
    private String endDate;
    
    public Rental() {}

    public Rental(String id, String residentId, String zoneId, String startDate, String endDate) {
        this.id = id;
        this.residentId = residentId;
        this.zoneId = zoneId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id; 
    }
    public void setId(String id) {
        this.id = id; 
    }
    public String getResidentId() {
        return residentId; 
    }
    public void setResidentId(String residentId) {
        this.residentId = residentId; 
    }
    public String getZoneId() {
        return zoneId;
    }
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId; 
    }
    public String getStartDate() {
        return startDate; 
    }
    public void setStartDate(String startDate) { 
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate; 
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

