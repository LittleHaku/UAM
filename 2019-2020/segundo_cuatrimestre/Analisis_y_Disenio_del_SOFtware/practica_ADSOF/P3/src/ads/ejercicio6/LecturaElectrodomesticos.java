package ads.ejercicio6;

import java.util.*;
import java.io.*;

/**
 * la clase LecturaElectrodomesticos, que lee los electrodomesticos del
 * documento que le pasan como argumento
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class LecturaElectrodomesticos {

	/**
	 * @param documento es el nombre del fichero a leer
	 * @return una lista de los Electrodomesticos creados de los datos del documento
	 * @throws IOException si el documento no existe
	 */
	public static List<Electrodomestico> leer(String documento) throws IOException {
		List<Electrodomestico> electrodomesticos = new ArrayList<>();
		BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(documento)));
		Boolean isFrost;

		String linea;
		Electrodomestico e;
		while ((linea = buffer.readLine()) != null) {
			// divide los tokens por el =
			String[] datos = linea.split("=");
			int num = datos.length;
			if (num == 5) {
				// e es una Television
				e = new Television(datos[0], datos[1], Double.valueOf(datos[2]), ClaseEnergetica.valueOf(datos[3]),
						Double.valueOf(datos[4]));
			} else if (num == 10) {
				// e es una Lavadora
				e = new Lavadora(datos[0], datos[1], Double.valueOf(datos[2]), ClaseEnergetica.valueOf(datos[3]),
						new Dimension(Double.valueOf(datos[4]), Double.valueOf(datos[5]), Double.valueOf(datos[6])),
						Double.valueOf(datos[7]), Double.valueOf(datos[8]), Double.valueOf(datos[9]));
			} else {
				// e es un Frigorifico
				if (datos[8].equals("Frost")) {
					isFrost = false;
				} else {
					isFrost = false;
				}
				e = new Frigorifico(datos[0], datos[1], Double.valueOf(datos[2]), ClaseEnergetica.valueOf(datos[3]),
						new Dimension(Double.valueOf(datos[4]), Double.valueOf(datos[5]), Double.valueOf(datos[6])),
						Double.valueOf(datos[7]), isFrost);
			}

			if (electrodomesticos.contains(e) == false) {
				electrodomesticos.add(e);
			} else {
				System.out.println("Duplicado no añadido:\n" + e);
			}

		}

		buffer.close();

		return electrodomesticos;
	}

}