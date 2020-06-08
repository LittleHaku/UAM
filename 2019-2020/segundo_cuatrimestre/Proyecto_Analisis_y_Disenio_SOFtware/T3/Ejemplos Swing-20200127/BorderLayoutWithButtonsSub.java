package border3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * También podemos crear ventanas subclasificando de JFrame
 * @author Juan de Lara
 *
 */
class MyClass extends JFrame {	
	private static final long serialVersionUID = 9118673928693575927L;

	public MyClass(String text) {
		super(text);
		
		int APP_WIDTH  = 500;
		int APP_HEIGHT = 550;
		
		JButton button1    = new JButton("NORTE");
		JButton button2    = new JButton("SUR");
		JButton button3    = new JButton("ESTE");
		JButton button4    = new JButton("OESTE");
		JButton button5    = new JButton("CENTRO");
		
		this.add(button1,  BorderLayout.NORTH);
		this.add(button2,  BorderLayout.SOUTH);
		this.add(button3,  BorderLayout.EAST);
		this.add(button4,  BorderLayout.WEST);
		this.add(button5,  BorderLayout.CENTER);
		
		// listeners para los botones
		button1.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {								
						JOptionPane.showMessageDialog(null, "NORTE");								
					}});			
		
		button2.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						JOptionPane.showMessageDialog(null, "SUR");
					}});
		
		button3.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						JOptionPane.showMessageDialog(null, "ESTE");
					}});			
		
		button4.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						JOptionPane.showMessageDialog(null, "OESTE");
					}});			
		
		button5.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						JOptionPane.showMessageDialog(null, "CENTRO");
					}});
		
		this.setSize(APP_WIDTH, APP_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		this.setVisible(true);	
	}
}

public class BorderLayoutWithButtonsSub {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				// Ventana principal 
				final MyClass frame = new MyClass("Una Ventana de Ejemplo que Hereda de JFrame");
			}
		};
		SwingUtilities.invokeLater(runner);		
	}

}
