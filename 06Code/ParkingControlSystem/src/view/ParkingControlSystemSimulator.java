package view;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */

import model.*; 
import java.util.Date;
import java.util.Scanner;
import parkingcontrolsystem.library.ParkingSpaceLibrary;

public class ParkingControlSystemSimulator {

    // --- Gestores y Controladores del Sistema ---
    private static Scanner scanner;
    private static ResidentManager residentManager;
    private static VisitorManager visitorManager;
    private static ParkingLot parkingLot;
    private static ParkingControlSystem controlSystem;
    private static EntryExitRecord logbook;
    private static SecurityGuard guard; // El actor principal para el control de acceso

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Parqueadero...");
        
        scanner = new Scanner(System.in);
        
        residentManager = new ResidentManager();
        
        parkingLot = new ParkingLot("MainLot"); 
        
        visitorManager = new VisitorManager();
        controlSystem = new ParkingControlSystem("PCS-01", parkingLot);
        logbook = new EntryExitRecord(); 
        
        guard = new SecurityGuard(
            "GUARD-01", 
            "Juan Perez", 
            "DIA", 
            "555-1234",
            controlSystem, 
            logbook, 
            residentManager, 
            visitorManager
        );
        
        guard.setOnDuty(true);
        
        System.out.println("\nSistema cargado y listo!");
        System.out.println(" Guardia " + guard.getName() + " esta en servicio.");
        System.out.println(" (" + residentManager.getTotalResidents() + " residentes cargados)");
        System.out.println(" (" + residentManager.getTotalVehicles() + " vehiculos registrados)"); 
        System.out.println(" (" + parkingLot.getTotalSpaces() + " espacios de parqueo cargados)");

        boolean salir = false;
        while (!salir) {
            showMenu();
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    manageFeature1_RegistrationEntryExit();
                    break;
                case "2":
                    manageFeature2_TrackStateSpace();
                    break;
                case "3":
                    manageFeature3_AssignSpaceManagement();
                    break;
                case "4":
                    manageFeature4_VerifyAuthorization();
                    break;
                case "5":
                    manageFeature5_SearchVehicleLicensePlate();
                    break;
                case "6":
                    manageFeature6_valideteUpdateVehicle();
                    break;
                case "7":
                    manageFeature7_manageRentals();
                    break;
                case "8":
                    manageFeature8_GenerateReports();
                    break;
                case "9":
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        }

        System.out.println("Cerrando el sistema...");
        guard.setOnDuty(false);
        
