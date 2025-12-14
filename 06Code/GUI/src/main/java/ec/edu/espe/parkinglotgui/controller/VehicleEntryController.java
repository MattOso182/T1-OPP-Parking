package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Emily Calle, T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.utils.MongoConnectionEntrances;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Date;
import java.util.regex.Pattern;

public class VehicleEntryController {
    
    private static final String ENTRANCES_COLLECTION = "Entrances";
    
    private final MongoCollection<Document> entrancesCollection;
    private final MongoConnectionEntrances mongoConnectionEntrances;
    
    private static final Pattern LICENSE_PLATE_PATTERN = 
        Pattern.compile("^[A-Z]{3}-\\d{4}$");

    public VehicleEntryController() {
        this.mongoConnectionEntrances = new MongoConnectionEntrances();
        this.entrancesCollection = mongoConnectionEntrances.getCollection(ENTRANCES_COLLECTION);
    }

    private boolean validateLicensePlateFormat(String licensePlate) {
        boolean isValid = LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
        
        if (!isValid) {
            System.err.println("Formato de placa inválido: " + licensePlate);
            System.err.println("El formato debe ser: ABC-1234 (3 letras, guion, 4 números)");
        }
        
        return isValid;
    }

    public boolean registerEntry(String licensePlate) {
        try {
            if (!validateLicensePlateFormat(licensePlate)) {
                System.err.println("La placa " + licensePlate + " tiene formato inválido");
                return false;
            }
            
            Document activeEntry = entrancesCollection.find(
                new Document("licensePlate", licensePlate)
                    .append("status", "PARKED")
            ).first();
            
            if (activeEntry != null) {
                System.err.println("El vehículo con placa " + licensePlate + " ya está estacionado");
                return false;
            }
            
            Date entryTime = new Date();
            
            Document entryRecord = new Document("licensePlate", licensePlate)
                                           .append("entryTime", entryTime)
                                           .append("status", "PARKED");

            entrancesCollection.insertOne(entryRecord);
            System.out.println("Registro de entrada exitoso para placa: " + licensePlate);
            
            mongoConnectionEntrances.closeConnection();
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Error al registrar la entrada del vehículo: " + e.getMessage());
            mongoConnectionEntrances.closeConnection();
            return false;
        }
    }
    
    public boolean isVehicleParked(String licensePlate) {
        try {
            Document activeEntry = entrancesCollection.find(
                new Document("licensePlate", licensePlate)
                    .append("status", "PARKED")
            ).first();
            
            return activeEntry != null;
        } catch (Exception e) {
            System.err.println("Error verificando estado del vehículo: " + e.getMessage());
            return false;
        }
    }
}
