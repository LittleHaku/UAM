package controlador;

import java.awt.*;
import java.awt.event.*;
import modelo.*;
import vista.*;

public class ControlDetalleProyecto implements ActionListener {
	
	private Ventana frame;
	private DetalleProyecto vista;
	private Proyecto modelo;
	
	public ControlDetalleProyecto(Ventana frame, Proyecto modelo) {
		this.frame = frame;
		this.vista = this.frame.getVistaDetalleProyecto(); 
		this.modelo = modelo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mostrarPanelAltaTarea();
	}
	
	private void mostrarPanelAltaTarea () {
		this.frame.getGetVistaAltaTarea().setVisible(true);
		this.vista.setVisible(false);		
		this.frame.getGetVistaAltaTarea().update();
	}
	
}
