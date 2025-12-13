package data;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import com.fasterxml.jackson.core.type.TypeReference;
import ec.edu.espe.parkinglot.model.ParkingZone;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ParkingZoneDAO {
    private final File file = new File("data/parkingzones.json");
    private List<ParkingZone> cache;

    public ParkingZoneDAO() {
        load();
    }

    private synchronized void load() {
        cache = JsonStorage.readList(file, new TypeReference<List<ParkingZone>>(){});
    }
    private synchronized void save() {
        JsonStorage.writeList(file, cache);
    }

    public synchronized List<ParkingZone> findAll() { return new ArrayList<>(cache); }
    public synchronized Optional<ParkingZone> findById(String id) {
        return cache.stream().filter(z -> z.getId().equals(id)).findFirst();
    }
    public synchronized void insert(ParkingZone p) { cache.add(p); save(); }
    public synchronized void update(ParkingZone p) {
        for (int i=0;i<cache.size();i++){
            if (cache.get(i).getId().equals(p.getId())) { cache.set(i,p); save(); return; }
        }
    }
    public synchronized void delete(String id) { cache.removeIf(z->z.getId().equals(id)); save(); }
}
