import java.io.IOException;
import java.net.Socket;

public class SaidaDados implements Runnable {

	private String ipServidor;
	private int portaSaida; 
	private String ipDestino;
	
	public SaidaDados(String ipServidor, int portaSaida, String ipDestino) {
		this.ipServidor = ipServidor;
		this.portaSaida = portaSaida;
		this.ipDestino = ipDestino;
	}
	
	public void run() {
		// Cria objetos necessarios
        Socket socketSaida = null;  //TODO tentar enviar e receber pelo mesmo ServerSocket
        
		// Conecta ao socket
		try {
    	socketSaida = new Socket(this.ipServidor, this.portaSaida);  
    	System.out.println("Criada conexão com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		} catch(IOException e) {
			System.out.println("Erro ao conectar com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		}
    	
		// Cria objeto que ficará enviando mensagens e coloca na Thread
		TrataSaida ts = new TrataSaida(socketSaida, this.ipDestino);
		new Thread(ts).start();
	}
}
