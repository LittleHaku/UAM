package proyecto;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import colectivoCiudadano.*;
import notificacion.*;
import es.uam.eps.sadp.grants.*;

/**
 * Esta clase representa un Proyecto.
 * <p>
 * Los proyectos son propuestos por ciudadanos o colectivos y pueden ser de
 * diversos tipos. <br>
 * En esta clase implementamos los atributos y la funcionalidad común a todos
 * ellos.
 * <p>
 * Todos los proyectos, independientemente de su tipo, comparten una serie de
 * atributos:
 * <ul>
 * <li>Identificador numérico unico (generado en la creación)
 * <li>Titulo
 * <li>Importe que se solicita y el que se obtiene (si es aprobado)
 * <li>Fecha de creacion
 * <li>Si el proyecto está cadudado o no
 * <li>El creador del proyecto (puede ser un ciudadano o un colectivo)
 * <li>Los ciudadadanos subscritos
 * <li>El estado del proyecto (Propuesto, Enviado, Aceptado...)
 * </ul>
 * <p>
 * A continuación explicamos algunos de los puntos tratados anterioremente
 * <p>
 * Un proyecto se considerará caducado al pasar más de
 * {@value DIAS_PARA_CADUCAR} dias desde que recibio el último apoyo. <br>
 * Si un proyecto está caducado, este no puede recibir más apoyos. Todas las
 * demás acciones sí pueden realizarse.
 * <p>
 * Las subscripciones a un proyecto implican la notificación al usuario
 * subscrito de los cambios de estado del proyecto <br>
 * Esta notificación se hará mediante la adición de una notificación a la lista
 * de notificaciones del usuario
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 23-03-20
 *
 */
public abstract class Proyecto implements Comparable<Proyecto>, Serializable {

	private static final long serialVersionUID = 7544501576126796758L;

	// Id que se asignará al siguiente proyecto que se cree
	private static int id_siguiente = 1;

	// Máxima longitud del título
	private static final int MAX_TITLE_LEN = 50;
	// Máxima longitud de la descripción
	private static final int MAX_DESCR_LEN = 500;
	// Dias que tienen que pasar antes de que el proyecto caduque
	private static final int DIAS_PARA_CADUCAR = 30;

	private int id;
	private String titulo;
	private String descripcion;
	private double importePedido;
	private Double importeAdjudicado;
	private LocalDate creacion;
	private LocalDate ultimoApoyo;
	private String idEnvio;
	private boolean caducado;
	private ElementoColectivo creador;
	private Set<ElementoColectivo> apoyos;
	private Set<Ciudadano> subscritos;
	private EstadoProyecto estado;

	/**
	 * Define el estado en el que se encuentra el proyecto. Cabe destacar que no se
	 * considera un estado la condición de caducado
	 * 
	 *
	 */
	public enum EstadoProyecto {
		/**
		 * El proyecto ha sido propuesto por un usuario pero aún no ha sido aprobado por
		 * el administrador
		 */
		PROPUESTO("propuesto"),
		/**
		 * El proyecto ha sido aprobado por el administrador. En este estado puede
		 * recibir apoyos siempre y cuando no esté caducado
		 */
		EN_VOTACION("en votacion"),
		/**
		 * El proyecto ha sido enviado al sistema de financiación. Todos los votos que
		 * reciba a partir de este momento serán aceptados pero no tendrán importancia a
		 * nivel de la adjudicación de fondos
		 */
		ENVIADO("enviado"),
		/**
		 * El proyecto ha sido aceptado por el sistema externo
		 */
		ACEPTADO("aceptado"),
		/**
		 * El proyecto ha sido rechazado por el sistema externo
		 */
		RECHAZADO("rechazado");

		private String txt;

		private EstadoProyecto(String s) {
			txt = s;
		}

		public String toString() {
			return txt;
		}
	}

