package ec.edu.espe.parkingLot.model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

import java.util.Date;

public class Rental {
 private String rentalId;
    private String residentId;
    private String spaceId;
    private Date startDate;
    private Date endDate;
    private double monthlyPrice;
    private boolean isActive;
    private String paymentStatus; // PENDING, PAID, OVERDUE
    
    public Rental() {}
    
    public Rental(String rentalId, String residentId, String spaceId, 
                  Date startDate, Date endDate, double monthlyPrice) {
        this.rentalId = rentalId;
        this.residentId = residentId;
        this.spaceId = spaceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyPrice = monthlyPrice;
        this.isActive = true;
        this.paymentStatus = "PENDING";
    }
    
    public boolean isExpired() {
        return new Date().after(endDate);
    }
    
    public long getDaysRemaining() {
        long diff = endDate.getTime() - new Date().getTime();
        return diff / (1000 * 60 * 60 * 24); 
    }
    
    public boolean renewRental(int additionalMonths) {
        if (!isActive) {
            System.out.println("Cannot renew inactive rental");
            return false;
        }
        
        long newEndDate = endDate.getTime() + (additionalMonths * 30L * 24 * 60 * 60 * 1000);
        this.endDate = new Date(newEndDate);
        this.paymentStatus = "PENDING";
        
        System.out.println("Rental renewed for " + additionalMonths + " months. New end date: " + endDate);
        return true;
    }
    
    public boolean cancelRental() {
        if (!isActive) {
            System.out.println("Rental is already inactive");
            return false;
        }
        
        this.isActive = false;
        this.paymentStatus = "CANCELLED";
        System.out.println("Rental cancelled successfully");
        return true;
    }
    
    public boolean processPayment() {
        if (!isActive) {
            System.out.println("Cannot process payment for inactive rental");
            return false;
        }
        
        this.paymentStatus = "PAID";
        System.out.println("Payment processed for rental: " + rentalId);
        return true;
    }
    
    // Getters and Setters
    public String getRentalId() { return rentalId; }
    public String getResidentId() { return residentId; }
    public String getSpaceId() { return spaceId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public double getMonthlyPrice() { return monthlyPrice; }
    public boolean isActive() { return isActive; }
    public String getPaymentStatus() { return paymentStatus; }
    
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setMonthlyPrice(double monthlyPrice) { this.monthlyPrice = monthlyPrice; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public String getRentalInfo() {
        return "Rental ID: " + rentalId +
               "\nResident ID: " + residentId +
               "\nSpace: " + spaceId +
               "\nPeriod: " + startDate + " to " + endDate +
               "\nMonthly Price: $" + monthlyPrice +
               "\nStatus: " + (isActive ? "ACTIVE" : "INACTIVE") +
               "\nPayment: " + paymentStatus +
               "\nDays Remaining: " + (isActive ? getDaysRemaining() : 0);
    }
}