package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ar.madlan.gestor.pedidos.modelo.Cliente;
import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import ar.madlan.gestor.pedidos.util.Fecha;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class DialogoAltaPedidoController implements MixinController {

	@FXML
	private GridPane main;
	@FXML
	private ChoiceBox<Cliente> comboCliente;
	@FXML
	private DatePicker txtFechaIngreso;
	@FXML
	private DatePicker txtFechaEntrega;
	@FXML
	private Button btnNuevo;

	private static final String RUTA_FXML = "dialogoAltaPedido.fxml";
	private Pedido pedido;
	private Dialog<Pedido> dialogo;
	private ButtonType btnCrearType = new ButtonType("Crear", ButtonData.OK_DONE);
	private Node btnCrear;
	private Modelo modelo;

	public DialogoAltaPedidoController(Pedido pedido, Modelo modelo) {
		this.pedido = pedido;
		this.modelo = modelo;
		try {
			cargar();
			dialogo = new Dialog<Pedido>();
			dialogo.getDialogPane().setContent(main);
			dialogo.getDialogPane().getButtonTypes().addAll(btnCrearType, ButtonType.CANCEL);
			btnCrear = dialogo.getDialogPane().lookupButton(btnCrearType);
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		txtFechaIngreso.setValue(Fecha.toLocalDate(pedido.getFechaIngreso()));
		txtFechaEntrega.getEditor().setText("");
		comboCliente.getItems().addAll(modelo.getData().getClientes());
		txtFechaIngreso.getEditor().textProperty().addListener((obs,o,n) -> validar());
		txtFechaEntrega.getEditor().textProperty().addListener((obs,o,n) -> validar());
		comboCliente.valueProperty().addListener((obs,o,n) -> validar());
		validar();

		comboCliente.setConverter(new StringConverter<Cliente>() {
			@Override
			public String toString(Cliente object) {
				return object.toString();
			}

			@Override
			public Cliente fromString(String string) {
				return null;
			}
		});

		dialogo.setResultConverter(dialogButton -> {
		    if (dialogButton == btnCrearType) {
		    	pedido.setFechaIngreso(Fecha.toInstant(txtFechaIngreso.getValue()));
		    	pedido.setFechaLimite(Fecha.toInstant(txtFechaEntrega.getValue()));
		    	pedido.setCliente(comboCliente.getValue());
		        return pedido;
		    }
		    return null;
		});

		btnNuevo.setOnAction(e -> onNuevo());
	}

	private void onNuevo() {
		new DialogoClienteController(new Cliente())
			.getDialogo()
			.showAndWait()
			.ifPresent(c -> {
				ArrayList<Cliente> clientes = modelo.getData().getClientes();
				clientes.add(c);
				try {
					modelo.persistir();
				} catch (Exception e) {
					e.printStackTrace();
				}
				comboCliente.getItems().setAll(clientes);
				comboCliente.getSelectionModel().select(c);
			});
	}

	private void validar() {
		boolean conError = false;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			formatter.parse(txtFechaIngreso.getEditor().getText());
		} catch (Exception e) {
			conError = true;
		}
		try {
			formatter.parse(txtFechaEntrega.getEditor().getText());
		} catch (Exception e) {
			conError = true;
		}
		if (comboCliente.getValue() == null) {
			conError = true;
		}

		btnCrear.setDisable(conError);
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return null;
	}

	public Dialog<Pedido> getDialogo() {
		return dialogo;
	}
}
