package model;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */
public class SecurityGuardTest {
    
    public SecurityGuardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of verifyAuthorization method, of class SecurityGuard.
     */
    @Test
    public void testVerifyAuthorization() {
        System.out.println("verifyAuthorization");
        String vehiclePlateOrID = "ABC-123";
        SecurityGuard instance = null;
        boolean expResult = false;
        boolean result = instance.verifyAuthorization(vehiclePlateOrID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerEntry method, of class SecurityGuard.
     */
    @Test
    public void testRegisterEntry() {
        System.out.println("registerEntry");
        String vehiclePlate = "";
        Date time = null;
        SecurityGuard instance = null;
        instance.registerEntry(vehiclePlate, time);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerExit method, of class SecurityGuard.
     */
    @Test
    public void testRegisterExit() {
        System.out.println("registerExit");
        String vehiclePlate = "";
        Date time = null;
        SecurityGuard instance = null;
        instance.registerExit(vehiclePlate, time);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGuardID method, of class SecurityGuard.
     */
    @Test
    public void testGetGuardID() {
        System.out.println("getGuardID");
        SecurityGuard instance = null;
        String expResult = "";
        String result = instance.getGuardID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGuardID method, of class SecurityGuard.
     */
    @Test
    public void testSetGuardID() {
        System.out.println("setGuardID");
        String guardID = "";
        SecurityGuard instance = null;
        instance.setGuardID(guardID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class SecurityGuard.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        SecurityGuard instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SecurityGuard.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        SecurityGuard instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getShift method, of class SecurityGuard.
     */
    @Test
    public void testGetShift() {
        System.out.println("getShift");
        SecurityGuard instance = null;
        String expResult = "";
        String result = instance.getShift();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setShift method, of class SecurityGuard.
     */
    @Test
    public void testSetShift() {
        System.out.println("setShift");
        String shift = "";
        SecurityGuard instance = null;
        instance.setShift(shift);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPhoneNumber method, of class SecurityGuard.
     */
    @Test
    public void testGetPhoneNumber() {
        System.out.println("getPhoneNumber");
        SecurityGuard instance = null;
        String expResult = "";
        String result = instance.getPhoneNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPhoneNumber method, of class SecurityGuard.
     */
    @Test
    public void testSetPhoneNumber() {
        System.out.println("setPhoneNumber");
        String phoneNumber = "";
        SecurityGuard instance = null;
        instance.setPhoneNumber(phoneNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOnDuty method, of class SecurityGuard.
     */
    @Test
    public void testIsOnDuty() {
        System.out.println("isOnDuty");
        SecurityGuard instance = null;
        boolean expResult = false;
        boolean result = instance.isOnDuty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOnDuty method, of class SecurityGuard.
     */
    @Test
    public void testSetOnDuty() {
        System.out.println("setOnDuty");
        boolean isOnDuty = false;
        SecurityGuard instance = null;
        instance.setOnDuty(isOnDuty);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getControlSystem method, of class SecurityGuard.
     */
    @Test
    public void testGetControlSystem() {
        System.out.println("getControlSystem");
        SecurityGuard instance = null;
        ParkingControlSystem expResult = null;
        ParkingControlSystem result = instance.getControlSystem();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntryExitSystem method, of class SecurityGuard.
     */
    @Test
    public void testGetEntryExitSystem() {
        System.out.println("getEntryExitSystem");
        SecurityGuard instance = null;
        EntryExitRecord expResult = null;
        EntryExitRecord result = instance.getEntryExitSystem();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResidentManager method, of class SecurityGuard.
     */
    @Test
    public void testGetResidentManager() {
        System.out.println("getResidentManager");
        SecurityGuard instance = null;
        ResidentManager expResult = null;
        ResidentManager result = instance.getResidentManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVisitorManager method, of class SecurityGuard.
     */
    @Test
    public void testGetVisitorManager() {
        System.out.println("getVisitorManager");
        SecurityGuard instance = null;
        VisitorManager expResult = null;
        VisitorManager result = instance.getVisitorManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
