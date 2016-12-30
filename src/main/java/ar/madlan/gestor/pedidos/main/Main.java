package ar.madlan.gestor.pedidos.main;

import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.persistencia.Persistencia;
import ar.madlan.gestor.pedidos.persistencia.PersistenciaXML;
import ar.madlan.gestor.pedidos.vista.PedidosController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Persistencia persistencia = new PersistenciaXML();
		Modelo modelo = new Modelo(persistencia);
		PedidosController controller = new PedidosController(modelo);
		controller.cargar(primaryStage);
		primaryStage.show();
	}

}
