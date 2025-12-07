
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
     * Test of registerVehicle method, of class Vehicles.
     */
    @Test
    public void testRegisterVehicle() {
        System.out.println("registerVehicle");
        Vehicles instance = null;
        boolean expResult = false;
        boolean result = instance.registerVehicle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateOwner method, of class Vehicles.
     */
    @Test
    public void testUpdateOwner() {
        System.out.println("updateOwner");
        String newOwnerId = "";
        Vehicles instance = null;
        instance.updateOwner(newOwnerId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of assignSpot method, of class Vehicles.
     */
    @Test
    public void testAssignSpot() {
        System.out.println("assignSpot");
        String spotId = "";
        Vehicles instance = null;
        instance.assignSpot(spotId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of releaseSpot method, of class Vehicles.
     */
    @Test
    public void testReleaseSpot() {
        System.out.println("releaseSpot");
        Vehicles instance = null;
        instance.releaseSpot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validatePlate method, of class Vehicles.
     */
    @Test
    public void testValidatePlate() {
        System.out.println("validatePlate");
        Vehicles instance = null;
        boolean expResult = false;
        boolean result = instance.validatePlate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlate method, of class Vehicles.
     */
    @Test
    public void testGetPlate() {
        System.out.println("getPlate");
        Vehicles instance = null;
        String expResult = "";
        String result = instance.getPlate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColor method, of class Vehicles.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        Vehicles instance = null;
        String expResult = "";
        String result = instance.getColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModel method, of class Vehicles.
     */
    @Test
    public void testGetModel() {
        System.out.println("getModel");
        Vehicles instance = null;
        String expResult = "";
        String result = instance.getModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isParked method, of class Vehicles.
     */
    @Test
    public void testIsParked() {
        System.out.println("isParked");
        Vehicles instance = null;
        boolean expResult = false;
        boolean result = instance.isParked();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOwnerId method, of class Vehicles.
     */
    @Test
    public void testGetOwnerId() {
        System.out.println("getOwnerId");
        Vehicles instance = null;
        String expResult = "";
        String result = instance.getOwnerId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleInfo method, of class Vehicles.
     */
    @Test
    public void testGetVehicleInfo() {
        System.out.println("getVehicleInfo");
        Vehicles instance = null;
        String expResult = "";
        String result = instance.getVehicleInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
