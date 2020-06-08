import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Una ventana subclase de JFrame y con un JMenuBar con dos JMenu de
 * tres JMenuItem cada uno, que muestran la opcion seleccionada con
 * con un JOptionPane.showMessageDialog
 * @author Eduardo
 */
class MyFrameClass extends JFrame implements ActionListener {

	private JMenuBar mBar;
    private JMenu m1, m2;
    private String menuName1 = "PRINCIPAL";
    private String menuName2 = "SECUNDARIO";
    
    private String[] mItemsList1 = { "Abrir", "Guardar", "Cerrar" };
    private JMenuItem[] mItems1= new JMenuItem[3];
    private String[] mItemsList2 = { "Editar", "Buscar", "Reemplazar" };
    private JMenuItem[] mItems2= new JMenuItem[3];
    
    public MyFrameClass(String text) {
        super(text);

        mBar = new JMenuBar();
        m1 = new JMenu(menuName1);
        m2 = new JMenu(menuName2);
        for (int i=0; i<mItems1.length; i++) { 
        	mItems1[i] = new JMenuItem( mItemsList1[i] );
        	mItems1[i].addActionListener(this);
        	m1.add( mItems1[i] ); 
        }
        for (int i=0; i<mItems2.length; i++) { 
        	mItems2[i] = new JMenuItem( mItemsList2[i] );
        	mItems2[i].addActionListener(this);
        	m2.add( mItems2[i] ); 
        }

        mBar.add(m1);
        mBar.add(m2);
        this.add(mBar,  BorderLayout.NORTH);

    }

    
    public void actionPerformed(ActionEvent e) {
    	String msg = "Unknown";
        for (int i=0; i<mItems1.length; i++) { 
        	if (e.getSource()==mItems1[i]) {
        		msg = menuName1 + " -> " + mItemsList1[i];
        		break;
        	}
        }
        for (int i=0; i<mItems2.length; i++) { 
        	if (e.getSource()==mItems2[i]) {
        		msg = menuName2 + " -> " + mItemsList2[i];
        		break;
        	}
        }
        JOptionPane.showMessageDialog(null, msg);
    }
   
}

public class BorderLayoutSimpleMenuBar {
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
                                        new MyFrameClass("Ventana con Menu");


                                frame.setSize(APP_WIDTH, APP_HEIGHT);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                frame.setVisible(true);

                        }
                };
                SwingUtilities.invokeLater(runner);
        }
}

