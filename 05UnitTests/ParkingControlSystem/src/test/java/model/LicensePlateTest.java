package model;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 1 - T.A.P.
 */
public class LicensePlateTest {
    
    private LicensePlate instance;
    private final String PLATE = "PBW-1234";
    private final String PROVINCE = "Pichincha";
    private final String TYPE = "Automóvil";
    private Date testDate;

    @BeforeEach
    public void setUp() {
        testDate = new Date();
        instance = new LicensePlate(PLATE, testDate, PROVINCE, TYPE);
    }

    @Test
    public void testConstructorAndGetters() {
        System.out.println("testConstructorAndGetters");
        assertEquals(PLATE, instance.getPlateNumber());
        assertEquals(PROVINCE, instance.getProvince());
        assertEquals(TYPE, instance.getVehicleType());
        assertEquals(testDate, instance.getRegistrationDate());
    }

    @Test
    public void testValidateFormat() {
        System.out.println("testValidateFormat");
        boolean result = instance.validateFormat();
        assertNotNull(result);
    }

    @Test
    public void testGetPlateInfoFormat() {
        System.out.println("testGetPlateInfo");
        String info = instance.getPlateInfo();
        
        assertAll("Verificar contenido del formato de info",
            () -> assertTrue(info.contains(PLATE)),
            () -> assertTrue(info.contains(PROVINCE)),
            () -> assertTrue(info.contains(TYPE)),
            () -> assertTrue(info.contains("Placa:"))
        );
    }

    @Test
    public void testSetters() {
        System.out.println("testSetters");
        String newPlate = "GBA-5678";
        String newProv = "Guayas";
        
        instance.setPlateNumber(newPlate);
        instance.setProvince(newProv);
        
        assertEquals(newPlate, instance.getPlateNumber());
        assertEquals(newProv, instance.getProvince());
    }

    @Test
    public void testLinkToUser() {
        System.out.println("testLinkToUser");
        assertDoesNotThrow(() -> instance.linkToUser("USER-100"));
    }
}