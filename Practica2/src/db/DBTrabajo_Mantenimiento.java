package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

public class DBTrabajo_Mantenimiento {

	private static HashMap<Integer, String> empleados = new HashMap<>();
	private static HashMap<Integer, String> tiposMantenimiento = new HashMap<>();
	private static HashMap<String, Integer> matriculas = new HashMap<>();

	public static void importarCSV(String pathFichero, int tamanoLote) {

		// Inicio del temporizador
		long inicio = System.currentTimeMillis();

		// Abrimos la conexion
		Connection conn = ConnectionManager.getConnection();

		// Cargamos los HashMaps de las FKs
		cargarHashMaps(conn);

		// Abrir el archivo
		try {

			PreparedStatement pst = conn.prepareStatement("INSERT INTO trabajo_mantenimiento VALUES (?,?,?,?,?,?)");

			// Guardamos el estado previo del commit
			boolean prevState = conn.getAutoCommit();

			Scanner sc = new Scanner(new File(pathFichero), "UTF-8");
			sc.nextLine();

			// Inicializamos el contador de los lotes
			int contador = 0;
			int contadorLotes = 0;

			// Iniciamos la transacción
			conn.setAutoCommit(false);

			while (sc.hasNext()) {
				String lineaDatos = sc.nextLine();
				String[] cols = lineaDatos.split(";(?=([^\\\"]|\\\"[^\\\"]*\\\")*$)");

				// Coger valores de los HashMaps y del csv
				String matricula = cols[0].replace("\"", "");
				Integer idTipoMantenimiento = Integer.parseInt(cols[1].replace("\"", ""));
				Integer idEmpleado = Integer.parseInt(cols[2].replace("\"", ""));
				LocalDateTime fecha = LocalDateTime.parse(cols[3].replace("\"", "").replace(" ", "T"));
				Double numHoras = Double.parseDouble(cols[4].replace("\"", ""));
				Double kmVehiculo = Double.parseDouble(cols[5].replace("\"", ""));

				// Variables de comprobacion
				Integer idVehiculo = matriculas.get(matricula);
				String tipoMantenimiento = tiposMantenimiento.get(idTipoMantenimiento);
				String empleado = empleados.get(idEmpleado);

				// Condicion para que las FKs sean correctas
				boolean FKCorrectas = idVehiculo != null && tipoMantenimiento != null && empleado != null;

				// Insert
				if (FKCorrectas) {
					pst.setInt(1, idVehiculo);
					pst.setInt(2, idTipoMantenimiento);
					pst.setInt(3, idEmpleado);
					pst.setObject(4, fecha);
					pst.setDouble(5, numHoras);
					pst.setDouble(6, kmVehiculo);
					pst.addBatch();
					contador++;
				}

				// Si el lote está lleno ejecutamos el batch
				if (contador == tamanoLote) {
					contadorLotes++;
					pst.executeBatch();
					conn.commit();
					contador = 0;

					// Abrimos la siguiente transaccion
					conn.setAutoCommit(false);
				}

			}

			if (contador != 0) {
				System.out.println("Acabado");

				// Ejecutamos el ultimo batch
				pst.executeBatch();

				// Hacemos commit del ultimo lote incompleto
				conn.commit();
			}

			// Cerramos los recursos
			pst.close();
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

		empleados.clear();
		tiposMantenimiento.clear();
		matriculas.clear();

		try {
			Statement st = conn.createStatement();

			// Cargamos los colores
			ResultSet rs = st.executeQuery("SELECT id_empleado,dni FROM empleado;");
			while (rs.next())
				empleados.put(rs.getInt(1), rs.getString(2));

			// Cargamos los tipos de mantenimiento
			ResultSet rs1 = st.executeQuery("SELECT id_tipo_mantenimiento,descripcion FROM tipo_mantenimiento;");
			while (rs1.next())
				tiposMantenimiento.put(rs1.getInt(1), rs1.getString(2));

			// Cargamos los matriculas
			ResultSet rs2 = st.executeQuery("SELECT id_vehiculo,matricula FROM vehiculo;");
			while (rs2.next())
				matriculas.put(rs2.getString(2), rs2.getInt(1));

			st.close();
			rs.close();
			rs1.close();
			rs2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
