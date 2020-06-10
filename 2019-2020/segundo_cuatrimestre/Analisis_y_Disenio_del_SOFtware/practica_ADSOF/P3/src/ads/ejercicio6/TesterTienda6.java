package ads.ejercicio6;

/**
 * Es el tester para el ejercicio6, donde se crean 5 electrodomesticos y se
 * meten a Stock, se imprimen, se vende 1 y luego se imprimen por tipos, para
 * probar todas las funciones de Stock
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class TesterTienda6 {
	public static void main(String[] args) {
		Electrodomestico tele1 = new Television("Loewe", "Art 48 4K Plata", 1699.00, ClaseEnergetica.A, 48);
		Electrodomestico tele2 = new Television("Loewe", "Art 48 4K Plata", 1699.00, ClaseEnergetica.A, 48);
		Electrodomestico lava1 = new Lavadora("Indesit", "XWE 91483 X", 329.0, ClaseEnergetica.A,
				new Dimension(59.5, 60.5, 85.0), 72, 9.5, 1500);
		Electrodomestico tele3 = new TelevisionCurva("LG", "32LF592U", 271.92, ClaseEnergetica.C, 32, 6.0,
				new Dimension(30, 50, 5));
		Electrodomestico frigo1 = new Frigorifico("Bosch", "KGN39VW21", 599.0, ClaseEnergetica.A,
				new Dimension(60, 65, 201), 83.0, true);

		//Se aniaden al Stock cuando se crean, lease el constructor de Electrodomestico

		// muestra 5 resultados
		Stock.mostrarCatalogoCompleto();
		
		// vende tele1
		new Venta(tele1);

		// muestra 4 resultados
		Stock.mostrarCatalogoTipo();

	}
}
