//Referencias:
//	
//	http://www.guj.com.br/articles/126

import java.io.IOException;  
import java.util.Scanner;
import java.sql.*;

public class OpenLync_client {
	
	private static String ipServidor;
	private static int portaEntrada;
	private static int portaSaida; 
	
	public OpenLync_client() {
		
	}
	
	public static String getIpServidor() {
		return ipServidor;
	}

	public static void setIpServidor(String ipServidor) {
		OpenLync_client.ipServidor = ipServidor;
	}

	public static int getPortaEntrada() {
		return portaEntrada;
	}

	public static void setPortaEntrada(int portaEntrada) {
		OpenLync_client.portaEntrada = portaEntrada;
	}

	public static int getPortaSaida() {
		return portaSaida;
	}

	public static void setPortaSaida(int portaSaida) {
		OpenLync_client.portaSaida = portaSaida;
	}

	private static void IniciaVariaveis() {
		ipServidor = "192.168.152.1"; // Define ip do servidor
		portaEntrada = 7606;  //Define porta de entrada de dados; Servidor -> Cliente
		portaSaida = 7609;// Define porta de sáida de dados; Cliente -> Servidor
	}

	public static void main(String[] args) {
		
		FormMain frmMain = new FormMain();
		frmMain.abrirTela();
		
		FormMain.abrirFrmLogin();
		
		
		IniciaVariaveis();
		
		// Instancia objeto que cuidará da entrada de dados e manda para uma thread
		EntradaDados ed = new EntradaDados(portaEntrada);
		new Thread(ed).start();
		
//		// Instancia objeto que cuidará da saida de dados e manda para uma thered
//		SaidaDados sd = new SaidaDados(ipServidor, portaSaida, ipDestino);
//		new Thread(sd).start();
//		*/
	}

}
