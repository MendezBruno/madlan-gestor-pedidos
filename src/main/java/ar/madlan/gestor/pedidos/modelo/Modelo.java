package ar.madlan.gestor.pedidos.modelo;

import ar.madlan.gestor.pedidos.persistencia.DataModelo;
import ar.madlan.gestor.pedidos.persistencia.Persistencia;

public class Modelo {
	private static final String RUTA_PERSISTENCIA = "data.xml";
	private Persistencia persistencia;
	private DataModelo data;

	public Modelo(Persistencia persistencia) {
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
