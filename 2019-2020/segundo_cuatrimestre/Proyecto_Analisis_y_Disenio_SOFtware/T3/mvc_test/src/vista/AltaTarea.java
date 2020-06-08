package vista;

import javax.swing.*;
import java.awt.event.*;

public class AltaTarea extends JPanel {
	
	private JTextField campo;
	private JButton boton;
	
	public AltaTarea() {
		
		JLabel etiqueta = new JLabel("Nombre de la tarea:");
		add(etiqueta);
		
		campo = new JTextField();
		add(campo);
		campo.setColumns(10);
		campo.requestFocus();
		
		boton = new JButton("Aceptar");
		add(boton);
		
	}
	
	public void setControlador(ActionListener c) {  
		boton.addActionListener(c);
	}
	
	public String getNombreTarea () {
		return campo.getText();
	}
	
	public void update () {
		campo.setText("");
		campo.grabFocus();
	}

}
