package aplicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import colectivoCiudadano.Ciudadano;
import colectivoCiudadano.Colectivo;
import es.uam.eps.sadp.grants.InvalidIDException;
import es.uam.eps.sadp.grants.InvalidRequestException;
import notificacion.*;
import proyecto.Distrito;
import proyecto.Proyecto;

/**
 * Esta clase es la responsable de guardar toda la información y de contener a
 * todos los elementos de otras clases
 * <p>
 * Si bien el diseño de esta clase está pensado para que solo haya una instancia
 * de ella en tiempo de ejecución, <br>
 * la mayoría de sus atributos no son de clase (entre otros motivos, para
 * facilitar su serialización).<br>
 * El único atributo de clase que hemos establecido es el número mínimo de
 * apoyos para que un proyecto pueda ser enviado <br>
 * Los demás elementos que guarda esta clase son:
 * <ul>
 * <li>Un mapa de tipo <code> {@literal <String,Usuario>} </code> que contiene
 * todos los usuarios asociados a su nombre de usuario
 * <li>Un mapa de tipo <code> {@literal <String,String>} </code> que relaciona
 * los NIF de los ciudadanos registrados con su nombre de usuario <br>
 * (ya que también pueden usar el NIF para iniciar sesión)
 * <li>Un mapa de tipo <code> {@literal <String,Colectivo>} </code> que
 * relaciona los colectivos con su nombre.
 * <li>Un mapa de tipo <code> {@literal <Integer,Proyecto>} </code> que
 * relaciona los proyectos con su id único.
 * <li>Una lista de los distritos que están disponibles en este momento <br>
 * (normalmente será una lista de los distritos por defecto)
 * <li>El usuario que ha iniciado sesion actualmente .
 * <li>El administrador de la aplicación (ni su nombre de usuario ni su
 * contraseña pueden cambiar).
 * </ul>
 * <p>
 * El uso de mapas en vez de arrays o sets se debe a la facilidad y rapidez de
 * acceder a un elemento si conocemos la "key" correspondiente.<br>
 * Por ejemplo, para que un usuario inicie sesión, hemos de comprobar los datos
 * aportados con sus credenciales. <br>
 * Como ya tenemos un nombre de usuario, con el mapa, podemos acceder al usuario
 * correspondiente para comprobar la contraseña en O(1).
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 28-03-20
 *
 *
 */
public class Aplicacion implements Serializable {

	private static final long serialVersionUID = 3808402353922838292L;
	/**
	 * Este objeto guardara todas las variables estaticas
	 */
	private Serializer serializer = new Serializer();
	private Map<String, Usuario> 	usuarios;
	private Map<String, String> 	nifaUsuario;
	private Map<String, Colectivo> 	colectivos;
	private Map<Integer, Proyecto> 	proyectos;
	private List<Distrito> 			distritos;

	private Usuario usrActivo;
	private Administrador admin = new Administrador("admin", "admin");
	private static int apoyosMinimos = 1;

	/**
	 * Crea una nueva aplicación
	 * <p>
	 * Esta tomará la lista de distritos por defecto especificada en la clase
	 * distrito.<br>
	 * Su administrador tendrá como usuario y contraseña "admin" y "admin",
	 * respectivamente.<br>
	 * No contará con ningún ciudadano, proyecto o colectivo.
	 */
	public Aplicacion() {
		usuarios 	= new HashMap<>();
		nifaUsuario = new HashMap<>();
		colectivos 	= new HashMap<>();
		proyectos 	= new HashMap<>();
		distritos 	= new ArrayList<>(Arrays.asList(Distrito.getDistritosPorDefecto())); // Para que se puedan añadir
																						// elementos a la coleccion
	}

	/**
	 * Establece el numero mínimo de apoyos para que un proyecto pueda ser enviado
	 * 
	 * @param nuevoval El nuevo número mínimo de apoyos
	 */
	public static void setApoyosMinimos(int nuevoval) {
		Aplicacion.apoyosMinimos = nuevoval;
	}

	/**
	 * Devuelve el numero mínimo de apoyos para que un proyecto pueda ser enviado
	 * 
	 * @return Los apoyos mínimos para enviar un proyecto
	 */
	public static int getApoyosMinimos() {
		return Aplicacion.apoyosMinimos;
	}

