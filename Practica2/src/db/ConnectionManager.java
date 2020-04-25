package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static boolean initialized = false;
	private static String server, port, database, user, password;
	private static Connection conn = null;

	public static void init(String server, String port, String database, String user, String password) {
		ConnectionManager.server = server;
		ConnectionManager.port = port;
		ConnectionManager.database = database;
		ConnectionManager.user = user;
		ConnectionManager.password = password;
		ConnectionManager.initialized = true;
	}

	public static Connection getConnection() {

		// Compobar si la clase esta configurada
		if (!initialized)
			throw new ExceptionInInitializerError("You should configure connection parameters");

		// Comprobar si la clase esta libre (en este ejemplo solo tenemos una conexion
		// asi que no aplica)

		// Comprobar si la conexion esta inicializada
		try {
			if (conn == null || conn.isClosed()) {

				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				conn = DriverManager
						.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database + "?" + "user=" + user
								+ "&password=" + password + "&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC");
			}
		} catch (ExceptionInInitializerError ex) {
			System.err.println("Error checking connection" + ex.getMessage());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Devolvemos la conexion

		return conn;
	}

	public static void closeConnection() {
		try {
			if (!conn.isClosed())
				;//conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionInInitializerError();
		}

	}
}
