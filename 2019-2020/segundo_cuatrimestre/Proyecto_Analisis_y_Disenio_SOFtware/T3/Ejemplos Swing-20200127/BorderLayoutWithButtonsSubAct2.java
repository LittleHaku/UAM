package border5;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import javax.swing.ImageIcon;


/**
 * También podemos crear ventanas subclasificando de JFrame. La propia
 * ventana puede implementar los métodos para los lísteners
 * @author Juan de Lara
 *
 */
class MyFrameClass extends JFrame implements ActionListener {	
	private static final long serialVersionUID = 9118673928693575927L;

	private JButton button1, button2, button3, button4, button5;
		
	public MyFrameClass(String text) {
		super(text);
		
		button1    = new JButton("NORTE");
		button2    = new JButton("SUR");
		button3    = new JButton("ESTE");
		button4    = new JButton("OESTE");
		button5    = new JButton("CENTRO");

		int APP_WIDTH  = 500;
		int APP_HEIGHT = 550;
		
		this.add(button1,  BorderLayout.NORTH);
		this.add(button2,  BorderLayout.SOUTH);
		this.add(button3,  BorderLayout.EAST);
		this.add(button4,  BorderLayout.WEST);
		this.add(button5,  BorderLayout.CENTER);
		
		// listeners para los botones
		button1.addActionListener(this);					
		button2.addActionListener(this);		
		button3.addActionListener(this);			
		button4.addActionListener(this);
		button5.addActionListener(this);
		
		this.setSize(APP_WIDTH, APP_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		this.setVisible(true);							
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String message = "CENTRO";
		// Es mucho mejor hacerse una tabla o mapa (HashMap) que asigne mensajes a botones...
		if (e.getSource()==this.button1) message = "NORTE";
		if (e.getSource()==this.button2) message = "SUR";
		if (e.getSource()==this.button3) message = "ESTE";
		if (e.getSource()==this.button4) message = "OESTE";
		
		JOptionPane.showMessageDialog(null, message);
	}
}

public class BorderLayoutWithButtonsSubAct2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				// Ventana principal 
				final MyFrameClass frame = new MyFrameClass("Una Ventana de Ejemplo que Hereda de JFrame con Listeners en la misma clase");
			}
		};
		SwingUtilities.invokeLater(runner);		
	}

}