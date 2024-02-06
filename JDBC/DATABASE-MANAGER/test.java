package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
	//You need to have the jar (Java Archive) of
	//https://github.com/xerial/sqlite-jdbc -- JDBC driver
	//https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.0.0/sqlite-jdbc-3.44.0.0.jar

	public static void main(String[] args) {
		
		// Database connection URL
		String url = "jdbc:sqlite:mySQLiteDatabase.db";

		try (Connection connection = DriverManager.getConnection(url)) {
			// Create tables and insert data
			createTables(connection);
			insertSampleData(connection);

			System.out.println("Sample SQLite database created successfully.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createTables(Connection connection) throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "username TEXT NOT NULL," + "email TEXT NOT NULL UNIQUE," + "password TEXT NOT NULL)";

		try (Statement statement = connection.createStatement()) {
			statement.execute(createTableSQL);
		}
	}

	private static void insertSampleData(Connection connection) throws SQLException {
		String insertDataSQL = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
			preparedStatement.setString(1, "john_doe");
			preparedStatement.setString(2, "john@example.com");
			preparedStatement.setString(3, "password123");
			preparedStatement.executeUpdate();
			preparedStatement.setString(1, "jane_smith");
			preparedStatement.setString(2, "jane@example.com");
			preparedStatement.setString(3, "securepass");
			preparedStatement.executeUpdate();
		}
	}
}
