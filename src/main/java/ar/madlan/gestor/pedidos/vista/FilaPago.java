package ar.madlan.gestor.pedidos.vista;

import ar.madlan.gestor.pedidos.modelo.Pago;
import ar.madlan.gestor.pedidos.util.Fecha;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilaPago {

	private BooleanProperty seleccion = new SimpleBooleanProperty(false);
	private StringProperty fecha = new SimpleStringProperty();
	private DoubleProperty monto = new SimpleDoubleProperty();
	
	public FilaPago(Pago pago) {
		fecha.set(Fecha.formatter.format(pago.getFecha()));
		monto.set(pago.getMonto());
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

}
