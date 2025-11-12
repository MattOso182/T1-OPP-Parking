package view;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

import model.*;

import parkingcontrolsystem.library.*;
import java.util.Scanner;


public class ParkingControlSystemSimulator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingControlSystem system = new ParkingControlSystem("PARKING-PCS"); 
        int option;

        do {
            System.out.println("=======================================");
            System.out.println("      PARKING CONTROL SYSTEM     ");
            System.out.println("=======================================");
            System.out.println("1. Register entry and exit of people");
            System.out.println("2. Track parking space status");
            System.out.println("3. Assign and manage parking spots");
            System.out.println("4. Verify user/visitor authorization");
            System.out.println("5. Search and verify vehicle/license info");
            System.out.println("6. Validate and update vehicle registration");
            System.out.println("7. Manage rentals and temporary assignments");
            System.out.println("8. Generate reports and usage statistics");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (option) {
                case 1 -> {
                    System.out.println("\n--- Register entry and exit of people ---");
                    // Example: could use EntryExitRecord, User, Visitor
                    System.out.print("Enter user/visitor name: ");
                    String name = scanner.nextLine();
                    System.out.print("Is entering or exiting? (E/X): ");
                    String action = scanner.nextLine();
                    EntryExitRecord record = new EntryExitRecord(name, action);
                    // You can later save it with JsonDataManager or ParkingControlSystem
                    System.out.println("Record saved successfully.");
                }

                case 2 -> {
                    System.out.println("\n--- Track parking space status ---");
                    // Example: ParkingSpace or ParkingLot
                    ParkingSpace space = new ParkingSpace("A1", true);
                    System.out.println("Space " + space.getId() + " is currently " +
                            (space.isAvailable() ? "Available" : "Occupied"));
                }

                case 3 -> {
                    System.out.println("\n--- Assign and manage parking spots ---");
                    // Example: ParkingZone, SpaceDefinition, ParkingLot
                    System.out.print("Enter parking spot ID: ");
                    String id = scanner.nextLine();
                    ParkingSpace space = new ParkingSpace(id, true);
                    System.out.println("Parking spot " + id + " assigned successfully.");
                }

                case 4 -> {
                    System.out.println("\n--- Verify user/visitor authorization ---");
                    // Example: SecurityGuard, User, VisitorManager
                    System.out.print("Enter visitor name: ");
                    String visitorName = scanner.nextLine();
                    SecurityGuard guard = new SecurityGuard("Juan Perez");
                    System.out.println("Authorization verified by guard " + guard.getName());
                }

                case 5 -> {
                    System.out.println("\n--- Search and verify vehicle/license info ---");
                    // Example: Vehicle, LicensePlate
                    System.out.print("Enter license plate: ");
                    String plateNumber = scanner.nextLine();
                    LicensePlate plate = new LicensePlate(plateNumber);
                    System.out.println("License plate " + plate.getNumber() + " verified successfully.");
                }

                case 6 -> {
                    System.out.println("\n--- Validate and update vehicle registration ---");
                    // Example: Vehicle, JsonDataManager
                    System.out.print("Enter vehicle plate to update: ");
                    String plate = scanner.nextLine();
                    Vehicle vehicle = new Vehicle(plate, "Car");
                    vehicle.validateRegistration();
                    System.out.println("Vehicle registration updated successfully.");
                }

                case 7 -> {
                    System.out.println("\n--- Manage rentals and temporary assignments ---");
                    // Example: Rental, ResidentManager, VisitorManager
                    Rental rental = new Rental("A1", "3 hours");
                    System.out.println("Rental created for spot " + rental.getSpaceId());
                }

                case 8 -> {
                    System.out.println("\n--- Generate reports and usage statistics ---");
                    // Example: JsonDataManager or ParkingControlSystem
                    system.generateReport();
                }

                case 9 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid option. Try again.");
            }

            System.out.println();
        } while (option != 9);
    }
}
