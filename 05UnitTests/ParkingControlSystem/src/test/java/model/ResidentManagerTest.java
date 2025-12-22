package model;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class ResidentManagerTest {
    
    private ResidentManager residentManager;

    @BeforeEach
    public void setUp() {
        residentManager = new ResidentManager();
    }

    @Test
    public void shouldNotAddNullResident() {
        boolean result = residentManager.addResident(null);
        assertFalse(result, "No se debería poder agregar un residente nulo");
    }

    @Test
    public void shouldReturnNullWhenResidentNotFoundById() {
        Resident result = residentManager.findResidentById("ID-INEXISTENTE");
        assertNull(result);
    }

    @Test
    public void shouldReturnNullWhenResidentNotFoundByPlate() {
        Resident result = residentManager.findResidentByVehiclePlate("ABC-0000");
        assertNull(result);
    }

    @Test
    public void shouldReturnFalseWhenRemovingInexistentResident() {
        boolean result = residentManager.removeResident("999999");
        assertFalse(result);
    }

    @Test
    public void shouldNotUpdateInexistentResident() {
        boolean result = residentManager.updateResidentInfo("NO-ID", "email@test.com", "0999");
        assertFalse(result);
    }

    @Test
    public void shouldNotAddVehicleToNullResident() {
        boolean result = residentManager.addVehicleToResident("", null);
        assertFalse(result);
    }

    @Test
    public void shouldNotRemoveVehicleFromInexistentResident() {
        boolean result = residentManager.removeVehicleFromResident("NON-ID", "ABC-123");
        assertFalse(result);
    }

    @Test
    public void shouldReturnNullForInexistentVehiclePlate() {
        Vehicles result = residentManager.findVehicleByPlate("NON-PLATE");
        assertNull(result);
    }

    @Test
    public void shouldNotAuthorizeVisitorForInexistentResident() {
        boolean result = residentManager.authorizeVisitor("NON-RES", "VIS-01");
        assertFalse(result);
    }

    @Test
    public void shouldNotRemoveInexistentAuthorizedVisitor() {
        boolean result = residentManager.removeAuthorizedVisitor("RES-01", "VIS-01");
        assertFalse(result);
    }

    @Test
    public void shouldReturnNullWhenCreatingRentalForInvalidData() {
        Rental result = residentManager.createRentalForResident("", "", 0, 0.0);
        assertNull(result);
    }

    @Test
    public void shouldNotCancelRentalForInexistentResident() {
        boolean result = residentManager.cancelRentalForResident("NO-ID");
        assertFalse(result);
    }

    @Test
    public void shouldNotRenewInexistentRental() {
        boolean result = residentManager.renewRentalForResident("NO-ID", 1);
        assertFalse(result);
    }

    @Test
    public void shouldFailPaymentProcessForInexistentResident() {
        boolean result = residentManager.processPaymentForRental("NO-ID");
        assertFalse(result);
    }

    @Test
    public void shouldStartWithZeroVehicles() {
        assertEquals(0, residentManager.getTotalVehicles());
    }

    @Test
    public void shouldReturnEmptyListForResidentsInitially() {
        List<Resident> result = residentManager.getAllResidents();
        // Dependiendo de tu implementación, puede ser null o una lista vacía
        assertTrue(result == null || result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForResidentsWithParkingInitially() {
        List<Resident> result = residentManager.getResidentsWithParking();
        assertTrue(result == null || result.isEmpty());
    }

    @Test
    public void shouldGenerateEmptyResidentsReport() {
        String report = residentManager.generateResidentsReport();
        assertNotNull(report);
    }

    @Test
    public void shouldGenerateEmptyRentalsReport() {
        String report = residentManager.generateRentalsReport();
        assertNotNull(report);
    }

    @Test
    public void shouldGenerateEmptyVehiclesReport() {
        String report = residentManager.generateVehiclesReport();
        assertNotNull(report);
    }

    @Test
    public void shouldNotThrowExceptionOnRefresh() {
        assertDoesNotThrow(() -> residentManager.refreshData());
    }

    @Test
    public void shouldStartWithZeroTotalResidents() {
        assertEquals(0, residentManager.getTotalResidents());
    }

    @Test
    public void shouldDenyAccessToUnknownVisitor() {
        boolean result = residentManager.validateVisitorAccess("UNKNOWN-ID");
        assertFalse(result);
    }

    @Test
    public void shouldReturnNullAuthorizerForUnknownVisitor() {
        Resident result = residentManager.findAuthorizingResident("UNKNOWN-ID");
        assertNull(result);
    }

    @Test
    public void shouldNotProcessNullVisitorEntry() {
        boolean result = residentManager.processVisitorEntry(null);
        assertFalse(result);
    }
}