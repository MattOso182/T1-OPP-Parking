package ec.edu.espe.parkinglotgui.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class EntryExitController {
    private MongoCollection<Document> collection;

    public EntryExitController() {
        MongoDatabase db = MongoDBConnection.getConnection();
        if (db != null) {
            collection = db.getCollection("Entrances");
        }
    }

    public List<Document> getAllRecords() {
        List<Document> records = new ArrayList<>();
        if (collection != null) {
            for (Document doc : collection.find()) {
                records.add(doc);
            }
        }
        return records;
    }
}