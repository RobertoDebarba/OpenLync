import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class TrataEntrada implements Runnable {
	
	private Socket servidor;
	
	public TrataEntrada(Socket servidor) { // Construtor
		this.servidor = servidor;
	}
	
	public void run() {
		
		// Cria scanner
		Scanner Sservidor = null;
		
		try {
			Sservidor = new Scanner(this.servidor.getInputStream());
		} catch (IOException e) {
		}
		
		// Le mensagem quando enviada
		String mensagem;
		while (Sservidor.hasNextLine()) {
			mensagem = Sservidor.nextLine();
			
			// Aqui acontece algo com a mensagem recebida -----------------------
			System.out.println(mensagem);
			
			//-------------------------------------------------------------------
			
		}
		
		Sservidor.close();
		
	}
	


}
