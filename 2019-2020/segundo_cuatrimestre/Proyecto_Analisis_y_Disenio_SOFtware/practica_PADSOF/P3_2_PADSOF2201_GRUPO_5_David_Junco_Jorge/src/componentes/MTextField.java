package componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextField;

/**
 * Campo de texto que dibuja un texto para indicar al usuario el valor a
 * introducir
 * <p>
 * La mayor diferencia entre el campo de texto estandard y esta version es la
 * inclusión <br>
 * de un texto que se dibuja sobre el campo cuando no hay nada escrito en él.
 * <br>
 * Este texto puede servir como indicación para el usuario, a fin de que este
 * sepa <br>
 * cuál es el valor que debe de introducir en el campo
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class MTextField extends JTextField {
    private static final long serialVersionUID = -7589927204668961874L;

    /* Texto de indicación */
    private String prompText;

    /* Color del texto de indicación */
    private final Color promptColor = Color.lightGray;

    /**
     * Crea un nuevo campo de texto con el texto de indicación dado
     * 
     * @param promptText Texto de indicación
     */
    public MTextField(String promptText) {
	super();
	this.prompText = promptText;
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	/*
	 * Pintamos el texto una vez se ha dibujado el componente para que este aparezca
	 * sobre el componente.
	 */
	if (this.getText() == null || this.getText().equals("")) {
	    Color c = g.getColor();
	    g.setColor(promptColor);

	    // Calculamos la posición vertical del texto
	    Insets margin = this.getMargin();
	    Insets borderMargin = this.getBorder().getBorderInsets(this);
	    int xcoord = margin.left + borderMargin.left;
	    int ycoord = getHeight() - margin.bottom - borderMargin.bottom;
	    ycoord -= g.getFontMetrics().getDescent();

	    g.drawString(prompText, xcoord, ycoord);

	    g.setColor(c);
	}
    }
}
