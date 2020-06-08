package modelo.notificacion;

import modelo.proyecto.Proyecto;

/**
 * Esta clase representa una notificación de cambio en el estadado de un
 * proyecto
 * <p>
 * Estas notificaciones se embiarán a los ciudadanos subscritos a un proyecto
 * cuando se produzcan modificaciones en su estado. <br>
 * Estas modificaciones pueden ser, por ejemplo, el envio del proyecto <br>
 * o su aceptación por parte del sistema externo
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 25-03-20
 *
 */
public class NotificacionCambioEstado extends Notificacion {

    private static final long serialVersionUID = 8214812185134542474L;
    private Proyecto proy;

    /**
     * Crea una nueva notificación de cambio de estado
     * <p>
     * 
     * @param proy         Proyecto que ha cambiado de estado
     * @param nuevo_estado String que representa el nuevo estado del proyecto
     */
    public NotificacionCambioEstado(Proyecto proy, String nuevo_estado) {
	super("El proyecto \"" + proy.getTitulo() + "\" ha pasado al estado de \"" + nuevo_estado + "\"");
	this.proy = proy;
    }

    /**
     * Devuelve el Proyecto que ha cambiado de estado
     * 
     * @return El proyecto
     */
    public Proyecto getProy() {
	return proy;
    }

    /**
     * Establece el proyecto que ha cambiado de estado
     * 
     * @param proy El nuevo proyecto
     */
    public void setProy(Proyecto proy) {
	this.proy = proy;
    }

}
