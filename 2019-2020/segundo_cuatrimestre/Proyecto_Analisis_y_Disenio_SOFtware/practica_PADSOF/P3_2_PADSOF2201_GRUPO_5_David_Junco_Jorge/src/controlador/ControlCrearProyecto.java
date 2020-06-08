package controlador;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import componentes.BiOptionIconDialog;
import componentes.ComponentConstants;
import componentes.IconDialog;
import componentes.ImageFileFilter;
import exceptions.EmptyFieldException;
import exceptions.FormatException;
import exceptions.VisualException;
import modelo.aplicacion.Aplicacion;
import modelo.colectivoCiudadano.Ciudadano;
import modelo.colectivoCiudadano.ElementoColectivo;
import modelo.proyecto.Distrito;
import modelo.proyecto.ProyInfraestructura;
import modelo.proyecto.ProySocial;
import modelo.proyecto.Proyecto;
import vista.Ventana;
import vista.VistaCrearProyecto;
import vista.VistaElegirColectivo;

/**
 * Controlador de la vista {@link ControlCrearProyecto}.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class ControlCrearProyecto extends Controlador {

    private VistaCrearProyecto vista;
    private String titulo;
    private String desc;
    private String path;
    private String grupo;
    private ArrayList<Distrito> distritos;
    private double importe;

    /**
     * Constructor del ControlCrearProyecto
     * 
     * @param frame  la ventana de la que es controlador
     * @param modelo el modelo del que es controlador
     */
    public ControlCrearProyecto(Ventana frame, Aplicacion modelo) {
	super("crearProyecto", frame, modelo);
	this.vista = (VistaCrearProyecto) frame.getVista(this.getId());
    }

    /**
     * {@inheritDoc} Manejador de los botones.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	switch (e.getActionCommand()) {
	    case "infraestructura": {

		vista.selectCard("infraestructura");
		break;

	    }
	    case "social": {

		vista.selectCard("social");
		break;

	    }
	    case "back": {

		frame.showVista("panelDeControl");
		break;

	    }
	    case "explorar archivos": {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFileFilter());
		int res = fc.showOpenDialog(null);
		try {
		    if (res == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			vista.setTextPath(file.toString());

		    } else {
			JOptionPane.showMessageDialog(null, "Debes elegir una imagen para el proyecto", "Aviso",
				JOptionPane.WARNING_MESSAGE);
		    }
		} catch (Exception iOException) {
		}
		break;
	    }
	    case "proponer proyecto ciu": {

		Proyecto p;

		try {
		    checkProy();
		} catch (VisualException ex) {
		    ex.showDialog(frame);
		    break;
		} catch (IOException e1) {
		    new VisualException(e1).showDialog(frame);
		    break;
		} catch (InvalidPathException e2) {
		    Consumer<ActionEvent> action = l -> {
			path = ComponentConstants.I_IMAGE;
		    };
		    BiOptionIconDialog.warningDialog(frame, "Ruta incorrecta",
			    "No se ha encontrado el archivo. ¿Desea utilizar la imagen por defecto?", action);
		    if (path.equals(ComponentConstants.I_IMAGE) == false) break;

		}

		try {
		    p = crearProy((Ciudadano) modelo.getUsrActivo());
		} catch (VisualException e2) {
		    e2.showDialog(frame);
		    break;
		} catch (IOException e3) {
		    new VisualException(e3).showDialog(frame);
		    break;
		}

		Consumer<ActionEvent> exitAction = event -> {

		    frame.showVista("panelDeControl");
		};

		if (modelo.addProyectoParaAutorizar(p) == true) {
		    IconDialog.successDialog(frame, "Proyecto Propuesto", "Proyecto propuesto correctamente",
			    exitAction);
		} else {
		    IconDialog.errorDialog(frame, "Error creando proyecto", "El proyecto no se ha podido proponer");
		}

		break;
	    }
	    case "proponer proyecto col": {
		try {
		    checkProy();
		} catch (VisualException ex) {
		    ex.showDialog(frame);
		    break;
		} catch (IOException e1) {
		    new VisualException(e1).showDialog(frame);
		    break;
		} catch (InvalidPathException e2) {
		    Consumer<ActionEvent> action = l -> {
			path = ComponentConstants.I_IMAGE;
		    };
		    BiOptionIconDialog.warningDialog(frame, "Ruta incorrecta",
			    "No se ha encontrado el archivo. ¿Desea utilizar la imagen por defecto?", action);
		    if (path.equals(ComponentConstants.I_IMAGE) == false) break;
		}
		VistaElegirColectivo v = (VistaElegirColectivo) frame.getVista("elegirColectivo");
		v.setButtonActionCommand("crearProyecto");
		v.setButtonActionCommand("crearProyecto");
		v.setTextButton("Crear Proyecto");
		v.setTextTitulo("Crear Como Colectivo");
		((ControlElegirColectivo) frame.getControl().getControlador("elegirColectivo")).initialise();
		frame.showVistaDialog("elegirColectivo");
	    }
	}

    }

    /**
     * Crea un proyecto con los atributos de la vista, se recomienda llamar
     * previamente a checkProyecto para prevenir errores Si hubiese algun error
     * lanza una excepción que se puede mostrar por pantalla
     * 
     * @param creador el ciudadano o colectivo que crea el proyecto
     * @return el Proyecto creado listo para añadirse a la aplicación
     * 
     * @throws VisualException se lanza cuando hay un error creando el proyecto
     * @throws IOException     se lanza cuando hay un error interno.
     */
    public Proyecto crearProy(ElementoColectivo creador) throws VisualException, IOException {
	Proyecto p;

	if (vista.getTipoProyecto() == true) {
	    p = new ProyInfraestructura(titulo, desc, importe, creador, distritos, path);

	    // Usamos el hashcode para diferenciar los archivos creados por distintas
	    // aplicaciones
	    // (o, mejor dicho, distintos datos cargados en la aplicación)

	    String np = new String();

	    if (path != ComponentConstants.I_IMAGE) {
		np = "Images/" + modelo.hashCode() + "_" + p.getId() + path.substring(path.lastIndexOf("."));

		if (!Paths.get("Images").toFile().exists()) {
		    Paths.get("Images").toFile().mkdir();
		}

		Files.copy(Paths.get(path), Paths.get(np), StandardCopyOption.REPLACE_EXISTING);
		((ProyInfraestructura) p).setImagen(np);
	    }

	} else {
	    p = new ProySocial(titulo, desc, importe, creador, grupo, vista.getImpacto());
	}

	return p;
    }

    /**
     * Comprueba que no haya ningún error en los campos de la vista infoProyecto Si
     * hubiese algun error lanza una excepción que se puede mostrar por pantalla
     * 
     * @throws VisualException      se lanza cuando hay un error en los campos de la
     *                              vista
     * @throws IOException          se lanza cuando hay cualquier error interno.
     * @throws InvalidPathException se lanza cuando el path introducido no se
     *                              encuentra en el sistema.
     */
    public void checkProy() throws VisualException, IOException, InvalidPathException {
	titulo = vista.getTitulo();
	String importe_s = vista.getImporte();
	desc = vista.getDesc();

	if (titulo.isEmpty()) {
	    throw new EmptyFieldException("Titulo", "el Titulo de Proyecto");
	}
	if (importe_s.isEmpty()) {
	    throw new EmptyFieldException("Importe", "el Importe Solicitado");
	}

	importe = 0;
	try {
	    importe = new Double(importe_s);
	} catch (NumberFormatException ex) {
	    throw new FormatException("Importe", "Double");
	}

	if (vista.getTipoProyecto() == true) {

	    distritos = new ArrayList<Distrito>();

	    for (Integer i : vista.getSelectedDistritos()) {
		distritos.add(modelo.getDistritos().get(i));
	    }

	    if (distritos.isEmpty()) {
		throw new EmptyFieldException("Distritos Afectados", "el conjunto de distritos");
	    }

	    path = vista.getPath();

	    if (Paths.get(path).toFile().exists() == false) {
		throw new InvalidPathException("El archivo no existe", "No se encuentra");
	    }

	}

	else {

	    grupo = vista.getGrupoSocial();
	    if (grupo.isEmpty()) {
		throw new EmptyFieldException("Grupo Social", "el Grupo Social");
	    }

	}

    }

    @Override
    public void initialise() {
	ArrayList<String> dist = new ArrayList<String>();
	List<Distrito> dist_defecto = modelo.getDistritos();

	for (Distrito d : dist_defecto) {
	    dist.add(d.getNombre());
	}

	if (((Ciudadano) modelo.getUsrActivo()).getColectRepresentados().isEmpty()) {
	    vista.setProponerColButton(false);
	} else {
	    vista.setProponerColButton(true);
	}

	vista.clearDistritos();
	vista.setDistritos(dist);
	vista.setTextDesc(null);
	vista.setTextGrupoSocial(null);
	vista.setTextImporte(null);
	vista.setTextPath(null);
	vista.setTextTitulo(null);

    }
}
