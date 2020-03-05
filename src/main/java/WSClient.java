
import java.net.URI;
import java.net.URI;
import javax.websocket.*;

import com.google.gson.Gson;

import UFPS.Json.EstadoPartida;
import UFPS.Json.Movimiento;
import UFPS.Json.Posicion;
import UFPS.Json.RegistroUsuarioJson;
import UFPS.model.Usuario;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ClientEndpoint
public class WSClient {
	private static Object waitLock = new Object();
	private static Gson gson;
	private static WebSocketContainer container = null;//
	private static Session session = null;
        public static char[] tablero;
        public static boolean movimientoValido;
        public static String mensaje = "Es tu turno";

	@OnMessage
	public void onMessage(String mensaje) {
//the new USD rate arrives from the websocket server side.
            String cadenaRanking = "[{";
            boolean valorRanking = mensaje.contains(cadenaRanking);
            String cadenaEstado = "rival";
            boolean valorEstado = mensaje.contains(cadenaEstado);
            if (valorEstado) {
               EstadoPartida estadoPartida = gson.fromJson(mensaje, EstadoPartida.class);
               System.out.println("Tu rival es = " + estadoPartida.getRival() );
               System.out.println("el numero de partida es = " + estadoPartida.getIdPartida());
               System.out.println("Es turno de " + estadoPartida.getTurno());
               if (estadoPartida.getResultado().equals("Ganador")) {
                   System.out.println("------------------------------------------------------");
            	   System.out.println("FELICIDADES GANASTE LA PARTIDA");
                   System.out.println("------------------------------------------------------");
               } else if (estadoPartida.getResultado().equals("Ganador")) {
            	   System.out.println("PERDISTE LA PARTIDA");
               }
               if (estadoPartida.getRival().equals(estadoPartida.getTurno())) {
            	   mensaje = "Aun no es tu turno, por favor espera";
               } else {
            	   mensaje = "Ya puedes dar";
                   tablero = estadoPartida.getTablero();
               }
            }
            String cadenaMovimiento = "mensaje";
            boolean valorMovimiento = mensaje.contains(cadenaMovimiento);
            if (valorMovimiento) {
               System.out.println("Es confirmacion de movimiento");
               Movimiento movimiento = gson.fromJson(mensaje, Movimiento.class);
               mensaje = movimiento.getMensaje();
               movimientoValido = movimiento.isMovimientoValido();
               tablero = movimiento.getTablero();
            }
	}

        public static void enviarMovimiento (Posicion posicion) {
            try {
                session.getBasicRemote().sendText(gson.toJson(posicion));
            } catch (IOException ex) {
                Logger.getLogger(WSClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

	private static void wait4TerminateSignal() {
		synchronized (waitLock) {
			try {
				waitLock.wait();
			} catch (InterruptedException e) {
			}
		}
	}

        public static void limpiarTablero () {
            tablero = null;
        }

	public static void main(Usuario usuario, ConsolaFront consola) {
		gson = new Gson();
                tablero = null;
		WebSocketContainer container = null;//
		session = null;
		try {
			// Tyrus is plugged via ServiceLoader API. See notes above
			container = ContainerProvider.getWebSocketContainer();
//WS1 is the context-root of my web.app 
//ratesrv is the  path given in the ServerEndPoint annotation on server implementation
			session = container.connectToServer(WSClient.class, URI.create("ws://localhost:8090/"));
			RegistroUsuarioJson registroUsuario = new RegistroUsuarioJson(usuario.getNombre());
			Gson gson = new Gson();
			session.getBasicRemote().sendText(gson.toJson(registroUsuario));
			Thread hilo = new Thread(consola);
	        hilo.start();
			while (true) {
				Thread.sleep(30000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}