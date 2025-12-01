
package model;

import java.util.Date;
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
public class VisitorTest {
    
    public VisitorTest() {
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
     * Test of verifyIdentity method, of class Visitor.
     */
    @Test
    public void testVerifyIdentity() {
        System.out.println("verifyIdentity");
        Visitor instance = null;
        boolean expResult = false;
        boolean result = instance.verifyIdentity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of assignTemporaryPass method, of class Visitor.
     */
    @Test
    public void testAssignTemporaryPass() {
        System.out.println("assignTemporaryPass");
        Visitor instance = null;
        boolean expResult = false;
        boolean result = instance.assignTemporaryPass();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recordExit method, of class Visitor.
     */
    @Test
    public void testRecordExit() {
        System.out.println("recordExit");
        Visitor instance = null;
        boolean expResult = false;
        boolean result = instance.recordExit();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasTemporaryPass method, of class Visitor.
     */
    @Test
    public void testHasTemporaryPass() {
        System.out.println("hasTemporaryPass");
        Visitor instance = null;
        boolean expResult = false;
        boolean result = instance.hasTemporaryPass();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVisitorID method, of class Visitor.
     */
    @Test
    public void testGetVisitorID() {
        System.out.println("getVisitorID");
        Visitor instance = null;
        String expResult = "";
        String result = instance.getVisitorID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVisitorID method, of class Visitor.
     */
    @Test
    public void testSetVisitorID() {
        System.out.println("setVisitorID");
        String visitorID = "";
        Visitor instance = null;
        instance.setVisitorID(visitorID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Visitor.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Visitor instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Visitor.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Visitor instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehiclePlate method, of class Visitor.
     */
    @Test
    public void testGetVehiclePlate() {
        System.out.println("getVehiclePlate");
        Visitor instance = null;
        String expResult = "";
        String result = instance.getVehiclePlate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVehiclePlate method, of class Visitor.
     */
    @Test
    public void testSetVehiclePlate() {
        System.out.println("setVehiclePlate");
        String vehiclePlate = "";
        Visitor instance = null;
        instance.setVehiclePlate(vehiclePlate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEntryTime method, of class Visitor.
     */
    @Test
    public void testSetEntryTime() {
        System.out.println("setEntryTime");
        Date entryTime = null;
        Visitor instance = null;
        instance.setEntryTime(entryTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntryTime method, of class Visitor.
     */
    @Test
    public void testGetEntryTime() {
        System.out.println("getEntryTime");
        Visitor instance = null;
        Date expResult = null;
        Date result = instance.getEntryTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExitTime method, of class Visitor.
     */
    @Test
    public void testGetExitTime() {
        System.out.println("getExitTime");
        Visitor instance = null;
        Date expResult = null;
        Date result = instance.getExitTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVisitorInfo method, of class Visitor.
     */
    @Test
    public void testGetVisitorInfo() {
        System.out.println("getVisitorInfo");
        Visitor instance = null;
        String expResult = "";
        String result = instance.getVisitorInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
