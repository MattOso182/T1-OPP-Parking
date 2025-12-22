package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class UserTest {
    
    private User user;
    private final String INITIAL_ID = "USR-100";

    @BeforeEach
    public void setUp() {
        user = new UserImpl(INITIAL_ID);
    }

    @Test
    public void shouldGetUserID() {
        assertEquals(INITIAL_ID, user.getUserID(), "El ID inicial debe coincidir");
    }

    @Test
    public void shouldUpdateUserID() {
        String newID = "USR-200";
        user.setUserID(newID);
        assertEquals(newID, user.getUserID(), "El ID debe actualizarse correctamente");
    }

   
    public class UserImpl extends User {
        public UserImpl(String userID) {
            super(userID);
        }
    }
}