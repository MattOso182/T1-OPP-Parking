package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

import ec.edu.espe.parkinglotgui.model.Block;
import ec.edu.espe.parkinglotgui.repository.BlockRepository;
import java.util.List;

public class BlockController {

    private final BlockRepository repository;

    public BlockController() {
        this.repository = new BlockRepository();
    }

    public List<Block> getAllBlocks() {
        return repository.findAll();
    }

    public boolean createBlock(int zoneId, int floorCount) {
        if (zoneId <= 0 || floorCount <= 0) {
            return false;
        }

        int newId = (int) repository.count() + 1;
        Block block = new Block(newId, zoneId, floorCount);
        
        repository.save(block);
        return true;
    }

    public boolean updateBlock(int id, int zoneId, int floorCount) {
        if (id <= 0 || zoneId <= 0 || floorCount <= 0) {
            return false;
        }

        repository.update(id, new Block(id, zoneId, floorCount));
        return true;
    }

    public void deleteBlock(int id) {
        repository.delete(id);
    }
}