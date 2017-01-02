package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.madlan.gestor.pedidos.modelo.ItemPedido;
import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.modelo.Pago;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import ar.madlan.gestor.pedidos.modelo.Proceso;
import ar.madlan.gestor.pedidos.util.Dialogos;
import ar.madlan.gestor.pedidos.util.Fecha;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogoPedidoController implements MixinController {

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
	private Button btnItemsImprimir;
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
	private Label txtCliente;
	@FXML
	private Button btnHistorialProceso;

	private Pedido pedido;
	private Dialog<Pedido> dialogo;
	private ButtonType btnGuardarType = new ButtonType("Guardar", ButtonData.OK_DONE);
	private Node btnGuardar;

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return main;
	}

	public DialogoPedidoController(Pedido pedido, Modelo modelo) {
		this.pedido = pedido;

		try {
			cargar();
			dialogo = new Dialog<Pedido>();
			dialogo.getDialogPane().setContent(main);
			dialogo.getDialogPane().getButtonTypes().addAll(btnGuardarType, ButtonType.CANCEL);
			btnGuardar = dialogo.getDialogPane().lookupButton(btnGuardarType);

			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
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
		pedido.getUltimoProceso().ifPresent(p -> {
			txtareaProceso.setText(p.getDescripcion());
		});
		txtFechaIngreso.setValue(pedido.getFechaIngreso().atZone(ZoneId.systemDefault()).toLocalDate());
		txtFechaEntrega.setValue(pedido.getFechaLimite().atZone(ZoneId.systemDefault()).toLocalDate());
		chkEntregado.setSelected(pedido.entregado());
		txtCliente.setText(pedido.getCliente().toString());
		btnItemsImprimir.setOnAction(e -> onItemsImprimir());
		btnItemsAgregar.setOnAction(e -> onItemAgregar());
		btnItemsQuitar.setOnAction(e -> onItemQuitar());
		btnPagosAgregar.setOnAction(e -> onPagoAgregar());
		btnPagosQuitar.setOnAction(e -> onPagoQuitar());
		btnHistorialProceso.setOnAction(e -> onHistorialProceso());

		btnGuardar.addEventFilter(ActionEvent.ACTION, e -> {
			if (!pasaValidacion()) {
				e.consume();
			}
		});

		dialogo.setResultConverter(dialogButton -> {
			if (dialogButton == btnGuardarType) {
				pedido.setFechaIngreso(Fecha.toInstant(txtFechaIngreso.getValue()));
				pedido.setFechaLimite(Fecha.toInstant(txtFechaEntrega.getValue()));
				pedido.setEntregado(chkEntregado.isSelected());
				String txtProceso = txtareaProceso.getText();
				Proceso ultimo = pedido.getUltimoProceso().orElse(new Proceso());
				if (!txtProceso.equals(ultimo.getDescripcion())){
					Proceso proceso = new Proceso();
					proceso.setDescripcion(txtProceso);
					proceso.setFecha(Instant.now());
					pedido.getProcesos().add(proceso);
				}
				return pedido;
		    }
		    return null;
		});
	}

	private void onItemsImprimir() {
		DialogoPedidoImprimirController controller = new DialogoPedidoImprimirController(pedido);
		controller.getDialogo().showAndWait();
	}

	private boolean pasaValidacion() {
		boolean valido = true;
		boolean fechaIngresoParseable = true;
		boolean fechaEntregaParseable = true;

		if (!Fecha.esParseable(txtFechaIngreso.getEditor().getText())) {
			valido = false;
			fechaIngresoParseable = false;
			Dialogos.advertir("Advertencia", "Datos inconsistentes",
					"La fecha de ingreso no es valida");
		}

		if (!Fecha.esParseable(txtFechaEntrega.getEditor().getText())) {
			valido = false;
			fechaEntregaParseable = false;
			Dialogos.advertir("Advertencia", "Datos inconsistentes",
					"La fecha de entrega no es valida");
		}

		if (fechaIngresoParseable && fechaEntregaParseable) {
			if (txtFechaEntrega.getValue().isBefore(txtFechaIngreso.getValue())) {
				valido = false;
				Dialogos.advertir("Advertencia", "Datos inconsistentes",
						"La fecha de entrega es anterior a la de ingreso");

			}
		}

		return valido;
	}

	private void onHistorialProceso() {
		new DialogoHistorialProceso(pedido).getDialogo().showAndWait();
	}

	private void onPagoQuitar() {
		List<FilaPago> seleccionados = tablaPagos.getItems().stream()
				.filter(f -> f.getSeleccion().get())
				.collect(Collectors.toList());
		if (seleccionados.isEmpty()) {
			return;
		}

		boolean ok = Dialogos.preguntar("Quitar pago", "Confirmar baja de items",
				"¿Esta seguro de proceder? La operación es reversible "
				+ "si presiona Cancelar");
		if (ok){
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
		stage.setMinWidth(main.getPrefWidth());
		stage.setMinHeight(main.getPrefHeight());
	}

	private void onItemQuitar() {
		List<FilaItemPedido> seleccionados = tablaItems.getItems().stream()
			.filter(f -> f.getSeleccion().get())
			.collect(Collectors.toList());
		if (seleccionados.isEmpty()) {
			return;
		}
		boolean ok = Dialogos.preguntar("Quitar items del pedido",
				"Confirmar baja de items",
				"¿Esta seguro de proceder? La operación es reversible "
				+ "si presiona Cancelar");
		if (ok){
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

	public Dialog<Pedido> getDialogo() {
		return dialogo;
	}
}
