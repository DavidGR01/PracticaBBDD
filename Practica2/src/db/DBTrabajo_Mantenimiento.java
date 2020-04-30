package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBTrabajo_Mantenimiento {
	
	private static HashMap<Integer, String> empleados = new HashMap<>();
	private static HashMap<Integer, String> tiposMantenimiento = new HashMap<>();
	private static HashMap<String, Integer> matriculas = new HashMap<>();
	
	public static void importarCSV(String pathFichero) {
		Connection conn = ConnectionManager.getConnection();
		cargarHashMaps(conn);

	}
	
	private static void cargarHashMaps(Connection conn) {
		
		empleados.clear();
		tiposMantenimiento.clear();
		matriculas.clear();

		try {
			Statement st = conn.createStatement();

			// Cargamos los colores
			ResultSet rs = st.executeQuery("SELECT * FROM color;");
//			while (rs.next())
//				empleados.put(rs.getString(2), rs.getInt(1));

			// Cargamos los tipos de mantenimiento
//			ResultSet rs1 = st.executeQuery("SELECT * FROM tipo_combustible;");
//			while (rs1.next())
//				tiposMantenimiento.put(rs1.getString(2), rs1.getInt(1));

			// Cargamos los matriculas
//			ResultSet rs2 = st.executeQuery("SELECT * FROM modelo;");
//			while (rs2.next())
//				matriculas.put(rs2.getString(2), rs2.getInt(1));


//			st.close();
//			rs.close();
//			rs1.close();
//			rs2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
