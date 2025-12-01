
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
public class ParkingControlSystemTest {
    
    public ParkingControlSystemTest() {
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
     * Test of startSystem method, of class ParkingControlSystem.
     */
    @Test
    public void testStartSystem() {
        System.out.println("startSystem");
        ParkingControlSystem instance = null;
        boolean expResult = false;
        boolean result = instance.startSystem();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopSystem method, of class ParkingControlSystem.
     */
    @Test
    public void testStopSystem() {
        System.out.println("stopSystem");
        ParkingControlSystem instance = null;
        instance.stopSystem();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerEntry method, of class ParkingControlSystem.
     */
    @Test
    public void testRegisterEntry() {
        System.out.println("registerEntry");
        String plate = "";
        ParkingControlSystem instance = null;
        boolean expResult = false;
        boolean result = instance.registerEntry(plate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerExit method, of class ParkingControlSystem.
     */
    @Test
    public void testRegisterExit() {
        System.out.println("registerExit");
        String plate = "";
        ParkingControlSystem instance = null;
        boolean expResult = false;
        boolean result = instance.registerExit(plate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkAvailability method, of class ParkingControlSystem.
     */
    @Test
    public void testCheckAvailability() {
        System.out.println("checkAvailability");
        ParkingControlSystem instance = null;
        int expResult = 0;
        int result = instance.checkAvailability();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateReport method, of class ParkingControlSystem.
     */
    @Test
    public void testGenerateReport() {
        System.out.println("generateReport");
        ParkingControlSystem instance = null;
        String expResult = "";
        String result = instance.generateReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSystemId method, of class ParkingControlSystem.
     */
    @Test
    public void testGetSystemId() {
        System.out.println("getSystemId");
        ParkingControlSystem instance = null;
        String expResult = "";
        String result = instance.getSystemId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isActive method, of class ParkingControlSystem.
     */
    @Test
    public void testIsActive() {
        System.out.println("isActive");
        ParkingControlSystem instance = null;
        boolean expResult = false;
        boolean result = instance.isActive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalVehicles method, of class ParkingControlSystem.
     */
    @Test
    public void testGetTotalVehicles() {
        System.out.println("getTotalVehicles");
        ParkingControlSystem instance = null;
        int expResult = 0;
        int result = instance.getTotalVehicles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParkingLot method, of class ParkingControlSystem.
     */
    @Test
    public void testGetParkingLot() {
        System.out.println("getParkingLot");
        ParkingControlSystem instance = null;
        ParkingLot expResult = null;
        ParkingLot result = instance.getParkingLot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkSystemStatus method, of class ParkingControlSystem.
     */
    @Test
    public void testCheckSystemStatus() {
        System.out.println("checkSystemStatus");
        ParkingControlSystem instance = null;
        boolean expResult = false;
        boolean result = instance.checkSystemStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDetailedReport method, of class ParkingControlSystem.
     */
    @Test
    public void testGetDetailedReport() {
        System.out.println("getDetailedReport");
        ParkingControlSystem instance = null;
        String expResult = "";
        String result = instance.getDetailedReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
