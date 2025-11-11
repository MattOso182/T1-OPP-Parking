package ec.edu.espe.parkingLot.model;

/**
 *
 * @author T.A.P(The Art of Programming)
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonDataManager {
     private static final String JSON_FILE_PATH = "parking_data.json";
    private Gson gson;
    
    public JsonDataManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public ParkingStructure loadParkingData() {
        try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
            ParkingStructureWrapper wrapper = gson.fromJson(reader, ParkingStructureWrapper.class);
            return wrapper != null ? wrapper.getParkingComplex() : null;
        } catch (IOException e) {
            System.out.println("Error loading JSON file: " + e.getMessage());
            return null;
        }
    }
    
    public void saveParkingData(List<BuildingBlock> blocks) {
        try {
            ParkingStructure structure = new ParkingStructure(
                "Residential Complex Parking", 
                233, 
                35, 
                blocks
            );
            ParkingStructureWrapper wrapper = new ParkingStructureWrapper(structure);
            
            try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
                gson.toJson(wrapper, writer);
                System.out.println("Parking data saved successfully to JSON");
            }
        } catch (IOException e) {
            System.out.println("Error saving JSON file: " + e.getMessage());
        }
    }
    
    private static class ParkingStructureWrapper {
        private ParkingStructure parkingComplex;
        
        public ParkingStructureWrapper(ParkingStructure parkingComplex) {
            this.parkingComplex = parkingComplex;
        }
        
        public ParkingStructure getParkingComplex() {
            return parkingComplex;
        }
        
        public void setParkingComplex(ParkingStructure parkingComplex) {
            this.parkingComplex = parkingComplex;
        }
    }
}
