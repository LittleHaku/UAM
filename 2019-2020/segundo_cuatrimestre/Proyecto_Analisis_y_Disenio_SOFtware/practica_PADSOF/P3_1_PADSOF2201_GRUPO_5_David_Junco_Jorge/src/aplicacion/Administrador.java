package aplicacion;

import java.util.*;

import notificacion.*;

/**
 * Esta clase representa a un administrador
 * <p>
 * El administrador es el usuario que, entre otras funciones, regula <br>
 * el registro de nuevos usuarios y la creación de proyectos.
 * <p>
 * A nivel de clase, se caracteriza por contener una lista de notificaciones. de
 * solicitud de admisión<br>
 * Esta lista contine las notificaciones de solicitudes que no han sido
 * aprobadas o rechazadas. <br>
 * Al aprobar o rechazar la solicitud, esta se debe eliminar de la lista.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 25-03-20
 *
 */
public class Administrador extends Usuario {

	private static final long serialVersionUID = 4590137384366306044L;

	private List<NotificacionAdmision> notif;

	/**
	 * Crea un nuevo administrador con el usuario y la contraseña dada
	 * <p>
	 * 
	 * @param usr      Nobre de usuario del administrador
	 * @param password Contraseña del administrador
	 */
	public Administrador(String usr, String password) {
		super(usr, password);
		notif = new ArrayList<>();
	}

	/**
	 * Comprueba si el usuario y la contraseña se corresponden con los almacenados
	 * para este administrador
	 * <p>
	 * En otras palabras, devuelve si este usuario puede inicar sesión con ese
	 * usuario y contraseña
	 * 
	 * @param usr Usuario a validar
	 * @param pwd Contraseña a validar
	 * @return <code>true</code> si estos coinciden con los almacenados<br>
	 *         <code>false</code> si no coinciden
	 */
	public boolean validarCredenciales(String usr, String pwd) {

		return ((usr.equals(this.getUsr())) && (pwd.equals(this.getPassword())));

	}

	/**
	 * Añade una notificación a la lista de notificaciones del administrador
	 * 
	 * @param a la notifiacion a añadir
	 */
	public void addNotificacion(NotificacionAdmision a) {
		this.notif.add(a);
	}

	/**
	 * Devuelve la lista de notificaciones de admisión de este administrador
	 * 
	 * @return Lista de las notificaciones
	 */
	public List<NotificacionAdmision> getNotificaciones() {
		return notif;
	}
}
