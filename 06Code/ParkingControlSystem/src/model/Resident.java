package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class Resident {

    private int id;
    private String name;
    private String apartmentNumber;
    private String vehiclePlate;
    private boolean hasActivateRental;

    public Resident(int id, String name, String apartmentNumber, String vehiclePlate, boolean hasActivateRental) {
        this.id = id;
        this.name = name;
        this.apartmentNumber = apartmentNumber;
        this.vehiclePlate = vehiclePlate;
        this.hasActivateRental = hasActivateRental;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public boolean isHasActivateRental() {
        return hasActivateRental;
    }

    public void setHasActivateRental(boolean hasActivateRental) {
        this.hasActivateRental = hasActivateRental;
    }
}
