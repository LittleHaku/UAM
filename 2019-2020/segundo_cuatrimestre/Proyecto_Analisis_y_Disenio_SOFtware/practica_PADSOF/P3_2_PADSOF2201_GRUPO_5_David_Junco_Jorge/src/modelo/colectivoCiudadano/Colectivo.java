package modelo.colectivoCiudadano;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import exceptions.IllegalActionException;
import modelo.proyecto.Proyecto;

/**
 * Esta clase representa a un colectivo
 * <p>
 * Un colectivo es un conjunto de ciudadanos y otros colectivos que se
 * caracteriza por:
 * <ul>
 * <li>Tiene un representante, que es el ciudadadano que lo crea. <br>
 * El representante es el único que puede proponer o apoyar proyectos en nombre
 * del colectivo o crear subcolectivos
 * <li>Tiene un nombre que debe de ser único
 * <li>Tiene una colección de integrantes. Estos puede ser tanto ciudadanos como
 * otros colectivos
 * <li>Tiene un colectivo padre que tomará el valor de <code> null </code> si el
 * colectivo no pertenece a ningún otro colectivo
 * <li>Puede crear y apoyar proyectos (que contarán con el apoyo de todos los
 * miembros del colectivo)
 * </ul>
 * <p>
 * Por otro lado, en relación a la pertenencia de un ciudadano a un colectivo a
 * un colectivo distinguimos dos casos
 * <ul>
 * <li>Pertenencia directa: un ciudadano pertenece directamente si pertenece al
 * set de integrantes
 * <li>Pertenencia indirecta: un ciudadano pertenece inderectamente si pertenece
 * directa o indirectamente a un elemento del set de integrantes (a un
 * subcolectivo)
 * </ul>
 * 
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 28-03-20
 */
public class Colectivo implements ElementoColectivo, Comparable<Colectivo>, Serializable {

    private static final long serialVersionUID = 1469586734375992363L;
    private String name;
    private Ciudadano representante;
    private Set<ElementoColectivo> integrantes;
    private Set<Proyecto> proyectosPropuestos;
    private Set<Proyecto> proyectosApoyados;
    private Colectivo colectivoPadre = null;

    /**
     * Crea un nuevo colectivo con los datos dados
     * 
     * @param representante Representante del colectivo
     * @param name          Nombre del colectivo
     */
    public Colectivo(Ciudadano representante, String name) {
	super();
	this.representante = representante;
	this.name = name;
	integrantes = new HashSet<>();
	proyectosPropuestos = new HashSet<>();
	proyectosApoyados = new HashSet<>();
	this.add(representante);
    }

    /**
     * Comprueba si se puede añadir un elemento a un colectivo
     * <p>
     * Un ciudadano se puede añadir a un colectivo siempre y cuando no pertenezca
     * (directa o indirectamente) al colectivo ni a ningino de sus subcolectivos.
     * <br>
     * Los subcolectivos se pueden añadir siempre y cuando no pertenezcan ya al
     * colectivo
     * 
     * @param e Elemento a añadir
     * 
     * @return <code> true </code> si el elemento se puede añadir <br>
     *         <code> false </code> si no se puede añadir
     * 
     */
    public boolean canAdd(ElementoColectivo e) {
	if (integrantes.contains(e)) return false;

	if (e instanceof Ciudadano) {
	    Ciudadano ciu = (Ciudadano) e;

	    // mira los colectivos hijo
	    if (perteneceASubcolectivo(ciu)) {
		return false;
	    }

	    // mira los colectivos padre
	    Colectivo cpadre = this.colectivoPadre;
	    while (cpadre != null) {

		if (cpadre.getDirectosCiudadanos().contains(ciu)) {
		    return false;
		}

		cpadre = cpadre.getColectivoPadre();
	    }
	}
	return true;
    }

    /**
     * Intenta añadir un elemento a un colectivo
     * <p>
     * Un ciudadano se puede añadir a un colectivo siempre y cuando no pertenezca
     * (directa o indirectamente) al colectivo ni a ningino de sus subcolectivos.
     * <br>
     * Los subcolectivos se pueden añadir siempre y cuando no pertenezcan ya al
     * colectivo
     * 
     * @param e Elemento a añadir
     * 
     * @return <code> true </code> si el elemento se ha podido añadir <br>
     *         <code> false </code> si no se ha podido añadir
     */
    public boolean add(ElementoColectivo e) {

	if (!canAdd(e)) return false;

	integrantes.add(e);
	e.added(this);
	return true;
    }

