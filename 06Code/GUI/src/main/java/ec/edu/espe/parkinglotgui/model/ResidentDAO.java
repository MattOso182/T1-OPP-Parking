package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoConnectionResidents;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

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

    public void deleteById(String residentID) {
        collection.deleteOne(new Document("residentID", residentID));
    }

    public void update(String residentID, Document update) {
        collection.updateOne(
                new Document("residentID", residentID),
                new Document("$set", update)
        );
    }

    public long count() {
        return collection.countDocuments();
    }

    public Document findByResidentID(String residentID) {
        return collection.find(new Document("residentID", residentID)).first();
    }
}