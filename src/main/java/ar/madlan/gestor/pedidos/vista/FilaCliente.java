package ar.madlan.gestor.pedidos.vista;

import java.util.Optional;

import ar.madlan.gestor.pedidos.modelo.Cliente;
import ar.madlan.gestor.pedidos.modelo.Modelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableRow;

public class FilaCliente {
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty apellido = new SimpleStringProperty();
	private Cliente cliente;
	private Modelo modelo;

	public FilaCliente(Cliente cliente, Modelo modelo) {
		this.modelo = modelo;
		setCliente(cliente);
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		nombre.set(cliente.getNombre());
		apellido.set(cliente.getApellido());
	}

	public Cliente getCliente() {
		return cliente;
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public StringProperty getApellido() {
		return apellido;
	}

	public static TableRow<FilaCliente> getRowFactory() {
		TableRow<FilaCliente> tableRow = new TableRow<>();
		tableRow.setOnMouseClicked(e -> {
			FilaCliente fila = tableRow.getItem();
			if (fila != null && e.getClickCount() == 2) {
				Optional<Cliente> optional = new DialogoClienteController(fila.cliente)
					.getDialogo().showAndWait();
				optional.ifPresent(i -> {
					fila.setCliente(i);
					try {
						fila.modelo.persistir();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
			}
		});
		return tableRow;
	}
}
