package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.model.Vehicle;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.util.ArrayList;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
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
public class VehicleEntryControllerTest {

    @Mock
    private MongoDatabase mockDatabase;

    @Mock
    private MongoCollection<Document> mockEntrancesCollection;

    @Mock
    private MongoCollection<Document> mockVehiclesCollection;

    @Mock
    private MongoCursor<Document> mockCursor;

    private VehicleEntryController controller;

    private MockedStatic<MongoDBConnection> mockedStatic;

    @BeforeEach
    void setUp() {
        mockedStatic = mockStatic(MongoDBConnection.class);
        mockedStatic.when(MongoDBConnection::getConnection).thenReturn(mockDatabase);

        lenient().when(mockDatabase.getCollection("Entrances")).thenReturn(mockEntrancesCollection);
        lenient().when(mockDatabase.getCollection("Vehicles")).thenReturn(mockVehiclesCollection);

        controller = new VehicleEntryController();
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    @Order(1)
    @DisplayName("TC001: register entry invalid plate")
    void testRegisterEntryInvalidPlate() {
        boolean result = controller.registerEntry("ABC1234", "P-01");
        assertFalse(result);
    }

   

    @Test
    @Order(3)
    @DisplayName("TC003: get all vehicles empty")
    void testGetAllVehiclesEmpty() {
        FindIterable<Document> mockFind = mock(FindIterable.class);

        when(mockVehiclesCollection.find()).thenReturn(mockFind);
        when(mockFind.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false);

        ArrayList<Vehicle> vehicles = controller.getAllVehicles();

        assertNotNull(vehicles);
        assertTrue(vehicles.isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("TC004: get all vehicles with one record")
    void testGetAllVehiclesOne() {
        Document doc = new Document("ownerId", "RES-001")
                .append("ownerName", "Juan")
                .append("plate", "ABC-1234")
                .append("color", "Red")
                .append("model", "Sedan")
                .append("parked", true);

        FindIterable<Document> mockFind = mock(FindIterable.class);

        when(mockVehiclesCollection.find()).thenReturn(mockFind);
        when(mockFind.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, false);
        when(mockCursor.next()).thenReturn(doc);

        ArrayList<Vehicle> vehicles = controller.getAllVehicles();

        assertEquals(1, vehicles.size());
        assertEquals("ABC-1234", vehicles.get(0).getPlate());
    }
}