package ar.madlan.gestor.pedidos.modelo;

public class Modelo {
	private static final String RUTA_PERSISTENCIA = "data.xml";
	private Persistencia persistencia;
	private DataModelo data;

	public Modelo() {
		try {
			data = persistencia.recuperar(RUTA_PERSISTENCIA);
		} catch (Exception e) {
			data = new DataModelo();
		}
	}
	
	public void persistir() throws Exception {
		persistencia.persistir(RUTA_PERSISTENCIA, data);
	}
	
	public DataModelo getData() {
		return data;
	}
}
