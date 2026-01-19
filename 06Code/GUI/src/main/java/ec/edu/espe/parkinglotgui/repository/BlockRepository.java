package ec.edu.espe.parkinglotgui.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import ec.edu.espe.parkinglotgui.model.Block;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class BlockRepository {
    private final MongoCollection<Document> collection;

    public BlockRepository() {
        MongoDatabase database = MongoDBConnection.getConnection();
        this.collection = database.getCollection("Blocks");
    }

    public List<Block> findAll() {
        List<Block> blocks = new ArrayList<>();
        for (Document doc : collection.find()) {
            blocks.add(Block.fromDocument(doc));
        }
        return blocks;
    }

    public void save(Block block) {
        collection.insertOne(block.toDocument());
    }

    public void update(int id, Block block) {
        collection.replaceOne(Filters.eq("id", id), block.toDocument());
    }

    public void delete(int id) {
        collection.deleteOne(Filters.eq("id", id));
    }

    public long count() {
        return collection.countDocuments();
    }
}