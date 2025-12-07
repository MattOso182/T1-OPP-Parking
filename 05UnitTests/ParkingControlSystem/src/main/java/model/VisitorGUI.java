package ec.edu.espe.parkinglot.model;

/**
 *
 * @author Emily Calle, @ESPE
 */
public class Visitor {
    private int visitorId;
    private String firstName;
    private String lastName;
    private String plate;
    private String vehicleType;
    private int age;
    private String hasPass;
    private int cI; //cedula

    public Visitor(int visitorId, String firstName, String lastName, String plate, String vehicleType, int age, String hasPass, int cI) {
        this.visitorId = visitorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.plate = plate;
        this.vehicleType = vehicleType;
        this.age = age;
        this.hasPass = hasPass;
        this.cI = cI;
    }
    
    
    /**
     * @return the visitorId
     */
    public int getVisitorId() {
        return visitorId;
    }

    /**
     * @param visitorId the visitorId to set
     */
    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * @return the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the hasPass
     */
    public String getHasPass() {
        return hasPass;
    }

    /**
     * @param hasPass the hasPass to set
     */
    public void setHasPass(String hasPass) {
        this.hasPass = hasPass;
    }

    /**
     * @return the cI
     */
    public int getcI() {
        return cI;
    }

    /**
     * @param cI the cI to set
     */
    public void setcI(int cI) {
        this.cI = cI;
    }
    
    
    
}