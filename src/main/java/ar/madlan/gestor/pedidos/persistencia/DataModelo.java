package ar.madlan.gestor.pedidos.persistencia;

import java.util.ArrayList;

import ar.madlan.gestor.pedidos.modelo.Cliente;
import ar.madlan.gestor.pedidos.modelo.Pedido;

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
