package ar.madlan.gestor.pedidos.vista;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class PedidosController implements Initializable, MixinController {
	
	@FXML
	private AnchorPane main;
	@FXML
	private TableView<FilaPedido> tabla;
	@FXML
	private TableColumn<FilaPedido, String> columnaCliente;
	@FXML
	private TableColumn<FilaPedido, String> columnaFechaLimite;
	@FXML
	private TableColumn<FilaPedido, String> columnaDetalle;
	@FXML
	private TableColumn<FilaPedido, Number> columnaMonto;
	@FXML
	private TableColumn<FilaPedido, Number> columnaPagado;
	@FXML
	private TableColumn<FilaPedido, String> columnaProceso;
	@FXML
	private TableColumn<FilaPedido, Boolean> columnaEntregado;
	@FXML
	private TableColumn<FilaPedido, Boolean> columnaPago;
	@FXML
	private TableColumn<FilaPedido, String> columnaAcciones;
	
	private static final String RUTA_FXML = "pedidos.fxml";

	public void initialize(URL location, ResourceBundle resources) {
		columnaCliente.setCellValueFactory(data -> data.getValue().getCliente());
		columnaFechaLimite.setCellValueFactory(data -> data.getValue().getFechaLimite());
		columnaMonto.setCellValueFactory(data -> data.getValue().getMonto());
		columnaPagado.setCellValueFactory(data -> data.getValue().getPagado());
		columnaProceso.setCellValueFactory(data -> data.getValue().getProceso());
		columnaEntregado.setCellValueFactory(data -> data.getValue().getEntregado());
		columnaPago.setCellValueFactory(data -> data.getValue().getPago());
		
		columnaDetalle.setCellFactory(tc -> FilaPedido.getTableCellDetalle());
		columnaAcciones.setCellFactory(tc -> FilaPedido.getTableCellAcciones());
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return main;
	}

	public ObservableList<FilaPedido> getPedidos() {
		return tabla.getItems();
	}
}
