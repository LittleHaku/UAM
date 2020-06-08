package vista;

import javax.swing.*;
import java.awt.*;
import controlador.*;

public class Ventana extends JFrame {
	
	private AltaTarea vistaAltaTarea;
	private DetalleProyecto vistaDetalleProyecto;
	
	private ControlAltaTarea contAltaTarea;
	private ControlDetalleProyecto contDetalleProyecto;
	
	private JPanel contentPane;
	
	public Ventana() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout());

		this.vistaAltaTarea = new AltaTarea();
		contentPane.add(vistaAltaTarea, "altaTarea");

		this.vistaDetalleProyecto = new DetalleProyecto(""); 
		contentPane.add(vistaDetalleProyecto, "detalleProyecto");
		
	}

	public void setControlador(Controlador controlador) {
		this.contAltaTarea = controlador.getControlAltaTarea();
		vistaAltaTarea.setControlador(contAltaTarea);
		this.contDetalleProyecto = controlador.getControlDetalleProyecto();
		vistaDetalleProyecto.setControlador(contDetalleProyecto);
	}

	public AltaTarea getGetVistaAltaTarea() {
		return this.vistaAltaTarea;
	}

	public DetalleProyecto getVistaDetalleProyecto() {
		return this.vistaDetalleProyecto;
	}
	
	public void mostrarPanel(String carta) {
		CardLayout l = (CardLayout)contentPane.getLayout();
		l.show(contentPane, carta);
	}

}
