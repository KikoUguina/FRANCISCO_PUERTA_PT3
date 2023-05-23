package controlador;

import vista.PrincipalVNT;

/**
 * Clase principal que contiene la ejecucion del programa
 * @author francisco.puerta
 *
 */
public class Principal {
	/**
	 * Metodo principal que ejecuta toda la aplicacion.
	 * @param args
	 */
	public static void main(String[] args) {
		PrincipalVNT miVentana = new PrincipalVNT();
		miVentana.cargaVentana(miVentana);
	}
	
}
