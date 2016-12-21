package ar.madlan.gestor.pedidos.modelo;

import java.time.Instant;
import java.util.ArrayList;

public class Pedido {
	private int numero;
	private long fechaIngreso;
	private long fechaLimite;
	private boolean entregado = false;
	private Proceso proceso = Proceso.EnEspera;
	private ArrayList<ItemPedido> items = new ArrayList<>();
	private ArrayList<Pago> pagos = new ArrayList<>();
	private Cliente cliente;
	
	public Double getMontoPagado() {
		return pagos.stream().map(p -> p.getMonto()).reduce(Double::sum).orElse(0.0);
	}
	
	public Double getMontoCosto() {
		return items.stream().map(i -> i.getMonto()).reduce(Double::sum).orElse(0.0);
	}
	
	public boolean pago() {
		return getMontoCosto().equals(getMontoPagado());
	}
	
	public Instant getFechaIngreso() {
		return Instant.ofEpochMilli(fechaIngreso);
	}
	
	public Instant getFechaLimite() {
		return Instant.ofEpochMilli(fechaLimite);
	}
	
	public void setFechaIngreso(Instant fecha) {
		fechaIngreso = fecha.toEpochMilli();
	}
	
	public void setFechaLimite(Instant fecha) {
		fechaLimite = fecha.toEpochMilli();
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean entregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ArrayList<ItemPedido> getItems() {
		return items;
	}

	public ArrayList<Pago> getPagos() {
		return pagos;
	}
}
