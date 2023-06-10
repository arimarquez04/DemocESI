package DemocESI.modelo;

import java.util.ArrayList;

public class MaterialPorPropuesta extends Material {
//	private static final String TIPO = "propuesta";
	private ArrayList<Propuesta> propuestas = new ArrayList<>();
	
	public MaterialPorPropuesta() {

	}

	public MaterialPorPropuesta(String titulo, String descripcion, String fuente, String enlace,
			String descripcionCategoria) {
		super(titulo, descripcion, fuente, enlace, descripcionCategoria);

	}

	public MaterialPorPropuesta(String titulo, int id) {
		super(titulo, id);
	}

	

	public void agregarPropuesta(Propuesta x) {

		propuestas.add(x);
	}

	public int cuantasPropuestas() {
		return propuestas.size();
	}

	public boolean esPrioritario() {
		return cuantasPropuestas() > 4;
	}


	public ArrayList<Propuesta> getPropuestas() {
		return propuestas;
	}

	public void setPropuestas(ArrayList<Propuesta> propuestas) {
		this.propuestas = propuestas;
	}

}
