import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TrataCliente implements Runnable {
	 
   private Socket Scliente;
   private int portaSaida;
 
   public TrataCliente(Socket cliente, int portaSaida) {
     this.Scliente = cliente;
     this.portaSaida = portaSaida;
   }
 
   public void run() {

		Scanner scannerCliente = null;
		try {
			scannerCliente = new Scanner(this.Scliente.getInputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar Scanner do cliente!");
		}
		
		while (scannerCliente.hasNextLine()) {

			// Salva IP do cliente que enviou a mensagem
			String remetente = this.Scliente.getInetAddress().getHostAddress();
			 
			// Varre mensagem
			Mensagens TratadorMensagens = new Mensagens();
			TratadorMensagens.tratarMensagens(scannerCliente.nextLine());
			 
			String msg = remetente + "|" + TratadorMensagens.getMensagemTratada();
			
			Socket Sdestino = null;
			PrintStream PSdestino = null;
			try {
				Sdestino = new Socket(TratadorMensagens.getIpDestino(), this.portaSaida);
				PSdestino = new PrintStream(Sdestino.getOutputStream());
				PSdestino.println(msg); //Envia a mensagem
			} catch (IOException e) {
				System.out.println("Não foi possivel estabelecer conexão com destinatario!");
			}
			
			// Limpa objetos por causa do loop
			try {
				Sdestino.close();
				PSdestino.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println(msg); // Mostra a mensagem enviada ao destinatario com o ip do remetente

		}
   }
}