package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
}
