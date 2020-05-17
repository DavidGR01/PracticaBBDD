package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import model.Devolucion;

public class DBDevolucion {

	/**
	 * Insercion de una nueva devolución. Void ya que no aporta nada devolver la
	 * misma devolucion
	 * 
	 * @param recogida
	 * @param conn     dada para no cerrar la conexión del método llamante
	 */
	public static void nuevaDevolucion(Devolucion devolucion, Connection conn) {

		try {

			PreparedStatement pstInsertar = conn.prepareStatement(
					"INSERT INTO devolucion(id_reserva,fecha_prevista,hora_prevista,fecha_efectiva,hora_efectiva,id_oficina,id_estado_transaccion) "
							+ "VALUES(?,?,?,?,?,?,?);");

			// Añadimos los atributos de la devolucion dada
			pstInsertar.setInt(1, devolucion.getIdReserva());
			pstInsertar.setObject(2, devolucion.getFechaPrevista());
			pstInsertar.setObject(3, devolucion.getHoraPrevista());
			pstInsertar.setObject(4, devolucion.getFechaEfectiva());
			pstInsertar.setObject(5, devolucion.getHoraEfectiva());
			pstInsertar.setInt(6, devolucion.getIdOficina());
			pstInsertar.setInt(7, devolucion.getIdEstadoTransaccion());

			// Ejecutamos la query
			pstInsertar.executeUpdate();

			// Cerramos los recursos
			pstInsertar.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Devolucion getDevolucionById(int idReserva) {

		Devolucion res = null;

		try {
			PreparedStatement pst = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM devolucion WHERE id_reserva = ? ;");
			pst.setInt(1, idReserva);

			ResultSet rs = pst.executeQuery();

			if (rs.next())
				res = new Devolucion(rs.getInt(1), rs.getObject(2, LocalDate.class), rs.getObject(3, LocalTime.class),
						rs.getObject(4, LocalDate.class), rs.getObject(5, LocalTime.class), rs.getInt(6), rs.getInt(7));

			// Cerramos los recursos
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection();
		}
		return res;
	}

}
