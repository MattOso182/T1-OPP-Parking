package parkingcontrolsystem.library;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */
public class SecurityGuard {
    private String id;
    private String name;
    private String shift; // Ejemplo: "Diurno", "Nocturno"

   
    public SecurityGuard(String id, String name, String shift) {
        this.id = id;
        this.name = name;
        this.shift = shift;
    }

    
    public void checkVehicle(Vehicle vehicle) {
        System.out.println("Verificando vehículo con placa: " + vehicle.getLicensePlate());
    }

    public void registerEntry(Vehicle vehicle) {
        System.out.println("Entrada registrada para vehículo: " + vehicle.getLicensePlate());
    }

    public void registerExit(Vehicle vehicle) {
        System.out.println("Salida registrada para vehículo: " + vehicle.getLicensePlate());
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}