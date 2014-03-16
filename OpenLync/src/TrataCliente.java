import java.io.IOException;
import java.io.PrintStream;
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
				 
				//OpenLync.mandaMensagem(ipDestino, msg);
				 
				 //-------------------------------------------------------
				 Socket Sdestino = null;
				 PrintStream PSdestino = null;
				 
				 try {
					   Sdestino = new Socket(ipDestino, 7606);  // Cria uma porta acima para nao dar conflito
					   PSdestino = new PrintStream(Sdestino.getOutputStream());
					   PSdestino.println(msg); //Envia a bagaça
				   } catch (IOException e) {
					   System.out.println("Não foi possivel estabelecer conexão com destinatario!");
				   }
				 
				 try {
					Sdestino.close();
					PSdestino.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				 
				 
				
				System.out.println(msg); // printa mensagem para teste

			 }
			 s.close();
	   }
}