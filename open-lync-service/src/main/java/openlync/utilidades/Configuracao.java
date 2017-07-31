package openlync.utilidades;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Configuracao {

	private static int portaSocket;

	public static int getPortaSocket() {
		return portaSocket;
	}

	public static void setPortaSocket(int portaSocket) {
		Configuracao.portaSocket = portaSocket;
	}

	public Configuracao() {
		
		lerCfg();
	}
	
	/**
	 * Le arquivo CPG carregando todos atributos da classe
	 */
	public static void lerCfg() {
		String userHome = System.getProperty("user.home");
		
		FileReader arq = null;
		try {
			arq = new FileReader(userHome+"/.OpenLync_service.cfg");
			BufferedReader lerArq = new BufferedReader(arq);
			
			portaSocket = Integer.parseInt(lerArq.readLine());
			
			arq.close();
			
		} catch (IOException e) {
			System.out.println("Arquivo de configuração ("+System.getProperty("user.home")+"/.OpenLync_service.cfg) não encontrado. Uma nova configuração sera criada.");
			
			portaSocket = 0;
			
			//Cria arquivo
			gravarCfg();
		}
	}
	
	/**
	 * Grava arquivo CPG com todos atributos da classe
	 */
	public static void gravarCfg() {
		String userHome = System.getProperty("user.home");
		
		FileWriter arq = null;
		try {
			arq = new FileWriter(userHome+"/.OpenLync_service.cfg");
			PrintWriter gravarArq = new PrintWriter(arq);

		    gravarArq.println(portaSocket+"");
		    
		    arq.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
