package modelo.colectivoCiudadano;

import java.util.Set;

import modelo.proyecto.Proyecto;

/**
 * Esta interfaz representa a un integrante de un colectivo.
 * <p>
 * Algunos de los aspectos en común que comparten los elementos que conforman
 * los colectivos (Ciudadanos y subcolectivos) son:
 * <ul>
 * <li>Tienen una lista de sus ciudadanos (él solo en el caso de un ciudadano)
 * <li>Tienen un representante (él mismo en el caso del ciudadano)
 * <li>Tienen un nombre que los identifica
 * <li>Ambos son añadidos y eliminados de colectivos por los que se podemos
 * definir su comportamiento cuendo son añadidos o eliminados
 * </ul>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 27-03-20
 *
 */
public interface ElementoColectivo {

    /**
     * Devuelve el nombre asociado a este elemento
     * <p>
     * En el caso de los ciudadanos será su nombre de usuario <br>
     * Para los colectivos se devolverá el nombre del colectivo <br>
     * 
     * @return El nombre
     */
    public String getName();

    /**
     * Devuelve todos los ciudadadano pertenecientes a este elemento
     * <p>
     * Cabe destacar que se incluyen tanto los ciudadanos que pertenecen
     * directamenta a este elemento como los que pertenecen a subelementos de este.
     * <br>
     * Es decir, los ciudadanos de subcolectivos de un colectivo estarían incluídos
     * en el resultado para ese colectivo. <br>
     * 
     * En el caso de los ciudadanos, se devuelve el ciudadadano solo.
     * 
     * @return El set de ciudadanos pertenecientes.
     */
    public Set<Ciudadano> getTodosCiudadanos();

    /**
     * Devuelve el representante asociado a este elemento
     * <p>
     * En el caso de los ciudadanos se devuelve él mismo ya que esta es una
     * extensión lógica <br>
     * de la definición de representante (es el ciudadano capaz de apoyar los
     * proyectos y crear nuevos colectivos) <br>
     * al caso de un solo ciudadano
     * 
     * @return El ciudadano representante
     */
    public Ciudadano getRepresentante();

    /**
     * Incluye las acciones que se deben de realizar sobre el elemento cuando este
     * es añadido a un colectivo
     * <p>
     * Este método se debe de llamar desde el método que añade al elemento al
     * colectivo <br>
     * Este método hará cambios en los datos del ElementoColectivo provocados por su
     * adición al colectivo
     * 
     * @param c Colectivo al que el elemento es añadido
     */
    public void added(Colectivo c);

    /**
     * Incluye las acciones que se deben de realizar sobre el elemento cuando este
     * es eliminado a un colectivo
     * <p>
     * Este método se debe de llamar desde el método que elimina al elemento al
     * colectivo <br>
     * Este método hará cambios en los datos del ElementoColectivo provocados por su
     * eliminación del colectivo
     * 
     * @param c Colectivo al que el elemento es añadido
     */
    public void removed(Colectivo c);

    /**
     * Incluye las acciones que se deben de realizar sobre el elemento cuando este
     * propone un proyecto
     * <p>
     * Este método se debe de llamar desde el método que crea el nuevo proyecto <br>
     * Este método hará cambios en los datos del ElementoColectivo provocados por la
     * creación del nuevo proyecto
     * 
     * @param p Proyecto propuesto
     */
    public void proponeProyecto(Proyecto p);

    /**
     * Incluye las acciones que se deben de realizar sobre el elemento cuando este
     * apoya un proyecto
     * <p>
     * Este método se debe de llamar desde el método en el que se apoya el proyecto
     * <br>
     * Este método hará cambios en los datos del ElementoColectivo provocados por el
     * apoyo a un nuevo proyecto
     * 
     * @param p Proyecto propuesto
     */
    public void apoyaProyecto(Proyecto p);

    /**
     * Devuelve los proyectos propuestos por este elemento
     * 
     * @return Proyectos propuestos por este elemento
     */
    public Set<Proyecto> getProyPropuestos();

    /**
     * Devuelve los proyectos propuestos por este elemento
     * 
     * @return Proyectos propuestos por este elemento
     */
    public Set<Proyecto> getProyApoyados();
}
