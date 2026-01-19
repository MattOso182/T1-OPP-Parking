package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class ResidentRentalControllerTest {

    @Mock
    private ResidentController residentController;

    @Mock
    private ParkingSpaceController spaceController;

    @InjectMocks
    private ResidentRentalController controller;

    private Resident resident;
    private Rental rental;

    @BeforeEach
    void setUp() {
        resident = new Resident();
        resident.setResidentID("RES-001");
        resident.setName("Juan");
        resident.setApartmentNumber("A-101");
        resident.setUserType("ROTATING");

        rental = new Rental();
        rental.setPaymentStatus("PAID");
        rental.setSpaceId("P-01");

        resident.setCurrentRental(rental);
    }

    @Test
    @Order(1)
    @DisplayName("TC001: validate resident ID format")
    void testIsValidResidentId() {
        assertTrue(controller.isValidResidentId("RES-001"));
        assertFalse(controller.isValidResidentId("ABC-123"));
    }

    @Test
    @Order(2)
    @DisplayName("TC002: search resident success")
    void testSearchResidentSuccess() {
        when(residentController.searchResidentById("RES-001")).thenReturn(resident);

        Object[] result = controller.searchResident("RES-001");

        assertTrue((Boolean) result[0]);
        assertTrue(result[1].toString().contains("Juan"));
        assertEquals(new Color(0, 150, 0), result[2]);
        assertEquals(resident, result[3]);
    }

    @Test
    @Order(3)
    @DisplayName("TC003: search resident not found")
    void testSearchResidentNotFound() {
        when(residentController.searchResidentById("RES-999")).thenReturn(null);

        Object[] result = controller.searchResident("RES-999");

        assertFalse((Boolean) result[0]);
    }

    @Test
    @Order(4)
    @DisplayName("TC004: process payment success")
    void testProcessPayment() {
        rental.setPaymentStatus("PENDING");
        resident.setCurrentRental(rental);

        when(residentController.searchResidentById("RES-001")).thenReturn(resident);
        when(residentController.updatePaymentStatusOnly("RES-001", "PAID")).thenReturn(true);

        Object[] result = controller.processPayment("RES-001");

        assertTrue((Boolean) result[0]);
    }

    @Test
    @Order(5)
    @DisplayName("TC005: process rental renewal same space")
    void testProcessRentalRenewalSameSpace() {
        when(residentController.searchResidentById("RES-001")).thenReturn(resident);
        when(residentController.updateRentalDates("RES-001", 2)).thenReturn(true);

        Object[] result = controller.processRentalRenewal("RES-001", "P-01", 2);

        assertTrue((Boolean) result[0]);
        assertEquals(90.0, (Double) result[3]);
    }

    @Test
    @Order(6)
    @DisplayName("TC006: cancel rental success")
    void testCancelRental() {
        when(residentController.searchResidentById("RES-001")).thenReturn(resident);
        when(residentController.cancelRental("RES-001")).thenReturn(true);

        Object[] result = controller.cancelRental("RES-001");

        assertTrue((Boolean) result[0]);
        assertEquals("P-01", result[2]);
    }

    @Test
    @Order(7)
    @DisplayName("TC007: get available spaces")
    void testGetAvailableSpaces() {
        when(spaceController.getAvailableSpaces()).thenReturn(Arrays.asList("P-01", "P-02"));

        List<String> spaces = controller.getAvailableSpaces();

        assertEquals(2, spaces.size());
    }

    @Test
    @Order(8)
    @DisplayName("TC008: get space details")
    void testGetSpaceDetails() {
        Document doc = new Document("type", "COMPACT")
                .append("isOccupied", false);

        when(spaceController.getSpaceDetails("P-01")).thenReturn(doc);

        String details = controller.getSpaceDetails("P-01");

        assertTrue(details.contains("DISPONIBLE"));
    }

    @Test
    @Order(9)
    @DisplayName("TC009: calculate payment amount")
    void testCalculatePaymentAmount() {
        double amount = controller.calculatePaymentAmount(3);
        assertEquals(135.0, amount);
    }

    @Test
    @Order(10)
    @DisplayName("TC010: extract months from text")
    void testExtractMonthsFromText() {
        int months = controller.extractMonthsFromText("3 meses");
        assertEquals(3, months);
    }
}