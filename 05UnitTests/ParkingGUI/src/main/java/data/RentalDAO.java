package data;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import com.fasterxml.jackson.core.type.TypeReference;
import ec.edu.espe.parkinglot.model.Rental;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class RentalDAO {
    private final File file = new File("data/rentals.json");
    private List<Rental> cache;

    public RentalDAO() { load(); }

    private synchronized void load() {
        cache = JsonStorage.readList(file, new TypeReference<List<Rental>>(){});
    }
    private synchronized void save() { JsonStorage.writeList(file, cache); }

    public synchronized List<Rental> findAll() { return new ArrayList<>(cache); }
    public synchronized Optional<Rental> findById(String id) {
        return cache.stream().filter(r->r.getId().equals(id)).findFirst();
    }
    public synchronized void insert(Rental r) { cache.add(r); save(); }
    public synchronized void update(Rental r) {
        for (int i=0;i<cache.size();i++){
            if (cache.get(i).getId().equals(r.getId())) { cache.set(i,r); save(); return; }
        }
    }
    public synchronized void delete(String id) { cache.removeIf(r->r.getId().equals(id)); save(); }
}
