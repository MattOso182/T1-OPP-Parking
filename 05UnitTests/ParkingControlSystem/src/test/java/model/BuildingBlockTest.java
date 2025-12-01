
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
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class BuildingBlockTest {
    
    public BuildingBlockTest() {
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
     * Test of addParkingSpace method, of class BuildingBlock.
     */
    @Test
    public void testAddParkingSpace() {
        System.out.println("addParkingSpace");
        ParkingSpaceLibrary space = null;
        BuildingBlock instance = new BuildingBlock();
        instance.addParkingSpace(space);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableSpaces method, of class BuildingBlock.
     */
    @Test
    public void testGetAvailableSpaces() {
        System.out.println("getAvailableSpaces");
        BuildingBlock instance = new BuildingBlock();
        int expResult = 0;
        int result = instance.getAvailableSpaces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlockStatus method, of class BuildingBlock.
     */
    @Test
    public void testGetBlockStatus() {
        System.out.println("getBlockStatus");
        BuildingBlock instance = new BuildingBlock();
        String expResult = "";
        String result = instance.getBlockStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlockName method, of class BuildingBlock.
     */
    @Test
    public void testGetBlockName() {
        System.out.println("getBlockName");
        BuildingBlock instance = new BuildingBlock();
        String expResult = "";
        String result = instance.getBlockName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBlockName method, of class BuildingBlock.
     */
    @Test
    public void testSetBlockName() {
        System.out.println("setBlockName");
        String blockName = "";
        BuildingBlock instance = new BuildingBlock();
        instance.setBlockName(blockName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlockCode method, of class BuildingBlock.
     */
    @Test
    public void testGetBlockCode() {
        System.out.println("getBlockCode");
        BuildingBlock instance = new BuildingBlock();
        String expResult = "";
        String result = instance.getBlockCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBlockCode method, of class BuildingBlock.
     */
    @Test
    public void testSetBlockCode() {
        System.out.println("setBlockCode");
        String blockCode = "";
        BuildingBlock instance = new BuildingBlock();
        instance.setBlockCode(blockCode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSections method, of class BuildingBlock.
     */
    @Test
    public void testGetSections() {
        System.out.println("getSections");
        BuildingBlock instance = new BuildingBlock();
        List<ParkingZone> expResult = null;
        List<ParkingZone> result = instance.getSections();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSections method, of class BuildingBlock.
     */
    @Test
    public void testSetSections() {
        System.out.println("setSections");
        List<ParkingZone> sections = null;
        BuildingBlock instance = new BuildingBlock();
        instance.setSections(sections);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParkingLot method, of class BuildingBlock.
     */
    @Test
    public void testGetParkingLot() {
        System.out.println("getParkingLot");
        BuildingBlock instance = new BuildingBlock();
        ParkingLotLibrary expResult = null;
        ParkingLotLibrary result = instance.getParkingLot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
