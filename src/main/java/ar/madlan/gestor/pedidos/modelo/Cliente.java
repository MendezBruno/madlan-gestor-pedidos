package ar.madlan.gestor.pedidos.modelo;

public class Cliente {
	private static int maxId;
	private String nombre;
	private String apellido;
	private int numero;

	public Cliente() {
		numero = nextId();
	}

	private int nextId() {
		int id = maxId;
		maxId++;
		return id;
	}

	public static void setMaxId(int id) {
		maxId = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}
}
