package DemocESI.vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import DemocESI.dao.CategoriaDAO;
import DemocESI.dao.MaterialDAO;
import DemocESI.dao.MaterialInstitucionalDAO;
import DemocESI.dao.MaterialPorPropuestaDAO;
import DemocESI.dao.PropuestaDAO;
import DemocESI.modelo.Categoria;
import DemocESI.modelo.Material;
import DemocESI.modelo.MaterialInstitucional;
import DemocESI.modelo.MaterialPorPropuesta;
import DemocESI.modelo.Propuesta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.Font;

public class VerMaterialesPanel extends JPanel {
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JButton btnCrearMaterial;
	private JButton btnVerDetalles;
	private JLabel lblCualesSeMuestran;
	private JButton btnCualesSeMuestran;
	private ArrayList<Material> materiales = new ArrayList<Material>();
	private ArrayList<MaterialPorPropuesta> materialesProp = new ArrayList<MaterialPorPropuesta>();
	private ArrayList<MaterialInstitucional> materialesInst = new ArrayList<MaterialInstitucional>();
	private boolean esPorPropuesta;

	/**
	 * Create the panel.
	 */
	public VerMaterialesPanel() {
		setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 365, 344);
		add(scrollPane);

		table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(new Object[][] {

		}, new String[] { "Titulo", "Categoria", "Descripcion", "Prioridad" });

