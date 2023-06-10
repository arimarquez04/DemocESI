package DemocESI.vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import DemocESI.dao.MaterialDAO;
import DemocESI.dao.MaterialPorPropuestaDAO;
import DemocESI.dao.PropuestaDAO;
import DemocESI.modelo.Material;
import DemocESI.modelo.MaterialPorPropuesta;
import DemocESI.modelo.Propuesta;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarPropuestaAMaterialPanel extends JPanel {
	private ArrayList<Material> materiales;
	private JTextField textCategoriaDePropuesta;

	/**
	 * Create the panel.
	 */
	public AgregarPropuestaAMaterialPanel(Propuesta p) {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("PROPUESTA");
		lblNewLabel.setBounds(164, 5, 59, 14);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("SELECCION\u00C1 EL MATERIAL AL QUE QUER\u00C9S AGREGAR TU PROPUESTA");
		lblNewLabel_1.setBounds(52, 30, 378, 14);
		add(lblNewLabel_1);
		JButton btn_AgregarASeleccionados = new JButton("AGREGAR A SELECCIONADOS");
		btn_AgregarASeleccionados.setBounds(253, 363, 202, 23);
		add(btn_AgregarASeleccionados);

		JButton btn_AhoraNo = new JButton("AHORA NO");
		btn_AhoraNo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new VerMaterialesPanel());
				marco.validate();
			}
		});
		btn_AhoraNo.setBounds(134, 363, 89, 23);
		add(btn_AhoraNo);

		JButton btn_Volver = new JButton("VOLVER");
		btn_Volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new VerMaterialesPanel());
				marco.validate();
			}
		});
		btn_Volver.setBounds(10, 363, 89, 23);
		add(btn_Volver);

		DefaultListModel<String> l1 = new DefaultListModel<>();
		ArrayList<MaterialPorPropuesta> materiales = new ArrayList<>();
		materiales = new MaterialPorPropuestaDAO().traerTodasLosMaterialesPropCompletos();
		for (MaterialPorPropuesta m : materiales) {
			if (m.getDescripcionCategoria().equalsIgnoreCase(p.getDescripcionCategoria())) {
				l1.addElement(m.getTitulo());
			}
		}
		JList<String> list = new JList<String>(l1);
		list.setBounds(39, 55, 349, 295);
		add(list);

		JLabel lblCategoriaDePropuesta = new JLabel("Categoria:");
		lblCategoriaDePropuesta.setBounds(426, 99, 69, 14);
		add(lblCategoriaDePropuesta);

		textCategoriaDePropuesta = new JTextField();
		textCategoriaDePropuesta.setEditable(false);
		textCategoriaDePropuesta.setBounds(415, 124, 86, 20);
		textCategoriaDePropuesta.setText(p.getDescripcionCategoria());
		add(textCategoriaDePropuesta);
		textCategoriaDePropuesta.setColumns(10);
		
		btn_AgregarASeleccionados.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				List<String> seleccionados = list.getSelectedValuesList();
				ArrayList<Material> materialesElegidos = new ArrayList<Material>();
				ArrayList<Material> materiales = new ArrayList<>();
				materiales = new MaterialDAO().traerTodasLosMateriales();
				for (Object o : seleccionados) {
					for (Material m : materiales) {
						if (o.toString().equalsIgnoreCase(m.getTitulo())) {
							materialesElegidos.add(m);
						}
					}
				}
				new PropuestaDAO().guardarMaterialesAUnaPropuesta(materialesElegidos, p);
				System.out.println("se llego!!!");
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new VerPropuestaPanel());
				marco.validate();
			}
		});

		
		/*
		 * private void cargarTabla(DefaultTableModel dataModel) {
		 * System.out.println("cargar tabla normal"); dataModel.setRowCount(0); mate
		 * materiales = pDao.traerTodasLasPropuestas(); for (Propuesta p : propuestas) {
		 * Object[] fila = new Object[] { p.getTitulo(), p.getDescripcionCategoria(),
		 * p.getAutor(), p.getEstado() }; dataModel.addRow(fila); } }
		 * 
		 * private void cargarTabla(DefaultTableModel dataModel, String estado) {
		 * System.out.println("cargar tabla con parametros"); dataModel.setRowCount(0);
		 * PropuestaDAO pDao = new PropuestaDAO(); propuestas =
		 * pDao.traerTodasLasPropuestas(); for (Propuesta p : propuestas) { if
		 * (p.getEstado().equalsIgnoreCase(estado)) { Object[] fila = new Object[] {
		 * p.getTitulo(), p.getDescripcionCategoria(), p.getAutor(), p.getEstado() };
		 * dataModel.addRow(fila); }
		 * 
		 * } }
		 * 
		 * private Propuesta obtenerPropuestaSeleccionada() { // TODO Mejorar (evitar
		 * relacionar el �ndice de la tabla con el �ndice del // ArrayList) int
		 * filaSeleccionada = table.getSelectedRow(); return
		 * propuestas.get(filaSeleccionada); }
		 */
	}
}
