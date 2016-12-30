package ar.madlan.gestor.pedidos.vista;

import ar.madlan.gestor.pedidos.modelo.Proceso;
import ar.madlan.gestor.pedidos.util.Fecha;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

public class FilaProceso {
	private StringProperty fecha = new SimpleStringProperty();
	private StringProperty descripcion = new SimpleStringProperty();

	public FilaProceso(Proceso proceso) {
		fecha.set(Fecha.formatter.format(proceso.getFecha()));
		descripcion.set(proceso.getDescripcion());
	}

	public StringProperty getFecha() {
		return fecha;
	}

	public StringProperty getDescripcion() {
		return descripcion;
	}

	public static TableCell<FilaProceso, String> getTableCellDescripcion() {
		TableCell<FilaProceso, String> cell = new TableCell<>();
        Text text = new Text();
        cell.setGraphic(text);
        cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
        text.wrappingWidthProperty().bind(cell.widthProperty());
        text.textProperty().bind(cell.itemProperty());
        return cell ;
	}
}
