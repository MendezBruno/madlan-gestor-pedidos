package ar.madlan.gestor.pedidos.vista;

import java.util.Optional;

import ar.madlan.gestor.pedidos.modelo.ItemPedido;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableRow;

public class FilaItemPedido {

	private BooleanProperty seleccion = new SimpleBooleanProperty(false);
	private DoubleProperty cantidad = new SimpleDoubleProperty();
	private StringProperty descripcion = new SimpleStringProperty();
	private DoubleProperty precioUnitario = new SimpleDoubleProperty();
	private DoubleProperty importe = new SimpleDoubleProperty();
	private ItemPedido itemPedido;
	
	public FilaItemPedido(ItemPedido itemPedido) {
		setItemPedido(itemPedido);
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
		cantidad.set(itemPedido.getCantidad());
		descripcion.set(itemPedido.getDescripcion());
		precioUnitario.set(itemPedido.getPrecioUnitario());
		importe.set(itemPedido.getImporte());
	}
	
	public BooleanProperty getSeleccion() {
		return seleccion;
	}

	public DoubleProperty getCantidad() {
		return cantidad;
	}

	public StringProperty getDescripcion() {
		return descripcion;
	}

	public DoubleProperty getPrecioUnitario() {
		return precioUnitario;
	}

	public DoubleProperty getImporte() {
		return importe;
	}

	public static TableRow<FilaItemPedido> getRowFactory() {
		TableRow<FilaItemPedido> tableRow = new TableRow<>();
		tableRow.setOnMouseClicked(e -> {
			FilaItemPedido fila = tableRow.getItem();
			if (fila != null && e.getClickCount() == 2) {
				Optional<ItemPedido> optional = new DialogoItemPedidoController(fila.getItemPedido())
					.getDialogo().showAndWait();
				optional.ifPresent(i -> fila.setItemPedido(i));
			}
		});
		return tableRow;
	}

	public ItemPedido getItemPedido() {
		return itemPedido;
	}
}
