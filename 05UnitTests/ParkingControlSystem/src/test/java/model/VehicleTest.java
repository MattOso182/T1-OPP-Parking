package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class VehicleTest {
    
    private Vehicles vehicle;
    private final String INITIAL_PLATE = "ABC-1234";
    private final String INITIAL_COLOR = "Rojo";
    private final String INITIAL_MODEL = "Sedan";
    private final String INITIAL_OWNER = "ID-5678";

    @BeforeEach
    public void setUp() {
        vehicle = new Vehicles(INITIAL_PLATE, INITIAL_COLOR, INITIAL_MODEL, INITIAL_OWNER);
    }

    @Test
    public void shouldRegisterVehicle() {
        boolean result = vehicle.registerVehicle();
        assertTrue(result, "El vehículo debería registrarse correctamente");
    }

    @Test
    public void shouldUpdateOwner() {
        String newOwnerId = "ID-9999";
        vehicle.updateOwner(newOwnerId);
        assertEquals(newOwnerId, vehicle.getOwnerId(), "El ID del dueño debería haberse actualizado");
    }

    @Test
    public void shouldAssignSpot() {
        String spotId = "A-101";
        vehicle.assignSpot(spotId);
        assertTrue(vehicle.isParked(), "El vehículo debería marcarse como estacionado");
    }

    @Test
    public void shouldReleaseSpot() {
        vehicle.assignSpot("A-101");
        vehicle.releaseSpot();
        assertFalse(vehicle.isParked(), "El vehículo ya no debería estar estacionado");
    }

    @Test
    public void shouldValidatePlate() {
        boolean result = vehicle.validatePlate();
        assertTrue(result, "La placa inicial debería ser válida");
    }

    @Test
    public void shouldGetPlate() {
        assertEquals(INITIAL_PLATE, vehicle.getPlate());
    }

    @Test
    public void shouldGetColor() {
        assertEquals(INITIAL_COLOR, vehicle.getColor());
    }

    @Test
    public void shouldGetModel() {
        assertEquals(INITIAL_MODEL, vehicle.getModel());
    }

    @Test
    public void shouldCheckIfIsParked() {
        assertFalse(vehicle.isParked(), "El vehículo no debería estar estacionado al inicio");
    }

    @Test
    public void shouldGetOwnerId() {
        assertEquals(INITIAL_OWNER, vehicle.getOwnerId());
    }

    @Test
    public void shouldReturnVehicleInfo() {
        String info = vehicle.getVehicleInfo();
        assertNotNull(info);
        assertTrue(info.contains(INITIAL_PLATE));
    }
}