	/**
	 * Modifica la serialización de los objetos de la clase Aplicación
	 * <p>
	 * Con este método conseguimos que al hacer una serialización de una Aplicación,
	 * <br>
	 * primero se actualicen los valores guardados en el objeto que utilizamos para
	 * guardar las variables estáticas en la serialización
	 * 
	 * @param out el ObjectOutputStream a escribir
	 * @throws IOException error con el OutputStream
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		serializer.guardarValores();
		out.defaultWriteObject();
	}

	/**
	 * Modifica la serialización de los objetos de la clase Aplicación
	 * <p>
	 * Con este método conseguimos que al hacer una deserialización de una
	 * Aplicación, <br>
	 * al final, se actualicen los valores de las variable estáticas a los guardados
	 * en el serializer.
	 * 
	 * @param in el ObjectInputStream a leer
	 * @throws IOException            error con el InputStream
	 * @throws ClassNotFoundException si se intenta serializar algo no serializable
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		serializer.recuperarValores();
	}

	/**
	 * Intenta iniciar sesión con los credenciales dados
	 * <p>
	 * El método busca si hay un usuario que responda a ese nombre de usuario. <br>
	 * Si es así, se intenta iniciar sesión. Se conseguirá iniciar sesión si el
	 * usuario encontrado valida los credenciales dados. <br>
	 * Asimismo, si se puede iniciar sesión, la aplicación ejecutará las labores de
	 * mantenimiento (llamando a <code> Aplicacion.ejecutarMantenimiento </code>)
	 * 
	 * @param usr Usuario dado
	 * @param pwd Contraseña dada
	 * @return Si se ha podido iniciar sesión, el nuevo usuario de la aplicación.
	 *         <br>
	 *         Si no se ha podido, <code> null</code>.
	 */
	public Usuario iniciarSesion(String usr, String pwd) {
		Usuario res = null;

		usr = nifaUsuario.getOrDefault(usr, usr);

		Usuario u = usuarios.getOrDefault(usr, null);

		if (u != null && u.validarCredenciales(usr, pwd)) {
			this.usrActivo = u;
			res = u;
		}

		if (admin.validarCredenciales(usr, pwd)) {
			this.usrActivo = admin;
			res = admin;

		}

		if (res != null) {
			ejecutarMantenimiento();
		}
		return res;
	}

	/**
	 * Cierra la sesión actual
	 */
	public void cerrarSesion() {
		this.usrActivo = null;
	}

