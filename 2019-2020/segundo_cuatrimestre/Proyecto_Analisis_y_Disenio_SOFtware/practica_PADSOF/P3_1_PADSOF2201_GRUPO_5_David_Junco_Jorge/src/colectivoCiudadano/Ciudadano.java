package colectivoCiudadano;

import java.util.*;

import aplicacion.Usuario;
import notificacion.*;
import proyecto.Proyecto;

/**
 * Esta clase representa a un ciudadano que es usuario de la aplicación
 * <p>
 * Los ciudadanos se caracterizan por tener los siguientes atributos:
 * <ul>
 * <li>Un nombre de usuario (Al igual que cualquier usuario de la aplicación
 * <li>Una contraseña para acceder
 * <li>Un NIF que debe ser de (como mucho) 9 caracteres y que puede ser usado
 * para acceder a la aplicación
 * <li>Un conjunto de colectivos a los que pertenece
 * <li>Una lista de notificaciones sobre cambios de estado en los proyectos a
 * los que está subscrito o de proyectos rechazados
 * <li>Si el usuario ha sido bloqueado por el administrador
 * <li>Si el registro del usuario ha sido aprobado por el administrador
 * </ul>
 * <p>
 * Sobre los estados del usuario, podemos hacer las siguientes
 * observaciones:<br>
 * Para que el usuario pueda iniciar sesión en la aplicación, su registro tiene
 * que haber sido aprobado por el administrador. Una vez tiene aprobado su
 * registro, ya puede acceder a la aplicación. <br>
 * Sin embargo, a partir de ese momento, puede ser bloqueado por el
 * administrador. Cuando un usuario ha sido bloqueado, sus apoyos no cuentan en
 * los proyectos hasta que es desbloqueado.<br>
 *
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 28-03-20
 *
 */
public class Ciudadano extends Usuario implements ElementoColectivo {

	private static final long serialVersionUID = 4026291045155379294L;

	/**
	 * Máxima longitud del NIF
	 */
	public static final int MAX_NIF_LEN = 9;
	private boolean bloqueado;
	private boolean registroAprobado;
	private String nif;
	private Set<Colectivo> pertenece;
	private Set<Colectivo> representa;
	private Set<Proyecto> proyectosPropuestos;
	private List<Notificacion> notificaciones;

	/**
	 * Crea un nuevo ciudadano con los valores dados
	 * 
	 * @param usr      Nombre de usuario
	 * @param password Contraseña
	 * @param nif      NIF
	 */
	public Ciudadano(String usr, String password, String nif) {
		super(usr, password);

		if (nif.length() > MAX_NIF_LEN) {
			throw new IllegalArgumentException("Se ha excedido la longitud máxima del nif");
		}

		this.nif = nif;
		pertenece = new HashSet<>();
		representa = new HashSet<>();
		proyectosPropuestos = new HashSet<>();
		notificaciones = new ArrayList<>();
	}

	/**
	 * Comprueba si los credenciales aportados son válidos
	 * <p>
	 * Para que estos se consideren validos, se tienen que cumplir simultaneamente:
	 * <ul>
	 * <li>El usuario aportado es igual al nombre de usuario del ciudadano o su NIF
	 * <li>La contraseña aportada es igual a la contraseña del ciudadano
	 * </ul>
	 * 
	 * @param usr Usuario aportado
	 * @param pwd Contraseña aportada
	 * @return <code> true </code> si el usuario y la contraseña se consideran
	 *         válidos <br>
	 *         <code> false </code> en caso contrario
	 */
	@Override
	public boolean validarCredenciales(String usr, String pwd) {

		return (((usr.equals(this.getUsr()) || usr.equals(nif))) && (pwd.equals(this.getPassword())));
	}

	/**
	 * Incluye las acciones que se deben de realizar sobre el ciudadano cuando este
	 * es añadido a un colectivo
	 * <p>
	 * En concreto, añade el colectivo a la colección de colectivos a la que
	 * pertenece el ciudadano. <br>
	 * Además, si el colectivo es nuevo y este es el representante, lo añade a la colección
	 * de colectivos representados
	 * 
	 * @param c Colectivo al que el ciudadano se ha apuntado
	 */
	@Override
	public void added(Colectivo c) {
		pertenece.add(c);
		if (c.getRepresentante().equals(this)) {
			representa.add(c);
		}
	}

	/**
	 * Incluye las acciones que se deben de realizar sobre el ciudadano cuando este
	 * es eliminado de un colectivo
	 * <p>
	 * En concreto, elimina el colectivo de la colección de colectivos a la que
	 * pertenece el ciudadano
	 * 
	 * @param c Colectivo del que el ciudadano se ha borrado
	 */
	@Override
	public void removed(Colectivo c) {
		pertenece.remove(c);
	}

