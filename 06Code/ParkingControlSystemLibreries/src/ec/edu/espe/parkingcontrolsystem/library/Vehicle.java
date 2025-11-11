
package ec.edu.espe.parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import java.util.Date;
import java.util.Objects;

public class Vehicle {

    // Atributos principales
    private String licensePlate;     
    private String ownerName;        
    private String ownerId;          
    private String phoneNumber;      
    private String block;            
    private String parkingSpaceId;   
    private String residentType;     
    private double monthlyFee;       
    private boolean paymentStatus;   

   
    public Vehicle() {
    }

    public Vehicle(String licensePlate, String ownerName, String ownerId, String phoneNumber, 
        String block, String parkingSpaceId, String residentType, 
        double monthlyFee, boolean paymentStatus) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;
        this.block = block;
        this.parkingSpaceId = parkingSpaceId;
        this.residentType = residentType;
        this.monthlyFee = monthlyFee;
        this.paymentStatus = paymentStatus;
    }

   
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(String parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getResidentType() {
        return residentType;
    }

    public void setResidentType(String residentType) {
        this.residentType = residentType;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public void registerPayment() {
        this.paymentStatus = true;
    }

  
    public void resetPaymentStatus() {
        this.paymentStatus = false;
    }


    public String getVehicleInfo() {
        return "Placa: " + licensePlate + "\n" +
               "Propietario: " + ownerName + "\n" +
               "Cédula: " + ownerId + "\n" +
               "Teléfono: " + phoneNumber + "\n" +
               "Bloque: " + block + "\n" +
               "Parqueadero: " + parkingSpaceId + "\n" +
               "Tipo de residente: " + residentType + "\n" +
               "Valor mensual: $" + monthlyFee + "\n" +
               "Pago al día: " + (paymentStatus ? "Sí" : "No");
    }

    @Override
    public String toString() {
        return licensePlate + " - " + ownerName + " (" + residentType + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(licensePlate, vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }
}