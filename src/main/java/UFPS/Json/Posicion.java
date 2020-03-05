package UFPS.Json;

public class Posicion {
	private String posicion;
	public final int tipo = 4;
	
	public Posicion () {}
	
	public Posicion (String posicion) {
		this.posicion = posicion;
	}

	public void setPocision(String posicion) {
		this.posicion = posicion;
	}

	public String getPocision() {
		return this.posicion;
	}
}
