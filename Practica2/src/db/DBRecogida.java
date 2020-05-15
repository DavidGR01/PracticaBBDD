package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import model.Recogida;
import model.Vehiculo;

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
	public static Recogida getRecogidaById(int id) {
		Recogida res = null;
		
		try {
			PreparedStatement pst = ConnectionManager.getConnection().prepareStatement("SELECT * FROM recogida WHERE id_reserva = ? ;");
			pst.setInt(1,id);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
				res = new Recogida(id,rs.getObject(2,LocalDate.class),rs.getObject(3,LocalTime.class),rs.getObject(4,LocalDate.class),(LocalTime)rs.getObject(5,LocalTime.class),rs.getInt(6),rs.getInt(7));
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
