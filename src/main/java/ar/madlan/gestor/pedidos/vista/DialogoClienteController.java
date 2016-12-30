package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;

import ar.madlan.gestor.pedidos.modelo.Cliente;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class DialogoClienteController implements MixinController {

	@FXML
	private GridPane main;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido;

	private static final String RUTA_FXML = "cliente.fxml";
	private Cliente cliente;
	private Dialog<Cliente> dialogo;
	private ButtonType btnGuardarType = new ButtonType("Guardar", ButtonData.OK_DONE);
	private Node btnGuardar;

	public DialogoClienteController(Cliente cliente) {
		this.cliente = cliente;
		try {
			cargar();
			dialogo = new Dialog<Cliente>();
			dialogo.getDialogPane().setContent(main);
			dialogo.getDialogPane().getButtonTypes().addAll(btnGuardarType, ButtonType.CANCEL);
			btnGuardar = dialogo.getDialogPane().lookupButton(btnGuardarType);

			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		txtApellido.setText(cliente.getApellido());
		txtNombre.setText(cliente.getNombre());
		txtApellido.textProperty().addListener((obs, old, n) -> validar());
		txtNombre.textProperty().addListener((obs, old, n) -> validar());
		validar();

		dialogo.setResultConverter(dialogButton -> {
		    if (dialogButton == btnGuardarType) {
		    	cliente.setNombre(txtNombre.getText());
		    	cliente.setApellido(txtApellido.getText());
		        return cliente;
		    }
		    return null;
		});
	}

	private void validar() {
		boolean conError = false;
		if (txtApellido.getText() == null || txtApellido.getText().isEmpty()) {
			conError = true;
		}
		if (txtNombre.getText() == null || txtNombre.getText().isEmpty()) {
			conError = true;
		}
		btnGuardar.setDisable(conError);
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return null;
	}

	public Dialog<Cliente> getDialogo() {
		return dialogo;
	}
}
