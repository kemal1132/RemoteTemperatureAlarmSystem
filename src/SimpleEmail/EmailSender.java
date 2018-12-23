package SimpleEmail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
 
	public static void main(String args[]) throws AddressException, MessagingException {
		generateAndSendEmail("Test Subject", "Test Body <h1>Does it work</h1>","kemaldemirel1132@gmail.com");
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	}
 
	public static void generateAndSendEmail(String subject, String emailBodyHtml, String Recipient) throws AddressException, MessagingException {
 
		// Step1 Setting up mail server
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.socketFactory.port", "465");
		mailServerProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.prot", "465");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2 Creating email
		System.out.println("\n\n 2nd ===> creating e mail");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		
		
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(Recipient));
		
		generateMailMessage.setSubject(subject);
		generateMailMessage.setContent(emailBodyHtml, "text/html");
		
		
		
		System.out.println("Mail has been created has been created successfully..");
 
		// Step3 send mail
		System.out.println("\n\n 3rd ===> Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		transport.connect("smtp.gmail.com", "farmtempmanager", "kemalonline1132");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}