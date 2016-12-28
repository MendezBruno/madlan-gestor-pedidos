package ar.madlan.gestor.pedidos.modelo;

import java.time.Instant;

public class Proceso {
	private long fecha;
	private String descripcion;
	
	public Instant getFecha() {
		return Instant.ofEpochMilli(fecha);
	}
	public void setFecha(Instant fecha) {
		this.fecha = fecha.toEpochMilli();
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
