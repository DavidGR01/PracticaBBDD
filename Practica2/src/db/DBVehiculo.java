package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
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
		long inicio = System.currentTimeMillis();
		Connection conn = ConnectionManager.getConnection();
		cargarHashMaps(conn);
		Statement st = null;
		ResultSet rs = null;
		// Abrir el archivo
		try {
			Scanner sc = new Scanner(new File(pathFichero), "UTF-8");
			sc.nextLine();

			boolean prevState = conn.getAutoCommit();
//			int cont = 0;
			while (sc.hasNext()) {
				String lineaDatos = sc.nextLine();
				String[] cols = lineaDatos.split(";(?=([^\\\"]|\\\"[^\\\"]*\\\")*$)");

				// Iniciamos la transacción
				conn.setAutoCommit(false);

				// Coger valores de los HashMaps y del csv
				Integer id_color = colores.get(cols[2].replace("\"", ""));
				Integer id_modelo = modelos.get(cols[6].replace("\"", ""));
				Integer id_categoria = categorias.get(cols[7].replace("\"", ""));
				Integer id_tipo_combustible = tiposCombustible.get(cols[8].replace("\"", ""));
				int ano_matriculacion = Integer.parseInt(cols[1].replace("\"", ""));
				int num_plazas = Integer.parseInt(cols[3].replace("\"", ""));
				double km = Double.parseDouble(cols[4].replace("\"", ""));

				// Condicion para que las FKs sean correctas
				boolean FKCorrectas = id_color != null && id_modelo != null && id_categoria != null
						&& id_tipo_combustible != null;

				// Comprobar la matricula
				st = conn.createStatement();
				rs = st.executeQuery("SELECT * FROM vehiculo WHERE matricula = " + cols[0] + " ;");

				if (rs.next() && FKCorrectas) {
					// Update
					st.addBatch("UPDATE vehiculo SET ano_matriculacion = " + ano_matriculacion + ", id_color = "
							+ id_color + " , " + "num_plazas = " + num_plazas + " , km = " + km + " , id_modelo = "
							+ id_modelo + ", id_categoria = " + id_categoria + ", id_tipo_combustible = "
							+ id_tipo_combustible + " WHERE matricula = " + cols[0] + ";");
					st.executeBatch();
				} else if (FKCorrectas) {
					// Insert
					st.addBatch(
							"INSERT INTO vehiculo (matricula, ano_matriculacion, id_color, num_plazas, km, id_modelo, id_categoria, id_tipo_combustible) VALUES ("
									+ cols[0] + ", " + ano_matriculacion + ", " + id_color + ", " + num_plazas + ", "
									+ km + ", " + id_modelo + ", " + id_categoria + ", " + id_tipo_combustible + ");");
					st.executeBatch();
				}

				// Hacemos commit
				conn.commit();
			}
			// Cerramos los recursos
			rs.close();
			st.close();
			sc.close();

			// Devolvemos el estado del commit a su estado anterior
			conn.setAutoCommit(prevState);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.closeConnection();
			long fin = System.currentTimeMillis();
			double tiempo = (double) ((fin - inicio) / 1000);
			System.out.println(tiempo + " segundos");
		}
	}

	private static void cargarHashMaps(Connection conn) {

		colores.clear();
		tiposCombustible.clear();
		modelos.clear();
		categorias.clear();

		try {
			Statement st = conn.createStatement();

			// Cargamos los colores
			ResultSet rs = st.executeQuery("SELECT * FROM color;");
			while (rs.next())
				colores.put(rs.getString(2), rs.getInt(1));

			// Cargamos los combustibles
			ResultSet rs1 = st.executeQuery("SELECT id_tipo_combustible,nombre FROM tipo_combustible;");
			while (rs1.next())
				tiposCombustible.put(rs1.getString(2), rs1.getInt(1));

			// Cargamos los modelos
			ResultSet rs2 = st.executeQuery("SELECT id_modelo,nombre FROM modelo;");
			while (rs2.next())
				modelos.put(rs2.getString(2), rs2.getInt(1));

			// Cargamos los categorias
			ResultSet rs3 = st.executeQuery("SELECT id_categoria, nombre FROM categoria;");
			while (rs3.next())
				categorias.put(rs3.getString(2), rs3.getInt(1));

			st.close();
			rs.close();
			rs1.close();
			rs2.close();
			rs3.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
