package ar.madlan.gestor.pedidos.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Dialogos {

	public static void advertir(String titulo, String header, String texto) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(texto);
		alert.showAndWait();
	}

	public static boolean preguntar(String titulo, String header, String texto) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(texto);
		return alert.showAndWait().get() == ButtonType.OK;
	}
}
