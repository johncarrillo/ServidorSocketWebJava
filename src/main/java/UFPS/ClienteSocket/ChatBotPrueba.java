package UFPS.ClienteSocket;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

import UFPS.Json.RegistroUsuarioJson;
import UFPS.model.Usuario;

public class ChatBotPrueba {

	/**
	 * main
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... argv) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://localhost:8080/hello";
        MySessionHandler sessionHandler = new MySessionHandler();
        stompClient.connect(url, sessionHandler);
        
        

        new Scanner(System.in).nextLine(); //Don't close immediately.
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // simulated delay
        
        sessionHandler.ranking();
    }

}
