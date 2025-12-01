
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
public class EntryExitRecordTest {
    
    public EntryExitRecordTest() {
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
     * Test of registerEntry method, of class EntryExitRecord.
     */
    @Test
    public void testRegisterEntry() {
        System.out.println("registerEntry");
        String vehiclePlate = "";
        Date entryTime = null;
        EntryExitRecord instance = new EntryExitRecord();
        instance.registerEntry(vehiclePlate, entryTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerExit method, of class EntryExitRecord.
     */
    @Test
    public void testRegisterExit() {
        System.out.println("registerExit");
        String vehiclePlate = "";
        Date exitTime = null;
        EntryExitRecord instance = new EntryExitRecord();
        instance.registerExit(vehiclePlate, exitTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateDuration method, of class EntryExitRecord.
     */
    @Test
    public void testCalculateDuration() {
        System.out.println("calculateDuration");
        EntryExitRecord instance = new EntryExitRecord();
        double expResult = 0.0;
        double result = instance.calculateDuration();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verifyRentalStatus method, of class EntryExitRecord.
     */
    @Test
    public void testVerifyRentalStatus() {
        System.out.println("verifyRentalStatus");
        EntryExitRecord instance = new EntryExitRecord();
        boolean expResult = false;
        boolean result = instance.verifyRentalStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecordInfo method, of class EntryExitRecord.
     */
    @Test
    public void testGetRecordInfo() {
        System.out.println("getRecordInfo");
        EntryExitRecord instance = new EntryExitRecord();
        String expResult = "";
        String result = instance.getRecordInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecordID method, of class EntryExitRecord.
     */
    @Test
    public void testGetRecordID() {
        System.out.println("getRecordID");
        EntryExitRecord instance = new EntryExitRecord();
        String expResult = "";
        String result = instance.getRecordID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehiclePlate method, of class EntryExitRecord.
     */
    @Test
    public void testGetVehiclePlate() {
        System.out.println("getVehiclePlate");
        EntryExitRecord instance = new EntryExitRecord();
        String expResult = "";
        String result = instance.getVehiclePlate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVehiclePlate method, of class EntryExitRecord.
     */
    @Test
    public void testSetVehiclePlate() {
        System.out.println("setVehiclePlate");
        String vehiclePlate = "";
        EntryExitRecord instance = new EntryExitRecord();
        instance.setVehiclePlate(vehiclePlate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntryTime method, of class EntryExitRecord.
     */
    @Test
    public void testGetEntryTime() {
        System.out.println("getEntryTime");
        EntryExitRecord instance = new EntryExitRecord();
        Date expResult = null;
        Date result = instance.getEntryTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEntryTime method, of class EntryExitRecord.
     */
    @Test
    public void testSetEntryTime() {
        System.out.println("setEntryTime");
        Date entryTime = null;
        EntryExitRecord instance = new EntryExitRecord();
        instance.setEntryTime(entryTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExitTime method, of class EntryExitRecord.
     */
    @Test
    public void testGetExitTime() {
        System.out.println("getExitTime");
        EntryExitRecord instance = new EntryExitRecord();
        Date expResult = null;
        Date result = instance.getExitTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExitTime method, of class EntryExitRecord.
     */
    @Test
    public void testSetExitTime() {
        System.out.println("setExitTime");
        Date exitTime = null;
        EntryExitRecord instance = new EntryExitRecord();
        instance.setExitTime(exitTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParkingSpaceID method, of class EntryExitRecord.
     */
    @Test
    public void testGetParkingSpaceID() {
        System.out.println("getParkingSpaceID");
        EntryExitRecord instance = new EntryExitRecord();
        String expResult = "";
        String result = instance.getParkingSpaceID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setParkingSpaceID method, of class EntryExitRecord.
     */
    @Test
    public void testSetParkingSpaceID() {
        System.out.println("setParkingSpaceID");
        String parkingSpaceID = "";
        EntryExitRecord instance = new EntryExitRecord();
        instance.setParkingSpaceID(parkingSpaceID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOperatorID method, of class EntryExitRecord.
     */
    @Test
    public void testGetOperatorID() {
        System.out.println("getOperatorID");
        EntryExitRecord instance = new EntryExitRecord();
        String expResult = "";
        String result = instance.getOperatorID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOperatorID method, of class EntryExitRecord.
     */
    @Test
    public void testSetOperatorID() {
        System.out.println("setOperatorID");
        String operatorID = "";
        EntryExitRecord instance = new EntryExitRecord();
        instance.setOperatorID(operatorID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
