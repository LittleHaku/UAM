package controlador;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import componentes.IconDialog;
import componentes.InformationDialog;
import es.uam.eps.sadp.grants.InvalidIDException;
import modelo.aplicacion.Administrador;
import modelo.aplicacion.Aplicacion;
import modelo.aplicacion.Usuario;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.Colectivo;
import modelo.colectivoCiudadano.ElementoColectivo;
import modelo.proyecto.Distrito;
import modelo.proyecto.ProyInfraestructura;
import modelo.proyecto.ProySocial;
import modelo.proyecto.Proyecto;
import modelo.proyecto.Proyecto.EstadoProyecto;
import vista.Ventana;
import vista.VistaElegirColectivo;
import vista.VistaInfoProyecto;;

/**
 * Controlador de la vista {@link VistaInfoProyecto}.
 * <p>
 * Este controlador gestiona los eventos de acción que se generen en su vista
 * 
 * @see VistaInfoProyecto
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlInfoProyecto extends Controlador {

    /* Vista del controlador */
    private final VistaInfoProyecto v;
    /* Proyecto de la vista */
    private Proyecto p;

    /**
     * Constructor de ControlInfoProyecto
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlInfoProyecto(Ventana frame, Aplicacion modelo) {
	super("infoProyecto", frame, modelo);
	this.v = (VistaInfoProyecto) frame.getVista(this.getId());
    }

    /**
     * Manejador de los botones.
     * <p>
     * {@inheritDoc}
     */

    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {

	    case "suscribirse": {
		if (p.addSubscrito((Ciudadano) modelo.getUsrActivo())) {
		    IconDialog.successDialog(frame, "Suscripcion Correcta", "Te has suscrito al proyecto");
		} else {
		    IconDialog.errorDialog(frame, "Error Suscripcion", "Error al suscribirse al proyecto");
		}

		updateFilaButtons();
		break;
	    }

	    case "apoyar_col": {
		VistaElegirColectivo v = (VistaElegirColectivo) frame.getVista("elegirColectivo");
		v.setButtonActionCommand("apoyarProyecto");
		v.setTextButton("Apoyar Proyecto");
		v.setTextTitulo("Apoyar Como Colectivo");
		((ControlElegirColectivo) frame.getControl().getControlador("elegirColectivo")).initialise_filtro(p);
		frame.showVistaDialog("elegirColectivo");

		((ControlPanelControl) frame.getControl().getControlador("panelDeControl")).initialise();
		setData(p.getId());
		break;
	    }

	    case "consultar_apoyos": {
		String desc = "Numero de apoyos del proyecto " + p.getTitulo();
		InformationDialog id = new InformationDialog(frame, desc,
			Integer.toString(p.getNumApoyos()) + " apoyos");
		id.setVisible(true);
		break;

	    }
	    case "con_estado": {

		try {
		    Double d = modelo.consultar(p);
		    if (d == null) {
			String desc = "Estado del Proyecto";
			InformationDialog id = new InformationDialog(frame, desc,
				"No hay cambios en el estado del proyecto");
			id.setVisible(true);
		    } else if (d == 0) {
			String desc = "Estado del Proyecto";
			InformationDialog id = new InformationDialog(frame, desc,
				"El Proyecto ha sido Rechazado por el sistema externo");
			id.setVisible(true);
			v.setTextEstado("Rechazado");
			v.setFilaButtons(-1);
			v.setTextImporteAdj(String.format("%.2f €", d));
			((ControlPanelControl) frame.getControl().getControlador("panelDeControl")).initialise();
			break;
		    } else {
			String desc = "Estado del Proyecto";
			InformationDialog id = new InformationDialog(frame, desc,
				"El Proyecto ha sido Aceptado por el sistema externo");
			id.setVisible(true);
		    }
		} catch (IOException | InvalidIDException e1) {
		    IconDialog.errorDialog(frame, "Error Consultando Proyecto",
			    "No se ha podido consultar:" + e1.getMessage());
		    break;
		}

		((ControlPanelControl) frame.getControl().getControlador("panelDeControl")).initialise();
		setData(p.getId());
		break;

	    }
	    case "apoyar": {
		if (((Ciudadano) modelo.getUsrActivo()).isBloqueado()) {
		    p.addApoyos((ElementoColectivo) modelo.getUsrActivo());
		    IconDialog.errorDialog(frame, "Usuario Bloqueado", "No se tendrá en cuenta su apoyo");
		    break;
		}
		if (p.addApoyos((ElementoColectivo) modelo.getUsrActivo())) {
		    IconDialog.successDialog(frame, "Proyecto Apoyado", "Proyecto apoyado correctamente");
		} else {
		    IconDialog.errorDialog(frame, "Proyecto No Apoyado", "No se ha podido apoyar al proyecto");
		}
		((ControlPanelControl) frame.getControl().getControlador("panelDeControl")).initialise();
		setData(p.getId());
		break;
	    }
	    case "enviar": {

		try {
		    if (modelo.enviar(p)) {
			IconDialog.successDialog(frame, "Proyecto Envaido", "Proyecto envaido correctamente");
		    } else {
			IconDialog.errorDialog(frame, "Proyecto No Enviado",
				"El proyecto no cumple con los requisitos para ser enviado");
		    }
		} catch (Exception ex) {
		    IconDialog.errorDialog(frame, "Error Enviando Proyecto",
			    "El proyecto no ha podido enviarse" + ex.getMessage());
		}
		setData(p.getId());
		break;
	    }

	}

    }

    /**
     * Cambia la vista para adaptarse a los datos del proyecto que se le pasa como
     * argumento
     * 
     * @param id la id del proyecto que se quiere inspeccionar
     * 
     */
    public void setData(int id) {

	p = modelo.getProyectos().get(id);

	String estado = "";
	if (p.isCaducado() && p.getEstado().equals(EstadoProyecto.EN_VOTACION)) estado = "Caducado";
	else if (p.isCaducado() && p.getEstado().equals(EstadoProyecto.RECHAZADO) == false
		&& p.getEstado().equals(EstadoProyecto.ACEPTADO) == false) {
		    estado = p.getEstado().toString() + " y Caducado";
		} else
	    estado = p.getEstado().toString();

	Double db = p.getImporteAdjudicado();
	if (db != null) {
	    v.setTextImporteAdj(String.format("%.2f €", db));
	}
	v.setGeneral(p.getTitulo(), estado, p.getDescripcion(), p.getImportePedido());

	if (p instanceof ProyInfraestructura) {

	    List<String> distritos = new ArrayList<>();
	    for (Distrito d : ((ProyInfraestructura) p).getDistritos()) {
		distritos.add(d.getNombre());
	    }

	    v.setInfra(distritos, ((ProyInfraestructura) p).getImagen());

	} else {

	    String impacto = "";
	    if (((ProySocial) p).isNacional()) {
		impacto = "Nacional";
	    } else {
		impacto = "Internacional";
	    }

	    v.setSocial(impacto, ((ProySocial) p).getGrupo());

	}

	updateFilaButtons();

    }

    /**
     * Actualiza la fila de botones de la vista según el estado del proyecto o el
     * usuario que inspeccione el proyecto.
     * 
     */
    private void updateFilaButtons() {

	Usuario usr = modelo.getUsrActivo();
	ElementoColectivo creador = p.getCreador();

	if (usr instanceof Administrador) {
	    v.setFilaButtons(-1);
	    return;

	}

	if (creador instanceof Ciudadano) {
	    Ciudadano ciu = (Ciudadano) creador;

	    if (usr.equals(ciu)) {
		v.setFilaButtons(0);
		if (!p.getEstado().equals(EstadoProyecto.ENVIABLE)) {
		    v.setEnviarButton(false);
		} else {
		    v.setEnviarButton(true);
		}
		if (!p.getEstado().equals(EstadoProyecto.ENVIADO)) {
		    v.setConultarButton(false);
		} else {
		    v.setConultarButton(true);
		}

	    } else {
		v.setFilaButtons(1);
		if (((Ciudadano) usr).getColectRepresentados().isEmpty()) {
		    v.setApoyarColButton(false);
		} else {
		    v.setApoyarColButton(true);
		}

		if (p.getSubscritos().contains(usr)) {
		    v.setSubButton(false);
		} else {
		    v.setSubButton(true);
		}
		if (p.getApoyos().contains((Ciudadano) usr)) {
		    v.setApoyarButton(false);
		} else {
		    int flag = 0;
		    for (ElementoColectivo e : p.getApoyos()) {
			if (e instanceof Colectivo) {
			    if (((Colectivo) e).contains((Ciudadano) usr)) {
				flag++;
				v.setApoyarButton(false);
			    }
			}
		    }
		    if (flag == 0) {
			v.setApoyarButton(true);
		    }
		}

	    }
	}

	else {
	    if (usr.equals(creador.getRepresentante())) {
		v.setFilaButtons(0);
		if (!p.getEstado().equals(EstadoProyecto.ENVIABLE)) {
		    v.setEnviarButton(false);
		} else {
		    v.setEnviarButton(true);
		}

	    } else {
		v.setFilaButtons(1);
		if (p.getSubscritos().contains(usr)) {
		    v.setSubButton(false);
		} else {
		    v.setSubButton(true);
		}
		if (p.getApoyos().contains((Ciudadano) usr)) {
		    v.setApoyarButton(false);
		} else {
		    int flag = 0;
		    for (ElementoColectivo e : p.getApoyos()) {
			if (e instanceof Colectivo) {
			    if (((Colectivo) e).contains((Ciudadano) usr)) {
				flag++;
				v.setApoyarButton(false);
			    }
			}
		    }
		    if (flag == 0) {
			v.setApoyarButton(true);
		    }
		}
	    }
	}
    }

    /**
     * Devuelve el proyecto que se está mostrando en la vista
     * 
     * @return el proyecto guardado como atributo
     * 
     */
    public Proyecto getProyecto() {
	return p;
    }

}
