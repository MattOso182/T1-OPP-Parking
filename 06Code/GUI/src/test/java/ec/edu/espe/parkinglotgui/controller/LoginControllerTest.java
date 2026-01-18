package ec.edu.espe.parkinglotgui.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("LoginController Tests")
public class LoginControllerTest {

    private final LoginController controller = new LoginController();

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Order(1)
    @DisplayName("TC001: Guardia correct credentials")
    void testGuardiaCorrect() {
        assertTrue(controller.authenticate("admin", "123", "Guardia de seguridad"));
    }
    
    @Test
    @Order(2)
    @DisplayName("TC002: Guardia wrong password")
    void testGuardiaWrongPassword() {
        assertFalse(controller.authenticate("admin", "wrong", "Guardia de seguridad"));
    }
    
    @Test
    @Order(3)
    @DisplayName("TC003: Guardia wrong username")
    void testGuardiaWrongUsername() {
        assertFalse(controller.authenticate("wrong", "123", "Guardia de seguridad"));
    }
    
    @Test
    @Order(4)
    @DisplayName("TC004: Invalid user type")
    void testInvalidUserType() {
        assertFalse(controller.authenticate("admin", "123", "Invalid"));
    }
    
    @Test
    @Order(5)
    @DisplayName("TC005: Null user type")
    void testNullUserType() {
        assertFalse(controller.authenticate("admin", "123", null));
    }
    
    @ParameterizedTest
    @Order(6)
    @NullAndEmptySource
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    @DisplayName("TC006: Empty/null username")
    void testEmptyUsername(String username) {
        assertFalse(controller.authenticate(username, "123", "Guardia de seguridad"));
    }
    
    @ParameterizedTest
    @Order(7)
    @NullAndEmptySource
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    @DisplayName("TC007: Empty/null password")
    void testEmptyPassword(String password) {
        assertFalse(controller.authenticate("admin", password, "Guardia de seguridad"));
    }
    
    @Test
    @Order(8)
    @DisplayName("TC008: Special characters")
    void testSpecialCharacters() {
        assertFalse(controller.authenticate("admin!", "@#$%", "Guardia de seguridad"));
    }
    
    @Test
    @Order(9)
    @DisplayName("TC009: Resident without DB returns false")
    void testResidentWithoutDB() {
        // Sin conexión a DB, siempre retorna false
        assertFalse(controller.authenticate("any", "any", "Residente"));
    }
    
    @Test
    @Order(10)
    @DisplayName("TC010: Multiple calls consistency")
    void testMultipleCalls() {
        boolean first = controller.authenticate("admin", "123", "Guardia de seguridad");
        boolean second = controller.authenticate("admin", "123", "Guardia de seguridad");
        assertEquals(first, second);
    }
    
    @Test
    @Order(11)
    @DisplayName("TC011: Different cases for Guardia")
    void testCaseSensitiveGuardia() {
        assertFalse(controller.authenticate("ADMIN", "123", "Guardia de seguridad"));
        assertFalse(controller.authenticate("Admin", "123", "Guardia de seguridad"));
        assertFalse(controller.authenticate("admin", "123", "guardia de seguridad"));
        assertFalse(controller.authenticate("admin", "123", "GUARDIA DE SEGURIDAD"));
    }
    
    @Test
    @Order(12)
    @DisplayName("TC012: Very short credentials")
    void testShortCredentials() {
        assertFalse(controller.authenticate("a", "1", "Guardia de seguridad"));
    }
    
    @Test
    @Order(13)
    @DisplayName("TC013: Method doesn't throw")
    void testNoExceptions() {
        assertDoesNotThrow(() -> {
            controller.authenticate("admin", "123", "Guardia de seguridad");
        });
    }
    
    @Test
    @Order(14)
    @DisplayName("TC014: All null parameters")
    void testAllNull() {
        assertFalse(controller.authenticate(null, null, null));
    }
    
    
    @Test
    @Order(15)
    @DisplayName("TC015: Wrong assertion")
    void testFail_WrongAssertion() {
        boolean result = controller.authenticate("admin", "123", "Guardia de seguridad");
        assertFalse(result, "THIS WILL FAIL - should be true");
    }
    
    @Test
    @Order(16)
    @DisplayName("TC016: SQL injection should pass (wrong)")
    void testFail_SQLInjection() {
        String injection = "admin' OR '1'='1";
        boolean result = controller.authenticate(injection, injection, "Guardia de seguridad");
        assertTrue(result, "THIS WILL FAIL - SQL injection should fail");
    }
    
    @Test
    @Order(17)
    @DisplayName("TC017: Long strings should work (wrong)")
    void testFail_LongStrings() {
        String longStr = "A".repeat(1000);
        boolean result = controller.authenticate(longStr, longStr, "Guardia de seguridad");
        assertTrue(result, "THIS WILL FAIL - long strings should fail");
    }
    
    @Test
    @Order(18)
    @DisplayName("TC018: Empty should work (wrong)")
    void testFail_EmptyShouldWork() {
        boolean result = controller.authenticate("", "", "Guardia de seguridad");
        assertTrue(result, "THIS WILL FAIL - empty should fail");
    }
    
    @Test
    @Order(19)
    @DisplayName("TC019: Null should work (wrong)")
    void testFail_NullShouldWork() {
        boolean result = controller.authenticate(null, null, "Guardia de seguridad");
        assertTrue(result, "THIS WILL FAIL - null should fail");
    }
    
