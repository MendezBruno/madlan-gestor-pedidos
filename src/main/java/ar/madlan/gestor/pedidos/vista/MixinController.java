package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface MixinController {
	
	String getFxml();
	
	default public void cargar(Stage stage) throws IOException {
		cargar();
		stage.setScene(new Scene(getNode()));
		onCargar(stage);
	}
	
	default void onCargar(Stage stage) {
		
	}

	default public void cargar() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(MixinController.class.getResource(getFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}
	
	public Parent getNode();
}
