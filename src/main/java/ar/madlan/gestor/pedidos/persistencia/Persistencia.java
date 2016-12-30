package ar.madlan.gestor.pedidos.persistencia;

public interface Persistencia {
	public DataModelo recuperar(String ruta) throws Exception;
	public void persistir(String ruta, DataModelo data) throws Exception;
}
