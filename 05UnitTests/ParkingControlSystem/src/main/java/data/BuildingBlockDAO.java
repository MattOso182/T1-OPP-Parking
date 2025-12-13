package data;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */


import com.fasterxml.jackson.core.type.TypeReference;
import model.BuiildingBlock;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class BuildingBlockDAO {
    private final File file = new File("data/buildingblocks.json");
    private List<BuiildingBlock> cache;

    public BuildingBlockDAO() { load(); }

    private synchronized void load() {
        cache = JsonStorage.readList(file, new TypeReference<List<BuiildingBlock>>(){});
    }
    private synchronized void save() { JsonStorage.writeList(file, cache); }

    public synchronized List<BuiildingBlock> findAll() { return new ArrayList<>(cache); }
    public synchronized Optional<BuiildingBlock> findById(String id) {
        return cache.stream().filter(b->b.getId().equals(id)).findFirst();
    }
    public synchronized void insert(BuiildingBlock b) { cache.add(b); save(); }
    public synchronized void update(BuiildingBlock b) {
        for (int i=0;i<cache.size();i++){
            if (cache.get(i).getId().equals(b.getId())) { cache.set(i,b); save(); return; }
        }
    }
    public synchronized void delete(String id) { cache.removeIf(b->b.getId().equals(id)); save(); }
}
