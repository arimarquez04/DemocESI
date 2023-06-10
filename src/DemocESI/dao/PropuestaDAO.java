package DemocESI.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DemocESI.modelo.*;

public class PropuestaDAO {

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

	public boolean guardarNuevaPropuesta(Propuesta p) throws ParseException {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "INSERT INTO `democesi`.`propuestas`(`origen_propuesta`,`estado_propuesta`,`autor_propuesta`,`fecha_propuesta`,`descripcion_propuesta`,`motivacion_propuesta`,`motivo_propuesta`,`id_categoria_propuesta`, `titulo_propuesta`) VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pStmt = c.prepareStatement(sql);
			// `origen_propuesta`,`estado_propuesta`,`autor_propuesta`,
			// `fecha_propuesta`,`descripcion_propuesta`,`motivacion_propuesta`,
			// `motivo_propuesta`,`id_categoria_propuesta`) VALUES();
			pStmt.setString(1, p.getOrigen());
			pStmt.setString(2, p.getEstado());
			pStmt.setString(3, p.getAutor());
			pStmt.setDate(4, p.getFecha());
			pStmt.setString(5, p.getDescripcion());
			pStmt.setString(6, p.getMotivacion());
			pStmt.setString(7, p.getMotivo());
			pStmt.setInt(8, new CategoriaDAO().buscarCategoria(p.getDescripcionCategoria()));
			pStmt.setString(9, p.getTitulo());
			filasAfectadas = pStmt.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("Nueva propuesta pendiente insertada a las ["
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

	public boolean guardarPropuestaModificada(Propuesta p, String tituloAnterior) throws ParseException {

		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "UPDATE `democesi`.`propuestas` SET `origen_propuesta` = ?,`estado_propuesta` = ?,`autor_propuesta` = ?,"
					+ "`fecha_propuesta` = ?,`descripcion_propuesta` = ?,`motivacion_propuesta` = ?,`motivo_propuesta` = ?,"
					+ "`id_categoria_propuesta` = ?,`titulo_propuesta` = ? WHERE `titulo_propuesta` = ?";

			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setString(1, p.getOrigen());
			pStmt.setString(2, p.getEstado());
			pStmt.setString(3, p.getAutor());
			pStmt.setDate(4, p.getFecha());
			pStmt.setString(5, p.getDescripcion());
			pStmt.setString(6, p.getMotivacion());
			pStmt.setString(7, p.getMotivo());
			pStmt.setInt(8, new CategoriaDAO().buscarCategoria(p.getDescripcionCategoria()));
			pStmt.setString(9, p.getTitulo());
			pStmt.setString(10, tituloAnterior);
			filasAfectadas = pStmt.executeUpdate();
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

	public boolean eliminarPropuestaExistente(String tituloAnterior) {
		int filasAfectadas = 0;
		Connection c = null;
		try {
			c = conectar();
			String sql = "DELETE FROM `democesi`.`propuestas` WHERE titulo_propuesta = ?;";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setString(1, tituloAnterior);
			filasAfectadas = pStmt.executeUpdate();
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

	public ArrayList<Propuesta> traerTodasLasPropuestas() {
		String columnaOrigen = "origen_propuesta";
		String columnaEstado = "estado_propuesta";
		String columnaAutor = "autor_propuesta";
		String columnaFecha = "fecha_propuesta";
		String columnaDescripcion = "descripcion_propuesta";
		String columnaMotivacion = "motivacion_propuesta";
		String columnaMotivo = "motivo_propuesta";
		String columnaTitulo = "titulo_propuesta";
		String columnaIDCategoria = "id_categoria_propuesta";
		ArrayList<Propuesta> propuestas = new ArrayList<Propuesta>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `propuestas`.`origen_propuesta`,`propuestas`.`estado_propuesta`,`propuestas`.`autor_propuesta`,"
					+ "`propuestas`.`fecha_propuesta`,`propuestas`.`descripcion_propuesta`,`propuestas`.`motivacion_propuesta`,"
					+ "`propuestas`.`motivo_propuesta`,`propuestas`.`id_categoria_propuesta`,`propuestas`.`titulo_propuesta` FROM"
					+ " `democesi`.`propuestas`";
			Statement stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				String origen = rs.getString(columnaOrigen);
				String estado = rs.getString(columnaEstado);
				String categoria = new CategoriaDAO().buscarCategoria(rs.getInt(columnaIDCategoria));
				String autor = rs.getString(columnaAutor);
				Date fecha = Date.valueOf(rs.getString(columnaFecha));
				String descripcion = rs.getString(columnaDescripcion);
				String motivacion = rs.getString(columnaMotivacion);
				String motivo = rs.getString(columnaMotivo);
				Propuesta pr = new Propuesta(titulo, estado, origen, autor, fecha, descripcion, motivacion, motivo,
						categoria);
				propuestas.add(pr);
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
		return propuestas;
	}

	public ArrayList<Propuesta> traerTodasLasPropuestas(String estadoElegido) {
		String columnaOrigen = "origen_propuesta";
		String columnaEstado = "estado_propuesta";
		String columnaAutor = "autor_propuesta";
		String columnaFecha = "fecha_propuesta";
		String columnaDescripcion = "descripcion_propuesta";
		String columnaMotivacion = "motivacion_propuesta";
		String columnaMotivo = "motivo_propuesta";
		String columnaTitulo = "titulo_propuesta";
		String columnaIDCategoria = "id_categoria_propuesta";
		ArrayList<Propuesta> propuestas = new ArrayList<Propuesta>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `propuestas`.`id_propuesta`,`propuestas`.`origen_propuesta`,`propuestas`.`estado_propuesta`,`propuestas`.`autor_propuesta`,`propuestas`.`fecha_propuesta`,`propuestas`.`descripcion_propuesta`,`propuestas`.`motivacion_propuesta`,`propuestas`.`motivo_propuesta`,`propuestas`.`id_categoria_propuesta`,`propuestas`.`titulo_propuesta` FROM `democesi`.`propuestas`\r\n"
					+ " where estado_propuesta = ?";
			PreparedStatement pStmt = c.prepareStatement(sql);
			pStmt.setString(1, estadoElegido);
			System.out.println("consulta ejecutada: " + pStmt.toString());
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				String origen = rs.getString(columnaOrigen);
				String estado = rs.getString(columnaEstado);
				String categoria = new CategoriaDAO().buscarCategoria(rs.getInt(columnaIDCategoria));
				String autor = rs.getString(columnaAutor);
				Date fecha = Date.valueOf(rs.getString(columnaFecha));
				String descripcion = rs.getString(columnaDescripcion);
				String motivacion = rs.getString(columnaMotivacion);
				String motivo = rs.getString(columnaMotivo);
				Propuesta pr = new Propuesta(titulo, estado, origen, autor, fecha, descripcion, motivacion, motivo,
						categoria);
				propuestas.add(pr);
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
		return propuestas;
	}

	public ArrayList<Propuesta> traerTodasLasPropuestasIdYTitulo() {
		String columnaTitulo = "titulo_propuesta";
		String columnaID = "id_propuesta";
		ArrayList<Propuesta> propuestas = new ArrayList<Propuesta>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `propuestas`.`titulo_propuesta`, `propuestas`.`id_propuesta` FROM"
					+ " `democesi`.`propuestas`";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				int id = rs.getInt(columnaID);
				Propuesta pr = new Propuesta(titulo, id);
				propuestas.add(pr);
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
		return propuestas;
	}

	public boolean guardarMaterialesAUnaPropuesta(ArrayList<Material> materialesElegidos, Propuesta propElegida) {
		int filasAfectadas = 0;
		Connection c = null;
		ArrayList<Propuesta> propuestasIDTitulo = traerTodasLasPropuestasIdYTitulo();
		int idPropuestaElegida = 0;
		for (Propuesta p : propuestasIDTitulo) {
			if (p.getTitulo().equalsIgnoreCase(propElegida.getTitulo())) {
				idPropuestaElegida = p.getId();
			}
		}

		ArrayList<Material> materialesIDTitulo = new MaterialDAO().traerTodasLosMaterialesIdYTitulo();

		ArrayList<Material> materialesElegidosConIdYTitulo = new ArrayList<>();

		for (Material m : materialesElegidos) {
			for (Material mat : materialesIDTitulo) {
				if (m.getTitulo().equalsIgnoreCase(mat.getTitulo())) {
					materialesElegidosConIdYTitulo.add(mat);
				}
			}
		}
		for (Material m : materialesElegidosConIdYTitulo) {
			try {
				c = conectar();
				String sql = "INSERT INTO `democesi`.`materiales_propuestas` (`id_material`, `id_propuesta`) VALUES (?,?)";
				PreparedStatement pStmt = c.prepareStatement(sql);
				pStmt.setInt(1, m.getId());
				pStmt.setInt(2, idPropuestaElegida);
				filasAfectadas = pStmt.executeUpdate();
				if (filasAfectadas > 0) {
					System.out.println("Nueva propuesta pendiente insertada a las ["
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

	public Propuesta traerUnaPropuestaSegunID(int idABuscar) {
		Propuesta propEncontrada = new Propuesta();
		String columnaOrigen = "origen_propuesta";
		String columnaEstado = "estado_propuesta";
		String columnaAutor = "autor_propuesta";
		String columnaFecha = "fecha_propuesta";
		String columnaDescripcion = "descripcion_propuesta";
		String columnaMotivacion = "motivacion_propuesta";
		String columnaMotivo = "motivo_propuesta";
		String columnaTitulo = "titulo_propuesta";
		String columnaIDCategoria = "id_categoria_propuesta";
		String columnaIDPropuesta = "id_propuesta";
		ArrayList<Propuesta> propuestas = new ArrayList<Propuesta>();
		Connection c = null;
		try {
			c = conectar();
			String sql = "SELECT `propuestas`.`origen_propuesta`,`propuestas`.`estado_propuesta`,`propuestas`.`autor_propuesta`,"
					+ "`propuestas`.`fecha_propuesta`,`propuestas`.`descripcion_propuesta`,`propuestas`.`motivacion_propuesta`,"
					+ "`propuestas`.`motivo_propuesta`,`propuestas`.`id_categoria_propuesta`,`propuestas`.`titulo_propuesta`, `propuestas`.`id_propuesta` FROM"
					+ " `democesi`.`propuestas`";
			Statement stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String titulo = rs.getString(columnaTitulo);
				String origen = rs.getString(columnaOrigen);
				String estado = rs.getString(columnaEstado);
				String categoria = new CategoriaDAO().buscarCategoria(rs.getInt(columnaIDCategoria));
				String autor = rs.getString(columnaAutor);
				Date fecha = Date.valueOf(rs.getString(columnaFecha));
				String descripcion = rs.getString(columnaDescripcion);
				String motivacion = rs.getString(columnaMotivacion);
				String motivo = rs.getString(columnaMotivo);
				int idProp = rs.getInt(columnaIDPropuesta);
				Propuesta pr = new Propuesta(titulo, estado, origen, autor, fecha, descripcion, motivacion, motivo,
						categoria);
				pr.setId(idProp);
				propuestas.add(pr);
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
		for (Propuesta p : propuestas) {
			if (p.getId() == idABuscar) {
				propEncontrada = p;
			}
		}
		return propEncontrada;
	}

	public ArrayList<Propuesta> traerTodasLasPropuestasAprobadas() {
		return traerTodasLasPropuestas(Propuesta.ESTADO_APROBADO);
	}
}
