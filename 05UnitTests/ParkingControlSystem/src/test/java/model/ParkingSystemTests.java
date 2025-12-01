package model;

import utils.JsonDataManager;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSystemTests {

    private ResidentManager residentManager;
    private ParkingLot parkingLot;
    private JsonDataManager dataManager;
    private VisitorManager visitorManager;
    private ParkingControlSystem controlSystem;
    private SecurityGuard securityGuard;
    private EntryExitRecord entryExitRecord;
    
    private Resident resident1_Assigned;
    private Resident resident2_Rotating;
    private Visitor visitor1;
    
    private final String RES_ID_1 = "RES-101";
    private final String RES_ID_2 = "RES-202";
    private final String VIS_ID_1 = "VIS-333";
    private final String ASSIGNED_SPACE = "P-A-10";
    private final String ROTATING_SPACE = "R-A-01";
    private final String PLATE_RESIDENT = "ABC-123";
    private final String PLATE_VISITOR = "XYZ-789";

    @BeforeEach
    public void setUp() {
        dataManager = new JsonDataManager();
        parkingLot = new ParkingLot("TestLot");
        residentManager = new ResidentManager();
        visitorManager = new VisitorManager(dataManager);
        entryExitRecord = new EntryExitRecord();
        
        controlSystem = new ParkingControlSystem("PCS-T", parkingLot, residentManager);
        
        securityGuard = new SecurityGuard(
            "GUARD-01", "Guard Test", "DIA", "555-0000",
            controlSystem, entryExitRecord, residentManager, visitorManager
        );
        securityGuard.setOnDuty(true); 

        residentManager.getAllResidents().clear();
        visitorManager.getAllVisitors().clear();
        parkingLot.freeSpaceAndSync(ASSIGNED_SPACE);
        parkingLot.freeSpaceAndSync(ROTATING_SPACE);
        
        resident1_Assigned = new Resident(RES_ID_1, "Ana Gomez", "1A", "ana@res.com", "111", ASSIGNED_SPACE);
        resident1_Assigned.addVehicle(new Vehicle(PLATE_RESIDENT, "Rojo", "Sedan", RES_ID_1));
        residentManager.addResident(resident1_Assigned);
        
        parkingLot.assignSpaceToVehicle(ASSIGNED_SPACE, PLATE_RESIDENT, resident1_Assigned.getUserType().name());

        resident2_Rotating = new Resident(RES_ID_2, "Juan Perez", "2B", "juan@res.com", "222");
        residentManager.addResident(resident2_Rotating);
        
        visitor1 = new Visitor(VIS_ID_1, VIS_ID_1, "Visitante A", PLATE_VISITOR, null, null);
        visitorManager.addVisitor(visitor1);
    }
    
    @AfterEach
    public void tearDown() { }

    // =======================================================
    // 25 CASOS MALOS (CP01 - CP25)
    // =======================================================

    @Test @DisplayName("CP01: [FALLA NP] Agregar residente con ID completamente nulo")
    void testCP01_AddResidentWithNullId() {
        Resident res = new Resident(null, "Test", "1A", "t@t.com", "111", ASSIGNED_SPACE);
        assertThrows(NullPointerException.class, () -> residentManager.addResident(res), 
                     "CP01 FALLA: El código no manejo ID nulo y debería haber lanzado NP.");
    }

    @Test @DisplayName("CP02: [FALLA LÓGICA] Asignar espacio con spaceId que no existe")
    void testCP02_AssignSpaceWithNonExistentSpaceId() {
        assertFalse(parkingLot.assignSpaceToVehicle("NON-EXISTENT", "TEST-PLATE", "Visitor"),
                    "CP02 FALLA: El sistema aceptó la asignación a un espacio inexistente.");
    }

    @Test @DisplayName("CP03: [FALLA LÓGICA] Verificar autorizacion con placa de caracteres especiales")
    void testCP03_VerifyAuthWithSpecialCharactersPlate() {
        assertFalse(securityGuard.verifyAuthorization("AB!C-123"), 
                    "CP03 FALLA: La autorización se concedió con una placa inválida.");
    }

    @Test @DisplayName("CP04: [FALLA LÓGICA] Salida con fecha anterior a la entrada")
    void testCP04_ExitWithTimeBeforeEntry() {
        Date entryTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        Date exitTimeBefore = cal.getTime();

        entryExitRecord.registerEntry(PLATE_RESIDENT, entryTime);
        entryExitRecord.registerExit(PLATE_RESIDENT, exitTimeBefore);
        
        assertNull(entryExitRecord.getExitTime(), "CP04 FALLA: El sistema registró la salida a pesar de ser anterior a la entrada.");
    }

    @Test @DisplayName("CP05: [FALLA LÓGICA] Buscar residente con ID vacio")
    void testCP05_FindResidentWithEmptyId() {
        assertNull(residentManager.findResidentById(""), "CP05 FALLA: El sistema encontró un residente con ID vacío.");
    }

    @Test @DisplayName("CP06: [FALLA LÓGICA] Validar placa con solo espacios en blanco")
    void testCP06_ValidatePlateWithOnlySpaces() {
        Vehicle v = new Vehicle("   ", "Negro", "Sedan", RES_ID_1);
        assertFalse(v.validatePlate(), "CP06 FALLA: El sistema marcó la placa como válida.");
    }

    @Test @DisplayName("CP07: [FALLA LÓGICA] Renovar alquiler por 0 meses adicionales")
    void testCP07_RenewRentalZeroMonths() {
        residentManager.createRentalForResident(RES_ID_2, ROTATING_SPACE, 1, 30.0);
        assertFalse(resident2_Rotating.getCurrentRental().renewRental(0), 
                    "CP07 FALLA: El sistema aceptó la renovación por 0 meses.");
    }

    @Test @DisplayName("CP08: [FALLA LÓGICA] Registrar entrada de residente eliminado")
    void testCP08_RegisterEntryOfRemovedResident() {
        String plateToRemove = PLATE_RESIDENT;
        residentManager.removeResident(RES_ID_1);
        assertFalse(securityGuard.verifyAuthorization(plateToRemove), 
                    "CP08 FALLA: El sistema autorizó la entrada de un residente eliminado.");
    }

    @Test @DisplayName("CP09: [FALLA NP] Agregar visitante con visitorID nulo")
    void testCP09_AddVisitorWithNullId() {
        Visitor v = new Visitor(null, "TEMP", "Null", "NULL-0", null, null);
        assertThrows(NullPointerException.class, () -> visitorManager.addVisitor(v), 
                     "CP09 FALLA: El código aceptó un ID nulo sin lanzar NP.");
    }

    @Test @DisplayName("CP10: [FALLA LÓGICA] Liberar espacio de residente WITH_PARKING (permanente)")
    void testCP10_FreeAssignedSpace() {
        // Este test pasa si el código es débil y lo permite, exponiendo la falla de negocio.
        assertTrue(parkingLot.freeSpaceAndSync(ASSIGNED_SPACE), 
                   "CP10 ESPERA FALLA: El sistema liberó el espacio permanente (debilidad de negocio).");
    }

    @Test @DisplayName("CP11: [FALLA NP] Remover vehiculo pasando placa nula")
    void testCP11_RemoveVehicleWithNullPlate() {
        assertThrows(NullPointerException.class, () -> resident1_Assigned.removeVehicle(null), 
                     "CP11 FALLA: El código aceptó placa nula sin lanzar NP.");
    }

    @Test @DisplayName("CP12: [FALLA SEGURIDAD] Calcular duracion con tiempos nulos")
    void testCP12_CalculateDurationWithNullTimes() {
        assertDoesNotThrow(() -> entryExitRecord.calculateDuration(), 
                           "CP12 FALLA: El método lanzó una excepción en lugar de manejar el nulo de forma segura.");
        assertTrue(entryExitRecord.calculateDuration() >= 0.0, "CP12 FALLA: La duración fue negativa o imposible.");
    }

    @Test @DisplayName("CP13: [FALLA LÓGICA] Registrar entrada con sistema de control inactivo")
    void testCP13_RegisterEntryControlSystemInactive() {
        controlSystem.stopSystem(); 
        ParkingControlSystem pcs = new ParkingControlSystem("PCS-INACT", parkingLot, residentManager);
        assertFalse(pcs.registerEntry(PLATE_RESIDENT), "CP13 FALLA: El sistema permitió la entrada estando inactivo.");
    }

    @Test @DisplayName("CP14: [FALLA LÓGICA] Crear alquiler con cantidad negativa de meses")
    void testCP14_CreateRentalWithNegativeMonths() {
        assertNull(residentManager.createRentalForResident(RES_ID_2, ROTATING_SPACE, -5, 30.0),
                   "CP14 FALLA: El sistema permitió la creación de un alquiler con meses negativos.");
    }

    @Test @DisplayName("CP15: [FALLA SEGURIDAD] Sincronizar estado con spaceId invalido/inexistente")
    void testCP15_SyncSpaceStatusWithInvalidId() {
        assertDoesNotThrow(() -> parkingLot.syncSpaceStatus("ID-INVALIDO", true),
                           "CP15 FALLA: El método lanzó una excepción al buscar un ID inexistente.");
    }

    @Test @DisplayName("CP16: [FALLA LÓGICA] Eliminar residente que no existe")
    void testCP16_RemoveNonExistentResident() {
        assertFalse(residentManager.removeResident("RES-NON-EXIST"), "CP16 FALLA: El sistema reportó que eliminó un residente inexistente.");
    }

    @Test @DisplayName("CP17: [FALLA SEGURIDAD] Establecer codigo de provincia con mas de 4 letras")
    void testCP17_SetProvinceCodeTooLong() {
        LicensePlate lp = new LicensePlate(PLATE_RESIDENT, new Date(), "PICH", "Car");
        assertDoesNotThrow(() -> lp.setProvince("LONGER"), "CP17 FALLA: El método lanzó una excepción al aceptar un código largo.");
    }

    @Test @DisplayName("CP18: [FALLA LÓGICA] Asignar pase temporal sin espacios rotatorios disponibles")
    void testCP18_AssignPassNoRotatingSpace() {
        parkingLot.assignSpaceToVehicle(ROTATING_SPACE, "TEMP-SAT", "Visitor");
        
        assertFalse(visitor1.assignTemporaryPass(), "CP18 FALLA: El sistema asignó un pase sin tener espacios libres.");
    }

    @Test @DisplayName("CP19: [FALLA LÓGICA] Registrar entrada con placa extremadamente larga")
    void testCP19_RegisterEntryExtremelyLongPlate() {
        String longPlate = "A".repeat(500);
        assertFalse(controlSystem.registerEntry(longPlate), "CP19 FALLA: El sistema intentó procesar una placa inválida y excesivamente larga.");
    }

    @Test @DisplayName("CP20: [FALLA NP] Establecer fecha de fin de alquiler nula")
    void testCP20_SetRentalEndDateNull() {
        Rental r = new Rental("R-TEST", RES_ID_2, ROTATING_SPACE, new Date(), new Date(), 20.0);
        assertThrows(NullPointerException.class, () -> r.setEndDate(null), 
                     "CP20 FALLA: El código aceptó una fecha nula sin lanzar NP.");
    }

    @Test @DisplayName("CP21: [FALLA NP] Autorizar visitante con visitorID nulo")
    void testCP21_AuthorizeVisitorWithNullId() {
        assertThrows(NullPointerException.class, () -> resident1_Assigned.authorizeVisitor(null),
                     "CP21 FALLA: El código aceptó un ID nulo sin lanzar NP.");
    }

    @Test @DisplayName("CP22: [FALLA LÓGICA] Registrar salida usando placa nula")
    void testCP22_RegisterExitWithNullPlate() {
        assertFalse(controlSystem.registerExit(null), "CP22 FALLA: El sistema intentó procesar una placa nula.");
    }

    @Test @DisplayName("CP23: [FALLA LÓGICA] Salida con vehículo en espacio ya liberado manualmente")
    void testCP23_RegisterExitSpaceAlreadyFreed() {
        parkingLot.freeSpaceAndSync(ASSIGNED_SPACE);
        
        assertFalse(controlSystem.registerExit(PLATE_RESIDENT), 
                    "CP23 FALLA: El sistema reportó una salida exitosa sin encontrar el vehículo.");
    }

    @Test @DisplayName("CP24: [FALLA NP] Desautorizar visitante con visitorID nulo")
    void testCP24_RemoveAuthorizedVisitorWithNullId() {
        assertThrows(NullPointerException.class, () -> resident1_Assigned.removeAuthorizedVisitor(null),
                     "CP24 FALLA: El código aceptó un ID nulo sin lanzar NP.");
    }

    @Test @DisplayName("CP25: [FALLA LÓGICA] Buscar espacio disponible en un lote con 0 espacios")
    void testCP25_FindAvailableSpaceInEmptyLot() {
        parkingLot.assignSpaceToVehicle(ROTATING_SPACE, "SAT-1", "Visitor");
        
        if (parkingLot.getTotalSpaces() == 2) {
            assertNull(parkingLot.findAvailableSpace(), "CP25 FALLA: El sistema encontró un espacio disponible en un lote lleno.");
        }
    }
}