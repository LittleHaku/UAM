package ads.ejercicio6;

/**
 * Clase Electrodomestico, abstracta
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */

public abstract class Electrodomestico {
	private String marca;
	private String modelo;
	private double precioBase;
	private ClaseEnergetica claseEnergetica;

	/**
	 * @param marca           la marca
	 * @param modelo          el modelo
	 * @param precioBase      el precioBase
	 * @param claseEnergetica la claseEnergetica
	 */
	public Electrodomestico(String marca, String modelo, double precioBase, ClaseEnergetica claseEnergetica) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.precioBase = precioBase;
		this.claseEnergetica = claseEnergetica;
		Stock.add(this);
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the precioBase
	 */
	public double getPrecioBase() {
		return precioBase;
	}

	/**
	 * @param precioBase the precioBase to set
	 */
	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}

	/**
	 * @return the claseEnergetica
	 */
	public ClaseEnergetica getClaseEnergetica() {
		return claseEnergetica;
	}

	/**
	 * @param claseEnergetica the claseEnergetica to set
	 */
	public void setClaseEnergetica(ClaseEnergetica claseEnergetica) {
		this.claseEnergetica = claseEnergetica;
	}

	/**
	 * @return String con los atributos de Electrodomestico
	 */
	public String toString() {
		return "" + marca + " " + modelo + ", " + String.format("%.2f", precioBase) + " Euros";
	}

	/**
	 * @return el coste de porte del electrodomestico
	 */
	public abstract double costePorte();

	/**
	 * @param viejo el Electrodomestico viejo
	 * @return double coste de entrega a domicilio
	 */
	public double descuento(Electrodomestico viejo) {
		if (viejo == null)
			return 0.0;
		if (viejo.claseEnergetica.equals(ClaseEnergetica.Desconocida)) {
			return 10.0;
		}
		if (viejo.claseEnergetica.equals(this.claseEnergetica)) {
			return 25.0;
		} else if (viejo.claseEnergetica.valor() < this.claseEnergetica.valor()) {
			return 25.0 + 15.0 * (this.claseEnergetica.valor() - viejo.claseEnergetica.valor());
		} else {
			return 25.0 - 5.0 * (viejo.claseEnergetica.valor() - this.claseEnergetica.valor());
		}
	}

	/**
	 * @param o el objeto a comparar
	 * @return true si los objetos son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object o) {
		// Si son de distinto tipo los objetos
		if (this.getClass() != o.getClass()) {
			return false;
		}

		Electrodomestico e = (Electrodomestico) o;

		if (((e.getModelo()).equals(this.getModelo())) && ((e.getMarca()).equals(this.getMarca()))) {
			return true;
		}

		return false;
	}
}
