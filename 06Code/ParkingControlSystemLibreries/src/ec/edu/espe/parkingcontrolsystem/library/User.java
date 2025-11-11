
package ec.edu.espe.parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */
public class User {
    private String userID;
    private String name;
    private String phoneNumber;
    private String block;
    private String type; 

    
    public User() {
    }

    
    public User(String userID, String name, String phoneNumber, String block, String type) {
        this.userID = userID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.block = block;
        this.type = type;
    }

    
    public User(String userID) {
        this.userID = userID;
    }

    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    
    public void showUserInfo() {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Block: " + block);
        System.out.println("Type: " + type);
    }

    public boolean validateContactInfo() {
        return phoneNumber != null && !phoneNumber.isEmpty();
    }

    public String getBasicInfo() {
        return "User ID: " + userID +
               "\nName: " + name +
               "\nPhone: " + phoneNumber +
               "\nBlock: " + block +
               "\nType: " + type;
    }
}
