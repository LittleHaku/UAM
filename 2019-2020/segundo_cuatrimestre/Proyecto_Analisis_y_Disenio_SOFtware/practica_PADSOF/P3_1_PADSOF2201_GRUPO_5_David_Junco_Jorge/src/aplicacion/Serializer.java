package aplicacion;

import java.io.Serializable;

import proyecto.Proyecto;

/**
 * Clase utilizada para guardar las variables estáticas durante la serialización
 * <p>
 * En principio, se incluyen los valores de:
 * <ul>
 * <li>Apoyos minimos para enviar un proyecto
 * <li>id del siguiente proyecto que se cree
 * </ul>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 28-03-20
 *
 */
public class Serializer implements Serializable {
	private static final long serialVersionUID = 6089370532470196337L;
	private int apoyosMinimos;
	private int id_siguiente = 1;

	public Serializer() {
	}

	/**
	 * Guarda los valores de las variables estáticas en el objeto
	 */
	public void guardarValores() {
		apoyosMinimos = Aplicacion.getApoyosMinimos();
		id_siguiente = Proyecto.getId_siguiente();
	}

	/**
	 * Establece los valores de las variables estáticas a los guardados en el objeto
	 */
	public void recuperarValores() {
		Aplicacion.setApoyosMinimos(apoyosMinimos);
		Proyecto.setId_siguiente(id_siguiente);
	}

}
