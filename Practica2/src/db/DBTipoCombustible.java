package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.TipoCombustible;

public class DBTipoCombustible {

	public static List<TipoCombustible> listadoTiposCombustible() {

		// Lista resultado
		List<TipoCombustible> res = new ArrayList<TipoCombustible>();

		try {

			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM tipo_combustible;");

			while (rs.next()) {

				// Cogemos los atributos del ResultSet
				int idTipoCombustible = rs.getInt(1);
				String nombre = rs.getString(2);
				String descripcion = rs.getString(3);

				// Lo añadimos a la lista
				res.add(new TipoCombustible(idTipoCombustible, nombre, descripcion));
			}

			// Cerramos los recursos
			st.close();
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
