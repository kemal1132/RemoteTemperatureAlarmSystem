package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import ServerAndClient.Record;

public class SimpleDatabase {
	

	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "root";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	private final String dbName = "TempRecord";

	private final String tableName = "FarmRecords";

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connectionProps);

		return conn;
	}

	public boolean executeUpdate(Connection conn, String command) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(command); // This will throw a SQLException if it fails
			return true;
		} finally {

			// This will run whether we throw an exception or not
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void createTable() {

		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		// Create a table
		try {
			String createString = "CREATE TABLE " + this.tableName + " ( " + "RECORDID INTEGER NOT NULL, "+ "RECORDTIME TIMESTAMP NOT NULL, "
					+ "PLANTID INTEGER NOT NULL, " + "TEMPERATURE INTEGER NOT NULL, " + "PRIMARY KEY (RECORDID))";
			this.executeUpdate(conn, createString);
			System.out.println("Created a table");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}

	}

	public boolean insertToDatabase(String RecordTime, int PlantID, int temp) {
		int count=0;
		String query = "SELECT COUNT(*) FROM FarmRecords;";
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		
		}
		String insertString = "INSERT INTO FarmRecords VALUES ("+count+","+ "\"" + RecordTime + "\","
				+ PlantID + "," + temp + ")";
		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return false;
		}
		try {
			this.executeUpdate(conn, insertString);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ArrayList<Record> getResultsFromDatabase(boolean onlyEmergency){
		String query;
		ArrayList<Record> results = new ArrayList<Record>();
		if(!onlyEmergency){
			query = "SELECT * FROM FarmRecords";
		}else{
			query = "Select * FROM FarmRecords WHERE TEMPERATURE<6";
		}
		try {
			Statement statement = this.getConnection().createStatement();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				String recordTime = rs.getString("RECORDTIME");
				int plantID = rs.getInt("PLANTID");
				int temperature = rs.getInt("TEMPERATURE");
				results.add(new Record(recordTime,plantID,temperature));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		return results;
		
	} 

	public static void main(String[] args) {
	SimpleDatabase database = new SimpleDatabase();
	database.createTable();

		

	}
}