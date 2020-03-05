package UFPS.Json;

import UFPS.model.Partida;

public class Movimiento {
	private String mensaje;
	private boolean movimientoValido;
	private char[] tablero;
	public final int tipo = 5;

	public Movimiento () {}
	
	public Movimiento (Partida partida) {
		this.tablero = partida.getPosicionesLista();
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isMovimientoValido() {
		return movimientoValido;
	}

	public void setMovimientoValido(boolean movimientoValido) {
		this.movimientoValido = movimientoValido;
	}

	public char[] getTablero() {
		return tablero;
	}

	public void setTablero(char[] tablero) {
		this.tablero = tablero;
	}
}
