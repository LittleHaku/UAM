package ads.practica3;

/**
 * la clase VentaCanarias, un tipo de Venta
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class VentaCanarias extends VentaDomicilio {

	/**
	 * @param nuevo el nuevo
	 */
	public VentaCanarias(Electrodomestico nuevo) {
		super(nuevo);
	}

	/**
	 * @return los costes de este tipo de venta
	 */
	public double costes() {
		return 0.07 * getNuevo().getPrecioBase();
	}

	/**
	 * @return el precio total, double
	 */
	public double precioTotal() {
		return getNuevo().getPrecioBase() + costes();
	}

	/**
	 * @return el impreso del ticket
	 */
	public String getTicket() {
		String ans = "--------------------------------------------\n";
		ans += getProductoVendido();
		ans += getPrecioProducto();
		ans += getDescuentoEntrega();
		ans += getCosteVenta();
		ans += this.getTotal();
		return ans;
	}

	/**
	 * @return el total del ticket
	 */
	public String getTotal() {
		String s = String.format("%.2f", precioTotal());
		return "TOTAL:" + String.format("%32s", s) + " Euros\n";
	}

	/**
	 * @return el coste de la entrega para el ticket
	 */
	private String getCosteVenta() {
		String s = "" + costes();
		return "Coste por venta a Canarias:" + String.format("%11s", s) + " Euros\n";
	}

}
