//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1
 
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;

public class OpenLync {
	
	private int portaEntrada;
	private int portaSaida;

	public static void main(String[] args) throws IOException {
		new OpenLync().iniciarServidor();
	}
			   
	public OpenLync () {
		this.portaEntrada = 7609;
		this.portaSaida = 7606;
	}
			   
	@SuppressWarnings("resource")
	public void iniciarServidor() throws IOException {
		ServerSocket servidor = null;
		try {
			servidor = new ServerSocket(this.portaEntrada);
			System.out.println("Servidor iniciado na porta "+ this.portaEntrada +"!");
		} catch(IOException e) {
			System.out.println("Erro ao criar servidor na porta "+ this.portaEntrada);
		}
			     
		while (true) {
			// aceita um cliente
			Socket cliente = servidor.accept();
			System.out.println("Nova conexão com o cliente " +   
			cliente.getInetAddress().getHostAddress()
			);
			   
			// cria tratador de cliente numa nova thread
			TrataCliente tc = new TrataCliente(cliente, portaSaida);
			new Thread(tc).start();
		} 
	}
}
