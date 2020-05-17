package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Estadisticas {

	public static String nVehiculos() {
		String res = "";
		try {
			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) AS n_vehiculos FROM vehiculo;");

			// Tabulamos los resultados
			res = printRs(rs);

			// Cerramos los recursos
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

		return res;
	}

	public static String marcaMasKm() {
		String res = "";
		try {
			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT mar.nombre AS 'Marca', SUM(km) AS 'km' FROM vehiculo AS v "
					+ "JOIN modelo AS m ON v.id_modelo = m.id_modelo "
					+ "JOIN marca mar ON mar.id_marca = m.id_marca GROUP BY mar.nombre "
					+ "ORDER BY SUM(km) DESC LIMIT 1;");

			// Tabulamos los resultados
			res = printRs(rs);

			// Cerramos los recursos
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

		return res;
	}

	public static String vehiculosMantenimientoMasCaro() {
		String res = "";
		try {
			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT v.*, SUM(num_horas * precio_hora) AS precio_acumulado FROM trabajo_mantenimiento AS trm "
							+ "JOIN tipo_mantenimiento AS tim ON trm.id_tipo_mantenimiento = tim.id_tipo_mantenimiento "
							+ "JOIN vehiculo AS v ON v.id_vehiculo = trm.id_vehiculo "
							+ "WHERE trm.fecha >= '2010-01-01 00:00:00' AND trm.fecha < '2021-01-01 00:00:00' "
							+ "GROUP BY id_vehiculo ORDER BY precio_acumulado DESC LIMIT 1;");

			// Tabulamos los resultados
			res = printRs(rs);

			// Cerramos los recursos
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

		return res;
	}

	public static String precioMedioMantenimientoPorKm(int idVehiculo) {
		String res = "";
		try {
			PreparedStatement pst = ConnectionManager.getConnection().prepareStatement(
					"SELECT v.id_vehiculo, sum(num_horas * precio_hora)/km AS 'Precio medio mantenimiento' "
							+ "FROM trabajo_mantenimiento AS trm "
							+ "JOIN tipo_mantenimiento AS tim ON trm.id_tipo_mantenimiento = tim.id_tipo_mantenimiento "
							+ "JOIN vehiculo AS v ON v.id_vehiculo = trm.id_vehiculo " + "WHERE v.id_vehiculo = ?;");

			pst.setInt(1, idVehiculo);

			ResultSet rs = pst.executeQuery();

			// Tabulamos los resultados
			res = printRs(rs);

			// Cerramos los recursos
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

		return res;
	}

	/**
	 * Limit en 50 para que se vean las cabeceras debido a la capacidad limitada de
	 * la consola
	 * 
	 * @return
	 */
	public static String vehiculosSinMantenimiento() {
		String res = "";
		try {
			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT v.* FROM trabajo_mantenimiento AS trm "
					+ "RIGHT JOIN vehiculo AS v ON v.id_vehiculo = trm.id_vehiculo "
					+ "WHERE num_horas IS NULL LIMIT 50;");

			// Tabulamos los resultados
			res = printRs(rs);

			// Cerramos los recursos
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

		return res;
	}

	public static String datosEstadisticosEmpleados() {
		String res = "";
		try {
			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT \r\n" + 
					"    nombre,\r\n" + 
					"    descripcion,\r\n" + 
					"    COUNT(*) AS num_mantenimientos,\r\n" + 
					"    AVG(num_horas) AS tiempo_medio,\r\n" + 
					"    SUM(ABS(num_horas_estimadas - num_horas)) / COUNT(*) AS desviacion\r\n" + 
					"FROM\r\n" + 
					"    trabajo_mantenimiento AS trm\r\n" + 
					"        JOIN\r\n" + 
					"    tipo_mantenimiento AS tm ON tm.id_tipo_mantenimiento = trm.id_tipo_mantenimiento\r\n" + 
					"        JOIN\r\n" + 
					"    empleado AS e ON e.id_empleado = trm.id_empleado\r\n" + 
					"GROUP BY e.id_empleado , tm.id_tipo_mantenimiento;");

			// Tabulamos los resultados
			res = printRs(rs);

			// Cerramos los recursos
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}

		return res;
	}

	/**
	 * Recorre el ResultSet dos veces: una para calcular las longitudes de columna
	 * óptimas y otra para mostrar los datos. No creemos que recorrer el resultset
	 * dos veces sea lo mas eficiente pero meta.getColumnDisplaySize(i) devuelve un
	 * tamaño demasiado grande por lo que hemos tenido que hacerlo así
	 * 
	 * @param rs ResultSet dado
	 * @return string tabulado del ResultSet dado
	 */
	private static String printRs(ResultSet rs) {

		// String resultado
		String res = "";

		try {
			// Metadatos del RS
			ResultSetMetaData meta = rs.getMetaData();

			// Array de longitudes maximas
			int[] longitudesMaximas = new int[meta.getColumnCount() + 1];

			// Recorremos todo el Resultset buscando las longitudes máximas para cada
			// columna
			while (rs.next())
				for (int i = 1; i <= meta.getColumnCount(); i++)
					longitudesMaximas[i] = Math.max(longitudesMaximas[i], rs.getString(i).length());

			// Devolvemos el cursor a su estado inicial
			rs.beforeFirst();

			String cabecera = "";
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				// Tenemos en cuenta la longitud de la cabecera y actualizamos el array de
				// longitudes máximas
				longitudesMaximas[i] = Math.max(longitudesMaximas[i], meta.getColumnName(i).length());
				cabecera += String.format("%-" + longitudesMaximas[i] + "s  ", meta.getColumnName(i));
			}
			res += cabecera + "\n";

			while (rs.next()) {
				String s = "";
				for (int i = 1; i <= meta.getColumnCount(); i++)
					s += String.format("%-" + longitudesMaximas[i] + "s  ", rs.getString(i));
				res += s + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
}
