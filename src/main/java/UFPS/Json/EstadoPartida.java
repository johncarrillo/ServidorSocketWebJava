package UFPS.Json;

import UFPS.model.Partida;
import UFPS.model.Usuario;
import co.edu.ufps.tictactoe.constantes.Resultado;

public class EstadoPartida {
	private String usuario;
	private String rival;
	private int puntaje;
	private int idPartida;
	private String estadoPartida;
	private String resultado;
	private String turno;
	private char[] tablero;

	public EstadoPartida () {
	}

	public EstadoPartida (Usuario cliente, Usuario clienteRival, Partida partida) {
		this.usuario = cliente.getNombre();
		this.rival = clienteRival.getNombre();
		this.puntaje = cliente.getPuntaje();
		this.idPartida = partida.getId();
		this.estadoPartida = partida.getEstado();
		System.out.println("nombre del jugador 1 es " + partida.getUsuario1().getNombre());
		System.out.println("nombre del jugador 2 es " + partida.getUsuario2().getNombre());
		System.out.println("siguiente jugador " + partida.getSiguienteJugador());
		if (partida.getJugadorGanador() != null && partida.getJugadorGanador().equals(cliente)) {
			this.resultado = Resultado.GANADOR;
		} else if (partida.getJugadorGanador() != null && partida.getJugadorGanador().equals(clienteRival)) {
			this.resultado = Resultado.PERDEDOR;
		} else {
			this.resultado = "";
		}
		tablero = partida.getPosicionesLista();
		if (partida.getSiguienteJugador() != null
				&& partida.getSiguienteJugador().getNombre().equals(cliente.getNombre())) {
			this.turno = partida.getSiguienteJugador().getNombre();
		} else if (partida.getSiguienteJugador() != null
				&& partida.getSiguienteJugador().getNombre().equals(clienteRival.getNombre())) {
			this.turno = partida.getSiguienteJugador().getNombre();
		}
	}
	
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public char[] getTablero() {
		return tablero;
	}
	public void setTablero(char[] tablero) {
		this.tablero = tablero;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getEstadoPartida() {
		return estadoPartida;
	}
	public void setEstadoPartida(String estadoPartida) {
		this.estadoPartida = estadoPartida;
	}
	public int getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	public String getRival() {
		return rival;
	}
	public void setRival(String rival) {
		this.rival = rival;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