	/**
	 * Crea un nuevo proyecto con los valores dados
	 * 
	 * @param titulo        Titulo del proyecto (máximo 25 caracteres)
	 * @param descripcion   Descripción del proyecto (máximo 500 caracteres)
	 * @param importePedido Importe que se solicitará al sistema externo cuando se
	 *                      envie el proyecto
	 * @param creador       Entidad creadora del proyecto
	 * 
	 * @throws IllegalArgumentException Si la longitud del titulo es demasiado larga
	 * @throws IllegalArgumentException Si la longitud de la descripción es
	 *                                  demasiado larga
	 */
	public Proyecto(String titulo, String descripcion, double importePedido, ElementoColectivo creador) {

		LocalDate date = LocalDate.now();

		if (titulo.length() > MAX_TITLE_LEN) {
			throw new IllegalArgumentException("Se ha excedido la longitud máxima del titulo");
		}
		if (descripcion.length() > MAX_DESCR_LEN) {
			throw new IllegalArgumentException("Se ha excedido la longitud máxima de la descripción");
		}

		this.titulo = titulo;
		this.descripcion = descripcion;
		this.importePedido = importePedido;
		this.importeAdjudicado = null;
		this.caducado = false;
		this.creacion = date;
		this.ultimoApoyo = date;
		this.id = id_siguiente;
		this.apoyos = new HashSet<>();
		this.subscritos = new HashSet<>();
		this.estado = EstadoProyecto.PROPUESTO;

		id_siguiente++;
		apoyos.add(creador);
		addSubscrito(creador.getRepresentante());
		this.creador = creador;

		creador.proyPropuesto(this); // Para que el creador registre que ha creado este proyecto
	}

	/**
	 * 
	 * Envia el proyecto al sistema de financiación externo.
	 * <p>
	 * <b>Cabe destacar que su uso externo se desaconseja. Se prefiere el uso de
	 * <code> Aplicacion.enviar() </code> </b>
	 * <p>
	 * El proyecto solo será enviado si se encuentra en el estado de EN_VOTACION
	 * 
	 * @return <code> true  </code> si el proyecto se ha podido enviar (estaba en
	 *         votación)<br>
	 *         <code> false </code> si el proyecto no se ha podido enviar (no estaba
	 *         en votación)
	 * 
	 * @throws IOException             Si no se ha podido comunicar con el sistema
	 *                                 externo
	 * @throws InvalidRequestException Si el sistema externo no acepta la petición
	 */
	public boolean enviar() throws IOException, InvalidRequestException {
		if (this.getEstado() != EstadoProyecto.EN_VOTACION)
			return false;

		GrantRequest req = new SolicitudProyecto(this);
		this.idEnvio = CCGG.getGateway().submitRequest(req);
		this.setEstado(EstadoProyecto.ENVIADO);

		return true;
	}

	/**
	 * Consulta el estado del proyecto al sistema de financiacón externo.
	 * <p>
	 * <b>Cabe destacar que su uso externo se desaconseja. Se prefiere el uso de
	 * <code> Aplicacion.consultar() </code> </b>
	 * <p>
	 * La consulta solo se realiza si el proyecto está en el estado de
	 * <code> ENVIADO </code>
	 * 
	 * @return Si el proyecto ya ha sido enviado, el importe adjudicado o
	 *         <code> null </code> si el sistema externo aún no lo ha evaluado. <br>
	 *         Si el proyecto no ha sido enviado, <code> null </code>
	 * 
	 * @throws IOException        Si no se ha podido comunicar con el sistema
	 *                            externo
	 * @throws InvalidIDException Si el sistema externo no tiene constancia del
	 *                            Proyecto
	 */
	public Double consultar() throws IOException, InvalidIDException {
		if (estado == EstadoProyecto.ENVIADO) {
			this.importeAdjudicado = CCGG.getGateway().getAmountGranted(idEnvio);

			if (importeAdjudicado != null) {
				if (importeAdjudicado > 0)
					this.setEstado(EstadoProyecto.ACEPTADO);
				else
					this.setEstado(EstadoProyecto.RECHAZADO);
			}

			return importeAdjudicado;
		} else if (estado == EstadoProyecto.ACEPTADO) {
			return this.importeAdjudicado;
		} else if (estado == EstadoProyecto.RECHAZADO) {
			return new Double(0);
		} else
			return null;

	}

