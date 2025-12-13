package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.utils.MongoConnectionEntrances; 
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date; 

public class VehicleEntryController {
    
    private static final String COLLECTION_NAME = "Entrances"; 
    
    private final MongoCollection<Document> collection;
    private final MongoConnectionEntrances mongoConnection;

    public VehicleEntryController() {
        this.mongoConnection = new MongoConnectionEntrances();
        this.collection = mongoConnection.getCollection(COLLECTION_NAME); 
    }

   
    public boolean registerEntry(String licensePlate) {

        try {
            Date entryTime = new Date();
            
            Document entryRecord = new Document("licensePlate", licensePlate)
                                           .append("entryTime", entryTime)
                                           .append("status", "PARKED");

            collection.insertOne(entryRecord);
            System.out.println("Registro de entrada exitoso para placa: " + licensePlate);
            
            mongoConnection.closeConnection(); 
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al registrar la entrada del vehículo: " + e.getMessage());
            mongoConnection.closeConnection(); 
            return false;
        }
    }
}