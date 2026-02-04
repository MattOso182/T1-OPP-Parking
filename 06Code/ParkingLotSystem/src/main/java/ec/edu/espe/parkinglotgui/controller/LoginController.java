package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.repository.UserRepository;
import ec.edu.espe.parkinglotgui.utils.TwoFactorAuthService;

/**
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class LoginController {

    private final UserRepository userRepository;
    private final TwoFactorAuthService twoFactorService;
    private String currentResidentID;

    public LoginController() {
        this.userRepository = new UserRepository();
        this.twoFactorService = new TwoFactorAuthService();
    }

    public boolean validateCredentials(String username, String password, String userType) {
        try {
            if ("Guardia de seguridad".equals(userType)) {
                return "admin".equals(username) && "123".equals(password);
            } else if ("Residente".equals(userType)) {
                boolean isValid = userRepository.findUserCredentials(username, password);
                if (isValid) {
                    this.currentResidentID = username;
                }
                return isValid;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean requiresTwoFactorForResident() {
        if (currentResidentID == null) {
            return false;
        }
        boolean need2FA = twoFactorService.isResidentEligibleFor2FA(currentResidentID);
        return need2FA;
    }

    public boolean sendTwoFactorCode() {
        if (currentResidentID == null) {
            return false;
        }
        return twoFactorService.sendVerificationCode(currentResidentID);
    }

    public boolean validateTwoFactorCode(String code) {
        if (currentResidentID == null) {
            return false;
        }
        return twoFactorService.validateCode(currentResidentID, code);
    }
}