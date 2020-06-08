package notificacion;

import aplicacion.Aplicacion;
import colectivoCiudadano.Ciudadano;

/**
 * Esta clase representa una notificación de registro de un nuevo ciudadano
 * <p>
 * Estas notificaciones se generan cuando los ciudadanos solicitan <br>
 * su registro en la aplicación y son enviadas al administrador <br>
 * para que este apruebe o rechaze su registro
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 25-03-20
 *
 */
public class NotificacionRegistro extends NotificacionAdmision {

	private static final long serialVersionUID = 5969555302649746087L;
	private Ciudadano c;

	/**
	 * Crea una nueva notificación de registro
	 * 
	 * @param c Ciudadano que solicita registrarse
	 */
	public NotificacionRegistro(Ciudadano c) {
		super("El ciudadano " + c.getName() + " ha solicitado su registro en la aplicacion");
		this.c = c;
	}

	/**
	 * Devuelve el ciudadano que ha solicitado registrarse
	 * 
	 * @return c Ciudadano que ha solicitado el registro
	 */
	public Ciudadano getCiudadano() {
		return this.c;
	}

	/**
	 * Marca al ciudadano como aceptado
	 * <p>
	 * Este método es el que se ejecutará cuando el administrador elija aceptar el
	 * proyecto
	 */
	@Override
	public void aceptar() {
		c.setRegistroAprobado(true);
	}

	@Override
	public void rechazar(Aplicacion a, String s) {
		a.eliminarCiudadano(c);

	}
}
