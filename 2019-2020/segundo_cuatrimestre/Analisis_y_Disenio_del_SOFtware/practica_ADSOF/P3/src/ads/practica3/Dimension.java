package ads.practica3;

/**
 * Clase dimension, compuesta por alto, ancho y fondo
 * 
 * @author Junco de las Heras y Marta Vaquerizo
 *
 */
public class Dimension {
	private double alto;
	private double ancho;
	private double fondo;

	/**
	 * @param alto  dimension alto
	 * @param ancho dimension ancho
	 * @param fondo dimension fondo
	 */
	public Dimension(double alto, double ancho, double fondo) {
		super();
		this.alto = alto;
		this.ancho = ancho;
		this.fondo = fondo;
	}

	/**
	 * @return the alto
	 */
	public double getAlto() {
		return alto;
	}

	/**
	 * @param alto the alto to set
	 */
	public void setAlto(double alto) {
		this.alto = alto;
	}

	/**
	 * @return the ancho
	 */
	public double getAncho() {
		return ancho;
	}

	/**
	 * @param ancho the ancho to set
	 */
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	/**
	 * @return the fondo
	 */
	public double getFondo() {
		return fondo;
	}

	/**
	 * @param fondo the fondo to set
	 */
	public void setFondo(double fondo) {
		this.fondo = fondo;
	}

	/**
	 * @return el volumen, la multiplicacion de las dimensiones / 1000000
	 */
	public double calcularVolumen() {
		return (alto * ancho * fondo) / (1000000);
	}

}
