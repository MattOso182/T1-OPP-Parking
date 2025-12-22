package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class ResidentDataTest {
    
    private ResidentData residentData;

    @BeforeEach
    public void setUp() {
        residentData = new ResidentData();
    }

    @Test
    public void shouldReturnResidentsList() {
        // Act
        List<Resident> residents = residentData.getResidents();
        
        assertNull(residents, "La lista inicial de residentes debe ser nula o vacía");
    }

    @Test
    public void shouldUpdateResidentsList() {
        List<Resident> newResidents = new ArrayList<>();
        newResidents.add(new Resident()); 
        
        residentData.setResidents(newResidents);
        assertEquals(newResidents, residentData.getResidents(), "La lista de residentes no se actualizó correctamente");
    }
}