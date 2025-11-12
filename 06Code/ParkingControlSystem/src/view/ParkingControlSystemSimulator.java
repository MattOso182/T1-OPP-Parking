package view;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */


import model.*; // Importa todas las clases de tu paquete model
import java.util.Date;
import java.util.Scanner;
import parkingcontrolsystem.library.ParkingSpaceLibrary;

/**
 * Clase principal que ejecuta el Sistema de Control de Parqueadero.
 * Inicializa los gestores y muestra el menú principal al usuario.
 * * @author T.A.P (The Art of Programming) - (Ensamblado por Gemini)
 */
public class ParkingControlSystemSimulator {

    // --- Gestores y Controladores del Sistema ---
    private static Scanner scanner;
    private static ResidentManager residentManager;
    private static VisitorManager visitorManager;
    private static ParkingLot parkingLot;
    private static ParkingControlSystem controlSystem;
    private static EntryExitRecord logbook;
    private static SecurityGuard guard; // El actor principal para el control de acceso

    /**
     * Punto de entrada principal de la aplicación.
     */
    public static void main(String[] args) {
        // --- 1. Inicialización de Componentes ---
        System.out.println("Iniciando Sistema de Parqueadero...");
        
        scanner = new Scanner(System.in);
        
        // Carga residentes, vehículos y alquileres desde residents_data.json
        residentManager = new ResidentManager();
        
        // Carga la estructura física del parqueadero desde parking_data.json
        parkingLot = new ParkingLot("MainLot"); 
        
        // Inicializa los sistemas de control
        visitorManager = new VisitorManager();
        controlSystem = new ParkingControlSystem("PCS-01", parkingLot);
        logbook = new EntryExitRecord(); // Un registro general de eventos
        
        // Crea el Guardia de Seguridad, dándole acceso a todos los sistemas
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
        
        // El guardia debe estar "de turno" para operar
        guard.setOnDuty(true);
        
        System.out.println("\n¡Sistema cargado y listo!");
        System.out.println(" Guardia " + guard.getName() + " está en servicio.");
        System.out.println(" (" + residentManager.getTotalResidents() + " residentes cargados)");
        System.out.println(" (" + parkingLot.getTotalSpaces() + " espacios de parqueo cargados)");

        // --- 2. Bucle del Menú Principal ---
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    gestionarFeature1_RegistroIngresoSalida();
                    break;
                case "2":
                    gestionarFeature2_RastrearEstadoEspacios();
                    break;
                case "3":
                    gestionarFeature3_AsignarGestionarEspacio();
                    break;
                case "4":
                    gestionarFeature4_VerificarAutorizacion();
                    break;
                case "5":
                    gestionarFeature5_BuscarVehiculoMatricula();
                    break;
                case "6":
                    gestionarFeature6_ValidarActualizarVehiculo();
                    break;
                case "7":
                    gestionarFeature7_GestionarAlquileres();
                    break;
                case "8":
                    gestionarFeature8_GenerarReportes();
                    break;
                case "9":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }

