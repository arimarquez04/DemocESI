package DemocESI.modelo;

public class MaterialInstitucional extends Material {

	private String procedencia;
	private boolean prioridad;
	private final static String TIPO = "institucional";

	public MaterialInstitucional() {

	}

	public MaterialInstitucional(String descripcion, String fuente, String enlace, String procedencia,
			Boolean prioridad, String descripcionCategoria, String titulo) {
		super(titulo, descripcion, fuente, enlace, descripcionCategoria);
		this.prioridad = prioridad;
		this.procedencia = procedencia;
	}

	public MaterialInstitucional(String titulo, int id) {
		super(titulo, id);
	}

	public boolean esPrioritario() {
		return prioridad;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public void setPrioridad(boolean prioridad) {
		this.prioridad = prioridad;
	}

	public String getTipo() {
		return TIPO;
	}

}
