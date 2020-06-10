package ads.ejercicio6;

/**
 * la clase Frigorifico
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Frigorifico extends ElectrodomesticoCocina {
	private boolean frost;

	/**
	 * @param marca           la marca
	 * @param modelo          el modelo
	 * @param precioBase      el precioBase
	 * @param claseEnergetica la claseEnergetica
	 * @param dimension       la dimension
	 * @param peso            el peso
	 * @param frost           booleano de si es frost o no
	 */
	public Frigorifico(String marca, String modelo, double precioBase, ClaseEnergetica claseEnergetica,
			Dimension dimension, double peso, boolean frost) {
		super(marca, modelo, precioBase, claseEnergetica, dimension, peso);
		this.frost = frost;
	}

	/**
	 * @return the frost
	 */
	public boolean isFrost() {
		return frost;
	}

	/**
	 * @param frost the frost to set
	 */
	public void setFrost(boolean frost) {
		this.frost = frost;
	}

	/**
	 * @return double coste de entrega a domicilio
	 */
	public double costePorte() {
		return getDimension().calcularVolumen() * 70.0;
	}

}
