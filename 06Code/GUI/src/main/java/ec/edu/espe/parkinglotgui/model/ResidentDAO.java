package ec.edu.espe.parkinglotgui.model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoConnectionResidents;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class ResidentDAO {

    private final MongoCollection<Document> collection;

    public ResidentDAO() {
        MongoDatabase db = MongoConnectionResidents.getDatabase();
        this.collection = db.getCollection("Residents");
    }

    public List<Document> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    public void insert(Document doc) {
        collection.insertOne(doc);
    }

    public void deleteById(String id) {
        collection.deleteOne(new Document("residentID", id));
    }

    public void update(String id, Document update) {
        collection.updateOne(
            new Document("residentID", id),
            new Document("$set", update)
        );
    }

    public long count() {
        return collection.countDocuments();
    }
}