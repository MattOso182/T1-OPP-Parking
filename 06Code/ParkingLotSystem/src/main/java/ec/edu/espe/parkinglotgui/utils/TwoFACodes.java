package ec.edu.espe.parkinglotgui.utils;

/**
 *
 * @author Mateo Aymacaña, T.A.P. The Art Of Programming
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class TwoFACodes {
     private static final Map<String, CodeInfo> codes = new ConcurrentHashMap<>();
    private static final long EXPIRATION_TIME = 5 * 60 * 1000; 
    
    private static class CodeInfo {
        String code;
        long timestamp;
        
        CodeInfo(String code) {
            this.code = code;
            this.timestamp = System.currentTimeMillis();
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > EXPIRATION_TIME;
        }
    }
    
    public static void saveCode(String username, String code) {
        codes.put(username, new CodeInfo(code));
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                codes.remove(username);
                System.out.println("Código expirado para: " + username);
            }
        }, EXPIRATION_TIME);
    }
    
    public static String getCode(String username) {
        CodeInfo info = codes.get(username);
        if (info != null && !info.isExpired()) {
            return info.code;
        }
        codes.remove(username);
        return null;
    }
    
    public static void removeCode(String username) {
        codes.remove(username);
    }
}
