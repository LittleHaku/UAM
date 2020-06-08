package modelo.notificacion;

import java.io.Serializable;

/**
 * Esta clase representa una notificación
 * <p>
 * Las notificaciones se envian a los usuarios por parte de la aplicación para
 * comunicar diversos eventos. <br>
 * En esta clase implementamos la funcionalidad y los atributos comunes a todas
 * las notificaciones que son:
 * <ul>
 * <li>El texto de la notificación (el que se mostrará al usuario). <br>
 * En ocasiones nos refiriremos a este texto como el cuerpo de la notificación
 * <li>Si la notificación ha sido vista por el usuario o no. <br>
 * </ul>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 25-03-20
 *
 */

public class Notificacion implements Serializable {

    private static final long serialVersionUID = -7911534414544688000L;

    private String cuerpo;
    private boolean visto;

    /**
     * Crea una nueva Notificacion con el texto que se le pasa. <br>
     * El campo visto toma el valor por defecto false
     * 
     * @param cuerpo Texto de la notificación
     */
    public Notificacion(String cuerpo) {
	this.cuerpo = cuerpo;
	this.visto = false;
    }

    /**
     * Devuelve el cuerpo de la notificacion
     * 
     * @return El cuerpo
     */
    public String getCuerpo() {
	return cuerpo;
    }

    /**
     * Devuelve si la notificación ha sido vista por el usario
     * 
     * @return El valor del campo visto
     */
    public boolean isVisto() {
	return visto;
    }

    /**
     * Establece el texto de la notificación
     * 
     * @param cuerpo El nuevo texto de la notificación
     */
    public void setCuerpo(String cuerpo) {
	this.cuerpo = cuerpo;
    }

    /**
     * Establece si la notificación ha sido vista por el usario
     * 
     * @param visto El nuevo valor de visto
     */
    public void setVisto(boolean visto) {
	this.visto = visto;
    }

    /**
     * Devuelve el texto de la notificación
     * 
     * @return El texto de la notificación
     */
    @Override
    public String toString() {
	return cuerpo;
    }

    /**
     * @param o el objeto a comparar
     * @return Devuelve true si son iguales, false sino
     */
    @Override
    public boolean equals(Object o) {
	if (this.getClass().equals(o.getClass())) {
	    return cuerpo.equals(((Notificacion) o).getCuerpo());
	}
	return false;
    }
}
