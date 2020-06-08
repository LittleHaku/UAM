package proyecto;

import java.util.*;

import colectivoCiudadano.ElementoColectivo;

/**
 * Esta clase representa a un proyecto social.
 * <p>
 * En los proyectos sociales, además de los atributos correspondientes a todos
 * los proyectos, consideramos:
 * <ul>
 * <li>Los distritos a los que el proyecto afectará
 * <li>Una imagen que se guardará con la ruta al archivo en disco
 * </ul>
 * <p>
 * Si bien los distritos procederán en un principio de la lista de distritos por
 * defecto, <br>
 * se pueden incluir distritos con cualquier nombre en la lista de distritos a
 * los que el proyecto afecta.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 23-03-20
 *
 */
public class ProyInfraestructura extends Proyecto {

	private static final long serialVersionUID = -9178410068151003206L;

	private List<Distrito> distritosAfectados;
	private String imagen;

	/**
	 * Crea un nuevo proyecto de infraestructura con los parámetros dados.
	 * 
	 * @param titulo        Titulo del proyecto (máximo 25 caracteres)
	 * @param descripcion   Descripción del proyecto (máximo 500 caracteres)
	 * @param importePedido Importe que se solicitará al sistema externo cuando se
	 *                      envie el proyecto
	 * @param creador       Entidad creadora del proyecto
	 * @param distritos     Lista con los distritos que se verán afectados por el
	 *                      proyecto
	 * @param imagen        Ruta a la imagen que describe el proyecto
	 * 
	 * @throws IllegalArgumentException Si la longitud del titulo es demasiado larga
	 * @throws IllegalArgumentException Si la longitud de la descripción es
	 *                                  demasiado larga
	 */
	public ProyInfraestructura(String titulo, String descripcion, double importePedido, ElementoColectivo creador,
			List<Distrito> distritos, String imagen) {

		super(titulo, descripcion, importePedido, creador);
		this.distritosAfectados = distritos;
		this.imagen = imagen;
	}

	/**
	 * Devuelve la lista de distritos afectados
	 * 
	 * @return La lista de distritos afectados
	 */
	public List<Distrito> getDistritos() {
		return distritosAfectados;
	}

	/**
	 * Devuelve ruta a la imagen que describe al proyecto
	 * 
	 * @return La ruta a la imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * Establece la lista de distritos afectados
	 * 
	 * @param distritos La nueva lista de distritos afectados
	 */
	public void setDistritos(List<Distrito> distritos) {
		this.distritosAfectados = distritos;
	}

	/**
	 * Establece la nueva ruta a la imagen que describe al proyecto
	 * 
	 * @param imagen Nueva ruta a la imagen
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\nImagen:\t\t\t");
		sb.append(this.imagen);
		sb.append("\nAfecta a " + this.distritosAfectados.size() + " distritos:\n");
		for (Distrito d : this.distritosAfectados) {
			sb.append("\t" + d.toString() + "\n");
		}
		return sb.toString();
	}

}
