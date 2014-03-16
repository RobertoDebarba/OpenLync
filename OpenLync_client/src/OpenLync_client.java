//Referencias:
//	
//	http://www.guj.com.br/articles/126

import java.io.IOException;  
import java.util.Scanner;

public class OpenLync_client {
	
	private static String ipServidor;
	private static int portaEntrada;
	private static int portaSaida; 
	private static String ipDestino;
	
	public OpenLync_client() {
		
	}
	
	private static void IniciaVariaveis() {
		ipServidor = "192.168.152.1"; // Define ip do servidor
		portaEntrada = 7606;  //Define porta de entrada de dados; Servidor -> Cliente
		portaSaida = 7609;// Define porta de sáida de dados; Cliente -> Servidor
		
		// Define ip destino para comunicação
		System.out.print("Digite o ip com o qual deseja se comunicar: ");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		ipDestino = scan.nextLine();  
	}

	public static void main(String[] args) throws IOException {
		
		IniciaVariaveis();
		
		// Instancia objeto que cuidará da entrada de dados e manda para uma thread
		EntradaDados ed = new EntradaDados(portaEntrada);
		new Thread(ed).start();
		
		// Instancia objeto que cuidará da saida de dados e manda para uma thered
		SaidaDados sd = new SaidaDados(ipServidor, portaSaida, ipDestino);
		new Thread(sd).start();
		
	}

}
