package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.model.Visitor;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Filters.eq; 
import javax.swing.JOptionPane;

public class VisitorController {

    private MongoCollection<Document> collection;

    public VisitorController() {
        try {
            MongoDatabase database = MongoDBConnection.getConnection();
            if (database != null) {
                // Conectar directamente a la colección "Visitors"
                collection = database.getCollection("Visitors");
                System.out.println("VisitorController conectado a la colección: Visitors");

                // Verificar que existe
                long count = collection.countDocuments();
                System.out.println("Documentos en colección Visitors: " + count);
            } else {
                System.err.println("No se pudo obtener conexión a la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Error inicializando VisitorController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Visitor> getAllVisitors() {
        List<Visitor> visitors = new ArrayList<>();

        if (collection == null) {
            System.err.println("ERROR: La colección 'Visitors' no está disponible");
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la colección 'Visitors'",
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
            return visitors;
        }

        try {
            System.out.println("\n=== BUSCANDO VISITANTES EN COLECCIÓN 'Visitors' ===");

            int docCount = 0;
            for (Document doc : collection.find()) {
                docCount++;
                System.out.println("\n--- Documento " + docCount + " ---");
                System.out.println("Campos disponibles: " + doc.keySet());

                // Mostrar todos los campos para debugging
                for (String key : doc.keySet()) {
                    Object value = doc.get(key);
                    System.out.println("  " + key + ": " + value
                            + " (Tipo: " + (value != null ? value.getClass().getSimpleName() : "null") + ")");
                }

                // Convertir documento a Visitor
                Visitor visitor = convertDocumentToVisitor(doc);
                if (visitor != null) {
                    System.out.println("Visitante convertido: " + visitor.getNameVisitor());
                    visitors.add(visitor);
                } else {
                    System.out.println("Documento no pudo ser convertido a Visitor");
                }
            }

            System.out.println("\n=== RESUMEN ===");
            System.out.println("Documentos procesados: " + docCount);
            System.out.println("Visitantes obtenidos: " + visitors.size());

        } catch (Exception e) {
            System.err.println("Error obteniendo visitantes: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("\nConvirtiendo documento...");

            if (doc.containsKey("libraryVisitor")) {
                System.out.println("  Encontrado campo 'libraryVisitor'");
                Object libraryVisitorObj = doc.get("libraryVisitor");

                if (libraryVisitorObj instanceof Document) {
                    Document libraryVisitorDoc = (Document) libraryVisitorObj;
                    System.out.println("  'libraryVisitor' es un Document con campos: " + libraryVisitorDoc.keySet());

                    extractFromLibraryVisitor(visitor, libraryVisitorDoc);

                    if (doc.containsKey("hasPass")) {
                        visitor.setHasPass(doc.getBoolean("hasPass"));
                        System.out.println("  hasPass desde documento principal: " + doc.getBoolean("hasPass"));
                    }

                    if (doc.containsKey("userID")) {
                        String userID = cleanField(doc.getString("userID"));
                        if (visitor.getUserID() == null || visitor.getUserID().isEmpty()) {
                            visitor.setUserID(userID);
                            System.out.println("  userID desde documento principal: " + userID);
                        }
                    }
                } else {
                    System.out.println("  'libraryVisitor' NO es un Document, es: "
                            + (libraryVisitorObj != null ? libraryVisitorObj.getClass().getSimpleName() : "null"));
                }
            } 
            else {
                System.out.println("  No hay campo 'libraryVisitor', buscando campos directos...");

                if (doc.containsKey("visitorID")) {
                    visitor.setVisitorID(cleanField(doc.getString("visitorID")));
                    System.out.println("  visitorID directo: " + visitor.getVisitorID());
                } else if (doc.containsKey("userID")) {
                    visitor.setVisitorID(cleanField(doc.getString("userID")));
                    System.out.println("  usando userID como visitorID: " + visitor.getVisitorID());
                }

                if (doc.containsKey("nameVisitor")) {
                    visitor.setNameVisitor(cleanField(doc.getString("nameVisitor")));
                    System.out.println("  nameVisitor: " + visitor.getNameVisitor());
                } else if (doc.containsKey("name")) {
                    visitor.setNameVisitor(cleanField(doc.getString("name")));
                    System.out.println("  name: " + visitor.getNameVisitor());
                }

                if (doc.containsKey("vehicleDate")) {
                    visitor.setVehiclePlate(cleanField(doc.getString("vehicleDate")));
                    System.out.println("  vehicleDate: " + visitor.getVehiclePlate());
                } else if (doc.containsKey("vehiclePlate")) {
                    visitor.setVehiclePlate(cleanField(doc.getString("vehiclePlate")));
                    System.out.println("  vehiclePlate: " + visitor.getVehiclePlate());
                }

                if (doc.containsKey("userID") && (visitor.getUserID() == null || visitor.getUserID().isEmpty())) {
                    visitor.setUserID(cleanField(doc.getString("userID")));
                    System.out.println("  userID: " + visitor.getUserID());
                }

                if (doc.containsKey("hasPass")) {
                    visitor.setHasPass(doc.getBoolean("hasPass"));
                    System.out.println("  hasPass: " + visitor.isHasPass());
                }
            }

            boolean hasValidData = (visitor.getVisitorID() != null && !visitor.getVisitorID().isEmpty())
                    || (visitor.getNameVisitor() != null && !visitor.getNameVisitor().isEmpty());

            if (!hasValidData) {
                System.out.println("  Visitante no válido (sin ID o nombre)");
                return null;
            }

            visitor.setLibraryVisitorStatus(visitor.isHasPass() ? "WITH_PASS" : "NO_PASS");
            System.out.println("  Visitante creado exitosamente: " + visitor.getNameVisitor());

        } catch (Exception e) {
            System.err.println("Error convirtiendo documento a Visitor: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return visitor;
    }

    private void extractFromLibraryVisitor(Visitor visitor, Document libraryVisitorDoc) {
        try {
            // visitorID desde libraryVisitor
            if (libraryVisitorDoc.containsKey("visitorID")) {
                visitor.setVisitorID(cleanField(libraryVisitorDoc.getString("visitorID")));
                System.out.println("    visitorID desde libraryVisitor: " + visitor.getVisitorID());
            }

            if (libraryVisitorDoc.containsKey("nameVisitor")) {
                visitor.setNameVisitor(cleanField(libraryVisitorDoc.getString("nameVisitor")));
                System.out.println("    nameVisitor desde libraryVisitor: " + visitor.getNameVisitor());
            }

            if (libraryVisitorDoc.containsKey("vehicleDate")) {
                visitor.setVehiclePlate(cleanField(libraryVisitorDoc.getString("vehicleDate")));
                System.out.println("    vehicleDate desde libraryVisitor: " + visitor.getVehiclePlate());
            }

            if (libraryVisitorDoc.containsKey("userID")) {
                visitor.setUserID(cleanField(libraryVisitorDoc.getString("userID")));
                System.out.println("    userID desde libraryVisitor: " + visitor.getUserID());
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
        // Remover caracteres extraños al final
        while (field.endsWith("_") || field.endsWith(",") || field.endsWith(".")) {
            field = field.substring(0, field.length() - 1).trim();
        }

        return field;
    }

    public void diagnoseVisitorStructure() {
        if (collection == null) {
            System.out.println("ERROR: La colección 'Visitors' no está disponible para diagnóstico");
            return;
        }

        try {
            System.out.println("\n=== DIAGNÓSTICO COMPLETO DE COLECCIÓN 'Visitors' ===");
            System.out.println("Total documentos: " + collection.countDocuments());

            int limit = 3; // Ver solo primeros 3 documentos para diagnóstico
            int docNum = 0;

            for (Document doc : collection.find().limit(limit)) {
                docNum++;
                System.out.println("\n--- DOCUMENTO " + docNum + " ---");
                System.out.println("_id: " + doc.getObjectId("_id"));
                System.out.println("Todos los campos: " + doc.keySet());

                for (String key : doc.keySet()) {
                    if (key.equals("_id")) {
                        continue;
                    }
                    Object value = doc.get(key);
                    String typeName = value != null ? value.getClass().getSimpleName() : "null";
                    String valueStr = value != null ? value.toString() : "null";

                    if (valueStr.length() > 100) {
                        valueStr = valueStr.substring(0, 100) + "...";
                    }

                    System.out.println("  " + key + ": " + valueStr + " [Tipo: " + typeName + "]");

                    if (value instanceof Document) {
                        Document subDoc = (Document) value;
                        System.out.println("    Sub-campos de " + key + ": " + subDoc.keySet());
                        for (String subKey : subDoc.keySet()) {
                            Object subValue = subDoc.get(subKey);
                            System.out.println("      " + subKey + ": " + subValue);
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error en diagnóstico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void testDocumentConversion() {
        if (collection == null) {
            return;
        }

        try {
            Document firstDoc = collection.find().first();
            if (firstDoc != null) {
                System.out.println("\n=== PRUEBA DE CONVERSIÓN DE DOCUMENTO ===");
                Visitor visitor = convertDocumentToVisitor(firstDoc);
                if (visitor != null) {
                    System.out.println("Visitante convertido exitosamente:");
                    System.out.println(visitor.getInfo());
                }
            }
        } catch (Exception e) {
            System.err.println("Error en prueba de conversión: " + e.getMessage());
        }
    }
 
    private Document convertVisitorToDocument(Visitor visitor) {
    Document doc = new Document();
    
    doc.append("visitorID", visitor.getVisitorID());
    doc.append("nameVisitor", visitor.getNameVisitor());
    doc.append("vehiclePlate", visitor.getVehiclePlate());
    doc.append("userID", visitor.getUserID());
    doc.append("hasPass", visitor.isHasPass());
    doc.append("libraryVisitorStatus", visitor.getLibraryVisitorStatus()); 
    
    
    return doc;
}

    public void saveVisitor(Visitor visitor) {
    if (collection == null) {
        JOptionPane.showMessageDialog(null, "Colección no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
        try {
        Document doc = convertVisitorToDocument(visitor);
        collection.insertOne(doc);
        System.out.println("Visitante agregado con éxito: " + visitor.getNameVisitor());
     } catch (Exception e) {
        System.err.println("Error al guardar visitante: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

  
    public boolean updateVisitor(Visitor visitor) {
    if (collection == null) {
        JOptionPane.showMessageDialog(null, "Colección no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    try {
        Document filter = new Document("visitorID", visitor.getVisitorID());
        
        Document updateSet = new Document("$set", new Document()
                .append("nameVisitor", visitor.getNameVisitor())
                .append("vehicleDate", visitor.getVehiclePlate())
                .append("userID", visitor.getUserID())
                .append("hasPass", visitor.isHasPass())
                .append("libraryVisitorStatus", visitor.getLibraryVisitorStatus()));
        
        com.mongodb.client.result.UpdateResult result = collection.updateOne(filter, updateSet);
        
        if (result.getModifiedCount() > 0) {
            System.out.println("Visitante actualizado: " + visitor.getVisitorID());
            return true;
            } else {
            System.out.println("No se encontró el visitante para actualizar: " + visitor.getVisitorID());
            return false;
        }
            } catch (Exception e) {
        System.err.println("Error al actualizar visitante: " + e.getMessage());
        JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return false;
         }
    }
   
    public boolean deleteVisitor(String visitorID) {
    if (collection == null) {
        JOptionPane.showMessageDialog(null, "Colección no disponible.", "Error de BD", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
     try {
        Document filter = new Document("visitorID", visitorID);
        
        com.mongodb.client.result.DeleteResult result = collection.deleteOne(filter);
        
            if (result.getDeletedCount() > 0) {
                System.out.println("Visitante eliminado: " + visitorID);
                return true;
            } else {
                System.out.println("No se encontró el visitante para eliminar: " + visitorID);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar visitante: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
