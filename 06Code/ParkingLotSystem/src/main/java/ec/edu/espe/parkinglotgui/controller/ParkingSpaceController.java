package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.repository.ParkingSpaceRepository;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class ParkingSpaceController {

    private final ParkingSpaceRepository repository;

    public ParkingSpaceController() {
        this.repository = new ParkingSpaceRepository();
    }

    public List<Document> getAvailableSpacesDetails() {
        List<Document> availableSpaces = new ArrayList<>();
        try {
            Document firstDoc = repository.findFirst();
            if (firstDoc == null || !firstDoc.containsKey("parkingComplex")) return availableSpaces;

            Document parkingComplex = firstDoc.get("parkingComplex", Document.class);
            if (parkingComplex.containsKey("blocks")) {
                List<Document> blocks = parkingComplex.getList("blocks", Document.class);

                for (Document block : blocks) {
                    String blockName = block.getString("blockName") != null ? block.getString("blockName") : block.getString("name");
                    String blockCode = block.getString("blockCode");

                    if (block.containsKey("sections")) {
                        for (Document section : block.getList("sections", Document.class)) {
                            String sectionName = section.getString("section") != null ? section.getString("section") : "Sección";

                            if (section.containsKey("spaces")) {
                                for (Document space : section.getList("spaces", Document.class)) {
                                    if (Boolean.FALSE.equals(space.getBoolean("isOccupied"))) {
                                        String spaceId = space.getString("spaceId");
                                        
                                        Document spaceDetail = new Document()
                                                .append("block", cleanField(blockName))
                                                .append("blockCode", cleanField(blockCode))
                                                .append("section", cleanField(sectionName))
                                                .append("id", cleanSpaceId(spaceId))
                                                .append("type", space.getString("type"))
                                                .append("fullId", blockCode + "-" + sectionName + "-" + cleanSpaceId(spaceId));

                                        availableSpaces.add(spaceDetail);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableSpaces;
    }

    public List<String> getAvailableSpaces() {
        List<String> ids = new ArrayList<>();
        for (Document doc : getAvailableSpacesDetails()) {
            ids.add(doc.getString("id"));
        }
        return ids;
    }

    public Document getSpaceDetails(String spaceId) {
        List<Document> allSpaces = getAvailableSpacesDetails();
        for (Document doc : allSpaces) {
            if (doc.getString("id").equals(cleanSpaceId(spaceId))) return doc;
        }
        return null;
    }

    public boolean updateSpaceOccupation(String spaceId, boolean isOccupied) {
        if (spaceId == null || spaceId.trim().isEmpty()) return false;
        return repository.updateOccupation(cleanSpaceId(spaceId), isOccupied);
    }

    public boolean freeParkingSpace(String spaceId) {
        return updateSpaceOccupation(spaceId, false);
    }

    private String cleanSpaceId(String spaceId) {
        return (spaceId == null) ? "" : spaceId.trim().replaceAll("_+$", "");
    }

    private String cleanField(String field) {
        return (field == null) ? "" : field.trim().replaceAll("[_,]+$", "");
    }
}