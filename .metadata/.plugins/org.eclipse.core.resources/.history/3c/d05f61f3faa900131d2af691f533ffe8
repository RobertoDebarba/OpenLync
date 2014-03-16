import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


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
        PrintStream PSsaida = null;  
        
        // ---------------------------------------------------------------------------------
		// Conecta ao socket
		try {
    	socketSaida = new Socket(this.ipServidor, this.portaSaida);  
    	System.out.println("Criada conexão com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		} catch(IOException e) {
			System.out.println("Erro ao conectar com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		}
    	
		// Cria printStream
    	try {
			PSsaida = new PrintStream(socketSaida.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar PrintStream de saída");
		} 
    	
    	// Manda novas mensagens   //TODO separa em classe
        System.out.print("Digite uma mensagem: ");
		Scanner scanMsg = new Scanner(System.in);
        String mensagem = this.ipDestino + "|" + scanMsg.nextLine(); 
        
        PSsaida.println(mensagem);  
	}
}
