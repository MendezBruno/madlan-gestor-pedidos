package ar.madlan.gestor.pedidos.main;

import ar.madlan.gestor.pedidos.vista.PedidosController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		PedidosController controller = new PedidosController();
		controller.cargar(primaryStage);
		primaryStage.show();
	}

}
