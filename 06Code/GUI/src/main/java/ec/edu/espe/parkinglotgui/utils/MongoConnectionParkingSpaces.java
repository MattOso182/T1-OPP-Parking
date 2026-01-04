package ec.edu.espe.parkinglotgui.utils;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnectionParkingSpaces {

    private static final String CONNECTION_STRING = "mongodb+srv://Mateo:Mateo21032006@cluster0.t4qmrfv.mongodb.net/ParkingLotDB?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "ParkingLotDB";

    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}