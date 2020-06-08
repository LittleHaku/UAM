package demostracionMain;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.*;

import aplicacion.*;
import aplicacion.Usuario;
import colectivoCiudadano.Ciudadano;
import colectivoCiudadano.Colectivo;
import colectivoCiudadano.ElementoColectivo;
import es.uam.eps.sadp.grants.CCGG;
import es.uam.eps.sadp.grants.InvalidIDException;
import fechaSimulada.FechaSimulada;
import notificacion.Notificacion;
import notificacion.NotificacionAdmision;
import notificacion.NotificacionCambioEstado;
import notificacion.NotificacionCreacionProyecto;
import proyecto.Distrito;
import proyecto.ProyInfraestructura;
import proyecto.ProySocial;
import proyecto.Proyecto;

/**
 * Clase Main, prueba la Aplicacion
 * 
 * @author David del Val, Junco de las Heras, Jorge Fernandez
 *
 */
public class Main {
	private static final String SEP = "------------------------------\n";
	private static Scanner s = null;

	/**
	 * Recibe un titulo y un array con las posibles opciones Imprime por pantalla
	 * las opciones disponibles, añadiendo "Salir" y devuelve la eleccion del
	 * usuario
	 * 
	 * @param title : titulo de la seleccion
	 * @param a     : opciones disponibles
	 * @return opt : eleccion del usuario
	 */
	public static int titlechoice(String title, String... a) {
		System.out.println("\n");
		System.out.println(title);
		for (int i = 0; i < a.length; ++i) {
			System.out.println((i + 1) + ": " + a[i]);
		}
		System.out.println((a.length + 1) + ": Salir");
		if (s == null)
			s = new Scanner(System.in);

		System.out.print("\nSeleccione la opcion: ");
		int opt = s.nextInt();
		s.nextLine();
		return opt;
	}

	/**
	 * Recibe un array con las opciones y lo pasa como argumento a la funcion
	 * titlechoice
	 *
	 * @param a : Opciones disponibles
	 * @return opt : eleccion del usuario
	 */
	public static int choice(String... a) {
		return titlechoice("Opciones disponibles:", a);
	}

	/**
	 * Recibe un titulo y un array con las posibles opciones Imprime por pantalla
	 * las opciones disponibles y devuelve la eleccion del usuario
	 * 
	 * @param title : titulo de la seleccion
	 * @param a     : opciones disponibles
	 * @return opt : eleccion del usuario
	 */
	public static int titlechoice_noSalir(String title, String... a) {
		System.out.println("\n");
		System.out.println(title);
		for (int i = 0; i < a.length; ++i) {
			System.out.println((i + 1) + ": " + a[i]);
		}
		if (s == null)
			s = new Scanner(System.in);

		System.out.print("\nSeleccione la opcion: ");
		int opt = s.nextInt();
		s.nextLine();
		return opt;
	}

