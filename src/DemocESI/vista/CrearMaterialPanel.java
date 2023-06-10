package DemocESI.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import DemocESI.dao.CategoriaDAO;
import DemocESI.dao.MaterialInstitucionalDAO;
import DemocESI.dao.MaterialPorPropuestaDAO;
import DemocESI.modelo.Categoria;
import DemocESI.modelo.Material;
import DemocESI.modelo.MaterialInstitucional;
import DemocESI.modelo.MaterialPorPropuesta;
import DemocESI.modelo.Propuesta;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class CrearMaterialPanel extends JPanel {
	private JTextField text_Titulo;
	private JTextField text_Enlace;
	private JTextField text_Fuente;
	private JTextField text_Descripcion;
	private JTextField text_Procedencia;
	private JCheckBox chckbx_Institucional;
	private JCheckBox chckbx_Prioritario;
	private JComboBox<String> comboBox_Categoria;
	private Material mat;

	/**
	 * Create the panel.
	 */
	public CrearMaterialPanel() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("MATERIALES");
		lblNewLabel.setBounds(168, 11, 85, 14);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("CREAR MATERIAL");
		lblNewLabel_1.setBounds(178, 36, 105, 14);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("T\u00CDTULO");
		lblNewLabel_2.setBounds(10, 61, 46, 14);
		add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("CATEGOR\u00CDA");
		lblNewLabel_4.setBounds(10, 161, 105, 14);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("ENLACE");
		lblNewLabel_5.setBounds(10, 111, 46, 14);
		add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("FUENTE");
		lblNewLabel_6.setBounds(10, 136, 46, 14);
		add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("DESCRIPCI\u00D3N");
		lblNewLabel_7.setBounds(237, 61, 95, 14);
		add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("INSTITUCIONAL");
		lblNewLabel_8.setBounds(193, 111, 78, 14);
		add(lblNewLabel_8);

		text_Titulo = new JTextField();
		text_Titulo.setBounds(66, 58, 86, 20);
		add(text_Titulo);
		text_Titulo.setColumns(10);

		text_Enlace = new JTextField();
		text_Enlace.setBounds(66, 111, 86, 20);
		add(text_Enlace);
		text_Enlace.setColumns(10);

		text_Fuente = new JTextField();
		text_Fuente.setBounds(66, 136, 86, 20);
		add(text_Fuente);
		text_Fuente.setColumns(10);

		text_Descripcion = new JTextField();
		text_Descripcion.setBounds(323, 61, 142, 135);
		add(text_Descripcion);
		text_Descripcion.setColumns(10);

		chckbx_Institucional = new JCheckBox("");
		chckbx_Institucional.setBounds(277, 108, 40, 23);
		chckbx_Institucional.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				text_Procedencia = new JTextField();
				text_Procedencia.setBounds(193, 208, 86, 67);
				add(text_Procedencia);
				text_Procedencia.setColumns(10);

				JLabel lblNewLabel_9 = new JLabel("PRIORITARIO");
				lblNewLabel_9.setBounds(193, 161, 78, 14);
				add(lblNewLabel_9);

				chckbx_Prioritario = new JCheckBox("");
				chckbx_Prioritario.setBounds(277, 161, 40, 23);
				add(chckbx_Prioritario);
				JLabel lblNewLabel_10 = new JLabel("PROCEDENCIA");
				lblNewLabel_10.setBounds(196, 182, 105, 14);
				add(lblNewLabel_10);
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.repaint();
			}
		});
		add(chckbx_Institucional);

		comboBox_Categoria = new JComboBox<String>();
		comboBox_Categoria.setBounds(10, 186, 142, 22);
		CategoriaDAO cDao = new CategoriaDAO();
		ArrayList<Categoria> categorias = cDao.traerTodasLasCategoria();
		for (Categoria c : categorias) {
			comboBox_Categoria.addItem(c.getDescripcion());
		}
		add(comboBox_Categoria);
		add(comboBox_Categoria);

		JButton btn_Volver = new JButton("VOLVER");
		btn_Volver.setBounds(10, 266, 89, 23);
		add(btn_Volver);
		btn_Volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new VerMaterialesPanel());
				marco.validate();
			}
		});

		JButton btn_Aceptar = new JButton("ACEPTAR");
		btn_Aceptar.setBounds(351, 266, 89, 23);
		add(btn_Aceptar);
		btn_Aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				// `descripcion_material`,`fuente_material`,`enlace_material`,`procedencia_material`,`prioridad_material`,`categoria_material`)
				if (esEdicion() == false) {
					String descripcion = text_Descripcion.getText();
					String fuente = text_Fuente.getText();
					String enlace = text_Enlace.getText();
					String procedencia = null;
					Boolean prioridad = false;
					try {
						procedencia = text_Procedencia.getText();
						prioridad = chckbx_Prioritario.isSelected();
					} catch (Exception e) {
						// TODO: handle exception
					}
					String categoria = comboBox_Categoria.getSelectedItem().toString();
					String titulo = text_Titulo.getText();
					if (chckbx_Institucional.isSelected()) {
						MaterialInstitucional matInst = new MaterialInstitucional(descripcion, fuente, enlace,
								procedencia, prioridad, categoria, titulo);
						MaterialInstitucionalDAO matInstDao = new MaterialInstitucionalDAO();
						matInstDao.guardarNuevoMaterialInstitucional(matInst);
					} else {
						MaterialPorPropuesta matProp = new MaterialPorPropuesta(titulo, descripcion, fuente, enlace,
								categoria);
						MaterialPorPropuestaDAO matPropDao = new MaterialPorPropuestaDAO();
						matPropDao.guardarNuevoMaterialInstitucional(matProp);
					}

					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
					marco.setContentPane(new VerMaterialesPanel());
					marco.repaint();
				} else {

					String descripcion = text_Descripcion.getText();
					String fuente = text_Fuente.getText();
					String enlace = text_Enlace.getText();
					String procedencia = null;
					Boolean prioridad = false;
					try {
						procedencia = text_Procedencia.getText();
						prioridad = chckbx_Prioritario.isSelected();
					} catch (Exception e) {
						// TODO: handle exception
					}
					String categoria = comboBox_Categoria.getSelectedItem().toString();
					String titulo = text_Titulo.getText();
					if (chckbx_Institucional.isSelected()) {
						MaterialInstitucional matInst = new MaterialInstitucional(descripcion, fuente, enlace,
								procedencia, prioridad, categoria, titulo);
						MaterialInstitucionalDAO matInstDao = new MaterialInstitucionalDAO();
						matInstDao.guardarMaterialInstitucionalModificado(matInst, titulo);
					} else {
						MaterialPorPropuesta matProp = new MaterialPorPropuesta(titulo, descripcion, fuente, enlace,
								categoria);
						MaterialPorPropuestaDAO matPropDao = new MaterialPorPropuestaDAO();
						matPropDao.guardarMaterialPorPropuestModificado(matProp, mat.getTitulo());
						
						JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
						marco.setContentPane(new MostrarPropuestasAsociadasAUnMaterialPorPropuestasPanel(matProp));
						marco.validate();
					}

					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
					marco.setContentPane(new VerMaterialesPanel());
					marco.repaint();

				}
			}

		});

	}

	public CrearMaterialPanel(MaterialPorPropuesta mat) {
		this();
		this.mat = mat;
		text_Titulo.setText(mat.getTitulo());
		text_Descripcion.setText(mat.getDescripcion());
		text_Enlace.setText(mat.getEnlace());
		text_Fuente.setText(mat.getFuente());
		comboBox_Categoria.setSelectedItem(mat.getDescripcionCategoria());
		JButton btnVerSusPropuestas = new JButton("Ver Propuestas Asociadas");
		btnVerSusPropuestas.setBounds(150, 266, 89, 23);
		add(btnVerSusPropuestas);
		btnVerSusPropuestas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((JComponent) ae.getSource());
				marco.setContentPane(new MostrarPropuestasAsociadasAUnMaterialPorPropuestasPanel(mat));
				marco.validate();
			}
		});

	}
	
	public CrearMaterialPanel(MaterialInstitucional mat) {
		this();
		this.mat = mat;
		text_Titulo.setText(mat.getTitulo());
		text_Descripcion.setText(mat.getDescripcion());
		text_Enlace.setText(mat.getEnlace());
		text_Fuente.setText(mat.getFuente());
		comboBox_Categoria.setSelectedItem(mat.getDescripcionCategoria());
		try {
			chckbx_Institucional.setSelected(true);
			chckbx_Prioritario = new JCheckBox();
			chckbx_Prioritario.setBounds(277, 161, 40, 23);
			chckbx_Prioritario.setSelected(mat.esPrioritario());
			add(chckbx_Prioritario);
			JLabel lblNewLabel_9 = new JLabel("PRIORITARIO");
			lblNewLabel_9.setBounds(193, 161, 78, 14);
			add(lblNewLabel_9);
			text_Procedencia = new JTextField();
			text_Procedencia.setBounds(193, 208, 86, 67);
			add(text_Procedencia);
			text_Procedencia.setColumns(10);
			text_Procedencia.setText(mat.getProcedencia());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean esEdicion() {
		return this.mat != null;

	}
}