		table.setModel(dataModel);
		scrollPane.setViewportView(table);
		lblNewLabel = new JLabel("VerMaterialesPanel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(42, 11, 327, 14);
		add(lblNewLabel);

		btnCrearMaterial = new JButton("Crear Material");
		btnCrearMaterial.setBounds(395, 114, 124, 23);
		btnCrearMaterial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new CrearMaterialPanel());
				marco.validate();

			}
		});
		add(btnCrearMaterial);

		btnVerDetalles = new JButton("Ver Detalles");
		btnVerDetalles.setBounds(395, 69, 122, 23);

		btnVerDetalles.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				
				System.out.println(esPorPropuesta);
				if(esPorPropuesta) {
				MaterialPorPropuesta matSeleccionado =  obtenerMaterialSeleccionado(esPorPropuesta);
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new CrearMaterialPanel(matSeleccionado));
				marco.validate();
				}
				else {
					MaterialInstitucional matSeleccionado =  obtenerMaterialSeleccionado();
					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
					marco.setContentPane(new CrearMaterialPanel(matSeleccionado));
					marco.validate();
				}
			}
		});
		add(btnVerDetalles);

		JLabel lblNewLabel_1 = new JLabel("Consultar por Categoria");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(395, 175, 124, 14);
		add(lblNewLabel_1);

		JComboBox<String> comboBoxCategorias = new JComboBox<String>();
		comboBoxCategorias.setBounds(397, 200, 122, 22);
		ArrayList<Categoria> categorias = new ArrayList<>();
		comboBoxCategorias.addItem("Todo");
		categorias = new CategoriaDAO().traerTodasLasCategoria();
		for (Categoria c : categorias) {
			comboBoxCategorias.addItem(c.getDescripcion());
		}
		/*
		comboBoxCategorias.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (comboBoxCategorias.getSelectedItem().toString() == "Todo") {
					System.out.println("elegido buscar todo");
					cargarTablaMaterialesPropuesta(dataModel);
				} else {
					cargarTablaMaterialesPropuesta(dataModel, comboBoxCategorias.getSelectedItem().toString());
				}
			}
		});*/
		add(comboBoxCategorias);
		JButton btnCargarMaterialesInst = new JButton("Ver Institucionales");
		btnCargarMaterialesInst.setBounds(395, 301, 124, 23);
		add(btnCargarMaterialesInst);
		btnCargarMaterialesInst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargarTablaMaterialesInst(dataModel);
				esPorPropuesta = true;
			}
			
		});
		
		JButton btnCargarMaterialesProp = new JButton("Ver por Propuesta");
		btnCargarMaterialesProp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCargarMaterialesProp.setBounds(395, 257, 124, 23);
		add(btnCargarMaterialesProp);
		
		btnCargarMaterialesProp.addActionListener(new ActionListener() {
				@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargarTablaMaterialesPropuesta(dataModel);
				esPorPropuesta = true;
			}
		} );
	}

	private void cargarTablaMaterialesPropuesta(DefaultTableModel dataModel) {
		System.out.println("cargar tabla normal con prop");
		dataModel.setRowCount(0);
		MaterialPorPropuestaDAO matPropDao = new MaterialPorPropuestaDAO();
		ArrayList <MaterialPorPropuesta>matsProp = matPropDao.traerTodasLosMaterialesPropCompletos();
		for (MaterialPorPropuesta p : matsProp) {
			Object[] fila = new Object[] { p.getTitulo(), p.getDescripcionCategoria(), p.getDescripcion(),
					p.esPrioritario() };
			dataModel.addRow(fila);
			materialesProp.add(p);
		}
	}

	private void cargarTablaMaterialesPropuesta(DefaultTableModel dataModel, String categoria) {
		System.out.println("cargar tabla con prop con parametros");
		dataModel.setRowCount(0);
		MaterialPorPropuestaDAO matPropDao = new MaterialPorPropuestaDAO();
		ArrayList <MaterialPorPropuesta>matsProp = matPropDao.traerTodasLosMaterialesPropCompletos();
		for (MaterialPorPropuesta p : matsProp) {
			if (p.getDescripcionCategoria().equalsIgnoreCase(categoria)) {
				Object[] fila = new Object[] { p.getTitulo(), p.getDescripcionCategoria(), p.getDescripcion(),
						p.esPrioritario(), };
				dataModel.addRow(fila);
				materialesProp.add(p);
			}
		}
	}
	
	private void cargarTablaMaterialesInst(DefaultTableModel dataModel) {
		System.out.println("cargar tabla normal cons inst");
		dataModel.setRowCount(0);
		MaterialInstitucionalDAO matInstDao = new MaterialInstitucionalDAO();
		ArrayList <MaterialInstitucional>matsInst = matInstDao.traerTodasLosMaterialesInstCompletos();
		for (MaterialInstitucional p : matsInst) {
			Object[] fila = new Object[] { p.getTitulo(), p.getDescripcionCategoria(), p.getDescripcion(),
					p.esPrioritario() };
			dataModel.addRow(fila);
			materialesInst.add(p);
		}
	}
	private void cargarTablaMaterialesInst(DefaultTableModel dataModel, String categoria) {
		System.out.println("cargar tabla con inst con parametros");
		dataModel.setRowCount(0);
		MaterialInstitucionalDAO matInstDao = new MaterialInstitucionalDAO();
		ArrayList <MaterialInstitucional>materialesInst = matInstDao.traerTodasLosMaterialesInstCompletos();
		for (MaterialInstitucional p : materialesInst) {
			if (p.getDescripcionCategoria().equalsIgnoreCase(categoria)) {
				Object[] fila = new Object[] { p.getTitulo(), p.getDescripcionCategoria(), p.getDescripcion(),
						p.esPrioritario(), };
				dataModel.addRow(fila);
				materiales.add(p);
			}
		}
	}

	private MaterialPorPropuesta obtenerMaterialSeleccionado(boolean esPorPropuesta) {
		// TODO Mejorar (evitar relacionar el �ndice de la tabla con el �ndice del
		// ArrayList)
		int filaSeleccionada = table.getSelectedRow();
		return materialesProp.get(filaSeleccionada);

	}
	private MaterialInstitucional obtenerMaterialSeleccionado() {
		// TODO Mejorar (evitar relacionar el �ndice de la tabla con el �ndice del
		// ArrayList)
		int filaSeleccionada = table.getSelectedRow();
		return materialesInst.get(filaSeleccionada);

	}
}
