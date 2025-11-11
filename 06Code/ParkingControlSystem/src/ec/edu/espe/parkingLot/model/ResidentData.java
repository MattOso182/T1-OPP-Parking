package model;

/**
 *
 * @author Mateo Aymacaña @ESPE T.A.P(The Art of Programming)
 */

import java.util.List;

public class ResidentData {
      private List<Resident> residents;
    
    public ResidentData() {}
    
    public ResidentData(List<Resident> residents) {
        this.residents = residents;
    }
    
    public List<Resident> getResidents() { return residents; }
    public void setResidents(List<Resident> residents) { this.residents = residents; }
}
