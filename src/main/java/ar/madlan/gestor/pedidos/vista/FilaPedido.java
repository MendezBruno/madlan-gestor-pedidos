package ar.madlan.gestor.pedidos.vista;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import ar.madlan.gestor.pedidos.util.Dialogos;
import ar.madlan.gestor.pedidos.util.Fecha;

public class FilaPedido {

	private Pedido pedido;
	private StringProperty cliente = new SimpleStringProperty();
	private ObjectProperty<LocalDate> fechaLimite = new SimpleObjectProperty<>();
	private Button btnVer = new Button("Ver");
	private DoubleProperty monto = new SimpleDoubleProperty();
	private DoubleProperty pagado = new SimpleDoubleProperty();
	private StringProperty proceso = new SimpleStringProperty();
	private BooleanProperty entregado = new SimpleBooleanProperty();
	private BooleanProperty pago = new SimpleBooleanProperty();
	private SplitMenuButton acciones = new SplitMenuButton();
	private Modelo modelo;
	private PedidosController controller;

	public FilaPedido(Pedido pedido, Modelo modelo, PedidosController controller) {
		setPedido(pedido);
		this.modelo = modelo;
		this.controller = controller;
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
		fechaLimite.set(Fecha.toLocalDate(pedido.getFechaLimite()));
		monto.set(pedido.getMontoCosto());
		pagado.set(pedido.getMontoPagado());
		pedido.getUltimoProceso().ifPresent(p -> proceso.set(p.getDescripcion()));
		entregado.set(pedido.entregado());
		pago.set(pedido.pago());
	}

	private void onEditar() {
		acciones.setText("Editar");
		acciones.setOnAction(e -> onEditar());
		onVer();
	}

	private void onBorrar() {
		acciones.setText("Borrar");
		acciones.setOnAction(e -> onBorrar());
		boolean ok = Dialogos.preguntar("Confirmacion",
				"¿Eliminar el pedido?",
				"Esta operacion no se puede deshacer");
		if(ok) {
			controller.getPedidos().remove(this);
			modelo.getData().getPedidos().remove(pedido);
			try {
				modelo.persistir();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void onVer() {
		try {
			// Se duplica el pedido para trabajar sobre una copia
			// y poder descartar los cambios si se quiere
			DialogoPedidoController pedidoController =
					new DialogoPedidoController(pedido.duplicar(), modelo);
			Optional<Pedido> optional = pedidoController.getDialogo().showAndWait();
			optional.ifPresent(p -> {
				ArrayList<Pedido> pedidos = modelo.getData().getPedidos();
				pedidos.removeIf(pedido -> p.equals(pedido));
				pedidos.add(p);
				setPedido(p);
				controller.actualizar();
				try {
					modelo.persistir();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	public StringProperty getCliente() {
		return cliente;
	}

	public ObjectProperty<LocalDate> getFechaLimite() {
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
					if (fila != null) {
						setGraphic(fila.btnVer);
					}
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
					if (fila != null) {
						setGraphic(fila.acciones);
					}
				}
			}
		};
	}

	public static TableCell<FilaPedido, LocalDate> getTableCellFechaLimite() {
		return new TableCell<FilaPedido, LocalDate>() {
			@Override
			protected void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				setText("");
				if (item != null && !empty) {
					setText(Fecha.formatter.format(item));
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