        // --- 3. Apagado del Sistema ---
        System.out.println("Cerrando el sistema...");
        guard.setOnDuty(false);
        // Tus gestores ya guardan en JSON en cada operación,
        // pero un guardado final por si acaso es buena práctica.
        parkingLot.saveToJson();
        System.out.println("¡Hasta luego!");
        scanner.close();
    }

    /**
     * Muestra el menú principal de 8 opciones en la consola.
     */
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("   SISTEMA DE GESTIÓN DE PARQUEADERO");
        System.out.println("=".repeat(30));
        System.out.println("1. Registrar ingreso/salida (Residentes/Visitantes)");
        System.out.println("2. Rastrear estado de espacios (Ocupación)");
        System.out.println("3. Asignar y gestionar espacios de parqueo");
        System.out.println("4. Verificar autorización de usuarios/visitantes");
        System.out.println("5. Buscar y verificar vehículo/matrícula");
        System.out.println("6. Validar y actualizar vehículos de residentes");
        System.out.println("7. Gestionar alquileres y asignaciones temporales");
        System.out.println("8. Generar reportes y estadísticas");
        System.out.println("9. Salir");
        System.out.println("-".repeat(30));
        System.out.print("Seleccione una opción: ");
    }

    // --- MÉTODOS PARA CADA FEATURE ---

    /**
     * FEATURE 1: Llama al SecurityGuard para registrar una entrada o salida.
     * El guardia se encarga de verificar autorización antes de actuar.
     */
    private static void gestionarFeature1_RegistroIngresoSalida() {
        System.out.println("\n--- [1] Registrar Ingreso/Salida ---");
        System.out.print("¿Va a (1) Registrar Ingreso o (2) Registrar Salida?: ");
        String tipo = scanner.nextLine();
        System.out.print("Ingrese la matrícula del vehículo: ");
        String plate = scanner.nextLine().toUpperCase();

        if (tipo.equals("1")) {
            System.out.println("Procesando INGRESO para " + plate + "...");
            // El guardia maneja la lógica de autorización y registro
            guard.registerEntry(plate, new Date());
        } else if (tipo.equals("2")) {
            System.out.println("Procesando SALIDA para " + plate + "...");
            // El guardia maneja la lógica de salida
            guard.registerExit(plate, new Date());
        } else {
            System.out.println("Opción no válida.");
        }
        pausar();
    }

    /**
     * FEATURE 2: Muestra el reporte de ocupación del ParkingControlSystem.
     */
    private static void gestionarFeature2_RastrearEstadoEspacios() {
        System.out.println("\n--- [2] Estado de Espacios (Disponibles/Ocupados) ---");
        // El controlSystem tiene el reporte más completo
        System.out.println(controlSystem.generateReport());
        pausar();
    }

    /**
     * FEATURE 3: Muestra información de un espacio específico.
     * La asignación es automática en la Feature 1.
     */
    private static void gestionarFeature3_AsignarGestionarEspacio() {
        System.out.println("\n--- [3] Gestionar Espacios de Parqueo ---");
        System.out.println("La asignación es automática al registrar un ingreso (Opción 1).");
        System.out.println("Para ver el detalle de un espacio específico:");
        System.out.print("Ingrese el ID del espacio (ej. A-101): ");
        String spaceId = scanner.nextLine().toUpperCase();

        ParkingSpaceLibrary space = parkingLot.getSpaceList().stream()
                .filter(s -> s.getSpaceId().equalsIgnoreCase(spaceId))
                .findFirst()
                .orElse(null);

        if (space != null) {
            System.out.println("Información del Espacio:");
            System.out.println(space.getSpaceInfo());
        } else {
            System.out.println("No se encontró el espacio con ID: " + spaceId);
        }
        pausar();
    }

    /**
     * FEATURE 4: Verifica si un visitante está autorizado por un residente.
     */
    private static void gestionarFeature4_VerificarAutorizacion() {
        System.out.println("\n--- [4] Verificar Autorización (Visitantes) ---");
        System.out.print("Ingrese el ID del Visitante (ej. Cédula): ");
        String visitorId = scanner.nextLine();

        // Revisa si el visitante ya está en el sistema
        Visitor visitor = visitorManager.findVisitorById(visitorId);
        if (visitor == null) {
            System.out.print("Visitante no registrado. Ingrese el nombre: ");
            
        }

        // Usamos el método de alto nivel del ResidentManager para procesar al visitante
        boolean isAuthorized = residentManager.processVisitorEntry(visitor);

        if (isAuthorized) {
            System.out.println("-> RESULTADO: VISITANTE AUTORIZADO Y PASE TEMPORAL ASIGNADO.");
        } else {
            System.out.println("-> RESULTADO: VISITANTE NO AUTORIZADO.");
            System.out.println(" (Un residente debe autorizarlo primero)");
        }
        pausar();
    }

    /**
     * FEATURE 5: Busca un vehículo en la base de datos de residentes.
     */
    private static void gestionarFeature5_BuscarVehiculoMatricula() {
        System.out.println("\n--- [5] Buscar Vehículo por Matrícula ---");
        System.out.print("Ingrese la matrícula a buscar: ");
        String plate = scanner.nextLine().toUpperCase();

        // 1. Validar formato usando LicensePlate (aunque no esté vinculado)
        LicensePlate lp = new LicensePlate(plate, new Date(), "N/A", "N/A");
        System.out.println("Formato de matrícula: " + (lp.validateFormat() ? "VÁLIDO" : "INVÁLIDO"));

        // 2. Buscar el vehículo en el ResidentManager
        Vehicle vehicle = residentManager.findVehicleByPlate(plate);

        if (vehicle != null) {
            System.out.println("\nVehículo encontrado:");
            System.out.println(vehicle.getVehicleInfo());

            // 3. Buscar al propietario
            Resident owner = residentManager.findResidentByVehiclePlate(plate);
            System.out.println("\nPropietario Registrado:");
            System.out.println("ID: " + owner.getResidentID() + ", Nombre: " + owner.getName());
        } else {
            System.out.println("\nNo se encontró ningún vehículo con esa matrícula en el sistema.");
        }
        pausar();
    }

    /**
     * FEATURE 6: Añade o elimina vehículos de un residente.
     */
    private static void gestionarFeature6_ValidarActualizarVehiculo() {
        System.out.println("\n--- [6] Gestionar Vehículos de Residente ---");
        System.out.print("Ingrese el ID del Residente: ");
        String residentId = scanner.nextLine();

        Resident resident = residentManager.findResidentById(residentId);
        if (resident == null) {
            System.out.println("Residente no encontrado.");
            pausar();
            return;
        }

        System.out.println("Residente: " + resident.getName());
        System.out.println("Vehículos actuales:");
        resident.getVehicles().forEach(v -> System.out.println(" - " + v.getVehicleInfo()));

        System.out.print("\n¿Desea (1) Añadir vehículo o (2) Quitar vehículo?: ");
        String opt = scanner.nextLine();

        if (opt.equals("1")) {
            System.out.print("Matrícula: "); String plate = scanner.nextLine().toUpperCase();
            System.out.print("Color: "); String color = scanner.nextLine();
            System.out.print("Modelo: "); String model = scanner.nextLine();
            
            Vehicle newVehicle = new Vehicle(plate, color, model, residentId);
            residentManager.addVehicleToResident(residentId, newVehicle);
        } else if (opt.equals("2")) {
            System.out.print("Matrícula del vehículo a quitar: ");
            String plate = scanner.nextLine().toUpperCase();
            residentManager.removeVehicleFromResident(residentId, plate);
        }
        System.out.println("Lista de vehículos actualizada.");
        pausar();
    }

    /**
     * FEATURE 7: Crea, renueva o cancela alquileres para residentes ROTATING.
     */
    private static void gestionarFeature7_GestionarAlquileres() {
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
                System.out.print("¿Cuántos meses adicionales? ");
                int months = Integer.parseInt(scanner.nextLine());
                residentManager.renewRentalForResident(residentId, months);
            } else if (opt.equals("2")) {
                String spaceId = resident.getCurrentRental().getSpaceId();
                residentManager.cancelRentalForResident(residentId);
                // ¡Importante! Liberar el espacio en el parqueadero
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
                    System.out.print("¿Cuántos meses? (ej. 1): ");
                    int months = Integer.parseInt(scanner.nextLine());
                    
                    // 1. Crea el alquiler en el ResidentManager
                    residentManager.createRentalForResident(residentId, rentalSpace.getSpaceId(), months, price);
                    
                    // 2. Ocupa el espacio en el ParkingLot
                    
                    parkingLot.saveToJson(); // Guarda el cambio de estado del espacio
                    
                } else {
                    System.out.println("No hay espacios disponibles para alquilar en este momento.");
                }
            }
        }
        pausar();
    }

    /**
     * FEATURE 8: Muestra un sub-menú para elegir qué reporte generar.
     */
    private static void gestionarFeature8_GenerarReportes() {
        System.out.println("\n--- [8] Generación de Reportes ---");
        System.out.println("1. Reporte de Ocupación de Espacios");
        System.out.println("2. Reporte de Residentes");
        System.out.println("3. Reporte de Alquileres Activos");
        System.out.println("4. Reporte de Vehículos");
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
                System.out.println(residentManager.generateVehiclesReport());
                break;
            default:
                System.out.println("Opción no válida.");
        }
        System.out.println("--- FIN DEL REPORTE ---");
        pausar();
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter.
     */
    private static void pausar() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}