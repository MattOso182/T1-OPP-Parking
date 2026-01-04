package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class User {
    private String username; 
    private String userType; 
    private String fullName;

    public User(String username, String userType, String fullName) {
        this.username = username;
        this.userType = userType;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public String getFullName() {
        return fullName;
    }
    
    public String getResidentID() {
        return userType.equals("Residente") ? username : null;
    }
}