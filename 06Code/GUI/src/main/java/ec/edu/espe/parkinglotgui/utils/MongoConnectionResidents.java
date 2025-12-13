package ec.edu.espe.parkinglotgui.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class MongoConnectionResidents {
    private static final String URI = "mongodb+srv://Mateo:Mateo21032006@cluster0.t4qmrfv.mongodb.net/?appName=Cluster0";

    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
        }
        return mongoClient.getDatabase("ParkingLotDB"); 
    }
}
