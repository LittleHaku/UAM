package ads.practica3;

/**
 * la clase Television
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Television extends Electrodomestico {
	private double tamanio;

	/**
	 * @param marca           la marca
	 * @param modelo          el modelo
	 * @param precioBase      el precioBase
	 * @param claseEnergetica la claseEnergetica
	 * @param tamanio         el tamanio
	 */
	public Television(String marca, String modelo, double precioBase, ClaseEnergetica claseEnergetica, double tamanio) {
		super(marca, modelo, precioBase, claseEnergetica);
		this.tamanio = tamanio;
	}

	/**
	 * @return the tamanio
	 */
	public double getTamanio() {
		return tamanio;
	}

	/**
	 * @param tamanio the tamanio to set
	 */
	public void setTamanio(double tamanio) {
		this.tamanio = tamanio;
	}

	/**
	 * @return double coste de entrega a domicilio
	 */
	public double costePorte() {
		if (tamanio <= 40) {
			if (getPrecioBase() > 500) {
				return 0.0;
			} else {
				return 35.0;
			}
		}

		return 35.0 + (tamanio - 40.0);
	}

}
