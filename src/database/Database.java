package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	private Connection connection;
	
	public Connection getConnection() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_db", "root", "");
		return connection;
	}
}
