
package model;

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
public class SpaceDefinitionTest {
    
    public SpaceDefinitionTest() {
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
     * Test of getSpaceId method, of class SpaceDefinition.
     */
    @Test
    public void testGetSpaceId() {
        System.out.println("getSpaceId");
        SpaceDefinition instance = null;
        String expResult = "";
        String result = instance.getSpaceId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOccupied method, of class SpaceDefinition.
     */
    @Test
    public void testIsOccupied() {
        System.out.println("isOccupied");
        SpaceDefinition instance = null;
        boolean expResult = false;
        boolean result = instance.isOccupied();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOccupied method, of class SpaceDefinition.
     */
    @Test
    public void testSetOccupied() {
        System.out.println("setOccupied");
        boolean occupied = false;
        SpaceDefinition instance = null;
        instance.setOccupied(occupied);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAvailableForRent method, of class SpaceDefinition.
     */
    @Test
    public void testIsAvailableForRent() {
        System.out.println("isAvailableForRent");
        SpaceDefinition instance = null;
        boolean expResult = false;
        boolean result = instance.isAvailableForRent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SpaceDefinition.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SpaceDefinition instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class SpaceDefinition.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        SpaceDefinition instance = null;
        String expResult = "";
        String result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
