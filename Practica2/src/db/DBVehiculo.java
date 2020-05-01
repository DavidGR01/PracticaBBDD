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

		// Inciamos el temporizador
		long inicio = System.currentTimeMillis();

		Connection conn = ConnectionManager.getConnection();

		// Cargamos los hashmaps
		cargarHashMaps(conn);

		// Abrir el archivo
		try {

			// PreparedStatements y ResultSet de matricula
			PreparedStatement pstUpdate = conn.prepareStatement(
					"UPDATE vehiculo SET ano_matriculacion = ?, id_color = ?, num_plazas = ?, km = ?, id_modelo = ?, id_categoria = ?, id_tipo_combustible = ? WHERE matricula = ? ;");
			PreparedStatement pstInsert = conn
					.prepareStatement("INSERT INTO vehiculo VALUES (default,?,?,?,?,?,?,?,?)");
			PreparedStatement pstMatricula = conn.prepareStatement("SELECT * FROM vehiculo WHERE matricula = ? ;");
			ResultSet rs = null;

			Scanner sc = new Scanner(new File(pathFichero), "UTF-8");
			sc.nextLine();

			// Guardamos el estado previo del commit
			boolean prevState = conn.getAutoCommit();

			while (sc.hasNext()) {
				String lineaDatos = sc.nextLine();
				String[] cols = lineaDatos.split(";(?=([^\\\"]|\\\"[^\\\"]*\\\")*$)");

				// Quitamos las comillas sobrantes
				for (int i = 0; i < cols.length; i++)
					cols[i] = cols[i].replace("\"", "");

				// Iniciamos la transacción
				conn.setAutoCommit(false);

				// Coger valores de los HashMaps y del csv
				Integer idColor = colores.get(cols[2]);
				Integer idModelo = modelos.get(cols[6]);
				Integer idCategoria = categorias.get(cols[7]);
				Integer idTipoCombustible = tiposCombustible.get(cols[8]);
				int anoMatriculacion = Integer.parseInt(cols[1]);
				int numPlazas = Integer.parseInt(cols[3]);
				double km = Double.parseDouble(cols[4]);

				// Condicion para que las FKs sean correctas
				boolean FKCorrectas = idColor != null && idModelo != null && idCategoria != null
						&& idTipoCombustible != null;

				// Comprobar la matricula
				pstMatricula.setString(1, cols[0]);

				rs = pstMatricula.executeQuery();

				if (rs.next() && FKCorrectas) {
					// Update
					pstUpdate.setInt(1, anoMatriculacion);
					pstUpdate.setInt(2, idColor);
					pstUpdate.setInt(3, numPlazas);
					pstUpdate.setDouble(4, km);
					pstUpdate.setInt(5, idModelo);
					pstUpdate.setInt(6, idCategoria);
					pstUpdate.setInt(7, idTipoCombustible);
					pstUpdate.setString(8, cols[0]);

					pstUpdate.addBatch();
					pstUpdate.executeBatch();
				} else if (FKCorrectas) {
					// Insert
					pstInsert.setString(1, cols[0]);
					pstInsert.setInt(2, anoMatriculacion);
					pstInsert.setInt(3, idColor);
					pstInsert.setInt(4, numPlazas);
					pstInsert.setDouble(5, km);
					pstInsert.setInt(6, idModelo);
					pstInsert.setInt(7, idCategoria);
					pstInsert.setInt(8, idTipoCombustible);

					pstInsert.addBatch();
					pstInsert.executeBatch();
				}

				// Hacemos commit
				conn.commit();
			}
			// Cerramos los recursos
			rs.close();
			pstUpdate.close();
			pstInsert.close();
			pstMatricula.close();
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
