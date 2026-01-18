package ec.edu.espe.parkinglotgui.controller;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("ParkingSpaceController Tests")
public class ParkingSpaceControllerTest {

    private final ParkingSpaceController controller = new ParkingSpaceController();

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Order(1)
    @DisplayName("TC001: Constructor doesn't throw")
    void testConstructor_NoException() {
        assertDoesNotThrow(() -> {
            new ParkingSpaceController();
        });
    }

    @Test
    @Order(2)
    @DisplayName("TC002: getFirstDocument returns null without DB")
    void testGetFirstDocument_NoDB() {
        Document result = controller.getFirstDocument();
        assertNull(result, "Should return null when no database connection");
    }

    @Test
    @Order(3)
    @DisplayName("TC003: getParkingComplexInfo returns null without data")
    void testGetParkingComplexInfo_NoData() {
        Document result = controller.getParkingComplexInfo();
        assertNull(result, "Should return null when no parking complex data");
    }

    @Test
    @Order(4)
    @DisplayName("TC004: getAvailableSpaces returns empty list without data")
    void testGetAvailableSpaces_NoData() {
        List<String> result = controller.getAvailableSpaces();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("TC005: getSpaceDetails with null ID")
    void testGetSpaceDetails_NullId() {
        Document result = controller.getSpaceDetails(null);
        assertNull(result);
    }

    @Test
    @Order(6)
    @DisplayName("TC006: getSpaceDetails with empty ID")
    void testGetSpaceDetails_EmptyId() {
        Document result = controller.getSpaceDetails("");
        assertNull(result);
    }

    @ParameterizedTest
    @Order(7)
    @NullAndEmptySource
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    @DisplayName("TC007: updateSpaceOccupation with invalid spaceId")
    void testUpdateSpaceOccupation_InvalidId(String spaceId) {
        boolean result = controller.updateSpaceOccupation(spaceId, true);
        assertFalse(result);
    }

    @Test
    @Order(8)
    @DisplayName("TC008: freeParkingSpace with null ID")
    void testFreeParkingSpace_NullId() {
        boolean result = controller.freeParkingSpace(null);
        assertFalse(result);
    }

    @Test
    @Order(9)
    @DisplayName("TC009: getAvailableSpacesDetails returns empty list")
    void testGetAvailableSpacesDetails_NoData() {
        List<Document> result = controller.getAvailableSpacesDetails();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(10)
    @DisplayName("TC010: cleanSpaceId null input")
    void testCleanSpaceId_Null() {
        try {
            var method = ParkingSpaceController.class.getDeclaredMethod("cleanSpaceId", String.class);
            method.setAccessible(true);
            String result = (String) method.invoke(controller, (String) null);
            assertEquals("", result);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    @Order(11)
    @DisplayName("TC011: cleanSpaceId trims underscores")
    void testCleanSpaceId_TrimsUnderscores() {
        try {
            var method = ParkingSpaceController.class.getDeclaredMethod("cleanSpaceId", String.class);
            method.setAccessible(true);
            String result = (String) method.invoke(controller, "A1_");
            assertEquals("A1", result);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    @Order(12)
    @DisplayName("TC012: cleanField null input")
    void testCleanField_Null() {
        try {
            var method = ParkingSpaceController.class.getDeclaredMethod("cleanField", String.class);
            method.setAccessible(true);
            String result = (String) method.invoke(controller, (String) null);
            assertEquals("", result);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    @Order(13)
    @DisplayName("TC013: cleanField trims underscores and commas")
    void testCleanField_TrimsSpecialChars() {
        try {
            var method = ParkingSpaceController.class.getDeclaredMethod("cleanField", String.class);
            method.setAccessible(true);
            String result = (String) method.invoke(controller, "Block A_,");
            assertEquals("Block A", result);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    @Order(14)
    @DisplayName("TC014: updateSpaceOccupation returns false for non-existent space")
    void testUpdateSpaceOccupation_NonExistent() {
        boolean result = controller.updateSpaceOccupation("NON_EXISTENT", true);
        assertFalse(result);
    }

    @Test
    @Order(15)
    @DisplayName("TC015: freeParkingSpace returns false for non-existent space")
    void testFreeParkingSpace_NonExistent() {
        boolean result = controller.freeParkingSpace("NON_EXISTENT");
        assertFalse(result);
    }

    @Test
    @Order(16)
    @DisplayName("TC016: getFirstDocument should return document (wrong)")
    void testGetFirstDocument_ShouldReturnDoc() {
        Document result = controller.getFirstDocument();
        assertNotNull(result, "THIS WILL FAIL - no DB connection");
    }

    @Test
    @Order(17)
    @DisplayName("TC017: getParkingComplexInfo should have data")
    void testGetParkingComplexInfo_ShouldHaveData() {
        Document result = controller.getParkingComplexInfo();
        assertNotNull(result);
        assertTrue(result.containsKey("name"), "THIS WILL FAIL - no data");
        assertTrue(result.containsKey("totalSpaces"), "THIS WILL FAIL - no data");
    }

    @Test
    @Order(18)
    @DisplayName("TC018: getAvailableSpaces should return spaces")
    void testGetAvailableSpaces_ShouldReturnSpaces() {
        List<String> result = controller.getAvailableSpaces();
        assertFalse(result.isEmpty(), "THIS WILL FAIL - no spaces available");
        assertEquals(5, result.size(), "THIS WILL FAIL - wrong count");
    }

    @Test
    @Order(19)
    @DisplayName("TC019: getSpaceDetails with valid ID")
    void testGetSpaceDetails_ValidId() {
        Document result = controller.getSpaceDetails("A1");
        assertNotNull(result, "THIS WILL FAIL - no such space");
        assertEquals("A1", result.getString("spaceId"), "THIS WILL FAIL - wrong ID");
    }

    @Test
    @Order(20)
    @DisplayName("TC020: updateSpaceOccupation should work")
    void testUpdateSpaceOccupation_ShouldWork() {
        boolean result = controller.updateSpaceOccupation("A1", true);
        assertTrue(result, "THIS WILL FAIL - cannot update without DB");
    }

    @Test
    @Order(21)
    @DisplayName("TC021: freeParkingSpace should work")
    void testFreeParkingSpace_ShouldWork() {
        boolean result = controller.freeParkingSpace("A1");
        assertTrue(result, "THIS WILL FAIL - cannot free without DB");
    }

    @Test
    @Order(22)
    @DisplayName("TC022: getAvailableSpacesDetails should return details")
    void testGetAvailableSpacesDetails_ShouldReturnData() {
        List<Document> result = controller.getAvailableSpacesDetails();
        assertFalse(result.isEmpty(), "THIS WILL FAIL - no spaces");
        assertEquals(10, result.size(), "THIS WILL FAIL - wrong count");
    }

    @Test
    @Order(23)
    @DisplayName("TC023: cleanSpaceId should preserve original")
    void testCleanSpaceId_PreserveOriginal() {
        try {
            var method = ParkingSpaceController.class.getDeclaredMethod("cleanSpaceId", String.class);
            method.setAccessible(true);
            String input = "A1_";
            String result = (String) method.invoke(controller, input);
            assertEquals(input, result, "THIS WILL FAIL - should trim underscores");
        } catch (Exception e) {
            fail("Reflection failed");
        }
    }

    @Test
    @Order(24)
    @DisplayName("TC024: cleanField should preserve commas")
    void testCleanField_PreserveCommas() {
        try {
            var method = ParkingSpaceController.class.getDeclaredMethod("cleanField", String.class);
            method.setAccessible(true);
            String input = "Block A,";
            String result = (String) method.invoke(controller, input);
            assertEquals(input, result, "THIS WILL FAIL - should trim commas");
        } catch (Exception e) {
            fail("Reflection failed");
        }
    }

    @Test
    @Order(25)
    @DisplayName("TC025: updateSpaceOccupation with null isOccupied")
    void testUpdateSpaceOccupation_NullIsOccupied() {
        boolean result = controller.updateSpaceOccupation("A1", true);
        assertFalse(result, "THIS WILL FAIL - parameter is boolean, not Boolean");
    }

    @Test
    @Order(26)
    @DisplayName("TC026: freeParkingSpace with very long ID")
    void testFreeParkingSpace_LongId() {
        String longId = "A".repeat(1000);
        boolean result = controller.freeParkingSpace(longId);
        assertTrue(result, "THIS WILL FAIL - long ID shouldn't work");
    }

    @Test
    @Order(27)
    @DisplayName("TC027: getSpaceDetails with SQL injection")
    void testGetSpaceDetails_SQLInjection() {
        String injection = "A1' OR '1'='1";
        Document result = controller.getSpaceDetails(injection);
        assertNotNull(result, "THIS WILL FAIL - SQL injection should fail");
    }

    @Test
    @Order(28)
    @DisplayName("TC028: Performance test - timeout")
    void testPerformance_Timeout() {
        assertTimeoutPreemptively(java.time.Duration.ofMillis(1), () -> {
            for (int i = 0; i < 1000000; i++) {
                controller.getAvailableSpaces();
            }
            fail("THIS SHOULD TIMEOUT");
        });
    }

    @ParameterizedTest
    @Order(29)
    @ValueSource(strings = {"A1", "B2", "C3", "D4", "E5"})
    @DisplayName("TC029: Multiple spaces should exist")
    void testMultipleSpaces_ShouldExist(String spaceId) {
        Document result = controller.getSpaceDetails(spaceId);
        assertNotNull(result, "THIS WILL FAIL - space " + spaceId + " doesn't exist");
        assertEquals(spaceId, result.getString("spaceId"));
    }

    @Test
    @Order(30)
    @DisplayName("TC030: Always fails assertion")
    void testAlwaysFails() {
        Document result = controller.getFirstDocument();
        assertNotNull(result, "THIS ALWAYS FAILS - no DB");
        assertEquals("Parking Complex", result.getString("name"));
    }

    @Test
    @Order(90)
    @DisplayName("TC090: Method doesn't throw on invalid input")
    void testNoExceptions_InvalidInput() {
        assertDoesNotThrow(() -> {
            controller.getSpaceDetails("!@#$%^&*()");
            controller.updateSpaceOccupation("!@#$", true);
            controller.freeParkingSpace("!@#$");
        });
    }

    @Test
    @Order(91)
    @DisplayName("TC091: Empty string handling consistency")
    void testEmptyStringHandling() {
        Document result1 = controller.getSpaceDetails("");
        Document result2 = controller.getSpaceDetails("   ");

        assertNull(result1);
        assertNull(result2);
    }

    @Test
    @Order(92)
    @DisplayName("TC092: Whitespace-only IDs")
    void testWhitespaceOnlyIds() {
        boolean updateResult = controller.updateSpaceOccupation("   ", true);
        boolean freeResult = controller.freeParkingSpace("  \t\n  ");

        assertFalse(updateResult);
        assertFalse(freeResult);
    }

    @Test
    @Order(93)
    @DisplayName("TC093: Very long space ID")
    void testVeryLongSpaceId() {
        String longId = "X".repeat(1000);
        Document result = controller.getSpaceDetails(longId);
        assertNull(result);
    }

    @Test
    @Order(94)
    @DisplayName("TC094: Null handling in all methods")
    void testNullHandling() {
        assertDoesNotThrow(() -> {
            controller.getSpaceDetails(null);
            controller.updateSpaceOccupation(null, true);
            controller.freeParkingSpace(null);
            controller.getAvailableSpaces();
            controller.getAvailableSpacesDetails();
        });
    }

    @Test
    @Order(95)
    @DisplayName("TC095: Boolean parameter edge cases")
    void testBooleanParameters() {
        boolean result1 = controller.updateSpaceOccupation("TEST", true);
        boolean result2 = controller.updateSpaceOccupation("TEST", false);

        assertFalse(result1);
        assertFalse(result2);
    }

    @Test
    @Order(96)
    @DisplayName("TC096: Multiple controller instances")
    void testMultipleInstances() {
        ParkingSpaceController c1 = new ParkingSpaceController();
        ParkingSpaceController c2 = new ParkingSpaceController();

        Document r1 = c1.getFirstDocument();
        Document r2 = c2.getFirstDocument();

        assertEquals(r1, r2);
    }

    @Test
    @Order(97)
    @DisplayName("TC097: Special characters in space ID")
    void testSpecialCharactersInId() {
        String[] specialIds = {"A-1", "B_2", "C.3", "D 4", "E@5"};

        for (String id : specialIds) {
            Document result = controller.getSpaceDetails(id);
            assertNull(result);
        }
    }

    @Test
    @Order(98)
    @DisplayName("TC098: Method return types")
    void testReturnTypes() {
        assertInstanceOf(Document.class, controller.getFirstDocument());
        assertInstanceOf(List.class, controller.getAvailableSpaces());
        assertInstanceOf(List.class, controller.getAvailableSpacesDetails());
        assertInstanceOf(Boolean.class, controller.updateSpaceOccupation("test", true));
        assertInstanceOf(Boolean.class, controller.freeParkingSpace("test"));
    }

    @Test
    @Order(99)
    @DisplayName("TC099: Integration test placeholder")
    void testIntegrationPlaceholder() {
        assertDoesNotThrow(() -> {
            new ParkingSpaceController();
        });
    }

    @Test
    @Order(100)
    @DisplayName("TC100: Final test - basic pass")
    void testFinal_BasicPass() {
        ParkingSpaceController c = new ParkingSpaceController();
        assertNotNull(c);

        List<String> spaces = c.getAvailableSpaces();
        assertNotNull(spaces);
    }
}
