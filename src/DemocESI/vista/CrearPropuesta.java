package DemocESI.vista;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import DemocESI.dao.CategoriaDAO;
import DemocESI.dao.PropuestaDAO;
import DemocESI.modelo.Categoria;
import DemocESI.modelo.MaterialPorPropuesta;
import DemocESI.modelo.Propuesta;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class CrearPropuesta extends JPanel {
	private JTextField text_Autor;
	private JTextField text_Fecha;
	private JTextField text_Descripcion;
	private JTextField text_Motivacion;
	private JTextField text_Origen;
	private JTextField text_Titulo;
	private Propuesta prop;
	private JComboBox<String> comboBox_Categoria;
	private JLabel lblNewLabel_8;
	private JTextField text_MotivoRechazo;
	private boolean estaRechazando;
	private JButton btn_RechazarPropuesta;
	private JButton btnAceptarPropuestaEIrAAgregarPropuestaAMaterial;

	/**
	 * Create the panel.
	 */
	public CrearPropuesta() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("CREAR O MODIFICAR PROPUESTA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(74, 11, 283, 14);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("AUTOR");
		lblNewLabel_1.setBounds(36, 66, 86, 14);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("FECHA ");
		lblNewLabel_2.setBounds(36, 134, 86, 14);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("CATEGOR\u00CDA");
		lblNewLabel_3.setBounds(10, 205, 125, 14);
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("DESCRIPCI\u00D3N");
		lblNewLabel_4.setBounds(309, 66, 85, 14);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("MOTIVACI\u00D3N");
		lblNewLabel_5.setBounds(309, 162, 85, 14);
		add(lblNewLabel_5);
		text_Origen = new JTextField();
		text_Origen.setBounds(184, 91, 86, 20);
		add(text_Origen);
		text_Origen.setColumns(10);

		text_Titulo = new JTextField();
		text_Titulo.setBounds(184, 159, 86, 20);
		add(text_Titulo);
		text_Titulo.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Titulo");
		lblNewLabel_6.setBounds(184, 134, 46, 14);
		add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Origen");
		lblNewLabel_7.setBounds(184, 66, 46, 14);
		add(lblNewLabel_7);

		text_Autor = new JTextField();
		text_Autor.setBounds(36, 91, 86, 20);
		add(text_Autor);
		text_Autor.setColumns(10);

		text_Fecha = new JTextField();
		text_Fecha.setBounds(33, 159, 86, 20);
		add(text_Fecha);
		text_Fecha.setColumns(10);

		text_Descripcion = new JTextField();
		text_Descripcion.setBounds(309, 91, 86, 57);
		add(text_Descripcion);
		text_Descripcion.setColumns(10);

		text_Motivacion = new JTextField();
		text_Motivacion.setBounds(309, 180, 86, 70);
		add(text_Motivacion);
		text_Motivacion.setColumns(10);

		comboBox_Categoria = new JComboBox<String>();
		comboBox_Categoria.setBounds(10, 230, 125, 22);
		CategoriaDAO cDao = new CategoriaDAO();
		ArrayList<Categoria> categorias = cDao.traerTodasLasCategoria();
		for (Categoria c : categorias) {
			comboBox_Categoria.addItem(c.getDescripcion());
		}
		add(comboBox_Categoria);

		JButton btn_Guardar = new JButton("GUARDAR");
		JButton btn_Volver = new JButton("VOLVER");
		btn_Volver.setBounds(33, 266, 89, 23);
		btn_Volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new VerPropuestaPanel());
				marco.validate();

			}
		});
		add(btn_Volver);

		btn_Guardar.setBounds(309, 266, 89, 23);
		btn_Guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String estado;
				String titulo = text_Titulo.getText();
				if (estaRechazando) {
					estado = new Propuesta().getEstadorechazado();
				} else {
					estado = new Propuesta().getEstadopendiente();
				}
				String origen = text_Origen.getText();
				String descripcionCategoria = comboBox_Categoria.getSelectedItem().toString();
				String autor = text_Autor.getText();
				Date fecha = Date.valueOf(text_Fecha.getText());
				String descripcion = text_Descripcion.getText();
				String motivacion = text_Motivacion.getText();
				String motivo;
				if (estaRechazando) {
					motivo = text_MotivoRechazo.getText();
				} else {
					motivo = null;
				}
				Propuesta nuevaPropuesta = new Propuesta(titulo, estado, origen, autor, fecha, descripcion, motivacion,
						motivo, descripcionCategoria);
				PropuestaDAO pDao = new PropuestaDAO();
				if (esEdicion() == true) {
					try {
						System.out.println("peticion de guardar modificacion enviada a PropuestDAO");
						pDao.guardarPropuestaModificada(nuevaPropuesta, prop.getTitulo());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {

					try {
						pDao.guardarNuevaPropuesta(nuevaPropuesta);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new VerPropuestaPanel());
				marco.validate();
			}
		});
		add(btn_Guardar);

		JLabel lblNewLabel_10 = new JLabel("(aaaa-mm-dd)");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setBounds(36, 180, 85, 14);
		add(lblNewLabel_10);

	}

	public CrearPropuesta(Propuesta prop) {
		this();
		btn_RechazarPropuesta = new JButton("RECHAZAR PROPUESTA");
		btnAceptarPropuestaEIrAAgregarPropuestaAMaterial = new JButton("Aceptar Propuesta");
		add(btn_RechazarPropuesta);
		add(btnAceptarPropuestaEIrAAgregarPropuestaAMaterial);

		this.prop = prop;
		text_Autor.setText(prop.getAutor());
		text_Fecha.setText(prop.getFecha().toString());
		text_Descripcion.setText(prop.getDescripcion());
		text_Motivacion.setText(prop.getMotivacion());
		text_Origen.setText(prop.getOrigen());
		text_Titulo.setText(prop.getTitulo());
		comboBox_Categoria.setSelectedItem(prop.getDescripcionCategoria());
		if(prop.getEstado().equalsIgnoreCase(new Propuesta().getEstadorechazado())) {
			text_MotivoRechazo = new JTextField();
			text_MotivoRechazo.setText(prop.getMotivo());
			text_MotivoRechazo.setBounds(446, 159, 86, 20);
			text_MotivoRechazo.setColumns(10);
			add(text_MotivoRechazo);
			JLabel lblNewLabel_9 = new JLabel("Motivo");
			lblNewLabel_9.setBounds(463, 134, 46, 14);
			add(lblNewLabel_9);
			
		}
		
		btn_RechazarPropuesta.setBounds(132, 266, 167, 23);
		btn_RechazarPropuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JLabel lblNewLabel_9 = new JLabel("Motivo");
				lblNewLabel_9.setBounds(463, 134, 46, 14);
				add(lblNewLabel_9);
				text_MotivoRechazo = new JTextField();
				text_MotivoRechazo.setBounds(446, 159, 86, 20);
				text_MotivoRechazo.setColumns(10);
				add(text_MotivoRechazo);
				estaRechazando = true;		
				remove(btn_RechazarPropuesta);
				remove(btnAceptarPropuestaEIrAAgregarPropuestaAMaterial);
				repaint();
			}
		});
		
		btnAceptarPropuestaEIrAAgregarPropuestaAMaterial.setBounds(160, 227, 125, 23);
		btnAceptarPropuestaEIrAAgregarPropuestaAMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String titulo = text_Titulo.getText();
				String estado = new Propuesta().getEstadoaprobrado();
				String origen = text_Origen.getText();
				String autor = text_Autor.getText();
				Date fecha = Date.valueOf(text_Fecha.getText());
				String descripcion = text_Descripcion.getText();
				String motivacion = text_Motivacion.getText();
				String motivo = null;
				String descripcionCategoria = comboBox_Categoria.getSelectedItem().toString();
				Propuesta p = new Propuesta(titulo, estado, origen, autor, fecha, descripcion, motivacion, motivo,
						descripcionCategoria);
				PropuestaDAO pDao = new PropuestaDAO();
				try {
					pDao.guardarPropuestaModificada(p, titulo);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new AgregarPropuestaAMaterialPanel(p));
				marco.validate();
			}
		
		});

		
	}

	public boolean esEdicion() {
		return this.prop != null;

	}
}
