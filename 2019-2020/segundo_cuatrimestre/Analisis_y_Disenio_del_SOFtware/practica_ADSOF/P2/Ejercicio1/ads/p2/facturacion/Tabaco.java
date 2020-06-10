/**
 * Esta es la clase de Tabaco, que es un Articulo
 * @author Junco de las Heras y Marta Vaquerizo
 */

package ads.p2.facturacion;

public class Tabaco extends Articulo {

	public Tabaco(long id, String desc, double precio) {
		super(id, desc, precio);
	}

	/**
	 * @param unidades número de unidades de las que se va a hacer el descuento
	 * @return 0 no hay descuento
	 */
	public double descuento(double unidades) {
		return 0.0;
	}
}
