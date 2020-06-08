package exceptions;

import javax.swing.JFrame;

import componentes.IconDialog;

/**
 * Esta clase representa una excepción que puede representar gráficamente con un
 * dialogo
 * <p>
 * Esta clase sirve de base para las demás excepciones que hemos implementado ya
 * que todas<br>
 * ellas deben de poder mostrarse en un dialogo. <br>
 * <p>
 * En el dialogo que aparece se muestran el mensaje de la excepción como título
 * <br>
 * y una descripción en el cuerpo del diálogo.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public class VisualException extends Exception {
    private static final long serialVersionUID = -9022008025323040599L;

    /* Descripción de la excepción */
    private String description;

    /**
     * Crea una nueva excepción con los parámetros dados
     * 
     * @param tit  Título de la excepción
     * @param desc Descripción de la excepción
     */
    public VisualException(String tit, String desc) {
	super(tit);
	description = desc;
    }

    /**
     * Crea una nueva <code>VisualException</code> que envuelve a la excepción dada
     * <p>
     * La excepción creada tendrá como título el nombre de <code>ex</code> y como
     * descripción el mensaje de <code>ex</code>
     * 
     * @param ex Excepción de la cual se toman los datos
     */
    public VisualException(Exception ex) {
	super(ex.getClass().getName());
	description = ex.getMessage();
    }

    public void showDialog(JFrame jf) {
	IconDialog.errorDialog(jf, getMessage(), description);
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
}
