
package model;

import java.util.List;
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
public class ResidentDataTest {
    
    public ResidentDataTest() {
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
     * Test of getResidents method, of class ResidentData.
     */
    @Test
    public void testGetResidents() {
        System.out.println("getResidents");
        ResidentData instance = new ResidentData();
        List<Resident> expResult = null;
        List<Resident> result = instance.getResidents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setResidents method, of class ResidentData.
     */
    @Test
    public void testSetResidents() {
        System.out.println("setResidents");
        List<Resident> residents = null;
        ResidentData instance = new ResidentData();
        instance.setResidents(residents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
