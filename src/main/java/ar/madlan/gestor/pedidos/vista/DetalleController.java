package ar.madlan.gestor.pedidos.vista;

import java.net.URL;
import java.util.ResourceBundle;

import ar.madlan.gestor.pedidos.modelo.Pedido;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DetalleController implements Initializable, MixinController {
	
	@FXML
	private AnchorPane main;

	private Pedido pedido;
	
	private static final String RUTA_FXML = "detalle.fxml";
	
	public DetalleController(Pedido pedido) {
		this.pedido = pedido;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Label label = new Label("Soy el detalle del pedido " + pedido.getNumero());
		main.getChildren().add(label);
	}
	
	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return main;
	}
}
