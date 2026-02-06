package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
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
    private double totalPrice;
    private int months;

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
            return false;
        }

        if (endDate == null) {
            endDate = new Date();
        }

        long newEndDate = endDate.getTime() + (additionalMonths * 30L * 24 * 60 * 60 * 1000);
        this.endDate = new Date(newEndDate);
        this.paymentStatus = "PENDING";

        return true;
    }

    public boolean cancelRental() {
        if (!isActive) {
            return false;
        }

        this.isActive = false;
        this.paymentStatus = "CANCELLED";
        return true;
    }

    public boolean processPayment() {
        if (!isActive) {
            return false;
        }

        this.paymentStatus = "PAID";
        System.out.println("Pago procesado por alquiler: " + rentalId);
        return true;
    }

    public double calculateTotal() {
        if (totalPrice > 0) {
            return totalPrice;
        } else if (months > 0) {
            return monthlyPrice * months;
        } else {
            return monthlyPrice;
        }
    }

    @Override
    public String toString() {
        return spaceId + " - $" + monthlyPrice + "/month - "
                + (isActive ? "Active" : "Inactive");
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

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMonths(int months) {
        this.months = months;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getMonths() {
        return months;
    }

    public int getMonthsUsed() {
        if (startDate == null || endDate == null) {
            return 1;
        }

        if (startDate.after(endDate)) {
            return 1;
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int startYear = startCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH);
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);

        int endYear = endCal.get(Calendar.YEAR);
        int endMonth = endCal.get(Calendar.MONTH);
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);

        int yearDiff = endYear - startYear;
        int monthDiff = endMonth - startMonth;
        int totalMonths = yearDiff * 12 + monthDiff;

        if (endDay < startDay) {
            totalMonths--;
        }

        if (totalMonths <= 0) {
            long diffInDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
            if (diffInDays > 0) {
                return 1;
            }
            return 0;
        }

        return totalMonths;
    }
}