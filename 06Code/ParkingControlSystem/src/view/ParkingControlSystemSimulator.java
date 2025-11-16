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
        scanner = new Scanner(System.in);

        boolean loggedIn = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (!loggedIn && attempts < MAX_ATTEMPTS) {
            System.out.println("=== SISTEMA DE GESTION DE PARQUEADEROS ===");
            System.out.print("Usuario: ");
            String username = scanner.nextLine().trim();

            System.out.print("Contrasenia: ");
            String password = scanner.nextLine().trim();

            if (username.equals("admin") && password.equals("123")) {
                loggedIn = true;
                System.out.println("\n¡Login exitoso! Bienvenido al sistema.\n");
            } else {
                attempts++;
                System.out.println("\nCredenciales incorrectas. Intentos restantes: " + (MAX_ATTEMPTS - attempts) + "\n");
            }
        }

        if (!loggedIn) {
            System.out.println("Demasiados intentos fallidos. Sistema bloqueado.");
            scanner.close();
            return;
        }

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
                    System.out.println("Total de vehiculos registrados: " + residentManager.getTotalVehicles());
                    System.out.println(residentManager.generateVehiclesReport());
                    break;
                case "2":
                    // 🚨 Opción 2 Implementada: Registrar nuevo residente + vehículo
                    registerNewResidentAndVehicle();
                    break;
                case "3":
                    
                    break;
                case "4":
                    manageFeature1RegistrationEntryExit();
                    break;
                case "5":
                    parkingLot.showSpacesStatus();
                    break;
                case "6":
                    manageFeature3AssignSpaceManagement();
                    break;
                case "7":
                    manageFeature4VerifyAuthorization();
                    break;
                case "8":
                    manageFeature5SearchVehicleLicensePlate();
                    break;
                case "9":
                    manageFeature6ValideteUpdateVehicle();
                    break;
                case "10":
                    manageFeature7ManageRentals();
                    break;
                case "11":
                    manageFeature8GenerateReports();
                    break;
                case "12":
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, intente de nuevo.");
            }
        }

        System.out.println("Cerrando el sistema...");
        guard.setOnDuty(false);

        parkingLot.saveToJson();
        System.out.println("Hasta luego...");
        scanner.close();
    }
    private static void registerNewResidentAndVehicle() {
        System.out.println("\n--- [2] Registrar Nuevo Residente + Vehiculo ---");

        System.out.println("--- DATOS DEL RESIDENTE ---");
        System.out.print("ID de Residente: ");
        String residentID = scanner.nextLine().trim().toUpperCase();

        if (residentManager.findResidentById(residentID) != null) {
            System.out.println("ERROR: Ya existe un residente con el ID " + residentID);
            pause();
            return;
        }

        System.out.print("Nombre completo: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Numero de Apartamento: ");
        String apartmentNumber = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Celular: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Tipo de Residente (1) Asignado / (2) Rotatorio: ");
        String typeOption = scanner.nextLine().trim();
        
        Resident newResident = null;
        String assignedParkingSpace = null;

        if (typeOption.equals("1")) {
            System.out.print("Espacio de Parqueo Asignado (ej. P-A-15): ");
            assignedParkingSpace = scanner.nextLine().trim().toUpperCase();
            newResident = new Resident(residentID, name, apartmentNumber, email, phone, assignedParkingSpace);
            System.out.println("Residente creado como tipo: ASIGNADO");
        } else if (typeOption.equals("2")) {
            newResident = new Resident(residentID, name, apartmentNumber, email, phone);
            System.out.println("Residente creado como tipo: ROTATORIO");
        } else {
            System.out.println("ERROR: Opcion de tipo de residente no valida. Cancelando registro.");
            pause();
            return;
        }

        System.out.println("\n--- DATOS DEL VEHICULO ---");
        System.out.print("Placa del Vehiculo: ");
        String plate = scanner.nextLine().trim().toUpperCase();

        if (residentManager.findVehicleByPlate(plate) != null) {
            System.out.println("ERROR: La placa " + plate + " ya está registrada en el sistema.");
            pause();
            return;
        }
        
        System.out.print("Color: ");
        String color = scanner.nextLine().trim();
        
        System.out.print("Modelo: ");
        String model = scanner.nextLine().trim();

        Vehicle newVehicle = new Vehicle(plate, color, model, residentID);
        
                newResident.addVehicle(newVehicle);

        if (residentManager.addResident(newResident)) {
            System.out.println("\n REGISTRO COMPLETO Y GUARDADO");
            System.out.println("Residente: " + name + " | Vehiculo: " + plate);
            
            if (newResident.getUserType() == UserType.WITH_PARKING && assignedParkingSpace != null) {
                boolean assigned = parkingLot.assignSpaceToVehicle(assignedParkingSpace, plate, newResident.getUserType().toString());
                if (assigned) {
                    System.out.println("Nota: Espacio " + assignedParkingSpace + " asignado y ocupado por el vehiculo.");
                } else {
                    System.out.println("ADVERTENCIA: No se pudo asignar el vehiculo al espacio de parqueo. Revise si el espacio existe o si esta ocupado.");
                }
            }
            
        } else {
            System.out.println(" ERROR: No se pudo agregar el residente.");
        }
        
        pause();
    }

    private static void showMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("   SISTEMA DE GESTION DE PARQUEADERO");
        System.out.println("=".repeat(30));
        System.out.println("1. Ver vehiculos registrados en el sistema");
        System.out.println("2. Registrar nuevo residente + vehiculo");
        System.out.println("3. Registrar visitante");
        System.out.println("4. Registrar ingreso/salida (Residentes/Visitantes)");
        System.out.println("5. Rastrear estado de espacios (Ocupacion)");
        System.out.println("6. Asignar y gestionar espacios de parqueo");
        System.out.println("7. Verificar autorizacion de usuarios/visitantes");
        System.out.println("8. Buscar y verificar vehiculo/placa");
        System.out.println("9. Validar y actualizar vehiculos de residentes");
        System.out.println("10. Gestionar alquileres y asignaciones temporales");
        System.out.println("11. Generar reportes y estadisticas");
        System.out.println("12. Salir");
        System.out.println("-".repeat(30));
        System.out.print("Seleccione una opcion: ");
    }

    private static void manageFeature1RegistrationEntryExit() {
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
                    pause();
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
        pause();
    }

    private static void manageFeature3AssignSpaceManagement() {
        System.out.println("\n--- [3] Gestionar Espacios de Parqueo ---");

        try {
            if (parkingLot == null) {
                System.out.println("Error: ParkingLot no esta inicializado");
                pause();
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
                pause();
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
        } catch (Exception exception) {
            System.out.println("Error en la gestion de espacios: " + exception.getMessage());
            exception.printStackTrace();
        }
        pause();
    }

    private static void manageFeature4VerifyAuthorization() {
        System.out.println("\n--- [4] Verificar Autorizacion (Visitantes) ---");
        System.out.print("Ingrese el ID del Visitante: ");

        String visitorId = scanner.nextLine().trim();
        if (visitorId.isEmpty()) {
            System.out.println("ERROR: El ID del Visitante no puede estar vacio.");
            pause();
            return;
        }

        Visitor visitor = visitorManager.findVisitorById(visitorId);

        if (visitor == null) {
            System.out.println("INFO: Visitante no registrado. Se creara un registro temporal.");
            System.out.print("Ingrese el nombre del Visitante: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("ERROR: El nombre no puede estar vacio para crear un registro temporal.");
                pause();
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
        pause();
    }

    private static void manageFeature5SearchVehicleLicensePlate() {
        System.out.println("\n--- [5] Buscar Vehiculo por Placa ---");
        System.out.print("Ingrese la placa a buscar: ");
        String plate = scanner.nextLine().toUpperCase().trim();

        if (plate.isEmpty()) {
            System.out.println("ERROR: La placa no puede estar vacia.");
            pause();
            return;
        }

        if (!plate.matches("^[A-Z]{3}-\\d{3,4}$")) {
            System.out.println("ERROR: Formato de placa invalido. Ejemplo: ABC-123 o ABC-1234");
            pause();
            return;
        }

        Vehicle vehicle = residentManager.findVehicleByPlate(plate);
        if (vehicle == null) {
            System.out.println("No se encontro ningun vehiculo con esa placa en el sistema.");
            pause();
            return;
        }

        System.out.println("\nVehiculo encontrado:");
        System.out.println(vehicle.getVehicleInfo());
        Resident owner = residentManager.findResidentByVehiclePlate(plate);
        if (owner != null) {
            System.out.println("\nPropietario Registrado:");
            System.out.println("ID: " + owner.getResidentID() + ", Nombre: " + owner.getName());
        } else {
            System.out.println("\nVehiculo sin propietario registrado.");
        }
        pause();
    }

    private static void manageFeature6ValideteUpdateVehicle() {
        System.out.println("\n--- [6] Gestionar Vehiculos de Residente ---");
        System.out.print("Ingrese el ID del Residente: ");
        String residentId = scanner.nextLine().trim();

        if (residentId.isEmpty()) {
            System.out.println("ERROR: El ID no puede estar vacio.");
            pause();
            return;
        }

        Resident resident = residentManager.findResidentById(residentId);
        if (resident == null) {
            System.out.println("Residente no encontrado.");
            pause();
            return;
        }

        System.out.println("Residente: " + resident.getName());
        System.out.println("Vehiculos actuales: " + resident.getVehicles().size());

        System.out.print("\nDesea (1) Anadir vehiculo o (2) Quitar vehiculo?: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1")) {
            System.out.print("Matricula: ");
            String plate = scanner.nextLine().toUpperCase().trim();
            System.out.print("Color: ");
            String color = scanner.nextLine().trim();
            System.out.print("Modelo: ");
            String model = scanner.nextLine().trim();

            if (plate.isEmpty() || color.isEmpty() || model.isEmpty()) {
                System.out.println("ERROR: Ningun campo puede estar vacio.");
                pause();
                return;
            }

            if (!plate.matches("^[A-Z]{3}-\\d{3,4}$")) {
                System.out.println("ERROR: Formato de placa invalido. Ejemplo: ABC-123 o ABC-1234");
                pause();
                return;
            }

            Vehicle newVehicle = new Vehicle(plate, color, model, residentId);
            boolean success = residentManager.addVehicleToResident(residentId, newVehicle);
            if (success) {
                System.out.println("Vehiculo agregado exitosamente");
            } else {
                System.out.println("No se pudo agregar - vehiculo ya existe");
            }

        } else if (opt.equals("2")) {
            System.out.print("Matricula del vehiculo a quitar: ");
            String plate = scanner.nextLine().toUpperCase().trim();

            if (plate.isEmpty()) {
                System.out.println("ERROR: La placa no puede estar vacia.");
                pause();
                return;
            }

            boolean success = residentManager.removeVehicleFromResident(residentId, plate);
            if (success) {
                System.out.println("Vehiculo removido exitosamente");
            } else {
                System.out.println("No se pudo remover - vehiculo no encontrado");
            }

        } else {
            System.out.println("Opcion no valida.");
        }
        pause();
    }

    private static void manageFeature7ManageRentals() {
        System.out.println("\n--- [7] Gestionar Alquileres Temporales ---");
        System.out.print("Ingrese el ID del Residente (tipo ROTATING): ");
        String residentId = scanner.nextLine();

        Resident resident = residentManager.findResidentById(residentId);
        if (resident == null) {
            System.out.println("Residente no encontrado.");
            pause();
            return;
        }

        if (resident.getUserType() == UserType.WITH_PARKING) {
            System.out.println("Este residente tiene un espacio asignado permanentemente. No requiere alquiler.");
            pause();
            return;
        }

        if (resident.hasActiveRental()) {
            System.out.println("\nEl residente tiene un alquiler activo:");
            System.out.println(resident.getCurrentRental().getRentalInfo());

            String opt = "";
            while (true) {
                System.out.print("Desea (1) Renovar, (2) Cancelar, (3) Procesar Pago: ");
                opt = scanner.nextLine().trim();

                if (opt.equals("1") || opt.equals("2") || opt.equals("3")) {
                    break;
                } else {
                    System.out.println("Opcion no valida. Por favor ingrese 1, 2 o 3.");
                }
            }

            if (opt.equals("1")) {
                int months = 0;
                boolean usuarioAcepta = false;
                while (true) {
                    System.out.print("Cuantos meses adicionales (1-12): ");
                    String mesesInput = scanner.nextLine().trim();

                    try {
                        months = Integer.parseInt(mesesInput);
                        if (months >= 1 && months <= 12) {
                            double total = months * 20.0;
                            System.out.println("El costo por " + months + " meses es: $" + total);

                            System.out.print("Esta de acuerdo con el pago? (s/n): ");
                            String confirmacion = scanner.nextLine().trim().toLowerCase();

                            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                                usuarioAcepta = true;
                                break;
                            } else {
                                System.out.println("Renovacion cancelada.");
                                break;
                            }
                        } else {
                            System.out.println("Los meses deben estar entre 1 y 12.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor ingrese un numero valido.");
                    }
                }

                if (usuarioAcepta) {
                    residentManager.renewRentalForResident(residentId, months);
                    System.out.println("Renovacion exitosa por " + months + " meses.");
                }

            } else if (opt.equals("2")) {
                while (true) {
                    System.out.print("Esta seguro que desea CANCELAR el rental (s/n): ");
                    String confirmacion = scanner.nextLine().trim().toLowerCase();

                    if (confirmacion.equals("s") || confirmacion.equals("si")) {
                        String spaceId = resident.getCurrentRental().getSpaceId();
                        boolean cancelado = residentManager.cancelRentalForResident(residentId);
                        if (cancelado) {
                            parkingLot.updateSpaceStatus(spaceId, "AVAILABLE");
                            System.out.println("Rental cancelado exitosamente.");
                            System.out.println("Espacio " + spaceId + " ha sido liberado.");
                        } else {
                            System.out.println("No se pudo cancelar el rental.");
                        }
                        break;
                    } else if (confirmacion.equals("n") || confirmacion.equals("no")) {
                        System.out.println("Operacion de cancelacion cancelada.");
                        break;
                    } else {
                        System.out.println("Por favor ingrese 's' para SI o 'n' para NO.");
                    }
                }

            } else if (opt.equals("3")) {
                boolean procesado = residentManager.processPaymentForRental(residentId);
                if (procesado) {
                    System.out.println("Pago procesado exitosamente.");
                } else {
                    System.out.println("No se pudo procesar el pago.");
                }
            }

        } else {
            System.out.println("\nEl residente no tiene un alquiler activo.");

            boolean volverAlMenu = false;

            while (!volverAlMenu) {
                System.out.print("Desea (1) Crear un nuevo alquiler o (2) Volver al menu: ");
                String crearRental = scanner.nextLine().trim();

                if (crearRental.equals("2")) {
                    System.out.println("Volviendo al menu principal.");
                    volverAlMenu = true;
                } else if (crearRental.equals("1")) {
                    System.out.println("Buscando espacio disponible para alquilar...");

                    ParkingSpaceLibrary rentalSpace = parkingLot.findAvailableSpace();

                    if (rentalSpace != null) {
                        System.out.println("Espacio encontrado: " + rentalSpace.getSpaceId());
                        double price = 30.0;
                        System.out.println("Precio mensual: $" + price);

                        int months = 0;
                        boolean usuarioAcepta = false;

                        while (true) {
                            System.out.print("Cuantos meses (1-24): ");
                            String mesesInput = scanner.nextLine().trim();

                            try {
                                months = Integer.parseInt(mesesInput);
                                if (months >= 1 && months <= 24) {
                                    double total = months * price;
                                    System.out.println("El costo total por " + months + " meses es: $" + total);

                                    System.out.print("Esta de acuerdo con el pago? (s/n): ");
                                    String confirmacion = scanner.nextLine().trim().toLowerCase();

                                    if (confirmacion.equals("s") || confirmacion.equals("si")) {
                                        usuarioAcepta = true;
                                        break;
                                    } else if (confirmacion.equals("n") || confirmacion.equals("no")) {
                                        System.out.println("Alquiler cancelado por el usuario.");
                                        break;
                                    } else {
                                        System.out.println("Opcion no valida. Por favor ingrese 's' para SI o 'n' para NO.");
                                        
                                    }
                                } else {
                                    System.out.println("Los meses deben estar entre 1 y 24.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Por favor ingrese un numero valido.");
                            }
                        }

                        if (usuarioAcepta) {
                            Rental nuevoRental = residentManager.createRentalForResident(residentId, rentalSpace.getSpaceId(), months, price);

                            if (nuevoRental != null) {
                                if (resident.getVehicles() != null && !resident.getVehicles().isEmpty()) {
                                    String vehiclePlate = resident.getVehicles().get(0).getPlate();
                                    parkingLot.assignSpaceToVehicle(rentalSpace.getSpaceId(), vehiclePlate, "Rental");
                                }

                                parkingLot.saveToJson();
                                System.out.println("Alquiler creado exitosamente:");
                                System.out.println(nuevoRental.getRentalInfo());
                            } else {
                                System.out.println("No se pudo crear el alquiler.");
                            }
                        } else {
                            System.out.println("Operación de alquiler cancelada.");
                        }

                        volverAlMenu = true;
                        break;

                    } else {
                        System.out.println("No hay espacios disponibles para alquilar en este momento.");
                        volverAlMenu = true;
                        break;
                    }
                } else {
                    System.out.println("Opcion no valida. Por favor ingrese 1 o 2.");

                }
            }
        }
        pause();
    }

    private static void manageFeature8GenerateReports() {
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
        pause();
    }

    private static void pause() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}