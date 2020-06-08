package controlador;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import modelo.*;
import vista.*;

public class ControlAltaTarea implements ActionListener {
	
	private AltaTarea vista;
	private Ventana frame;
	private Proyecto modelo;
	
	public ControlAltaTarea(Ventana frame, Proyecto modelo) {
		this.frame = frame;
		this.vista = frame.getGetVistaAltaTarea();
		this.modelo = modelo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// validar valores en la vista
		String nombreTarea = vista.getNombreTarea();
		if (nombreTarea.equals("")) {
			JOptionPane.showMessageDialog(vista,
					"Debe introducir un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// modificar modelo
		Tarea tarea = new Tarea(nombreTarea);
		modelo.addTarea(tarea);
		
		// mostrar nueva vista
		DetalleProyecto nuevaVista = this.getPanelDetalleProyecto();
		nuevaVista.update(modelo.toString());

		nuevaVista.setVisible(true);
		vista.setVisible(false);
		
		// this.frame.mostrarPanel("detalleProyecto"); 
			// alternativa usando CardLayout a las dos líneas anteriores
		
	}
	
	public DetalleProyecto getPanelDetalleProyecto() {
		return frame.getVistaDetalleProyecto();
	}
	
}
