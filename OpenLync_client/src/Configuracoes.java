import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Configuracoes {

	private static String ipServidor;
	private static String ipLocal;
	private static int portaEntrada = 7606;
	private static int portaSaida = 7609;
	
	public static String getIpServidor() {
		return ipServidor;
	}

	/**
	 * Seta variavel e escreve no arquivo CFG o ip do servidor
	 */
	public static void setIpServidor(String ipServidor) {
		
		String userHome = System.getProperty("user.home");
		
		FileWriter arq = null;
		try {
			arq = new FileWriter(userHome+"/.OpenLync.cfg");
			PrintWriter gravarArq = new PrintWriter(arq);

		    gravarArq.printf(ipServidor);
		    Configuracoes.ipServidor = ipServidor;
		    
		    arq.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getIpLocal() {
		return ipLocal;
	}

	public static void setIpLocal(String ipLocal) {
		Configuracoes.ipLocal = ipLocal;
	}

	public static int getPortaEntrada() {
		return portaEntrada;
	}

	public static void setPortaEntrada(int portaEntrada) {
		Configuracoes.portaEntrada = portaEntrada;
	}

	public static int getPortaSaida() {
		return portaSaida;
	}

	public static void setPortaSaida(int portaSaida) {
		Configuracoes.portaSaida = portaSaida;
	}

	public Configuracoes() {
		
		lerCfgGetIP();
	}
	
	/**
	 * Le arquivo CPG e resgata IP do servidor salvo
	 */
	private static void lerCfgGetIP() {
		
		String userHome = System.getProperty("user.home");
		
		FileReader arq = null;
		try {
			arq = new FileReader(userHome+"/.OpenLync.cfg");
			BufferedReader lerArq = new BufferedReader(arq);
			
			ipServidor = lerArq.readLine(); // Define ip do servidor
			
			arq.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Arquivo de configurações não encontrado! Um novo perfil será criado.", "Aviso", 1);
			ipServidor = "";
			
			//Cria arquivo
			setIpServidor("");
		}
	}
	
	/**
	 * Acessa o servidor (aplicativo) e solicita IP local
	 * 
	 * Se o servidor não responder o Sistema irá travar;
	 * Importante!!: o metodo verificarConexaoBanco deve ser chamado antes desse
	 */
	@SuppressWarnings("resource")
	public static boolean verificarIPlocal() {
		
		// Conecta ao socket
		Socket socketSaida = null;
	    try {
	    	Criptografia cript = new Criptografia();
	    	
	    	ServerSocket SSentrada = new ServerSocket(Configuracoes.getPortaEntrada());
	    	
			socketSaida = new Socket(Configuracoes.getIpServidor(), Configuracoes.getPortaSaida());
			
			PrintStream PSsaida = new PrintStream(socketSaida.getOutputStream());
			//Criptografa e manda solicitação
			PSsaida.println(cript.criptografarMensagem("SYSTEM|RETURN IP CLIENT")); 
			
			// Recebe mensagem de resposta
			Socket socketEntrada = SSentrada.accept();
			
			Scanner s = new Scanner(socketEntrada.getInputStream());
			String msg = s.nextLine();
			
			Mensagens TratadorMensagens = new Mensagens();
			//Descriptografa e trata mensagem
			TratadorMensagens.tratarMensagem(cript.descriptografarMensagem(msg));
			
			//Se for mensagem de sistema
			if (TratadorMensagens.getIpRemetente().equals("SYSTEM")) { //Aqui IP representa a mensagem de SISTEMA
				//Seta o ip local
				Configuracoes.setIpLocal(TratadorMensagens.getMensagemTratada());
			
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
}
