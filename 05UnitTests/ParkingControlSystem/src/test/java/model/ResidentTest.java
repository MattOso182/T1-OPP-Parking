
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
public class ResidentTest {
    
    public ResidentTest() {
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
     * Test of hasActiveRental method, of class Resident.
     */
    @Test
    public void testHasActiveRental() {
        System.out.println("hasActiveRental");
        Resident instance = new Resident();
        boolean expResult = false;
        boolean result = instance.hasActiveRental();
        assertEquals(expResult, result);
    
    }

    /**
     * Test of getRentedSpace method, of class Resident.
     */
    @Test
    public void testGetRentedSpace() {
        System.out.println("getRentedSpace");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getRentedSpace();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setCurrentRental method, of class Resident.
     */
    @Test
    public void testSetCurrentRental() {
        System.out.println("setCurrentRental");
        Rental rental = null;
        Resident instance = new Resident();
        instance.setCurrentRental(rental);
      
    }

    /**
     * Test of getCurrentRental method, of class Resident.
     */
    @Test
    public void testGetCurrentRental() {
        System.out.println("getCurrentRental");
        Resident instance = new Resident();
        Rental expResult = null;
        Rental result = instance.getCurrentRental();
        assertEquals(expResult, result);       
    }

    /**
     * Test of addVehicle method, of class Resident.
     */
    @Test
    public void testAddVehicle() {
        System.out.println("addVehicle");
        Vehicles vehicle = null;
        Resident instance = new Resident();
        boolean expResult = false;
        boolean result = instance.addVehicle(vehicle);
        assertEquals(expResult, result);
    
    }

    /**
     * Test of removeVehicle method, of class Resident.
     */
    @Test
    public void testRemoveVehicle() {
        System.out.println("removeVehicle");
        String plate = "";
        Resident instance = new Resident();
        boolean expResult = false;
        boolean result = instance.removeVehicle(plate);
        assertEquals(expResult, result);
    }

    /**
     * Test of findVehicleByPlate method, of class Resident.
     */
    @Test
    public void testFindVehicleByPlate() {
        System.out.println("findVehicleByPlate");
        String plate = "";
        Resident instance = new Resident();
        Vehicles expResult = null;
        Vehicles result = instance.findVehicleByPlate(plate);
        assertEquals(expResult, result);
    }

    /**
     * Test of authorizeVisitor method, of class Resident.
     */
    @Test
    public void testAuthorizeVisitor() {
        System.out.println("authorizeVisitor");
        String visitorID = "";
        Resident instance = new Resident();
        boolean expResult = false;
        boolean result = instance.authorizeVisitor(visitorID);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of removeAuthorizedVisitor method, of class Resident.
     */
    @Test
    public void testRemoveAuthorizedVisitor() {
        System.out.println("removeAuthorizedVisitor");
        String visitorID = "";
        Resident instance = new Resident();
        boolean expResult = false;
        boolean result = instance.removeAuthorizedVisitor(visitorID);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getResidentID method, of class Resident.
     */
    @Test
    public void testGetResidentID() {
        System.out.println("getResidentID");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getResidentID();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getName method, of class Resident.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getApartmentNumber method, of class Resident.
     */
    @Test
    public void testGetApartmentNumber() {
        System.out.println("getApartmentNumber");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getApartmentNumber();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getEmail method, of class Resident.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPhone method, of class Resident.
     */
    @Test
    public void testGetPhone() {
        System.out.println("getPhone");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getPhone();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getUserType method, of class Resident.
     */
    @Test
    public void testGetUserType() {
        System.out.println("getUserType");
        Resident instance = new Resident();
        UserType expResult = null;
        UserType result = instance.getUserType();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getAssignedParkingSpace method, of class Resident.
     */
    @Test
    public void testGetAssignedParkingSpace() {
        System.out.println("getAssignedParkingSpace");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getAssignedParkingSpace();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getVehicles method, of class Resident.
     */
    @Test
    public void testGetVehicles() {
        System.out.println("getVehicles");
        Resident instance = new Resident();
        List<Vehicles> expResult = null;
        List<Vehicles> result = instance.getVehicles();
        assertEquals(expResult, result);
      
    }

    /**
     * Test of getAuthorizedVisitors method, of class Resident.
     */
    @Test
    public void testGetAuthorizedVisitors() {
        System.out.println("getAuthorizedVisitors");
        Resident instance = new Resident();
        List<String> expResult = null;
        List<String> result = instance.getAuthorizedVisitors();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getCurrentParkingSpace method, of class Resident.
     */
    @Test
    public void testGetCurrentParkingSpace() {
        System.out.println("getCurrentParkingSpace");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getCurrentParkingSpace();
        assertEquals(expResult, result);
      
    }

    /**
     * Test of getResidentInfo method, of class Resident.
     */
    @Test
    public void testGetResidentInfo() {
        System.out.println("getResidentInfo");
        Resident instance = new Resident();
        String expResult = "";
        String result = instance.getResidentInfo();
        assertEquals(expResult, result);
    }
    

    /**
     * Test of setEmail method, of class Resident.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        Resident instance = new Resident();
        instance.setEmail(email);
    }

    /**
     * Test of setPhone method, of class Resident.
     */
    @Test
    public void testSetPhone() {
        System.out.println("setPhone");
        String phone = "";
        Resident instance = new Resident();
        instance.setPhone(phone);
       
    }
    
}
