package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.*;

import modelo.*;
import vista.*;
import controlador.*;

public class Main {
	public static void main(String[] args) {
		/*JFrame ventana = new JFrame("mi ventana");
		ventana.setLayout(new FlowLayout());
		JButton ok = new JButton("ok");
		JTextField hola = new JTextField("hola", 10);
		Container contenedor = ventana.getContentPane();
		
		contenedor.add(ok);
		contenedor.add(hola);
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventana.setSize(300, 400);
		
		JPanel panel = new JPanel();
		JButton boton2 = new JButton("opcion2");
		panel.setBackground(Color.red);
		//panel.add(boton2);
		contenedor.add(panel);*/
		
		//ventana.setBackground(Color.red);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {						
					Ventana frame = new Ventana();
					Proyecto modelo = new Proyecto();
					Controlador controlador = new Controlador(frame, modelo);
					frame.setControlador(controlador);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
