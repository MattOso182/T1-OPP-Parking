package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import ec.edu.espe.parkinglotgui.model.Block;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class BlockController {

    private static final String COLLECTION_NAME = "Blocks";
    private final MongoCollection<Document> collection;

    public BlockController() {
        MongoDatabase database = MongoDBConnection.getConnection();
        collection = database.getCollection(COLLECTION_NAME);
    }

    public List<Block> getAllBlocks() {
        List<Block> blocks = new ArrayList<>();

        for (Document doc : collection.find()) {
            blocks.add(Block.fromDocument(doc));
        }

        return blocks;
    }

    public boolean createBlock(int zoneId, int floorCount) {
        if (zoneId <= 0 || floorCount <= 0) {
            return false;
        }

        int newId = (int) collection.countDocuments() + 1;

        Block block = new Block(newId, zoneId, floorCount);
        collection.insertOne(block.toDocument());
        return true;
    }

    public boolean updateBlock(int id, int zoneId, int floorCount) {
        if (id <= 0 || zoneId <= 0 || floorCount <= 0) {
            return false;
        }

        collection.replaceOne(
                Filters.eq("id", id),
                new Block(id, zoneId, floorCount).toDocument()
        );

        return true;
    }

    public void deleteBlock(int id) {
        collection.deleteOne(Filters.eq("id", id));
    }
}

