package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Emily Calle, T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.model.Visitor;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.parkinglotgui.utils.MongoConnectionVisitors;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import java.awt.HeadlessException;

public class VisitorController {

    private MongoCollection<Document> collection;
    private MongoCollection<Document> residentCollection; 

    private static final Pattern PLATE_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{4}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s\\-' ]+$");
    
    public VisitorController() {
        try {
            MongoDatabase database = MongoConnectionVisitors.getConnection(); 
            if (database != null) {
                collection = database.getCollection("Visitors");
                residentCollection = database.getCollection("Residents"); 
            } else {
            }
        } catch (Exception e) {
            System.err.println("Error inicializando VisitorController: " + e.getMessage());
        }
    }

    private boolean checkIfResidentExists(String residentID) {
        if (residentCollection == null) {
            JOptionPane.showMessageDialog(null, "Error: La colección de Residentes no está disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (residentID == null || residentID.trim().isEmpty()) {
            return false; 
        }
        
        try {
            Document resident = residentCollection.find(Filters.eq("residentID", residentID)).first(); 
            return resident != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean checkIfVisitorIDExists(String visitorID) {
        if (collection == null) {
            JOptionPane.showMessageDialog(null, "Error: Colección de Visitantes no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (visitorID == null || visitorID.trim().isEmpty()) {
            return false; 
        }
        
        try {
            Document visitor = collection.find(Filters.eq("visitorID", visitorID)).first();
            return visitor != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkIfPlateExists(String vehiclePlate) {
        if (collection == null) {
            JOptionPane.showMessageDialog(null, "Error: Colección de Visitantes no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Document visitor = collection.find(Filters.eq("vehiclePlate", vehiclePlate.trim())).first();
            return visitor != null;
        } catch (Exception e) {
            return false;
        }
    }

    
    private boolean isValidVisitorID(String visitorID) {
        if (visitorID == null || visitorID.trim().isEmpty()) {
            return false;
        }
        return ID_PATTERN.matcher(visitorID.trim()).matches();
    }
    
    private boolean isRequiredResidentID(String residentID) {
        return residentID != null && !residentID.trim().isEmpty();
    }
    
    private boolean isValidVehiclePlate(String vehiclePlate) {
        if (vehiclePlate == null || vehiclePlate.trim().isEmpty()) {
            return false; 
        }
        return PLATE_PATTERN.matcher(vehiclePlate.trim()).matches();
    }

    public boolean saveVisitor(Visitor visitor) {
        if (collection == null) {
            JOptionPane.showMessageDialog(null, "Colección de Visitantes no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidVisitorID(visitor.getVisitorID())) {
            JOptionPane.showMessageDialog(null, "El ID del visitante debe contener solo números.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (checkIfVisitorIDExists(visitor.getVisitorID())) {
            JOptionPane.showMessageDialog(null, "El ID de visitante (" + visitor.getVisitorID() + ") ya existe y no puede duplicarse.", "Error de Unicidad", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!isRequiredResidentID(visitor.getResidentID())) {
            JOptionPane.showMessageDialog(null, "El ID de residente es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!isValidVehiclePlate(visitor.getVehiclePlate())) {
            JOptionPane.showMessageDialog(null, "La placa del vehículo es obligatoria y su formato debe ser 'ABC-0000'.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (checkIfPlateExists(visitor.getVehiclePlate())) {
            JOptionPane.showMessageDialog(null, "La placa del vehículo (" + visitor.getVehiclePlate() + ") ya está registrada por otro visitante.", "Error de Unicidad", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!checkIfResidentExists(visitor.getResidentID())) {
            JOptionPane.showMessageDialog(null, 
                    "El ID de Residente (" + visitor.getResidentID() + ") no existe en la base de datos de Residentes. No se puede registrar el visitante.", 
                    "Error de Integridad", 
                    JOptionPane.ERROR_MESSAGE);
            return false; 
        }

        try {
            Document doc = convertVisitorToDocument(visitor);
            collection.insertOne(doc);
            JOptionPane.showMessageDialog(null, "Visitante registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

   public boolean updateVisitor(Visitor visitor) {
        if (collection == null) {
            JOptionPane.showMessageDialog(null, "Colección de Visitantes no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!isValidVisitorID(visitor.getVisitorID())) {
            JOptionPane.showMessageDialog(null, "El ID del visitante debe contener solo números.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!isRequiredResidentID(visitor.getResidentID())) {
            JOptionPane.showMessageDialog(null, "El ID de residente es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!isValidVehiclePlate(visitor.getVehiclePlate())) {
             JOptionPane.showMessageDialog(null, "La placa del vehículo es obligatoria y su formato debe ser 'ABC-0000'.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String currentVisitorID = visitor.getVisitorID();
        String newPlate = visitor.getVehiclePlate();

        if (newPlate != null && !newPlate.trim().isEmpty()) {
            try {
                org.bson.conversions.Bson filterDuplicate = Filters.and(
                    Filters.eq("vehiclePlate", newPlate.trim()),
                    Filters.ne("visitorID", currentVisitorID)
                );
                
                Document duplicate = collection.find(filterDuplicate).first();
                
                if (duplicate != null) {
                    JOptionPane.showMessageDialog(null, "La placa del vehículo (" + newPlate + ") ya está registrada por otro visitante.", "Error de Unicidad", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Error al verificar placa en actualización: " + e.getMessage());
                return false;
            }
        }
        
        if (!checkIfResidentExists(visitor.getResidentID())) {
            JOptionPane.showMessageDialog(null, 
                    "El ID de Residente (" + visitor.getResidentID() + ") no existe en la base de datos de Residentes. No se puede actualizar el visitante.", 
                    "Error de Integridad", 
                    JOptionPane.ERROR_MESSAGE);
            return false; 
        }

        try {
            Document filter = new Document("visitorID", visitor.getVisitorID());

            Document updateSet = new Document("$set", new Document()
                    .append("nameVisitor", visitor.getNameVisitor())
                    .append("vehiclePlate", visitor.getVehiclePlate())
                    .append("residentID", visitor.getResidentID())
                    .append("hasPass", visitor.isHasPass())
                    .append("libraryVisitorStatus", visitor.getLibraryVisitorStatus()));

            UpdateResult result = collection.updateOne(filter, updateSet);

            if (result.getModifiedCount() > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un visitante con ese ID para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar visitante: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public List<Visitor> getAllVisitors() {
        List<Visitor> visitors = new ArrayList<>();

        if (collection == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la colección 'Visitors'",
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
            return visitors;
        }

        try {
            for (Document doc : collection.find()) {
                Visitor visitor = convertDocumentToVisitor(doc);
                if (visitor != null) {
                    visitors.add(visitor);
                }
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo visitantes: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Error al obtener visitantes: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return visitors;
    }

    private Visitor convertDocumentToVisitor(Document doc) {
        Visitor visitor = new Visitor();

        try {
            if (doc.containsKey("libraryVisitor")) {
                Object libraryVisitorObj = doc.get("libraryVisitor");
                if (libraryVisitorObj instanceof Document) {
                    Document libraryVisitorDoc = (Document) libraryVisitorObj;
                    extractFromLibraryVisitor(visitor, libraryVisitorDoc);
                    
                    if (doc.containsKey("residentID") && (visitor.getResidentID() == null || visitor.getResidentID().isEmpty())) {
                        visitor.setResidentID(cleanField(doc.getString("residentID")));
                    }
                    if (doc.containsKey("hasPass")) {
                        visitor.setHasPass(doc.getBoolean("hasPass"));
                    }
                }
            } else { 

                if (doc.containsKey("visitorID")) {
                    visitor.setVisitorID(cleanField(doc.getString("visitorID")));
                }
                if (doc.containsKey("nameVisitor")) {
                    visitor.setNameVisitor(cleanField(doc.getString("nameVisitor")));
                } else if (doc.containsKey("name")) {
                    visitor.setNameVisitor(cleanField(doc.getString("name")));
                }

                if (doc.containsKey("vehiclePlate")) {
                    visitor.setVehiclePlate(cleanField(doc.getString("vehiclePlate")));
                } else if (doc.containsKey("vehicleDate")) {
                     visitor.setVehiclePlate(cleanField(doc.getString("vehicleDate")));
                }

                if (doc.containsKey("residentID")) {
                    visitor.setResidentID(cleanField(doc.getString("residentID")));
                }
                
                if (doc.containsKey("hasPass")) {
                    visitor.setHasPass(doc.getBoolean("hasPass"));
                }
            }

            boolean hasValidData = (visitor.getVisitorID() != null && !visitor.getVisitorID().isEmpty())
                    || (visitor.getNameVisitor() != null && !visitor.getNameVisitor().isEmpty());

            if (!hasValidData) {
                return null;
            }

            visitor.setLibraryVisitorStatus(visitor.isHasPass() ? "WITH_PASS" : "NO_PASS");

        } catch (Exception e) {
            System.err.println("Error convirtiendo documento a Visitor: " + e.getMessage());
            return null;
        }

        return visitor;
    }

    private void extractFromLibraryVisitor(Visitor visitor, Document libraryVisitorDoc) {
        try {
            if (libraryVisitorDoc.containsKey("visitorID")) {
                visitor.setVisitorID(cleanField(libraryVisitorDoc.getString("visitorID")));
            }
            if (libraryVisitorDoc.containsKey("nameVisitor")) {
                visitor.setNameVisitor(cleanField(libraryVisitorDoc.getString("nameVisitor")));
            }
            if (libraryVisitorDoc.containsKey("vehiclePlate")) {
                visitor.setVehiclePlate(cleanField(libraryVisitorDoc.getString("vehiclePlate")));
            }
            if (libraryVisitorDoc.containsKey("residentID")) {
                visitor.setResidentID(cleanField(libraryVisitorDoc.getString("residentID")));
            }
        } catch (Exception e) {
            System.err.println("Error extrayendo datos de libraryVisitor: " + e.getMessage());
        }
    }

    private String cleanField(String field) {
        if (field == null) {
            return "";
        }

        field = field.trim();
        while (field.endsWith("_") || field.endsWith(",") || field.endsWith(".")) {
            field = field.substring(0, field.length() - 1).trim();
        }

        return field;
    }

    private Document convertVisitorToDocument(Visitor visitor) {
        Document doc = new Document();

        doc.append("visitorID", visitor.getVisitorID());
        doc.append("nameVisitor", visitor.getNameVisitor());
        doc.append("vehiclePlate", visitor.getVehiclePlate());
        doc.append("residentID", visitor.getResidentID());
        doc.append("hasPass", visitor.isHasPass());
        doc.append("libraryVisitorStatus", visitor.getLibraryVisitorStatus());

        return doc;
    }

    public boolean deleteVisitor(String visitorID) {
        if (collection == null) {
            JOptionPane.showMessageDialog(null, "Colección no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (visitorID == null || visitorID.trim().isEmpty()) {
             JOptionPane.showMessageDialog(null, "El ID del visitante no puede estar vacío para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
             return false;
        }

        try {
            Document filter = new Document("visitorID", visitorID);
            DeleteResult result = collection.deleteOne(filter);

            if (result.getDeletedCount() > 0) {
                 JOptionPane.showMessageDialog(null, "Visitante eliminado exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                 JOptionPane.showMessageDialog(null, "No se encontró un visitante con ID: " + visitorID, "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (HeadlessException e) {
            System.err.println("Error al eliminar visitante: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}