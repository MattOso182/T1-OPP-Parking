package view;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming).
 */
import model.*;
import java.util.Date;
import java.util.Scanner;
import parkingcontrolsystem.library.ParkingSpaceLibrary;

public class ParkingControlSystemSimulator {

    private static Scanner scanner;
    private static ResidentManager residentManager;
    private static VisitorManager visitorManager;
    private static ParkingLot parkingLot;
    private static ParkingControlSystem controlSystem;
    private static EntryExitRecord logbook;
    private static SecurityGuard guard;

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Parqueadero...");

        scanner = new Scanner(System.in);

        residentManager = new ResidentManager();

        parkingLot = new ParkingLot("MainLot");

        visitorManager = new VisitorManager();
        controlSystem = new ParkingControlSystem("PCS-01", parkingLot, residentManager);
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
                    parkingLot.showSpacesStatus();
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

            ParkingSpaceLibrary existingSpace = parkingLot.findSpaceByVehicle(plate);
            if (existingSpace != null) {
                System.out.println("El vehiculo YA tiene un espacio asignado: " + existingSpace.getSpaceId());
                System.out.print("Continuar con nuevo ingreso? (s/n): ");
                String respuesta = scanner.nextLine().toLowerCase();
                if (!respuesta.equals("s")) {
                    pausar();
                    return;
                }
            }

            guard.registerEntry(plate, new Date());

        } else if (tipo.equals("2")) {
            System.out.println("Procesando SALIDA para " + plate + "...");

            ParkingSpaceLibrary occupiedSpace = parkingLot.findSpaceByVehicle(plate);
            if (occupiedSpace != null) {
                System.out.println(" Vehiculo encontrado en espacio: " + occupiedSpace.getSpaceId());
                guard.registerExit(plate, new Date());
            } else {
                System.out.println(" No se encontro el vehículo en el estacionamiento");
            }
        } else {
            System.out.println("Opcion no valida.");
        }
        pausar();
    }

