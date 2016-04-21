import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnection {
	// The hostname or IP address for the IMS Connect server.
	private static String HOSTNAME = "portal.prosentient.com";
	
	// The IMS Connect server port number
	private static String PORT = "5555";
	
	// The resource (PSB) used to access the target IMS database.
	private static String DATABASENAME = "INSURPSB";
	
	// Security credentials to access the IMS resources.
	private static String USERNAME = "MyUserID";
	private static String PASSWORD = "MyPassword";
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// Create the connection string for the driver manager
		String connectionString = 
				"jdbc:ims://" + HOSTNAME + ":" + PORT + "/" + DATABASENAME;
		
		// Use IMS's IMSDriver to get the appropriate DriverManager
		Class.forName("com.ibm.ims.jdbc.IMSDriver");
		
		Connection conn = 
				DriverManager.getConnection(connectionString, USERNAME, PASSWORD);

		/*
		 * Add query and application logic here.
		 */
		
		// Cleaning up connection resource
		conn.commit();
		conn.close();
	}
}