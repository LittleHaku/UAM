package vista;

import javax.swing.*;
import java.awt.event.*;
import controlador.*;

public class DetalleProyecto extends JPanel {

	private String descripcionProyecto;
	private JButton boton;
	private JLabel etiqueta;
	
	public DetalleProyecto(String descripcionProyecto) { 
		this.descripcionProyecto = descripcionProyecto;
		etiqueta = new JLabel(this.descripcionProyecto);
		add(etiqueta);
		boton = new JButton("Volver");
		add(boton);
	}

	public void setControlador(ActionListener c) {
		boton.addActionListener(c);
	}
		
	public void update(String descripcionProyecto) { 
		this.descripcionProyecto = descripcionProyecto;
		etiqueta.setText(this.descripcionProyecto);
	}
	
}
