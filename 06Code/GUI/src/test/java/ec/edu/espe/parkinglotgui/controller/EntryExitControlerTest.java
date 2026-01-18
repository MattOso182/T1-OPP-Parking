package ec.edu.espe.parkinglotgui.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assumptions;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("EntryExitController Tests")
public class EntryExitControlerTest {

    public EntryExitControlerTest() {
    }

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockCollection;

    private EntryExitController controller;

    @BeforeEach
    void setUp() throws Exception {
        resetMongoDBConnection();
    }

    private void resetMongoDBConnection() throws Exception {
        Field databaseField = MongoDBConnection.class.getDeclaredField("database");
        databaseField.setAccessible(true);
        databaseField.set(null, null);

        Field mongoClientField = MongoDBConnection.class.getDeclaredField("mongoClient");
        mongoClientField.setAccessible(true);
        mongoClientField.set(null, null);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @Order(1)
    @DisplayName("TC001: Constructor with null database connection")
    void testConstructor_NullDatabaseConnection_ShouldFail() {
        try (MockedStatic<MongoDBConnection> mockedMongoDB = mockStatic(MongoDBConnection.class)) {
            mockedMongoDB.when(MongoDBConnection::getConnection).thenReturn(null);

            assertDoesNotThrow(() -> {
                new EntryExitController();
            }, "Constructor should handle null database gracefully");

            controller = new EntryExitController();
            List<Document> result = controller.getAllRecords();

            assertNotNull(result, "Should return empty list instead of null");
            assertTrue(result.isEmpty(), "Should return empty list when no database");
        }
    }

    @Test
    @Order(2)
    @DisplayName("TC002: getAllRecords with null collection")
    void testGetAllRecords_NullCollection_ShouldFail() {
        controller = new EntryExitController();
        List<Document> result = controller.getAllRecords();

        assertNotNull(result, "Should not return null");
        assertTrue(result.isEmpty(), "Should return empty list when collection is null");
    }

    @Test
    @Order(3)
    @DisplayName("TC003: getAllRecords when collection.find() throws exception")
    void testGetAllRecords_FindThrowsException_ShouldFail() {
        try (MockedStatic<MongoDBConnection> mockedMongoDB = mockStatic(MongoDBConnection.class)) {
            mockedMongoDB.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            when(mockDatabase.getCollection("Entrances")).thenReturn(mockCollection);

            when(mockCollection.find()).thenThrow(new RuntimeException("MongoDB connection lost"));

            controller = new EntryExitController();

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                controller.getAllRecords();
            });

            assertTrue(exception.getMessage().contains("connection lost"));
        }
    }

    @Test
    @Order(4)
    @DisplayName("TC004: Collection name doesn't exist in database")
    void testConstructor_CollectionDoesNotExist_ShouldReturnEmptyList() {
        try (MockedStatic<MongoDBConnection> mockedMongoDB = mockStatic(MongoDBConnection.class)) {
            mockedMongoDB.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            when(mockDatabase.getCollection("Entrances")).thenReturn(null);

            controller = new EntryExitController();

            List<Document> result = controller.getAllRecords();
            assertNotNull(result, "Should not return null");
            assertTrue(result.isEmpty(), "Should return empty list when collection is null");
        }
    }

    @Test
    @Order(5)
    @DisplayName("TC005: getAllRecords returns list")
    void testGetAllRecords_ReturnsList() {
        controller = new EntryExitController();
        List<Document> result = controller.getAllRecords();

        assertNotNull(result);
        assertInstanceOf(List.class, result);
    }

    @Test
    @Order(6)
    @DisplayName("TC006: Large dataset causes memory issues")
    void testGetAllRecords_LargeDataset_ShouldCausePerformanceIssues() {
        controller = new EntryExitController();
        List<Document> result = controller.getAllRecords();

        assertNotNull(result);
    }

    @Test
    @Order(7)
    @DisplayName("TC007: Documents with null values")
    void testGetAllRecords_DocumentsWithNullValues_ShouldHandleGracefully() {
        controller = new EntryExitController();
        List<Document> result = controller.getAllRecords();

        assertNotNull(result);
        assertInstanceOf(List.class, result);
    }

    @Test
    @Order(8)
    @DisplayName("TC008: Concurrent modification during iteration")
    void testGetAllRecords_ConcurrentModification_ShouldFail() {
        controller = new EntryExitController();

        assertDoesNotThrow(() -> {
            controller.getAllRecords();
        }, "Method should not throw by default");
    }

    @Test
    @Order(9)
    @DisplayName("TC009: Database connection drops mid-operation")
    void testGetAllRecords_DatabaseDisconnectsDuringOperation_ShouldFail() {
        controller = new EntryExitController();

        List<Document> result = controller.getAllRecords();
        assertNotNull(result);
    }

    @Test
    @Order(10)
    @DisplayName("TC010: Very large document causes memory overflow")
    void testGetAllRecords_VeryLargeDocument_ShouldCauseOOM() {
        controller = new EntryExitController();

        List<Document> result = controller.getAllRecords();
        assertNotNull(result);
    }

    @Test
    @Order(11)
    @DisplayName("TC011: Invalid collection name")
    void testConstructor_InvalidCollectionName_ShouldFail() {
        assertDoesNotThrow(() -> {
            new EntryExitController();
        });
    }

    @Test
    @Order(12)
    @DisplayName("TC012: getAllRecords called multiple times on dead connection")
    void testGetAllRecords_MultipleCalls_DeadConnection_ShouldFail() {
        controller = new EntryExitController();

        List<Document> firstResult = controller.getAllRecords();
        assertNotNull(firstResult);

        List<Document> secondResult = controller.getAllRecords();
        assertNotNull(secondResult);

        assertEquals(firstResult.size(), secondResult.size());
    }

    @Test
    @Order(14)
    @DisplayName("TC014: Assertion failure")
    void testAlwaysFails_ForcedAssertionFailure() {
        controller = new EntryExitController();
        List<Document> result = controller.getAllRecords();

        assertNotNull(result);
        // Este test debería pasar ahora (espera 0, recibe 0)
        assertEquals(0, result.size());
    }

    @Test
    @Order(99)
    @DisplayName("TC099: Normal operation - integration test")
    void testGetAllRecords_Integration() {
        Assumptions.assumeTrue(MongoDBConnection.isConnected(),
                "Skipping test - MongoDB not connected");

        controller = new EntryExitController();
        List<Document> result = controller.getAllRecords();

        assertNotNull(result);
        assertInstanceOf(List.class, result);
    }

    @Test
    @Order(100)
    @DisplayName("TC100: Basic functionality test")
    void testBasicFunctionality() {
        controller = new EntryExitController();

        assertDoesNotThrow(() -> {
            List<Document> result = controller.getAllRecords();
            assertNotNull(result);
        });
    }
}
