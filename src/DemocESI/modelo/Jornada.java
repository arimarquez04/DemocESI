package DemocESI.modelo;

import java.util.ArrayList;

public class Jornada {
	private ArrayList<Material> materiales = new ArrayList<>();

	private String referente;
	private String objetivo;
	private String titulo;

	public void agregarMaterial(Material x) {

		materiales.add(x);
	}

	public boolean esPrioritario() {

		return cuantosMaterialesPrioritarios() >= (cuantosMateriales() / 2);

	}

	public int cuantosMateriales() {

		return materiales.size();
	}

	public int cuantosMaterialesPrioritarios() {
		int a = 0;
		for (Material x : materiales) {
			if (x.esPrioritario()) {
				a++;
			}
		}
		return a;
	}

	public String getReferente() {
		return referente;
	}

	public void setReferente(String referente) {
		this.referente = referente;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
