package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.event.ListSelectionEvent;

import componentes.IconDialog;
import exceptions.InternalErrorException;
import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import modelo.notificacion.Notificacion;
import modelo.notificacion.NotificacionCambioEstado;
import modelo.proyecto.Proyecto;
import modelo.proyecto.Proyecto.EstadoProyecto;
import vista.Ventana;
import vista.VistaPanelDeControl;

/**
 * Controlador de la vista {@link VistaPanelDeControl}.
 * <p>
 * Este controlador gestiona los eventos de acción y de ratón que se generen en
 * su vista
 * 
 * @see VistaPanelDeControl
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlPanelControl extends Controlador {
    /* Vista del controlador */
    private final VistaPanelDeControl vista;

    /**
     * Constructor del ControlPanelControl
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlPanelControl(Ventana frame, Aplicacion modelo) {
	super("panelDeControl", frame, modelo);

	this.vista = (VistaPanelDeControl) frame.getVista(this.getId());
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

	    case "buscarProyecto": {

		frame.showVista("buscarProyecto");
		break;
	    }

	    case "propProyecto": {

		frame.showVista("crearProyecto");
		break;
	    }

	    case "crearColectivo": {

		frame.showVista("crearColectivo");
		break;
	    }

	    case "apuntarseColectivo": {

		frame.showVista("apuntarseColectivo");
		break;
	    }
	    case "salirColectivo": {

		try {
		    salirDeColectivo();
		} catch (VisualException e2) {
		    e2.showDialog(frame);
		}

		initialise();
		break;
	    }
	    case "exit": {
		frame.showVista("inicial");
		break;
	    }
	    case "informeAfinidad": {

		frame.showVista("informeAfinidad");
		break;
	    }
	    default: {
		System.out.println(e.getActionCommand());

	    }
	}
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
	vista.setSalirColectivoEnable(!vista.getNameColectivoSelected().equals(""));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
	if (e.getClickCount() >= 2) {
	    int i = vista.getIdProyectoSelected();
	    if (i != -1) {
		((ControlInfoProyecto) frame.getControl().getControlador("infoProyecto")).setData(i);
		frame.showVistaDialog("infoProyecto");
	    }

	}

    }

    /**
     * Acciones a llevar a cabo para salir de un colectivo
     */
    private void salirDeColectivo() throws VisualException {
	String name = vista.getNameColectivoSelected();
	if (name.equals("")) {
	    throw new InternalErrorException("Colectivo no encontrado");
	}
	Colectivo col = modelo.encontrarColectivo(name);
	if (col == null) throw new InternalErrorException("Colectivo no encontrado");

	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	if (c == null) throw new InternalErrorException("Ciudadano no reconocido");

	col.remove(c);

	String desc = "Se ha salido del colectivo \"" + name
		+ "\". Sus apoyos a través del colectivo también han sido eliminado";

	IconDialog.successDialog(frame, "Se ha salido correctamente del colectivo", desc);

    }

    /* Gestiona los eventos de acción de las notificaciones */
    private void actionNotificacion(ActionEvent e) {
	String s = e.getActionCommand();
	s = s.substring(s.indexOf('_') + 1);
	int pos = Integer.parseInt(s.substring(0, s.indexOf('_')));
	String command = s.substring(s.indexOf('_') + 1);

	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	List<Notificacion> ln = c.getNotificaciones();
	pos = ln.size() - 1 - pos;

	switch (command) {
	    case "Discard":
		ln.remove(pos);
		setNotificaciones();
		break;
	    case "Click":
		ln.get(pos).setVisto(true);
		setNotificaciones();
		break;
	    case "Ver proyecto":
		Proyecto p = ((NotificacionCambioEstado) ln.get(pos)).getProy();
		((ControlInfoProyecto) frame.getControl().getControlador("infoProyecto")).setData(p.getId());
		frame.showVistaDialog("infoProyecto");
		break;

	}
    }

    /**
     * Establece los datos de los colectivos en la vista
     */
    public void setColectivos() {
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	Set<Colectivo> col = c.getPertenece();
	List<String> nombres = new ArrayList<>();
	List<String> roles = new ArrayList<>();
	List<String> supercol = new ArrayList<>();
	for (Colectivo co : col) {
	    nombres.add(co.getName());
	    roles.add((co.getRepresentante().equals(c)) ? "Representante" : "Miembro");
	    Colectivo p = co.getColectivoPadre();
	    if (p == null) {
		supercol.add("");
	    } else {
		supercol.add(p.getName());
	    }
	}
	vista.setColectivos(nombres, supercol, roles);
    }

    /**
     * Establece los datos de los proyectos en la vista
     */
    public void setProyectos() {
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	List<String> ids = new ArrayList<>();
	List<String> nombres = new ArrayList<>();
	List<String> roles = new ArrayList<>();

	for (Proyecto p : c.getProyPropuestos()) {
	    ids.add(Integer.toString(p.getId()));
	    nombres.add(p.getTitulo());
	    roles.add("Creador");
	}
	List<Proyecto> l1 = c.getColectRepresentados().stream().flatMap(e -> e.getProyPropuestos().stream())
		.collect(Collectors.toList());
	for (Proyecto p : l1) {
	    if (ids.contains(Integer.toString(p.getId())) == false) {
		ids.add(Integer.toString(p.getId()));
		nombres.add(p.getTitulo());
		roles.add("Propuesto por " + p.getCreador().getName());
	    }
	}

	List<Proyecto> l2 = c.getPertenece().stream().flatMap(e -> e.getProyApoyados().stream())
		.collect(Collectors.toList());
	for (Proyecto p : l2) {
	    if (ids.contains(Integer.toString(p.getId())) == false) {
		if (p.getCreador().getRepresentante().equals(c)) continue;
		ids.add(Integer.toString(p.getId()));
		nombres.add(p.getTitulo());
		roles.add("Apoyado desde Colectivo");
	    }
	}
	for (Proyecto p : c.getProyApoyados()) {
	    if (p.getCreador().getRepresentante().equals(c)) continue;
	    if (ids.contains(Integer.toString(p.getId())) == false) {
		ids.add(Integer.toString(p.getId()));
		nombres.add(p.getTitulo());
		roles.add("Apoyo");
	    }
	}

	vista.setProyectos(ids, nombres, roles);
    }

    /**
     * Establece los datos de las notificaciones en la vista
     */
    public void setNotificaciones() {
	Ciudadano c = (Ciudadano) modelo.getUsrActivo();
	List<String> texto = new ArrayList<>();
	List<List<String>> acciones = new ArrayList<>();
	List<Boolean> visto = new ArrayList<>();
	List<Boolean> discardable = new ArrayList<>();

	for (Notificacion n : c.getNotificaciones()) {
	    texto.add(n.getCuerpo());
	    visto.add(n.isVisto());
	    discardable.add(true);
	    List<String> act = new ArrayList<>();
	    if (n instanceof NotificacionCambioEstado) {
		if (((NotificacionCambioEstado) n).getProy().getEstado().equals(EstadoProyecto.RECHAZADO) == false) {
		    act.add("Ver proyecto");
		}
	    }
	    acciones.add(act);
	}
	vista.setNotificaciones(texto, acciones, visto, discardable);
    }

    @Override
    public void initialise() {
	setColectivos();
	setProyectos();
	setNotificaciones();

	String name = modelo.getUsrActivo().getUsr();
	if (name.contains(" ")) {
	    name = name.substring(0, name.indexOf(" "));
	}
	vista.setNombre(name);

    }

}
