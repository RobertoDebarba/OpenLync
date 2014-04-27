//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1
 
import java.io.IOException;  
import java.net.ServerSocket;  

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class OpenLync {
	
	private static int portaEntrada;
	private static int portaSaida;
	
	private static FormMain frmMain;
	
	private static Thread TClientes;
	private static ServerSocket servidor;

	public static void main(String[] args) throws IOException {
		new OpenLync();
	}
			   
	public int getPortaEntrada() {
		return portaEntrada;
	}

	public static void setPortaEntrada(int portaEntrada) {
		OpenLync.portaEntrada = portaEntrada;
	}

	public OpenLync () {
		portaEntrada = 7609;
		portaSaida = 7606;
		
		//Cria Form
		FormMain frmMain = new FormMain();
		frmMain.setVisible(true);
		
		
		
//		frmMain = new FormMain();
//		frmMain.editPorta.setText(""+portaEntrada);
//		
//		frmMain.setStatus(false);
//		frmMain.setStatusDB(false);
		
	}

	public static void iniciarServidor() {
		
		servidor = null;
		try {
			servidor = new ServerSocket(portaEntrada);
			System.out.println("Servidor iniciado na porta "+ portaEntrada +"!");
		} catch(IOException e) {
			System.out.println("Erro ao criar servidor na porta "+ portaEntrada);
		}
		
		// Cria Thread para verificar entrada de novos clientes	     
		NovosClientes verifNovosClientes = new NovosClientes(servidor, portaSaida);
		TClientes = new Thread(verifNovosClientes);
		TClientes.start();
		
		//frmMain.setStatus(true);
	}
	
	public static void pararServidor() {
		try {
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TClientes.interrupt();
		
		System.out.println("Servidor parado!");
		
		//frmMain.setStatus(false);
	}
}
