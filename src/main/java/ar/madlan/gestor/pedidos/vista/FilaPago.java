package ar.madlan.gestor.pedidos.vista;

import java.util.Optional;

import ar.madlan.gestor.pedidos.modelo.Pago;
import ar.madlan.gestor.pedidos.util.Fecha;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableRow;

public class FilaPago {

	private BooleanProperty seleccion = new SimpleBooleanProperty(false);
	private StringProperty fecha = new SimpleStringProperty();
	private DoubleProperty monto = new SimpleDoubleProperty();
	private Pago pago;
	
	public FilaPago(Pago pago) {
		setPago(pago);
	}
	
	public void setPago(Pago pago) {
		this.pago = pago;
		fecha.set(Fecha.formatter.format(pago.getFecha()));
		monto.set(pago.getMonto());
	}
	
	public Pago getPago() {
		return pago;
	}
	
	public BooleanProperty getSeleccion() {
		return seleccion;
	}
	
	public StringProperty getFecha() {
		return fecha;
	}

	public DoubleProperty getMonto() {
		return monto;
	}

	public static TableRow<FilaPago> getRowFactory() {
		TableRow<FilaPago> tableRow = new TableRow<>();
		tableRow.setOnMouseClicked(e -> {
			FilaPago fila = tableRow.getItem();
			if (fila != null && e.getClickCount() == 2) {
				Optional<Pago> optional = new DialogoPagoController(fila.getPago())
					.getDialogo().showAndWait();
				optional.ifPresent(i -> fila.setPago(i));
			}
		});
		return tableRow;
	}
}
