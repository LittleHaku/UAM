package border2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Una ventana con BorderLayout y con botones  
 * @author Juan de Lara 
 */
public class BorderLayoutWithButtonsFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {

				int APP_WIDTH  = 500;
				int APP_HEIGHT = 550;

				// Ventana principal 
				final JFrame frame = new JFrame("Una Ventana de Ejemplo");
				
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
				
				frame.setSize(APP_WIDTH, APP_HEIGHT);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
				frame.setVisible(true);
				
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
			}
		};
		SwingUtilities.invokeLater(runner);		
	}

}
