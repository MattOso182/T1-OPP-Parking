package ec.edu.espe.parkinglotgui.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnectionEntrances {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private final String DATABASE_NAME = "ParkingLotDB"; 

    public MongoConnectionEntrances() {
        try {
            String connectionString = "mongodb+srv://Mateo:Mateo21032006@cluster0.t4qmrfv.mongodb.net/?appName=Cluster0"; 
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
        }
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        if (database == null) {
             throw new IllegalStateException("La base de datos no está inicializada.");
        }
        return database.getCollection(collectionName);
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null; 
        }
    }
}