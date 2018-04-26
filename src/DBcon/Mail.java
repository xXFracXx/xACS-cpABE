package DBcon;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.*;

public class Mail {
	private static final String USER_NAME = "xacsmail";  // GMail user name (just the part before "@gmail.com")
    private static final String PASSWORD = "xacsmail1232!"; // GMail password
    public boolean secretMail(String msg, String name, String email, String type) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String to = email; // list of recipient email addresses
        String subject;
        if(type.equals("sk")){
            subject = "File Request: Approved!"; 
        } else if(type.equals("activate")){
            subject = "Welcome to xACS!";
        } else {
            subject = "xACS";
        }
        String body = msg;
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        MimeMessage message = new MimeMessage(session);
        //System.out.println("[DBcon\\Mail] Message: " + msg);
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress();
            toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            //message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("[DBcon\\Mail] Done Email");
            //return true;
        } catch (AddressException ae) {
            ae.printStackTrace();
            return false;
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
        return false;
    }

}
