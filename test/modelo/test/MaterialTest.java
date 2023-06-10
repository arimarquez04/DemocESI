package modelo.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import DemocESI.dao.MaterialDAO;
import DemocESI.dao.MaterialInstitucionalDAO;
import DemocESI.modelo.Material;
import DemocESI.modelo.MaterialInstitucional;
import DemocESI.modelo.MaterialPorPropuesta;

class MaterialTest {

	/*
	@Test
	void test() {
		MaterialInstitucional mat = new MaterialInstitucional();
		MaterialDAO matDao = new MaterialDAO();
		ArrayList<Material> materiales = matDao.traerTodasLosMateriales();
		String resultadoEsperado = "";
		for(Material m :materiales) {
			if(m.getTipo().equalsIgnoreCase(mat.getTipo())) {
				mat.setDescripcion(m.getDescripcion());
				mat.setDescripcionCategoria(m.getDescripcionCategoria());
				mat.setEnlace(m.getEnlace());
				mat.setDescripcion(m.getDescripcion());
				mat.setPrioridad(m.esPrioritario());
				mat.setProcedencia(m.getProcedencia());
				mat.setTitulo(m.getTitulo());
				resultadoEsperado = m.getProcedencia();
			}
		}
		assertEquals(resultadoEsperado, mat.getProcedencia());
	}
	*/
	@Test
	void test2() {
	}
}
