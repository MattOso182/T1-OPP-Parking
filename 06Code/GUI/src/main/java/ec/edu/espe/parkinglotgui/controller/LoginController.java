package ec.edu.espe.parkinglotgui.controller;

import ec.edu.espe.parkinglotgui.repository.UserRepository;

/**
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class LoginController {

    private final UserRepository userRepository;

    public LoginController() {
        this.userRepository = new UserRepository();
    }

    public boolean authenticate(String username, String password, String userType) {
        try {
            if ("Guardia de seguridad".equals(userType)) {
                return "admin".equals(username) && "123".equals(password);
                
            } else if ("Residente".equals(userType)) {
                return userRepository.findUserCredentials(username, password);
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
}