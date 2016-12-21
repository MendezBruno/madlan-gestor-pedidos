package ar.madlan.gestor.pedidos.main;

import ar.madlan.gestor.pedidos.vista.PedidosController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(PedidosController.class.getResource("pedidos.fxml"));
		fxmlLoader.setController(new PedidosController());
		primaryStage.setScene(new Scene((Parent) fxmlLoader.load()));
		primaryStage.show();
	}

}
