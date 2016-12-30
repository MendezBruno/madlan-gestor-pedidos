package ar.madlan.gestor.pedidos.main;

import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.vista.PedidosController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Modelo modelo = new Modelo();
		PedidosController controller = new PedidosController(modelo);
		controller.cargar(primaryStage);
		primaryStage.show();
	}

}
