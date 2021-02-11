
package services;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailService {
    public static void sendEmail(final String recepient, String code) throws Exception{
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
	// En el video, esta el enlace para que puedas trabajar con el correo sin problemas.
        final String myAccountEmail = "INGRESA_TU_CORREO";
        final String password = "INGRESA_TU_PASSWORD";
        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password); 
            }
    });
        
             Message message = prepareMessage(session, myAccountEmail, recepient, code);
             Transport.send(message);
    }
    
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String code){
        try {
           
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Prueba de 2AuthCode");
            message.setText("Code es: "+code);
            return message;
        } catch (AddressException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