    /**
     * Comprueba si el ciudadano pertenece (directa o indirectamente) a uno de los
     * subcolectivos de este colectivo
     * 
     * @param ciu Ciudadano cuya pertenencia se comprueba
     * 
     * @return <code> true </code> si pertenece <br>
     *         <code> false </code> si no pertenece
     */
    private boolean perteneceASubcolectivo(Ciudadano ciu) {

	for (Colectivo col : this.getSubcolectivos()) {

	    if (col.getDirectosCiudadanos().contains(ciu)) {
		return true;
	    } else if (col.perteneceASubcolectivo(ciu)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Intenta borrar un elemento del colectivo
     * 
     * @param e ElementoColectivo a borrar
     * 
     * @throws IllegalActionException Si el ciudadano es el representante
     * 
     */
    public void remove(ElementoColectivo e) throws IllegalActionException {
	if (e.equals(representante)) {
	    throw new IllegalActionException("Usted es el represenante",
		    "No pude salir de los colectivos a los que representa");
	}
	integrantes.remove(e);
	e.removed(this);
    }

    @Override
    public void added(Colectivo c) {
	this.colectivoPadre = c;
    }

    @Override
    public void removed(Colectivo c) {
	this.colectivoPadre = null;
    }

    @Override
    public void proponeProyecto(Proyecto p) {
	this.proyectosPropuestos.add(p);
	this.proyectosApoyados.add(p);

    }

    @Override
    public void apoyaProyecto(Proyecto p) {
	this.proyectosApoyados.add(p);

    }

    @Override
    public Set<Proyecto> getProyPropuestos() {

	return proyectosPropuestos;
    }

    @Override
    public Set<Proyecto> getProyApoyados() {
	return proyectosApoyados;
    }

    @Override
    public Ciudadano getRepresentante() {
	return representante;
    }

    @Override
    public String getName() {
	return name;
    }

    /**
     * Comprueba si un elemento pertenece directamente a este colectivo
     * 
     * @param c Elemento cuya pertenecia se comprueba
     * 
     * @return <code> true </code> si pertenece <br>
     *         <code> false </code> si no pertenece
     */
    public boolean contains(ElementoColectivo c) {
	return this.integrantes.contains(c);
    }

    /**
     * Devuelve los integrantes del colectivo
     * <p>
     * Se considera integrantes a los elementos (ciudadanos u otros colectivos) <br>
     * que pertenecen directamente al colectivo
     * 
     * @return Integrantes del colectivo
     */
    public Set<ElementoColectivo> getElementos() {
	return integrantes;
    }

    /**
     * Devuelve los ciudadanos que pertenecen directamente al colectivo
     * 
     * @return El set de ciudadanos
     */
    public Set<Ciudadano> getDirectosCiudadanos() {
	return integrantes.stream().filter(e -> e instanceof Ciudadano).map(e -> (Ciudadano) e)
		.collect(Collectors.toSet());
    }

    @Override
    public Set<Ciudadano> getTodosCiudadanos() {
	return integrantes.stream().flatMap(e -> e.getTodosCiudadanos().stream()).collect(Collectors.toSet());
    }

    /**
     * Devuelve los colectivos que pertenecen directamente al colectivo
     * 
     * @return El set de subcolectivos
     */
    public Set<Colectivo> getSubcolectivos() {
	Set<Colectivo> c = new HashSet<>();
	integrantes.stream().filter(e -> e instanceof Colectivo).forEach(e -> c.add((Colectivo) e));
	return c;
    }

    /**
     * Devuelve el colectivo padre
     * 
     * @return El colectivo padre
     */
    public Colectivo getColectivoPadre() {
	return colectivoPadre;
    }

    /**
     * Establece el representante del colectivo
     * 
     * @param representante El nuevo representante
     */
    public void setRepresentante(Ciudadano representante) {
	this.representante = representante;
    }

    /**
     * Establece el nombre del colectivo
     * 
     * @param name Nuevo nombre para el colectivo
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Calcula el hash de este colectivo
     * <p>
     * La inclusion de este método permite el almacenamiento de colectivos <br>
     * en estructuras de datos como los <code> HashMap </code>
     * 
     * 
     * @return Hashscode del colectivo
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    /**
     * Comprueba si dos objetos son iguales
     * <p>
     * Si el objeto dado no es un colectivo, siempre devolverá falso<br>
     * En el caso de dos colectivos, se considera que son iguales si sus nombres lo
     * son <br>
     * (ya que los nombres son únicos)
     * 
     * @param obj Objeto a comparar
     * 
     * @return <code> true </code> si son iguales <br>
     *         <code> false </code> si son diferentes
     */
    @Override
    public boolean equals(Object obj) {

	if (obj == null || getClass() != obj.getClass()) return false;

	Colectivo other = (Colectivo) obj;
	if (name == null && other.name != null) return false;
	if (name != null && !name.equals(other.name)) return false;

	return true;
    }

    /**
     * Devuelve una representación en forma de cadena de caracteres de este
     * colectivo.
     * <p>
     * En esta se incluyen todos los campos y una lista de los integrantes con sus
     * nombres
     * 
     * @return Representación en forma de cadena de caracteres
     */
    @Override
    public String toString() {
	StringBuilder b = new StringBuilder();
	b.append("Nombre:\t\t\t");
	b.append(name);
	b.append("\nRepresentante:\t\t");
	b.append(representante.getUsr());
	b.append("\nColectivo Padre:\t");
	b.append((colectivoPadre == null) ? "" : colectivoPadre.name);
	b.append("\nIntegrantes:\n");
	integrantes.forEach(e -> b.append("\t" + e.getName() + "\n"));

	return b.toString();
    }

    /**
     * Compara dos colectivos
     * <p>
     * Dos colectivos se comparan según su nombre (siguiendo un orden lexicográfico)
     * 
     * @param o Colectivo con que se compara
     * 
     * @return <code> {@literal < 0} </code> si el nombre de este colectivo es menor
     *         <br>
     *         <code> = 0 </code> si son el mismo colectivo <br>
     *         <code> {@literal > 0} </code> si el nombre de este colectivo es mayor
     */
    @Override
    public int compareTo(Colectivo o) {
	return this.getName().compareTo(o.getName());
    }

}
