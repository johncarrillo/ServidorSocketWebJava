import java.io.StringReader;
import java.net.URI;
import java.net.UnknownHostException;

import javax.json.Json;
import javax.json.JsonObject;
 
/**
 * ChatBot
 * @author Jiji_Sasidharan
 */
public class WebSocket {
 
    /**
     * main
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) {
		try {
			new CustomEndPoint(8090).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}