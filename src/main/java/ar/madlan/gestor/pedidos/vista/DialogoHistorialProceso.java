package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;

import ar.madlan.gestor.pedidos.modelo.Pedido;
import ar.madlan.gestor.pedidos.modelo.Proceso;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class DialogoHistorialProceso implements MixinController {

	private static final String RUTA_FXML = "procesos.fxml";

	@FXML
	private VBox main;
	@FXML
	private TableView<FilaProceso> tabla;
	@FXML
	private TableColumn<FilaProceso, String> columnaFecha;
	@FXML
	private TableColumn<FilaProceso, String> columnaDescripcion;

	private Pedido pedido;

	private Dialog<Proceso> dialogo;


	public DialogoHistorialProceso(Pedido pedido) {
		this.pedido = pedido;
		try {
			cargar();
			dialogo = new Dialog<Proceso>();
			dialogo.getDialogPane().setContent(main);
			dialogo.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		columnaFecha.setCellValueFactory(data -> data.getValue().getFecha());
		columnaDescripcion.setCellValueFactory(data -> data.getValue().getDescripcion());
		columnaDescripcion.setCellFactory(columna -> FilaProceso.getTableCellDescripcion());

		for (Proceso proceso : pedido.getProcesos()) {
			tabla.getItems().add(new FilaProceso(proceso));
		}
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return null;
	}

	public Dialog<Proceso> getDialogo() {
		return dialogo;
	}
}
