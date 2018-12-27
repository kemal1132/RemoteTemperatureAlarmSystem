package ServerAndClient;

import static org.junit.jupiter.api.Assertions.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.jupiter.api.Test;

import DatabaseManager.SimpleDatabase;
import SimpleEmail.EmailSender;

class junit {

	@Test
	void testEmail() {
	 try {
		EmailSender.generateAndSendEmail("Test", "Testing", "kemaldemirel1132@gmail.com");
	} catch (AddressException e) {
		fail("Shouldn't give exception");
		e.printStackTrace();
	} catch (MessagingException e) {
		fail("Shouldn't give exception");
		e.printStackTrace();
	}	
	}
	@Test
	void testDatabase() {
	
		 SimpleDatabase database = new SimpleDatabase();
		 assertTrue(database.insertToDatabase("1998-02-20 08:30", 10, 5));
	 
	 
	}

}
