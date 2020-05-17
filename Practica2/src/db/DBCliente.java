package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Cliente;

public class DBCliente {

	/**
	 * Inserta un nuevo cliente
	 * 
	 * @param c Cliente a insertar
	 * @return mismo cliente con la nueva id o null si no se ha podido insertar
	 */
	public static Cliente insertarCliente(Cliente c) {

		try {
			Connection conn = ConnectionManager.getConnection();

			// Sentencia para comprobar si existe ya el cliente
			PreparedStatement pstExiste = conn.prepareStatement("SELECT * FROM cliente WHERE dni_cliente = ? ;");
			pstExiste.setInt(1, c.getDniCliente());

			// Sentencia para verificar la FK id_poblacion
			PreparedStatement pstFK = conn.prepareStatement("SELECT * FROM poblacion WHERE id_poblacion = ? ;");
			pstFK.setInt(1, c.getIdPoblacion());

			ResultSet rsExiste = pstExiste.executeQuery();
			ResultSet rsFK = pstFK.executeQuery();

			// Si no existe e id_poblacion es correcta lo añadimos, si existe devolvemos -1
			if (!rsExiste.next() && rsFK.next()) {
				PreparedStatement pstInsertar = conn.prepareStatement(
						"INSERT INTO cliente(dni_cliente,nombre,apellido1,apellido2,telefono_contacto,id_poblacion) "
								+ "VALUES(?,?,?,?,?,?);",
						Statement.RETURN_GENERATED_KEYS);

				// Añadimos los atributos del cliente dado
				pstInsertar.setInt(1, c.getDniCliente());
				pstInsertar.setString(2, c.getNombre());
				pstInsertar.setString(3, c.getApellido1());
				pstInsertar.setString(4, c.getApellido2());
				pstInsertar.setString(5, c.getTelefonoContacto());
				pstInsertar.setInt(6, c.getIdPoblacion());

				// Ejecutamos la query
				pstInsertar.executeUpdate();

				// ResultSet para obetener la PK generada
				ResultSet claveGenerada = pstInsertar.getGeneratedKeys();

				claveGenerada.next();
				// Añadimos el nuevo id al cliente
				c.setIdCliente(claveGenerada.getInt(1));

				// Cerramos los recursos
				pstInsertar.close();
				claveGenerada.close();
			}

			// Cerramos los recursos
			pstExiste.close();
			pstFK.close();
			rsExiste.close();
			rsFK.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Si o si cerramos la conexión
			ConnectionManager.closeConnection();
		}

		return c.getIdCliente() != -1 ? c : null;

	}

	/**
	 * Búsqueda de un cliente por DNI
	 * 
	 * @param DNI parametro de busqueda
	 * @return cliente pedido o null si no existe
	 */
	public static Cliente obtenerClientePorDNI(int DNI) {

		Cliente res = null;

		try {
			PreparedStatement pst = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM cliente WHERE dni_cliente = ? ;");

			pst.setInt(1, DNI);

			ResultSet rs = pst.executeQuery();

			if (rs.next())
				res = new Cliente(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getInt(7));

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

}
