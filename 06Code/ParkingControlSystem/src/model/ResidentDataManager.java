package model;

/**
 *
 * @author Mateo Aymacaña @ESPE T.A.P(The Art of Programming)
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResidentDataManager {
    private static final String RESIDENTS_JSON_PATH = "residents_data.json";
    private Gson gson;
    
    public ResidentDataManager() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    }
    
    public ResidentData loadResidentsData() {
        try (FileReader reader = new FileReader(RESIDENTS_JSON_PATH)) {
            ResidentData residentData = gson.fromJson(reader, ResidentData.class);
            System.out.println("Successfully loaded residents data from JSON");
            return residentData;
        } catch (IOException e) {
            System.out.println("Error loading residents JSON: " + e.getMessage());
            return new ResidentData(); 
        }
    }
    
    public void saveResidentsData(List<Resident> residents) {
        try {
            ResidentData residentData = new ResidentData(residents);
            try (FileWriter writer = new FileWriter(RESIDENTS_JSON_PATH)) {
                gson.toJson(residentData, writer);
                System.out.println("Residents data saved successfully to JSON");
            }
        } catch (IOException e) {
            System.out.println("Error saving residents JSON: " + e.getMessage());
        }
    }
}
