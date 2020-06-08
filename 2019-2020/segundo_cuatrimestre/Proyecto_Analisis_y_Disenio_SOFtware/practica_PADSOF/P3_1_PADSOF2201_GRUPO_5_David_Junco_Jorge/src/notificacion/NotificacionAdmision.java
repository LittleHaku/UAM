package notificacion;

import aplicacion.Aplicacion;

/**
 * Esta clase representa una notificación de solicitud de admisión
 * <p>
 * Consideramos notificaciones de admisión aquellas que se mandan al
 * administrador para comunicarle <br>
 * una solicitud de admisión a la aplicación.
 * <p>
 * Estas solicitudes de admisión pueden generarse, por ejemplo, en los
 * siguientes escenarios:
 * <ul>
 * <li>Cuando un nuevo usuario solicita su registro
 * <li>Cuando un usuario solicita proponer un proyecto
 * </ul>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 25-03-20
 *
 */
public abstract class NotificacionAdmision extends Notificacion {

	private static final long serialVersionUID = -8779166193292604025L;

	/**
	 * Crea una nueva notificación de solicitud de admisión
	 * 
	 * @param cuerpo Texto de la notificación
	 */
	public NotificacionAdmision(String cuerpo) {
		super(cuerpo);
	}

	/**
	 * Acepta la solicitud de admisión
	 * <p>
	 * En la implementación de este método se incluirán las acciones que deban de
	 * realizarse para aceptar la solicitud
	 */
	public abstract void aceptar();

	/**
	 * Realiza los cambios en la aplicación relacionados con el rechazo de la
	 * admisión del elemento
	 * 
	 * @param a      Aplicación en la que se realizan los cambios
	 * @param motivo Descripción del motivo del rechazo, Si no se quiere indicar
	 *               motivo, <code> null </code>
	 */
	public abstract void rechazar(Aplicacion a, String motivo);
}
