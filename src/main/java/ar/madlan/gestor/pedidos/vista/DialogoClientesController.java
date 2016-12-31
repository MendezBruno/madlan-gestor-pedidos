package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import ar.madlan.gestor.pedidos.modelo.Cliente;
import ar.madlan.gestor.pedidos.modelo.Modelo;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class DialogoClientesController implements MixinController{

	private static final String RUTA_FXML = "clientes.fxml";

	@FXML
	private VBox main;
	@FXML
	private TableView<FilaCliente> tabla;
	@FXML
	private TableColumn<FilaCliente, String> columnaNombre;
	@FXML
	private TableColumn<FilaCliente, String> columnaApellido;
	@FXML
	private Button btnNuevo;

	private Dialog<?> dialogo;
	private ArrayList<Cliente> clientes;

	private Modelo modelo;

	public DialogoClientesController(Modelo modelo) {
		this.modelo = modelo;
		this.clientes = modelo.getData().getClientes();
		try {
			cargar();
			dialogo = new Dialog<Object>();
			dialogo.getDialogPane().setContent(main);
			dialogo.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

			//Esto es para que no muestre el boton cerrar por debajo
			Node closeButton = dialogo.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);

			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		columnaApellido.setCellValueFactory(data -> data.getValue().getApellido());
		columnaNombre.setCellValueFactory(data -> data.getValue().getNombre());
		tabla.setRowFactory(tabla -> FilaCliente.getRowFactory());

		for (Cliente cliente : clientes) {
			tabla.getItems().add(new FilaCliente(cliente, modelo));
		}

		btnNuevo.setOnAction(e -> onNuevo());
	}

	private void onNuevo() {
		Cliente cliente = new Cliente();
		DialogoClienteController controller = new DialogoClienteController(cliente);
		Optional<Cliente> optional = controller.getDialogo().showAndWait();
		optional.ifPresent(i -> {
			tabla.getItems().add(new FilaCliente(i, modelo));
			clientes.add(i);
			try {
				modelo.persistir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return null;
	}

	public Dialog<?> getDialogo() {
		return dialogo;
	}
}
