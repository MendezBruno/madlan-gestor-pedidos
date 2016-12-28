package ar.madlan.gestor.pedidos.modelo;

import java.time.Instant;

public class ItemPedido {
	private long fechaCreacion;
	private double precioUnitario;
	private String descripcion;
	private double cantidad;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Instant getFechaCreacion() {
		return Instant.ofEpochMilli(fechaCreacion);
	}
	public void setFechaCreacion(Instant fechaCreacion) {
		this.fechaCreacion = fechaCreacion.toEpochMilli();
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public double getImporte() {
		return precioUnitario * cantidad;
	}
}
