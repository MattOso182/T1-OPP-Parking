package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoConnectionParkingSpaces;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

public class ParkingSpaceDAO {

    private final MongoCollection<Document> collection;

    public ParkingSpaceDAO() {
        MongoDatabase db = MongoConnectionParkingSpaces.getDatabase();
        collection = db.getCollection("ParkingSpaces");
    }

    public List<String> getAvailableResidentSpaces() {
        List<String> spaces = new ArrayList<>();

        Document doc = collection.find().first();
        if (doc == null) return spaces;

        Document parkingComplex = doc.get("parkingComplex", Document.class);
        List<Document> blocks = parkingComplex.getList("blocks", Document.class);

        for (Document block : blocks) {
            List<Document> sections = block.getList("sections", Document.class);

            for (Document section : sections) {
                List<Document> spaceList = section.getList("spaces", Document.class);

                for (Document space : spaceList) {
                    if ("RESIDENT".equals(space.getString("type"))
                            && !space.getBoolean("isOccupied")) {
                        spaces.add(space.getString("spaceId"));
                    }
                }
            }
        }
        return spaces;
    }

    public void markSpaceAsOccupied(String spaceId) {
        collection.updateOne(
                Filters.eq("parkingComplex.blocks.sections.spaces.spaceId", spaceId),
                Updates.set("parkingComplex.blocks.$[].sections.$[].spaces.$[s].isOccupied", true),
                new UpdateOptions().arrayFilters(
                        List.of(Filters.eq("s.spaceId", spaceId))
                )
        );
    }

    public void markSpaceAsAvailable(String spaceId) {
        collection.updateOne(
                Filters.eq("parkingComplex.blocks.sections.spaces.spaceId", spaceId),
                Updates.set("parkingComplex.blocks.$[].sections.$[].spaces.$[s].isOccupied", false),
                new UpdateOptions().arrayFilters(
                        List.of(Filters.eq("s.spaceId", spaceId))
                )
        );
    }
}