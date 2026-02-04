package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.model.Vehicle;
import ec.edu.espe.parkinglotgui.repository.EntryExitRepository;
import ec.edu.espe.parkinglotgui.repository.VehicleRepository;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class VehicleEntryController {

    private final EntryExitRepository entryExitRepo;
    private final VehicleRepository vehicleRepo;
    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{4}$");

    public VehicleEntryController() {
        this.entryExitRepo = new EntryExitRepository();
        this.vehicleRepo = new VehicleRepository();
    }

    private boolean validateLicensePlateFormat(String licensePlate) {
        return LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
    }

    public boolean registerEntry(String licensePlate, String assignedSpace) {
        if (!validateLicensePlateFormat(licensePlate)) return false;
        if (entryExitRepo.isVehicleParked(licensePlate)) return false;

        ParkingSpaceController spaceController = new ParkingSpaceController();
        if (!spaceController.updateSpaceOccupation(assignedSpace, true)) {
            return false;
        }

        Document entry = new Document("licensePlate", licensePlate)
                .append("spaceId", assignedSpace)
                .append("entryTime", new Date())
                .append("status", "PARKED");

        entryExitRepo.saveEntry(entry);
        return true;
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }
}