package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Color;

public class DBColor {

	public static List<Color> listadoColores() {
		// Lista resultado
		List<Color> res = new ArrayList<Color>();

		try {

			Statement st = ConnectionManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM color;");

			while (rs.next()) {

				// Cogemos los atributos del ResultSet
				int idColor = rs.getInt(1);
				String descripcion = rs.getString(2);

				// Lo añadimos a la lista
				res.add(new Color(idColor, descripcion));
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
