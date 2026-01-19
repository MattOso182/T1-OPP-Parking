package ec.edu.espe.monthlyrentmanager.model;

import ec.edu.espe.monthlyrentmanager.utils.RentalManager;

/**
 *
 * @author T.A.P, The Art of Programming, @ESPE
 */
public class Resident {
    private int id;
    private String name;
    private int months;

    public Resident(int id, String name, int months) {
        this.id = id;
        this.name = name;
        this.months = months;
    }

    public double calculateTotalRent() {
        double monthlyRent = RentalManager.getInstance().getMonthlyRent();
        return monthlyRent * months;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMonths() {
        return months;
    }
}
