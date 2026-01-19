package ec.edu.espe.parkinglotgui.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * @author T.A.P. (The Art of Programming), @ESPE
 */

public class ParkingSpaceRepository {
    private MongoCollection<Document> collection;

    public ParkingSpaceRepository() {
        MongoDatabase database = MongoDBConnection.getConnection();
        if (database != null) {
            this.collection = database.getCollection("ParkingSpaces");
        }
    }

    public Document findFirst() {
        return (collection != null) ? collection.find().first() : null;
    }

    public boolean updateOccupation(String spaceId, boolean isOccupied) {
        Document firstDoc = findFirst();
        if (firstDoc == null) return false;

        Document query = new Document("_id", firstDoc.getObjectId("_id"))
                .append("parkingComplex.blocks.sections.spaces.spaceId", spaceId);

        Document update = new Document("$set",
                new Document("parkingComplex.blocks.$[block].sections.$[section].spaces.$[space].isOccupied", isOccupied));

        List<Document> arrayFilters = new ArrayList<>();
        arrayFilters.add(new Document("block.sections.spaces.spaceId", spaceId));
        arrayFilters.add(new Document("section.spaces.spaceId", spaceId));
        arrayFilters.add(new Document("space.spaceId", spaceId));

        return collection.updateOne(query, update, new UpdateOptions().arrayFilters(arrayFilters)).getModifiedCount() > 0;
    }

    public boolean saveComplex(Document parkingComplex, Object id) {
        return collection.updateOne(
                new Document("_id", id),
                new Document("$set", new Document("parkingComplex", parkingComplex))
        ).getModifiedCount() > 0;
    }
}