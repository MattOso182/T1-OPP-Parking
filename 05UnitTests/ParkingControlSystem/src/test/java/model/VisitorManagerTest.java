
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
public class VisitorManagerTest {
    
    public VisitorManagerTest() {
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
     * Test of addVisitor method, of class VisitorManager.
     */
    @Test
    public void testAddVisitor() {
        System.out.println("addVisitor");
        Visitor visitor = null;
        VisitorManager instance = null;
        instance.addVisitor(visitor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveVisitors method, of class VisitorManager.
     */
    @Test
    public void testSaveVisitors() {
        System.out.println("saveVisitors");
        VisitorManager instance = null;
        instance.saveVisitors();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findVisitorById method, of class VisitorManager.
     */
    @Test
    public void testFindVisitorById() {
        System.out.println("findVisitorById");
        String visitorID = "";
        VisitorManager instance = null;
        Visitor expResult = null;
        Visitor result = instance.findVisitorById(visitorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isVisitorAuthorized method, of class VisitorManager.
     */
    @Test
    public void testIsVisitorAuthorized() {
        System.out.println("isVisitorAuthorized");
        String visitorID = "";
        VisitorManager instance = null;
        boolean expResult = false;
        boolean result = instance.isVisitorAuthorized(visitorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recordVisitorExit method, of class VisitorManager.
     */
    @Test
    public void testRecordVisitorExit() {
        System.out.println("recordVisitorExit");
        String visitorID = "";
        VisitorManager instance = null;
        boolean expResult = false;
        boolean result = instance.recordVisitorExit(visitorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllVisitors method, of class VisitorManager.
     */
    @Test
    public void testListAllVisitors() {
        System.out.println("listAllVisitors");
        VisitorManager instance = null;
        instance.listAllVisitors();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllVisitors method, of class VisitorManager.
     */
    @Test
    public void testGetAllVisitors() {
        System.out.println("getAllVisitors");
        VisitorManager instance = null;
        List<Visitor> expResult = null;
        List<Visitor> result = instance.getAllVisitors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
