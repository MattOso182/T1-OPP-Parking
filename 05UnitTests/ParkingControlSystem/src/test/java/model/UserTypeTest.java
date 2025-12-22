
package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class UserTypeTest {
    
    public UserTypeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of values method, of class UserType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        UserType[] expResult = null;
        UserType[] result = UserType.values();
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of valueOf method, of class UserType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        UserType expResult = null;
        UserType result = UserType.valueOf(name);
        assertEquals(expResult, result);
       
    }
    
}