	/**
	 * Recibe un String con una pregunta que espera como respuesta si o no
	 * 
	 * @param question : pregunta
	 * @return boolean : true si la respuesta fue si, false si fue no
	 */
	public static boolean ynchoice(String question) {
		System.out.println(question);
		while (true) {
			String str = s.nextLine();
			if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("si")
					|| str.equalsIgnoreCase("s")) {
				return true;
			}
			if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")) {
				return false;
			}
			System.out.println("Conteste con y/n");
		}
	}

	/**
	 * En esta funcion el usuario elige uno de los colectivos que representa
	 * 
	 * @param c : ciudadano
	 * @return Colectivo elegido entre los que representa el ciudadano
	 */
	public static Colectivo seleccionarColectivoRepresentado(Ciudadano c) {
		List<Colectivo> representa = new ArrayList<>();

		c.getRepresenta().forEach(representa::add);

		if (representa.size() == 0) {
			System.out.println("El ciudadano activo no es representante de ningun colectivo");
			return null;
		}
		String[] nombres = representa.stream().map(e -> e.getName()).toArray(String[]::new);

		int ch = choice(nombres);
		if (ch == representa.size() + 1)
			return null;

		return representa.get(ch - 1);
	}

	/**
	 * Ventana que se abre cuando el ciudadano quiere proponer un proyecto
	 * 
	 * @param a : Aplicacion
	 * @param c : Ciudadano que quiere proponer el proyecto
	 */
	public static void ventanaProponerProyecto(Aplicacion a, Ciudadano c) {
		Proyecto p;

		/***** Elegimos la entidad que va a proponer el proyecto *****/
		int option = choice("Proponer proyecto como individuo",
				"Proponer proyecto como colectivo (es necesario ser representante)");

		if (option == 3) {
			return;
		}

		ElementoColectivo obj = null;
		switch (option) {
		case 1: {
			obj = c;
			break;
		}

		case 2: {
			obj = seleccionarColectivoRepresentado(c);
		}
		}

		if (obj == null)
			return;

		/***** Creamos el nuevo proyecto *****/
		System.out.println("Introduce un titulo: ");
		String titulo = s.nextLine();
		while (titulo.length() > 25) {
			System.out.println("Titulo demasiado largo (maximo de 25 caracteres), vuelve a introducirlo: ");
			titulo = s.nextLine();
		}
		System.out.println("Introduce una descripcion: ");
		String desc = s.nextLine();
		while (desc.length() > 500) {
			System.out.println("Descripcion demasiado larga (maximo de 500 caracteres), vuelve a introducirla: ");
			desc = s.nextLine();
		}
		System.out.println("Introduce el importe solicitado: ");
		Double importe = s.nextDouble();
		s.nextLine(); // Consumir salto de linea

		int tipo = choice("Proyecto Social", "Infraestructura");

		switch (tipo) {
		case 1: { // Proyecto social

			System.out.println("Introduce el grupo: ");
			String grupo = s.nextLine();
			while (grupo.length() > 50) {
				System.out
						.println("Nombre del grupo demasiado largo (maximo de 50 caracteres), vuelve a introducirlo: ");
				grupo = s.nextLine();
			}
			int tiposocial = titlechoice_noSalir("Extension del proyecto:", "Nacional", "Internacional");

			boolean nacional = (tiposocial == 1);

			p = new ProySocial(titulo, desc, importe, obj, grupo, nacional);

			a.addProyectoParaAutorizar(p);
			System.out.println("El proyecto ha sido añadido y enviado al administrador para ser aprobado");
			break;
		}

		case 2: { // Proyecto de infraestructura

			System.out.println("Introduce la imagen (String): ");
			String imagen = s.nextLine();
			System.out.println(
					"Selecciona los distritos que se ven afetados (Todos en una linea separados por espacios): ");

			for (int i = 0; i < a.getDistritos().size(); ++i) {
				System.out.println(String.format("%-4d", i) + a.getDistritos().get(i).toString());
			}
			ArrayList<Distrito> distritos = new ArrayList<Distrito>();

			String[] dist = s.nextLine().split(" ");
			for (String s : dist) {
				distritos.add(a.getDistritos().get(Integer.parseInt(s)));
			}

			p = new ProyInfraestructura(titulo, desc, importe, obj, distritos, imagen);
			a.addProyectoParaAutorizar(p);
			System.out.println("El proyecto ha sido añadido y enviado al administrador para ser aprobado");
			break;
		}
		}

	}

	/**
	 * Ventana que se abre cuando el usuario quere explorar los poryectos existentes
	 * en la aplicacion Desde esta ventana se podrá apoyar o suscribirse a un
	 * proyecto
	 * 
	 * @param a : Aplicacion
	 * @param c : Ciudadano que explora los proyectos
	 */
	public static void ventanaExplorarProyectos(Aplicacion a, Ciudadano c) {
		Set<Proyecto> proyectos = a.getProyectosAprobados();

		System.out.println("Los proyectos existentes son: ");
		proyectos.forEach(e -> System.out.println(SEP + e + "\n" + SEP));

		int option = choice("Suscribirse a un proyecto", "Apoyar un proyecto");
		if (option == 3)
			return;

		System.out.println("\nIntroduce el nombre del proyecto: ");
		String nombre_p = s.nextLine();

		/***** Buscamos el proyecto *****/
		List<Proyecto> lisp = a.encontrarProyecto(nombre_p);
		Proyecto p;
		if (lisp.size() == 0) {
			System.out.println("No se ha encontrado el proyecto");
			return;
		} else if (lisp.size() == 1) {
			p = lisp.get(0);
		} else {
			int ch = titlechoice_noSalir("Elija el proyecto",
					lisp.stream().map(Proyecto::toString).toArray(String[]::new));
			p = lisp.get(ch - 1);
		}

		/***** Si hemos encontrado el proyecto, hacemos la accion elegida *****/
		// Subscribirse al proyecto
		if (option == 1) {
			if (p.addSubscrito(c)) {
				System.out.println("Se ha añadido la subscripción");
			}
			esperaEnter();
		}

		// Apoyar el proyecto
		else {
			int tipo_apoyo = choice("Individual", "Apoyar proyecto como colectivo (es necesario ser representante");
			if (tipo_apoyo == 3)
				return;

			switch (tipo_apoyo) {
			case 1: {
				p.addApoyos(c);
				break;
			}

			case 2: {
				Colectivo obj = seleccionarColectivoRepresentado(c);
				if (obj != null)
					p.addApoyos(obj);
			}
			}
			System.out.println("El proyecto ha sido apoyado");
			esperaEnter();
		}

	}

	public static void esperaEnter() {
		System.out.println("Presione enter para acceder al menu principal");
		s.nextLine();
	}

	/**
	 * Ventana donde se listan las opciones que puede realizar un usuario. Algunas
	 * opciones se implementan directamente en este metodo, mientras que otras
	 * llaman a una funcion auxiliar
	 * 
	 * 
	 * @param a : Aplicacion
	 */
	public static void ventanaUsuario(Aplicacion a) {

		while (true) {
			System.out.println("\n\n\n\n\n\n\n\n");
			Usuario u = a.getUsrActivo();
			Ciudadano c = (Ciudadano) u; // c es el ciudadano activo
			if (c == null)
				return;
			List<Notificacion> l = c.getNotificaciones();

			System.out.println("Hola " + c.getName() + ", tiene " + l.size() + " notificaciones:");

			if (l.size() != 0) {

				System.out.println("No leidas:");
				l.stream().filter(e -> !e.isVisto()).forEachOrdered(System.out::println);

				System.out.println("Leidas:");
				l.stream().filter(e -> e.isVisto()).forEachOrdered(System.out::println);

				l.forEach(e -> e.setVisto(true));
			}
			int opt = choice("Administrar notificaciones", "Visualizar lista de colectivos a los que perteneces",
					"Crear un nuevo colectivo", "Visualizar lista de colectivos", "Apuntarse a colectivo existente",
					"Desapuntarse de un colectivo", "Explorar los proyectos que has propuesto", "Proponer proyecto",
					"Explorar proyectos");

			if (opt == 10)
				return;

			switch (opt) {

			case 1: { // Administrar notificaciones
				while (true) {
					String[] not = l.stream().map(e -> e.toString()).toArray(String[]::new);
					int opt2 = titlechoice("Elija la notificacion ", not);
					if (opt2 == not.length + 1)
						break;

					List<String> opciones = new ArrayList<>();
					opciones.add("Eliminar notificacion");
					if (l.get(opt2 - 1) instanceof NotificacionCambioEstado)
						opciones.add("Ver el proyecto asociado");

					int opt3 = choice(opciones.toArray(new String[opciones.size()]));

					if (opt3 == opciones.size() + 1)
						break;

					switch (opciones.get(opt2 - 1)) {

					case "Eliminar notificacion": {
						l.remove(opt2 - 1);
						break;
					}
					case "Ver el proyecto asociado": {
						System.out.println(((NotificacionCambioEstado) l.get(opt2 - 1)).getProy());

						break;
					}
					}
				}
				break;

			}

			case 2: { // Visualizar lista de colectivos a los que pertences
				Set<Colectivo> per = c.getPertenece();
				System.out.println("El ciudadano " + c.getUsr() + " pertence a " + per.size() + " colectivos");

				per.forEach(e -> System.out.println(e.getName()));

				int informe = choice("Consultar Informe de Afinidad entre dos Proyectos");
				if (informe == 2)
					break;
				else {
					System.out.print("Introduzca el Colectivo 1: ");
					String n_col = s.nextLine();

					Colectivo c1 = a.encontrarColectivo(n_col);
					if (c1 == null) {
						System.out.println("El colectivo no se ha encontrado");
						break;
					}

					System.out.print("Introduzca el Colectivo 2: ");
					n_col = s.nextLine();

					Colectivo c2 = a.encontrarColectivo(n_col);
					if (c2 == null) {
						System.out.println("El colectivo no se ha encontrado");
						break;
					}

					System.out.println(
							"La afinidad entre los colectivos " + c1.getName() + " y " + c2.getName() + " es:");
					System.out.println(a.getInformeAfinidad(c1, c2));
					esperaEnter();
				}

				break;
			}

			case 3: { // Crear un nuevo colectivo
				System.out.print("Escriba el nombre del colectivo a crear: ");
				String name = s.nextLine();
				boolean sub = ynchoice(
						"¿Quiere que este colectivo sea subcolectivo de uno de los colectivos a los que usted representa?");
				Colectivo col = null;

				if (sub) {
					col = seleccionarColectivoRepresentado(c);
					if (col == null)
						break;
				}

				Colectivo nuevo;
				if (sub) {
					nuevo = new Colectivo(c, name);
				} else {
					nuevo = new Colectivo(c, name);
				}
				if (a.addColectivo(nuevo)) {
					if (sub)
						col.add(nuevo);
					System.out.println("El colectivo ha sido creado");
				}

				else {
					System.out.println("No se ha podido crear el colectivo porque este ya existe");
					c.removed(nuevo);
				}
				esperaEnter();

				break;
			}

			case 4: { // Visualizar lista de colectivos
				Set<Colectivo> mc = a.getColectivos();
				System.out.println("Lista de colectivos totales (" + mc.size() + ") en total:");
				mc.forEach(e -> System.out.println(SEP + e + "\n" + SEP));

				esperaEnter();
				break;
			}

			case 5: { // Apuntarse a un colectivo existente
				System.out.print("Apuntarse al colectivo: ");
				String name = s.nextLine();
				Colectivo col = a.encontrarColectivo(name);
				if (col == null) {
					System.out.println("El colectivo no se ha encontrado");
				} else {
					if (col.add(c)) {
						System.out.println(
								"El ciudadano " + c.getUsr() + " ha sido añadido" + " al colectivo " + col.getName());
					} else {
						System.out.println("El ciudadano " + c.getUsr() + " NO ha podido ser añadido" + " al colectivo "
								+ col.getName());
					}

				}
				esperaEnter();
				break;
			}
			case 6: { // Borrarse de un colectivo
				System.out.print("Borrarse del colectivo: ");
				String name = s.nextLine();
				Colectivo col = a.encontrarColectivo(name);
				if (col == null) {
					System.out.println("El colectivo no se ha encontrado");
				} else {
					if (col.remove(c)) {
						System.out.println("El ciudadano " + c.getUsr() + " ha sido borrado con exito del colectivo "
								+ col.getName());
					} else {
						System.out.println("El ciudadano " + c.getUsr()
								+ " no se ha podido borrar del colectivo ya que es el representante");
					}

				}
				esperaEnter();
				break;
			}
			case 7: { // Explorar proyectos que has propuesto

				while (true) {

					List<Proyecto> pr = new ArrayList<Proyecto>();

					c.getTodosProyPropuestos().forEach(pr::add);

					String[] nombres = pr.stream().map(Proyecto::getTitulo).toArray(String[]::new);

					int choice = titlechoice("Elija el proyecto", nombres);

					if (choice == pr.size() + 1)
						break;

					Proyecto p = pr.get(choice - 1);

					ArrayList<String> acciones = new ArrayList<>();
					acciones.add("Consultar informe de popularidad");
					if (a.isProyectoEnviable(p)) {
						acciones.add("Enviar");
					}
					if (p.getEstado() == Proyecto.EstadoProyecto.ENVIADO
							|| p.getEstado() == Proyecto.EstadoProyecto.ACEPTADO) {
						acciones.add("Consultar");
					}

					int option = choice(acciones.toArray(new String[acciones.size()]));
					if (option == acciones.size() + 1)
						break;
					String accion = acciones.get(option - 1);

					switch (accion) {
					case "Enviar": {
						try {
							if (a.enviar(p)) {
								System.out.println("El proyecto se ha enviado correctamente");
							} else {
								System.out.println("El proyecto no cumple los requisitos para ser enviado");
							}
						} catch (Exception e) {
							System.out.println("No se ha podido enviar el proyecto " + e.getMessage());
						}
						break;
					}
					case "Consultar informe de popularidad": {
						System.out.println("El proyecto tiene " + p.getNumApoyos() + " apoyos");

						break;
					}
					case "Consultar": {
						Double d = null;

						try {
							d = a.consultar(p);// Consulta pero a través de aplicación
							if (d != null) {
								if (d == 0) {
									System.out.println("El proyecto ha sido rechazado");
								} else {
									System.out.println("El proyecto ha sido adjudicado con " + String.format("%.2f", d) + " euros");
								}

							} else {
								System.out.println("No se han podido obtener los datos");
							}
						} catch (IOException e) {
							System.out.println("No se ha podido establecer comunicación con el sistema externo: "
									+ e.getMessage());
						} catch (InvalidIDException e1) {
							System.out.println(
									"Error al solicitar la información al sistema externo: " + e1.getMessage());
						}

						break;
					}
					}

					choice = titlechoice("Elija el proyecto", nombres);
				}
				break;

			}

			case 8: { // Proponer proyecto
				ventanaProponerProyecto(a, c);
				break;
			}

			case 9: { // Explorar proyectos
				ventanaExplorarProyectos(a, c);
				break;
			}
			}
		}
	}

	/**
	 * Ventana donde se muestran las opciones disponibles de un administrador A esta
	 * ventana solo se puede aceder si se es administrador y parmite: -Configurar
	 * numero minimo de apoyos -Resolver notificaciones -Bloquear / Desbloquear
	 * ciudadanos -Consultar lista de ciudadanos
	 * 
	 * @param a : Aplicacion
	 */
	public static void ventanaAdministrador(Aplicacion a) {
		Administrador adm = (Administrador) a.getUsrActivo();
		while (true) {
			if (adm.getNotificaciones().size() != 0)
				System.out.println("Tiene " + adm.getNotificaciones().size() + " notificaciones sin resolver");

			int opt = choice("Configurar numero minimio de apoyos", "Resolver notificaciones",
					"Bloquear / Desbloquear ciudadanos", "Consultar lista de ciudadanos");
			if (opt == 5)
				return;

			switch (opt) {
			case 1: { // Configurar numero minimo de apoyos
				System.out.println("Actualmente el numero minimo de apoyos es " + Aplicacion.getApoyosMinimos());
				System.out.print("Introduzca el nuevo valor minimo de apoyos: ");
				Aplicacion.setApoyosMinimos(s.nextInt());
				s.nextLine();
				break;
			}
			case 2: { // Resolver notificaciones
				while (true) {
					List<NotificacionAdmision> lis = adm.getNotificaciones();
					String[] options = lis.stream().map(e -> e.toString()).toArray(String[]::new);
					int opt2 = titlechoice("Elija la notificacion que quiere evaluar:", options);

					if (opt2 == lis.size() + 1)
						break;

					int opt3 = choice("Aceptar", "Rechazar");
					if (opt3 == 3)
						break;

					if (opt3 == 1) {
						lis.get(opt2 - 1).aceptar();
					} else {
						if(lis.get(opt2-1) instanceof NotificacionCreacionProyecto) {
							System.out.print("Introduzca un motivo para el rechazo: ");
							String mot = s.nextLine();
							lis.get(opt2 - 1).rechazar(a, mot);
						}
						else {
							lis.get(opt2 - 1).rechazar(a, "");
						}
						
					}

					System.out.println("El proceso se ha completado satisfactoriamente");

					lis.remove(opt2 - 1);

				}

				break;
			}
			case 3: { // Bloquear y desbloquear ciudadanos
				System.out.println("Introduzca el nombre del ciudadano: ");
				String name = s.nextLine();
				Ciudadano c = a.encontrarCiudadano(name);
				if (c == null) {
					System.out.println("No se ha podido encontrar al ciudadano");
					esperaEnter();
				} else if (c.isRegistroAprobado() == false) {
					System.out.println("El registro de " + c.getName()
							+ " no ha sido aprobado todavía, por lo que no se puede bloquear");
					esperaEnter();
				} else {
					String status = "El ciudadano seleccionado se encuentra ";
					status += (c.isBloqueado()) ? "bloqueado" : "desbloqueado";
					System.out.println(status);
					boolean op = ynchoice("¿Desea cambiar su estado?");
					if (op == true) {
						c.setBloqueado(!c.isBloqueado());

						status = "El ciudadano seleccionado se encuentra ahora ";
						status += (c.isBloqueado()) ? "bloqueado" : "desbloqueado";
						System.out.println(status);
						esperaEnter();
					}
				}

				break;
			}
			case 4: { // Consultar lista de ciudadanos registrados
				Set<Usuario> s = a.getTodosUsuarios();
				long siz = s.parallelStream().filter(e -> e instanceof Ciudadano).count();
				System.out.println("Hay " + siz + " ciudadanos registrados:");
				s.stream().filter(e -> e instanceof Ciudadano)
						.forEachOrdered(e -> System.out.println("\n" + SEP + e + "\n" + SEP));
				esperaEnter();
				break;
			}
			}

		}

	}

	/**
	 * Ventana inicial que se abre al iniciar la aplicacion Permite: -Iniciar Sesion
	 * -Registrarse -Guardar los datos
	 * 
	 * @param a : Aplicacion
	 */
	public static void ventanaInicial(Aplicacion a) {
		while (true) {
			int opt = choice("Iniciar sesion", "Registrarse", "Guardar", "Cargar datos guardados");

			if (opt == 5)
				return;
			// Pedir usuario y contraseña es comun para ambos casos

			switch (opt) {
			case 1: {
				System.out.print("Usuario: ");
				String usr = s.nextLine();
				System.out.print("Contraseña: ");
				String pwd = s.nextLine();
				Usuario iniciado = a.iniciarSesion(usr, pwd);

				if (iniciado == null) {
					System.out.println("No se ha podido iniciar sesion");
				} else if (a.ModoAdministrador()) {
					System.out.println("Se ha iniciado sesion como administrador");
					ventanaAdministrador(a);
				} else {
					Ciudadano c = (Ciudadano) iniciado;
					if (c.isRegistroAprobado()) {
						System.out.println("Se ha iniciado sesion como " + c.getUsr());
						ventanaUsuario(a);

					} else {
						System.out.println("Su solicitud de registro todavía no ha sido aprobada por el administrador");
					}
				}
				a.cerrarSesion();
				break;
			}
			case 2: {
				System.out.print("Usuario: ");
				String usr = s.nextLine();
				System.out.print("Contraseña: ");
				String pwd = s.nextLine();
				System.out.print("NIF: ");
				String nif = s.nextLine();
				if (a.registro(usr, pwd, nif)) {
					System.out.println("Se ha registrado correctamente");

				} else {
					System.out.println("No se ha podido registrar, intentelo de nuevo");
				}
				break;
			}
			case 3: {
				System.out.print("Introduzca el nombre del archivo que desea escribir:");
				String rt=s.nextLine();
				try {
					FileOutputStream f = new FileOutputStream(rt);
					ObjectOutputStream out = new ObjectOutputStream(f);
					out.writeObject(a);
					out.close();
					f.close();
					System.out.println("Datos guardados");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case 4:{
				System.out.print("Introduzca el nombre del archivo que desea cargar:");
				String rt=s.nextLine();
				if (Files.exists(Paths.get(rt), LinkOption.NOFOLLOW_LINKS)) {
					try {
						FileInputStream f = new FileInputStream(rt);
						ObjectInputStream in = new ObjectInputStream(f);
						a = (Aplicacion) in.readObject();
						in.close();
						f.close();
						System.out.println("Datos cargados\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("El archivo especificado no existe");
				}
			}
			}
		}

	}

	/**
	 * Funcion main donde se crea la aplicación, se implementa es listema externo y
	 * se cargan los datos guardados en el fichero save.save si este existe
	 * 
	 * Al salir, se guardan los datos en el el archivo save.save aunque también se puede guardar
	 * directamente en el menu.
	 * 
	 * @param args argumentos de entrada
	 */
	public static void main(String[] args) {
		Aplicacion a = new Aplicacion();
		FechaSimulada.restablecerHoyReal();
		CCGG.getGateway().setDate(FechaSimulada.getHoy());

		if (Files.exists(Paths.get("save.save"), LinkOption.NOFOLLOW_LINKS)) {
			try {
				FileInputStream f = new FileInputStream("save.save");
				ObjectInputStream in = new ObjectInputStream(f);
				a = (Aplicacion) in.readObject();
				in.close();
				f.close();
				System.out.println("Datos cargados\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Main.ventanaInicial(a);
		
		s.close();
		try {
			FileOutputStream f = new FileOutputStream("save.save");
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(a);
			out.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
