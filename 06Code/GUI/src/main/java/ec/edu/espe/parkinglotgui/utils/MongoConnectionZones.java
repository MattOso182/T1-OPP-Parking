package ec.edu.espe.parkinglotgui.utils;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnectionZones {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoConnectionZones() {
        mongoClient = MongoClients.create("mongodb+srv://Mateo:Mateo21032006@cluster0.t4qmrfv.mongodb.net/?appName=Cluster0");
        database = mongoClient.getDatabase("ParkingLotDB");
    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
