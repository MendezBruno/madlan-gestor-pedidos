package ar.madlan.gestor.pedidos.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Fecha {
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			"dd/MMM/yyyy").withZone(ZoneId.systemDefault());

	public static LocalDate toLocalDate(Instant fecha) {
		return fecha.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Instant toInstant(LocalDate fecha) {
		return fecha.atStartOfDay(ZoneId.systemDefault()).toInstant();
	}

	public static boolean esParseable(String text) {
		try {
			DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
