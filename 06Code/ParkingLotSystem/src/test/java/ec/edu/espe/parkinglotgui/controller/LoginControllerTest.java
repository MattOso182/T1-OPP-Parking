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

/*
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("LoginController Tests")
public class LoginControllerTest {

    private final LoginController controller = new LoginController();

    @Test
    @Order(1)
    @DisplayName("TC001: Guardia correct credentials")
    void testGuardiaCorrect() {
        assertTrue(controller.validateCredentials("admin", "123", "Guardia de seguridad"));
    }
    
    @Test
    @Order(2)
    @DisplayName("TC002: Guardia wrong password")
    void testGuardiaWrongPassword() {
        assertFalse(controller.validateCredentials("admin", "wrong", "Guardia de seguridad"));
    }
    
    @Test
    @Order(3)
    @DisplayName("TC003: Guardia wrong username")
    void testGuardiaWrongUsername() {
        assertFalse(controller.validateCredentials("wrong", "123", "Guardia de seguridad"));
    }
    
    @Test
    @Order(4)
    @DisplayName("TC004: Invalid user type")
    void testInvalidUserType() {
        assertFalse(controller.validateCredentials("admin", "123", "Invalid"));
    }
    
    @Test
    @Order(5)
    @DisplayName("TC005: Null user type")
    void testNullUserType() {
        assertFalse(controller.validateCredentials("admin", "123", null));
    }
    
    @ParameterizedTest
    @Order(6)
    @NullAndEmptySource
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    @DisplayName("TC006: Empty/null username")
    void testEmptyUsername(String username) {
        assertFalse(controller.validateCredentials(username, "123", "Guardia de seguridad"));
    }
    
    @ParameterizedTest
    @Order(7)
    @NullAndEmptySource
    @ValueSource(strings = {"", "  ", "\t", "\n"})
    @DisplayName("TC007: Empty/null password")
    void testEmptyPassword(String password) {
        assertFalse(controller.validateCredentials("admin", password, "Guardia de seguridad"));
    }
    
    @Test
    @Order(8)
    @DisplayName("TC008: Special characters")
    void testSpecialCharacters() {
        assertFalse(controller.validateCredentials("admin!", "@#$%", "Guardia de seguridad"));
    }
    
    @Test
    @Order(9)
    @DisplayName("TC009: Resident without DB returns false")
    void testResidentWithoutDB() {
        assertFalse(controller.validateCredentials("any", "any", "Residente"));
    }
    
    @Test
    @Order(10)
    @DisplayName("TC010: Multiple calls consistency")
    void testMultipleCalls() {
        boolean first = controller.validateCredentials("admin", "123", "Guardia de seguridad");
        boolean second = controller.validateCredentials("admin", "123", "Guardia de seguridad");
        assertEquals(first, second);
    }
    
    @Test
    @Order(11)
    @DisplayName("TC011: Different cases for Guardia")
    void testCaseSensitiveGuardia() {
        assertFalse(controller.validateCredentials("ADMIN", "123", "Guardia de seguridad"));
        assertFalse(controller.validateCredentials("Admin", "123", "Guardia de seguridad"));
        assertFalse(controller.validateCredentials("admin", "123", "guardia de seguridad"));
        assertFalse(controller.validateCredentials("admin", "123", "GUARDIA DE SEGURIDAD"));
    }
    
    @Test
    @Order(12)
    @DisplayName("TC012: Very short credentials")
    void testShortCredentials() {
        assertFalse(controller.validateCredentials("a", "1", "Guardia de seguridad"));
    }
    
    @Test
    @Order(13)
    @DisplayName("TC013: Method doesn't throw")
    void testNoExceptions() {
        assertDoesNotThrow(() -> {
            controller.validateCredentials("admin", "123", "Guardia de seguridad");
        });
    }
    
    @Test
    @Order(14)
    @DisplayName("TC014: All null parameters")
    void testAllNull() {
        assertFalse(controller.validateCredentials(null, null, null));
    }
    
    @Test
    @Order(15)
    @DisplayName("TC015: Wrong assertion")
    void testFail_WrongAssertion() {
        boolean result = controller.validateCredentials("admin", "123", "Guardia de seguridad");
        assertFalse(result);
    }
    
    @Test
    @Order(100)
    @DisplayName("TC100: Basic pass")
    void testFinal() {
        assertTrue(controller.validateCredentials("admin", "123", "Guardia de seguridad"));
    }
}