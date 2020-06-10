package ads.ejercicio6;

import java.util.*;

/**
 * la clase Venta donde se guardan las ventas que ha habido
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Venta {
	private Electrodomestico nuevo;
	private Electrodomestico viejo;
	private static ArrayList<Venta> ventas = new ArrayList<>();

	/**
	 * @param nuevo el nuevo
	 */
	public Venta(Electrodomestico nuevo) {
		this.nuevo = nuevo;
		ventas.add(this);
		Stock.remove(nuevo);
	}

	/**
	 * @param nuevo el nuevo
	 * @param viejo el viejo
	 */
	public Venta(Electrodomestico nuevo, Electrodomestico viejo) {
		this.nuevo = nuevo;
		this.viejo = viejo;
		ventas.add(this);
		Stock.remove(nuevo);
		Stock.add(viejo);
	}

	/**
	 * @return the nuevo
	 */
	public Electrodomestico getNuevo() {
		return nuevo;
	}

	/**
	 * @param nuevo the nuevo to set
	 */
	public void setNuevo(Electrodomestico nuevo) {
		this.nuevo = nuevo;
	}

	/**
	 * @return the viejo
	 */
	public Electrodomestico getViejo() {
		return viejo;
	}

	/**
	 * @param viejo the viejo to set
	 */
	public void setViejo(Electrodomestico viejo) {
		this.viejo = viejo;
	}

	/**
	 * @return the ventas
	 */
	public List<Venta> getVentas() {
		return ventas;
	}

	/**
	 * @param ventas the ventas to set
	 */
	public void setVentas(ArrayList<Venta> ventas) {
		Venta.ventas = ventas;
	}

	/**
	 * @return precio total a pagar
	 */
	public double precioTotal() {
		return nuevo.getPrecioBase() - nuevo.descuento(viejo);
	}

	/**
	 * @return el impreso del ticket
	 */
	public String getTicket() {
		String ans = "--------------------------------------------\n";
		ans += getProductoVendido();
		ans += getPrecioProducto();
		ans += getDescuentoEntrega();
		ans += getTotal();

		return ans;
	}

	/**
	 * @return el producto con todos sus atributos para el ticket
	 */
	public String getProductoVendido() {
		return "Producto vendido: " + nuevo.toString() + "\n--------------------------------------------\n";
	}

	/**
	 * @return el precio base del producto para el ticket
	 */
	public String getPrecioProducto() {
		String s = "" + nuevo.getPrecioBase();
		return "Precio producto:" + String.format("%22s", s) + " Euros\n";
	}

	/**
	 * @return el descuento aplicado al electrodomestico para el ticket
	 */
	public String getDescuentoEntrega() {
		String s = "" + nuevo.descuento(viejo);
		return "Descuento entrega:" + String.format("%20s", s) + " Euros\n";
	}

	/**
	 * @return el total del ticket
	 */
	public String getTotal() {
		String s = String.format("%.2f", precioTotal()) + " Euros\n";
		return "TOTAL:" + String.format("%39s", s);
	}

	/**
	 * @return string de resumen ventas
	 */
	public static String resumenVentas() {
		String total = "\nRESUMEN VENTAS: \n";

		for (Venta v : ventas) {
			total += v.getTotal();
		}
		return total;
	}

	/**
	 * @param cantidad minima que tiene que costar para ser escrito en la String
	 * @return string de resumen ventas
	 */
	public static String resumenVentas(int cantidad) {
		String total = "RESUMEN VENTAS: \n";

		for (Venta v : ventas) {
			if (v.precioTotal() > cantidad) {
				total += v.getTotal();
			}
		}
		return total;
	}

	/**
	 * @param marca marca de la que se va a aniadir a la String
	 * @return string resumen ventas de la marca marca
	 */
	public static String resumenVentas(String marca) {
		String total = "RESUMEN VENTAS: \n";

		for (Venta v : ventas) {
			if (v.nuevo.getMarca().contains(marca) == true) {
				total += v.getTotal();
			}
		}
		return total;
	}

	/**
	 * @return elimina la ultima venta que se ha hecho y devuelve el ArrayList
	 */
	public static Venta anular() {
		if (ventas.isEmpty() == true) {
			return null;
		}

		Venta venta = ultima();

		ventas.remove(ultima());
		Stock.add(venta.getNuevo());
		Stock.remove(venta.getViejo());

		return venta;
	}

	/**
	 * @return la ultima venta del ArrayList
	 */
	public static Venta ultima() {
		if (ventas.isEmpty() == true) {
			return null;
		}
		return ventas.get(ventas.size() - 1);
	}

}
