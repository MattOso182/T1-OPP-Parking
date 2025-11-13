package parkingcontrolsystem.library;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import java.util.Date;

public class RentalLibrary {
    private String rentalId;
    private String residentId;
    private String vehiclePlate;
    private String spaceId;
    private Date startDate;
    private Date endDate;
    private boolean isActive;

  
    public RentalLibrary() {
    }

    
    public RentalLibrary(String rentalId, String residentId, String vehiclePlate, String spaceId, Date startDate, Date endDate, boolean isActive) {
        this.rentalId = rentalId;
        this.residentId = residentId;
        this.vehiclePlate = vehiclePlate;
        this.spaceId = spaceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    
    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    
    public boolean validateRental() {
        
        Date today = new Date();
        return isActive && today.after(startDate) && today.before(endDate);
    }

    public void activateRental() {
        this.isActive = true;
    }

    public void deactivateRental() {
        this.isActive = false;
    }

    public String getRentalInfo() {
        return "Rental ID: " + rentalId +
               "\nResident ID: " + residentId +
               "\nVehicle Plate: " + vehiclePlate +
               "\nSpace ID: " + spaceId +
               "\nStart Date: " + startDate +
               "\nEnd Date: " + endDate +
               "\nActive: " + (isActive ? "Yes" : "No");
    }
}
