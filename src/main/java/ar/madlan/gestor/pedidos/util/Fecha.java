package ar.madlan.gestor.pedidos.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Fecha {
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			"dd/MMM/yyyy").withZone(ZoneId.systemDefault());
}
