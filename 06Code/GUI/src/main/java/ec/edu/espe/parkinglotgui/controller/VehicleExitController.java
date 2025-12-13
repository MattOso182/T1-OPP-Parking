package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.utils.MongoConnectionEntrances; 
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Date;

public class VehicleExitController {
    
    private static final String COLLECTION_NAME = "Entrances"; 
    
    private final MongoCollection<Document> collection;
    private final MongoConnectionEntrances mongoConnection;

    public VehicleExitController() {
        this.mongoConnection = new MongoConnectionEntrances();
        this.collection = mongoConnection.getCollection(COLLECTION_NAME); 
    }

    public boolean registerExit(String licensePlate) {

        try {
            Date exitTime = new Date();
            
            Bson filter = Filters.and(
                Filters.eq("licensePlate", licensePlate),
                Filters.eq("status", "PARKED")
            );
            
            Bson updates = Updates.combine(
                Updates.set("exitTime", exitTime),  
                Updates.set("status", "EXITED")     
            );

            UpdateResult result = collection.updateOne(filter, updates);

            if (result.getMatchedCount() == 0) {
                System.out.println("Error: No se encontró ningún vehículo con placa " + licensePlate + " en estado 'PARKED' para registrar la salida.");
                return false;
            }
            
            System.out.println("Salida registrada con éxito para placa: " + licensePlate);
            
            mongoConnection.closeConnection();
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al registrar la salida del vehículo: " + e.getMessage());
            mongoConnection.closeConnection();
            return false;
        }
    }
}