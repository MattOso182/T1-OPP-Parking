package ec.edu.espe.parkinglotgui.utils;

/**
 *
 * @author Emily Calle, T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnectionVisitors { 

    private static MongoDatabase database;
    private static MongoClient mongoClient;
    
    private static final String CONNECTION_URI = "mongodb+srv://Mateo:Mateo21032006@cluster0.t4qmrfv.mongodb.net/ParkingLotDB?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "ParkingLotDB";

    public static MongoDatabase getConnection() {
        if (database == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_URI);
                database = mongoClient.getDatabase(DATABASE_NAME);
                
                database.runCommand(new Document("ping", 1));
                
                
            } catch (Exception e) {
                database = null;
            }
        }
        return database;
    }
    
    public static boolean isConnected() {
        try {
            if (database == null) {
                getConnection();
            }
            
            if (database == null) {
                return false;
            }
            
            database.runCommand(new Document("ping", 1));
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            database = null;
            mongoClient = null;
        }
    }
}