import java.awt.BorderLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Una ventanas subclase de JFrame y con una JList seleccionable dentro
 * de un JScrollPane.
 * Se muestra la seleccion con JLabel en el Layout CENTER 
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
    private JLabel labelOne;
	        
    public MyFrameClass(String text) {
        super(text);
               
        listOne    = new JList(dataList);
        scrollingListOne = new JScrollPane(listOne);
        labelOne    = new JLabel();
        labelOne.setHorizontalAlignment(SwingConstants.CENTER);
                
        this.add(scrollingListOne,  BorderLayout.EAST);
        this.add(labelOne,  BorderLayout.CENTER);
                
        // listeners para la lista
        listOne.addListSelectionListener(this);                                       
    }
 
    public void valueChanged(ListSelectionEvent e) {               
        int selection = this.listOne.getSelectedIndex(); 
        labelOne.setText(dataList[selection]);
    } 
}

public class BorderJScrollPaneWithJListJLabel {
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
                                        new MyFrameClass("Ventana Subclase de JFrame con ListaSeleccionable");
                                                                                                
                                frame.setSize(APP_WIDTH, APP_HEIGHT);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                
                                frame.setVisible(true);                                                        
                        }
                };
                SwingUtilities.invokeLater(runner);                
        }
}
