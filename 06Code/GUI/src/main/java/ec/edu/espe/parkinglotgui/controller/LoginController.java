package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.List;

public class LoginController {

    public boolean authenticate(String username, String password, String userType) {
        try {
            if (userType.equals("Guardia de seguridad")) {
                return username.equals("admin") && password.equals("123");
                
            } else if (userType.equals("Residente")) {
                return authenticateResident(username, password);
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }

    private boolean authenticateResident(String residentID, String password) {
        try {
            MongoDatabase database = MongoDBConnection.getConnection();
            
            if (database == null) {
                return false;
            }
            
            MongoCollection<Document> collection = database.getCollection("Users");
            Document result = collection.find().first();
            
            if (result == null) {
                return false;
            }
            
            List<Document> users = (List<Document>) result.get("users");
            
            if (users == null) {
                return false;
            }
            
            for (Document user : users) {
                String dbResidentID = user.getString("residentID");
                String dbPassword = user.getString("password");
                
                if (dbResidentID != null && dbPassword != null &&
                    dbResidentID.equals(residentID) && dbPassword.equals(password)) {
                    return true;
                }
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
}