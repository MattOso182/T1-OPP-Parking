package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import ec.edu.espe.parkinglotgui.model.Visitor;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class VisitorControllerTest {

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockVisitorCollection;

    @Mock
    private MongoCollection<Document> mockResidentCollection;

    @Mock
    private FindIterable<Document> mockIterable;

    @Mock
    private MongoCursor<Document> mockCursor;

    @Mock
    private UpdateResult mockUpdateResult;

    @Mock
    private DeleteResult mockDeleteResult;

    private VisitorController controller;

    @BeforeEach
    void setUp() {
        try (MockedStatic<MongoDBConnection> mocked = mockStatic(MongoDBConnection.class)) {
            mocked.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            when(mockDatabase.getCollection("Visitors")).thenReturn(mockVisitorCollection);
            when(mockDatabase.getCollection("Residents")).thenReturn(mockResidentCollection);
            controller = new VisitorController();
        }
    }

    @Test
    @Order(1)
    @DisplayName("TC001: save visitor invalid ID")
    void testSaveVisitorInvalidId() {
        Visitor v = new Visitor();
        v.setVisitorID("ABC");
        v.setVehiclePlate("ABC-1234");
        v.setResidentID("RES-001");

        boolean result = controller.saveVisitor(v);
        assertFalse(result);
    }

    @Test
    @Order(2)
    @DisplayName("TC002: save visitor success")
    void testSaveVisitorSuccess() {
        Visitor v = new Visitor();
        v.setVisitorID("1");
        v.setNameVisitor("Carlos");
        v.setVehiclePlate("ABC-1234");
        v.setResidentID("RES-001");
        v.setHasPass(true);

        when(mockVisitorCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        when(mockResidentCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(new Document());

        boolean result = controller.saveVisitor(v);
        assertTrue(result);
        verify(mockVisitorCollection, times(1)).insertOne(any(Document.class));
    }

    @Test
    @Order(3)
    @DisplayName("TC003: update visitor success")
    void testUpdateVisitorSuccess() {
        Visitor v = new Visitor();
        v.setVisitorID("1");
        v.setNameVisitor("Carlos");
        v.setVehiclePlate("ABC-1234");
        v.setResidentID("RES-001");
        v.setHasPass(false);

        when(mockVisitorCollection.updateOne(any(Bson.class), any(Document.class)))
                .thenReturn(mockUpdateResult);
        when(mockUpdateResult.getModifiedCount()).thenReturn(1L);

        boolean result = controller.updateVisitor(v);
        assertTrue(result);
    }

    @Test
    @Order(4)
    @DisplayName("TC004: delete visitor success")
    void testDeleteVisitorSuccess() {
        when(mockVisitorCollection.deleteOne(any(Bson.class)))
                .thenReturn(mockDeleteResult);
        when(mockDeleteResult.getDeletedCount()).thenReturn(1L);

        boolean result = controller.deleteVisitor("1");
        assertTrue(result);
    }

    @Test
    @Order(5)
    @DisplayName("TC005: get all visitors empty")
    void testGetAllVisitorsEmpty() {
        when(mockVisitorCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false);

        List<Visitor> visitors = controller.getAllVisitors();
        assertNotNull(visitors);
        assertTrue(visitors.isEmpty());
    }
}