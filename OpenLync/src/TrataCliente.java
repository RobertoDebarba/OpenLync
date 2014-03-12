import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TrataCliente implements Runnable {
	 
	   private Socket cliente;
	 
	   public TrataCliente(Socket cliente) {
	     this.cliente = cliente;
	   }
	 
	   public void run() {
			// quando chegar uma msg, distribui pra todos
			 
			// Inicia Imput Strim
			Scanner s = null;
			// Instancia imout strem
			try {
				s = new Scanner(this.cliente.getInputStream());
			} catch (IOException e) {
			}
			
			 while (s.hasNextLine()) {
				 //servidor.distribuiMensagem(s.nextLine());
				 
				 // Aqui começa as pira
				 // Verificar o IP do cliente e mardar ------------------------------------------------
				 String remetente = cliente.getInetAddress().getHostAddress(); // Salva ip do servidor em var remetente
				 
				 // Varre linha para capturar ip de destino ---
				 
				 boolean pParte = true;
				 String ipDestino = "";
				 String mensagem = "";
				 
				 String sLinha = s.nextLine();  // "nextLine()" só pode ser usado uma vez
				 System.out.println("com destino = " + sLinha); //FIXME remover
				 
				 for(int i=0;i<(sLinha).length();i++){  
					   char c = (sLinha).charAt(i);
					   
					   if (c == '|') {
						   pParte = false;
					   }
					   else if (pParte) {
						   ipDestino = ipDestino + c;
					   }
					   else if (!pParte) {
						   mensagem = mensagem + c;
					   }
				 }  
				 
				 String msg = remetente + "|" + mensagem;
				 
				 System.out.println("com remetente = " + msg); //FIXME remover
				 
				 // Envia mensagem (seja o que Deus quiser) ---
				 
				OpenLync.mandaMensagem(ipDestino, msg);

			 }
			 s.close();
	   }
}