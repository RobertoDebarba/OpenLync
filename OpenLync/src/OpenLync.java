//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1
 
import java.io.IOException;  
import java.net.ServerSocket;  

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

	public void iniciarServidor() throws IOException {
		ServerSocket servidor = null;
		try {
			servidor = new ServerSocket(this.portaEntrada);
			System.out.println("Servidor iniciado na porta "+ this.portaEntrada +"!");
		} catch(IOException e) {
			System.out.println("Erro ao criar servidor na porta "+ this.portaEntrada);
		}
		
		// Cria Thread para verificar entrada de novos clientes	     
		NovosClientes verifNovosClientes = new NovosClientes(servidor, portaSaida);
		new Thread(verifNovosClientes).start();
	}
}
