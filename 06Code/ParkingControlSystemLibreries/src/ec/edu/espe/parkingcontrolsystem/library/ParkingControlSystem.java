package ec.edu.espe.parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ParkingControlSystem {

    
    private String systemId;
    private boolean active;
    private int totalVehicles;
    private List<ParkingSpace> parkingSpaces;
    private List<EntryExitRecord> records;


    public ParkingControlSystem() {
        this.systemId = "PCS-001";
        this.active = false;
        this.totalVehicles = 0;
        this.parkingSpaces = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public ParkingControlSystem(String systemId, boolean active, int totalVehicles) {
        this.systemId = systemId;
        this.active = active;
        this.totalVehicles = totalVehicles;
        this.parkingSpaces = new ArrayList<>();
        this.records = new ArrayList<>();
    }


    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(int totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public List<EntryExitRecord> getRecords() {
        return records;
    }

    public void setRecords(List<EntryExitRecord> records) {
        this.records = records;
    }


    public boolean startSystem() {
        if (!active) {
            active = true;
            System.out.println(" Parking Control System iniciado correctamente.");
            return true;
        }
        System.out.println("️ El sistema ya está en funcionamiento.");
        return false;
    }

    
    public boolean stopSystem() {
        if (active) {
            active = false;
            System.out.println(" Sistema detenido correctamente.");
            return true;
        }
        System.out.println("️ El sistema ya está detenido.");
        return false;
    }


    public boolean registerEntry(String plate) {
        if (!active) {
            System.out.println(" No se puede registrar, el sistema está inactivo.");
            return false;
        }

        Date entryTime = new Date();
        EntryExitRecord record = new EntryExitRecord("REC-" + (records.size() + 1), plate, entryTime, null, "P-001", "OP-001");
        records.add(record);
        totalVehicles++;
        System.out.println("Entrada registrada: " + plate + " a las " + entryTime);
        return true;
    }

    
    public boolean registerExit(String plate) {
        for (EntryExitRecord record : records) {
            if (record.getVehiclePlate().equals(plate) && record.getExitTime() == null) {
                record.setExitTime(new Date());
                totalVehicles--;
                System.out.println("Salida registrada: " + plate + " a las " + record.getExitTime());
                return true;
            }
        }
        System.out.println("No se encontró registro activo para la placa: " + plate);
        return false;
    }

  
    public boolean checkAvailability() {
        for (ParkingSpace space : parkingSpaces) {
            if (!space.isOccupied()) {
                System.out.println(" Espacio disponible: " + space.getSpaceId());
                return true;
            }
        }
        System.out.println("No hay espacios disponibles actualmente.");
        return false;
    }


   
    public String generateReport() {
        String report = "====== PARKING REPORT ======\n";
        report += "System ID: " + systemId + "\n";
        report += "Active: " + active + "\n";
        report += "Total Vehicles: " + totalVehicles + "\n";
        report += "Total Records: " + records.size() + "\n";
        report += "=============================\n";
        System.out.println(report);
        return report;
    }

}