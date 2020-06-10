package ads.practica3;

import java.io.IOException;
import java.util.List;

/**
 * Segundo tester el tercer apartado de la P2 DEBE EJECUTARSE CON UN ARGUMENTO
 * EN LA LINEA DE COMANDOS
 *
 * @author Profesores ADS
 * 
 */

public class TesterTienda2 {
	/**
	 * @param args El primer y único argumento será el nombre del archivo de texto a
	 *             leer
	 * @throws IOException si el documento no existe
	 */
	public static void main(String[] args) throws IOException {
		List<Electrodomestico> productos = LecturaElectrodomesticos.leer(args[0]);

		for (Electrodomestico e : productos) {
			System.out.println(">> " + e);
		}
	}
}
