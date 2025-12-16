package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Rental {

    private String rentalId;
    private String residentId;
    private String spaceId;
    private Date startDate;
    private Date endDate;
    private double monthlyPrice;
    private boolean isActive;
    private String paymentStatus;

    public Rental() {
        this.isActive = true;
        this.paymentStatus = "PENDING";
        this.startDate = new Date();
    }

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

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRentalId() {
        return rentalId;
    }

    public String getResidentId() {
        return residentId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public boolean isExpired() {
        return new Date().after(endDate);
    }

    public int getMonthsUsed() {
        if (startDate == null) {
            return 0;
        }

        Date referenceDate;
        Date currentDate = new Date();

        if (endDate == null || endDate.after(currentDate)) {
            referenceDate = currentDate;
        } else {
            referenceDate = endDate;
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(referenceDate);

        int yearsDiff = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
        int monthsDiff = endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
        int totalMonths = yearsDiff * 12 + monthsDiff;

        if (endCal.get(Calendar.DAY_OF_MONTH) < startCal.get(Calendar.DAY_OF_MONTH)) {
            totalMonths--;
        }

        return Math.max(1, totalMonths);
    }

    public long getDaysUsed() {
        if (startDate == null) {
            return 0;
        }

        Date referenceDate;
        Date currentDate = new Date();

        if (endDate == null || endDate.after(currentDate)) {
            referenceDate = currentDate;
        } else {
            referenceDate = endDate;
        }

        long diff = referenceDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public boolean renewRental(int additionalMonths) {
        if (!isActive) {
            System.out.println("Cannot renew an inactive rental");
            return false;
        }

        if (endDate == null) {
            endDate = new Date();
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
            System.out.println("Cannot process payment for an inactive rental");
            return false;
        }

        this.paymentStatus = "PAID";
        System.out.println("Payment processed for rental: " + rentalId);
        return true;
    }

    @Override
    public String toString() {
        return spaceId + " - $" + monthlyPrice + "/month - "
                + (isActive ? "Active" : "Inactive");
    }
}
