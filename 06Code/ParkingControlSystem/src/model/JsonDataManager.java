package model;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonDataManager {

    private static final String PARKING_JSON_FILE_PATH = "parking_data.json";
    private static final String RESIDENTS_JSON_FILE_PATH = "residents_data.json";

    private Gson gson;

    public JsonDataManager() {
    this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    }
   
    public ParkingStructure loadParkingData() {
        try (FileReader reader = new FileReader(PARKING_JSON_FILE_PATH)) {
            ParkingStructureWrapper wrapper = gson.fromJson(reader, ParkingStructureWrapper.class);
            return wrapper != null ? wrapper.getParkingComplex() : null;
        } catch (IOException e) {
            System.out.println("Error loading parking JSON file: " + e.getMessage());
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

            try (FileWriter writer = new FileWriter(PARKING_JSON_FILE_PATH)) {
                gson.toJson(wrapper, writer);
                System.out.println(" Parking data saved successfully to JSON");
            }
        } catch (IOException e) {
            System.out.println("Error saving parking JSON file: " + e.getMessage());
        }
    }

  
    public List<Resident> loadResidentsData() {
        try (FileReader reader = new FileReader(RESIDENTS_JSON_FILE_PATH)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray residentsArray = root.getAsJsonArray("residents");

            if (residentsArray == null) {
                System.out.println("No 'residents' array found in JSON");
                return Collections.emptyList();
            }

            return gson.fromJson(residentsArray, new TypeToken<List<Resident>>(){}.getType());
        } catch (IOException e) {
            System.out.println("Error loading residents JSON file: " + e.getMessage());
            return Collections.emptyList();
        } catch (Exception ex) {
            System.out.println(" Error parsing residents JSON: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

  
    public void saveResidentsData(List<Resident> residents) {
        try {
            JsonObject root = new JsonObject();
            root.add("residents", gson.toJsonTree(residents));

            try (FileWriter writer = new FileWriter(RESIDENTS_JSON_FILE_PATH)) {
                gson.toJson(root, writer);
                System.out.println(" Residents data saved successfully to JSON");
            }
        } catch (IOException e) {
            System.out.println("Error saving residents JSON file: " + e.getMessage());
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