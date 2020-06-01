package gr.uoi.cs.mye30;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class Database {
	private static String url;

	public static void connectTo(String url) throws SQLException {
		Database.url = url;
		new Driver();
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}

}
