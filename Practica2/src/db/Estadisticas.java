package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import model.Vehiculo;

public class Estadisticas {

	public static String nVehiculos() {
		String res = "";
		try {
			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(*) AS n_vehiculos FROM vehiculo;");

			// Imprimimos los resultados
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

			// Imprimimos los resultados
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

			// Imprimimos los resultados
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
					"SELECT v.id_vehiculo, SUM(num_horas * precio_hora)/sum(km) AS 'Precio medio mantenimiento' "
							+ "FROM trabajo_mantenimiento AS trm "
							+ "JOIN tipo_mantenimiento AS tim ON trm.id_tipo_mantenimiento = tim.id_tipo_mantenimiento "
							+ "JOIN vehiculo AS v ON v.id_vehiculo = trm.id_vehiculo " + "WHERE v.id_vehiculo = ?;");

			pst.setInt(1, idVehiculo);

			ResultSet rs = pst.executeQuery();

			// Imprimimos los resultados
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
	 * Limit en 50 para que se vean las cabeceras debido a la capacidad limitada de la consola
	 * @return
	 */
	public static String vehiculosSinMantenimiento() {
		String res = "";
		try {
			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT v.* FROM trabajo_mantenimiento AS trm "
					+ "RIGHT JOIN vehiculo AS v ON v.id_vehiculo = trm.id_vehiculo " + "WHERE num_horas IS NULL LIMIT 50;");

			// Imprimimos los resultados
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
			String query = ""
					+ "SELECT "
					+ "    ck.nombre, "
					+ "    ck.descripcion, "
					+ "    ck.num_mantenimientos, "
					+ "    ck.tiempo_medio, "
					+ "    ABS(ck.tiempo_medio - num_horas) / ck.num_mantenimientos AS desviacion "
					+ "FROM "
					+ "    (SELECT "
					+ "        COUNT(*) AS num_mantenimientos, "
					+ "            SUM(num_horas) / COUNT(*) AS tiempo_medio, "
					+ "            num_horas, "
					+ "            descripcion, "
					+ "            nombre "
					+ "    FROM "
					+ "        trabajo_mantenimiento AS trm "
					+ "    JOIN tipo_mantenimiento AS tm ON tm.id_tipo_mantenimiento = trm.id_tipo_mantenimiento "
					+ "    JOIN empleado AS e ON e.id_empleado = trm.id_empleado "
					+ "    GROUP BY e.id_empleado , tm.id_tipo_mantenimiento) AS ck;";
			ResultSet rs = st.executeQuery(query);

			// Imprimimos los resultados
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
	
	private static String printRs(ResultSet rs) {
		String res = "";
		try {
			ResultSetMetaData meta = rs.getMetaData();
			String cabecera = "";
			for (int i = 1; i <= meta.getColumnCount(); i++)
				cabecera += String.format("%-" + 35 + "s  ", meta.getColumnName(i));
			res += cabecera + "\n";

			while (rs.next()) {
				String s = "";
				for (int i = 1; i <= meta.getColumnCount(); i++)
					s += String.format("%-" + Math.max(35, meta.getColumnName(i).length()) + "s  ", rs.getString(i));
				res += s + "\n";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
}