private static void manageFeature3_AssignSpaceManagement() {
    System.out.println("\n--- [3] Gestionar Espacios de Parqueo ---");

    try {
        if (parkingLot == null) {
            System.out.println("Error: ParkingLot no esta inicializado");
            pausar();
            return;
        }

        parkingLot.showDetailedSpacesStatus();

        System.out.println("\nOpciones:");
        System.out.println("1. Asignar espacio manualmente");
        System.out.println("2. Liberar espacio manualmente");
        System.out.println("3. Ver detalle de espacio especifico");
        System.out.print("Seleccione opcion: ");
        
        String opcion = scanner.nextLine().trim(); 
        if (opcion.isEmpty()) {
            System.out.println("ERROR: La opcion no puede estar vacia.");
            pausar();
            return;
        }

        switch (opcion) {
            case "1":
                System.out.print("Ingrese ID del espacio a asignar: ");
                String spaceToAssign = scanner.nextLine().toUpperCase().trim();
                
                if (spaceToAssign.isEmpty()) {
                    System.out.println("ERROR: El ID del espacio no puede estar vacio.");
                    break;
                }
                
                System.out.print("Ingrese placa del vehiculo: ");
                String plateToAssign = scanner.nextLine().toUpperCase().trim();

                if (plateToAssign.isEmpty()) {
                    System.out.println("ERROR: La placa del vehiculo no puede estar vacia.");
                    break;
                }

                if (parkingLot.spaceExists(spaceToAssign)) {
                    
                    ParkingSpaceLibrary space = parkingLot.getSpaceList().stream()
                            .filter(s -> s.getSpaceId().equalsIgnoreCase(spaceToAssign))
                            .findFirst().orElse(null);
                            
                    if (space == null) {
                        System.out.println("Error interno: No se pudo recuperar la informacion del espacio.");
                        break;
                    }

                    if (space.isOccupied()) { 
                         System.out.println("ERROR: El espacio " + spaceToAssign + " ya se encuentra ocupado.");
                         break;
                    }
                    
                    boolean assigned = parkingLot.assignSpaceToVehicle(spaceToAssign, plateToAssign, "Manual");
                    if (assigned) {
                        System.out.println("Espacio asignado exitosamente");
                    } else {
                        System.out.println("No se pudo asignar el espacio por una razon interna desconocida.");
                    }
                } else {
                    System.out.println("El espacio " + spaceToAssign + " no existe");
                }
                break;

            case "2":
                System.out.print("Ingrese ID del espacio a liberar: ");
                String spaceToFree = scanner.nextLine().toUpperCase().trim();

                if (spaceToFree.isEmpty()) {
                    System.out.println("ERROR: El ID del espacio no puede estar vacio.");
                    break;
                }

                if (parkingLot.spaceExists(spaceToFree)) {
                    
                    ParkingSpaceLibrary space = parkingLot.getSpaceList().stream()
                            .filter(s -> s.getSpaceId().equalsIgnoreCase(spaceToFree))
                            .findFirst().orElse(null);
                            
                    if (space == null) {
                        System.out.println("Error interno: No se pudo recuperar la informacion del espacio.");
                        break;
                    }
                    
                    if (!space.isOccupied()) { 
                        System.out.println("ERROR: El espacio " + spaceToFree + " ya esta disponible (no esta ocupado).");
                        break;
                    }

                    boolean freed = parkingLot.freeSpaceAndSync(spaceToFree);
                    if (freed) {
                        System.out.println("Espacio liberado exitosamente");
                    } else {
                        System.out.println("No se pudo liberar el espacio por una razon interna desconocida.");
                    }
                } else {
                    System.out.println("El espacio " + spaceToFree + " no existe");
                }
                break;

            case "3":
                System.out.print("Ingrese el ID del espacio para ver detalle: ");
                String spaceId = scanner.nextLine().toUpperCase().trim();

                if (spaceId.isEmpty()) {
                    System.out.println("ERROR: El ID del espacio no puede estar vacio.");
                    break;
                }
                
                ParkingSpaceLibrary space = parkingLot.getSpaceList().stream()
                        .filter(s -> s.getSpaceId().equalsIgnoreCase(spaceId))
                        .findFirst()
                        .orElse(null);

                if (space != null) {
                    System.out.println("\n--- INFORMACION DETALLADA DEL ESPACIO ---");
                    System.out.println("ID: " + space.getSpaceId());
                    System.out.println("Estado: " + (space.isOccupied() ? "OCUPADO" : "DISPONIBLE"));
                    System.out.println("Vehiculo: " + (space.isOccupied() && space.getVehiclePlate() != null ? space.getVehiclePlate() : "Ninguno"));
                    System.out.println("Asignado a: " + (space.getAssignedTo() != null ? space.getAssignedTo() : "Nadie"));
                    System.out.println("Tipo de residente: " + (space.getResidentType() != null ? space.getResidentType() : "Ninguno"));

                    if (space.isOccupied() && space.getVehiclePlate() != null) {
                        Resident owner = residentManager.findResidentByVehiclePlate(space.getVehiclePlate());
                        if (owner != null) {
                            System.out.println("\n--- PROPIETARIO REGISTRADO ---");
                            System.out.println("Nombre: " + owner.getName());
                            System.out.println("Apartamento: " + owner.getApartmentNumber());
                            System.out.println("Tipo: " + owner.getUserType());
                        } else {
                            System.out.println("\nVehiculo no registrado con ningun residente");
                        }
                    }

                    System.out.println("\n--- INFORMACION COMPLETA ---");
                    System.out.println(space.getSpaceInfo());

                } else {
                    System.out.println("No se encontro el espacio con ID: " + spaceId);
                }
                break;

            default:
                System.out.println("Opcion no valida");
        }
    } catch (Exception e) {
        System.out.println("Error en la gestion de espacios: " + e.getMessage());
        e.printStackTrace();
    }
    pausar();
}

   private static void manageFeature4_VerifyAuthorization() {
    System.out.println("\n--- [4] Verificar Autorizacion (Visitantes) ---");
    System.out.print("Ingrese el ID del Visitante: ");
    
    String visitorId = scanner.nextLine().trim();
    if (visitorId.isEmpty()) {
        System.out.println("ERROR: El ID del Visitante no puede estar vacio.");
        pausar();
        return;
    }
    
    Visitor visitor = visitorManager.findVisitorById(visitorId);
    
    if (visitor == null) {
        System.out.println("INFO: Visitante no registrado. Se creara un registro temporal.");
        System.out.print("Ingrese el nombre del Visitante: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("ERROR: El nombre no puede estar vacio para crear un registro temporal.");
            pausar();
            return;
        }
        
        visitor = new Visitor(visitorId, "TEMP-" + visitorId, name, "VISITOR", new Date(), null);
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
            System.out.print("Matricula: ");
            String plate = scanner.nextLine().toUpperCase();
            System.out.print("Color: ");
            String color = scanner.nextLine();
            System.out.print("Modelo: ");
            String model = scanner.nextLine();

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
                    System.out.print("Cuantos meses? (ej. 1): ");
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
        System.out.println("5. Reporte del Sistema Completo");
        System.out.print("Seleccione el reporte a generar: ");
        String opt = scanner.nextLine();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("INICIO DEL REPORTE");
        System.out.println("=".repeat(50));

        switch (opt) {
            case "1":
                System.out.println("=== REPORTE DE OCUPACION DE ESPACIOS ===");
                System.out.println("Total espacios: " + parkingLot.getTotalSpaces());
                System.out.println("Espacios disponibles: " + parkingLot.getAvailableSpaces());
                System.out.println("Espacios ocupados: " + (parkingLot.getTotalSpaces() - parkingLot.getAvailableSpaces()));
                System.out.println("\n--- DETALLE POR ESPACIOS ---");
                parkingLot.showDetailedSpacesStatus();
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

            case "5":
                System.out.println("=== REPORTE DEL SISTEMA COMPLETO ===");
                System.out.println("\n--- ESTADO DEL PARKING ---");
                System.out.println("Total espacios: " + parkingLot.getTotalSpaces());
                System.out.println("Disponibles: " + parkingLot.getAvailableSpaces());
                System.out.println("Ocupados: " + (parkingLot.getTotalSpaces() - parkingLot.getAvailableSpaces()));

                System.out.println("\n--- ESTADO DE RESIDENTES ---");
                System.out.println("Total residentes: " + residentManager.getTotalResidents());
                System.out.println("Con parking asignado: " + residentManager.getResidentsWithParking().size());
                System.out.println("Rotating: " + residentManager.getRotatingResidents().size());
                System.out.println("Con rental activo: " + residentManager.getRotatingResidentsWithRental().size());

                System.out.println("\n--- VEHICULOS ---");
                System.out.println("Total vehiculos: " + residentManager.getTotalVehicles());

                System.out.println("\n--- RENTALS ---");
                System.out.println("Rentals activos: " + residentManager.getAllActiveRentals().size());
                System.out.println("Rentals expirados: " + residentManager.getExpiredRentals().size());
                break;

            default:
                System.out.println("Opcion no valida.");
        }

        System.out.println("=".repeat(50));
        System.out.println("FIN DEL REPORTE");
        System.out.println("=".repeat(50));
        pausar();
    }

    private static void pausar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
