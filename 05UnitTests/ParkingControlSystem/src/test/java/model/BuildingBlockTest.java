package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import parkingcontrolsystem.library.ParkingSpaceLibrary;

/**
 * @author Team 1 - T.A.P.
 */
public class BuildingBlockTest {
    
    private BuildingBlock instance;
    private List<ParkingZone> testSections;
    private final String TEST_NAME = "Edificio Central";
    private final String TEST_CODE = "ED-01";

    public BuildingBlockTest() {
    }

    @BeforeEach
    public void setUp() {
        testSections = new ArrayList<>();
        testSections.add(new ParkingZone()); 
        instance = new BuildingBlock(TEST_NAME, TEST_CODE, testSections);
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
        testSections = null;
    }

   
    public void testConstructorAndGetters() {
        System.out.println("Testing Constructor and Getters");
        assertEquals(TEST_NAME, instance.getBlockName(), "El nombre del bloque no coincide");
        assertEquals(TEST_CODE, instance.getBlockCode(), "El código del bloque no coincide");
        assertEquals(1, instance.getSections().size(), "La cantidad de secciones no coincide");
        assertNotNull(instance.getParkingLot(), "ParkingLotLibrary no debería ser null");
    }

   
    @Test
    public void testGetBlockStatus() {
        System.out.println("getBlockStatus");
        String result = instance.getBlockStatus();
        assertTrue(result.contains(TEST_NAME), "El estado debe contener el nombre del bloque");
        assertTrue(result.contains("0 available"), "Debe indicar 0 espacios disponibles inicialmente");
    }

  
    @Test
    public void testAddParkingSpace() {
        System.out.println("addParkingSpace");
        ParkingSpaceLibrary space = new ParkingSpaceLibrary(); 
        
        int initialSpaces = instance.getAvailableSpaces();
        instance.addParkingSpace(space);
        
        assertEquals(initialSpaces + 1, instance.getAvailableSpaces(), 
                "El número de espacios debería haber aumentado en 1");
    }

    @Test
    public void testSetters() {
        System.out.println("Testing Setters");
        String newName = "Torre Norte";
        String newCode = "TN-02";
        
        instance.setBlockName(newName);
        instance.setBlockCode(newCode);
        
        assertEquals(newName, instance.getBlockName());
        assertEquals(newCode, instance.getBlockCode());
    }
}