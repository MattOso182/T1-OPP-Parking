package model;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 1 - T.A.P.
 */
public class EntryExitRecordTest {
    
    private EntryExitRecord record;
    private final String TEST_PLATE = "ABC-1234";

    public EntryExitRecordTest() {
    }

    @BeforeEach
    public void setUp() {
        record = new EntryExitRecord();
    }
    
    @AfterEach
    public void tearDown() {
        record = null;
    }

    @Test
    public void testConstructorAndID() {
        System.out.println("testConstructorAndID");
        assertNotNull(record.getRecordID(), "El ID del registro no debe ser nulo");
        assertTrue(record.getRecordID().startsWith("REC-"), "El ID debe iniciar con el prefijo REC-");
    }

    @Test
    public void testRegisterEntry() {
        System.out.println("testRegisterEntry");
        Date entryTime = new Date();
        
        record.registerEntry(TEST_PLATE, entryTime);
        
        assertEquals(TEST_PLATE, record.getVehiclePlate(), "La placa no coincide tras el registro");
        assertEquals(entryTime, record.getEntryTime(), "La hora de entrada no coincide");
    }

    @Test
    public void testRegisterExitSuccess() {
        System.out.println("testRegisterExitSuccess");
        Date entryTime = new Date(System.currentTimeMillis() - 3600000); 
        Date exitTime = new Date();
        
        record.registerEntry(TEST_PLATE, entryTime);
        
        record.registerExit(TEST_PLATE, exitTime);
        
        assertEquals(exitTime, record.getExitTime(), "La hora de salida debería haberse actualizado");
    }

    @Test
    public void testRegisterExitFailure() {
        System.out.println("testRegisterExitFailure");
        Date exitTime = new Date();
        
        record.registerExit("XYZ-999", exitTime);
        
        assertNull(record.getExitTime(), "La salida no debería registrarse si no hay entrada activa");
    }

    @Test
    public void testCalculateDuration() {
        System.out.println("testCalculateDuration");
        double duration = record.calculateDuration();
        assertNotNull(duration);
    }

    @Test
    public void testVerifyRentalStatus() {
        System.out.println("testVerifyRentalStatus");
        assertTrue(record.verifyRentalStatus());
    }

    @Test
    public void testSettersAndGetters() {
        System.out.println("testSettersAndGetters");
        String operator = "OP-001";
        String space = "A-15";
        
        record.setOperatorID(operator);
        record.setParkingSpaceID(space);
        
        assertEquals(operator, record.getOperatorID());
        assertEquals(space, record.getParkingSpaceID());
    }
}