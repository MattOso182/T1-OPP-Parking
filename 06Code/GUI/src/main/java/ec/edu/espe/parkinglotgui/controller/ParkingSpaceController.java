package ec.edu.espe.parkinglotgui.controller;


/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoDBConnection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpaceController {

    private MongoCollection<Document> collection;

    public ParkingSpaceController() {
        try {
            MongoDatabase database = MongoDBConnection.getConnection();
            if (database != null) {
                collection = database.getCollection("ParkingSpaces");
            }
        } catch (Exception e) {
        }
    }

    public Document getFirstDocument() {
        try {
            if (collection != null) {
                return collection.find().first();
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Document getParkingComplexInfo() {
        try {
            Document firstDoc = getFirstDocument();
            if (firstDoc != null && firstDoc.containsKey("parkingComplex")) {
                Document parkingComplex = firstDoc.get("parkingComplex", Document.class);
                Document info = new Document();

                if (parkingComplex.containsKey("name")) {
                    info.append("name", cleanField(parkingComplex.getString("name")));
                }
                if (parkingComplex.containsKey("totalSpaces")) {
                    info.append("totalSpaces", parkingComplex.getInteger("totalSpaces"));
                }
                if (parkingComplex.containsKey("availableForRent")) {
                    info.append("availableForRent", parkingComplex.getInteger("availableForRent"));
                }
                if (parkingComplex.containsKey("blocks")) {
                    List<Document> blocks = parkingComplex.getList("blocks", Document.class);
                    info.append("totalBlocks", blocks.size());

                    int totalSpacesCount = 0;
                    for (Document block : blocks) {
                        if (block.containsKey("sections")) {
                            List<Document> sections = block.getList("sections", Document.class);
                            for (Document section : sections) {
                                if (section.containsKey("spaces")) {
                                    totalSpacesCount += section.getList("spaces", Document.class).size();
                                }
                            }
                        }
                    }
                    info.append("actualSpacesCount", totalSpacesCount);
                }

                return info;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<String> getAvailableSpaces() {
        List<String> availableSpaces = new ArrayList<>();

        try {
            Document firstDoc = collection.find().first();
            if (firstDoc == null) {
                return availableSpaces;
            }

            if (firstDoc.containsKey("parkingComplex")) {
                Document parkingComplex = firstDoc.get("parkingComplex", Document.class);

                if (parkingComplex.containsKey("blocks")) {
                    List<Document> blocks = parkingComplex.getList("blocks", Document.class);

                    for (Document block : blocks) {
                        if (block.containsKey("sections")) {
                            List<Document> sections = block.getList("sections", Document.class);

                            for (Document section : sections) {
                                if (section.containsKey("spaces")) {
                                    List<Document> spaces = section.getList("spaces", Document.class);

                                    for (Document space : spaces) {
                                        String spaceId = space.getString("spaceId");
                                        Boolean isOccupied = space.getBoolean("isOccupied");
                                        Boolean isAvailableForRent = space.getBoolean("isAvailableForRent");

                                        if (isOccupied != null && !isOccupied
                                                && isAvailableForRent != null && isAvailableForRent
                                                && spaceId != null && !spaceId.trim().isEmpty()) {

                                            availableSpaces.add(cleanSpaceId(spaceId));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        return availableSpaces;
    }

    private String cleanSpaceId(String spaceId) {
        if (spaceId == null) {
            return "";
        }

        spaceId = spaceId.trim();
        while (spaceId.endsWith("_")) {
            spaceId = spaceId.substring(0, spaceId.length() - 1);
        }
        return spaceId;
    }

    private String cleanField(String field) {
        if (field == null) {
            return "";
        }

        field = field.trim();
        while (field.endsWith("_") || field.endsWith(",")) {
            field = field.substring(0, field.length() - 1).trim();
        }
        return field;
    }

    public Document getSpaceDetails(String spaceId) {
        try {
            Document firstDoc = collection.find().first();
            if (firstDoc != null && firstDoc.containsKey("parkingComplex")) {
                Document parkingComplex = firstDoc.get("parkingComplex", Document.class);

                if (parkingComplex.containsKey("blocks")) {
                    List<Document> blocks = parkingComplex.getList("blocks", Document.class);

                    for (Document block : blocks) {
                        if (block.containsKey("sections")) {
                            List<Document> sections = block.getList("sections", Document.class);

                            for (Document section : sections) {
                                if (section.containsKey("spaces")) {
                                    List<Document> spaces = section.getList("spaces", Document.class);

                                    for (Document space : spaces) {
                                        String currentSpaceId = space.getString("spaceId");
                                        if (currentSpaceId != null
                                                && cleanSpaceId(currentSpaceId).equals(cleanSpaceId(spaceId))) {
                                            return space;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean updateSpaceOccupation(String spaceId, boolean isOccupied) {
        try {
            if (collection == null) {
                return false;
            }

            Document firstDoc = collection.find().first();
            if (firstDoc == null) {
                return false;
            }

            Document query = new Document("_id", firstDoc.getObjectId("_id"))
                    .append("parkingComplex.blocks.sections.spaces.spaceId", spaceId);

            Document update = new Document("$set",
                    new Document("parkingComplex.blocks.$[block].sections.$[section].spaces.$[space].isOccupied", isOccupied)
            );

            List<Document> arrayFilters = new ArrayList<>();
            arrayFilters.add(new Document("block.sections.spaces.spaceId", spaceId));
            arrayFilters.add(new Document("section.spaces.spaceId", spaceId));
            arrayFilters.add(new Document("space.spaceId", spaceId));

            return collection.updateOne(
                    query,
                    update,
                    new com.mongodb.client.model.UpdateOptions().arrayFilters(arrayFilters)
            ).getModifiedCount() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean freeParkingSpace(String spaceId) {
        try {
            if (collection == null) {
                return false;
            }

            Document firstDoc = collection.find().first();
            if (firstDoc == null) {
                return false;
            }

            Document query = new Document("_id", firstDoc.getObjectId("_id"))
                    .append("parkingComplex.blocks.sections.spaces.spaceId", spaceId);

            Document update = new Document("$set",
                    new Document("parkingComplex.blocks.$[block].sections.$[section].spaces.$[space].isOccupied", false)
                            .append("parkingComplex.blocks.$[block].sections.$[section].spaces.$[space].isAvailableForRent", true)
            );

            List<Document> arrayFilters = new ArrayList<>();
            arrayFilters.add(new Document("block.sections.spaces.spaceId", spaceId));
            arrayFilters.add(new Document("section.spaces.spaceId", spaceId));
            arrayFilters.add(new Document("space.spaceId", spaceId));

            boolean updated = collection.updateOne(
                    query,
                    update,
                    new com.mongodb.client.model.UpdateOptions().arrayFilters(arrayFilters)
            ).getModifiedCount() > 0;

            if (!updated) {
                return freeParkingSpaceAlternative(spaceId);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private boolean freeParkingSpaceAlternative(String spaceId) {
        try {
            Document firstDoc = collection.find().first();
            if (firstDoc == null) {
                return false;
            }

            Document parkingComplex = firstDoc.get("parkingComplex", Document.class);
            List<Document> blocks = parkingComplex.getList("blocks", Document.class);

            for (int b = 0; b < blocks.size(); b++) {
                Document block = blocks.get(b);
                List<Document> sections = block.getList("sections", Document.class);

                for (int s = 0; s < sections.size(); s++) {
                    Document section = sections.get(s);
                    List<Document> spaces = section.getList("spaces", Document.class);

                    for (int sp = 0; sp < spaces.size(); sp++) {
                        Document space = spaces.get(sp);
                        String currentSpaceId = space.getString("spaceId");

                        if (currentSpaceId != null && cleanSpaceId(currentSpaceId).equals(cleanSpaceId(spaceId))) {
                            space.put("isOccupied", false);
                            space.put("isAvailableForRent", true);
                            spaces.set(sp, space);
                            section.put("spaces", spaces);
                            sections.set(s, section);
                            block.put("sections", sections);
                            blocks.set(b, block);
                            parkingComplex.put("blocks", blocks);

                            return collection.updateOne(
                                    new Document("_id", firstDoc.getObjectId("_id")),
                                    new Document("$set", new Document("parkingComplex", parkingComplex))
                            ).getModifiedCount() > 0;
                        }
                    }
                }
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}