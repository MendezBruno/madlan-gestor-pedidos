package ar.madlan.gestor.pedidos.vista;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import ar.madlan.gestor.pedidos.modelo.ItemPedido;
import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.modelo.Pago;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PedidoController implements Initializable, MixinController {
	
	private static final String RUTA_FXML = "pedido.fxml";
	
	@FXML
	private AnchorPane main;
	@FXML
	private TableView<FilaItemPedido> tablaItems;
	@FXML
	private TableColumn<FilaItemPedido, Boolean> columnaItemsSeleccion;
	@FXML
	private TableColumn<FilaItemPedido, Number> columnaItemsCantidad;
	@FXML
	private TableColumn<FilaItemPedido, String> columnaItemsPedido;
	@FXML
	private TableColumn<FilaItemPedido, Number> columnaItemsPrecioUnitario;
	@FXML
	private TableColumn<FilaItemPedido, Number> columnaItemsImporte;
	@FXML
	private CheckBox chkSeleccionItems;
	@FXML
	private Button btnItemsAgregar;
	@FXML
	private Button btnItemsQuitar;
	@FXML
	private TableView<FilaPago> tablaPagos;
	@FXML
	private TableColumn<FilaPago, Boolean> columnaPagosSeleccion;
	@FXML
	private TableColumn<FilaPago, String> columnaPagosFecha;
	@FXML
	private TableColumn<FilaPago, Number> columnaPagosMonto;
	@FXML
	private CheckBox chkSeleccionPagos;
	@FXML
	private Button btnPagosAgregar;
	@FXML
	private Button btnPagosQuitar;
	@FXML
	private TextArea txtareaProceso;
	@FXML
	private DatePicker txtFechaIngreso;
	@FXML
	private DatePicker txtFechaEntrega;
	@FXML
	private CheckBox chkEntregado;
	@FXML
	private Hyperlink cliente;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;

	private Pedido pedido;

	private Stage stage;

	private ArrayList<Pedido> pedidos;
	
	@Override
	public String getFxml() {
		return RUTA_FXML;
	}
	
	@Override
	public Parent getNode() {
		return main;
	}
	
	public PedidoController(Pedido pedido, Modelo modelo) {
		this.pedido = pedido;
		this.pedidos = modelo.getData().getPedidos();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnaItemsSeleccion.setCellFactory(CheckBoxTableCell.forTableColumn(columnaItemsSeleccion));
		columnaItemsSeleccion.setCellValueFactory(data -> data.getValue().getSeleccion());
		columnaItemsCantidad.setCellValueFactory(data -> data.getValue().getCantidad());
		columnaItemsPedido.setCellValueFactory(data -> data.getValue().getDescripcion());
		columnaItemsPrecioUnitario.setCellValueFactory(data -> data.getValue().getPrecioUnitario());
		columnaItemsImporte.setCellValueFactory(data -> data.getValue().getImporte());
		tablaItems.setRowFactory(tabla -> FilaItemPedido.getRowFactory());
		columnaPagosSeleccion.setCellFactory(CheckBoxTableCell.forTableColumn(columnaPagosSeleccion));
		columnaPagosSeleccion.setCellValueFactory(data -> data.getValue().getSeleccion());
		columnaPagosFecha.setCellValueFactory(data -> data.getValue().getFecha());
		columnaPagosMonto.setCellValueFactory(data -> data.getValue().getMonto());
		tablaPagos.setRowFactory(tabla -> FilaPago.getRowFactory());
		chkSeleccionItems.selectedProperty().addListener((obs, o, n) -> {
			tablaItems.getItems().forEach(i -> i.getSeleccion().set(n));
		});
		chkSeleccionPagos.selectedProperty().addListener((obs, o, n) -> {
			tablaPagos.getItems().forEach(i -> i.getSeleccion().set(n));
		});
		for (ItemPedido itemPedido : pedido.getItems()) {
			tablaItems.getItems().add(new FilaItemPedido(itemPedido));
		}
		for (Pago pago : pedido.getPagos()) {
			tablaPagos.getItems().add(new FilaPago(pago));
		}
		txtareaProceso.setText(pedido.getUltimoProceso().getDescripcion());
		txtFechaIngreso.setValue(pedido.getFechaIngreso().atZone(ZoneId.systemDefault()).toLocalDate());
		txtFechaEntrega.setValue(pedido.getFechaLimite().atZone(ZoneId.systemDefault()).toLocalDate());
		chkEntregado.setSelected(pedido.entregado());
		cliente.setText(pedido.getCliente().getNombre());
		btnItemsAgregar.setOnAction(e -> onItemAgregar());
		btnItemsQuitar.setOnAction(e -> onItemQuitar());
		btnPagosAgregar.setOnAction(e -> onPagoAgregar());
		btnPagosQuitar.setOnAction(e -> onPagoQuitar());
		btnCancelar.setOnAction(e -> onCancelar());
		btnGuardar.setOnAction(e -> onGuardar());
	}

	private void onCancelar() {
		stage.close();
	}
	
	private void onPagoQuitar() {
		List<FilaPago> seleccionados = tablaPagos.getItems().stream()
				.filter(f -> f.getSeleccion().get())
				.collect(Collectors.toList());
		if (seleccionados.isEmpty()) {
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quitar pago");
		alert.setHeaderText("Confirmar baja de items");
		alert.setContentText("¿Esta seguro de proceder? "
				+ "La operación es reversible si presiona Cancelar");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    for (FilaPago filaPago : seleccionados) {
				pedido.getItems().remove(filaPago.getPago());
				tablaItems.getItems().remove(filaPago);
			}
		}
	}

	private void onPagoAgregar() {
		Pago pago = new Pago();
		pago.setFecha(Instant.now());
		DialogoPagoController controller = new DialogoPagoController(pago);
		Optional<Pago> optional = controller.getDialogo().showAndWait();
		optional.ifPresent(i -> {
			tablaPagos.getItems().add(new FilaPago(i));
			pedido.getPagos().add(i);
		});
	}

	@Override
	public void onCargar(Stage stage) {
		MixinController.super.onCargar(stage);
		this.stage = stage;
		stage.setMinWidth(main.getPrefWidth());
		stage.setMinHeight(main.getPrefHeight());
	}

	private void onGuardar() {
		pedido.setFechaIngreso(Instant.from(txtFechaIngreso.getValue()));
		pedido.setFechaLimite(Instant.from(txtFechaEntrega.getValue()));
		pedido.setEntregado(chkEntregado.isSelected());
		pedidos.removeIf(p -> p.equals(pedido));
		pedidos.add(pedido);
		stage.close();
	}

	private void onItemQuitar() {
		List<FilaItemPedido> seleccionados = tablaItems.getItems().stream()
			.filter(f -> f.getSeleccion().get())
			.collect(Collectors.toList());
		if (seleccionados.isEmpty()) {
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quitar items del pedido");
		alert.setHeaderText("Confirmar baja de items");
		alert.setContentText("¿Esta seguro de proceder? "
				+ "La operación es reversible si presiona Cancelar");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    for (FilaItemPedido filaItemPedido : seleccionados) {
				pedido.getItems().remove(filaItemPedido.getItemPedido());
				tablaItems.getItems().remove(filaItemPedido);
			}
		}
	}

	private void onItemAgregar() {
		ItemPedido itemPedido = new ItemPedido();
		DialogoItemPedidoController controller = new DialogoItemPedidoController(itemPedido);
		Optional<ItemPedido> optional = controller.getDialogo().showAndWait();
		optional.ifPresent(i -> {
			tablaItems.getItems().add(new FilaItemPedido(i));
			pedido.getItems().add(i);
		});
	}
}
