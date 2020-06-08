import es.uam.eps.sadp.grants.*;
public class Ejemplo {
	public static void main(String[] args) throws Exception {
		CCGG proxy = CCGG.getGateway();
		Proyecto proyecto = new Proyecto();
		proxy.setDate(FechaSimulada.getHoy()); // 2017-03-01
		proyecto.enviar();
		FechaSimulada.avanzar(30); // 2017-03-31
		proxy.setDate(FechaSimulada.getHoy());
		proyecto.consultar();
		System.out.println("Resultado:" + proyecto.cantidadConcedida);
	}
}
class Proyecto {
	String idEnvio;
	String titulo = "mi proyecto";
	double cantidadConcedida;
	void enviar () throws Exception {
		GrantRequest req = new SolicitudFinanciacion(this);
		CCGG proxy = CCGG.getGateway();
		String id = proxy.submitRequest(req);
		System.out.println("Valor:" + id);
		this.idEnvio=id;
	}
	void consultar () throws Exception {
		CCGG proxy = CCGG.getGateway();
		cantidadConcedida = proxy.getAmountGranted(this.idEnvio);
	}
}
class SolicitudFinanciacion implements GrantRequest {
	Proyecto p; 
	SolicitudFinanciacion(Proyecto p) {this.p=p;}
	public String getExtraData() {
		return "extraData";
	}
	public String getProjectDescription() {
		return "descripcion";
	}
	public ProjectKind getProjectKind() {
		return GrantRequest.ProjectKind.valueOf("Social");
	}
	public String getProjectTitle() {
		return p.titulo;
	}
	public double getRequestedAmount() {
		return 1000;
	}
}
