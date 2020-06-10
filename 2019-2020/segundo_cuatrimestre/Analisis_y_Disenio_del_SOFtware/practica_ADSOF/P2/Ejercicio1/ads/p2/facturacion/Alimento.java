/**
 * Esta es la clase de Alimento, que es un Articulo
 * @author Junco de las Heras y Marta Vaquerizo
 */

package ads.p2.facturacion;

public class Alimento extends Articulo {
	private boolean promo3x2;

	public Alimento(long id, String desc, double precio, boolean promo) {
		super(id, desc, precio);
		promo3x2 = promo;
	}

	/**
	 * @return true si hay promo3x2 y false en caso contrario
	 */
	public boolean getPromo3x2() {
		return promo3x2;
	}

	/**
	 * Es el setter de la promocion 3x2
	 * 
	 * @param promo booleano de si hay promocion o no
	 */
	public void setPromo3x2(boolean promo) {
		promo3x2 = promo;
	}

	/**
	 * @return 0.10 que es el tipo de IVA general
	 */
	public double tipoIVA() {
		return 0.10;
	}

	/**
	 * @param unidades número de unidades de las que se va a hacer el descuento
	 * @return 0 en general, a menos que haya un descuento 3x2
	 */
	public double descuento(double unidades) {
		if (promo3x2)
			return getPrecioUnitario() * (int) (unidades / 3);
		else
			return 0.0;
	}
}
