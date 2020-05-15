package db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnectionManager {

	private static boolean initialited = false;

	private static String servidor, puerto, baseDatos, usuario, password;

	private static Connection conn = null;

	public static void init(String servidor, String puerto, String baseDatos, String usuario, String password) {

		ConnectionManager.servidor = servidor;
		ConnectionManager.puerto = puerto;
		ConnectionManager.baseDatos = baseDatos;
		ConnectionManager.usuario = usuario;
		ConnectionManager.password = password;

		initialited = true;
	}

	public static Connection getConnection() {

		// comprobar si la clase esta configuarada
		if (!initialited)
			throw new ExceptionInInitializerError("You should configure conecction parameters");
		// comprobar si la conexion esta libre
		// en este ejemplo no va a pasar pq no hay mas de un proceso

		// comprobar si la conexion esta iniciada
		try {
			if (conn == null || conn.isClosed())
				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

					conn = DriverManager.getConnection("jdbc:mysql://" + servidor + ": " + puerto + "/" + baseDatos
							+ "?" + "user= " + usuario + "&password=" + password
							+ "&useJDBCCompliantTimezoneShift=true&serverTimezone=" + TimeZone.getDefault().getID());

				} catch (ExceptionInInitializerError e) {
					System.err.println(e.getMessage());
					throw new ExceptionInInitializerError("Error when checking the Conecction: " + e.getMessage());
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					System.err.println("No se ha podido cargar el driver mysql el JDBC");
					throw new ExceptionInInitializerError("Error when  loading JBDC MYSQL DRIVER" + e.getMessage());
				} catch (SQLException e) {
					System.err.println("No se ha podido conectar a la base de datos");
					throw new ExceptionInInitializerError("Error when  coneccting with server" + e.getMessage());
				}
		} catch (ExceptionInInitializerError e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// devolvemos la conexion pedida
		return conn;
	}

	public static void closeConnection() {
		try {
			
			// comprobar si la clase esta configuarada
			if (!initialited)
				throw new ExceptionInInitializerError("You should configure conecction parameters");
			
			if (!conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionInInitializerError("Error");
		}

	}
}
