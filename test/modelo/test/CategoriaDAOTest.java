package modelo.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import DemocESI.dao.CategoriaDAO;
import DemocESI.dao.PropuestaDAO;
import DemocESI.modelo.Propuesta;

class CategoriaDAOTest {

	@Test
	void test() {
		CategoriaDAO c = new CategoriaDAO();
		Propuesta p = new Propuesta();
		p.setdescripcionCategoria("Drogas");
		CategoriaDAO cdao = new CategoriaDAO();
		assertEquals(p.getDescripcionCategoria(), cdao.buscarCategoria(p.getDescripcionCategoria()));
		
	}

}