	/**
	 * Añade los apoyos al proyecto
	 * <p>
	 * No se añadirán apoyos repetidos. Sin embargo un ciudadano puede apoyar
	 * simultaneamente de forma directa y a través de un colectivo. <br>
	 * En todo caso, a efectos de cómputo de apoyos, cada ciudadano solo se contará
	 * una vez
	 * 
	 * @see Proyecto#isApoyable()
	 * 
	 * @param e ElementoColectivo cuyos ciudadanos pasan a apoyar al proyecto
	 * @return <code> true </code> si se han podido añadir los apoyos <br>
	 *         <code> false </code> si no se han podido añadir
	 */
	public boolean addApoyos(ElementoColectivo e) {
		if (!isApoyable())
			return false;
		this.ultimoApoyo = LocalDate.now();

		this.apoyos.add(e);
		return true;
	}

	/**
	 * Devuelve si el proyecto se puede apoyar
	 * <p>
	 * Los estados que hacen imposible apoyar un proyecto son:
	 * <ul>
	 * <li>Proyecto caducado
	 * <li>Proyecto no aceptado por el administrador todavía
	 * <li>Proyecto ya aceptado por administración
	 * </ul>
	 * 
	 * @return Si el proyecto se puede apoyar
	 */
	public boolean isApoyable() {
		if (this.caducado)
			return false;
		if (estado == EstadoProyecto.PROPUESTO || estado == EstadoProyecto.ACEPTADO
				|| estado == EstadoProyecto.RECHAZADO)
			return false;

		return true;
	}

	/**
	 * Añade un subscriptor al proyecto
	 * <p>
	 * Si ya estaba subscrito, la llamada al método no tiene efecto
	 * 
	 * @param c Ciudadano que se subscribe al proyecto
	 * 
	 * @return <code> true </code> si se han podido añadir la subscripción <br>
	 *         <code> false </code> si no se han podido añadir
	 */
	public boolean addSubscrito(Ciudadano c) {
		if (estado == EstadoProyecto.PROPUESTO || estado == EstadoProyecto.ACEPTADO
				|| estado == EstadoProyecto.RECHAZADO)
			return false;

		this.subscritos.add(c);
		return true;
	}

	/**
	 * Actualiza el valor del campo caducado de este proyecto
	 * <p>
	 * Compara la fecha actual con la del último apoyo. <br>
	 * Si la diferencia es superior a los dias establecidos
	 * ({@value #DIAS_PARA_CADUCAR}), <br>
	 * la variable caducado tomará el valor <code> true </code>
	 */
	public void actualizarCaducado() {
		if (caducado)
			return;

		caducado = LocalDate.now().isAfter(ultimoApoyo.plusDays(DIAS_PARA_CADUCAR));
	}

	/**
	 * Devuelve el titulo del proyecto
	 * 
	 * @return El título
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Devuelve la descripción del proyecto
	 * 
	 * @return La descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Devuelve el importe que se pide al sistema externo al enviar el proyecto
	 * 
	 * @return El importe pedido
	 */
	public double getImportePedido() {
		return importePedido;
	}

	/**
	 * Devuelve el importe adjudicado por el sistema de financiación externo para el
	 * proyecto
	 * 
	 * @return El importe adjudicado si el proyecto ha sido evaluado por el sistema
	 *         externo <br>
	 *         <code> null </code> si el proyecto no ha recibido el veredicto del
	 *         sistema externo
	 */
	public Double getImporteAdjudicado() {
		return importeAdjudicado;
	}

	/**
	 * Devuelve la fecha de creación del proyecto
	 * 
	 * @return La fecha de creación
	 */
	public LocalDate getCreacion() {
		return creacion;
	}

	/**
	 * Devuelve la fecha en la que el proyecto recibió su apoyo más reciente
	 * 
	 * @return La fecha en la que el proyecto recibió su apoyo más reciente
	 */
	public LocalDate getUltimoApoyo() {
		return ultimoApoyo;
	}

