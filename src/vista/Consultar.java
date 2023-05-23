package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.BaseDeDatos;
import modelo.City;
import modelo.Fichero;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ItemEvent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Clase que contiene toda la ventana de consultar.
 */
public class Consultar extends JPanel {
	private JTable tablaCiudad;
	private JComboBox cmbPaises;
	private JButton btnBorrar;
	private JTextField txtCiudad;
	private JScrollPane scrollPane;
	DefaultTableModel modeloTabla = new DefaultTableModel();
	DefaultListModel modeloLista = new DefaultListModel<>();
	private JButton btnModificar;
	private JSlider sldPoblacion;
	private JTextField txtNombreCiu;
	private JTextField txtnumPoblacion;
	private JComboBox cmbNomPais;
	private JComboBox cmbNomDistrito;
	private ButtonGroup bg = new ButtonGroup();
	private JList lstDistrito;
	private JScrollPane scrollPane_1;
	private JMenuItem mntmXML;
	private JMenuItem MNTMsql;


	public Consultar() {
		setBackground(new Color(250, 231, 250));
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 121, 745, 403);
		add(scrollPane);
		
		tablaCiudad = new JTable();
		tablaCiudad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tablaCiudad.getSelectedRow() != -1) {
		            String city = (String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 0);
		        	txtNombreCiu.setText(city);
		        	String population = modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 5).toString();
		        	txtnumPoblacion.setText(population);
		        	String pais =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 1);
		        	cmbNomPais.setSelectedItem(pais);
		        	String distrito =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 2);
		        	cmbNomDistrito.setSelectedItem(distrito);

		        } else {
		        	JOptionPane.showMessageDialog(null, "Error");
		        }
			}
		});
		scrollPane.setViewportView(tablaCiudad);
		modeloTabla.setColumnIdentifiers(new Object[]{"Ciudad", "País", "Distrito", "Continente", "Idioma.Oficial", "Población"});
		tablaCiudad.setModel(modeloTabla);
		
		txtCiudad = new JTextField();
		txtCiudad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCiudad.setText("");
			}
		});
		txtCiudad.setText("Escribe nombre de ciudad");
		txtCiudad.setEnabled(false);
		txtCiudad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				BaseDeDatos bd = new BaseDeDatos();
				modeloTabla.setRowCount(0);
				String selected=txtCiudad.getText();
				for(City p : bd.consulCiudades(selected)) {
					modeloTabla.addRow(new Object[] {p.getCiudad(), p.getDistrito(), p.getPais(), p.getContinente(), p.getIdioma(), p.getPoblacion()});
				}
			}
		});
		
		txtCiudad.setBounds(41, 60, 166, 50);
		add(txtCiudad);
		txtCiudad.setColumns(10);
		
		cmbPaises = new JComboBox();
		cmbPaises.setEnabled(false);
		cmbPaises.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				BaseDeDatos bd = new BaseDeDatos();
				String selected = (String) cmbPaises.getSelectedItem();
				modeloTabla.setRowCount(0);
				for(City p : bd.consulPais(selected)) {
					modeloTabla.addRow(new Object[] {p.getCiudad(), p.getDistrito(), p.getPais(), p.getContinente(), p.getIdioma(), p.getPoblacion()});
				}
			}
		});
		cargaComboPais();
		
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(620, 60, 166, 50);
		btnBorrar.setBackground(Color.WHITE);
		btnBorrar.setForeground(Color.RED);
		btnBorrar.setFont(new Font("Tahoma", Font.PLAIN, 33));
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tablaCiudad.getSelectedRow();
			    if (selectedRow == -1) {
			        JOptionPane.showMessageDialog(null, "Por favor seleccione una ciudad para borrar.");
			        return;
			    }
				String ciudad = (String)modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 0);
				String pais =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 1);
				String distrito =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 2);
				String continente =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 3);
				String idioma =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 4);
				int poblacion = (int) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 5);
				City ciudadElegida = new City(ciudad, pais, distrito, continente, idioma, poblacion);
				BaseDeDatos bd = new BaseDeDatos();
				
				bd.borrar(ciudadElegida);
				JOptionPane.showMessageDialog(null, "Ciudad borrada correctamente!");
				modeloTabla.removeRow(selectedRow);
			}
		});
		add(btnBorrar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String ciudad = (String)modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 0);			
				String pais =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 1);
				String distrito =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 2);
				String continente =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 3);
				String idioma =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 4);
				int poblacion = (int) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 5);
				
				City ciudadElegida = new City(
						txtNombreCiu.getText(),  
						pais,
						cmbNomDistrito.getSelectedItem().toString(),
						continente,
						idioma,
						Integer.valueOf(txtnumPoblacion.getText())
				);
				City cityActual = new City(ciudad, pais, distrito,  continente, idioma, (int)modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 5));
				
				BaseDeDatos bd = new BaseDeDatos();
				
				bd.modificar(cityActual, ciudadElegida);
				modeloTabla.setValueAt(ciudadElegida.getCiudad(), tablaCiudad.getSelectedRow(), 0);
				modeloTabla.setValueAt(ciudadElegida.getPoblacion(), tablaCiudad.getSelectedRow(), 5);
				modeloTabla.setValueAt(ciudadElegida.getDistrito(), tablaCiudad.getSelectedRow(), 2);
			}
		});
		btnModificar.setForeground(Color.BLACK);
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 33));
		btnModificar.setBackground(Color.WHITE);
		btnModificar.setBounds(685, 573, 217, 49);
		add(btnModificar);
		
		sldPoblacion = new JSlider();
		sldPoblacion.setEnabled(false);
		sldPoblacion.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				BaseDeDatos bd = new BaseDeDatos();
				int selected = sldPoblacion.getValue();
				modeloTabla.setRowCount(0);
				for(City p : bd.consulPoblacion(selected)) {
					modeloTabla.addRow(new Object[] {p.getCiudad(), p.getDistrito(), p.getPais(), p.getContinente(), p.getIdioma(), p.getPoblacion()});
				}
				
			}
		});
		sldPoblacion.setOrientation(SwingConstants.VERTICAL);
		sldPoblacion.setBounds(796, 85, 143, 464);
		add(sldPoblacion);
		sldPoblacion.setMinimum(50000);
		sldPoblacion.setMaximum(2000000);
		sldPoblacion.setMajorTickSpacing(50000);
		sldPoblacion.setPaintTicks(true);
		sldPoblacion.setPaintLabels(true);
		
		txtNombreCiu = new JTextField();
		txtNombreCiu.setBounds(41, 572, 144, 50);
		add(txtNombreCiu);
		txtNombreCiu.setColumns(10);
		
		txtnumPoblacion = new JTextField();
		txtnumPoblacion.setColumns(10);
		txtnumPoblacion.setBounds(195, 572, 148, 50);
		add(txtnumPoblacion);
		
		cmbNomPais = new JComboBox();
		cargaCountries(cmbNomPais);
		cmbNomPais.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cmbNomDistrito.removeAllItems();
				BaseDeDatos bd = new BaseDeDatos();
				ArrayList<String> arrLDistritos = new ArrayList<>();
				
				String pais =(String) modeloTabla.getValueAt(tablaCiudad.getSelectedRow(), 1);
				arrLDistritos=bd.consultarDistrictIns(pais);
				
				for(int i = 0; i<arrLDistritos.size(); i++) {
					cmbNomDistrito.addItem(arrLDistritos.get(i));
				}
				
			}
		});
		cmbNomPais.setEnabled(false);
		cmbNomPais.setBounds(353, 572, 154, 50);
		add(cmbNomPais);
		
		cmbNomDistrito = new JComboBox();
		//cargaComboDistritoModificar();
		cmbNomDistrito.setBounds(518, 572, 157, 50);
		add(cmbNomDistrito);
		
		JLabel lblNewLabel = new JLabel("Filtro para modificar el país seleccionado de la tabla.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(41, 535, 555, 26);
		add(lblNewLabel);
		
		JLabel lblFiltroParaConsultar = new JLabel("Filtro para consultar ciudades.");
		lblFiltroParaConsultar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFiltroParaConsultar.setBounds(23, 22, 224, 26);
		add(lblFiltroParaConsultar);
		
		JLabel lblFiltroParaConsultar_2 = new JLabel("Filtro para consultar por poblacion.");
		lblFiltroParaConsultar_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltroParaConsultar_2.setBounds(801, 60, 189, 26);
		add(lblFiltroParaConsultar_2);
		
		JRadioButton rdbtnPais = new JRadioButton("New radio button");
		rdbtnPais.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cmbPaises.setEnabled(true);
				txtCiudad.setEnabled(false);
				lstDistrito.setEnabled(false);
				sldPoblacion.setEnabled(false);
			}
		});
		rdbtnPais.setBounds(213, 74, 21, 23);
		add(rdbtnPais);
		
		JRadioButton rdbtnDistrito = new JRadioButton("New radio button");
		rdbtnDistrito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lstDistrito.setEnabled(true);
				cmbPaises.setEnabled(false);
				txtCiudad.setEnabled(false);
				sldPoblacion.setEnabled(false);
			}
		});
		rdbtnDistrito.setBounds(406, 74, 21, 23);
		add(rdbtnDistrito);
		
		JRadioButton rdbtnCiudad = new JRadioButton("New radio button");
		rdbtnCiudad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cmbPaises.setEnabled(false);
				txtCiudad.setEnabled(true);
				lstDistrito.setEnabled(false);
				sldPoblacion.setEnabled(false);
			}
		});
		rdbtnCiudad.setBounds(14, 74, 21, 23);
		add(rdbtnCiudad);
		
		JRadioButton rdbtnPoblacion = new JRadioButton("New radio button");
		rdbtnPoblacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cmbPaises.setEnabled(false);
				txtCiudad.setEnabled(false);
				lstDistrito.setEnabled(false);
				sldPoblacion.setEnabled(true);
			}
		});
		rdbtnPoblacion.setToolTipText("");
		rdbtnPoblacion.setBounds(858, 543, 21, 23);
		add(rdbtnPoblacion);
		
		bg.add(rdbtnPais);
		bg.add(rdbtnCiudad);
		bg.add(rdbtnPoblacion);
		bg.add(rdbtnDistrito);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(433, 11, 176, 99);
		add(scrollPane_1);
		
		lstDistrito = new JList();
		lstDistrito = cargaList();
		lstDistrito.setEnabled(false);
		lstDistrito.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				BaseDeDatos bd = new BaseDeDatos();
				String selected = (String) lstDistrito.getSelectedValue();
				modeloTabla.setRowCount(0);
				for(City p : bd.consulDistritos(selected)) {
					modeloTabla.addRow(new Object[] {p.getCiudad(), p.getDistrito(), p.getPais(), p.getContinente(), p.getIdioma(), p.getPoblacion()});
				}
			}
		});
		scrollPane_1.setViewportView(lstDistrito);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(630, 11, 360, 38);
		add(toolBar);
		
		mntmXML = new JMenuItem("XML");
		mntmXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fichero f = new Fichero();
				f.CrearXml(tablaCiudad, "Mixml");
				JOptionPane.showMessageDialog(null, "XML creado correctamente!");
			}
		});
		mntmXML.setHorizontalAlignment(SwingConstants.CENTER);
		toolBar.add(mntmXML);
		
		JMenuItem mntmXLS = new JMenuItem("XLS\r\n");
		mntmXLS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fichero ex = new Fichero();
				try {
					ex.exportarExcel(tablaCiudad);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "XLS creado correctamente!");
			}
		});
		mntmXLS.setHorizontalAlignment(SwingConstants.CENTER);
		toolBar.add(mntmXLS);
		
		MNTMsql = new JMenuItem("SQL");
		MNTMsql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				Fichero f = new Fichero();
				f.CrearSQL(meterJTableEnArrayList());
				JOptionPane.showMessageDialog(null, "SQL creado correctamente!");
			}
		});
		MNTMsql.setHorizontalAlignment(SwingConstants.CENTER);
		toolBar.add(MNTMsql);
	}
	/**
	 * Metodo que sirve para cargar el comboBox de los paises.
	 */
	public void cargaComboPais() {
		BaseDeDatos bd = new BaseDeDatos();
		ResultSet rs;
		rs  = bd.consultarPaises();
		cmbPaises.addItem("Paises");
			try {
				while(rs.next()) {
					cmbPaises.addItem(rs.getString("name"));
				}
				bd.desconectar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		cmbPaises.setBounds(234, 60, 166, 50);
		add(cmbPaises);
	}
	/**
	 * Metodo que sirve para cargar el JList de los distritos.
	 * @return
	 */
	public JList cargaList(){
		BaseDeDatos bd = new BaseDeDatos();
		ResultSet rs = bd.consultarDistritos();
		modeloLista.addElement("Distritos");
		try {
			while(rs.next()) {
				modeloLista.addElement(rs.getString("district"));
			}
			bd.desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JList lstDistrito = new JList(modeloLista);
		return lstDistrito;
	}
	/**
	 * Metodo que sirve para cargar el comboBox de los paises de modificar.
	 */
	public void cargaComboPaisModificar() {
		BaseDeDatos bd = new BaseDeDatos();
		ResultSet rs;
		rs  = bd.consultarPaises();
		cmbNomPais.addItem("Paises");
			try {
				while(rs.next()) {
					cmbNomPais.addItem(rs.getString("name"));
				}
				bd.desconectar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		cmbNomPais.setBounds(234, 60, 166, 50);
		add(cmbNomPais);
	}
	/**
	 * Metodo que sirve para cargar el comboBox de los distritos de modificar.
	 */
	public void cargaComboDistritoModificar() {
		BaseDeDatos bd = new BaseDeDatos();
		ResultSet rs;
		rs  = bd.consultarDistritos();
		cmbNomDistrito.addItem("Distritos");
			try {
				while(rs.next()) {
					cmbNomDistrito.addItem(rs.getString("district"));
				}
				bd.desconectar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		cmbNomDistrito.setBounds(427, 60, 183, 50);
		add(cmbNomDistrito);
	}
	/**
	 * Metodo que sirve para cargar los paises en el comboBox.
	 * @param c
	 */
	public void cargaCountries(JComboBox c){
		BaseDeDatos bd = new BaseDeDatos();
		ResultSet rs;
		rs = bd.consultarPaises();
		c.addItem("Países");
		c.addItem("Distritos");
		try {
			while(rs.next()) {
				c.addItem(rs.getString("name"));
			}
			bd.desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metodo que sirve para meter la JTable en un arrayList para luego exportarla.
	 * @return
	 */
	public ArrayList<City> meterJTableEnArrayList() {
		ArrayList<City> arrJTable = new ArrayList<>();
		for(int i = 0 ; i < tablaCiudad.getRowCount(); i++) {
			City miTabla = new City(
				(String) modeloTabla.getValueAt(i, 0),
				(String) modeloTabla.getValueAt(i, 1),
				(String) modeloTabla.getValueAt(i, 2),
				(String) modeloTabla.getValueAt(i, 3),
				(String) modeloTabla.getValueAt(i, 4),
				(int) modeloTabla.getValueAt(i, 5)
			);
			arrJTable.add(miTabla);
		}
		return arrJTable;
	}
}
