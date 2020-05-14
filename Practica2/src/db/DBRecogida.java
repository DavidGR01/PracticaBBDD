package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Recogida;

public class DBRecogida {

	/**
	 * Insercion de una nueva recogida. Void ya que no aporta nada devolver la misma
	 * recogida
	 * 
	 * @param recogida
	 * @param conn     dada para no cerrar la conexión del método llamante
	 */
	public static void nuevaRecogida(Recogida recogida, Connection conn) {

		try {

			PreparedStatement pstInsertar = conn.prepareStatement(
					"INSERT INTO recogida(id_reserva,fecha_prevista,hora_prevista,fecha_efectiva,hora_efectiva,id_oficina,id_estado_transaccion) "
							+ "VALUES(?,?,?,?,?,?,?);");

			// Añadimos los atributos de la recogida dada
			pstInsertar.setInt(1, recogida.getIdReserva());
			pstInsertar.setObject(2, recogida.getFechaPrevista());
			pstInsertar.setObject(3, recogida.getHoraPrevista());
			pstInsertar.setObject(4, recogida.getFechaEfectiva());
			pstInsertar.setObject(5, recogida.getHoraEfectiva());
			pstInsertar.setInt(6, recogida.getIdOficina());
			pstInsertar.setInt(7, recogida.getIdEstadoTransaccion());

			// Ejecutamos la query
			pstInsertar.executeUpdate();

			// Cerramos los recursos
			pstInsertar.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
