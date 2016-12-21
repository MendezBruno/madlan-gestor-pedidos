package ar.madlan.gestor.pedidos.vista;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PedidosController implements Initializable {
	@FXML
	private Label lblTitulo;

	public void initialize(URL location, ResourceBundle resources) {
		lblTitulo.setText("Titulo");
	}
}
