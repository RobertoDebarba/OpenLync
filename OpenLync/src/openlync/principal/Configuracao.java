package openlync.principal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import openlync.utilidades.Criptografia;

public class Configuracao {

	private static String ipServidorDB;
	private static String userDB;
	private static String passDB;
	private static int portaSocket;

	public static int getPortaSocket() {
		return portaSocket;
	}

	public static void setPortaSocket(int portaSocket) {
		Configuracao.portaSocket = portaSocket;
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
			arq = new FileReader(userHome+"/.OpenLyncServer.cfg");
			BufferedReader lerArq = new BufferedReader(arq);
			
			ipServidorDB = cript.descriptografarMensagem(lerArq.readLine());
			userDB = cript.descriptografarMensagem(lerArq.readLine());
			passDB = cript.descriptografarMensagem(lerArq.readLine());
			portaSocket = Integer.parseInt(cript.descriptografarMensagem(lerArq.readLine()));
			
			arq.close();
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, "Arquivo de configurações não encontrado! Um novo perfil será criado.", "Aviso", 1);
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
			arq = new FileWriter(userHome+"/.OpenLyncServer.cfg");
			PrintWriter gravarArq = new PrintWriter(arq);

		    gravarArq.println(cript.criptografarMensagem(ipServidorDB));
		    gravarArq.println(cript.criptografarMensagem(userDB));
		    gravarArq.println(cript.criptografarMensagem(passDB));
		    gravarArq.println(cript.criptografarMensagem(portaSocket+""));
		    
		    arq.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
