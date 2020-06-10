package producto;

import java.util.Date;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Esta clase representa un producto Observable. Se nos da implementada, pero se
 * aniaden en el set precio 2 llamadas a funciones para que se ejecute la
 * funcion de los Observadores
 * 
 * @author Junco de las Heras y Marta Vaquerizo y Profesores ADSOF
 *
 */
public class Producto extends Observable { // Una clase para probar las reglas
	private double precio;
	private Date caducidad; // Otra opcion es usar Calendar

	/**
	 * Constructor de Producto
	 * 
	 * @param p precio del producto a crear
	 * @param c fecha de caducidad
	 */
	public Producto(double p, Date c) {
		this.precio = p;
		this.caducidad = c;
	}

	/**
	 * Getter del precio del Producto
	 * 
	 * @return precio del Producto
	 */
	public double getPrecio() {
		return this.precio;
	}

	/**
	 * Setter del precio del Producto
	 * 
	 * @param p precio a settear
	 */
	public void setPrecio(double p) {
		this.setChanged();
		this.notifyObservers();
		this.precio = p;
	}

	/**
	 * Getter de la fecha de caducidad del Producto.
	 * 
	 * @return fecha de caducidad.
	 */
	public Date getCaducidad() {
		return this.caducidad;
	}

	/**
	 * Calcula la diferencia entre dos fechas dadas en una unidad de tiempo dada.
	 * 
	 * @param date1    primera fecha
	 * @param date2    segunda fecha
	 * @param timeUnit unidad en la que se quiere la diferencia de tiempo entre las
	 *                 dos fechas.
	 * @return la diferencia entre las fechas en la unidad de tiempo indicada.
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	/**
	 * {@inheritDoc} Devuelve un String con la información de Producto.
	 */
	@Override
	public String toString() {
		return this.precio + ", caducidad: " + this.caducidad;
	}
}
