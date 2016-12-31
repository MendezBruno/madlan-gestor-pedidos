package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PedidosController implements Initializable, MixinController {

	@FXML
	private AnchorPane main;
	@FXML
	private TableView<FilaPedido> tabla;
	@FXML
	private TableColumn<FilaPedido, String> columnaCliente;
	@FXML
	private TableColumn<FilaPedido, LocalDate> columnaFechaLimite;
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
	@FXML
	private MenuItem menuBtnClientes;
	@FXML
	private MenuItem menuBtnNuevoPedido;

	private static final String RUTA_FXML = "pedidos.fxml";
	public static Stage stage;
	private Modelo modelo;

	public PedidosController(Modelo modelo) {
		this.modelo = modelo;
	}

	public void initialize(URL location, ResourceBundle resources) {
		columnaCliente.setCellValueFactory(data -> data.getValue().getCliente());
		columnaFechaLimite.setCellValueFactory(data -> data.getValue().getFechaLimite());
		columnaMonto.setCellValueFactory(data -> data.getValue().getMonto());
		columnaPagado.setCellValueFactory(data -> data.getValue().getPagado());
		columnaProceso.setCellValueFactory(data -> data.getValue().getProceso());
		columnaEntregado.setCellValueFactory(data -> data.getValue().getEntregado());
		columnaPago.setCellValueFactory(data -> data.getValue().getPago());

		columnaFechaLimite.setCellFactory(tc -> FilaPedido.getTableCellFechaLimite());
		columnaDetalle.setCellFactory(tc -> FilaPedido.getTableCellDetalle());
		columnaEntregado.setCellFactory(CheckBoxTableCell.forTableColumn(columnaEntregado));
		columnaPago.setCellFactory(CheckBoxTableCell.forTableColumn(columnaPago));
		columnaAcciones.setCellFactory(tc -> FilaPedido.getTableCellAcciones());
		tabla.setRowFactory(tabla -> FilaPedido.getTableRow(tabla));

		setPedidos();

		menuBtnClientes.setOnAction(e -> onClientes());
		menuBtnNuevoPedido.setOnAction(e -> onNuevoPedido());
	}
	private void setPedidos() {
		tabla.getItems().clear();
		for (Pedido pedido : modelo.getData().getPedidos()) {
			tabla.getItems().add(new FilaPedido(pedido, modelo, this));
		}
	}

	private void onNuevoPedido() {
		Pedido pedido = new Pedido();
		pedido.setFechaIngreso(Instant.now());
		DialogoAltaPedidoController controller = new DialogoAltaPedidoController(pedido, modelo);
		Optional<Pedido> optional = controller.getDialogo().showAndWait();
		optional.ifPresent(p -> {
			try {
				modelo.getData().getPedidos().add(p);
				setPedidos();
				modelo.persistir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void onClientes() {
		new DialogoClientesController(modelo).getDialogo().showAndWait();
	}

	@Override
	public void cargar(Stage stage) throws IOException {
		MixinController.super.cargar(stage);
		PedidosController.stage = stage;
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

	public void actualizar() {
		ArrayList<FilaPedido> lista = new ArrayList<>(tabla.getItems());
		tabla.getItems().setAll(lista);
	}
}
