package modelo.aplicacion;

import java.io.Serializable;

/**
 * Clase base para los usuarios de la aplicación
 * <p>
 * En esta clase se implementan las funcionalidades básicas de validación de
 * credenciales y de comparación de usuarios. <br>
 * Los usuarios solo tienen dos atributos: nombre de usuario y contraseña.
 * 
 * @author David del Val
 * @author Junco de las Heras
 * @author Jorge Fernandez
 *
 * @version 28-03-20
 *
 */
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 9034447309973429079L;
    private String usr;
    private String password;

    /**
     * Crea un nuevo usuario con los datos dados
     * 
     * @param usr      Nombre de usuario
     * @param password Contraseña
     */
    public Usuario(String usr, String password) {
	this.usr = usr;
	this.password = password;
    }

    /**
     * Comprueba la validez de los credenciales dados
     * 
     * @param usr Usuario dado
     * @param pwd Contraseña dada
     * @return <code> true </code> si los credenciales son validos <br>
     *         <code> false </code> si los credenciales no los son
     */
    public abstract boolean validarCredenciales(String usr, String pwd);

    /**
     * Devuelve el nombre de usuario
     * 
     * @return El nombre de usuario
     */
    public String getUsr() {
	return usr;
    }

    /**
     * Devuelve la contraseña
     * 
     * @return La contraseña
     */
    public String getPassword() {
	return password;
    }

    /**
     * Establece el nombre de usuario
     * 
     * @param usr Nuevo nombre de usuario
     */
    public void setUsr(String usr) {
	this.usr = usr;
    }

    /**
     * Establece la contraseña
     * 
     * @param password Nueva contraseña
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * Calcula el hash de este usuario
     * <p>
     * La inclusion de este método permite el almacenamiento de usuarios <br>
     * en estructuras de datos como los <code> HashMap </code>
     * 
     * @return El hash del usuario
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((usr == null) ? 0 : usr.hashCode());
	return result;
    }

    /**
     * Comprueba si dos objetos son iguales
     * <p>
     * Si el objeto dado no es un usuario siempre devuelve <code> false </code> <br>
     * Si <code> obj </code> es un usuario, se considera que son iguales si tienen
     * el mismo nombre de usuario
     * 
     * @param obj Objeto a comparar
     * @return <code> true </code> si son iguales <br>
     *         <code> false </code> si son diferentes
     */
    @Override
    public boolean equals(Object obj) {
	if (obj == null || getClass() != obj.getClass()) return false;

	Usuario other = (Usuario) obj;
	if (usr == null && other.usr != null) return false;
	if (usr != null && !usr.equals(other.usr)) return false;

	return true;
    }

    /**
     * Devuelve la representación como String de este usuario
     * <p>
     * En el String aparecen el nombre de usuario y la contraseña
     * 
     * @return Representación como String del usuario
     */
    @Override
    public String toString() {
	return "Usuario:\t" + usr + "\n" + "Contraseña:\t" + password;
    }

}
