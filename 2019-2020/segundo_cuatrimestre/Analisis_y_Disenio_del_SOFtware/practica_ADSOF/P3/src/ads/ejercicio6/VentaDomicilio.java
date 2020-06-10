package ads.ejercicio6;

/**
 * 
 * la clase VentaDomicilio, un tipo de Venta
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class VentaDomicilio extends Venta {

	/**
	 * @param nuevo el nuevo
	 * @param viejo el viejo
	 */
	public VentaDomicilio(Electrodomestico nuevo, Electrodomestico viejo) {
		super(nuevo, viejo);
	}

	/**
	 * @param nuevo el nuevo
	 */
	public VentaDomicilio(Electrodomestico nuevo) {
		super(nuevo);
	}

	/**
	 * @return precio total a pagar
	 */
	public double precioTotal() {
		return getNuevo().getPrecioBase() + getNuevo().costePorte() - getNuevo().descuento(getViejo());
	}

	/**
	 * @return el impreso del ticket
	 */
	public String getTicket() {
		String ans = "--------------------------------------------\n";
		ans += getProductoVendido();
		ans += getPrecioProducto();
		ans += getDescuentoEntrega();
		ans += getCoste();
		ans += this.getTotal();
		return ans;
	}

	/**
	 * @return el total del ticket
	 */
	public String getTotal() {
		String s = String.format("%.2f", precioTotal());
		return "TOTAL: " + String.format("%31s", s) + " Euros\n";
	}

	/**
	 * @return el coste de la entrega para el ticket
	 */
	private String getCoste() {
		String s = "" + getNuevo().costePorte();
		return "Coste Porte:" + String.format("%26s", s) + " Euros\n";
	}

}
