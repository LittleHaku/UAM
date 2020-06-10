package ads.ejercicio6;

import java.util.ArrayList;

/**
 * es la clase Stock para el ejercicio extra 6, se puede aniadir, eliminar
 * elementos del catalogo e imprimir todos los electrodomesticos a la vez o por
 * tipo de Electrodomestico
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Stock {
	private static ArrayList<Electrodomestico> catalogo = new ArrayList<>();

	/**
	 * @param e es el electrodomestico a aniadirse
	 */
	public static void add(Electrodomestico e) {
		if (e == null) {
			return;
		}
		catalogo.add(e);
	}

	/**
	 * @param e es el electrodomestico a eliminarse, descatalogarse
	 */
	public static void remove(Electrodomestico e) {
		if (e == null) {
			return;
		}
		catalogo.remove(e);
	}

	/**
	 * imprime el catalogo completo Electrodomestico por Electrodomestico
	 */
	public static void mostrarCatalogoCompleto() {
		System.out.println("Catalogo completo:");
		for (Electrodomestico e : catalogo) {
			System.out.println(e);
		}
		System.out.println("");
	}

	/**
	 * imprime el catalogo por tipo de Electrodomestico
	 */
	public static void mostrarCatalogoTipo() {
		// Guarda el tipo de electrodomestico que ya ha impreso
		ArrayList<String> mostrados = new ArrayList<>();
		String name;

		// Para cada tipo de Electrodomestico que hay en catalogo
		for (Electrodomestico e1 : catalogo) {
			name = e1.getClass().getName();
			if (mostrados.contains(name) == true) {
				continue;
			}
			mostrados.add(name);
			System.out.println("Tipo: " + name);

			// Coge todos los que son de igual tipo que el y los imprime
			for (Electrodomestico e2 : catalogo) {
				if (e2.getClass().getName() == name) {
					System.out.println(e2);
				}
			}
			System.out.println("");
		}

	}
}
