
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
public class ResidentManagerTest {
    
    public ResidentManagerTest() {
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
     * Test of addResident method, of class ResidentManager.
     */
    @Test
    public void testAddResident() {
        System.out.println("addResident");
        Resident resident = null;
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.addResident(resident);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findResidentById method, of class ResidentManager.
     */
    @Test
    public void testFindResidentById() {
        System.out.println("findResidentById");
        String residentID = "";
        ResidentManager instance = new ResidentManager();
        Resident expResult = null;
        Resident result = instance.findResidentById(residentID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findResidentByVehiclePlate method, of class ResidentManager.
     */
    @Test
    public void testFindResidentByVehiclePlate() {
        System.out.println("findResidentByVehiclePlate");
        String plate = "";
        ResidentManager instance = new ResidentManager();
        Resident expResult = null;
        Resident result = instance.findResidentByVehiclePlate(plate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeResident method, of class ResidentManager.
     */
    @Test
    public void testRemoveResident() {
        System.out.println("removeResident");
        String residentID = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.removeResident(residentID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateResidentInfo method, of class ResidentManager.
     */
    @Test
    public void testUpdateResidentInfo() {
        System.out.println("updateResidentInfo");
        String residentID = "";
        String newEmail = "";
        String newPhone = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.updateResidentInfo(residentID, newEmail, newPhone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addVehicleToResident method, of class ResidentManager.
     */
    @Test
    public void testAddVehicleToResident() {
        System.out.println("addVehicleToResident");
        String residentID = "";
        Vehicle vehicle = null;
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.addVehicleToResident(residentID, vehicle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeVehicleFromResident method, of class ResidentManager.
     */
    @Test
    public void testRemoveVehicleFromResident() {
        System.out.println("removeVehicleFromResident");
        String residentID = "";
        String plate = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.removeVehicleFromResident(residentID, plate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findVehicleByPlate method, of class ResidentManager.
     */
    @Test
    public void testFindVehicleByPlate() {
        System.out.println("findVehicleByPlate");
        String plate = "";
        ResidentManager instance = new ResidentManager();
        Vehicle expResult = null;
        Vehicle result = instance.findVehicleByPlate(plate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of authorizeVisitor method, of class ResidentManager.
     */
    @Test
    public void testAuthorizeVisitor() {
        System.out.println("authorizeVisitor");
        String residentId = "";
        String visitorId = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.authorizeVisitor(residentId, visitorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAuthorizedVisitor method, of class ResidentManager.
     */
    @Test
    public void testRemoveAuthorizedVisitor() {
        System.out.println("removeAuthorizedVisitor");
        String residentId = "";
        String visitorId = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.removeAuthorizedVisitor(residentId, visitorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createRentalForResident method, of class ResidentManager.
     */
    @Test
    public void testCreateRentalForResident() {
        System.out.println("createRentalForResident");
        String residentId = "";
        String spaceId = "";
        int months = 0;
        double monthlyPrice = 0.0;
        ResidentManager instance = new ResidentManager();
        Rental expResult = null;
        Rental result = instance.createRentalForResident(residentId, spaceId, months, monthlyPrice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelRentalForResident method, of class ResidentManager.
     */
    @Test
    public void testCancelRentalForResident() {
        System.out.println("cancelRentalForResident");
        String residentId = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.cancelRentalForResident(residentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of renewRentalForResident method, of class ResidentManager.
     */
    @Test
    public void testRenewRentalForResident() {
        System.out.println("renewRentalForResident");
        String residentId = "";
        int additionalMonths = 0;
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.renewRentalForResident(residentId, additionalMonths);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processPaymentForRental method, of class ResidentManager.
     */
    @Test
    public void testProcessPaymentForRental() {
        System.out.println("processPaymentForRental");
        String residentId = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.processPaymentForRental(residentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalVehicles method, of class ResidentManager.
     */
    @Test
    public void testGetTotalVehicles() {
        System.out.println("getTotalVehicles");
        ResidentManager instance = new ResidentManager();
        int expResult = 0;
        int result = instance.getTotalVehicles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllResidents method, of class ResidentManager.
     */
    @Test
    public void testGetAllResidents() {
        System.out.println("getAllResidents");
        ResidentManager instance = new ResidentManager();
        List<Resident> expResult = null;
        List<Resident> result = instance.getAllResidents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResidentsWithParking method, of class ResidentManager.
     */
    @Test
    public void testGetResidentsWithParking() {
        System.out.println("getResidentsWithParking");
        ResidentManager instance = new ResidentManager();
        List<Resident> expResult = null;
        List<Resident> result = instance.getResidentsWithParking();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRotatingResidents method, of class ResidentManager.
     */
    @Test
    public void testGetRotatingResidents() {
        System.out.println("getRotatingResidents");
        ResidentManager instance = new ResidentManager();
        List<Resident> expResult = null;
        List<Resident> result = instance.getRotatingResidents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRotatingResidentsWithRental method, of class ResidentManager.
     */
    @Test
    public void testGetRotatingResidentsWithRental() {
        System.out.println("getRotatingResidentsWithRental");
        ResidentManager instance = new ResidentManager();
        List<Resident> expResult = null;
        List<Resident> result = instance.getRotatingResidentsWithRental();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllActiveRentals method, of class ResidentManager.
     */
    @Test
    public void testGetAllActiveRentals() {
        System.out.println("getAllActiveRentals");
        ResidentManager instance = new ResidentManager();
        List<Rental> expResult = null;
        List<Rental> result = instance.getAllActiveRentals();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getExpiredRentals method, of class ResidentManager.
     */
    @Test
    public void testGetExpiredRentals() {
        System.out.println("getExpiredRentals");
        ResidentManager instance = new ResidentManager();
        List<Rental> expResult = null;
        List<Rental> result = instance.getExpiredRentals();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateResidentsReport method, of class ResidentManager.
     */
    @Test
    public void testGenerateResidentsReport() {
        System.out.println("generateResidentsReport");
        ResidentManager instance = new ResidentManager();
        String expResult = "";
        String result = instance.generateResidentsReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateRentalsReport method, of class ResidentManager.
     */
    @Test
    public void testGenerateRentalsReport() {
        System.out.println("generateRentalsReport");
        ResidentManager instance = new ResidentManager();
        String expResult = "";
        String result = instance.generateRentalsReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateVehiclesReport method, of class ResidentManager.
     */
    @Test
    public void testGenerateVehiclesReport() {
        System.out.println("generateVehiclesReport");
        ResidentManager instance = new ResidentManager();
        String expResult = "";
        String result = instance.generateVehiclesReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshData method, of class ResidentManager.
     */
    @Test
    public void testRefreshData() {
        System.out.println("refreshData");
        ResidentManager instance = new ResidentManager();
        instance.refreshData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalResidents method, of class ResidentManager.
     */
    @Test
    public void testGetTotalResidents() {
        System.out.println("getTotalResidents");
        ResidentManager instance = new ResidentManager();
        int expResult = 0;
        int result = instance.getTotalResidents();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateVisitorAccess method, of class ResidentManager.
     */
    @Test
    public void testValidateVisitorAccess() {
        System.out.println("validateVisitorAccess");
        String visitorId = "";
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.validateVisitorAccess(visitorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAuthorizingResident method, of class ResidentManager.
     */
    @Test
    public void testFindAuthorizingResident() {
        System.out.println("findAuthorizingResident");
        String visitorId = "";
        ResidentManager instance = new ResidentManager();
        Resident expResult = null;
        Resident result = instance.findAuthorizingResident(visitorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processVisitorEntry method, of class ResidentManager.
     */
    @Test
    public void testProcessVisitorEntry() {
        System.out.println("processVisitorEntry");
        Visitor visitor = null;
        ResidentManager instance = new ResidentManager();
        boolean expResult = false;
        boolean result = instance.processVisitorEntry(visitor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
