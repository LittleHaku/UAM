import java.awt.BorderLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Una ventana subclase de JFrame y con una JList seleccionable dentro
 * de un JScrollPane.
 * Muestra la seleccion con un JOptionPane.showMessageDialog
 * La ventana se crea de altura menor al tamaño de la lista,
 * pero se puede hacer scroll sin redimensionar la ventana con el raton.
 * @author Eduardo
 */
class MyFrameClass extends JFrame implements ListSelectionListener {

    private String[] dataList = {"Andalucia", "Aragon", "Asturias", "Baleares", 
                        "Canarias", "Cantabria", "Castilla la Mancha", 
                        "Castilla y Leon", "Catalunya", "Extremadura", 
                        "Galicia", "Madrid", "Murcia", "Navarra", 
                        "Pais Vasco", "La Rioja", "Pais Valenciano", 
                        "Ceuta", "Melilla"};

    private JList listOne;
    private JScrollPane scrollingListOne;
	        
    public MyFrameClass(String text) {
        super(text);
               
        listOne    = new JList(dataList);
        scrollingListOne = new JScrollPane(listOne);
                
        this.add(scrollingListOne,  BorderLayout.EAST);
                
        // listener para la lista
        listOne.addListSelectionListener(this);                                       
    }
 
    public void valueChanged(ListSelectionEvent e) {               
        int selection = this.listOne.getSelectedIndex(); 
        JOptionPane.showMessageDialog(null, dataList[selection]);
    } 
}

public class BorderJScrollPaneWithJList {
        /**
         * @param args
         */
        public static void main(String[] args) {
                Runnable runner = new Runnable() {
                        public void run() {
                                int APP_WIDTH  = 500;
                                int APP_HEIGHT = 250;

                                // Ventana principal 
                                final MyFrameClass frame = 
                                        new MyFrameClass("Ventana Subclase de JFrame con Lista Seleccionable y Scroll");
                                                                                                
                                frame.setSize(APP_WIDTH, APP_HEIGHT);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                
                                frame.setVisible(true);                                                        
                        }
                };
                SwingUtilities.invokeLater(runner);                
        }
}
