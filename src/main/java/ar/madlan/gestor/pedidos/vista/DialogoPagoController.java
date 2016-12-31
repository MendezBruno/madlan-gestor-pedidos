package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;

import ar.madlan.gestor.pedidos.modelo.Pago;
import ar.madlan.gestor.pedidos.util.Fecha;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class DialogoPagoController implements MixinController {

	private static final String RUTA_FXML = "dialogoPago.fxml";

	@FXML
	private GridPane main;
	@FXML
	private DatePicker datePickerFecha;
	@FXML
	private TextField txtMonto;

	private Pago pago;
	private Dialog<Pago> dialogo;
	private ButtonType btnGuardarType;
	private Node btnGuardar;

	public DialogoPagoController(Pago pago) {
		this.pago = pago;
		try {
			cargar();
			dialogo = new Dialog<Pago>();
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
		txtMonto.setText(pago.getMonto()+"");
		datePickerFecha.setValue(Fecha.toLocalDate(pago.getFecha()));
		txtMonto.textProperty().addListener((obs,o,n) -> validar());
		datePickerFecha.getEditor().textProperty().addListener((obs,o,n) -> validar());
		dialogo.setResultConverter(btnType -> {
			if (btnType == btnGuardarType) {
				pago.setFecha(Fecha.toInstant(datePickerFecha.getValue()));
				pago.setMonto(Double.parseDouble(txtMonto.getText()));
				return pago;
			}
			return null;
		});
	}

	private void validar() {
		boolean conErrores = false;
		if (txtMonto.getText() == null || txtMonto.getText().isEmpty()) {
			conErrores = true;
		} else {
			try {
				Double.parseDouble(txtMonto.getText());
			} catch (Exception e) {
				conErrores = true;
			}
		}
		if (datePickerFecha.getValue() == null) {
			conErrores = true;
		}
		if (datePickerFecha.getEditor().getText().isEmpty()) {
			conErrores = true;
		}
		if (!Fecha.esParseable(datePickerFecha.getEditor().getText())) {
			conErrores = true;
		}
		btnGuardar.setDisable(conErrores);
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return null;
	}

	public Dialog<Pago> getDialogo() {
		return dialogo;
	}
}
