
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
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getName method, of class ParkingStructure.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        ParkingStructure instance = new ParkingStructure();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class ParkingStructure.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        ParkingStructure instance = new ParkingStructure();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalSpaces method, of class ParkingStructure.
     */
    @Test
    public void testGetTotalSpaces() {
        System.out.println("getTotalSpaces");
        ParkingStructure instance = new ParkingStructure();
        int expResult = 0;
        int result = instance.getTotalSpaces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTotalSpaces method, of class ParkingStructure.
     */
    @Test
    public void testSetTotalSpaces() {
        System.out.println("setTotalSpaces");
        int totalSpaces = 0;
        ParkingStructure instance = new ParkingStructure();
        instance.setTotalSpaces(totalSpaces);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableForRent method, of class ParkingStructure.
     */
    @Test
    public void testGetAvailableForRent() {
        System.out.println("getAvailableForRent");
        ParkingStructure instance = new ParkingStructure();
        int expResult = 0;
        int result = instance.getAvailableForRent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAvailableForRent method, of class ParkingStructure.
     */
    @Test
    public void testSetAvailableForRent() {
        System.out.println("setAvailableForRent");
        int availableForRent = 0;
        ParkingStructure instance = new ParkingStructure();
        instance.setAvailableForRent(availableForRent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlocks method, of class ParkingStructure.
     */
    @Test
    public void testGetBlocks() {
        System.out.println("getBlocks");
        ParkingStructure instance = new ParkingStructure();
        List<BuildingBlock> expResult = null;
        List<BuildingBlock> result = instance.getBlocks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBlocks method, of class ParkingStructure.
     */
    @Test
    public void testSetBlocks() {
        System.out.println("setBlocks");
        List<BuildingBlock> blocks = null;
        ParkingStructure instance = new ParkingStructure();
        instance.setBlocks(blocks);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
