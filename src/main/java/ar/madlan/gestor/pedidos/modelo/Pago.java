package ar.madlan.gestor.pedidos.modelo;

import java.time.Instant;

public class Pago {
	private double monto;
	private long fecha;
	
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public Instant getFecha() {
		return Instant.ofEpochMilli(fecha);
	}
	public void setFecha(Instant fecha) {
		this.fecha = fecha.toEpochMilli();
	}
}
