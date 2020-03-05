package UFPS.ClienteSocket;


import UFPS.model.Usuario;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class MySessionHandler extends StompSessionHandlerAdapter {
	StompSession session;
	StompHeaders connectedHeaders;
	
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/topic/greetings", this);
        session.send("/app/hello", "{\"nombre\":\"Client\"}".getBytes());
        System.out.println("New session: {}" + session.getSessionId());
        this.session = session;
        this.connectedHeaders = connectedHeaders;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Usuario.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
    	MappingJackson2MessageConverter a = new MappingJackson2MessageConverter();
    	System.out.println("Received: {}" + payload.toString());
    }

    public void ranking () {
    	session.subscribe("/topic/ranking", this);
        session.send("/app/ranking", "{\"nombre\":\"Clientes\"}".getBytes());
    }
}
