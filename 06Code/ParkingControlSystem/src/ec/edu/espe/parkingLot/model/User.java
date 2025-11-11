package ec.edu.espe.parkingLot.model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

public class User {

    protected String userID; 

    public User(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}