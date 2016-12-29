package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;
import java.time.Instant;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ar.madlan.gestor.pedidos.modelo.ItemPedido;

public class DialogoItemPedidoController implements MixinController{
	
	@FXML
	private GridPane main;
	@FXML
	private TextField txtDescripcion;
	@FXML
	private TextField txtCantidad;
	@FXML
	private TextField txtPrecioUnitario;
	@FXML
	private Label lblImporte;
	
	private static final String RUTA_FXML = "dialogoItemPedido.fxml";
	private Dialog<ItemPedido> dialogo;
	private Node btnGuardar;
	private ItemPedido itemPedido;
	private ButtonType btnGuardarType;

	public DialogoItemPedidoController(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
		try {
			cargar();
			dialogo = new Dialog<ItemPedido>();
			dialogo.getDialogPane().setContent(main);
			btnGuardarType = new ButtonType("Guardar", ButtonData.OK_DONE);
			dialogo.getDialogPane().getButtonTypes().addAll(btnGuardarType, ButtonType.CANCEL);
			btnGuardar = dialogo.getDialogPane().lookupButton(btnGuardarType);
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		txtDescripcion.setText(itemPedido.getDescripcion());
		txtCantidad.setText(itemPedido.getCantidad()+"");
		txtPrecioUnitario.setText(itemPedido.getPrecioUnitario()+"");
		txtDescripcion.textProperty().addListener((obs, old, n) -> validar());
		txtCantidad.textProperty().addListener((obs, old, n) -> validar());
		txtPrecioUnitario.textProperty().addListener((obs, old, n) -> validar());
		validar();
		
		dialogo.setResultConverter(dialogButton -> {
		    if (dialogButton == btnGuardarType) {
		    	itemPedido.setDescripcion(txtDescripcion.getText());
		    	itemPedido.setCantidad(Double.parseDouble(txtCantidad.getText()));
		    	itemPedido.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
		    	itemPedido.setFechaCreacion(Instant.now());
		        return itemPedido;
		    }
		    return null;
		});
	}

	private void validar() {
		boolean conError = false;
		Double cantidad = null;
		Double precioUnitario = null;
		if (txtDescripcion.getText() == null || txtDescripcion.getText().isEmpty()) {
			conError = true;
		}
		if (txtCantidad.getText() == null || txtCantidad.getText().isEmpty()) {
			conError = true;
		} else {
			try {
				cantidad = Double.parseDouble(txtCantidad.getText());
			} catch (Exception e) {
				conError = true;
			}
		}
		if (txtPrecioUnitario.getText() == null || txtPrecioUnitario.getText().isEmpty()) {
			conError = true;
		} else {
			try {
				precioUnitario = Double.parseDouble(txtPrecioUnitario.getText());
			} catch (Exception e) {
				conError = true;
			}
		}
		if (cantidad != null && precioUnitario != null) {
			lblImporte.setText((cantidad * precioUnitario) + "");
		} else {
			lblImporte.setText("");
		}
		btnGuardar.setDisable(conError);
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return null;
	}

	public Dialog<ItemPedido> getDialogo() {
		return dialogo;
	}
}
