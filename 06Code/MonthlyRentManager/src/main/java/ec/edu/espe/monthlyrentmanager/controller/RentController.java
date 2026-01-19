package ec.edu.espe.monthlyrentmanager.controller;

import ec.edu.espe.monthlyrentmanager.model.Resident;

/**
 *
 * @author T.A.P,The Art of Programming, @ESPE
 */
public class RentController {
    public double getTotalToPay(int id, String name, int months) {
        Resident resident = new Resident(id, name, months);
        return resident.calculateTotalRent();
    }
}
