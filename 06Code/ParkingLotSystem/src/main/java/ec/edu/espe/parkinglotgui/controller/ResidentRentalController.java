package ec.edu.espe.parkinglotgui.controller;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import ec.edu.espe.parkinglotgui.model.Resident;
import ec.edu.espe.parkinglotgui.model.Rental;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ResidentRentalController {

    private ResidentController residentController;
    private ParkingSpaceController spaceController;
    private Resident currentResident;

    public ResidentRentalController() {
        this.residentController = new ResidentController();
        this.spaceController = new ParkingSpaceController();
    }

    public boolean isValidResidentId(String residentId) {
        return residentId != null && residentId.matches("^RES-\\d{3}$");
    }

    public boolean isRotatingResident(Resident resident) {
        return resident != null && "ROTATING".equals(resident.getUserType());
    }

    public boolean isValidSpaceSelection(String spaceText) {
        if (spaceText == null || spaceText.isEmpty()) {
            return false;
        }
        return !spaceText.equals("Selecciona un espacio...")
                && !spaceText.equals("Select space...")
                && !spaceText.equals("- Select -");
    }

    public boolean isValidMonthsSelection(String monthsText) {
        if (monthsText == null || monthsText.isEmpty()) {
            return false;
        }
        return !monthsText.equals("Selecciona tu tiempo de uso...")
                && !monthsText.equals("Select months...")
                && !monthsText.equals("- Select -");
    }

    public Object[] searchResident(String residentId) {
        Object[] result = new Object[4];

        if (!isValidResidentId(residentId)) {
            result[0] = false;
            result[1] = "Formato de ID inválido. Use RES-001";
            result[2] = Color.RED;
            return result;
        }

        try {
            currentResident = residentController.searchResidentById(residentId);

            if (currentResident == null) {
                result[0] = false;
                result[1] = "Residente no encontrado: " + residentId;
                result[2] = Color.RED;
                return result;
            }

            String userType = currentResident.getUserType();

            if ("WITH_PARKING".equals(userType)) {
                result[0] = false;
                result[1] = currentResident.getName() + " tiene estacionamiento permanente";
                result[2] = new Color(100, 0, 0);
                result[3] = currentResident;
                return result;
            }

            if (!"ROTATING".equals(userType)) {
                result[0] = false;
                result[1] = "Tipo de usuario no válido: " + userType;
                result[2] = Color.RED;
                return result;
            }

            String displayText = buildResidentDisplayInfo(currentResident);
            result[0] = true;
            result[1] = displayText;
            result[2] = new Color(0, 150, 0);
            result[3] = currentResident;

        } catch (Exception e) {
            result[0] = false;
            result[1] = "Error al buscar residente";
            result[2] = Color.RED;
        }

        return result;
    }

    private String buildResidentDisplayInfo(Resident resident) {
        StringBuilder info = new StringBuilder();
        info.append(resident.getName())
                .append(" | ID: ").append(resident.getResidentID())
                .append(" | Apt: ").append(resident.getApartmentNumber())
                .append(" | ROTANTE");

        if (resident.getCurrentRental() != null) {
            Rental rental = resident.getCurrentRental();
            info.append(" | Pago: ").append(rental.getPaymentStatus());

            if (rental.getSpaceId() != null && !rental.getSpaceId().isEmpty()) {
                info.append(" | Espacio: ").append(rental.getSpaceId());
            }
        }

        return info.toString();
    }

    public Object[] processPayment(String residentId) {
        Object[] result = new Object[2];

        try {
            Resident resident = residentController.searchResidentById(residentId);

            if (resident == null || resident.getCurrentRental() == null) {
                result[0] = false;
                result[1] = "El residente no tiene renta activa";
                return result;
            }

            if (!"PENDING".equalsIgnoreCase(resident.getCurrentRental().getPaymentStatus())) {
                result[0] = false;
                result[1] = "No hay pago pendiente";
                return result;
            }

            boolean success = residentController.updatePaymentStatusOnly(residentId, "PAID");

            if (success) {
                currentResident = residentController.searchResidentById(residentId);
                result[0] = true;
                result[1] = "Pago procesado exitosamente";
            } else {
                result[0] = false;
                result[1] = "Error al procesar el pago";
            }

        } catch (Exception e) {
            result[0] = false;
            result[1] = "Error: " + e.getMessage();
        }

        return result;
    }

    public Object[] processRentalRenewal(String residentId, String spaceId, int months) {
        Object[] result = new Object[4];

        try {
            if (months < 1 || months > 12) {
                result[0] = false;
                result[1] = "Seleccione entre 1 y 12 meses";
                return result;
            }

            Resident resident = residentController.searchResidentById(residentId);
            if (resident == null) {
                result[0] = false;
                result[1] = "Residente no encontrado";
                return result;
            }

            Rental currentRental = resident.getCurrentRental();
            if (currentRental == null) {
                result[0] = false;
                result[1] = "El residente no tiene renta activa";
                return result;
            }

            String currentStatus = currentRental.getPaymentStatus();
            String currentSpace = currentRental.getSpaceId();

            if (!"PAID".equalsIgnoreCase(currentStatus)
                    && !"RENTAL_CANCELED".equalsIgnoreCase(currentStatus)) {
                result[0] = false;
                result[1] = "No puede renovar. Estado actual: " + currentStatus;
                return result;
            }

            boolean isSameSpace = currentSpace != null && currentSpace.equals(spaceId);
            boolean success = false;
            String operationType = "";

            try {
                if ("RENTAL_CANCELED".equalsIgnoreCase(currentStatus)) {
                    success = residentController.activateRentalWithSpace(residentId, months, spaceId);

                } else if (isSameSpace) {
                    operationType = "Extender renta existente";
                    success = residentController.updateRentalDates(residentId, months);

                } else {

                    List<String> availableSpaces = spaceController.getAvailableSpaces();

                    if (!availableSpaces.contains(spaceId)) {
                        result[0] = false;
                        result[1] = "El espacio seleccionado ya no está disponible";
                        return result;
                    }
                    success = residentController.renewRentalWithSpace(residentId, months, spaceId);
                }


            } catch (Exception dbEx) {
                result[0] = false;
                result[1] = "Error en base de datos al " + operationType.toLowerCase() + ": " + dbEx.getMessage();
                dbEx.printStackTrace(); 
                return result;
            }

            if (success) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, months);

                double totalAmount = calculatePaymentAmount(months);

                result[0] = true;
                result[1] = "Renovación procesada exitosamente";
                result[2] = sdf.format(cal.getTime());
                result[3] = totalAmount;

                currentResident = residentController.searchResidentById(residentId);

            } else {
                result[0] = false;
                result[1] = "Error al procesar en la base de datos. Operación: " + operationType;
            }

        } catch (IllegalStateException e) {
            result[0] = false;
            result[1] = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            result[0] = false;
            result[1] = "Error en el proceso: " + e.getMessage();
            e.printStackTrace();
        }

        return result;
    }

    public Object[] cancelRental(String residentId) {
        Object[] result = new Object[3];

        try {
            Resident resident = residentController.searchResidentById(residentId);

            if (resident == null || resident.getCurrentRental() == null) {
                result[0] = false;
                result[1] = "No tiene renta activa";
                return result;
            }

            String currentStatus = resident.getCurrentRental().getPaymentStatus();
            String spaceId = resident.getCurrentRental().getSpaceId();

            if (!"PAID".equalsIgnoreCase(currentStatus)) {
                result[0] = false;
                result[1] = "Solo puede cancelar rentas pagadas. Estado actual: " + currentStatus;
                return result;
            }

            boolean success = residentController.cancelRental(residentId);

            if (success) {
                result[0] = true;
                result[1] = "Renta cancelada exitosamente";
                result[2] = spaceId;
                currentResident = residentController.searchResidentById(residentId);
            } else {
                result[0] = false;
                result[1] = "Error al cancelar";
            }

        } catch (Exception e) {
            result[0] = false;
            result[1] = "Error: " + e.getMessage();
        }

        return result;
    }

    public Object[] processPaymentAndRenewal(String residentId, String spaceId, int months) {
        Object[] result = new Object[4];

        try {
            Object[] paymentResult = processPayment(residentId);
            boolean paymentSuccess = (Boolean) paymentResult[0];

            if (!paymentSuccess) {
                return paymentResult;
            }

            Object[] renewalResult = processRentalRenewal(residentId, spaceId, months);
            boolean renewalSuccess = (Boolean) renewalResult[0];

            if (renewalSuccess) {
                result[0] = true;
                result[1] = "Pago y renovación completados";
                result[2] = renewalResult[2];
                result[3] = renewalResult[3];
            } else {
                result[0] = false;
                result[1] = "Pago exitoso, pero error en renovación: " + renewalResult[1];
            }

        } catch (Exception e) {
            result[0] = false;
            result[1] = "Error en el proceso: " + e.getMessage();
        }

        return result;
    }

    public int extractMonthsFromText(String monthsText) {
        if (monthsText == null) {
            return 1;
        }
        String numbersOnly = monthsText.replaceAll("[^0-9]", "");
        if (!numbersOnly.isEmpty()) {
            try {
                int months = Integer.parseInt(numbersOnly);
                return Math.max(1, Math.min(months, 12));
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }

    public List<String> getAvailableSpaces() {
        return spaceController.getAvailableSpaces();
    }

    public String getSpaceDetails(String spaceId) {
        org.bson.Document details = spaceController.getSpaceDetails(spaceId);
        if (details != null) {
            String type = details.getString("type");
            Boolean occupied = details.getBoolean("isOccupied");
            return "Tipo: " + type + ", Estado: " + (occupied != null && occupied ? "OCUPADO" : "DISPONIBLE");
        }
        return "";
    }

    public Resident getCurrentResident() {
        return currentResident;
    }

    public boolean[] getButtonStates(Resident resident, boolean validSpace, boolean validMonths) {
        boolean[] states = new boolean[4];

        if (resident == null || !"ROTATING".equals(resident.getUserType())) {
            return states;
        }

        Rental rental = resident.getCurrentRental();
        String status = rental != null ? rental.getPaymentStatus() : "NO_RENTAL";

        switch (status.toUpperCase()) {
            case "PENDING":
                states[0] = true;
                states[1] = validSpace && validMonths;
                break;

            case "PAID":
                states[2] = validSpace && validMonths;
                states[3] = true;
                break;

            case "NO_RENTAL":
            case "RENTAL_CANCELED":
                states[2] = validSpace && validMonths;
                break;
        }

        return states;
    }

    public double calculatePaymentAmount(int months) {
        return 45.00 * months;
    }

    public String calculateEndDate(int months) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        return sdf.format(cal.getTime());
    }

}
