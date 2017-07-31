package openlync.principal;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import openlync.utilidades.Criptografia;


public class Configuracao {

	private static String ipServidorMensagens;
	private static String ipServidorDB;
	private static String userDB;
	private static String passDB;
	private static String ipLocal;
	private static int portaSocket;
	
	public static int getPortaSocket() {
		return portaSocket;
	}

	public static void setPortaSocket(int portaSocket) {
		Configuracao.portaSocket = portaSocket;
	}
	
	public static String getIpLocal() {
		return ipLocal;
	}

	public static String getIpServidorMensagens() {
		return ipServidorMensagens;
	}

	public static void setIpServidorMensagens(String ipServidorMensagens) {
		Configuracao.ipServidorMensagens = ipServidorMensagens;
	}

	public static String getIpServidorDB() {
		return ipServidorDB;
	}

	public static void setIpServidorDB(String ipServidorDB) {
		Configuracao.ipServidorDB = ipServidorDB;
	}

	public static String getUserDB() {
		return userDB;
	}

	public static void setUserDB(String userDB) {
		Configuracao.userDB = userDB;
	}

	public static String getPassDB() {
		return passDB;
	}

	public static void setPassDB(String passDB) {
		Configuracao.passDB = passDB;
	}

	public static void setIpLocal(String ipLocal) {
		Configuracao.ipLocal = ipLocal;
	}

	public Configuracao() {
		
		lerCfg();
	}
	
	/**
	 * Le arquivo CPG carregando todos atributos da classe
	 */
	public static void lerCfg() {
		
		Criptografia cript = new Criptografia();
		
		String userHome = System.getProperty("user.home");
		
		FileReader arq = null;
		try {
			arq = new FileReader(userHome+"/.OpenLync.cfg");
			BufferedReader lerArq = new BufferedReader(arq);
			
			ipServidorMensagens = cript.descriptografarMensagem(lerArq.readLine());
			ipServidorDB = cript.descriptografarMensagem(lerArq.readLine());
			userDB = cript.descriptografarMensagem(lerArq.readLine());
			passDB = cript.descriptografarMensagem(lerArq.readLine());
			portaSocket = Integer.parseInt(cript.descriptografarMensagem(lerArq.readLine()));
				
			arq.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Arquivo de configurações não encontrado! Um novo perfil será criado.", "Aviso", 1);
			ipServidorMensagens = "";
			ipServidorDB = "";
			userDB = "";
			passDB = "";
			portaSocket = 0;
			
			//Cria arquivo
			gravarCfg();
		}
	}
	
	/**
	 * Grava arquivo CPG com todos atributos da classe
	 */
	public static void gravarCfg() {
		Criptografia cript = new Criptografia();
		
		String userHome = System.getProperty("user.home");
		
		FileWriter arq = null;
		try {
			arq = new FileWriter(userHome+"/.OpenLync.cfg");
			PrintWriter gravarArq = new PrintWriter(arq);

		    gravarArq.println(cript.criptografarMensagem(ipServidorMensagens));
		    gravarArq.println(cript.criptografarMensagem(ipServidorDB));
		    gravarArq.println(cript.criptografarMensagem(userDB));
		    gravarArq.println(cript.criptografarMensagem(passDB));
		    gravarArq.println(cript.criptografarMensagem(portaSocket+""));
		    
		    arq.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Acessa o servidor (aplicativo) e solicita IP local
	 * 
	 * Se o servidor não responder o Sistema irá travar;
	 * Importante!!: o metodo verificarConexaoBanco deve ser chamado antes desse
	 */
	@SuppressWarnings("resource")
	public static boolean verificarIPlocal() { //TODO
		
		// Conecta ao socket
		Criptografia cript = new Criptografia();
		DataOutputStream out = null;
		boolean result = false;
		
	    try {
	    	
	    	Socket servidor = new Socket(Configuracao.getIpServidorMensagens(), Configuracao.getPortaSocket());
	    	
	    	out = new DataOutputStream(servidor.getOutputStream());
	    	
	    	out.writeUTF(cript.criptografarMensagem("SYSTEM|RETURN IP CLIENT"));
	    	
	    	DataInputStream input = new DataInputStream(servidor.getInputStream());
	    	
	    	Mensagem tratadorMensagem = new Mensagem();
	    	String[] retornoMensagem = tratadorMensagem.tratarMensagem(input.readUTF());
	    	
			//Se for mensagem de sistema
			if (retornoMensagem[Mensagem.IP_REMETENTE].equals("SYSTEM")) {
				//Seta o ip local
				Configuracao.setIpLocal(retornoMensagem[Mensagem.MENSAGEM]);

				result = true;
			} else {
				result = false;
			}
			
		} catch (IOException e1) {
			
			result = false;
		}  finally {
	    	//Encerra a Thread no servidor
	    	try {
				out.writeUTF(cript.criptografarMensagem("SYSTEM|KILL CLIENT"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  
	    return result;
	}
}
