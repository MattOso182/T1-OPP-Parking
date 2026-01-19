package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
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
public class VehicleExitControllerTest {

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockCollection;

    @Mock
    private FindIterable<Document> mockIterable;

    @Mock
    private UpdateResult mockUpdateResult;

    private VehicleExitController controller;

    @BeforeEach
    void setUp() {
        try (MockedStatic<MongoDBConnection> mocked = mockStatic(MongoDBConnection.class)) {
            mocked.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);
            when(mockDatabase.getCollection("Entrances")).thenReturn(mockCollection);
            controller = new VehicleExitController();
        }
    }

    @Test
    @Order(1)
    @DisplayName("TC001: invalid license plate format")
    void testIsVehicleParkedInvalidPlate() {
        boolean result = controller.isVehicleParked("ABC1234");
        assertFalse(result);
    }

    @Test
    @Order(2)
    @DisplayName("TC002: vehicle is parked")
    void testIsVehicleParkedTrue() {
        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(new Document());

        boolean result = controller.isVehicleParked("ABC-1234");
        assertTrue(result);
    }

    @Test
    @Order(3)
    @DisplayName("TC003: register exit with no active entry")
    void testRegisterExitNoActiveEntry() {
        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        boolean result = controller.registerExit("ABC-1234");
        assertFalse(result);
    }

    @Test
    @Order(4)
    @DisplayName("TC004: register exit success")
    void testRegisterExitSuccess() {
        Document activeEntry = new Document("licensePlate", "ABC-1234")
                .append("status", "PARKED")
                .append("spaceId", "P-01");

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(activeEntry);
        when(mockCollection.updateOne(any(Bson.class), any(Bson.class)))
                .thenReturn(mockUpdateResult);
        when(mockUpdateResult.getMatchedCount()).thenReturn(1L);

        boolean result = controller.registerExit("ABC-1234");
        assertTrue(result);
    }

    @Test
    @Order(5)
    @DisplayName("TC005: get parked vehicles empty")
    void testGetParkedVehiclesEmpty() {
        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        doAnswer(invocation -> {
            List<Document> list = invocation.getArgument(0);
            return list;
        }).when(mockIterable).into(any(List.class));

        List<Document> result = controller.getParkedVehicles();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}