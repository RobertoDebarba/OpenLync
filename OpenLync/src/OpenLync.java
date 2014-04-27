//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1
 
import java.io.IOException;  
import java.net.ServerSocket;  


public class OpenLync {
	
	private static int portaEntrada;
	private static int portaSaida;
	
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
		
	}

	public static void iniciarServidor() {
		
		servidor = null;
		try {
			servidor = new ServerSocket(portaEntrada);
			FormMain.frmInicial.adicionarLog("Servidor iniciado na porta "+ portaEntrada +"!");
		} catch(IOException e) {
			FormMain.frmInicial.adicionarLog("Erro ao criar servidor na porta "+ portaEntrada);
		}
		
		// Cria Thread para verificar entrada de novos clientes	     
		NovosClientes verifNovosClientes = new NovosClientes(servidor, portaSaida);
		TClientes = new Thread(verifNovosClientes);
		TClientes.start();

	}
	
	public static void pararServidor() {
		try {
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TClientes.interrupt();
		
		FormMain.frmInicial.adicionarLog("Servidor parado!");

	}
}
