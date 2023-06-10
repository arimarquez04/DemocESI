package DemocESI.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import DemocESI.dao.CategoriaDAO;
import DemocESI.modelo.Categoria;
import DemocESI.vista.*;

public class App {

	public static void main(String[] args) {
		JFrame marco = new JFrame();
		marco.setBounds(0, 0, 800, 600);
		marco.setVisible(true);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar jMb = new JMenuBar();
		marco.setJMenuBar(jMb);

		JMenuItem menuPropuestas = new JMenuItem("Propuestas");
		menuPropuestas.setSelected(true);
		JMenuItem menuMateriales = new JMenuItem("Materiales");
		menuMateriales.setSelected(true);
		JMenuItem menuJornadas = new JMenuItem("Jornadas");
		menuJornadas.setSelected(true);
		jMb.add(menuJornadas);
		jMb.add(menuMateriales);
		jMb.add(menuPropuestas);
		marco.getContentPane().setLayout(null);
		marco.setVisible(true);

		menuPropuestas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new VerPropuestaPanel());
				marco.validate();

			}
		});
		
		menuMateriales.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				marco.setContentPane(new VerMaterialesPanel());
				marco.validate();
				
			}
		});
	}
}
