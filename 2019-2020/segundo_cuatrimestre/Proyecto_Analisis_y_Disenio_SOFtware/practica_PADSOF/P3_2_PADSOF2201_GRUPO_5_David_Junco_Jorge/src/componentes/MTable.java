package componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Esta clase define una tabla con algunas modificaciones
 * <p>
 * En particular:
 * <ul>
 * <li>Las celdas pueden tener más de una linea de texto
 * <li>Las columnas tienen una anchura objetivo que intentan conseguir y que no
 * superan
 * </ul>
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class MTable extends JTable implements ComponentListener {
    private static final long serialVersionUID = 3188412539703316209L;
    private CustomCellRenderer renderer;
    private List<String> head;
    private Map<Integer, Integer> targetWidths;

    /**
     * Crea una nueva tabla con las cabeceras indicadas
     * 
     * @param head Lista con los titulos de las cabeceras
     */
    public MTable(List<String> head) {
	super(0, head.size());
	this.head = head;
	targetWidths = new HashMap<>();
	renderer = new CustomCellRenderer();

	this.setBackground(Color.white);
	this.setShowGrid(false);
	this.getTableHeader().setDefaultRenderer(new CustomCellRenderer());
	this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	this.addComponentListener(this);
    }

    /* Las celdas de esta tabla nunca son editables */
    @Override
    public boolean isCellEditable(int row, int column) {
	return false;
    }

    /* Todas las celdas tienen el renderer que hemos definido */
    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
	return renderer;
    }

    /**
     * Establece los datos que aparecen en la tabla
     * <p>
     * Además, vuelve a fijar los tamaños buscados en las columnas
     * 
     * @param ls Listas de los datos a mostrar
     */
    @SafeVarargs
    public final void setData(List<String>... ls) {
	this.setModel(new CustomTableModel(head, ls));

	for (Integer i : targetWidths.keySet()) {
	    if (this.getColumnCount() > i) {
		this.getColumnModel().getColumn(i).setMaxWidth(targetWidths.get(i));
		this.getColumnModel().getColumn(i).setPreferredWidth(targetWidths.get(i));
	    }
	}

    }

    /**
     * Establece la anchura objetivo de la columna indicada
     * <p>
     * A partir de este momento, la columna indicada intentará tener la anchura
     * definida.<br>
     * Sin embargo, no se excederá esta anchura
     * 
     * @param ncol   Número de la columna cuyo tamaño se establece
     * @param maxwid Anchura máxima y esperada de la columna
     */
    public void setColumnTargetWidth(int ncol, int maxwid) {
	targetWidths.put(ncol, maxwid);
	if (this.getColumnCount() > ncol) {
	    this.getColumnModel().getColumn(ncol).setMaxWidth(maxwid);
	    this.getColumnModel().getColumn(ncol).setPreferredWidth(maxwid);
	}
    }

    @Override
    public void componentResized(ComponentEvent e) {

	/* Recalculamos la altura de las filas de la tabla */

	for (int row = 0; row < this.getRowCount(); row++) {
	    int rowHeight = 0;

	    for (int column = 0; column < this.getColumnCount(); column++) {
		Component comp = this.prepareRenderer(this.getCellRenderer(row, column), row, column);
		rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
	    }

	    this.setRowHeight(row, rowHeight);
	}

    }

    @Override
    public void componentShown(ComponentEvent e) {
	componentResized(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}

/**
 * Implementación del modelo de tabla
 * <p>
 * Esta implementación nos permite definir los datos de la cabecera por
 * separado,<br>
 * así como establecer los datos sin tener que cambiar la estructura de datos
 * que los almacena
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 *
 */
class CustomTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -3478479173119553740L;
    List<String>[] data;
    List<String> head;

    /**
     * Crea un nuevo modelo de tabla con los parámetros dados
     * 
     * @param head Lista de los titulos de las cabeceras
     * @param data Varias listas, cada una con los datos de una columna
     */
    @SafeVarargs
    public CustomTableModel(List<String> head, List<String>... data) {
	super();
	this.head = head;
	this.data = data;
    }

    @Override
    public int getRowCount() {
	return data[0].size();
    }

    @Override
    public int getColumnCount() {
	return data.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	return data[columnIndex].get(rowIndex);
    }

    @Override
    public String getColumnName(int column) {
	return head.get(column);
    }

}

/**
 * Implementación de <code> TableCellRenderer </code>
 * <p>
 * Esta implementeción del código que renderiza la tabla <br>
 * es la que nos permite tener celdas con más de una linea de texto.
 * <p>
 * Se basa en el uso de una JLabel en vez de una JTextAre como el componente
 * <br>
 * que forma cada celda
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 *
 */
class CustomCellRenderer implements TableCellRenderer {
    private static final Color SELECTED_BACKGROUND = new Color(60, 150, 255, 80);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	    int row, int column) {
	if (row >= table.getRowCount()) return null;
	if (column >= table.getColumnCount()) return null;
	if (value == null) {
	    return null;
	}

	JTextArea jt = new JTextArea(value.toString());

	jt.setLineWrap(true);
	jt.setWrapStyleWord(true);
	jt.setSize(table.getColumnModel().getColumn(column).getWidth(), jt.getPreferredSize().height);
	jt.setBackground(new Color(0, 0, 0, 0));
	if (row == -1) {
	    jt.setBackground(new Color(220, 220, 220));

	}

	if (isSelected || hasFocus) {
	    jt.setBackground(SELECTED_BACKGROUND);
	}

	if (row != -1) {
	    Border b = new BevelBorder(BevelBorder.RAISED, jt.getBackground(), jt.getBackground().darker().darker());
	    b = new CompoundBorder(b, BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    jt.setBorder(b);
	} else {
	    jt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	jt.setEditable(false);
	return jt;
    }

}