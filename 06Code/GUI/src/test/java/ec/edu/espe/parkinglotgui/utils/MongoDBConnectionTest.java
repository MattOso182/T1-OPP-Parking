package ec.edu.espe.parkinglotgui.utils;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("MongoDBConnection Tests")

public class MongoDBConnectionTest {

    public MongoDBConnectionTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        MongoDBConnection.closeConnection();
    }

    @AfterAll
    public static void tearDownClass() {
        MongoDBConnection.closeConnection();
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Order(1)
    @DisplayName("TC001:Same Instance")
    void testConnection() {
        // Arrange
        MongoDatabase db1 = MongoDBConnection.getConnection();
        MongoDatabase db2 = MongoDBConnection.getConnection();

        // Assert
        assertNotNull(db1, "First connection should not be null");
        assertNotNull(db2, "Second connection should not be null");
        assertSame(db1, db2, "Should return the same connection instance (Singleton pattern)");
    }

    @Test
    @Order(2)
    @DisplayName("TC002: Verify Successful Connection")
    void testConnectionSuccess() {
        // Act
        boolean isConnected = MongoDBConnection.isConnected();

        // Assert
        assertTrue(isConnected, "Should be connected to MongoDB");
    }

    @Test
    @Order(3)
    @DisplayName("TC003: Get Correct Database")
    void testGetCorrectDatabase() {
        // Act
        MongoDatabase db = MongoDBConnection.getConnection();

        // Assert
        assertNotNull(db, "Connection should not be null");
        assertEquals("ParkingLotDB", db.getName(),
                "Should connect to 'ParkingLotDB' database");
    }

    @Test
    @Order(4)
    @DisplayName("TC004: Close Connection")
    void testCloseConnection() {
        // Arrange 
        MongoDatabase dbBefore = MongoDBConnection.getConnection();
        assertNotNull(dbBefore, "There should be a connection before closing");

        // Act
        MongoDBConnection.closeConnection();

        // Assert
        MongoDatabase dbAfter = MongoDBConnection.getConnection();
        assertNotNull(dbAfter, "Should be able to reconnect after closing");

    }

    @Test
    @Order(5)
    @DisplayName("TC005: Reconnection After Close")
    void testReconnectionAfterClose() {
        // Arrange
        MongoDBConnection.closeConnection();

        // Act & Assert
        assertDoesNotThrow(() -> {
            MongoDatabase db = MongoDBConnection.getConnection();
            assertNotNull(db, "Should reconnect successfully");
        }, "Should not throw exception when reconnecting");
    }

    @Test
    @Order(6)
    @DisplayName("TC006: isConnected Method Without Previous Connection")
    void testIsConnectedWithoutPreviousConnection() {
        // Arrange
        MongoDBConnection.closeConnection();

        // Act
        boolean connected = MongoDBConnection.isConnected();

        // Assert
        assertTrue(connected, "isConnected() should establish connection if none exists");

        MongoDatabase db = MongoDBConnection.getConnection();
        assertNotNull(db, "There should be a connection after isConnected()");
    }
}
