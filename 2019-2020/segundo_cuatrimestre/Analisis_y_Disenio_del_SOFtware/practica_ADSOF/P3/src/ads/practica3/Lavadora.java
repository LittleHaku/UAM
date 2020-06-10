package ads.practica3;

/**
 * la clase Lavadora
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Lavadora extends ElectrodomesticoCocina {
	private double kilosCarga;
	private double rpm;

	/**
	 * @param marca           la marca
	 * @param modelo          el modelo
	 * @param precioBase      el precioBase
	 * @param claseEnergetica la claseEnergetica
	 * @param dimension       la dimension
	 * @param peso            el peso
	 * @param kilosCarga      el kilosCarga
	 * @param rpm             el rpm
	 */
	public Lavadora(String marca, String modelo, double precioBase, ClaseEnergetica claseEnergetica,
			Dimension dimension, double peso, double kilosCarga, double rpm) {
		super(marca, modelo, precioBase, claseEnergetica, dimension, peso);
		this.kilosCarga = kilosCarga;
		this.rpm = rpm;
	}

	/**
	 * @param marca           la marca
	 * @param modelo          el modelo
	 * @param precioBase      el precioBase
	 * @param claseEnergetica la claseEnergetica
	 * @param dimension       la dimension
	 * @param peso            el peso
	 */
	public Lavadora(String marca, String modelo, double precioBase, ClaseEnergetica claseEnergetica,
			Dimension dimension, double peso) {
		super(marca, modelo, precioBase, claseEnergetica, dimension, peso);
	}

	/**
	 * @return the kilosCarga
	 */
	public double getKilosCarga() {
		return kilosCarga;
	}

	/**
	 * @param kilosCarga the kilosCarga to set
	 */
	public void setKilosCarga(double kilosCarga) {
		this.kilosCarga = kilosCarga;
	}

	/**
	 * @return the rpm
	 */
	public double getRpm() {
		return rpm;
	}

	/**
	 * @param rpm the rpm to set
	 */
	public void setRpm(double rpm) {
		this.rpm = rpm;
	}

	/**
	 * @return double coste de entrega a domicilio
	 */
	public double costePorte() {
		if (getPeso() <= 50) {
			return 35.0;
		}

		return 35.0 + 0.5 * (getPeso() - 50);
	}

}
