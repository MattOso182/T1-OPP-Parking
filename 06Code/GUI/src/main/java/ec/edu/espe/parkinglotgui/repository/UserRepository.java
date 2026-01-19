package ec.edu.espe.parkinglotgui.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.List;

public class UserRepository {
    private MongoCollection<Document> collection;

    public UserRepository() {
        MongoDatabase database = MongoDBConnection.getConnection();
        if (database != null) {
            this.collection = database.getCollection("Users");
        }
    }

    public boolean findUserCredentials(String residentID, String password) {
        if (collection == null) return false;

        Document result = collection.find().first();
        if (result == null || !result.containsKey("users")) return false;

        List<Document> users = (List<Document>) result.get("users");
        for (Document user : users) {
            String dbID = user.getString("residentID");
            String dbPass = user.getString("password");

            if (residentID.equals(dbID) && password.equals(dbPass)) {
                return true;
            }
        }
        return false;
    }
}