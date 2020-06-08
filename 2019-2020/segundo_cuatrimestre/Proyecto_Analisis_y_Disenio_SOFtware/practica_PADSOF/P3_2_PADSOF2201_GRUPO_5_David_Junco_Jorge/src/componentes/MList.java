package componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

/**
 * Lista de <code> RadioButtons </code>
 * <p>
 * Esta lista permite la selección de un elemento. <br>
 * Pero a diferencia de la lista estadard, el elemento seleccionado se muestra
 * <br>
 * con un <code> RadioButton </code> que estará seleccionado si el elemento
 * correspondiente lo está.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class MList extends JList<String> {
    private static final long serialVersionUID = -5415991054872730754L;

    /**
     * Crea una nueva lista con el ListModel dado
     * 
     * @param dlm Modelo de datos para la lista
     */
    public MList(ListModel<String> dlm) {
	super(dlm);
	CustomJListRenderer ls = new CustomJListRenderer();
	this.setCellRenderer(ls);
    }
}

/**
 * 
 * Renderer para las celdas de la tabla
 * <p>
 * Es el responsable de dibujar los componentes que forman la lista
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 *
 */
class CustomJListRenderer implements ListCellRenderer<String> {
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
	    boolean isSelected, boolean cellHasFocus) {
	JRadioButton jr = new JRadioButton(value);
	jr.setSelected(isSelected);
	jr.setOpaque(false);
	jr.setBackground(new Color(0, 0, 0, 0));
	jr.setMargin(new Insets(5, 5, 10, 10));
	if (!list.isEnabled()) jr.setForeground(Color.GRAY);
	return jr;
    }
}
