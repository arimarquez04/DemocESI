package DemocESI.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DemocESI.modelo.Categoria;

public class CategoriaDAO {
	private Connection conectar() {
		String url = "jdbc:mysql://localhost:3306/democesi";
		String usr = "root";
		String pass = "b4CSLw";
		Connection c = null;
		try {
			c = DriverManager.getConnection(url, usr, pass);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return c;

	}

	public ArrayList<Categoria> traerTodasLasCategoria() {
		ArrayList<Categoria> categorias = new ArrayList<>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `categorias`.`id_categoria`,`categorias`.`descripcion_categoria` FROM `democesi`.`categorias`";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id_categoria");
				String descripcion = rs.getString("descripcion_categoria");
				Categoria cat = new Categoria(id, descripcion);
				categorias.add(cat);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return categorias;
	}

	public String buscarCategoria(int categoriaBuscar) {
		ArrayList<Categoria> categorias = traerTodasLasCategoria();
		String categoriaEncontrada = null;
		for (Categoria c : categorias) {
			if (c.getId() == categoriaBuscar) {
				categoriaEncontrada = c.getDescripcion();
			}
		}
		return categoriaEncontrada;
	}

	public int buscarCategoria(String categoriaBuscar) {
		ArrayList<Categoria> categorias = traerTodasLasCategoria();
		int categoriaEncontrada = -1;
		for (Categoria c : categorias) {
			if (c.getDescripcion().equalsIgnoreCase(categoriaBuscar) == true) {
				categoriaEncontrada = c.getId();
			}
		}
		return categoriaEncontrada;
	}
}

