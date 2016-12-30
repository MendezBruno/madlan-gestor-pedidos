package ar.madlan.gestor.pedidos.modelo;

import java.time.Instant;
import java.util.ArrayList;

public class Pedido {
	private int numero;
	private long fechaIngreso;
	private long fechaLimite;
	private boolean entregado = false;
	private ArrayList<Proceso> procesos = new ArrayList<>();
	private ArrayList<ItemPedido> items = new ArrayList<>();
	private ArrayList<Pago> pagos = new ArrayList<>();
	private Cliente cliente;

	public Double getMontoPagado() {
		return pagos.stream().map(p -> p.getMonto()).reduce(Double::sum).orElse(0.0);
	}

	public Double getMontoCosto() {
		return items.stream().map(i -> i.getImporte()).reduce(Double::sum).orElse(0.0);
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

	public ArrayList<Proceso> getProcesos() {
		return procesos;
	}
	public Proceso getUltimoProceso() {
		return procesos.get(procesos.size()-1);
	}

	public Pedido duplicar(){
		Pedido pedido = new Pedido();
		pedido.numero = numero;
		pedido.cliente = cliente;
		pedido.entregado = entregado;
		pedido.fechaIngreso = fechaIngreso;
		pedido.fechaLimite = fechaLimite;
		pedido.items.addAll(items);
		pedido.pagos.addAll(pagos);
		pedido.procesos.addAll(procesos);
		return pedido;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof Pedido) {
			return ((Pedido) obj).numero == numero;
		} else {
			return false;
		}
	}
}
