/**
 * Esta es la clase de Libro, que es un Articulo
 * @author Junco de las Heras y Marta Vaquerizo
 */

package ads.p2.facturacion;

public class Libro extends Articulo {
	String categoria;

	public Libro(long id, String desc, double precio, String categoria) {
		super(id, desc, precio);
		this.categoria = categoria;
	}

	/**
	 * @return 0.04, que es el iva del libro
	 */
	public double tipoIVA() {
		return 0.04;
	}

	/**
	 * @param unidades número de unidades de las que se va a hacer el descuento
	 * @return 0 en general, a menos que sea LibroDeTexto que es 0.15 o que sea
	 *         Coleccion que es 0.5 a partir de la 3ª unidad
	 */
	public double descuento(double unidades) {
		if (categoria.equals("LibroDeTexto")) {
			return this.getPrecioUnitario() * unidades * 0.15;
		}
		if (categoria.equals("Coleccion")) {
			if (unidades >= 3.0) {
				return this.getPrecioUnitario() * (unidades - 2) * 0.5;
			}
			return 0;
		}
		return 0;
	}

}
