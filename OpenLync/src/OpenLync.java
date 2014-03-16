//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1
 
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;

public class OpenLync {

	public static void main(String[] args) throws IOException {
	     new OpenLync().executa();
	}
	   
	private int porta;
	private int portaSaida;
	private ServerSocket SSservidor;
	private Socket Scliente;
   
	public OpenLync() {
		this.porta = 7609;
		this.portaSaida = 7608;
	}
   
	public void executa () throws IOException {
		
		// Abre porta do servidor
		try {
			SSservidor = new ServerSocket(this.porta);
			System.out.println("Porta "+this.porta+" aberta!");
		} catch (IOException e) {
			System.out.println("Erro ao criar ServerSocket na porta " + this.porta);
		}
		
		while (true) {
			// aceita um cliente
			Scliente = SSservidor.accept();
			System.out.println("Nova conexão com o cliente " + Scliente.getInetAddress().getHostAddress());
   
			// cria tratador de cliente numa nova thread
			TrataCliente tc = new TrataCliente(Scliente, portaSaida);
			new Thread(tc).start();
		}
 
	}
   
 }
