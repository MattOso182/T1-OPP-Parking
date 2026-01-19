package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.repository.EntryExitRepository; 
import org.bson.Document;
import java.util.List;

/**
 * @author Arelis Samantha Bonilla Cruz, Student, @ESPE
 */
public class EntryExitController {
    
    private final EntryExitRepository repository;

    public EntryExitController() {
        this.repository = new EntryExitRepository();
    }

    public List<Document> getAllRecords() {
        return repository.findAll();
    }
}