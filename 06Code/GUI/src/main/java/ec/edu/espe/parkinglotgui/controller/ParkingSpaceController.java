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
                System.out.println("ParkingSpaceController connected to: ParkingSpaces");

                Document firstDoc = collection.find().first();
                if (firstDoc != null) {
                    System.out.println("First document keys: " + firstDoc.keySet());

                    if (firstDoc.containsKey("parkingComplex")) {
                        Document parkingComplex = firstDoc.get("parkingComplex", Document.class);
                        System.out.println("parkingComplex keys: " + parkingComplex.keySet());

                        if (parkingComplex.containsKey("blocks")) {
                            List<Document> blocks = parkingComplex.getList("blocks", Document.class);
                            System.out.println("Blocks found inside parkingComplex: " + blocks.size());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing ParkingSpaceController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Document getFirstDocument() {
        try {
            if (collection != null) {
                Document firstDoc = collection.find().first();
                if (firstDoc != null) {
                    System.out.println("Retrieved first document from ParkingSpaces collection");
                    return firstDoc;
                } else {
                    System.err.println("No documents found in ParkingSpaces collection");
                }
            } else {
                System.err.println("Collection is not initialized");
            }
        } catch (Exception e) {
            System.err.println("Error getting first document: " + e.getMessage());
            e.printStackTrace();
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
            System.err.println("Error getting parking complex info: " + e.getMessage());
        }
        return null;
    }

    public List<String> getAvailableSpaces() {
        List<String> availableSpaces = new ArrayList<>();

        try {
            System.out.println("\n=== GETTING AVAILABLE SPACES ===");

            Document firstDoc = collection.find().first();

            if (firstDoc == null) {
                System.out.println("ERROR: No documents found");
                return availableSpaces;
            }

            System.out.println("Document keys: " + firstDoc.keySet());

            if (firstDoc.containsKey("parkingComplex")) {
                Document parkingComplex = firstDoc.get("parkingComplex", Document.class);
                System.out.println("Found parkingComplex with keys: " + parkingComplex.keySet());

                if (parkingComplex.containsKey("blocks")) {
                    List<Document> blocks = parkingComplex.getList("blocks", Document.class);
                    System.out.println("Processing " + blocks.size() + " blocks...");

                    for (Document block : blocks) {
                        String blockName = block.getString("blockName");
                        System.out.println("  Block: " + cleanField(blockName));

                        if (block.containsKey("sections")) {
                            List<Document> sections = block.getList("sections", Document.class);

                            for (Document section : sections) {
                                String sectionName = section.getString("section");
                                System.out.println("    Section: " + cleanField(sectionName));

                                if (section.containsKey("spaces")) {
                                    List<Document> spaces = section.getList("spaces", Document.class);
                                    System.out.println("      Spaces in this section: " + spaces.size());

                                    for (Document space : spaces) {
                                        String spaceId = space.getString("spaceId");
                                        Boolean isOccupied = space.getBoolean("isOccupied");
                                        Boolean isAvailableForRent = space.getBoolean("isAvailableForRent");

                                        String cleanedSpaceId = cleanSpaceId(spaceId);
                                        System.out.println("      Space: " + cleanedSpaceId
                                                + " | Occupied: " + isOccupied
                                                + " | AvailableForRent: " + isAvailableForRent);

                                        if (isOccupied != null && !isOccupied
                                                && isAvailableForRent != null && isAvailableForRent) {

                                            if (spaceId != null && !spaceId.trim().isEmpty()) {
                                                availableSpaces.add(cleanedSpaceId);
                                                System.out.println("        ADDED TO AVAILABLE LIST");
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("      No 'spaces' key in section");
                                }
                            }
                        } else {
                            System.out.println("    No 'sections' key in block");
                        }
                    }
                } else {
                    System.out.println("No 'blocks' key in parkingComplex");
                }
            } else {
                System.out.println("No 'parkingComplex' key found");
            }

            System.out.println("Total available spaces found: " + availableSpaces.size());
            System.out.println("Available spaces: " + availableSpaces);

        } catch (Exception e) {
            System.err.println("Error getting available spaces: " + e.getMessage());
            e.printStackTrace();
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
            System.err.println("Error getting space details: " + e.getMessage());
        }
        return null;
    }

    public boolean updateSpaceOccupation(String spaceId, boolean isOccupied) {
        try {
            if (collection == null) {
                System.err.println("Collection is not initialized");
                return false;
            }

            System.out.println("\n=== UPDATING SPACE OCCUPATION ===");
            System.out.println("Space ID: " + spaceId);
            System.out.println("New isOccupied: " + isOccupied);

            Document firstDoc = collection.find().first();
            if (firstDoc == null) {
                System.err.println("No documents found in collection");
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

            com.mongodb.client.result.UpdateResult result = collection.updateOne(
                    query,
                    update,
                    new com.mongodb.client.model.UpdateOptions().arrayFilters(arrayFilters)
            );

            if (result.getModifiedCount() > 0) {
                System.out.println("SUCCESS: Space " + spaceId + " updated. isOccupied: " + isOccupied);
                return true;
            } else {
                System.out.println("Space not found or not updated: " + spaceId);
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error updating space occupation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean freeParkingSpace(String spaceId) {
        try {
            if (collection == null) {
                System.err.println("Collection is not initialized");
                return false;
            }

            System.out.println("\n=== FREEING PARKING SPACE ===");
            System.out.println("Space ID: " + spaceId);

            Document firstDoc = collection.find().first();
            if (firstDoc == null) {
                System.err.println("No documents found in collection");
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

            com.mongodb.client.result.UpdateResult result = collection.updateOne(
                    query,
                    update,
                    new com.mongodb.client.model.UpdateOptions().arrayFilters(arrayFilters)
            );

            if (result.getModifiedCount() > 0) {
                System.out.println("SUCCESS: Space " + spaceId + " freed and available for rent");
                return true;
            } else {
                System.out.println("Space not found or not updated: " + spaceId);
                return freeParkingSpaceAlternative(spaceId);
            }

        } catch (Exception e) {
            System.err.println("Error freeing parking space: " + e.getMessage());
            e.printStackTrace();
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

            boolean foundAndUpdated = false;

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

                            Document update = new Document("$set",
                                    new Document("parkingComplex", parkingComplex)
                            );

                            com.mongodb.client.result.UpdateResult result = collection.updateOne(
                                    new Document("_id", firstDoc.getObjectId("_id")),
                                    update
                            );

                            foundAndUpdated = result.getModifiedCount() > 0;

                            if (foundAndUpdated) {
                                System.out.println("ALTERNATIVE SUCCESS: Space " + spaceId + " freed");
                            }
                            break;
                        }
                    }
                    if (foundAndUpdated) {
                        break;
                    }
                }
                if (foundAndUpdated) {
                    break;
                }
            }

            if (!foundAndUpdated) {
                System.err.println("Space not found: " + spaceId);
            }

            return foundAndUpdated;

        } catch (Exception e) {
            System.err.println("Error in alternative free space: " + e.getMessage());
            return false;
        }
    }
}
