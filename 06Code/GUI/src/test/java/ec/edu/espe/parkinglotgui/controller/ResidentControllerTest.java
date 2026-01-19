package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.util.List;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class ResidentControllerTest {

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockCollection;

    @Mock
    private FindIterable<Document> mockIterable;

    @Mock
    private MongoCursor<Document> mockCursor;

    private ResidentController controller;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private void mockCollectionNames() {
        FindIterable<String> mockNames = mock(FindIterable.class);
        when(mockDatabase.listCollectionNames()).thenReturn(mockNames);
        when(mockNames.into(any(List.class))).thenAnswer(invocation -> {
            List<String> list = invocation.getArgument(0);
            list.add("Residents");
            return list;
        });
    }

    @Test
    @Order(1)
    @DisplayName("TC001: ResidentController instance creation")
    void testResidentControllerCreation() {
        try (MockedStatic<MongoDBConnection> mocked = mockStatic(MongoDBConnection.class)) {
            mocked.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            mockCollectionNames();
            when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);

            controller = new ResidentController();
            assertNotNull(controller);
        }
    }

    @Test
    @Order(2)
    @DisplayName("TC002: searchResident with null ID")
    void testSearchResident_NullId() {
        try (MockedStatic<MongoDBConnection> mocked = mockStatic(MongoDBConnection.class)) {
            mocked.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            mockCollectionNames();
            when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);

            when(mockCollection.find()).thenReturn(mockIterable);
            when(mockIterable.iterator()).thenReturn(mockCursor);
            when(mockCursor.hasNext()).thenReturn(false);

            controller = new ResidentController();
            Resident resident = controller.searchResident(null);
            assertNull(resident);
        }
    }

    @Test
    @Order(3)
    @DisplayName("TC003: searchResident by ID")
    void testSearchResident_ById() {
        try (MockedStatic<MongoDBConnection> mocked = mockStatic(MongoDBConnection.class)) {

            mocked.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            mockCollectionNames();
            when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);

            when(mockCollection.find()).thenReturn(mockIterable);
            when(mockIterable.iterator()).thenReturn(mockCursor);

            when(mockCursor.hasNext()).thenReturn(true, false);
            when(mockCursor.next()).thenReturn(
                    new Document("residentID", "RES-001")
                            .append("name", "Juan")
            );

            controller = new ResidentController();
            Resident resident = controller.searchResident("RES-001");

            assertNotNull(resident);
            assertEquals("RES-001", resident.getResidentID());
        }
    }

    @Test
    @Order(4)
    @DisplayName("TC004: getAllResidents returns empty list")
    void testGetAllResidents_Empty() {
        try (MockedStatic<MongoDBConnection> mocked = mockStatic(MongoDBConnection.class)) {

            mocked.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            mockCollectionNames();
            when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);

            when(mockCollection.find()).thenReturn(mockIterable);
            when(mockIterable.iterator()).thenReturn(mockCursor);
            when(mockCursor.hasNext()).thenReturn(false);

            controller = new ResidentController();
            List<Resident> residents = controller.getAllResidents();

            assertNotNull(residents);
            assertTrue(residents.isEmpty());
        }
    }

    @Test
    @Order(5)
    @DisplayName("TC005: addResident basic test")
    void testAddResident() {
        try (MockedStatic<MongoDBConnection> mocked = mockStatic(MongoDBConnection.class)) {

            mocked.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            mockCollectionNames();
            when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);

            when(mockCollection.find()).thenReturn(mockIterable);
            when(mockIterable.iterator()).thenReturn(mockCursor);
            when(mockCursor.hasNext()).thenReturn(false);

            controller = new ResidentController();
            boolean result = controller.addResident("Ana", "A-101", "0999999999");

            assertTrue(result);
            verify(mockCollection, times(1)).insertOne(any(Document.class));
        }
    }
}