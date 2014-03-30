//Referencias:
//	
//	http://www.guj.com.br/articles/126

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class OpenLync_client {
	
	private static String ipServidor;
	private static int portaEntrada;
	private static int portaSaida; 
	
	private static String ipLocal = "";
	
	public OpenLync_client() {
		
	}
	
	public static String getIpLocal() {
		return ipLocal;
	}

	public static void setIpLocal(String ipLocal) {
		OpenLync_client.ipLocal = ipLocal;
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
	
	@SuppressWarnings("resource")
	public static boolean verificarConexaoServidor() {
		
		// Conecta ao socket
		Socket socketSaida = null;
	    try {
	    	ServerSocket SSentrada = new ServerSocket(portaEntrada);
	    	
	    	
			socketSaida = new Socket(ipServidor, portaSaida);
			
			PrintStream PSsaida = new PrintStream(socketSaida.getOutputStream());
			PSsaida.println("TESTCONNECTION|RETURN IP CLIENT"); 
			
			// Recebe mensagem de resposta
			Socket socketEntrada = SSentrada.accept();
			
			Scanner s = new Scanner(socketEntrada.getInputStream());
			String msg = s.nextLine();
			
			Mensagens TratadorMensagens = new Mensagens();
			TratadorMensagens.tratarMensagem(msg);
			
			//Se for mensagem de sistema
			if (TratadorMensagens.getIpRemetente().equals("TESTCONNECTION")) { //Aqui IP representa a mensagem de SISTEMA
				OpenLync_client.setIpLocal(TratadorMensagens.getMensagemTratada());
			
				SSentrada.close();
				socketSaida.close();
				PSsaida.close();
				s.close();
				
				return true;
			} else {
				return false;
			}
			
		} catch (IOException e1) {
			return false;
		}  
	    
	}

	public static void main(String[] args) {
		
		FormMain frmMain = new FormMain();
		frmMain.abrirTela();
		
		FormMain.abrirFrmLogin();
		
		IniciaVariaveis(); //FIXME
	}
	
	public static void iniciarEntrada() {
		
		// Instancia objeto que cuidará da entrada de dados e manda para uma thread
		EntradaDados ed = new EntradaDados(portaEntrada);
		new Thread(ed).start();
	}
}
