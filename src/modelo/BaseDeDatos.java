package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

/**
 * Clase que contiene cualquier tipo de interaccion con la base de datos
 * @author francisco.puerta
 *
 */
public class BaseDeDatos {
	
	Connection conexion;
	Statement consulta = null;
	
	/**
	 * Metodo que conecta con la base de datos.
	 * @return
	 */
	public Statement conectar() {
		try {
			//conexion = DriverManager.getConnection("jdbc:mysql://localhost/world", "root", "");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/world", "paises2023", "paises2023");
			consulta = conexion.createStatement();
		} catch (CommunicationsException err) {
			System.err.println("\nError: BASE DE DATOS NO INICIADA");
			JOptionPane.showMessageDialog(null, "Base de datos no iniciada.");
			 System. exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return consulta;
	}
	
	/**
	 * Metodo que desconecta con la base de datos.
	 */
	public void desconectar() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que recoge la consulta de todos los paises desde la base de datos.
	 * @return
	 */
	public ResultSet consultarPaises() {
		Statement consultaActual = conectar();
		ResultSet rs = null;
		try {
			rs = consultaActual.executeQuery("select name from country order by name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Metodo que recoge la consulta de todos los distritos desde la base de datos.
	 * @return
	 */
	public ResultSet consultarDistritos() {
		Statement consultaActual = conectar();
		ResultSet rs = null;
		try {
			rs = consultaActual.executeQuery("select DISTINCT district from city WHERE district != '' order by district");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Metodo que recoge la consulta de todas las ciudades desde la base de datos.
	 * @return
	 */
	public ResultSet consultarCiudades() {
		Statement consultaActual = conectar();
		ResultSet rs = null;
		try {
			rs = consultaActual.executeQuery("select name from city order by name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Metodo que filtra los paises a la hora de consultarlos.
	 * @param selected
	 * @return
	 */
	public ArrayList<City> consulPais(String selected) {
		Statement consultaActual = conectar();
		ArrayList<City> arrLCities = new ArrayList<>();
		try {
			ResultSet rs = consultaActual.executeQuery("select ci.name, district, co.name, continent, group_concat(distinct language), ci.population "
														+ "from country co, city ci, countrylanguage cl "
														+ "WHERE ci.countrycode = code AND code = cl.countrycode "
														+ "AND IsOfficial = 'T'"
														+ "AND co.name = '" + selected + "' GROUP BY 1 order by ci.name"
														);
														
			
			while(rs.next()) {
				City ciudadActual = new City(
						rs.getString("ci.name"), 
						rs.getString("district"), 
						selected,
						rs.getString("continent"),
						rs.getString("group_concat(distinct language)"),  
						rs.getInt("population")  
				);
				arrLCities.add(ciudadActual);
			}
			desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrLCities;
	}

	/**
	 * Metodo que filtra los distritos a la hora de consultarlos.
	 * @param selected
	 * @return
	 */
	public ArrayList<City> consulDistritos(String selected) {
		Statement consultaActual = conectar();
		ArrayList<City> arrLCities = new ArrayList<>();
		try {
			ResultSet rs = consultaActual.executeQuery("select ci.name, district, co.name, continent, group_concat(distinct language), ci.population "
														+ "from country co, city ci, countrylanguage cl "
														+ "WHERE ci.countrycode = code AND code = cl.countrycode "
														+ "AND IsOfficial = 'T'"
														+ "AND district = '" + selected + "' GROUP BY 1 order by ci.name");
														
			while(rs.next()) {
				City ciudadActual = new City(
						rs.getString("ci.name"),
						selected,   
						rs.getString("co.name"),
						rs.getString("continent"),
						rs.getString("group_concat(distinct language)"), 
						rs.getInt("population")  
				);
				arrLCities.add(ciudadActual);
			}
			desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrLCities;
	}
	
	/**
	 * Metodo que filtra los distritos para la funcion insertar a la hora de consultarlos.
	 * @param selected
	 * @return
	 */
	public ArrayList<String> consultarDistrictIns(String selected) {
		Statement consultaActual = conectar();
		ArrayList<String> arrLDistrito = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = consultaActual.executeQuery("select distinct city.district"
					+ " from city, country "
					+ "where country.name = '"+selected+"' "
					+ "and country.code = city.countryCode "
					+ "order by city.district");
			if (rs==null) {
				System.out.println("La consulta ha fallado.");
			}
			while (rs.next()) {
				String aux;
				aux=rs.getString("district");
				arrLDistrito.add(aux);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return arrLDistrito;
	}
	/**
	 * Metodo que filtra las ciudades a la hora de consultarlos.
	 * @param selected
	 * @return
	 */
	public ArrayList<City> consulCiudades(String selected) {
		Statement consultaActual = conectar();
		ArrayList<City> arrLCities = new ArrayList<>();
		try {
			ResultSet rs = consultaActual.executeQuery("select ci.name, district, co.name, continent, group_concat(distinct language), ci.population "
														+ "from country co, city ci, countrylanguage cl "
														+ "WHERE ci.countrycode = code AND code = cl.countrycode "
														+ "AND IsOfficial = 'T'"
														+ "AND ci.name like '%" + selected + "%' group by 1");
			while(rs.next()) {
				City ciudadActual = new City(
						rs.getString("ci.name"),
						rs.getString("district"),
						rs.getString("co.name"),
						rs.getString("continent"),
						rs.getString("group_concat(distinct language)"),  
						rs.getInt("population")  
				);
				arrLCities.add(ciudadActual);
			}
			desconectar();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrLCities;
	}
	
	/**
	 * Metodo que filtra la poblacion a la hora de consultarla.
	 * @param selected
	 * @return
	 */
	public ArrayList<City> consulPoblacion(int selected) {
		Statement consultaActual = conectar();
		ArrayList<City> arrLCities = new ArrayList<>();
		try {
			ResultSet rs = consultaActual.executeQuery("select ci.name, district, co.name, continent, group_concat(distinct language), ci.population "
														+ "from country co, city ci, countrylanguage cl "
														+ "WHERE ci.countrycode = code AND code = cl.countrycode "
														+ "AND IsOfficial = 'T'"
														+ "AND ci.population >= '" + selected + "' GROUP BY 1 order by ci.name");
			while(rs.next()) {
				City cityActual = new City(
						rs.getString("ci.name"),
						rs.getString("district"), 
						rs.getString("co.name"),
						rs.getString("continent"),
						rs.getString("group_concat(distinct language)"), 
						rs.getInt("population")  
				);
				arrLCities.add(cityActual);
			}
			desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrLCities;
	}
	
	/**
	 * Metodo que sirve para modificar el elemento seleccionado desde la JTable en la base de datos.
	 * @param ciudadActual
	 * @param ciudadNueva
	 */
	public void modificar(City ciudadActual, City ciudadNueva) {
		Statement consultaActual = conectar();
		try {
			int valor = consultaActual.executeUpdate("update city "
					+ "set name ='" + ciudadNueva.getCiudad() + "', "
					+ "population = " + ciudadNueva.getPoblacion() + ", "
					+ "district = '" + ciudadNueva.getDistrito() + "'"
					+ " where name = '" + ciudadActual.getCiudad() + "'"
					+ " AND population = " + ciudadActual.getPoblacion());
			if(valor==1) {
				System.out.println("\nArticulo modificado correctamente\n");
			} else {
				System.err.println("No hay articulo con ese id\n");
			}
			desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metodo que sirve para borrar de la base de datos el elemento seleccionado de la JTable.
	 * @param miCiudad
	 */
	public void borrar(City miCiudad) {
		Statement consultaActual = conectar();
		try {
			consultaActual.executeUpdate("DELETE FROM city where name='"+miCiudad.getCiudad()+"' "
					+"AND district='"+miCiudad.getDistrito()+"' "
					+"AND population="+miCiudad.getPoblacion());
			desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metodo que sirve para modificar de la base de datos el elemento seleccionado de la JTable.
	 * @param cityEscrita
	 * @param paisElegido
	 * @param distritoElegido
	 * @param poblacionElegida
	 */
	public void insertar(String cityEscrita, String paisElegido, String distritoElegido, int poblacionElegida) {
		Statement consultaActual = conectar();
		ResultSet rs;
		try {
			rs=consultaActual.executeQuery("select code from country where name = '"+paisElegido+"' ");
			if(rs.next()) {
				String codeCountry = rs.getString("code");
				consultaActual.executeUpdate("insert into city (name, countrycode, district, population) values ('"
						+cityEscrita+"', '"
						+codeCountry+"', '"
						+distritoElegido+"', "
						+poblacionElegida+")"
					);
			}
			desconectar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}