
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
public class VehicleTest {
    
    public VehicleTest() {
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
     * Test of registerVehicle method, of class Vehicle.
     */
    @Test
    public void testRegisterVehicle() {
        System.out.println("registerVehicle");
        Vehicle instance = null;
        boolean expResult = false;
        boolean result = instance.registerVehicle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateOwner method, of class Vehicle.
     */
    @Test
    public void testUpdateOwner() {
        System.out.println("updateOwner");
        String newOwnerId = "";
        Vehicle instance = null;
        instance.updateOwner(newOwnerId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of assignSpot method, of class Vehicle.
     */
    @Test
    public void testAssignSpot() {
        System.out.println("assignSpot");
        String spotId = "";
        Vehicle instance = null;
        instance.assignSpot(spotId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of releaseSpot method, of class Vehicle.
     */
    @Test
    public void testReleaseSpot() {
        System.out.println("releaseSpot");
        Vehicle instance = null;
        instance.releaseSpot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validatePlate method, of class Vehicle.
     */
    @Test
    public void testValidatePlate() {
        System.out.println("validatePlate");
        Vehicle instance = null;
        boolean expResult = false;
        boolean result = instance.validatePlate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlate method, of class Vehicle.
     */
    @Test
    public void testGetPlate() {
        System.out.println("getPlate");
        Vehicle instance = null;
        String expResult = "";
        String result = instance.getPlate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColor method, of class Vehicle.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Vehicle instance = null;
        String expResult = "";
        String result = instance.getColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModel method, of class Vehicle.
     */
    @Test
    public void testGetModel() {
        System.out.println("getModel");
        Vehicle instance = null;
        String expResult = "";
        String result = instance.getModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isParked method, of class Vehicle.
     */
    @Test
    public void testIsParked() {
        System.out.println("isParked");
        Vehicle instance = null;
        boolean expResult = false;
        boolean result = instance.isParked();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOwnerId method, of class Vehicle.
     */
    @Test
    public void testGetOwnerId() {
        System.out.println("getOwnerId");
        Vehicle instance = null;
        String expResult = "";
        String result = instance.getOwnerId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleInfo method, of class Vehicle.
     */
    @Test
    public void testGetVehicleInfo() {
        System.out.println("getVehicleInfo");
        Vehicle instance = null;
        String expResult = "";
        String result = instance.getVehicleInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
