import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/*
 * Quando um novo cliente se conecta, uma nova Thread é criada com essa classe,
 * que possui um loop infinito para verificar cada mensagem enviada pelo cliente.
 */
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
			TratadorMensagens.tratarMensagem(scannerCliente.nextLine());
			 
			String msg = remetente + "|" + TratadorMensagens.getMensagemTratada();
			
			//Envia a mensagem
			TratadorMensagens.enviarMensagem(msg, this.portaSaida);
			
			// Mostra a mensagem enviada ao destinatario com o ip do remetente
			System.out.println(msg);

		}
   }
}