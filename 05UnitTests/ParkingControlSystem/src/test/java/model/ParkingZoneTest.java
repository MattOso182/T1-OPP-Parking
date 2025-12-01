
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
public class ParkingZoneTest {
    
    public ParkingZoneTest() {
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
     * Test of getSection method, of class ParkingZone.
     */
    @Test
    public void testGetSection() {
        System.out.println("getSection");
        ParkingZone instance = new ParkingZone();
        String expResult = "";
        String result = instance.getSection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSection method, of class ParkingZone.
     */
    @Test
    public void testSetSection() {
        System.out.println("setSection");
        String section = "";
        ParkingZone instance = new ParkingZone();
        instance.setSection(section);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpaces method, of class ParkingZone.
     */
    @Test
    public void testGetSpaces() {
        System.out.println("getSpaces");
        ParkingZone instance = new ParkingZone();
        List<SpaceDefinition> expResult = null;
        List<SpaceDefinition> result = instance.getSpaces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSpaces method, of class ParkingZone.
     */
    @Test
    public void testSetSpaces() {
        System.out.println("setSpaces");
        List<SpaceDefinition> spaces = null;
        ParkingZone instance = new ParkingZone();
        instance.setSpaces(spaces);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
