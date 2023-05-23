package vista;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import modelo.BaseDeDatos;
import modelo.City;

import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;
import java.awt.Color;

/**
 * Clase que contiene todo lo que esta relacionado con insertar una ciudad.
 */
public class Insertar extends JPanel {
	private JLabel lblNombrePas;
	private JLabel lblNewLabel_2;
	private JButton btnInsertar;
	private JComboBox cmbPaisesIns;
	private JComboBox cmbDistritosIns;


	public Insertar() {
		setBackground(new Color(252, 205, 205));
		setLayout(null);
		
		JTextField txtCiudadEscrita = new JTextField();
		txtCiudadEscrita.setBounds(36, 67, 115, 37);
		add(txtCiudadEscrita);
		txtCiudadEscrita.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre Ciudad\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(36, 35, 108, 14);
		add(lblNewLabel);
		
		
		
		lblNombrePas = new JLabel("Nombre País\r\n");
		lblNombrePas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombrePas.setBounds(173, 35, 108, 14);
		add(lblNombrePas);
		
		lblNewLabel_2 = new JLabel("Nombre Distrito\r\n");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(322, 35, 108, 14);
		add(lblNewLabel_2);
		
		JSlider sldNumPoblacion = new JSlider();
		sldNumPoblacion.setOrientation(SwingConstants.VERTICAL);
		sldNumPoblacion.setBounds(495, 11, 108, 435);
		sldNumPoblacion.setMinimum(50000);
		sldNumPoblacion.setMaximum(2000000);
		sldNumPoblacion.setMajorTickSpacing(50000);
		sldNumPoblacion.setPaintTicks(true);
		sldNumPoblacion.setPaintLabels(true);
		add(sldNumPoblacion);
		
		btnInsertar = new JButton("INSERTAR");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BaseDeDatos bd = new BaseDeDatos();
				String ciudadEscrita = txtCiudadEscrita.getText();
				String pais = cmbPaisesIns.getSelectedItem().toString();
				String distrito = cmbDistritosIns.getSelectedItem().toString();
				int poblacion = sldNumPoblacion.getValue();
				
				bd.insertar(ciudadEscrita, pais, distrito, poblacion);
				JOptionPane.showMessageDialog(null, "Ciudad añadida correctamente!");
			}
		});
		btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 45));
		btnInsertar.setBounds(51, 139, 413, 287);
		add(btnInsertar);
		
		cmbPaisesIns = new JComboBox();
		cargaCountries(cmbPaisesIns);
		cmbPaisesIns.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cmbDistritosIns.removeAllItems();
				BaseDeDatos bd = new BaseDeDatos();
				ArrayList<String> arrLDistritos = new ArrayList<>();
				
				String pais;
				pais=(String) cmbPaisesIns.getSelectedItem();
				arrLDistritos=bd.consultarDistrictIns(pais);
				
				for(int i = 0; i<arrLDistritos.size(); i++) {
					cmbDistritosIns.addItem(arrLDistritos.get(i));
				}
			}
		});
		cmbPaisesIns.setBounds(161, 67, 151, 37);
		add(cmbPaisesIns);
		
		
		cmbDistritosIns = new JComboBox();
		cmbDistritosIns.setBounds(322, 67, 142, 37);
		add(cmbDistritosIns);
		
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
}