	/**
	 * Devuelve el identificador numérico del proyecto
	 * 
	 * @return El identificador numérico
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve un set que contiene las entidades que apoyan el proyecto
	 * <p>
	 * Estas pueden ser tanto ciudadanos como colectivos
	 * 
	 * @return Apoyos del proyecto
	 */
	public Set<ElementoColectivo> getApoyos() {
		return apoyos;
	}

	/**
	 * Devuelve un set que contiene a los ciudadanos subscritos al proyecto
	 * 
	 * @return Subscriptores del proyecto
	 */
	public Set<Ciudadano> getSubscritos() {
		return subscritos;
	}

	/**
	 * Devuelve el estado del proyecto
	 * 
	 * @return El estado
	 */
	public EstadoProyecto getEstado() {
		return estado;
	}

	/**
	 * Devuelve el número de ciudadano que apoyan el proyecto
	 * <p>
	 * Los ciudadano pueden apoyar el proyecto directamente o a través de un
	 * colectivo. <br>
	 * En cualquier caso, solo se contarán una vez, aunque apoyen al proyecto por
	 * diversas vías.
	 * 
	 * @return El número de apoyos
	 */
	public int getNumApoyos() {
		Set<Ciudadano> apoyos = this.apoyos.parallelStream().flatMap(e -> e.getTodosCiudadanos().parallelStream())
				.collect(Collectors.toSet());
		return (int) apoyos.parallelStream().filter(e -> !e.isBloqueado()).count();

	}

	/**
	 * Devuelve la entidad que creó el proyecto
	 * 
	 * @return El creador del proyecto
	 */
	public ElementoColectivo getCreador() {
		return this.creador;
	}

