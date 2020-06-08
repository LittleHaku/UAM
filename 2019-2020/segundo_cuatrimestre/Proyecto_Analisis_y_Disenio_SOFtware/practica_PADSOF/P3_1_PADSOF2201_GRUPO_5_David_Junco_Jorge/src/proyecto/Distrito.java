package proyecto;

import java.io.Serializable;

/**
 * Esta clase representa a un distrito. Cada distrito se caracteriza por un
 * nombre. Los distritos se utilizan para definir las zonas a las que afectan
 * los proyectos de infraestructura.
 * 
 * Por defecto se ha incluido una lista de 20 distritos predefinidos
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 23-03-20
 */
public class Distrito implements Serializable {

	private static final long serialVersionUID = 3174507353102845916L;

	private String nombre;

	private static final Distrito[] distritosPorDefecto = { new Distrito("Arganzuela"), new Distrito("Barajas"),
			new Distrito("Carabanchel"), new Distrito("Centro"), new Distrito("Chamartin"), new Distrito("Chamberi"),
			new Distrito("Ciudad Lineal"), new Distrito("Fuencarral el Pardo"), new Distrito("Hortaleza"),
			new Distrito("Latina"), new Distrito("Moncloa Aravaca"), new Distrito("Puente de Vallecas"),
			new Distrito("Retiro"), new Distrito("Salamanca"), new Distrito("San Blas Canillejas"),
			new Distrito("Tetuan"), new Distrito("Usera"), new Distrito("Vicalvaro"), new Distrito("Villa de Vallecas"),
			new Distrito("Villaverde"), };

	/**
	 * Devuelve una lista con los 20 distritos que se han incluido por defecto
	 * 
	 * @return Lista con los distritos
	 */
	public static Distrito[] getDistritosPorDefecto() {
		return Distrito.distritosPorDefecto;
	}

	/**
	 * Crea un nuevo destrito con el nombre dado
	 * 
	 * @param nombre Nombre del nuevo distrito
	 */
	public Distrito(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el nombre del distrito
	 * 
	 * @return El nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del distrito
	 * 
	 * @param nombre Nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la representación del objeto en forma de String, en este caso, el
	 * nombre del distrito
	 * 
	 * @return String que representa al distrito
	 */
	@Override
	public String toString() {
		return nombre;
	}

	/**
	 * 
	 * 
	 * Comprueba la igualdad entre dos objetos
	 * <p>
	 * Si el objeto dado no es un distrito, nunca serán iguales. <br>
	 * Si ambos son distritos, un distrito es igual a otro si sus nombres son
	 * iguales
	 * 
	 * {@inheritDoc}
	 * 
	 * @param b Objeto a comparar
	 * @return Si los objetos son iguales
	 * 
	 * 
	 */
	@Override
	public boolean equals(Object b) {
		if (this == b)
			return true;
		if (b == null || b.getClass() != this.getClass())
			return false;

		return this.nombre.equals(((Distrito) b).getNombre());
	}
}
