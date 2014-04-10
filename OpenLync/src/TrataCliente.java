
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/*
 * Quando um novo cliente se conecta, uma nova Thread é criada com essa classe,
 * que possui um loop infinito para verificar cada mensagem enviada pelo cliente.
 */
public class TrataCliente implements Runnable {
	 
   private Socket Scliente;
   private int portaSaida;
   Mensagens TratadorMensagens = new Mensagens();
   String remetente;
   String msg;
   
   public TrataCliente(Socket cliente, int portaSaida) {
	   this.Scliente = cliente;
	   this.portaSaida = portaSaida;
	   remetente = this.Scliente.getInetAddress().getHostAddress(); // Salva IP do cliente que enviou a mensagem
   }
   
   public void encerrarThread() {
	   	System.out.println("Encerrada a Thread "+ Thread.currentThread().getId());
		Thread.currentThread().interrupt();
   }
 
   public void run() {

	   	System.out.println("Criada a Thread "+ Thread.currentThread().getId());
	   
		Scanner scannerCliente = null;
		try {
			scannerCliente = new Scanner(this.Scliente.getInputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar Scanner do cliente!");
		}
		
		while (scannerCliente.hasNextLine()) {
			
			// Varre mensagem
			String mensg = scannerCliente.nextLine();
			
			this.msg = "";
			TratadorMensagens.setIpDestino("");
			TratadorMensagens.setMensagemTratada("");
			TratadorMensagens.tratarMensagem(mensg);
			
			if (TratadorMensagens.getIpDestino().equals("SYSTEM")) {			// SE for teste de sistema
				
				MsgSistema();				
			} else if (TratadorMensagens.getIpDestino().equals("FILE")) {		//Se for envio de arquivo //FIXME arquivo
				
				MsgArquivo();
			} else {															//Se for mensagem normal
			
				MsgMensagem();
			}
		}
   }
   
   private void MsgSistema() {
	   
	   	if (TratadorMensagens.getMensagemTratada().equals("RETURN IP CLIENT")) {
		   	msg = "SYSTEM|" + remetente;
			
		   	TratadorMensagens.setIpDestino(remetente);
			
			//Envia a mensagem
			TratadorMensagens.enviarMensagem(msg, this.portaSaida);
			
			// Mostra a mensagem enviada ao destinatario com o ip do remetente
			System.out.println(msg);
			
	   	} else if (TratadorMensagens.getMensagemTratada().equals("KILL CLIENT")) {
	   		
	   		encerrarThread();
	   	}
   }
   
   private void MsgArquivo() {  //FIXME arquivo
	   
	   System.out.println("Recebendo arquivo de "+ remetente);
		
	   try {    
           InputStream in = this.Scliente.getInputStream();  
           InputStreamReader isr = new InputStreamReader(in);  
           BufferedReader reader = new BufferedReader(isr);  
           String fName = reader.readLine();  
           System.out.println("Nome do arquivo: "+ fName);  
           File f1 = new File("/home/roberto/joao2.jpg");  
           @SuppressWarnings("resource")
			FileOutputStream out = new FileOutputStream(f1);  
 
           int tamanho = 4096; // buffer de 4KB    
           byte[] buffer = new byte[tamanho];    
           int lidos = -1;
           while ((lidos = in.read(buffer, 0, tamanho)) != -1) {     
               out.write(buffer, 0, lidos);    
           }    
           
           System.out.println("Recebimento do arquivo concluido!");
           out.flush();    
           
       } catch (IOException e) {  
       		System.out.println("Erro ao receber arquivo!");
       		e.printStackTrace();
       }
   }
   
   private void MsgMensagem() {
	   
	   msg = remetente + "|" + TratadorMensagens.getMensagemTratada();
		
	   //Envia a mensagem
	   TratadorMensagens.enviarMensagem(msg, this.portaSaida);
		
	   // Mostra a mensagem enviada ao destinatario com o ip do remetente
	   System.out.println(msg);
   }
}