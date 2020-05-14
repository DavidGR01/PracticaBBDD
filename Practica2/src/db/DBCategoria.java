package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;

public class DBCategoria {

	public static List<Categoria> listadoCategorias() {

		// Lista resultado
		List<Categoria> res = new ArrayList<Categoria>();

		try {

			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM categoria;");

			while (rs.next()) {

				// Cogemos los atributos del ResultSet
				int idCategoria = rs.getInt(1);
				String nombre = rs.getString(2);
				double precioDiario = rs.getDouble(3);
				double ratioMantenimiento = rs.getDouble(4);

				// Lo añadimos a la lista
				res.add(new Categoria(idCategoria, nombre, precioDiario, ratioMantenimiento));
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
