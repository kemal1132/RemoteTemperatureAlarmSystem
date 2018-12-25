package ServerAndClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client implements Runnable {
	private static Socket clientSocket = null; // The client socket
	private static ObjectOutputStream os = null; // The output stream


	

	public static void main(String[] args) {
		int portNumber = 3333;
		String host = "localhost";

	
		try {
			clientSocket = new Socket(host, portNumber);
			os = new ObjectOutputStream(clientSocket.getOutputStream()); 
			os.flush();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to the host " + host);
		}

		/*
		 * If everything has been initialized then we want to write some data to
		 * the socket we have opened a connection to on the port portNumber.
		 */
		if (clientSocket != null && os != null) {
			/* Create a thread to read from the server. */
			new Thread(new Client()).start();
		}
	}

	
	public void run() {
	while(true){
		SecureRandom rand = new SecureRandom();
		int randomTemp= rand.nextInt(50);
		int randomPlantID= rand.nextInt(100);
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = dateFormat.format(date);
		try {
			System.out.println("Trying to write "+new Record(strDate,randomPlantID,randomTemp-10));
			Client.os.writeObject(new Record(strDate,randomPlantID,randomTemp-10));
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	}
}