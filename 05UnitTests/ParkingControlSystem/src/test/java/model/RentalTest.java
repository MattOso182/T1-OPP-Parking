
package model;

import java.util.Date;
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
public class RentalTest {
    
    public RentalTest() {
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
     * Test of isExpired method, of class Rental.
     */
    @Test
    public void testIsExpired() {
        System.out.println("isExpired");
        Rental instance = new Rental();
        boolean expResult = false;
        boolean result = instance.isExpired();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDaysRemaining method, of class Rental.
     */
    @Test
    public void testGetDaysRemaining() {
        System.out.println("getDaysRemaining");
        Rental instance = new Rental();
        long expResult = 0L;
        long result = instance.getDaysRemaining();
        assertEquals(expResult, result);
    }

    /**
     * Test of renewRental method, of class Rental.
     */
    @Test
    public void testRenewRental() {
        System.out.println("renewRental");
        int additionalMonths = 0;
        Rental instance = new Rental();
        boolean expResult = false;
        boolean result = instance.renewRental(additionalMonths);
        assertEquals(expResult, result);
    }

    /**
     * Test of cancelRental method, of class Rental.
     */
    @Test
    public void testCancelRental() {
        System.out.println("cancelRental");
        Rental instance = new Rental();
        boolean expResult = false;
        boolean result = instance.cancelRental();
        assertEquals(expResult, result);
    }

    /**
     * Test of processPayment method, of class Rental.
     */
    @Test
    public void testProcessPayment() {
        System.out.println("processPayment");
        Rental instance = new Rental();
        boolean expResult = false;
        boolean result = instance.processPayment();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRentalId method, of class Rental.
     */
    @Test
    public void testGetRentalId() {
        System.out.println("getRentalId");
        Rental instance = new Rental();
        String expResult = "";
        String result = instance.getRentalId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getResidentId method, of class Rental.
     */
    @Test
    public void testGetResidentId() {
        System.out.println("getResidentId");
        Rental instance = new Rental();
        String expResult = "";
        String result = instance.getResidentId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSpaceId method, of class Rental.
     */
    @Test
    public void testGetSpaceId() {
        System.out.println("getSpaceId");
        Rental instance = new Rental();
        String expResult = "";
        String result = instance.getSpaceId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStartDate method, of class Rental.
     */
    @Test
    public void testGetStartDate() {
        System.out.println("getStartDate");
        Rental instance = new Rental();
        Date expResult = null;
        Date result = instance.getStartDate();
        assertEquals(expResult, result);
    
    }

    /**
     * Test of getEndDate method, of class Rental.
     */
    @Test
    public void testGetEndDate() {
        System.out.println("getEndDate");
        Rental instance = new Rental();
        Date expResult = null;
        Date result = instance.getEndDate();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getMonthlyPrice method, of class Rental.
     */
    @Test
    public void testGetMonthlyPrice() {
        System.out.println("getMonthlyPrice");
        Rental instance = new Rental();
        double expResult = 0.0;
        double result = instance.getMonthlyPrice();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of isActive method, of class Rental.
     */
    @Test
    public void testIsActive() {
        System.out.println("isActive");
        Rental instance = new Rental();
        boolean expResult = false;
        boolean result = instance.isActive();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPaymentStatus method, of class Rental.
     */
    @Test
    public void testGetPaymentStatus() {
        System.out.println("getPaymentStatus");
        Rental instance = new Rental();
        String expResult = "";
        String result = instance.getPaymentStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEndDate method, of class Rental.
     */
    @Test
    public void testSetEndDate() {
        System.out.println("setEndDate");
        Date endDate = null;
        Rental instance = new Rental();
        instance.setEndDate(endDate);
     
    }

    /**
     * Test of setMonthlyPrice method, of class Rental.
     */
    @Test
    public void testSetMonthlyPrice() {
        System.out.println("setMonthlyPrice");
        double monthlyPrice = 0.0;
        Rental instance = new Rental();
        instance.setMonthlyPrice(monthlyPrice);
     
    }

    /**
     * Test of setPaymentStatus method, of class Rental.
     */
    @Test
    public void testSetPaymentStatus() {
        System.out.println("setPaymentStatus");
        String paymentStatus = "";
        Rental instance = new Rental();
        instance.setPaymentStatus(paymentStatus);
    }

    /**
     * Test of getRentalInfo method, of class Rental.
     */
    @Test
    public void testGetRentalInfo() {
        System.out.println("getRentalInfo");
        Rental instance = new Rental();
        String expResult = "";
        String result = instance.getRentalInfo();
        assertEquals(expResult, result);
    }
    
}
