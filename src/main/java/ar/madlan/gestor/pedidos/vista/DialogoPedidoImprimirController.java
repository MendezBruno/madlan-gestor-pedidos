package ar.madlan.gestor.pedidos.vista;

import java.io.IOException;

import ar.madlan.gestor.pedidos.modelo.ItemPedido;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogoPedidoImprimirController implements MixinController {

	private static final String RUTA_FXML = "pedidoImprimir.fxml";

	@FXML
	private ScrollPane main;
	@FXML
	private VBox vBox;
	@FXML
	private TableView<FilaItemPedido> tablaItems;
	@FXML
	private TableColumn<FilaItemPedido, Number> columnaItemsCantidad;
	@FXML
	private TableColumn<FilaItemPedido, String> columnaItemsPedido;
	@FXML
	private TableColumn<FilaItemPedido, Number> columnaItemsPrecioUnitario;
	@FXML
	private TableColumn<FilaItemPedido, Number> columnaItemsImporte;
	@FXML
	private Button btnExportar;
	@FXML
	private Label lblMonto;

	private Pedido pedido;
	private Dialog<Pedido> dialogo;

	public DialogoPedidoImprimirController(Pedido pedido) {
		this.pedido = pedido;

		try {
			cargar();
			dialogo = new Dialog<Pedido>();
			dialogo.getDialogPane().setContent(main);
			dialogo.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			dialogo.setResizable(true);

			//Esto es para que no muestre el boton cerrar por debajo
			Node closeButton = dialogo.getDialogPane().lookupButton(ButtonType.CLOSE);
            closeButton.managedProperty().bind(closeButton.visibleProperty());
            closeButton.setVisible(false);

			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		columnaItemsCantidad.setCellValueFactory(data -> data.getValue().getCantidad());
		columnaItemsPedido.setCellValueFactory(data -> data.getValue().getDescripcion());
		columnaItemsPrecioUnitario.setCellValueFactory(data -> data.getValue().getPrecioUnitario());
		columnaItemsImporte.setCellValueFactory(data -> data.getValue().getImporte());

		for (ItemPedido itemPedido : pedido.getItems()) {
			tablaItems.getItems().add(new FilaItemPedido(itemPedido));
		}

		columnaItemsPedido.setCellFactory(columna -> new TableCell<FilaItemPedido, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null) setText(item);
				setMinHeight(50);
			}
		});

		btnExportar.setOnAction(e -> onExportar());

		lblMonto.setText(pedido.getMontoCosto()+"");
	}

	private void onExportar() {
		PrinterJob job = PrinterJob.createPrinterJob();
		 if(job != null){
		   job.showPrintDialog(PedidosController.stage); // Window must be your main Stage
		   btnExportar.setVisible(false);
		   Printer printer = Printer.getDefaultPrinter();
		   PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0,0,0,0 );
		   job.getJobSettings().setJobName("Detalle pedido "+pedido.getNumero());
		   job.printPage(pageLayout,vBox);
		   job.endJob();
		 }
		 btnExportar.setVisible(true);
		 dialogo.close();
	}

	@Override
	public String getFxml() {
		return RUTA_FXML;
	}

	@Override
	public Parent getNode() {
		return null;
	}

	public Dialog<Pedido> getDialogo() {
		return dialogo;
	}
}
