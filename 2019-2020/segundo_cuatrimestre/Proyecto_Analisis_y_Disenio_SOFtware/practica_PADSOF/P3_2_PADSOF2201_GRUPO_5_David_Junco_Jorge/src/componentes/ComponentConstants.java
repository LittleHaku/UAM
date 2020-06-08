package componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
/**
 * Interfaz que contiene las constantes usadas por varios componentes gráficos de <br>
 * la aplicación
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 26-04-20
 */
public interface ComponentConstants {
    /* Fuente para el título */
    public static final Font   F_TITLE		   = new Font("Helvetica", Font.BOLD, 35);
    /* Fuente para el encabezado */
    public static final Font   F_HEADING	   = new Font("Helvetica", Font.BOLD, 20);
    /* Fuente para el sub-encabezado */
    public static final Font   F_SUB_HEAD	   = new Font("Helvetica", Font.BOLD, 16);
    /* Fuente para el sub-encabezado */
    public static final Font   F_SUB_SUB_HEAD	   = new Font("Helvetica", Font.BOLD, 13);
    /* Fuente pequeña */
    public static final Font   F_FOOTNOTE	   = new Font("Helvetica", Font.PLAIN, 12);
    
    /* Fuente para el título sin negrita */
    public static final Font   F_TEXT_TITLE	   = new Font("Helvetica", Font.PLAIN, 35);
    /* Fuente para el encabezado sin negrita */
    public static final Font   F_TEXT_HEADING	   = new Font("Helvetica", Font.PLAIN, 20);
    /* Fuente para el sub-encabezado sin negrita */
    public static final Font   F_TEXT_SUB_HEAD	   = new Font("Helvetica", Font.PLAIN, 16);
    /* Fuente para el sub-encabezado sin negrita */
    public static final Font   F_TEXT_SUB_SUB_HEAD = new Font("Helvetica", Font.PLAIN, 13);
    
    /* Icono de flecha de volver */
    public static final String I_ARROW		   = "icons/arrow_s.png";
    /* Icono de imagen por defecto para proyecto */
    public static final String I_IMAGE		   = "icons/default.png";
    /* Icono de salir */
    public static final String I_DOOR		   = "icons/puerta2_s.png";
    /* Icono de la aplicación */
    public static final String I_LOGO		   = "icons/logo_ss.png";
    /* Icono de error */
    public static final String I_ERROR		   = "icons/error.png";
    /* Icono de confirmación */
    public static final String I_SUCCESS	   = "icons/success.png";
    /* Icono de advertencia */
    public static final String I_WARNING	   = "icons/warning.png";

    /* Color de fondo para diálogos */
    public static final Color  C_DIALOG		   = new Color(240, 240, 240);
    /* Color de descripción para diálogos */
    public static final Color  C_FORE_DIALOG	   = new Color(60, 60, 60);
    /* Border para los diálogos */
    public static final Border C_BORDER		   = BorderFactory.createRaisedBevelBorder();
    
    
    /* Color de fondo para las vistas */
    public static final Color  C_VISTA 		   = new Color(0xF5F5F5);	
}
