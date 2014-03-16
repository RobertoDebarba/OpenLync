import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class TrataSaida implements Runnable {
	
	private Socket socketSaida;
	private String ipDestino;

	public TrataSaida(Socket socketSaida, String ipDestino) {
		
		this.socketSaida = socketSaida;
		this.ipDestino = ipDestino;
	}
	
	public void run(){
		
		PrintStream PSsaida = null;  

		// Cria printStream
    	try {
			PSsaida = new PrintStream(this.socketSaida.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar PrintStream de saída");
		} 
		
		while (true) {
			
	    	// Manda novas mensagens  
	        System.out.print("Digite uma mensagem: ");
	        @SuppressWarnings("resource")
			Scanner scanMsg = new Scanner(System.in);
	        String mensagem = this.ipDestino + "|" + scanMsg.nextLine(); 
	        
	        PSsaida.println(mensagem);  
		}
	}
}
