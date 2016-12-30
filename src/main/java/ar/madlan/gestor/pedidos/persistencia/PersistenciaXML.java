package ar.madlan.gestor.pedidos.persistencia;

import java.io.File;
import java.io.FileWriter;

import com.thoughtworks.xstream.XStream;

public class PersistenciaXML implements Persistencia{

	private XStream xstream = new XStream();

	@Override
	public DataModelo recuperar(String ruta) throws Exception {
		return (DataModelo) xstream.fromXML(new File(ruta));
	}

	@Override
	public void persistir(String path, DataModelo data) throws Exception {
		if (!path.contains(".xml")) {
			path+=".xml";
		}
		FileWriter out = new FileWriter(path);
		XStream xStream = new XStream();
		xStream.toXML(this, out);
		out.close();
	}
}