        parkingLot.saveToJson();
        System.out.println("¡Hasta luego!");
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("   SISTEMA DE GESTION DE PARQUEADERO");
        System.out.println("=".repeat(30));
        System.out.println("1. Registrar ingreso/salida (Residentes/Visitantes)");
        System.out.println("2. Rastrear estado de espacios (Ocupacion)");
        System.out.println("3. Asignar y gestionar espacios de parqueo");
        System.out.println("4. Verificar autorizacion de usuarios/visitantes");
        System.out.println("5. Buscar y verificar vehiculo/placa");
        System.out.println("6. Validar y actualizar vehiculos de residentes");
        System.out.println("7. Gestionar alquileres y asignaciones temporales");
        System.out.println("8. Generar reportes y estadisticas");
        System.out.println("9. Salir");
        System.out.println("-".repeat(30));
        System.out.print("Seleccione una opcion: ");
    }

    private static void manageFeature1_RegistrationEntryExit() {
        System.out.println("\n--- [1] Registrar Ingreso/Salida ---");
        System.out.print("Va a (1) Registrar Ingreso o (2) Registrar Salida?: ");
        String tipo = scanner.nextLine();
        System.out.print("Ingrese la placa del vehiculo (ej. ABC-123): ");
        String plate = scanner.nextLine().toUpperCase();

        if (tipo.equals("1")) {
            System.out.println("Procesando INGRESO para " + plate + "...");
            guard.registerEntry(plate, new Date());
        } else if (tipo.equals("2")) {
            System.out.println("Procesando SALIDA para " + plate + "...");
            guard.registerExit(plate, new Date());
        } else {
            System.out.println("Opcion no valida.");
        }
        pausar();
    }

    private static void manageFeature2_TrackStateSpace() {
        System.out.println("\n--- [2] Estado de Espacios (Disponibles/Ocupados) ---");
        // El controlSystem tiene el reporte más completo
        System.out.println(controlSystem.generateReport());
        pausar();
    }

    private static void manageFeature3_AssignSpaceManagement() {
        System.out.println("\n--- [3] Gestionar Espacios de Parqueo ---");
        System.out.println("La asignacion es automatica al registrar un ingreso (Opcion 1).");
        System.out.println("Para ver el detalle de un espacio especifico:");
        System.out.print("Ingrese el ID del espacio (ej. A-101): ");
        String spaceId = scanner.nextLine().toUpperCase();

        ParkingSpaceLibrary space = parkingLot.getSpaceList().stream()
                .filter(s -> s.getSpaceId().equalsIgnoreCase(spaceId))
                .findFirst()
                .orElse(null);

        if (space != null) {
            System.out.println("Informacion del Espacio:");
            System.out.println(space.getSpaceInfo());
        } else {
            System.out.println("No se encontro el espacio con ID: " + spaceId);
        }
        pausar();
    }

    private static void manageFeature4_VerifyAuthorization() {
        System.out.println("\n--- [4] Verificar Autorizacion (Visitantes) ---");
        System.out.print("Ingrese el ID del Visitante: ");
        String visitorId = scanner.nextLine();

        Visitor visitor = visitorManager.findVisitorById(visitorId);
        if (visitor == null) {
            System.out.print("Visitante no registrado. Ingrese el nombre: ");
            String name = scanner.nextLine();
            visitor = new Visitor(visitorId, "TEMP-" + visitorId, name, "", new Date(), null);
        }

        boolean isAuthorized = residentManager.processVisitorEntry(visitor);

        if (isAuthorized) {
            System.out.println("-> RESULTADO: VISITANTE AUTORIZADO Y PASE TEMPORAL ASIGNADO.");
        } else {
            System.out.println("-> RESULTADO: VISITANTE NO AUTORIZADO.");
            System.out.println(" (Un residente debe autorizarlo primero)");
        }
        pausar();
    }

    private static void manageFeature5_SearchVehicleLicensePlate() {
        System.out.println("\n--- [5] Buscar Vehiculo por Placa ---");
        System.out.print("Ingrese la placa a buscar: ");
        String plate = scanner.nextLine().toUpperCase();

        LicensePlate lp = new LicensePlate(plate, new Date(), "N/A", "N/A");
        System.out.println("Formato de Placa: " + (lp.validateFormat() ? "VALIDO" : "INVALIDO"));

        Vehicle vehicle = residentManager.findVehicleByPlate(plate);

        if (vehicle != null) {
            System.out.println("\nVehiculo encontrado:");
            System.out.println(vehicle.getVehicleInfo());

            Resident owner = residentManager.findResidentByVehiclePlate(plate);
            System.out.println("\nPropietario Registrado:");
            System.out.println("ID: " + owner.getResidentID() + ", Nombre: " + owner.getName());
        } else {
            System.out.println("\nNo se encontro ningun vehiculo con esa placa en el sistema.");
        }
        pausar();
    }

    private static void manageFeature6_valideteUpdateVehicle() {
        System.out.println("\n--- [6] Gestionar Vehiculos de Residente ---");
        System.out.print("Ingrese el ID del Residente: ");
        String residentId = scanner.nextLine();

        Resident resident = residentManager.findResidentById(residentId);
        if (resident == null) {
            System.out.println("Residente no encontrado.");
            pausar();
            return;
        }

        System.out.println("Residente: " + resident.getName());
        System.out.println("Vehiculos actuales: " + resident.getVehicles().size());
        
        System.out.println("Total de vehiculos en sistema: " + residentManager.getTotalVehicles());

        System.out.print("\nDesea (1) Anadir vehiculo o (2) Quitar vehiculo?: ");
        String opt = scanner.nextLine();

        if (opt.equals("1")) {
            System.out.print("Matricula: "); String plate = scanner.nextLine().toUpperCase();
            System.out.print("Color: "); String color = scanner.nextLine();
            System.out.print("Modelo: "); String model = scanner.nextLine();
            
            Vehicle newVehicle = new Vehicle(plate, color, model, residentId);
            
            if (newVehicle.validatePlate()) {
                boolean success = residentManager.addVehicleToResident(residentId, newVehicle);
                if (success) {
                    System.out.println("Vehiculo agregado exitosamente");
                    System.out.println("Nuevo total de vehiculos: " + residentManager.getTotalVehicles());
                } else {
                    System.out.println("No se pudo agregar - vehiculo ya existe");
                }
            } else {
                System.out.println("No se pudo agregar - placa invalida");
            }
            
        } else if (opt.equals("2")) {
            System.out.print("Matricula del vehiculo a quitar: ");
            String plate = scanner.nextLine().toUpperCase();
            boolean success = residentManager.removeVehicleFromResident(residentId, plate);
            if (success) {
                System.out.println("Vehiculo removido exitosamente");
                System.out.println("Nuevo total de vehiculos: " + residentManager.getTotalVehicles());
            } else {
                System.out.println("No se pudo remover - vehiculo no encontrado");
            }
        } else {
            System.out.println("Opcion no valida.");
        }
        pausar();
    }

    private static void manageFeature7_manageRentals() {
        System.out.println("\n--- [7] Gestionar Alquileres Temporales ---");
        System.out.print("Ingrese el ID del Residente (tipo ROTATING): ");
        String residentId = scanner.nextLine();

        Resident resident = residentManager.findResidentById(residentId);
        if (resident == null) {
            System.out.println("Residente no encontrado.");
            pausar();
            return;
        }

        if (resident.getUserType() == UserType.WITH_PARKING) {
            System.out.println("Este residente tiene un espacio asignado permanentemente. No requiere alquiler.");
            pausar();
            return;
        }

        if (resident.hasActiveRental()) {
            System.out.println("\nEl residente tiene un alquiler activo:");
            System.out.println(resident.getCurrentRental().getRentalInfo());
            System.out.print("¿Desea (1) Renovar, (2) Cancelar, (3) Procesar Pago?: ");
            String opt = scanner.nextLine();
            
            if (opt.equals("1")) {
                System.out.print("¿Cuantos meses adicionales? ");
                int months = Integer.parseInt(scanner.nextLine());
                residentManager.renewRentalForResident(residentId, months);
            } else if (opt.equals("2")) {
                String spaceId = resident.getCurrentRental().getSpaceId();
                residentManager.cancelRentalForResident(residentId);
                parkingLot.updateSpaceStatus(spaceId, "AVAILABLE");
                System.out.println("Espacio " + spaceId + " ha sido liberado.");
            } else if (opt.equals("3")) {
                residentManager.processPaymentForRental(residentId);
            }
        } else {
            System.out.println("\nEl residente no tiene un alquiler activo.");
            System.out.print("¿Desea (1) Crear un nuevo alquiler?: ");
            if (scanner.nextLine().equals("1")) {
                System.out.println("Buscando espacio disponible para alquilar...");
                
                ParkingSpaceLibrary rentalSpace = parkingLot.getSpaceList().stream()
                    .findFirst()
                    .orElse(null);
                
                if (rentalSpace != null) {
                    System.out.println("Espacio encontrado: " + rentalSpace.getSpaceId());
                    System.out.print("Precio mensual (ej. 50.0): ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("¿Cuantos meses? (ej. 1): ");
                    int months = Integer.parseInt(scanner.nextLine());
                    
                    residentManager.createRentalForResident(residentId, rentalSpace.getSpaceId(), months, price);
                    
                    parkingLot.saveToJson(); 
                    
                } else {
                    System.out.println("No hay espacios disponibles para alquilar en este momento.");
                }
            }
        }
        pausar();
    }

    private static void manageFeature8_GenerateReports() {
        System.out.println("\n--- [8] Generacion de Reportes ---");
        System.out.println("1. Reporte de Ocupacion de Espacios");
        System.out.println("2. Reporte de Residentes");
        System.out.println("3. Reporte de Alquileres Activos");
        System.out.println("4. Reporte de Vehiculos");
        System.out.print("Seleccione el reporte a generar: ");
        String opt = scanner.nextLine();

        System.out.println("\n--- INICIO DEL REPORTE ---");
        switch (opt) {
            case "1":
                System.out.println(controlSystem.generateReport());
                break;
            case "2":
                System.out.println(residentManager.generateResidentsReport());
                break;
            case "3":
                System.out.println(residentManager.generateRentalsReport());
                break;
            case "4":
                System.out.println("=== REPORTE DE VEHICULOS ===");
                System.out.println("Total de vehiculos registrados: " + residentManager.getTotalVehicles());
                System.out.println(residentManager.generateVehiclesReport());
                break;
            default:
                System.out.println("Opcion no valida.");
        }
        System.out.println("--- FIN DEL REPORTE ---");
        pausar();
    }

    private static void pausar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}