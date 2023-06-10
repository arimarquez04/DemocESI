package DemocESI.modelo;

//import java.util.ArrayList;

public abstract class Material {
	private int id;
	private String titulo;
	private String descripcion;
	private String fuente;
	private String enlace;
	private String descripcionCategoria;

	public Material() {

	}

	public Material(String titulo, int id) {
		this.id = id;
		this.titulo = titulo;
	}

	public Material(String titulo, String descripcion, String fuente, String enlace, String descripcionCategoria) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.enlace = enlace;
		this.fuente = fuente;
		this.descripcionCategoria = descripcionCategoria;
	}

	public abstract boolean esPrioritario();

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}

	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
