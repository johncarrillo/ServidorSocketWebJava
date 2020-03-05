
import UFPS.Json.Posicion;
import UFPS.Json.RegistroUsuarioJson;
import UFPS.model.Usuario;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jjcarrillo
 */
public class ConsolaFront implements Runnable{
    Scanner sc = null;
    WSClient clienteWs;
    Usuario usuario;
    
    public ConsolaFront () {
        sc = new Scanner(System.in);
        pedirNombre();
        WSClient.main(this.usuario, this);
    }

    public RegistroUsuarioJson pedirNombre () {
        System.out.println("Escriba su nombre de usuario:");
        String nombre = sc.nextLine();
        try {
			usuario = new Usuario(nombre);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        RegistroUsuarioJson registroUsuario = new RegistroUsuarioJson(nombre);
        return registroUsuario;
    }

    public Posicion pedirPosicionMovimiento () {
        System.out.println("Posición:");
        Posicion posicion = null;
        while (true) {
            String pocision = sc.nextLine();
            if (Pattern.matches("(0|1|2|3|4|5|6|7|8)", pocision)) {
                posicion = new Posicion(pocision);
                break;
            } else {
                System.out.println("solo se puede poner un posición de 0 a 8");
            }
        }
        clienteWs.enviarMovimiento(posicion);
        return posicion;
    }

    public void transformarTablero (char[] tablero) {
            System.out.println("[" + tablero[0] +  "] " + "[" + tablero[1] +  "] " + "[" + tablero[2] +  "] ");
            System.out.println("[" + tablero[3] +  "] " + "[" + tablero[4] +  "] " + "[" + tablero[5] +  "] ");
            System.out.println("[" + tablero[6] +  "] " + "[" + tablero[7] +  "] " + "[" + tablero[8] +  "] ");
        }
    
    public static void main (String[] args) {
         new ConsolaFront();
    }

	public void run() {
		while (true) {
            try {
                Thread.sleep(1000);
                if (clienteWs.tablero != null) {
                    System.out.println("mensaje: \n" + WSClient.mensaje);
                    this.transformarTablero(clienteWs.tablero);
                    this.pedirPosicionMovimiento();
                    clienteWs.limpiarTablero();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ConsolaFront.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	}
}
