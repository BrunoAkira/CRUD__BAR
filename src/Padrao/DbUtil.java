package Padrao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	private static Connection connection = null;
	
	public static Connection getConnection() {
		
		if (connection != null) {
			return connection;
		} else {
			try {
							
				//String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				String url = "jdbc:sqlserver://localhost;databaseName=Bar"; 
				String user = "sa";
				String password = "123456";
				
				//Class.forName(driver); 
				
				connection = DriverManager.getConnection(url, user, password);
				
				connection.setAutoCommit(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} //if
		
		return connection;
		
	} //getConnection

} //DbUtil