package ads.practica3;

/**
 * Es el tester para el ejercicio5, donde se crean electrodomesticos y se pureba
 * la VentaCanarias y la TelevisionCurva
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class TesterTienda5 {
	public static void main(String[] args) {
		Electrodomestico tele1 = new Television("Loewe", "Art 48 4K Plata", 1699.00, ClaseEnergetica.A, 48);
		Electrodomestico tele2 = new Television("Loewe", "Art 48 4K Plata", 1699.00, ClaseEnergetica.A, 48);
		Electrodomestico tele3 = new TelevisionCurva("LG", "32LF592U", 271.92, ClaseEnergetica.C, 32, 6.0,
				new Dimension(30, 50, 5));
		Electrodomestico lava1 = new Lavadora("Indesit", "XWE 91483 X", 329.0, ClaseEnergetica.A,
				new Dimension(59.5, 60.5, 85.0), 72, 9.5, 1500);
		Electrodomestico frigo1 = new Frigorifico("Bosch", "KGN39VW21", 599.0, ClaseEnergetica.A,
				new Dimension(60, 65, 201), 83.0, true);

		Venta[] ventas = new Venta[8];
		ventas[0] = new Venta(tele1);
		ventas[1] = new VentaCanarias(tele1);
		ventas[2] = new Venta(tele1,
				new Lavadora("Superkin", "", 0.0, ClaseEnergetica.Desconocida, new Dimension(59.5, 60.5, 85.0), 100)); // sin
																														// carga
																														// ni
																														// rpm
		ventas[3] = new VentaDomicilio(lava1, frigo1);
		ventas[4] = new Venta(tele2, new Television("Telefunken", "", 0.0, ClaseEnergetica.D, 32));
		ventas[5] = new VentaDomicilio(lava1, tele2);
		ventas[6] = new VentaCanarias(tele3);
		ventas[7] = new VentaCanarias(tele1);

		for (Venta v : ventas)
			System.out.print(v.getTicket());

		System.out.println(Venta.resumenVentas());
		System.out.println(Venta.ultima().getTicket());

		Venta anulada = Venta.anular(); // anulamos la última venta
		System.out.println("Venta anulada:\n" + anulada.getTicket());

		Venta.anular(); // anulamos otra
		// eliminado por redundante Venta.resumenVentas(); // sin las dos ultimas ventas
		System.out.println(Venta.resumenVentas(500)); // sin las dos ultimas ventas, ni la de importe < 500
		System.out.println(Venta.resumenVentas("Indes")); // solo ventas (no anuladas) de marca Indesit

	}
}
