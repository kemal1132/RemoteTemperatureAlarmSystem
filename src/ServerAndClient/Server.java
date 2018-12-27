package ServerAndClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.mail.MessagingException;
import DatabaseManager.SimpleDatabase;
import SimpleEmail.EmailSender;


class Server implements Runnable {
	private static ObjectInputStream objectIn;

	private static boolean closed = false;
	private static SimpleDatabase database;

	public static void main(String args[]) throws Exception {
		ServerSocket serverSocket = new ServerSocket(3333);
		Socket socket = serverSocket.accept();
		
		new ServerGui();
		ServerGui.main(null);
		
		database = new SimpleDatabase();
		
	

		objectIn = new ObjectInputStream(socket.getInputStream());

		new Thread(new Server()).start();
		while(!closed){
		Thread.sleep(1000);
		}
		System.out.println("closing");
		objectIn.close();
		socket.close();
		serverSocket.close();
		System.exit(0);
	}

	public void run() {
		
		Record temporary;
		try {
			while (true) {
				System.out.println("Waiting");
				
					while ((temporary = (Record) objectIn.readObject()) != null) {
					
						System.out.println(temporary);
						if(temporary.getTemperature()<6){
							String subject ="Warning!!! On date "+temporary.getRecordTime()+" Temperature has dropped to "+temporary.getTemperature();
							String emailBodyHtml ="On plant "+ temporary.getPlantId()+" temperature "+ temporary.getTemperature()+" Has been seen.";
							try {
							
								EmailSender.generateAndSendEmail(subject, emailBodyHtml, ServerGui.getEMail());
							} catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						database.insertToDatabase(temporary.getRecordTime(), temporary.getPlantId(), temporary.getTemperature());
						
						
						
					}
				
			}

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}