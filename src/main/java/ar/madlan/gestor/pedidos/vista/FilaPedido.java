package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ar.madlan.gestor.pedidos.modelo.Pedido;

public class FilaPedido {
	
	private Pedido pedido;
	private StringProperty cliente = new SimpleStringProperty();
	private StringProperty fechaLimite = new SimpleStringProperty();
	private Button btnVer = new Button("Ver");
	private DoubleProperty monto = new SimpleDoubleProperty();
	private DoubleProperty pagado = new SimpleDoubleProperty();
	private StringProperty proceso = new SimpleStringProperty();
	private BooleanProperty entregado = new SimpleBooleanProperty();
	private BooleanProperty pago = new SimpleBooleanProperty();
	private SplitMenuButton acciones = new SplitMenuButton();

	public FilaPedido(Pedido pedido) {
		setPedido(pedido);
		MenuItem menuItemEditar = new MenuItem("Editar");
		MenuItem menuItemBorrar = new MenuItem("Borrar");
		menuItemEditar.setOnAction(e -> onEditar());
		menuItemBorrar.setOnAction(e -> onBorrar());
		acciones.getItems().addAll(menuItemEditar, menuItemBorrar);
		acciones.setText("Editar");
		acciones.setOnAction(e -> onEditar());
		btnVer.setOnAction(e -> onVer());
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
		cliente.set(pedido.getCliente().getNombre());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
				"dd/MMM/yyyy").withZone(ZoneId.systemDefault());
		fechaLimite.set(formatter.format(pedido.getFechaLimite()));
		monto.set(pedido.getMontoCosto());
		pagado.set(pedido.getMontoPagado());
		proceso.set(pedido.getProceso().toString());
		entregado.set(pedido.entregado());
		pago.set(pedido.pago());
	}

	private void onEditar() {
		acciones.setText("Editar");
		acciones.setOnAction(e -> onEditar());
	}
	
	private void onBorrar() {
		acciones.setText("Borrar");
		acciones.setOnAction(e -> onBorrar());
	}
	

	private void onVer() {
		try {
			Stage ventanaDetalle = new Stage();
			DetalleController detalleController = new DetalleController(pedido);
			detalleController.cargar(ventanaDetalle);
			ventanaDetalle.initOwner(PedidosController.stage.getScene().getWindow());
			ventanaDetalle.initModality(Modality.APPLICATION_MODAL);
			ventanaDetalle.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public StringProperty getCliente() {
		return cliente;
	}

	public StringProperty getFechaLimite() {
		return fechaLimite;
	}

	public DoubleProperty getMonto() {
		return monto;
	}

	public DoubleProperty getPagado() {
		return pagado;
	}

	public StringProperty getProceso() {
		return proceso;
	}

	public BooleanProperty getEntregado() {
		return entregado;
	}

	public BooleanProperty getPago() {
		return pago;
	}

	public static TableCell<FilaPedido, String> getTableCellDetalle() {
		return new TableCell<FilaPedido, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(null);
				if (!empty) {
					FilaPedido fila = (FilaPedido) getTableRow().getItem();
					setGraphic(fila.btnVer);
				}
			}
		};
	}

	public static TableCell<FilaPedido, String> getTableCellAcciones() {
		return new TableCell<FilaPedido, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(null);
				if (!empty) {
					FilaPedido fila = (FilaPedido) getTableRow().getItem();
					setGraphic(fila.acciones);
				}
			}
		};
	}

	public static TableRow<FilaPedido> getTableRow(TableView<FilaPedido> tabla) {
		return new TableRow<FilaPedido>() {
			@Override
			protected void updateItem(FilaPedido item, boolean empty) {
				super.updateItem(item, empty);
				setStyle("");
				if (!empty) {
					if (item.entregado.get()){
						setStyle("-fx-background-color: #D9EAD3");
					} else if (item.pedido.getFechaLimite()
							.minus(Duration.ofDays(15)).isBefore(Instant.now())){
						//Si la fecha limite menos 15 dias es antes que ahora -> riesgo
						setStyle("-fx-background-color: #F4CCCC");
					}
				}
			}
		};
	}
}
