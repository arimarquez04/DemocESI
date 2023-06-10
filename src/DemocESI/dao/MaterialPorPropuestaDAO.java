package DemocESI.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DemocESI.modelo.Material;
import DemocESI.modelo.MaterialInstitucional;
import DemocESI.modelo.MaterialPorPropuesta;
import DemocESI.modelo.Propuesta;

public class MaterialPorPropuestaDAO {
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

	/*
	 * public boolean traerPropuestasDelMaterial(MaterialPorPropuesta matProp) {
	 * 
	 * }
	 */

	public boolean guardarMaterialPorPropuestModificado(MaterialPorPropuesta matProp, String tituloAnterior) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "UPDATE `democesi`.`materiales` SET `descripcion_material` = ?,`fuente_material` = ?,`enlace_material` = ?,`procedencia_material` = ?,`prioridad_material` = ?,`categoria_material` = ?,`titulo_material` = ? WHERE `materiales`.`titulo_material` = ?";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setString(1, matProp.getDescripcion());
			pStmt.setString(2, matProp.getFuente());
			pStmt.setString(3, matProp.getEnlace());
			pStmt.setString(4, null);
			pStmt.setBoolean(5, false);
			pStmt.setInt(6, new CategoriaDAO().buscarCategoria(matProp.getDescripcionCategoria()));
			pStmt.setString(7, matProp.getTitulo());
			pStmt.setString(8, tituloAnterior);
			filasAfectadas = pStmt.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Nuevo material por propuesta pendiente insertado a las ["
						+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "]");
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return filasAfectadas != 0;
	}

	// guerda un material por propuesta!!
	public boolean guardarNuevoMaterialInstitucional(MaterialPorPropuesta matProp) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "INSERT INTO `democesi`.`materiales`(`descripcion_material`,`fuente_material`,`enlace_material`,`procedencia_material`,`prioridad_material`,`categoria_material`, `titulo_material`)VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setString(1, matProp.getDescripcion());
			pStmt.setString(2, matProp.getFuente());
			pStmt.setString(3, matProp.getEnlace());
			pStmt.setString(4, null);
			pStmt.setBoolean(5, false);
			pStmt.setInt(6, new CategoriaDAO().buscarCategoria(matProp.getDescripcionCategoria()));
			pStmt.setString(7, matProp.getTitulo());
			filasAfectadas = pStmt.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Nuevo material por propuesta pendiente insertado a las ["
						+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "]");
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		return filasAfectadas != 0;
	}

	public boolean guardarPropuestasAUnMaterial(MaterialPorPropuesta matProp) {
		int filasAfectadas = 0;
		for (Propuesta p : matProp.getPropuestas()) {
			Connection c = null;
			try {
				c = conectar();
				String sql = "INSERT INTO `democesi`.`materiales_propuestas` (`id_material`, `id_propuesta`) VALUES (?,?);";
				PreparedStatement pStmt = c.prepareStatement(sql);
				pStmt.setInt(1, matProp.getId());
				pStmt.setInt(2, p.getId());
				System.out.println(pStmt);
				filasAfectadas = pStmt.executeUpdate();
				if (filasAfectadas > 0) {
					System.out.println("Nuevo propuesta guardada al material a las ["
							+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + "]");
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} finally {
				try {
					if (c != null) {
						c.close();
					}
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
		return filasAfectadas != 0;

	}

	public ArrayList<Material> traerTodasLosMaterialesConIdYTitulo() {
		String columnaTitulo = "titulo_material";
		String columnaID = "id_material";
		String columnaProcedencia = "procedencia_material";
		ArrayList<Material> materiales = new ArrayList<Material>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `materiales`.`id_material`, `materiales`.`titulo_material`, `materiales`.`procedencia_material` FROM `democesi`.`materiales`;";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				int id = rs.getInt(columnaID);
				String procedencia = rs.getNString(columnaProcedencia);
				if (procedencia == null) {
					MaterialPorPropuesta matProp = new MaterialPorPropuesta(titulo, id);
					materiales.add(matProp);
				} else {
					MaterialInstitucional matInst = new MaterialInstitucional(titulo, id);
					materiales.add(matInst);
				}
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
		return materiales;

	}
	

	public ArrayList<MaterialPorPropuesta> traerTodasLosMaterialesPropConIdYTitulo() {
		String columnaTitulo = "titulo_material";
		String columnaID = "id_material";
		ArrayList<MaterialPorPropuesta> materiales = new ArrayList<MaterialPorPropuesta>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `materiales`.`id_material`, `materiales`.`titulo_material` FROM `democesi`.`materiales` "
					+ "where procedencia_material is null;";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				int id = rs.getInt(columnaID);
				MaterialPorPropuesta matProp = new MaterialPorPropuesta(titulo, id);
				materiales.add(matProp);

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
		return materiales;

	}

	public ArrayList<MaterialPorPropuesta> traerTodasLosMaterialesPropCompletos() {
		String columnaTitulo = "titulo_material";
		String columnaID = "id_material";
		String columnaDescripcion = "descripcion_material";
		String columnaPrioridad = "prioridad_material";// Es un bool
		String columnaFuente = "fuente_material";
		String columnaEnlace = "enlace_material";
		String columnaIDCategoria = "categoria_material";
		ArrayList<MaterialPorPropuesta> materiales = new ArrayList<MaterialPorPropuesta>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `materiales`.`id_material`,`materiales`.`descripcion_material`, `materiales`.`fuente_material`, "
					+ " `materiales`.`enlace_material`,  `materiales`.`procedencia_material`,  `materiales`.`prioridad_material`,"
					+ " `materiales`.`categoria_material`, `materiales`.`titulo_material`"
					+ " FROM `democesi`.`materiales` Where procedencia_material is null;";

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				int id = rs.getInt(columnaID);
				String descripcion = rs.getString(columnaDescripcion);
				String fuente = rs.getString(columnaFuente);
				String enlace = rs.getString(columnaEnlace);
				String descripcionCategoria = new CategoriaDAO().buscarCategoria(rs.getInt(columnaIDCategoria));
				MaterialPorPropuesta matProp = new MaterialPorPropuesta(titulo, descripcion, fuente, enlace,
						 descripcionCategoria);
				materiales.add(matProp);

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
		return materiales;

	}
	
	public int buscarIdMaterial(MaterialPorPropuesta matProp) {

		ArrayList<MaterialPorPropuesta> materiales = traerTodasLosMaterialesPropConIdYTitulo();

		for (MaterialPorPropuesta mat : materiales) {
			if (mat.getTitulo().equalsIgnoreCase(matProp.getTitulo())) {
				return mat.getId();
			}
		}
		return 0;
	}

}
