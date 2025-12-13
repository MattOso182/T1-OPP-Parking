package data;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */


import com.fasterxml.jackson.core.type.TypeReference;
import ec.edu.espe.parkinglot.model.BuildingBlock;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class BuildingBlockDAO {
    private final File file = new File("data/buildingblocks.json");
    private List<BuildingBlock> cache;

    public BuildingBlockDAO() { load(); }

    private synchronized void load() {
        cache = JsonStorage.readList(file, new TypeReference<List<BuildingBlock>>(){});
    }
    private synchronized void save() { JsonStorage.writeList(file, cache); }

    public synchronized List<BuildingBlock> findAll() { return new ArrayList<>(cache); }
    public synchronized Optional<BuildingBlock> findById(String id) {
        return cache.stream().filter(b->b.getId().equals(id)).findFirst();
    }
    public synchronized void insert(BuildingBlock b) { cache.add(b); save(); }
    public synchronized void update(BuildingBlock b) {
        for (int i=0;i<cache.size();i++){
            if (cache.get(i).getId().equals(b.getId())) { cache.set(i,b); save(); return; }
        }
    }
    public synchronized void delete(String id) { cache.removeIf(b->b.getId().equals(id)); save(); }
}
