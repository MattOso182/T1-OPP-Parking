package ec.edu.espe.monthlyrentmanager.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author T.A.P,The Art of Programming, @ESPE
 */
public class RentalManager {
    private static RentalManager instance;
    private double monthlyRent;
    private final String FILE_PATH = "src/data/rent.txt";

    private RentalManager() {
        loadRentFromFile();
    }

    public static RentalManager getInstance() {
        if (instance == null) {
            instance = new RentalManager();
        }
        return instance;
    }

    private void loadRentFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            monthlyRent = Double.parseDouble(br.readLine());
        } catch (Exception e) {
            monthlyRent = 20; 
        }
    }

    private void saveRentToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            pw.println(monthlyRent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getMonthlyRent() {
        return monthlyRent;
    }
    
    public void updateMonthlyRent(double newRent) {
        this.monthlyRent = newRent;
        saveRentToFile();
    }
}
