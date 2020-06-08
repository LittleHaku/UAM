package proyecto;

import colectivoCiudadano.ElementoColectivo;

/**
 * Esta clase representa a un proyecto social.
 * <p>
 * En los proyectos sociales, además de los atributos correspondientes a todos
 * los proyectos, consideramos:
 * <ul>
 * <li>El grupo social que respalda el proyecto
 * <li>El alcance del proyecto, que puede ser nacional o internacional
 * </ul>
 * 
 * 
 * 
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 23-03-20
 *
 */
public class ProySocial extends Proyecto {

	private static final long serialVersionUID = -5834871303294385887L;

	private static final int MAX_GRUPO_LEN = 50;
	private String grupo;
	private boolean nacional;

	/**
	 * Crea un nuevo proyecto social con los valores dados.
	 * 
	 * @param titulo        Titulo del proyecto (máximo 25 caracteres)
	 * @param descripcion   Descripción del proyecto (máximo 500 caracteres)
	 * @param importePedido Importe que se solicitará al sistema externo cuando se
	 *                      envie el proyecto
	 * @param creador       Entidad creadora del proyecto
	 * @param grupo         Nombre del grupo social que respalda el proyecto (máximo
	 *                      50 caracteres)
	 * @param nacional      <code> true </code> si el alcance del proyecto es
	 *                      nacional; <code> false </code> si es internacional
	 * 
	 * @throws IllegalArgumentException Si la longitud del titulo es demasiado larga
	 * @throws IllegalArgumentException Si la longitud de la descripción es
	 *                                  demasiado larga
	 * @throws IllegalArgumentException Si la longitud del nombre del grupo social
	 *                                  es demasiado larga
	 */
	public ProySocial(String titulo, String descripcion, double importePedido, ElementoColectivo creador, String grupo,
			boolean nacional) {
		super(titulo, descripcion, importePedido, creador);

		if (grupo.length() > MAX_GRUPO_LEN) {
			throw new IllegalArgumentException("Se ha excedido la longitud máxima del nombre del grupo social");
		}

		this.grupo = grupo;
		this.nacional = nacional;
	}

	/**
	 * Devuelve el nombre del grupo social que respalda el proyecto
	 * 
	 * @return Nombre del grupo social
	 */
	public String getGrupo() {
		return grupo;
	}

	/**
	 * Devuelve si el alcance el proyecto es nacional o internacional
	 * 
	 * @return <code> true </code> si el alcance del proyecto es nacional <br>
	 *         <code> false </code> si es internacional
	 */
	public boolean isNacional() {
		return nacional;
	}

	/**
	 * Establece el nombre del grupo social que respalda el proyecto
	 * 
	 * @param grupo Nuevo nombre del grupo social
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	/**
	 * Establece si el alcance el proyecto es nacional o internacional
	 * 
	 * @param nacional Nuevo valor del campo nacional. <code> true </code> indica
	 *                 que es nacional; <code> false </code> indica que es
	 *                 internacional
	 */
	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\nGrupo social:\t\t");
		sb.append(this.grupo);
		sb.append("\nAlcance el proyecto:\t");
		sb.append(nacional ? "Nacional" : "Internacional");
		return sb.toString();
	}

}
