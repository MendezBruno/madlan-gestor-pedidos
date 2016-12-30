package ar.madlan.gestor.pedidos.modelo;

import java.util.ArrayList;

public class DataModelo {
	private ArrayList<Pedido> pedidos = new ArrayList<>();
	private ArrayList<Cliente> clientes = new ArrayList<>();
	
	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	public ArrayList<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}
}
