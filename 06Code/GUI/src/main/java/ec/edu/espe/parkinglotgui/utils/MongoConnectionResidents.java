package ec.edu.espe.parkinglotgui.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class MongoConnectionResidents {

    private MongoClient mongoClient;
    private MongoDatabase database;

    private static final String CONNECTION_STRING = "mongodb+srv://Mateo:Mateo21032006@cluster0.t4qmrfv.mongodb.net/?appName=Cluster0";
    private static final String DATABASE_NAME = "ParkingLotDB"; 

    public MongoConnectionResidents() {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("Conectado a MongoDB Residents");
        } catch (Exception e) {
            System.err.println("Error conectando a MongoDB Residents: " + e.getMessage());
        }
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public static MongoDatabase getDatabase() {
        MongoClient client = MongoClients.create(CONNECTION_STRING);
        return client.getDatabase(DATABASE_NAME);
    }
    
    public static MongoCollection<Document> getCollectionStatic(String collectionName) {
        MongoClient client = MongoClients.create(CONNECTION_STRING);
        MongoDatabase db = client.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        client.close(); 
        return collection;
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("🔌 Conexión Residents cerrada");
        }
    }
}
