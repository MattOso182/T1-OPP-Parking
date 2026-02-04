package ec.edu.espe.parkinglotgui.utils;

/**
 *
 * @author Mateo Aymacaña, T.A.P. The Art Of Programming
 */

import ec.edu.espe.parkinglotgui.repository.ResidentRepository;
import ec.edu.espe.parkinglotgui.utils.EmailAuthenticator;
import ec.edu.espe.parkinglotgui.model.Resident;

public class TwoFactorAuthService {
    
    private final ResidentRepository residentRepository;
    private final EmailAuthenticator emailAuthenticator;
    
    public TwoFactorAuthService() {
        this.residentRepository = new ResidentRepository();
        this.emailAuthenticator = new EmailAuthenticator( );
    }
    
    public boolean isResidentEligibleFor2FA(String residentID) {
        if (residentID == null || residentID.trim().isEmpty()) {
            return false;
        }
        
        String email = getResidentEmail(residentID);
        return email != null && !email.isEmpty() && email.contains("@");
    }
    
    public boolean sendVerificationCode(String residentID) {
        Resident resident = getResidentWithName(residentID);
        
        if (resident == null) {
            System.err.println("Residente no encontrado: " + residentID);
            return false;
        }
        
        String email = resident.getEmail();
        String nombre = resident.getName();
        
        if (email == null || email.trim().isEmpty()) {
            System.err.println("Residente no tiene email: " + residentID);
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Usuario"; 
        }
        
        return emailAuthenticator.sendVerificationCode(email, nombre, residentID);
    }
    
    public boolean validateCode(String residentID, String code) {
        return ec.edu.espe.parkinglotgui.utils.EmailAuthenticator.validateCode(residentID, code);
    }
    
    private Resident getResidentWithName(String residentID) {
        try {
            return residentRepository.findById(residentID);
        } catch (Exception e) {
            System.err.println("Error obteniendo residente: " + e.getMessage());
            return null;
        }
    }
    
    private String getResidentEmail(String residentID) {
        try {
            Resident resident = residentRepository.findById(residentID);
            if (resident != null && resident.getEmail() != null) {
                String email = resident.getEmail().trim();
                return !email.isEmpty() ? email : null;
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo email de residente: " + e.getMessage());
        }
        return null;
    }
}
