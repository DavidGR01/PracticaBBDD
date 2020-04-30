package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



import model.Categoria;
import model.TipoCombustible;
import model.Vehiculo;

public class DBVehiculo {

	private static HashMap<String, Integer> colores = new HashMap<>();
	private static HashMap<String, Integer> tiposCombustible = new HashMap<>();
	private static HashMap<String, Integer> modelos = new HashMap<>();
	private static HashMap<String, Integer> categorias = new HashMap<>();

	public List<Vehiculo> getVehiculos(TipoCombustible combustible, Categoria categoria) {
		return null;
	}

	public static void importarCSV(String pathFichero) {

		// Abrir el archivo
		try {
			Scanner sc = new Scanner(new File(pathFichero), "UTF-8");
			sc.nextLine();
			while (sc.hasNext()) {
				String lineaDatos = sc.nextLine();
				String[] cols = lineaDatos.split(";(?=([^\\\"]|\\\"[^\\\"]*\\\")*$)");
				// Iniciamos la transacción
				Connection conn = ConnectionManager.getConnection();
				boolean prevState = conn.getAutoCommit();
				conn.setAutoCommit(false);

				// Selects
				cargarHashMaps();
				Integer id_color = colores.get(cols[2].replace("\"", ""));
				Integer id_modelo = modelos.get(cols[6].replace("\"", ""));
				Integer id_categoria = categorias.get(cols[7].replace("\"", ""));
				Integer id_tipo_combustible = tiposCombustible.get(cols[8].replace("\"", ""));

				boolean FKCorrectas = id_color != null && id_modelo != null && id_categoria != null
						&& id_tipo_combustible != null;

				PreparedStatement pstMatricula = conn.prepareStatement("SELECT * FROM vehiculo WHERE matricula = ? ;");
				pstMatricula.setString(1, cols[0]);
				ResultSet rsMatricula = pstMatricula.executeQuery();
				if (rsMatricula.next() && FKCorrectas) {
					// Update
					String ano_matriculacion =  cols[1];
					int num_plazas =  Integer.parseInt(cols[3]);
					int km =  Integer.parseInt(cols[4]);
					Statement st = conn.createStatement();
							
					st.addBatch("UPDATE vehiculo SET ano_matriculacion = " +ano_matriculacion+ ", id_color = "+id_color+" , "
							+ "num_plazas = "+num_plazas+" , km = "+ km +" , id_modelo = "+ id_modelo+ ", id_categoria = "+ id_categoria +
							", id_tipo_combustible = "+ id_tipo_combustible +
							 "WHERE matricula = "+ cols[0]+ ";");
					
					st.executeBatch();
					
				} else {

				}
				// insert
				rsMatricula.close();
				pstMatricula.close();

				conn.commit();

				// Cerramos la conexion
				conn.setAutoCommit(prevState);
				ConnectionManager.closeConnection();
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			// Rollback
		}
	}

	private static void cargarHashMaps() {
		Connection conn = ConnectionManager.getConnection();

		try {
			// Cargamos los colores
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM color;");
			while (rs.next())
				colores.put(rs.getString(2), rs.getInt(1));

			// Cargamos los combustibles
			Statement st1 = conn.createStatement();
			ResultSet rs1 = st1.executeQuery("SELECT * FROM tipo_combustible;");
			while (rs1.next())
				tiposCombustible.put(rs1.getString(2), rs1.getInt(1));

			// Cargamos los modelos
			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery("SELECT * FROM modelo;");
			while (rs2.next())
				modelos.put(rs2.getString(2), rs2.getInt(1));

			// Cargamos los categorias
			Statement st3 = conn.createStatement();
			ResultSet rs3 = st3.executeQuery("SELECT * FROM categoria;");
			while (rs.next())
				categorias.put(rs3.getString(2), rs3.getInt(1));

			st.close();
			rs.close();
			st1.close();
			rs1.close();
			st2.close();
			rs2.close();
			st3.close();
			rs3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionManager.closeConnection();
	}
}
