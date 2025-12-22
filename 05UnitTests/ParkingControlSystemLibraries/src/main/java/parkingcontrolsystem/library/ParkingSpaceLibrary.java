package parkingcontrolsystem.library;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class ParkingSpaceLibrary {
    private String spaceId;            
    private boolean isOccupied;        
    private String assignedTo;         
    private String residentType;       
    private String vehiclePlate;       

    
    public ParkingSpaceLibrary() {
    }

    
    public ParkingSpaceLibrary(String spaceId, boolean isOccupied, String assignedTo, String residentType, String vehiclePlate) {
        this.spaceId = spaceId;
        this.isOccupied = isOccupied;
        this.assignedTo = assignedTo;
        this.residentType = residentType;
        this.vehiclePlate = vehiclePlate;
    }

    
    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getResidentType() {
        return residentType;
    }

    public void setResidentType(String residentType) {
        this.residentType = residentType;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    

    public void assignSpace(String assignedTo, String residentType, String vehiclePlate) {
        this.assignedTo = assignedTo;
        this.residentType = residentType;
        this.vehiclePlate = vehiclePlate;
        this.isOccupied = true;
    }

   
    public String freeSpace() {
        this.assignedTo = null;
        this.residentType = "Ninguno";
        this.vehiclePlate = null;
        this.isOccupied = false;
        
        return (assignedTo + residentType + vehiclePlate + isOccupied);
    }


    public String getSpaceInfo() {
        return "Parqueadero: " + spaceId + "\n" +
               "Ocupado: " + (isOccupied ? "Sí" : "No") + "\n" +
               "Asignado a: " + (assignedTo != null ? assignedTo : "Nadie") + "\n" +
               "Tipo de residente: " + residentType + "\n" +
               "Vehículo: " + (vehiclePlate != null ? vehiclePlate : "Ninguno");
    }

    @Override
    public String toString() {
        return "Espacio " + spaceId + " - " + (isOccupied ? "Ocupado" : "Disponible");
    }
}