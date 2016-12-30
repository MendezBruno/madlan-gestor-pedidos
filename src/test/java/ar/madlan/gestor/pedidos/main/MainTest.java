package ar.madlan.gestor.pedidos.main;

import java.time.Instant;

import ar.madlan.gestor.pedidos.modelo.Cliente;
import ar.madlan.gestor.pedidos.modelo.ItemPedido;
import ar.madlan.gestor.pedidos.modelo.Modelo;
import ar.madlan.gestor.pedidos.modelo.Pago;
import ar.madlan.gestor.pedidos.modelo.Pedido;
import ar.madlan.gestor.pedidos.modelo.Proceso;
import ar.madlan.gestor.pedidos.vista.PedidosController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainTest extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Modelo modelo = crearModelo();
		PedidosController controller = new PedidosController(modelo);
		controller.cargar(primaryStage);
		primaryStage.show();
	}

	private Modelo crearModelo() {
		Pedido pedido = new Pedido();
		ItemPedido itemPedido1 = new ItemPedido();
		ItemPedido itemPedido2 = new ItemPedido();
		Pago pago1 = new Pago();
		Pago pago2 = new Pago();
		Cliente cliente = new Cliente();
		itemPedido1.setDescripcion("La paz mundial");
		itemPedido1.setFechaCreacion(Instant.now());
		itemPedido1.setCantidad(10.0);
		itemPedido1.setPrecioUnitario(1.0);
		itemPedido2.setDescripcion("Una hamburguesa");
		itemPedido2.setFechaCreacion(Instant.now());
		itemPedido2.setCantidad(6.0);
		itemPedido2.setPrecioUnitario(7.0);
		pago1.setMonto(1.0);
		pago1.setFecha(Instant.now());
		pago2.setMonto(5.0);
		pago2.setFecha(Instant.now());
		cliente.setNombre("Bruno");
		cliente.setNumero(666);
		pedido.getItems().add(itemPedido1);
		pedido.getItems().add(itemPedido2);
		pedido.getPagos().add(pago1);
		pedido.getPagos().add(pago2);
		pedido.setCliente(cliente);
		pedido.setFechaIngreso(Instant.now());
		pedido.setFechaLimite(Instant.now());
		pedido.setNumero(666);
		Proceso proceso1 = new Proceso();
		proceso1.setDescripcion("lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero lero ");
		proceso1.setFecha(Instant.now());
		Proceso proceso2 = new Proceso();
		proceso2.setDescripcion("Haciendo algo");
		proceso2.setFecha(Instant.now());
		pedido.getProcesos().add(proceso1);
		pedido.getProcesos().add(proceso2);
//		pedido.setEntregado(true);

		Modelo modelo = new Modelo();
		modelo.getData().getPedidos().add(pedido);
		return modelo;
	}
}