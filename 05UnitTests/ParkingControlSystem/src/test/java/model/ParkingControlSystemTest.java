package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emily Calle, @ESPE
 */
public class ParkingControlSystemTest {
    
    private ParkingControlSystem instance;
    private ParkingLot parkingLot;
    private ResidentManager residentManager;
    
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
       ParkingLot parkingLot = new ParkingLot("L01"); 
        ResidentManager residentManager = new ResidentManager();
        
        instance = new ParkingControlSystem("SYS-01", parkingLot, residentManager);
    }
    @Test
    public void shouldStartSystem() {
        boolean result = instance.startSystem();
        assertNotNull(result);
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testStartSystem() {
        System.out.println("startSystem");
        boolean result = instance.startSystem();
        assertTrue(result);
    }

    @Test
    public void testStopSystem() {
        System.out.println("stopSystem");
        instance.stopSystem();
        assertFalse(instance.isActive());
    }

    @Test
    public void testRegisterEntry() {
        System.out.println("registerEntry");
        String plate = "PBX-1234";
        boolean result = instance.registerEntry(plate);
        assertTrue(result);
    }

    @Test
    public void testRegisterExit() {
        System.out.println("registerExit");
        String plate = "PBX-1234";
        instance.registerEntry(plate);
        boolean result = instance.registerExit(plate);
        assertTrue(result);
    }

    @Test
    public void testCheckAvailability() {
        System.out.println("checkAvailability");
        int result = instance.checkAvailability();
        assertTrue(result >= 0);
    }

    @Test
    public void testGenerateReport() {
        System.out.println("generateReport");
        String result = instance.generateReport();
        assertNotNull(result);
    }

    @Test
    public void testGetSystemId() {
        System.out.println("getSystemId");
        String expResult = "SYS-001";
        String result = instance.getSystemId();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsActive() {
        System.out.println("isActive");
        instance.startSystem();
        assertTrue(instance.isActive());
    }

    @Test
    public void testGetTotalVehicles() {
        System.out.println("getTotalVehicles");
        int result = instance.getTotalVehicles();
        assertEquals(0, result);
    }

    @Test
    public void testGetParkingLot() {
        System.out.println("getParkingLot");
        ParkingLot result = instance.getParkingLot();
        assertEquals(parkingLot, result);
    }

    @Test
    public void testCheckSystemStatus() {
        System.out.println("checkSystemStatus");
        boolean result = instance.checkSystemStatus();
        assertTrue(result);
    }

    @Test
    public void testGetDetailedReport() {
        System.out.println("getDetailedReport");
        String result = instance.getDetailedReport();
        assertNotNull(result);
    }
}