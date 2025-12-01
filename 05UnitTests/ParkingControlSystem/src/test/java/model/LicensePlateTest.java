
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
public class LicensePlateTest {
    
    public LicensePlateTest() {
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
     * Test of validateFormat method, of class LicensePlate.
     */
    @Test
    public void testValidateFormat() {
        System.out.println("validateFormat");
        LicensePlate instance = null;
        boolean expResult = false;
        boolean result = instance.validateFormat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of linkToUser method, of class LicensePlate.
     */
    @Test
    public void testLinkToUser() {
        System.out.println("linkToUser");
        String userID = "";
        LicensePlate instance = null;
        instance.linkToUser(userID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlateInfo method, of class LicensePlate.
     */
    @Test
    public void testGetPlateInfo() {
        System.out.println("getPlateInfo");
        LicensePlate instance = null;
        String expResult = "";
        String result = instance.getPlateInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlateNumber method, of class LicensePlate.
     */
    @Test
    public void testGetPlateNumber() {
        System.out.println("getPlateNumber");
        LicensePlate instance = null;
        String expResult = "";
        String result = instance.getPlateNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlateNumber method, of class LicensePlate.
     */
    @Test
    public void testSetPlateNumber() {
        System.out.println("setPlateNumber");
        String plateNumber = "";
        LicensePlate instance = null;
        instance.setPlateNumber(plateNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProvince method, of class LicensePlate.
     */
    @Test
    public void testGetProvince() {
        System.out.println("getProvince");
        LicensePlate instance = null;
        String expResult = "";
        String result = instance.getProvince();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProvince method, of class LicensePlate.
     */
    @Test
    public void testSetProvince() {
        System.out.println("setProvince");
        String province = "";
        LicensePlate instance = null;
        instance.setProvince(province);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegistrationDate method, of class LicensePlate.
     */
    @Test
    public void testGetRegistrationDate() {
        System.out.println("getRegistrationDate");
        LicensePlate instance = null;
        Date expResult = null;
        Date result = instance.getRegistrationDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRegistrationDate method, of class LicensePlate.
     */
    @Test
    public void testSetRegistrationDate() {
        System.out.println("setRegistrationDate");
        Date registrationDate = null;
        LicensePlate instance = null;
        instance.setRegistrationDate(registrationDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleType method, of class LicensePlate.
     */
    @Test
    public void testGetVehicleType() {
        System.out.println("getVehicleType");
        LicensePlate instance = null;
        String expResult = "";
        String result = instance.getVehicleType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVehicleType method, of class LicensePlate.
     */
    @Test
    public void testSetVehicleType() {
        System.out.println("setVehicleType");
        String vehicleType = "";
        LicensePlate instance = null;
        instance.setVehicleType(vehicleType);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
