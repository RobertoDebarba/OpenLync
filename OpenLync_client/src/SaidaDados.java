import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SaidaDados implements Runnable {

	private Socket socketSaida = null;
	private PrintStream PSsaida = null;
	
	private String ipServidor;
	private int portaSaida; 
	private String ipDestino;
	
	public SaidaDados(String ipDestino) {
		this.ipServidor = OpenLync_client.getIpServidor();
		this.portaSaida = OpenLync_client.getPortaSaida();
		this.ipDestino = ipDestino;
	}
	
	public void run() {

		// Conecta ao socket
		try {
    	socketSaida = new Socket(this.ipServidor, this.portaSaida);  
    	System.out.println("Criada conexão com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		} catch(IOException e) {
			System.out.println("Erro ao conectar com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		}
		
		
		PSsaida = null;  

		// Cria printStream
    	try {
			PSsaida = new PrintStream(this.socketSaida.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar PrintStream de saída");
		} 
		
		
		// Cria objeto que ficará enviando mensagens e coloca na Thread
//		TrataSaida ts = new TrataSaida(this.socketSaida, this.ipDestino);
//		new Thread(ts).start();
	}
	
	public void enviarMensagem(String msg) {
		String mensagem = this.ipDestino + "|" + msg; 
        
        PSsaida.println(mensagem); 
	}
}
