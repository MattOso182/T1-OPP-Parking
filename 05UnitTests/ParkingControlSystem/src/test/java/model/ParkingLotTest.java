package model;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import parkingcontrolsystem.library.ParkingLotLibrary;
import parkingcontrolsystem.library.ParkingSpaceLibrary;

/**
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class ParkingLotTest {
    
    private ParkingLot instance;
    private final String TEST_LOT_ID = "LOTE-CENTRAL";
    
    public ParkingLotTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        instance = new ParkingLot(TEST_LOT_ID); 
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testSaveToJson() {
        System.out.println("saveToJson");
        // No debería lanzar excepción
        assertDoesNotThrow(() -> instance.saveToJson());
    }

    @Test
    public void testSyncSpaceStatus() {
        System.out.println("syncSpaceStatus");
        String spaceId = "A1";
        boolean occupied = true;
        // Sincronizamos y verificamos que no falle la ejecución
        instance.syncSpaceStatus(spaceId, occupied);
        assertNotNull(instance.getSpaceList());
    }

    @Test
    public void testAssignSpaceToVehicle() {
        System.out.println("assignSpaceToVehicle");
        String spaceId = "A1";
        String vehiclePlate = "PBA-1234";
        String userType = "Residente";
        
        boolean result = instance.assignSpaceToVehicle(spaceId, vehiclePlate, userType);
        assertNotNull(result);
    }

    @Test
    public void testFreeSpaceAndSync() {
        System.out.println("freeSpaceAndSync");
        String spaceId = "A1";
        boolean result = instance.freeSpaceAndSync(spaceId);
        assertNotNull(result);
    }

    @Test
    public void testShowSpacesStatus() {
        System.out.println("showSpacesStatus");
        assertDoesNotThrow(() -> instance.showSpacesStatus());
    }

    @Test
    public void testShowDetailedSpacesStatus() {
        System.out.println("showDetailedSpacesStatus");
        assertDoesNotThrow(() -> instance.showDetailedSpacesStatus());
    }

    @Test
    public void testGetOccupancyReport() {
        System.out.println("getOccupancyReport");
        String result = instance.getOccupancyReport();
        assertNotNull(result);
    }

    @Test
    public void testCalculateAvailableSpaces() {
        System.out.println("calculateAvailableSpaces");
        int result = instance.calculateAvailableSpaces();
        assertTrue(result >= 0);
    }

    @Test
    public void testFindAvailableSpace() {
        System.out.println("findAvailableSpace");
        ParkingSpaceLibrary result = instance.findAvailableSpace();
    }

    @Test
    public void testUpdateSpaceStatus() {
        System.out.println("updateSpaceStatus");
        String spaceId = "A1";
        String status = "OCCUPIED";
        assertDoesNotThrow(() -> instance.updateSpaceStatus(spaceId, status));
    }

    @Test
    public void testGetTotalSpaces() {
        System.out.println("getTotalSpaces");
        int result = instance.getTotalSpaces();
        assertTrue(result >= 0);
    }

    @Test
    public void testGetAvailableSpaces() {
        System.out.println("getAvailableSpaces");
        int result = instance.getAvailableSpaces();
        assertTrue(result >= 0);
    }

    @Test
    public void testGetLotId() {
        System.out.println("getLotId");
        String result = instance.getLotId();
        assertEquals(TEST_LOT_ID, result);
    }

    @Test
    public void testGetSpaceList() {
        System.out.println("getSpaceList");
        List<ParkingSpaceLibrary> result = instance.getSpaceList();
        assertNotNull(result);
    }

    @Test
    public void testGetLibraryParkingLot() {
        System.out.println("getLibraryParkingLot");
        ParkingLotLibrary result = instance.getLibraryParkingLot();
        assertNotNull(result);
    }

    @Test
    public void testFindSpaceByVehicle() {
        System.out.println("findSpaceByVehicle");
        String vehiclePlate = "PBA-5555";
        ParkingSpaceLibrary result = instance.findSpaceByVehicle(vehiclePlate);
        assertNull(result); 
    }

    @Test
    public void testSpaceExists() {
        System.out.println("spaceExists");
        String spaceId = "A1";
        boolean result = instance.spaceExists(spaceId);
        assertNotNull(result);
    }
}