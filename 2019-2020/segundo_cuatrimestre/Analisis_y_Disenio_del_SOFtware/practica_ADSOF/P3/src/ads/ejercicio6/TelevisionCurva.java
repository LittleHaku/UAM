package ads.ejercicio6;

/**
 * la clase TelevisionCurva, un tipo de Television
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class TelevisionCurva extends Television {
	private double peso;
	private Dimension dimension;

	/**
	 * @param marca           la marca
	 * @param modelo          el modelo
	 * @param precioBase      el precioBase
	 * @param claseEnergetica la claseEnergetica
	 * @param tamanio         el tamanio
	 * @param peso            el peso
	 * @param dimension       la dimension
	 */
	public TelevisionCurva(String marca, String modelo, double precioBase, ClaseEnergetica claseEnergetica,
			double tamanio, double peso, Dimension dimension) {
		super(marca, modelo, precioBase, claseEnergetica, tamanio);
		this.peso = peso;
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
	 * @return double coste de entrega a domicilio
	 */
	@Override
	public double costePorte() {
		return super.costePorte() + 25.0 * (dimension.calcularVolumen());
	}

}
