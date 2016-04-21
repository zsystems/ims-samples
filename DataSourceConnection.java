import java.sql.Connection;
import java.sql.SQLException;
import com.ibm.ims.jdbc.IMSDataSource;

public class DataSourceConnection {
	// The hostname or IP address for the IMS Connect server.
	private static String HOSTNAME = "portal.prosentient.com";
	
	// The IMS Connect server port number
	private static int PORT = 5555;
	
	// DatabaseName is the name of the PSB used to access the target IMS database.
	private static String DATABASENAME = "INSURPSB";
	
	// Security credentials to access the IMS resources.
	private static String USERNAME = "MyUserID";
	private static String PASSWORD = "MyPassword";
	
	public static void main(String[] args) throws SQLException {
		Connection conn = null;

		// Create an instance of DataSource
		IMSDataSource ds = new com.ibm.ims.jdbc.IMSDataSource();

		// Set the URL of the fully qualified name of the Java metadata class
		ds.setDatabaseName(DATABASENAME);

		// Set the data store server
		ds.setDatastoreServer(HOSTNAME);

		// Set the port number
		ds.setPortNumber(PORT);

		// Set the JDBC connectivity driver type
		ds.setDriverType(IMSDataSource.DRIVER_TYPE_4);

		// Set user ID for connection
		ds.setUser(USERNAME);

		// Set password for connection
		ds.setPassword(PASSWORD);

		// Create JDBC connection
		conn = ds.getConnection();
		
		/*
		 * Add query and application logic here.
		 */
		
		// Cleaning up connection resource
		conn.commit();
		conn.close();
	}
}

