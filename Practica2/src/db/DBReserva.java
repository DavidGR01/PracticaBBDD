package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import model.Cliente;
import model.Devolucion;
import model.Recogida;
import model.Reserva;
import model.Vehiculo;

public class DBReserva {

	/**
	 * Inserción de una nueva reserva asuminendo que cliente y vehículo ya existen
	 * en la base de datos
	 * 
	 * @param v Vehiculo dado
	 * @param c Cliente dado
	 * @return la nueva reserva con su id Auto generado
	 */
	public static Reserva nuevaReserva(Vehiculo v, Cliente c) {

		Connection conn = ConnectionManager.getConnection();

		Reserva res = null;

		try {

			// Guardamos el estado previo del commit
			boolean prevState = conn.getAutoCommit();

			// Inicializamos la transaccion
			conn.setAutoCommit(false);

			// Obtener id_estado_transaccion. Asumimos que existe
			Statement stTransaccion = conn.createStatement();
			ResultSet rsTransaccion = stTransaccion.executeQuery(
					"SELECT id_estado_transaccion FROM estado_transaccion WHERE descripcion = 'RESERVA PENDIENTE DE PAGO';");

			rsTransaccion.next();
			int idEstado = rsTransaccion.getInt(1);

			// Inserción de la reserva
			PreparedStatement pst = conn.prepareStatement(
					"INSERT INTO reserva(fecha,id_estado_transaccion,id_vehiculo,id_cliente) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			LocalDateTime fecha = LocalDateTime.now();
			pst.setObject(1, fecha);
			pst.setInt(2, idEstado);
			pst.setInt(3, v.getIdVehiculo());
			pst.setInt(4, c.getIdCliente());

			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();

			rs.next();
			int idReserva = rs.getInt(1);
			res = new Reserva(idReserva, fecha, idEstado, v.getIdVehiculo(), c.getIdCliente());

			LocalTime hora = LocalTime.of(12, 30);

			LocalDate fechaRecogida = LocalDate.of(fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth() + 1);
			// Nueva recogida
			DBRecogida.nuevaRecogida(new Recogida(idReserva, fechaRecogida, hora, null, null, 1, idEstado), conn);

			LocalDate fechaDevolucion = LocalDate.of(fechaRecogida.getYear(), fechaRecogida.getMonthValue(),
					fechaRecogida.getDayOfMonth() + 3);
			// Nueva devolución
			DBDevolucion.nuevaDevolucion(new Devolucion(idReserva, fechaDevolucion, hora, null, null, 1, idEstado),
					conn);

			// Commit
			conn.commit();

			// Commit a su estado anterior
			conn.setAutoCommit(prevState);

			// Cerramos los recursos
			stTransaccion.close();
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// Si falla algo hacemos rollback
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			// Si o si cerramos la conexión
			ConnectionManager.closeConnection();
		}

		return res;
	}

}
