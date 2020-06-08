package complejos;

public class Complejo {
  private float real;
  private float imaginario;

  public Complejo (float real, float imaginario) {
    this.real = real; 
    this.imaginario = imaginario;
  }

  public float getReal(){
    return real;
  }

  public float getImaginario() {
    return imaginario;
  }

  public Complejo sumar(Complejo c) {
    return new Complejo( 
      real + c.getReal(), 
      imaginario + c.getImaginario());
  }
  
  public Complejo producto(Complejo c) {
	    return new Complejo( 
	      real * c.getReal() -imaginario * c.getImaginario(), 
	      real * c.getImaginario() + imaginario * c.getReal() );
  }
  public Complejo conjugado() {
	    return new Complejo( real,  - imaginario );
  }
  
  public Complejo divididoPor(Complejo c) {
	  if ( c.getImaginario() == 0) {
		  if ( c.getReal() == 0) throw new ArithmeticException();
		  return new Complejo( real  / c.getReal(),  imaginario / c.getReal() );
	  }
	  System.out.println( c.producto(c.conjugado()).getReal() + ":" +
			  				c.producto(c.conjugado()).getImaginario() );
	  return this.producto(c.conjugado()).divididoPor( c.producto(c.conjugado()) );
 }
}
