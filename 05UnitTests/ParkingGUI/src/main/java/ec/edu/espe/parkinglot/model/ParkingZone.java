package ec.edu.espe.parkinglot.model;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

public class ParkingZone {
    private String id;
    private String tipo;
    private int capacidad;

    public ParkingZone() {}

    public ParkingZone(String id, String tipo, int capacidad) {
        this.id = id;
        this.tipo = tipo;
        this.capacidad = capacidad;
    }

    public String getId() {
        return id; 
    }
    public void setId(String id) { 
        this.id = id; 
    }
    public String getTipo() {
        return tipo; 
    }
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }
    public int getCapacidad() {
        return capacidad; 
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad; 
    }
}

