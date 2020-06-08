import java.awt.BorderLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Una ventana subclase de JFrame y con una JList seleccionable.
 * Muestra la seleccion con un JOptionPane.showMessageDialog
 * La ventana se crea de altura menor al tamaño de la lista,
 * se redimensiona con el raton para ver la lista completa.
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
        
    public MyFrameClass(String text) {
        super(text);
               
        listOne    = new JList(dataList);
                
        this.add(listOne,  BorderLayout.EAST);
                
        // listeners para la lista
        listOne.addListSelectionListener(this);                                       
    }
 
    public void valueChanged(ListSelectionEvent e) {               
        int selection = this.listOne.getSelectedIndex(); 
        JOptionPane.showMessageDialog(null, dataList[selection]);
    } 
}

public class BorderJList {
        /**
         * @param args
         */
        public static void main(String[] args) {
                Runnable runner = new Runnable() {
                        public void run() {
                                int APP_WIDTH  = 500;
                                int APP_HEIGHT = 200;

                                // Ventana principal 
                                final MyFrameClass frame = 
                                        new MyFrameClass("Ventana Subclase de JFrame con ListaSeleccionable");
                                                                                                
                                frame.setSize(APP_WIDTH, APP_HEIGHT);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                
                                frame.setVisible(true);                                                        
                        }
                };
                SwingUtilities.invokeLater(runner);                
        }
}
