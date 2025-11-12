
package parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import java.util.ArrayList;
import java.util.List;


public class ResidentLibrary {
    
    
    private String id;                  
    private String fullName;            
    private String phoneNumber;         
    private String block;               
    private String type;                
    private double monthlyFee;          
    private boolean paymentStatus;      
    private List<VehicleLibrary> vehicles;     

    
    public ResidentLibrary() {
        vehicles = new ArrayList<>();
    }

    public ResidentLibrary(String id, String fullName, String phoneNumber, String block, String type, double monthlyFee) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.block = block;
        this.type = type;
        this.monthlyFee = monthlyFee;
        this.paymentStatus = false;
        this.vehicles = new ArrayList<>();
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<VehicleLibrary> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleLibrary> vehicles) {
        this.vehicles = vehicles;
    }

   

    
    public void addVehicle(VehicleLibrary vehicle) {
        vehicles.add(vehicle);
    }

   
    public void removeVehicle(VehicleLibrary vehicle) {
        vehicles.remove(vehicle);
    }

    
    public void registerPayment() {
        this.paymentStatus = true;
    }

   
    public void resetPaymentStatus() {
        this.paymentStatus = false;
    }

    
    public String getResidentInfo() {
        String info = "Residente: " + fullName + "\n" +
                      "Cédula: " + id + "\n" +
                      "Teléfono: " + phoneNumber + "\n" +
                      "Bloque: " + block + "\n" +
                      "Tipo: " + type + "\n" +
                      "Valor mensual: $" + monthlyFee + "\n" +
                      "Pago al día: " + (paymentStatus ? "Sí" : "No") + "\n" +
                      "Vehículos asociados:\n";
        for (VehicleLibrary v : vehicles) {
            info += "  - " + v.getLicensePlate() + " (" + v.getResidentType() + ")\n";
        }
        return info;
    }

    @Override
    public String toString() {
        return fullName + " (" + type + ")";
    }
}