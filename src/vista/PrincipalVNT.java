package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Clase que contiene la ventana principal.
 * @author francisco.puerta
 *
 */
public class PrincipalVNT extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/**
	 * Metodo que sirve para cargar la ventana y que sea visible.
	 * @param frame
	 */
	public static void cargaVentana(PrincipalVNT frame) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Clase que contiene la primera ventana que sale al ejecutar el programa y la llamada a las demas ventanas.
	 */
	public PrincipalVNT() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1036, 717);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		JMenuItem mntmEntrar = new JMenuItem("Consultar");
		mntmEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Consultar p = new Consultar();
				nuevoPanel(p);
			}
		});
		mnOpciones.add(mntmEntrar);
		
		JMenuItem mntmSalir = new JMenuItem("Salir de la aplicacion.");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmInsertar = new JMenuItem("Insertar\r\n");
		mntmInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Insertar c = new Insertar();
				nuevoPanel(c);
			}
		});
		mnOpciones.add(mntmInsertar);
		
		mntmSalir.setForeground(Color.RED);
		mnOpciones.add(mntmSalir);
		
		JMenu mnNewMenu = new JMenu("|");
		menuBar.add(mnNewMenu);
		
		JMenu mnAcercaDe = new JMenu("Acerca de");
		mnAcercaDe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Francisco Puerta Uguina, 19 años "
						+ "desarrollador de aplicaciones web en el Centro de Formacion"
						+ " Profesional del JuanXXIII en Alcorcón.");
			}
		});
		
		menuBar.add(mnAcercaDe);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(226, 248, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JLabel lblTexto = new JLabel("SISTEMA DE GESTIÓN DE BASE DE DATOS");
		lblTexto.setFont(new Font("Myanmar Text", Font.BOLD, 35));
		contentPane.add(lblTexto, "name_1028552348274400");
	}
	/**
	 * Metodo que sirve para crear el nuevo panel a la hora de pulsar un boton.
	 * @param panelActual
	 */
	public void nuevoPanel(JPanel panelActual) {
		contentPane.removeAll();
		contentPane.add(panelActual);
		contentPane.repaint();
		contentPane.revalidate();
	}

}
