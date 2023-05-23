package modelo;
/**
 * Clase principal de la aplicacion que contiene los constructores, getters y setters.
 * @author francisco.puerta
 *
 */
public class City {
	private String ciudad;
	private String pais;
	private String distrito;
	private String continente;
	private String idioma;
	private int poblacion;
	
	public City(String ciudad, String pais, String distrito, String continente, String idioma, int poblacion) {
		this.ciudad = ciudad;
		this.pais = pais;
		this.distrito = distrito;
		this.continente = continente;
		this.idioma = idioma;
		this.poblacion = poblacion;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public String getPais() {
		return pais;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public String getDistrito() {
		return distrito;
	}
	
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	
	public String getContinente() {
		return continente;
	}
	
	public void setContinente(String continente) {
		this.continente = continente;
	}
	
	public String getIdioma() {
		return idioma;
	}
	
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public int getPoblacion() {
		return poblacion;
	}
	
	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}
	
	
	
}
