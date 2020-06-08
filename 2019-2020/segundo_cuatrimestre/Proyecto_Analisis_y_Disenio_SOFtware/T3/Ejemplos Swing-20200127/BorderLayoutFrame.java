package border1;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class BorderLayoutFrame implements Runnable {	
	public void run() {

		// Ventana principal 
		JFrame frame = new JFrame("Una Ventana de Ejemplo");
		
		JButton button1    = new JButton("NORTE");
		JButton button2    = new JButton("SUR");
		JButton button3    = new JButton("ESTE");
		JButton button4    = new JButton("OESTE");
		JButton button5    = new JButton("CENTRO");
		
		frame.add(button1,  BorderLayout.NORTH);
		frame.add(button2,  BorderLayout.SOUTH);
		frame.add(button3,  BorderLayout.EAST);
		frame.add(button4,  BorderLayout.WEST);
		frame.add(button5,  BorderLayout.CENTER);
		
		frame.setSize(500, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		BorderLayoutFrame app = new BorderLayoutFrame();
		SwingUtilities.invokeLater(app);               
	}

}
