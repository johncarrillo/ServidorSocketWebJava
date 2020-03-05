package UFPS.Json;

public class RegistroUsuarioJson {
	private String nombre;
	public final int tipo = 1;

	public RegistroUsuarioJson() {
	}

	public RegistroUsuarioJson(String nombre) {
		this.nombre = nombre;
	}

	public void setNombre (String nombre) {
		this.nombre = nombre;
	}

	public String getNombre () {
		return this.nombre;
	}
}
