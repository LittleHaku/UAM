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
 * Una ventana subclase de JFrame, con DOS JList, el contenido de 
 * la segunda depende de la seleccion en la primera, y ambas dentro 
 * de su respectivo JScrollPane
 * Se muestra la seleccion de ambas JList con JLabel en el Layout CENTER 
 * @author Eduardo
 */
class MyFrameClass extends JFrame implements ListSelectionListener {

    private String[] dataListOne = {"Andalucia", "Aragon", "Asturias", "Baleares", 
                        "Canarias", "Cantabria", "Castilla la Mancha", 
                        "Castilla y Leon", "Catalunya", "Extremadura", 
                        "Galicia", "Madrid", "Murcia", "Navarra", 
                        "Pais Vasco", "La Rioja", "Pais Valenciano", 
                        "Ceuta", "Melilla"};
    private String[] dataListTwo = { "" };

    private JList listOne, listTwo;
    private JScrollPane scrollingListOne, scrollingListTwo;
    private JLabel labelOne;

    private int listOneSelection;
    private int listTwoSelection;
	        
    public MyFrameClass(String text) {
        super(text);
               
        // primera lista
        listOne    = new JList(dataListOne);
        scrollingListOne = new JScrollPane(listOne);
        // etiqueta central
        labelOne    = new JLabel();
        labelOne.setHorizontalAlignment(SwingConstants.CENTER);
        // segunda lista: cambiará con el valor seleccionado en la primera
        listTwo    = new JList();
        scrollingListTwo = new JScrollPane();
                
        this.add(scrollingListOne,  BorderLayout.EAST);
        this.add(labelOne,  BorderLayout.CENTER);
        this.add(scrollingListTwo,  BorderLayout.WEST);
                
        // listeners para la lista principal
        listOne.addListSelectionListener(this);                                       
    }
 
    public void valueChanged(ListSelectionEvent e) {               
      if (e.getSource().equals(this.listOne)) {
            if  (listTwoSelection >= 0)  {
                     listOneSelection = this.listOne.getSelectedIndex(); 
                     labelOne.setText(dataListOne[listOneSelection]);
                     dataListTwo = dataListOne[listOneSelection].split("");
                     // cambiar contenido de la segunda lista
                     // listTwo    = new JList(dataListTwo);
                     listTwo.setListData(dataListTwo);
                     listTwo.setFixedCellWidth(30);                                       
                     listTwo.addListSelectionListener(this);                                       
                     scrollingListTwo.setViewportView(listTwo);
            }
      } 
      else if (e.getSource().equals(this.listTwo)) {
            listTwoSelection = this.listTwo.getSelectedIndex(); 
            if  (listTwoSelection >= 0)  {
                     labelOne.setText(dataListOne[listOneSelection] + " -> " 
                                     +  dataListTwo[listTwoSelection]);
            }
      }
    } 
}

public class BorderJScrollPaneWithTwoJLists {
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
                                        new MyFrameClass("Ventana Subclase de JFrame con DOS Lista Seleccionables");
                                                                                                
                                frame.setSize(APP_WIDTH, APP_HEIGHT);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                
                                frame.setVisible(true);                                                        
                        }
                };
                SwingUtilities.invokeLater(runner);                
        }
}
