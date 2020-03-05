
import java.io.IOException;
import java.net.InetSocketAddress;
import UFPS.Json.*;
import UFPS.model.Partida;
import UFPS.model.Usuario;
import co.edu.ufps.tictactoe.socket.HiloJugador;
import co.edu.ufps.tictactoe.util.Utils;

import java.net.UnknownHostException;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class CustomEndPoint extends WebSocketServer {

	private Gson gson;
	private Utils util;
	private Partida partida;
	private RegistroUsuarioJson registroUsuarioJson;
	private HiloJugador hiloJugador;
	private Usuario usuario;
	private ClientHandshake clientHandshake;

	public CustomEndPoint(int puerto) throws UnknownHostException {
		super(new InetSocketAddress(puerto));
		gson = new Gson();
		this.util = new Utils();
		System.out.println("Recibiendo conexiones en el puerto " + puerto);
	}

	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		System.out.println("Se ha cerrado la conexión");

	}

	@Override
	public void onError(WebSocket arg0, Exception e) {
		System.out.println("Error de conexión");
		e.printStackTrace();

	}

	@Override
	public void onMessage(WebSocket webSocket, String mensaje) {
		// webSocket.send("Gracias por el mensaje: " + mensaje);
		RegistroUsuarioJson registro = null;
		Posicion posicion = null;
		Revancha revancha = null;
		System.out.println("Se ha recibido el mensaje: " + mensaje);

		try {
			String cadenaRegistro = "nombre";
			boolean valorRegistro = mensaje.contains(cadenaRegistro);
			if (valorRegistro) {
				System.out.println("registro");
				this.registroUsuarioJson = gson.fromJson(mensaje, RegistroUsuarioJson.class);
				this.usuario = new Usuario(registroUsuarioJson.getNombre());

				this.usuario.setWebSocket(webSocket);
				this.util.getListaUsuario().add(this.usuario);
				this.partida = this.util.buscarPartida(registroUsuarioJson.getNombre());

				if (this.partida == null) {
					System.out.println("Es una partida nula");
					this.partida = new Partida();
					this.util.agregarPartida(this.partida);
				}
				System.out.println("Vamos al hilo del jugador");
				hiloJugador = new HiloJugador(this.partida, this.usuario);

				/*
				 * Envio de Ranking
				 */
				System.out.println(this.util.imprimirRanking());
				webSocket.send(this.util.imprimirRanking());

				if (this.partida.getUsuario2() != null) {
					EstadoPartida estadoPartidaJson = null;
					if (this.partida.getUsuario1().getNombre().equals(this.usuario.getNombre())) {
						estadoPartidaJson = new EstadoPartida(this.partida.getUsuario2(), this.usuario, this.partida);
						this.partida.getUsuario1().getWebSocket().send(gson.toJson(estadoPartidaJson));
						
						estadoPartidaJson = new EstadoPartida(this.usuario, this.partida.getUsuario2(), this.partida);
						this.partida.getUsuario2().getWebSocket().send(gson.toJson(estadoPartidaJson));
					}
					else if (this.partida.getUsuario2().getNombre().equals(this.usuario.getNombre())){
						estadoPartidaJson = new EstadoPartida(this.usuario, this.partida.getUsuario1(), this.partida);
						this.partida.getUsuario2().getWebSocket().send(gson.toJson(estadoPartidaJson));
						estadoPartidaJson = new EstadoPartida(this.partida.getUsuario1(), this.usuario, this.partida);
						this.partida.getUsuario1().getWebSocket().send(gson.toJson(estadoPartidaJson));
						
					}
				}
			}
			String cadenaPosicion = "posicion";
			boolean valorPosicion = mensaje.contains(cadenaPosicion);
			if (valorPosicion) {
				System.out.println("Es de pocision");
                                posicion = gson.fromJson(mensaje, Posicion.class);
                                boolean valido = partida.agregarMovimiento(posicion.getPocision());
                                Movimiento movimiento = new Movimiento(partida);
                                movimiento.setMovimientoValido(valido);
                                if (!valido) {
                                    this.usuario.getWebSocket().send(gson.toJson(movimiento));
                                }
                                EstadoPartida estadoPartidaJson = null;
                                if (this.partida.getUsuario1().getNombre().equals(this.usuario.getNombre())) {
                                    estadoPartidaJson = new EstadoPartida(this.partida.getUsuario2(), this.usuario, this.partida);
                                    this.partida.getUsuario1().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                    estadoPartidaJson = new EstadoPartida(this.usuario, this.partida.getUsuario2(), this.partida);
                                    this.partida.getUsuario2().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                } else if (this.partida.getUsuario2().getNombre().equals(this.usuario.getNombre())){
                                    estadoPartidaJson = new EstadoPartida(this.usuario, this.partida.getUsuario1(), this.partida);
                                    this.partida.getUsuario2().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                    estadoPartidaJson = new EstadoPartida(this.partida.getUsuario1(), this.usuario, this.partida);
                                    this.partida.getUsuario1().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                }
                                if (this.partida.getJugadorGanador() != null) {
                                	this.partida.reiniciarPartida();
                                	if (this.partida.getUsuario1().getNombre().equals(this.usuario.getNombre())) {
                                        estadoPartidaJson = new EstadoPartida(this.partida.getUsuario2(), this.usuario, this.partida);
                                        this.partida.getUsuario1().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                        estadoPartidaJson = new EstadoPartida(this.usuario, this.partida.getUsuario2(), this.partida);
                                        this.partida.getUsuario2().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                    } else if (this.partida.getUsuario2().getNombre().equals(this.usuario.getNombre())){
                                        estadoPartidaJson = new EstadoPartida(this.usuario, this.partida.getUsuario1(), this.partida);
                                        this.partida.getUsuario2().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                        estadoPartidaJson = new EstadoPartida(this.partida.getUsuario1(), this.usuario, this.partida);
                                        this.partida.getUsuario1().getWebSocket().send(gson.toJson(estadoPartidaJson));
                                    }
                                }
			}
			String cadenaMovimiento = "mensaje";
			boolean valorMovimiento = mensaje.contains(cadenaMovimiento);
			if (valorMovimiento) {
				System.out.println("Es confirmacion de movimiento");
			}
			String revanchaTexto = "revancha";
			boolean valorRevancha = mensaje.contains(revanchaTexto);
			if (valorRevancha) {
				System.out.println("Es confirmacion de movimiento");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Se envia el ranking
		 */

	}

	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake arg1) {
		// webSocket.send("Bienvenido a mi servidor");
		System.out.println("Se ha iniciado una nueva conexión");
		this.clientHandshake = arg1;

	}

	public static void main(String[] args) {
		try {
			new CustomEndPoint(8090).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}