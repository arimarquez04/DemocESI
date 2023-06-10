package DemocESI.vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import DemocESI.dao.PropuestaDAO;
import DemocESI.modelo.Propuesta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VerPropuestaPanel extends JPanel {
	private JTable table;
	private ArrayList<Propuesta> propuestas;
	/**
	 * Create the panel.
	 */
	public VerPropuestaPanel() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 365, 344);
		add(scrollPane);
		
		table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"Titulo", "Categoria	", "Autor", "Estado"
			}
		);
		table.setModel(dataModel);
		scrollPane.setViewportView(table);
		
		JFormattedTextField frmtdtxtfldVerpropuestaspanel = new JFormattedTextField();
		frmtdtxtfldVerpropuestaspanel.setBounds(25, 10, 160, 25);
		frmtdtxtfldVerpropuestaspanel.setText("VerPropuestasPanel");
		add(frmtdtxtfldVerpropuestaspanel);
		


		JButton btnIrACrearPropuesta = new JButton("Crear Nueva Propuesta");
		btnIrACrearPropuesta.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnIrACrearPropuesta.setBounds(385, 287, 160, 25);
		btnIrACrearPropuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new CrearPropuesta());
				marco.validate();
			}
		});
		add(btnIrACrearPropuesta);
		
		JComboBox<String> cBoxConsultarPorEstado = new JComboBox<String>();
		cBoxConsultarPorEstado.setBounds(386, 213, 159, 22);
		cBoxConsultarPorEstado.addItem("TODO");
		cBoxConsultarPorEstado.addItem(new Propuesta().getEstadopendiente().toUpperCase());
		cBoxConsultarPorEstado.addItem(new Propuesta().getEstadoaprobrado().toUpperCase());
		cBoxConsultarPorEstado.addItem(new Propuesta().getEstadorechazado().toUpperCase());
		cBoxConsultarPorEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cBoxConsultarPorEstado.getSelectedItem().toString()=="TODO") {
					System.out.println("elegido buscar todo");
					cargarTabla(dataModel);
				}
				else {
					cargarTabla(dataModel, cBoxConsultarPorEstado.getSelectedItem().toString());
				}
			}
		});
		add(cBoxConsultarPorEstado);
		
		JLabel lblNewLabel = new JLabel("Consultar por Estado");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(385, 188, 160, 14);
		add(lblNewLabel);
		cargarTabla(dataModel);
		
		JButton btnIrAVerDetalles = new JButton("Ver Detalles");
		btnIrAVerDetalles.setBounds(385, 123, 160, 25);
		btnIrAVerDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new CrearPropuesta(obtenerPropuestaSeleccionada()));
				marco.validate();
			}
		});
		add(btnIrAVerDetalles);
		
		/*JButton btnIrAMenu = new JButton("Volver al Menu");
		btnIrAMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new MenuPanel());
				marco.validate();
			}
		});

		add(btnIrAMenu);
*/
	}
	private void cargarTabla(DefaultTableModel dataModel) {
		System.out.println("cargar tabla normal");
		dataModel.setRowCount(0);
		PropuestaDAO pDao = new PropuestaDAO();
		propuestas = pDao.traerTodasLasPropuestas();
		for (Propuesta p : propuestas) {
			Object[] fila = new Object[] {p.getTitulo(), p.getDescripcionCategoria(), p.getAutor(), p.getEstado()};
			dataModel.addRow(fila);
		}
	}
	private void cargarTabla(DefaultTableModel dataModel, String estado) {
		System.out.println("cargar tabla con parametros");
		dataModel.setRowCount(0);
		PropuestaDAO pDao = new PropuestaDAO();
		propuestas = pDao.traerTodasLasPropuestas();
		for (Propuesta p : propuestas) {
			if(p.getEstado().equalsIgnoreCase(estado)) {
			Object[] fila = new Object[] {p.getTitulo(), p.getDescripcionCategoria(), p.getAutor(), p.getEstado()};
			dataModel.addRow(fila);
			}
			
		}
	}

	private Propuesta obtenerPropuestaSeleccionada() {
		// TODO Mejorar (evitar relacionar el índice de la tabla con el índice del ArrayList)
		int filaSeleccionada = table.getSelectedRow();
		return propuestas.get(filaSeleccionada);
	}
}
