package modelo.notificacion;

import modelo.aplicacion.Aplicacion;
import modelo.proyecto.Proyecto;
import modelo.proyecto.Proyecto.EstadoProyecto;

/**
 * Esta clase representa una notificación de creación de proyecto
 * <p>
 * Estas notificaciones se generan cuando los ciudadanos proponen <br>
 * nuevos proyectos en la aplicación y son enviadas al administrador <br>
 * para que este apruebe o rechaze los proyectos
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 25-03-20
 *
 */
public class NotificacionCreacionProyecto extends NotificacionAdmision {

    private static final long serialVersionUID = 6055250858037947310L;
    private Proyecto p;

    /**
     * Crea una nueva notificación de creación de proyecto
     * 
     * @param p Proyecto que ha sido creado
     */
    public NotificacionCreacionProyecto(Proyecto p) {
	super("El proyecto " + p.getTitulo() + " ha sido propuesto por " + p.getCreador().getName());
	this.p = p;
    }

    /**
     * Devuelve el proyecto que ha sido creado
     * 
     * @return p Proyecto que ha sido creado
     */
    public Proyecto getProyecto() {
	return this.p;
    }

    /**
     * Pasa el proyecto al estado de votación
     * <p>
     * Este método es el que se ejecutará cuando el administrador elija aceptar el
     * proyecto
     * 
     */
    @Override
    public void aceptar() {
	p.setEstado(EstadoProyecto.EN_VOTACION);
	p.actualizarEnviable();
	p.getCreador().proponeProyecto(p); // Para que el creador registre que ha creado este proyecto
    }

    @Override
    public void rechazar(Aplicacion a, String s) {
	p.getCreador().getRepresentante().addNotificacion(
		new Notificacion("El proyecto \"" + p.getTitulo() + "\" ha sido rechazado alegando: \n" + s));
	a.eliminarProyecto(p);

    }

}
