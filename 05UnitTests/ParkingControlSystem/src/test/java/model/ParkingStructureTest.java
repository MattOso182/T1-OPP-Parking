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
public class ParkingStructureTest {
    
    private ParkingStructure instance;

    public ParkingStructureTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        instance = new ParkingStructure();
    }
    
    @AfterEach
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getName method, of class ParkingStructure.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // Se mantiene el fail como pediste
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class ParkingStructure.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        instance.setName(name);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalSpaces method, of class ParkingStructure.
     */
    @Test
    public void testGetTotalSpaces() {
        System.out.println("getTotalSpaces");
        int expResult = 0;
        int result = instance.getTotalSpaces();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTotalSpaces method, of class ParkingStructure.
     */
    @Test
    public void testSetTotalSpaces() {
        System.out.println("setTotalSpaces");
        int totalSpaces = 0;
        instance.setTotalSpaces(totalSpaces);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableForRent method, of class ParkingStructure.
     */
    @Test
    public void testGetAvailableForRent() {
        System.out.println("getAvailableForRent");
        int expResult = 0;
        int result = instance.getAvailableForRent();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAvailableForRent method, of class ParkingStructure.
     */
    @Test
    public void testSetAvailableForRent() {
        System.out.println("setAvailableForRent");
        int availableForRent = 0;
        instance.setAvailableForRent(availableForRent);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlocks method, of class ParkingStructure.
     */
    @Test
    public void testGetBlocks() {
        System.out.println("getBlocks");
        List<BuildingBlock> expResult = null;
        List<BuildingBlock> result = instance.getBlocks();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBlocks method, of class ParkingStructure.
     */
    @Test
    public void testSetBlocks() {
        System.out.println("setBlocks");
        List<BuildingBlock> blocks = null;
        instance.setBlocks(blocks);
        fail("The test case is a prototype.");
    }
}