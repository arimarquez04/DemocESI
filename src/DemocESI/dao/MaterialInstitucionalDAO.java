package DemocESI.dao;

import java.sql.Connection;
import java.sql.Date;
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

public class MaterialInstitucionalDAO {
	
	//INSERT INTO `democesi`.`materiales`(`id_material`,`descripcion_material`,`fuente_material`,`enlace_material`,`procedencia_material`,`prioridad_material`,`categoria_material`)VALUES(?,?,?,?,?,?);
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
	
	public boolean guardarMaterialInstitucionalModificado(MaterialInstitucional matInst, String tituloAnterior) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "UPDATE `democesi`.`materiales` SET `descripcion_material` = ?,`fuente_material` = ?,`enlace_material` = ?,`procedencia_material` = ?,`prioridad_material` = ?,`categoria_material` = ?,`titulo_material` = ? WHERE `materiales`.`titulo_material` = ?";					
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setString(1, matInst.getDescripcion());
			pStmt.setString(2, matInst.getFuente());
			pStmt.setString(3, matInst.getEnlace());
			pStmt.setString(4, matInst.getProcedencia());
			pStmt.setBoolean(5, matInst.esPrioritario());
			pStmt.setInt(6, new CategoriaDAO().buscarCategoria(matInst.getDescripcionCategoria()));
			pStmt.setString(7, matInst.getTitulo());
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
	
	public boolean guardarNuevoMaterialInstitucional(MaterialInstitucional matInst) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "INSERT INTO `democesi`.`materiales`(`descripcion_material`,`fuente_material`,`enlace_material`,`procedencia_material`,`prioridad_material`,`categoria_material`, `titulo_material`)VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setString(1, matInst.getDescripcion());
			pStmt.setString(2, matInst.getFuente());
			pStmt.setString(3, matInst.getEnlace());
			pStmt.setString(4, matInst.getProcedencia());
			pStmt.setBoolean(5, matInst.esPrioritario());
			pStmt.setInt(6, new CategoriaDAO().buscarCategoria(matInst.getDescripcionCategoria()));
			pStmt.setString(7, matInst.getTitulo());
			filasAfectadas = pStmt.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Nuevo material institucional pendiente insertado a las ["
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
	
	public ArrayList<MaterialInstitucional> traerTodasLosMaterialesInstConIdYTitulo() {
		String columnaTitulo = "titulo_material";
		String columnaID = "id_material";

		ArrayList<MaterialInstitucional> materiales = new ArrayList<MaterialInstitucional>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `materiales`.`id_material`, `materiales`.`titulo_material` FROM `democesi`.`materiales` "
					+ "where procedencia_material is not null;";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				int id = rs.getInt(columnaID);
				MaterialInstitucional matInst = new MaterialInstitucional(titulo, id);
				materiales.add(matInst);

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
	
	public int buscarIdMaterial(MaterialInstitucional matInst) {

		ArrayList<MaterialInstitucional> materiales = traerTodasLosMaterialesInstConIdYTitulo();

		for (MaterialInstitucional mat : materiales) {
			if (mat.getTitulo().equalsIgnoreCase(matInst.getTitulo())) {
				return mat.getId();
			}
		}
		return 0;
	}
	public ArrayList<MaterialInstitucional> traerTodasLosMaterialesInstCompletos() {
		String columnaTitulo = "titulo_material";
		String columnaID = "id_material";
		String columnaDescripcion = "descripcion_material";
		String columnaPrioridad = "prioridad_material";// Es un bool
		String columnaFuente = "fuente_material";
		String columnaEnlace = "enlace_material";
		String columnaIDCategoria = "categoria_material";
		String columnaProcedencia = "procedencia_material";
		
		ArrayList<MaterialInstitucional> materiales = new ArrayList<MaterialInstitucional>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `materiales`.`id_material`,`materiales`.`descripcion_material`, `materiales`.`fuente_material`, "
					+ " `materiales`.`enlace_material`,  `materiales`.`procedencia_material`,  `materiales`.`prioridad_material`,"
					+ " `materiales`.`categoria_material`, `materiales`.`titulo_material`"
					+ " FROM `democesi`.`materiales` Where procedencia_material is not null;";

			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				int id = rs.getInt(columnaID);
				String descripcion = rs.getString(columnaDescripcion);
				String fuente = rs.getString(columnaFuente);
				String enlace = rs.getString(columnaEnlace);
				boolean prioridad = rs.getBoolean(columnaPrioridad);
				String procedencia = rs.getString(columnaProcedencia);
				String descripcionCategoria = new CategoriaDAO().buscarCategoria(rs.getInt(columnaIDCategoria));
				MaterialInstitucional matInst = new MaterialInstitucional(descripcion, fuente, enlace, procedencia,
						prioridad, descripcionCategoria, titulo);
				materiales.add(matInst);

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
	
	
	
}
