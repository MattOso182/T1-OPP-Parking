package model;

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
    private String paymentStatus; 

    public Rental() {
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

    public boolean isExpired() {
        return new Date().after(endDate);
    }

    public long getDaysRemaining() {
        long diff = endDate.getTime() - new Date().getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    public boolean renewRental(int additionalMonths) {
        if (!isActive) {
            System.out.println("No se puede renovar un alquiler inactivo");
            return false;
        }

        long newEndDate = endDate.getTime() + (additionalMonths * 30L * 24 * 60 * 60 * 1000);
        this.endDate = new Date(newEndDate);
        this.paymentStatus = "PENDING";

        System.out.println("Alquiler renovado por " + additionalMonths + " meses. Nueva fecha de fin: " + endDate);
        return true;
    }

    public boolean cancelRental() {
        if (!isActive) {
            System.out.println("El alquiler ya esta inactivo");
            return false;
        }

        this.isActive = false;
        this.paymentStatus = "CANCELLED";
        System.out.println("Alquiler cancelado exitosamente");
        return true;
    }

    public boolean processPayment() {
        if (!isActive) {
            System.out.println("No se puede procesar el pago para un alquiler inactivo");
            return false;
        }

        this.paymentStatus = "PAID";
        System.out.println("Pago procesado para el alquiler: " + rentalId);
        return true;
    }

    // Getters and Setters
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRentalInfo() {
        return "ID de Alquiler: " + rentalId
                + "\nID de Residente: " + residentId
                + "\nEspacio: " + spaceId
                + "\nPeríodo: " + startDate + " a " + endDate
                + "\nPrecio Mensual: $" + monthlyPrice
                + "\nEstado: " + (isActive ? "ACTIVO" : "INACTIVO")
                + "\nPago: " + paymentStatus
                + "\nDías Restantes: " + (isActive ? getDaysRemaining() : 0);
    }
}