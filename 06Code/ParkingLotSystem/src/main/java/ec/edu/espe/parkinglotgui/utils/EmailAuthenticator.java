package ec.edu.espe.parkinglotgui.utils;

/**
 *
 * @author Mateo Aymacaña, T.A.P. The Art Of Programming
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.security.SecureRandom;

public class EmailAuthenticator {

    private final String emailFrom = "cevallosmateo07@gmail.com";
    private final String smtpUsername = "a22ea2001@smtp-brevo.com";
    private final String smtpPassword = "xsmtpsib-ad2163c16d3e2b73959e50f46dd8531f907fe4ae9d1f1e0a61c493b211fe4975-lb7U7kJJ1CYkVaqP";

    public EmailAuthenticator() {
    }

    public String generateCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }

    public boolean sendVerificationCode(String emailTo, String name, String username) {
        try {
            String verificationCode = generateCode();
            TwoFACodes.saveCode(username, verificationCode);

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp-relay.brevo.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp-relay.brevo.com");

            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUsername, smtpPassword);
                }
            });

            session.setDebug(false);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom, "ParkingLot System"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("🔐 Código de Verificación - ParkingLot System");

            String htmlContent = createEmailHTML(name, verificationCode);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);

            return true;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    private String createEmailHTML(String name, String code) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset='UTF-8'></head>"
                + "<body style='font-family: Arial, sans-serif;'>"
                + "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>"
                + "<h2 style='color: #2c3e50;'>ParkingLot System</h2>"
                + "<p>Estimado/a <strong>" + name + "</strong>,</p>"
                + "<p>Se ha solicitado acceso a su cuenta. Su código de verificación es:</p>"
                + "<div style='font-size: 32px; font-weight: bold; text-align: center; "
                + "background: #f8f9fa; padding: 20px; margin: 20px 0; border-radius: 5px; "
                + "border-left: 4px solid #3498db;'>"
                + code
                + "</div>"
                + "<p>Este código es válido por <strong>5 minutos</strong>.</p>"
                + "<p style='color: #7f8c8d; font-size: 12px; margin-top: 30px; padding-top: 20px; "
                + "border-top: 1px solid #eee;'>"
                + "Si no reconoce esta actividad, por favor contacte al administrador.<br>"
                + "© " + java.time.Year.now() + " ParkingLot System - ESPE"
                + "</p>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    public static boolean validateCode(String username, String enteredCode) {
        String savedCode = TwoFACodes.getCode(username);
        boolean isValid = savedCode != null && savedCode.equals(enteredCode);

        if (isValid) {
            System.out.println("Código válido para: " + username);
        } else {
            System.out.println("Código inválido para: " + username);
        }

        return isValid;
    }
}
