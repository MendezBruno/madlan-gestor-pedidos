package ar.madlan.gestor.pedidos.vista;

import java.net.URL;
import java.util.ResourceBundle;

import ar.madlan.gestor.pedidos.modelo.ItemPedido;
import ar.madlan.gestor.pedidos.modelo.Pago;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import ar.madlan.gestor.pedidos.util.Fecha;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;

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
	private TextField txtFechaIngreso;
	@FXML
	private TextField txtFechaEntrega;
	@FXML
	private CheckBox chkEntregado;
	@FXML
	private Hyperlink cliente;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;

	private Pedido pedido;
	
	@Override
	public String getFxml() {
		return RUTA_FXML;
	}
	
	@Override
	public Parent getNode() {
		return main;
	}
	
	public PedidoController(Pedido pedido) {
		this.pedido = pedido;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnaItemsSeleccion.setCellFactory(CheckBoxTableCell.forTableColumn(columnaItemsSeleccion));
		columnaItemsSeleccion.setCellValueFactory(data -> data.getValue().getSeleccion());
		columnaItemsCantidad.setCellValueFactory(data -> data.getValue().getCantidad());
		columnaItemsPedido.setCellValueFactory(data -> data.getValue().getDescripcion());
		columnaItemsPrecioUnitario.setCellValueFactory(data -> data.getValue().getPrecioUnitario());
		columnaItemsImporte.setCellValueFactory(data -> data.getValue().getImporte());
		columnaPagosSeleccion.setCellFactory(CheckBoxTableCell.forTableColumn(columnaPagosSeleccion));
		columnaPagosSeleccion.setCellValueFactory(data -> data.getValue().getSeleccion());
		columnaPagosFecha.setCellValueFactory(data -> data.getValue().getFecha());
		columnaPagosMonto.setCellValueFactory(data -> data.getValue().getMonto());
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
		txtFechaIngreso.setText(Fecha.formatter.format(pedido.getFechaIngreso()));
		txtFechaEntrega.setText(Fecha.formatter.format(pedido.getFechaLimite()));
		chkEntregado.setSelected(pedido.entregado());
		cliente.setText(pedido.getCliente().getNombre());
	}
}
