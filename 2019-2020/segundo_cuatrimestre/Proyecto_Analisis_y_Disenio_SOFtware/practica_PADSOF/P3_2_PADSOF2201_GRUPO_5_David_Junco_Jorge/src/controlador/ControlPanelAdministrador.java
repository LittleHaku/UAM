package controlador;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import componentes.BiOptionIconDialog;
import componentes.IconDialog;
import componentes.InsertValueDialog;
import exceptions.VisualException;
import modelo.aplicacion.Administrador;
import modelo.aplicacion.Aplicacion;
import modelo.aplicacion.Usuario;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.notificacion.Notificacion;
import modelo.notificacion.NotificacionAdmision;
import modelo.notificacion.NotificacionCreacionProyecto;
import modelo.notificacion.NotificacionRegistro;
import modelo.proyecto.Proyecto;
import vista.Ventana;
import vista.VistaPanelAdministrador;

/**
 * Controlador de la vista {@link VistaPanelAdministrador}.
 * <p>
 * Este controlador gestiona los eventos de acción y de selección de lista que
 * se generen en su vista
 * 
 * @see VistaPanelAdministrador
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlPanelAdministrador extends Controlador {
    /* Vista del controlador */
    private final VistaPanelAdministrador vista;

    /**
     * Constructor del ControlPanelAdministrador
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlPanelAdministrador(Ventana frame, Aplicacion modelo) {
	super("panelAdministrador", frame, modelo);

	this.vista = (VistaPanelAdministrador) frame.getVista(this.getId());
    }

    /**
     * Manejador de los botones.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().startsWith("N_")) {
	    actionNotificacion(e);
	    return;
	}

	switch (e.getActionCommand()) {

	    case "cambiarNVotos": {
		int nv = Aplicacion.getApoyosMinimos();
		String titol = "Apoyos mínimos para enviar un proyecto";
		String desc = "Actualmente los apoyos mínimos requeridos para que se pueda enviar un proyecto "
			+ (nv > 1 ? "son " : "es ") + nv
			+ ". Al hacer click en el botón se actualizarán al valor dado.";

		Consumer<String> exitAction = input -> {
		    try {
			int nval = Integer.parseInt(input);
			Aplicacion.setApoyosMinimos(nval);
		    } catch (NumberFormatException ex) {
			IconDialog.errorDialog(frame, "Valor incorrecto", "El valor introducido no es un número");
		    }
		};

		InsertValueDialog ivd = new InsertValueDialog(frame, titol, desc, exitAction);
		ivd.setTextFieldText(Integer.toString(nv));
		ivd.setVisible(true);

		break;
	    }
	    case "cambiarEstado": {
		String nombreUsuario = vista.getNameUsuarioSeleccionado();
		if (nombreUsuario != null) {
		    Ciudadano c = modelo.encontrarCiudadano(nombreUsuario);
		    if (c == null) {
			IconDialog.errorDialog(frame, "Error interno", "Usuario no encontrado");
		    } else {
			c.setBloqueado(!c.isBloqueado());
			modelo.actualizarEnviables();
			setCiudadanos();
		    }
		}
		break;
	    }
	    case "guardar": {
		JFileChooser jfc = new JFileChooser();
		if (jfc.showSaveDialog(frame) == JFileChooser.CANCEL_OPTION) return;

		try {
		    Aplicacion.saveToFile(jfc.getSelectedFile());
		    IconDialog.successDialog(frame, "Datos guardados",
			    "Se han guardado los datos en " + jfc.getSelectedFile() + ".");

		} catch (Exception ex) {
		    new VisualException(ex).showDialog(frame);
		}

		break;
	    }
	    case "cargar": {
		String desc = "Al importar datos de un archivo externo se sobrescribirán los datos guardados en la aplicación actualmente.\nSe recomienda exporar los datos actuales antes de llevar a cabo esta acción.\nEsta operación no se puede deshacer";
		BiOptionIconDialog.warningDialog(frame, "¿Desea importar datos desde un archivo?", desc,
			this::actionImport);

		break;
	    }
	    case "reset": {
		String desc = "¿Está seguro de que desa restablecer los datos? Esta operación no se puede deshacer";
		BiOptionIconDialog.warningDialog(frame, "¿Desea restablecer los datos?", desc, ev -> {
		    Aplicacion.reset();
		    this.initialise();
		});

		break;
	    }
	    case "exit": {
		frame.showVista("inicial");
		break;
	    }
	    default: {
		System.out.println(e.getActionCommand());
	    }

	}
    }

    /**
     * Código de importar datos a la aplicación
     * 
     * @param e Evento de acción
     */
    private void actionImport(ActionEvent e) {
	JFileChooser jfc = new JFileChooser();
	if (jfc.showSaveDialog(frame) == JFileChooser.CANCEL_OPTION) return;

	try {
	    Aplicacion.loadFromFile(jfc.getSelectedFile());
	    IconDialog.successDialog(frame, "Datos leídos.",
		    "Se han cargado los datos de " + jfc.getSelectedFile() + ".");

	} catch (Exception ex) {
	    new VisualException(ex).showDialog(frame);
	}
	this.initialise();
    }

    /**
     * Código de gestión de los eventos generados en las notificaciones
     * 
     * @param e Evento de acción
     */
    private void actionNotificacion(ActionEvent e) {

	Administrador c = (Administrador) modelo.getUsrActivo();
	List<NotificacionAdmision> ln = c.getNotificaciones();

	String s = e.getActionCommand();
	s = s.substring(s.indexOf('_') + 1);
	int pos = ln.size() - 1 - Integer.parseInt(s.substring(0, s.indexOf('_')));
	String command = s.substring(s.indexOf('_') + 1);

	switch (command) {

	    case "Discard": {
		ln.remove(pos);
		setNotificaciones();
		break;
	    }

	    case "Click": {
		ln.get(pos).setVisto(true);
		setNotificaciones();
		break;
	    }

	    case "Ver proyecto": {
		Proyecto p = ((NotificacionCreacionProyecto) ln.get(pos)).getProyecto();
		((ControlInfoProyecto) frame.getControl().getControlador("infoProyecto")).setData(p.getId());
		frame.showVistaDialog("infoProyecto");
		break;
	    }

	    case "Aceptar": {
		ln.get(pos).aceptar();
		String txt = "";
		String desc = "";
		if (ln.get(pos) instanceof NotificacionCreacionProyecto) {
		    txt = "Proyecto aceptado";
		    desc = "El proyecto ha sido añadido a la aplicación";
		} else {
		    txt = "Ciudadano aceptado";
		    desc = "El ciudadano ha sido añadido a la aplicación";
		}
		IconDialog.successDialog(frame, txt, desc);
		ln.remove(pos);
		initialise();
		break;
	    }

	    case "Rechazar": {
		if (ln.get(pos) instanceof NotificacionCreacionProyecto) {
		    Consumer<String> exitAction = motivo -> {

			ln.get(pos).rechazar(modelo, motivo);
			ln.remove(pos);
			initialise();
		    };
		    InsertValueDialog inv = new InsertValueDialog(frame, "Motivo de rechazo",
			    "Introduzca el motivo por el que ha rechazado el proyecto", exitAction);
		    inv.setVisible(true);

		} else {
		    ln.get(pos).rechazar(modelo, null);
		    IconDialog.successDialog(frame, "Ciudadano rechazado",
			    "El ciudadano ha sido eliminado de la aplicación");
		    ln.remove(pos);
		    initialise();
		}

		break;
	    }
	}
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
	if (e.getSource() instanceof ListSelectionModel) {
	    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
	    if (lsm.isSelectionEmpty()) {
		vista.setCambioEstadoEnable(false);
	    } else {
		vista.setCambioEstadoEnable(true);
	    }
	}

    }

    @Override
    public void initialise() {
	setCiudadanos();
	setNotificaciones();
    }

    /**
     * Establece los datos de los ciudadanos en la ventana
     * 
     */
    public void setCiudadanos() {
	Set<Usuario> users = modelo.getTodosUsuarios();
	List<String> nombres = new ArrayList<>();
	List<String> nifs = new ArrayList<>();
	List<String> estados = new ArrayList<>();
	for (Usuario u : users) {
	    if (u instanceof Ciudadano) {
		Ciudadano c = (Ciudadano) u;
		nombres.add(c.getName());
		nifs.add(c.getNif());
		if (!c.isRegistroAprobado()) {
		    estados.add("Registro pendiente");
		} else {
		    if (c.isBloqueado()) {
			estados.add("Bloqueado");
		    } else {
			estados.add("Desbloqueado");
		    }
		}

	    }
	}
	vista.setCiudadanos(nombres, nifs, estados);
    }

    /**
     * Establece las notificaciones en la ventana
     * <p>
     * Cada notificación tendrá las acciones correspondientes al tipo de
     * notificación
     */
    public void setNotificaciones() {
	Administrador c = (Administrador) modelo.getUsrActivo();
	List<NotificacionAdmision> ln = c.getNotificaciones();
	List<String> texto = new ArrayList<>();
	List<List<String>> acciones = new ArrayList<>();
	List<Boolean> visto = new ArrayList<>();
	List<Boolean> discardable = new ArrayList<>();
	for (Notificacion n : ln) {
	    texto.add(n.getCuerpo());
	    visto.add(n.isVisto());
	    discardable.add(false);
	    List<String> act = new ArrayList<>();
	    if (n instanceof NotificacionRegistro) {
		act.add("Aceptar");
		act.add("Rechazar");
	    }
	    if (n instanceof NotificacionCreacionProyecto) {
		act.add("Ver proyecto");
		act.add("Aceptar");
		act.add("Rechazar");
	    }
	    acciones.add(act);
	}
	vista.setNotificaciones(texto, acciones, visto, discardable);
    }

}
