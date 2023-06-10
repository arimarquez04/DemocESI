package DemocESI.modelo;

import java.sql.Date;
//import java.time.*;
import java.time.LocalDate;

//import javax.xml.crypto.Data;
public class Propuesta {
	
	public static final String ESTADO_APROBADO = "aprobado";
	public static final String ESTADO_PENDIENTE = "pendiente";
	public static final String ESTADO_RECHAZADO = "rechazado";
	private String titulo;
	private String estado;
	private String origen;
	private String descripcionCategoria;
	
	private int id;
	
	private String autor;
	private Date fecha;//arreglar
	private String descripcion;
	private String motivacion;
	private String motivo;
	public Propuesta() {
		
	}
	public Propuesta(String titulo, int id) {
		this.titulo = titulo;
		this.id = id;
	}
	
	public Propuesta(String estado) {
		this.estado = estado;
	}
	
	public Propuesta(String titulo, String estado, String origen , String autor, Date fecha2, String descripcion, String motivacion, String motivo, String descripcionCategoria) {
		this.titulo = titulo;
		this.estado = estado;
		this.origen = origen;
		this.descripcionCategoria = descripcionCategoria;
		this.autor = autor;
		this.fecha = fecha2;
		this.descripcion = descripcion;
		this.motivacion = motivacion;
		this.motivo = motivo;
	}
	

	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}

	public void setdescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}


	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMotivacion() {
		return motivacion;
	}

	public void setMotivacion(String motivacion) {
		this.motivacion = motivacion;
	}

	public void cambiarEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoaprobrado() {
		return ESTADO_APROBADO;
	}

	public static String getEstadopendiente() {
		return ESTADO_PENDIENTE;
	}

	public static String getEstadorechazado() {
		return ESTADO_RECHAZADO;
	}

	public String getEstado() {
		return estado;
	}

	public Date getFecha() {

		return fecha;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


}
