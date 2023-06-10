package DemocESI.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DemocESI.modelo.Material;
import DemocESI.modelo.MaterialInstitucional;
import DemocESI.modelo.MaterialPorPropuesta;

public class MaterialDAO {
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

	public ArrayList<Material> traerTodasLosMateriales() {
		String columnaTitulo = "titulo_material";
		String columnaDescripcion = "descripcion_material";
		String columnaPrioridad = "prioridad_material";// Es un bool
		String columnaFuente = "fuente_material";
		String columnaEnlace = "enlace_material";
		String columnaProcedencia = "procedencia_material";
		String columnaIDCategoria = "categoria_material";// No se que es esto y como funcionara pero lo puse porque
															// estaba asi en propuesta
		ArrayList<Material> materiales = new ArrayList<Material>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `materiales`.`id_material`,`materiales`.`descripcion_material`, `materiales`.`fuente_material`,  `materiales`.`enlace_material`,  `materiales`.`procedencia_material`,  `materiales`.`prioridad_material`, `materiales`.`categoria_material`, `materiales`.`titulo_material` FROM `democesi`.`materiales`;";

			/*
			 * "SELECT `propuestas`.`origen_propuesta`,`propuestas`.`estado_propuesta`,`propuestas`.`autor_propuesta`,"
			 * +
			 * "`propuestas`.`fecha_propuesta`,`propuestas`.`descripcion_propuesta`,`propuestas`.`motivacion_propuesta`,"
			 * +
			 * "`propuestas`.`motivo_propuesta`,`propuestas`.`id_categoria_propuesta`,`propuestas`.`titulo_propuesta` FROM"
			 * + " `democesi`.`propuestas`";
			 */
			Statement stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				String descripcion = rs.getString(columnaDescripcion);
				String fuente = rs.getString(columnaFuente);
				String procedencia = rs.getString(columnaProcedencia);
				String descripcionCategoria = new CategoriaDAO().buscarCategoria(rs.getInt(columnaIDCategoria));
				boolean prioridad = rs.getBoolean(columnaPrioridad);
				String enlace = rs.getString(columnaEnlace);
				if (procedencia == null) {
					MaterialPorPropuesta matProp = new MaterialPorPropuesta(titulo, descripcion, fuente, enlace,
							descripcionCategoria);
					materiales.add(matProp);
				} else {
					MaterialInstitucional matInst = new MaterialInstitucional(descripcion, fuente, enlace, procedencia,
							prioridad, descripcionCategoria, titulo);
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

	public ArrayList<Material> traerTodasLosMaterialesIdYTitulo() {

		ArrayList<Material> materiales = new ArrayList<Material>();
		ArrayList<MaterialPorPropuesta> materialesProp = new MaterialPorPropuestaDAO()
				.traerTodasLosMaterialesPropConIdYTitulo();
		ArrayList<MaterialInstitucional> materialesInst = new MaterialInstitucionalDAO()
				.traerTodasLosMaterialesInstConIdYTitulo();
		for (MaterialPorPropuesta mProp : materialesProp) {
			materiales.add(mProp);
		}
		for (MaterialInstitucional mInst : materialesInst) {
			materiales.add(mInst);
		}
		return materiales;

	}

	public int buscarIdDeMaterial(Material mt) {
		int id = -1;
		ArrayList<Material> materiales = traerTodasLosMaterialesIdYTitulo();
		Material matSeleccionado = null;
		for (Material m : materiales) {
			if (mt.getTitulo().equalsIgnoreCase(m.getTitulo())) {
				matSeleccionado = m;
			}
		}
		return matSeleccionado.getId();
	}
	/*
	 * private String descripcionCategoria;
	 * 
	 * public String getDescripcionCategoria() { return descripcionCategoria; }
	 * public void setDescripcionCategoria(String descripcionCategoria) {
	 * this.descripcionCategoria = descripcionCategoria; }
	 */

}