	@Override
	public void proyPropuesto(Proyecto p) {
		this.proyectosPropuestos.add(p);

	}

	/**
	 * Añade una notificacion a la lisa de notificaciones del Ciudadano
	 * 
	 * @param n Notificación recibida
	 */
	public void addNotificacion(Notificacion n) {
		notificaciones.add(n);
	}

	/**
	 * Devuelve si el ciudadano está bloqueado
	 * <p>
	 * Si el ciudadano está bloqueado, sus apoyos serán ignorados.
	 * 
	 * @return <code> true</code> si esta bloqueado, <br>
	 *         <code>false </code> si no lo está
	 */
	public boolean isBloqueado() {
		return bloqueado;
	}

	/**
	 * Devuelve si el registro del ciudadano ha sido aprobado
	 * <p>
	 * Mientras su registro no haya sido aprobado, el ciudadano no podrá usar su
	 * cuenta para iniciar sesión en la aplicación
	 * 
	 * @return <code> true</code> si este ha sido aprobado, <br>
	 *         <code>false </code> si no lo ha sido
	 */
	public boolean isRegistroAprobado() {
		return registroAprobado;
	}

	/**
	 * Devuelve el NIF del ciudadano
	 * 
	 * @return El NIF
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Devuelve el nombre del ciudadano
	 * 
	 * @return El nombre
	 */
	public String getName() {
		return this.getUsr();
	}

	@Override
	public Set<Proyecto> getProyPropuestos() {
		return proyectosPropuestos;
	}

	/**
	 * Devuelve todos los proyectos que este ciudadano ha propuesto
	 * <p>
	 * En este método se devuelven tanto los proyectos que el ciudadano ha propuesto
	 * directamente <br>
	 * como los que haya propuesto en nombre de un colectivo al que represente
	 * 
	 * @return Todos los proyectos propuestos por este ciudadano
	 */
	public Set<Proyecto> getTodosProyPropuestos() {
		Set<Proyecto> res = new HashSet<>();
		this.getProyPropuestos().forEach(res::add);
		representa.forEach(col -> col.getProyPropuestos().forEach(res::add));

		return res;
	}

	/**
	 * Devuelve los colectivos a los que este ciudadano representa
	 * 
	 * @return Colectivos representados por este ciudadano
	 */
	public Set<Colectivo> getRepresenta() {
		return this.representa;
	}

	/**
	 * Devuelve el set de colectivos a los que el ciudadano pertenece directamente
	 * 
	 * @return Set de colectivos a los que pertenece
	 */
	public Set<Colectivo> getPertenece() {
		return pertenece;
	}

	@Override
	public Set<Ciudadano> getTodosCiudadanos() {
		Set<Ciudadano> c = new HashSet<>();
		c.add(this);
		return c;
	}

	@Override
	public Ciudadano getRepresentante() {
		return this;
	}

	/**
	 * Devuelve la lista de notificaciones asociada a este ciudadano
	 * 
	 * @return La lista de Notificaciones
	 */
	public List<Notificacion> getNotificaciones() {
		return this.notificaciones;
	}

	/**
	 * Establece si el ciudadano está bloqueado
	 * 
	 * @param bloqueado Nuevo valor de bloqueado
	 */
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	/**
	 * Establece si el registro del ciudadano ha sido aprobado
	 * 
	 * @param aprobado Si el registro ha sido aprobado
	 */
	public void setRegistroAprobado(boolean aprobado) {
		this.registroAprobado = aprobado;
	}

	/**
	 * Establece el NIF del ciudadano
	 * 
	 * @param nif Nuevo valor del NIF del ciudadano
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * Establece la lista de notificaciones del ciudadano
	 * 
	 * @param c Nueva lista de notificaciones
	 */
	public void setNotificaciones(List<Notificacion> c) {
		this.notificaciones = c;
	}

	/**
	 * Devuelve una representación en forma de caracteres de este ciudadano
	 * <p>
	 * En la cadena devuelta se incluye el valor de todos los campos así como la
	 * lista de colectivos a los que pertenece
	 * 
	 * @return La cadena de caracteres
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\nNIF:\t\t");
		sb.append(this.nif);
		sb.append("\nBloqueado:\t");
		sb.append(this.bloqueado);
		sb.append("\nPertenece a:\n");
		pertenece.forEach(e -> sb.append("\t" + e.getName() + "\n"));

		return sb.toString();
	}

}