	/**
	 * Procesa una solicitud de registro de un ciudadano en la aplicación
	 * <p>
	 * La solicitud se podrá presentar al administrador si en ella no hay NIF o
	 * nombre de usuario repetido. <br>
	 * Aunque la solicitud sea presentada, el ciudadano no podrá iniciar sesión
	 * hasta que el administrador la apruebe.
	 * 
	 * @param name Nombre de usuario para el nuevo ciudadano
	 * @param pass Contraseña para el nuevo ciudadano
	 * @param nif  NIF del nuevo ciudadano
	 * @return <code> true </code> si se ha podido presentar la solicitud <br>
	 *         <code> false </code> si había datos repetidos
	 */
	public boolean registro(String name, String pass, String nif) {
		if (usuarios.containsKey(name) || usuarios.containsKey(nif)) {
			return false;
		}

		if (nifaUsuario.containsKey(name) || nifaUsuario.containsKey(nif)) {
			return false;
		}

		if (admin.getUsr().equals(name) || admin.getUsr().equals(nif)) {
			return false;
		}

		Ciudadano nuevo = new Ciudadano(name, pass, nif);

		if (addCiudadano(nuevo)) {
			admin.addNotificacion(new NotificacionRegistro(nuevo));
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Ejecuta las labores de mantenimiento que deben de realizarse periódicamente
	 * <p>
	 * Como ejemplo de labor de mantenimiento, podemos considerar la comprobación de
	 * proyectos caducados
	 */
	public void ejecutarMantenimiento() {
		actualizarCaducados();
	}

	/**
	 * Comprueba si algún proyecto ha caducado y, en caso afirmativo, lo marca como
	 * caducado
	 */
	public void actualizarCaducados() {
		proyectos.values().forEach(Proyecto::actualizarCaducado);
	}

	/**
	 * Comprueba si un proyecto puede ser enviado
	 * 
	 * @param p Proyecto que se quiere comprobar
	 * @return <code> true </code> si el proyecto puede ser enviado <br>
	 *         <code> false </code> en caso contrario
	 */
	public boolean isProyectoEnviable(Proyecto p) {
		return p.getNumApoyos() >= Aplicacion.apoyosMinimos;
	}

	/**
	 * Añade un proyecto y presenta la solicitud para que este sea aprobado por el
	 * administrador
	 * <p>
	 * Tras la ejecución de este método, el proyecto ya estará almacenado en la
	 * aplicación pero no se podrá interactuar con él
	 * <p>
	 * El proyecto no podrá se añadido si ya está en esta aplicación
	 * 
	 * @param p Proyecto a añadir
	 * @return <code> true </code> si el proyecto ha sido añadido <br>
	 *         <code> false </code> en caso contrario
	 */
	public boolean addProyectoParaAutorizar(Proyecto p) {

		admin.addNotificacion(new NotificacionCreacionProyecto(p));
		return addProyecto(p);
	}

	/**
	 * Añade un colectivo, si es posible
	 * <p>
	 * Un colectivo puede ser añadido siempre y cuando no exista ya otro con el
	 * mismo nombre (el nombre de un colectivo es único)
	 * 
	 * @param c Colectivo a añadir
	 * @return <code> true </code> si el colectivo ha sido añadido <br>
	 *         <code> false </code> en caso contrario
	 */
	public boolean addColectivo(Colectivo c) {
		if (colectivos.containsKey(c.getName()))
			return false;

		this.colectivos.put(c.getName(), c);
		return true;
	}

	/**
	 * Añade un proyecto, si este no existe ya
	 * <p>
	 * En este caso, el proyecto se añade pero no se envia ninguna notificación al
	 * administrador
	 * 
	 * @param p Proyecto a añadir
	 * @return <code> true </code> si el proyecto ha sido añadido <br>
	 *         <code> false </code> en caso contrario
	 */
	public boolean addProyecto(Proyecto p) {
		if (proyectos.containsKey(p.getId()))
			return false;

		this.proyectos.put(p.getId(), p);
		return true;
	}

	/**
	 * Añade un ciudadano, si no hay datos repetidos
	 * <p>
	 * En este caso, el ciudadano se añade pero no se envia ninguna notificación al
	 * administrador
	 * 
	 * @param c Ciudadano a añadir
	 * @return <code> true </code> si el ciudadano ha sido añadido <br>
	 *         <code> false </code> en caso contrario
	 */
	public boolean addCiudadano(Ciudadano c) {
		if (usuarios.containsKey(c.getUsr()) || usuarios.containsKey(c.getNif())) {
			return false;
		}

		if (nifaUsuario.containsKey(c.getUsr()) || nifaUsuario.containsKey(c.getNif())) {
			return false;
		}

		if (admin.getUsr().equals(c.getUsr()) || admin.getUsr().equals(c.getNif())) {
			return false;
		}

		this.usuarios.put(c.getUsr(), c);
		this.nifaUsuario.put(c.getNif(), c.getUsr());
		return true;
	}

	/**
	 * Elimina un proyecto
	 * <p>
	 * Esta función solo debería ser utilizada con fines de comprobar el
	 * funcionamiento de la aplicación, <br>
	 * los proyectos no deben eliminarse
	 * 
	 * @param p Proyecto a eliminar
	 */
	public void eliminarProyecto(Proyecto p) {
		proyectos.remove(p.getId());
	}

	/**
	 * Elimina el registro de un ciudadano
	 * <p>
	 * Esta función solo debería ser utilizada con fines de comprobar el
	 * funcionamiento de la aplicación, <br>
	 * los usuarios no deben eliminarse
	 * 
	 * @param c Ciudadano a eliminar
	 */
	public void eliminarCiudadano(Ciudadano c) {
		usuarios.remove(c.getUsr());
		nifaUsuario.remove(c.getNif());
	}

	/**
	 * Comprueba si el usuario activo es un administrador
	 * 
	 * @return<code> true </code> si el usuario activo es un administrador <br>
	 * <code> false </code> en caso contrario
	 */
	public boolean ModoAdministrador() {
		return this.usrActivo.equals(admin);
	}

	/**
	 * Produce el informe de afinidad
	 * <p>
	 * Un informe de afinidad se define como la proporcion de proyectos propuesto
	 * por un colectivo que ha sido apoyados por el otro entre los proyectos que han
	 * propuestos por ambos. En concreto, la fórmula para calcularlo es:
	 * <p>
	 * (Proyectos propuestos por {@code C1} apoyados por {@code C2} + Proyectos
	 * propuestos por {@code C2} apoyados por {@code C1}) / (Proyectos propuestos
	 * por {@code C1} + Propuestos apoyados por {@code C2})
	 * 
	 * @param C1 primer colectivo
	 * @param C2 segundo colectivo
	 * @return el informe de afinidad
	 */
	public double getInformeAfinidad(Colectivo C1, Colectivo C2) {
		List<Proyecto> p_C1 = new ArrayList<Proyecto>();
		List<Proyecto> p_C2 = new ArrayList<Proyecto>();
		List<Proyecto> apoyos_C1 = new ArrayList<Proyecto>();
		List<Proyecto> apoyos_C2 = new ArrayList<Proyecto>();

		this.getTodosProyectos().stream().filter(e -> e.getCreador().equals(C1)).forEachOrdered(p_C1::add);
		this.getTodosProyectos().stream().filter(e -> e.getCreador().equals(C2)).forEachOrdered(p_C2::add);

		this.getTodosProyectos().stream().filter(e -> e.getApoyos().contains(C1)).forEachOrdered(apoyos_C1::add);
		this.getTodosProyectos().stream().filter(e -> e.getApoyos().contains(C2)).forEachOrdered(apoyos_C2::add);

		Set<Proyecto> C1_con_ap_C2 = p_C1.stream().filter(apoyos_C2::contains).collect(Collectors.toSet());
		Set<Proyecto> C2_con_ap_C1 = p_C2.stream().filter(apoyos_C1::contains).collect(Collectors.toSet());

		if (C1_con_ap_C2.isEmpty() && C2_con_ap_C1.isEmpty()) {
			return 0;
		}

		double afinidad = (double) ((C1_con_ap_C2.size()) + (C2_con_ap_C1.size())) / (p_C1.size() + p_C2.size());

		return afinidad;

	}

	/**
	 * Comprueba la respuesta del sistema externo ante un proyecto enviado
	 * <p>
	 * <b>Cabe destacar que para uso externo se debe usar este método en vez de
	 * <code> Proyecto.consultar </code> </b>
	 * <p>
	 * Llama a <code> Proyecto.consultar </code> y, si el proyecto ha sido
	 * RECHAZADO, este es eliminado de la aplicacion (notificando a todos los
	 * subscriptores)
	 * 
	 * @param p El proyecto a consultar
	 * @return Si el proyecto ya ha sido enviado, el importe adjudicado o
	 *         <code> null </code> si el sistema externo aún no lo ha evaluado. <br>
	 *         Si el proyecto no ha sido enviado, <code> null </code>
	 * 
	 * @throws IOException        Si no se ha podido comunicar con el sistema
	 *                            externo
	 * @throws InvalidIDException Si el sistema externo no tiene constancia del
	 *                            Proyecto
	 * @see Proyecto#consultar()
	 */
	public Double consultar(Proyecto p) throws IOException, InvalidIDException {
		Double d = p.consultar();

		if (d != null && d.equals(0.0)) {
			this.eliminarProyecto(p);
		}
		return d;
	}

	/**
	 * Envia el proyecto al sistema externo de financiación
	 * <p>
	 * <b>Cabe destacar que para uso externo se debe usar este método en vez de
	 * <code> Proyecto.enviar </code> </b>
	 * <p>
	 * Para que el proyecto pueda ser enviado, han de cumplirse las condiciones <br>
	 * definidas en los métodos <code> Aplicacion.isProyectoEnviable </code> y
	 * <code> Proyecto.enviar </code>
	 * 
	 * @param p El proyecto a enviar
	 * @return <code> true </code> si el proyecto se ha enviado correctametne <br>
	 *         <code> false </code> si el proyecto no se ha podido enviar
	 * 
	 * @throws IOException             Si no se ha podido comunicar con el sistema
	 *                                 externo
	 * @throws InvalidRequestException Si el sistema externo no acepta la petición
	 * 
	 * @see Proyecto#enviar()
	 */
	public boolean enviar(Proyecto p) throws IOException, InvalidRequestException {
		if (!isProyectoEnviable(p)) {
			return false;
		} else
			return p.enviar();
	}

	/**
	 * Devuelve un Set con todos los usuarios de la aplicación
	 * 
	 * @return Set con todos los usuarios
	 */
	public Set<Usuario> getTodosUsuarios() {
		HashSet<Usuario> hs = new HashSet<>();
		hs.addAll(usuarios.values());
		hs.add(admin);
		return hs;
	}

	/**
	 * Devuelve un Set con todos los usuarios de la aplicación que hayan sido
	 * aprobados
	 * 
	 * @return Set con todos los usuarios aprobados
	 */
	public Set<Usuario> getUsuariosAprobados() {
		HashSet<Usuario> hs = new HashSet<>();
		for (Usuario u : usuarios.values()) {
			if (u instanceof Ciudadano) {
				if (((Ciudadano) u).isRegistroAprobado()) {
					hs.add(u);
				}
			} else {
				hs.add(u);
			}
		}
		hs.add(admin);
		return hs;
	}

	/**
	 * Devuelve un Set con todos los colectivos en la aplicación
	 * 
	 * @return Set con todos los colectivos
	 */
	public Set<Colectivo> getColectivos() {
		TreeSet<Colectivo> hs = new TreeSet<>();
		hs.addAll(colectivos.values());
		return hs;
	}

	/**
	 * Devuelve un Set con todos los proyectos en la aplicación
	 * 
	 * @return Set con todos los proyectos
	 */
	public Set<Proyecto> getTodosProyectos() {
		TreeSet<Proyecto> hs = new TreeSet<>();
		hs.addAll(proyectos.values());
		return hs;
	}

	/**
	 * Devuelve un Set con todos los proyectos en la aplicación que hayan sido
	 * aprobados por el administrador
	 * 
	 * @return Set con todos los proyectos aprobados
	 */
	public Set<Proyecto> getProyectosAprobados() {
		TreeSet<Proyecto> hs = new TreeSet<>();
		proyectos.values().stream().filter(p -> p.getEstado() != Proyecto.EstadoProyecto.PROPUESTO).forEach(hs::add);
		return hs;
	}

	/**
	 * Devuelve una lista con los distritos guardados en la aplicación
	 * 
	 * @return Lista de Distritos
	 */
	public List<Distrito> getDistritos() {
		return distritos;
	}

	/**
	 * Devuelve el usuario activo
	 * 
	 * @return El usuario activo
	 */
	public Usuario getUsrActivo() {
		return usrActivo;
	}

	/**
	 * Busca un colectivo dado su nombre
	 * 
	 * @param nombre Nombre a buscar
	 * 
	 * @return El colectivo que tiene ese nombre, si existe. <br>
	 *         <code> null </code> si no exite ninguno
	 */
	public Colectivo encontrarColectivo(String nombre) {
		return colectivos.getOrDefault(nombre, null);
	}

	/**
	 * Devuelve una lista con todos los proyectos que tienen el nombre dado
	 * 
	 * @param nombre Nombre a buscar
	 * @return Lista de los proyectos con ese nombre (puede estar vacía)
	 */
	public List<Proyecto> encontrarProyecto(String nombre) {
		List<Proyecto> proyect = new ArrayList<>();
		proyectos.values().stream().filter(e -> e.getTitulo().equals(nombre)).forEach(e -> proyect.add(e));
		return proyect;
	}

	/**
	 * Busca un ciudadano dado su nombre
	 * 
	 * @param nombre Nombre a buscar
	 * @return El ciudadano encontrado <br>
	 *         <code> null</code> si no se ha encontrado ninguno
	 */
	public Ciudadano encontrarCiudadano(String nombre) {
		Usuario u = usuarios.getOrDefault(nombre, null);

		if (u instanceof Ciudadano)
			return (Ciudadano) u;

		return null;
	}

	/**
	 * Establece los distritos de la aplicación
	 * 
	 * @param distritos Nueva lista de distritos
	 */
	public void setDistritos(List<Distrito> distritos) {
		this.distritos = distritos;
	}

	/**
	 * Establece el usario activo de la aplicación
	 * <p>
	 * Solo debe de ser usado para el "testing" de la clase
	 * 
	 * @param usrActivo Nuevo usuario activo
	 */
	public void setUsrActivo(Usuario usrActivo) {
		this.usrActivo = usrActivo;
	}

}
