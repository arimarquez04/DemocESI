package DemocESI.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import DemocESI.dao.MaterialDAO;
import DemocESI.dao.MaterialPorPropuestaDAO;
import DemocESI.dao.PropuestaDAO;
import DemocESI.modelo.Material;
import DemocESI.modelo.MaterialPorPropuesta;
import DemocESI.modelo.Propuesta;

import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class MostrarPropuestasAsociadasAUnMaterialPorPropuestasPanel extends JPanel {
	private JTable table;
	private	ArrayList<Propuesta> propuestas = new ArrayList<Propuesta>();

	/**
	 * Create the panel.
	 */
	public MostrarPropuestasAsociadasAUnMaterialPorPropuestasPanel(MaterialPorPropuesta matProp) {
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("Mostrar Propuestas Asociadas a un Material");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 430, 14);
		add(lblTitulo);
		
		table = new JTable();
		table.setBounds(10, 51, 213, 328);
		add(table);
		
		
		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEliminar.setBounds(233, 96, 89, 23);
		add(btnEliminar);
		
		JButton btnVolver = new JButton("VOLVER");
		btnVolver.setBounds(233, 282, 89, 23);
		add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new VerMaterialesPanel());
				marco.validate();
			}
		});
		
		
		
		propuestas = new PropuestaDAO().traerTodasLasPropuestasAprobadas();
		
		DefaultListModel<String> l1 = new DefaultListModel<>();
		Propuesta prop = new Propuesta();
		for (Propuesta p : propuestas) {
			if ((p.getDescripcionCategoria().equalsIgnoreCase(matProp.getDescripcionCategoria()))
					&&(p.getEstado().equalsIgnoreCase(prop.getEstadoaprobrado()))) {
				l1.addElement(p.getTitulo());
				
				
			}
		}
		JList<String> list = new JList<>(l1);
		list.setBounds(345, 50, 173, 329);
		add(list);
		
		
		
		
		JButton btnAgregar = new JButton("AGREGAR");
		btnAgregar.setBounds(528, 96, 89, 23);
		add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//obtengo los titulos de los seleccionados
				Object[] seleccionados = list.getSelectedValues();

				//crear arraylist de TODAS las propuestas con id y titulo
				ArrayList<Propuesta> todasLasPropuestasConIdYTitulo = new ArrayList<>();
				PropuestaDAO propDao = new PropuestaDAO();
				todasLasPropuestasConIdYTitulo = propDao.traerTodasLasPropuestasIdYTitulo();
				
				//crar arraylist vacio para ser llenado con las propuesta(con id y titulo) seleccionadas
				ArrayList<Propuesta> propuestasSeleccionadas = new ArrayList<>();

				//lleno el arraylist "propuestasSeleccionadas"
				for (Object o : seleccionados) {
					for (Propuesta p : todasLasPropuestasConIdYTitulo) {
						if (o.toString().equalsIgnoreCase(p.getTitulo())) {
							propuestasSeleccionadas.add(p);
						}
					}
				}
				
				//agrego las propuestas seleccionadas al materialProp
				for(Propuesta p : propuestasSeleccionadas) {
					matProp.agregarPropuesta(p);
				}			
				
				MaterialPorPropuestaDAO matDao = new MaterialPorPropuestaDAO();
//				MaterialPorPropuesta nuevomatProp = matDao.buscarMaterialPropTraerIDyTitulo(matProp);
				
				//recuperar id de material
				matProp.setId(matDao.buscarIdMaterial(matProp));
				matDao.guardarPropuestasAUnMaterial(matProp);
				
				
				//1. Recuperar id de material
				//2. Recuperar id de las propuestas.
				//3. Insertar en tabla intermedia
				
				/*
				
				//ArrayList<Propuesta>
				*/
				
			}
		});
		
		
		
		
	}
}
