
package ec.edu.espe.parkinglotgui.model;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */

public class ResidentParkingPrice implements ParkingPriceStrategy {
    @Override
    public double calculateTotal(int months) {
        return 45.00 * months; 
    }
}