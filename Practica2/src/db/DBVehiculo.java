package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.Categoria;
import model.Color;
import model.TipoCombustible;
import model.Vehiculo;

public class DBVehiculo {

	private static HashMap<String, Integer> colores = new HashMap<>();
	private static HashMap<String, Integer> tiposCombustible = new HashMap<>();
	private static HashMap<String, Integer> modelos = new HashMap<>();
	private static HashMap<String, Integer> categorias = new HashMap<>();

	public static List<Vehiculo> getVehiculos(TipoCombustible combustible, Categoria categoria) {

		// Lista resultado
		List<Vehiculo> res = new ArrayList<Vehiculo>();

		try {

			PreparedStatement pst = ConnectionManager.getConnection().prepareStatement("SELECT * FROM vehiculo "
					+ "WHERE id_tipo_combustible = ? AND id_categoria = ? ORDER BY km, ano_matriculacion DESC LIMIT 20 ;");

			// Añadimos a la sentencia los parametros pasados
			pst.setInt(1, combustible.getIdTipoCombustible());
			pst.setInt(2, categoria.getIdCategoria());

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				// Cogemos los atributos del ResultSet
				int idVehiculo = rs.getInt(1);
				String matricula = rs.getString(2);
				int anoMatriculacion = rs.getInt(3);
				int idColor = rs.getInt(4);
				int numPlazas = rs.getInt(5);
				double km = rs.getInt(6);
				int idModelo = rs.getInt(7);
				int idCategoria = rs.getInt(8);
				int idTipoCombustible = rs.getInt(9);

				// Lo añadimos a la lista
				res.add(new Vehiculo(idVehiculo, matricula, anoMatriculacion, idColor, numPlazas, km, idModelo,
						idCategoria, idTipoCombustible));
			}

			// Cerramos los recursos
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Si o si cerramos la conexión
			ConnectionManager.closeConnection();
		}

		return res;

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

			// Desactivamos FOREIGN_KEY_CHECKS ya que ya lo comprobamos a mano
			conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0; ").executeQuery();

			int counter = 0;

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

				System.out.println(counter++);

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

			// Reactivamos FOREIGN_KEY_CHECKS
			conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1; ").executeQuery();

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
	public static Vehiculo getVehiculoById(int id) {
		
		Vehiculo res = null;
		
		try {
			PreparedStatement pst = ConnectionManager.getConnection().prepareStatement("SELECT * FROM vehiculo WHERE ID_VEHICULO = ? ;");
			pst.setInt(1,id);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				res = new Vehiculo(id,rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getDouble(6),rs.getInt(7),rs.getInt(8),rs.getInt(9));
		rs.close();
		pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionManager.closeConnection();
		}
		return res;
	}
}
