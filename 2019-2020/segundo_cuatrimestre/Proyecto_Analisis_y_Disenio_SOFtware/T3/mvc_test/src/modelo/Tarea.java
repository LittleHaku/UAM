package modelo;

public class Tarea { 
	
	private String nombre;
	
	public Tarea (String nombre) { 
		this.nombre = nombre;
	}
	
	public String getNombre() { 
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}