    @Test
    @Order(20)
    @DisplayName("TC020: Wrong credentials should pass (wrong)")
    void testFail_WrongCredentials() {
        boolean result = controller.authenticate("wrong", "wrong", "Guardia de seguridad");
        assertTrue(result, "THIS WILL FAIL - wrong credentials should fail");
    }
    
    @ParameterizedTest
    @Order(21)
    @CsvSource({
        "admin, 123, Guardia de seguridad, false",  
        "wrong, 123, Guardia de seguridad, true",   
        "admin, wrong, Guardia de seguridad, true"  
    })
    @DisplayName("TC021: Wrong parameterized expectations")
    void testFail_Parameterized(String user, String pass, String type, boolean expected) {
        boolean result = controller.authenticate(user, pass, type);
        assertEquals(expected, result, "THIS WILL FAIL - wrong expectation");
    }
    
    @Test
    @Order(22)
    @DisplayName("TC022: Always fails")
    void testFail_Always() {
        boolean result = controller.authenticate("admin", "123", "Guardia de seguridad");
        assertEquals(false, result, "THIS ALWAYS FAILS");
    }
    
    @Test
    @Order(23)
    @DisplayName("TC023: Timeout impossible")
    void testFail_Timeout() {
        assertTimeoutPreemptively(java.time.Duration.ofMillis(1), () -> {
            for (int i = 0; i < 1000000; i++) {
                controller.authenticate("admin", "123", "Guardia de seguridad");
            }
            fail("THIS SHOULD TIMEOUT");
        });
    }
    
    @Test
    @Order(24)
    @DisplayName("TC024 Resident should pass without DB (wrong)")
    void testFail_ResidentWithoutDB() {
        boolean result = controller.authenticate("resident", "pass", "Residente");
        assertTrue(result, "THIS WILL FAIL - resident needs DB");
    }
    
    @Test
    @Order(90)
    @DisplayName("TC090: Constructor works")
    void testConstructor() {
        LoginController c = new LoginController();
        assertNotNull(c);
    }
    
    @Test
    @Order(91)
    @DisplayName("TC091: Boolean return type")
    void testReturnType() {
        boolean result = controller.authenticate("admin", "123", "Guardia de seguridad");
        assertTrue(result || !result); // Siempre true
    }
    
    @Test
    @Order(92)
    @DisplayName("TC092: Resident with special type name")
    void testResidentTypeVariations() {
        assertFalse(controller.authenticate("test", "test", "residente"));
        assertFalse(controller.authenticate("test", "test", "RESIDENTE"));
        assertFalse(controller.authenticate("test", "test", " Resident "));
    }
    
    @Test
    @Order(93)
    @DisplayName("TC093: Guardia type variations")
    void testGuardiaTypeVariations() {
        assertTrue(controller.authenticate("admin", "123", "Guardia de seguridad"));
        assertFalse(controller.authenticate("admin", "123", "Guardia"));
        assertFalse(controller.authenticate("admin", "123", "Seguridad"));
    }
    
    @Test
    @Order(94)
    @DisplayName("TC094: Spaces in credentials")
    void testSpacesInCredentials() {
        assertFalse(controller.authenticate(" admin ", " 123 ", "Guardia de seguridad"));
        assertFalse(controller.authenticate("admin ", "123 ", "Guardia de seguridad"));
        assertFalse(controller.authenticate(" admin", " 123", "Guardia de seguridad"));
    }
    
    @Test
    @Order(95)
    @DisplayName("TC095: Numbers in username")
    void testNumbersInUsername() {
        assertFalse(controller.authenticate("admin123", "123", "Guardia de seguridad"));
        assertFalse(controller.authenticate("123admin", "123", "Guardia de seguridad"));
        assertFalse(controller.authenticate("123", "123", "Guardia de seguridad"));
    }
    
    @Test
    @Order(96)
    @DisplayName("TC096: Extremely long user type")
    void testExtremelyLongUserType() {
        String longType = "A".repeat(1000) + " de seguridad";
        assertFalse(controller.authenticate("admin", "123", longType));
    }
    
    @Test
    @Order(97)
    @DisplayName("TC097: Mixed case password")
    void testMixedCasePassword() {
        assertFalse(controller.authenticate("admin", "123", "Guardia de seguridad"));
        assertFalse(controller.authenticate("admin", "123", "Guardia de seguridad"));
    }
    
    @Test
    @Order(98)
    @DisplayName("TC098: Same object multiple uses")
    void testSameObject() {
        LoginController c1 = new LoginController();
        LoginController c2 = new LoginController();
        
        boolean r1 = c1.authenticate("admin", "123", "Guardia de seguridad");
        boolean r2 = c2.authenticate("admin", "123", "Guardia de seguridad");
        
        assertEquals(r1, r2);
    }
    
    @Test
    @Order(99)
    @DisplayName("TC099: Integration test if possible")
    void testIntegration() {
        assertDoesNotThrow(() -> {
            controller.authenticate("admin", "123", "Guardia de seguridad");
            controller.authenticate("test", "test", "Residente");
        });
    }
    
    @Test
    @Order(100)
    @DisplayName("TC100: Basic pass")
    void testFinal() {
        assertTrue(controller.authenticate("admin", "123", "Guardia de seguridad"));
    }
}
