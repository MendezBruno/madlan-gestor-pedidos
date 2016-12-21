package ar.madlan.gestor.pedidos.modelo;

import java.time.Instant;

public class ItemPedido {
	private long fechaCreacion;
	private double monto;
	private String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public Instant getFechaCreacion() {
		return Instant.ofEpochMilli(fechaCreacion);
	}
	public void setFechaCreacion(Instant fechaCreacion) {
		this.fechaCreacion = fechaCreacion.toEpochMilli();
	}
}