	/**
	 * Devuelve si el proyecto está caducado
	 * 
	 * @return <code> true </code> si el proyecto está caducado <br>
	 *         <code> false </code> si el proyecto no está caducado
	 */
	public boolean isCaducado() {
		return this.caducado;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Establece la descripción del proyecto
	 * 
	 * @param descripcion Nuevo valor de la descripción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Establece el importe que se pedirá al enviar el proyecto
	 * 
	 * @param importePedido Nuevo valor del importe que se pedirá
	 */
	public void setImportePedido(double importePedido) {
		this.importePedido = importePedido;
	}

	/**
	 * Establece el importe que ha adjudicado el sistema externo
	 * 
	 * @param importeAdjudicado Nuevo valor del importe adjudicado
	 */
	public void setImporteAdjudicado(Double importeAdjudicado) {
		this.importeAdjudicado = importeAdjudicado;
	}

	/**
	 * Establece la fecha de creación del proyecto
	 * 
	 * @param creacion Nuevo valor de la fecha de creación
	 */
	public void setCreacion(LocalDate creacion) {
		this.creacion = creacion;
	}

	/**
	 * Establece la fecha de último apoyo del proyecto
	 * 
	 * @param ultimoApoyo Nuevo valor de la fecha de último apoyo
	 */
	public void setUltimoApoyo(LocalDate ultimoApoyo) {
		this.ultimoApoyo = ultimoApoyo;
	}

	/**
	 * Establece el identificador único del proyecto
	 * 
	 * @param id Nuevo valor del identificador
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Establece el estado del proyecto
	 * <p>
	 * Al cambiar el estado, se enviará una notificación a los ciudadanos subscritos
	 * 
	 * @param estado Nuevo valor del estado del proyecto
	 */
	public void setEstado(EstadoProyecto estado) {
		if (this.estado != estado) {
			this.subscritos.forEach(c -> c.addNotificacion(new NotificacionCambioEstado(this, estado.toString())));
		}
		this.estado = estado;
	}

	/**
	 * Establece si el proyecto está caducado
	 * 
	 * @param caducado Nuevo valor del campo caducado
	 */
	public void setCaducado(boolean caducado) {
		this.caducado = caducado;
	}

	/**
	 * Devuelve la representación como cadena de caracteres del proyecto
	 * <p>
	 * La representación consta de los valores de los campos del objeto. Las
	 * colecciones ocupan varias lineas, una por elemento y los demás atributos, una
	 * cada uno.
	 * 
	 * @return String que representa este proyecto
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id\t\t\t");
		builder.append(id);
		builder.append("\ntitulo\t\t\t");
		builder.append(titulo);
		builder.append("\ndescripcion\t\t");
		builder.append(descripcion);
		builder.append("\nimportePedido\t\t");
		builder.append(importePedido);
		builder.append("\nimporteAdjudicado\t");
		if(importeAdjudicado == null)builder.append("Sin determinar");
		else builder.append(String.format("%.2f", importeAdjudicado));
		builder.append("\ncreacion\t\t"); 
		builder.append(creacion);
		builder.append("\numeroApoyos\t\t");
		builder.append(this.getNumApoyos());
		builder.append("\nultimoApoyo\t\t");
		builder.append(ultimoApoyo);
		builder.append("\napoyos\n");
		apoyos.forEach(e -> builder.append("\t" + e.getName() + "\n"));
		builder.append("\nsubscritos\n");
		subscritos.forEach(e -> builder.append("\t" + e.getName() + "\n"));
		builder.append("\nestado\t\t\t");
		if (caducado == true && estado.equals(EstadoProyecto.EN_VOTACION) == true) builder.append("caducado");
		else if(caducado == true) builder.append(estado + ",  caducado");
		else builder.append(estado); 
		
		return builder.toString();
	}

	/**
	 * Compara este proyecto a otro
	 * <p>
	 * Los proyectos se comparan en función a su identificador numerico (por orden
	 * de creación)
	 * 
	 * @param p2 Proyecto a compararo
	 * 
	 * @return <code> {@literal < 0} </code> si p2 se creó después <br>
	 *         <code> = 0 </code> si son el mismo proyecto <br>
	 *         <code> {@literal > 0} </code> si p2 se creó antes
	 */
	@Override
	public int compareTo(Proyecto p2) {
		return id - p2.id;
	}

	/**
	 * Comprueba si dos proyectos son iguales
	 * <p>
	 * Dos proyectos son iguales si tienen el mismo identificador
	 * 
	 * @return <code> true </code> si son iguales (mismo id) <br> <code> false </code> si no lo son
	 */
	@Override
	public boolean equals(Object o){
		if(o==this){
			return true;
		}
		if(!this.getClass().equals(o.getClass())){
			return true;
		}
		return id == ((Proyecto)o).id;

	}
	/**
	 * Devuelve el identificador que se asignará al siguiente proyecto que se cree
	 * 
	 * @return El siguiente id
	 */
	public static int getId_siguiente() {
		return id_siguiente;
	}

	/**
	 * Establece el identificador que se asignará al siguiente proyecto que se cree
	 * <p>
	 * A partir del próximo proyecto, los identificadores se asigarán de forma
	 * correlativa a partir de este nuevo valor
	 * 
	 * @param Id_siguiente El identificador para el próximo proyecto
	 */
	public static void setId_siguiente(int Id_siguiente) {
		Proyecto.id_siguiente = Id_siguiente;
	}

}

/**
 * Esta clase es una implementación de la interfaz necesaria para poder realizar
 * los envios al sistema externo de financiación.
 * 
 * Contiene los métodos definidos en la interfaz, en cuya documentación se puede
 * consultar su funcionamiento
 *
 */
class SolicitudProyecto implements GrantRequest {
	Proyecto p;

	public SolicitudProyecto(Proyecto p) {
		this.p = p;
	}

	@Override
	public String getExtraData() {
		return p.toString();
	}

	@Override
	public String getProjectDescription() {
		return p.getDescripcion();
	}

	@Override
	public ProjectKind getProjectKind() {
		if (p instanceof ProySocial)
			return GrantRequest.ProjectKind.Social;
		else
			return GrantRequest.ProjectKind.Infrastructure;
	}

	@Override
	public String getProjectTitle() {
		return p.getTitulo();
	}

	@Override
	public double getRequestedAmount() {
		return p.getImportePedido();
	}

}
