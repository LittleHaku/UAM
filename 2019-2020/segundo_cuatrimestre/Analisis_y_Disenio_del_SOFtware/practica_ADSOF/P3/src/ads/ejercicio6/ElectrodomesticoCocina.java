package ads.ejercicio6;

/**
 * Clase ElectrodomesticoCocina, abstracta
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public abstract class ElectrodomesticoCocina extends Electrodomestico {
	private Dimension dimension;
	private double peso;

	/**
	 * @param marca           la marca
	 * @param modelo          el modelo
	 * @param precioBase      el precioBase
	 * @param claseEnergetica la claseEnergetica
	 * @param dimension       la dimension
	 * @param peso            el peso
	 */
	public ElectrodomesticoCocina(String marca, String modelo, double precioBase, ClaseEnergetica claseEnergetica,
			Dimension dimension, double peso) {
		super(marca, modelo, precioBase, claseEnergetica);
		this.dimension = dimension;
		this.peso = peso;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * @param dimension the dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

}
