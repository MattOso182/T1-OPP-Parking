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

        Document query = new Document("_id", firstDoc.getObjectId("_id"));

        Document update = new Document("$set",
            new Document("parkingComplex.blocks.$[].sections.$[].spaces.$[p].isOccupied", isOccupied)
        );

        List<Document> arrayFilters = List.of(
            new Document("p.spaceId", spaceId)
        );

        UpdateOptions options = new UpdateOptions().arrayFilters(arrayFilters);

        return collection.updateOne(query, update, options).getModifiedCount() > 0;
    }

    public boolean saveComplex(Document parkingComplex, Object id) {
        return collection.updateOne(
                new Document("_id", id),
                new Document("$set", new Document("parkingComplex", parkingComplex))
        ).getModifiedCount() > 0;
    }
    
    public List<Document> findOccupiedSpaces() {
        List<Document> occupiedSpaces = new ArrayList<>();
        Document root = findFirst();
        if (root == null) return occupiedSpaces;

        Document parkingComplex = root.get("parkingComplex", Document.class);
        if (parkingComplex == null) return occupiedSpaces;

        List<Document> blocks = parkingComplex.getList("blocks", Document.class);
        if (blocks == null) return occupiedSpaces;

        for (Document block : blocks) {
            String blockName = block.getString("blockName");

            List<Document> sections = block.getList("sections", Document.class);
            if (sections == null) continue;

            for (Document section : sections) {
                String sectionName = section.getString("section");

                List<Document> spaces = section.getList("spaces", Document.class);
                if (spaces == null) continue;

                for (Document space : spaces) {
                    if (Boolean.TRUE.equals(space.getBoolean("isOccupied"))) {
                        occupiedSpaces.add(new Document()
                            .append("block", blockName != null ? blockName.trim() : "")
                            .append("section", sectionName != null ? sectionName.trim() : "")
                            .append("id", space.getString("spaceId") != null
                                    ? space.getString("spaceId").trim().replaceAll("_+$", "")
                                    : "")
                        );
                    }
                }
            }
        }
        return occupiedSpaces;
    }
}