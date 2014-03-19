//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1
 
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;

public class OpenLync {
	
	
	
	public static void main(String[] args) throws IOException {
	     // inicia o servidor
	     new OpenLync(7609).executa();
	   }
	   
	   private int porta;
	   
	   public OpenLync (int porta) {
	     this.porta = porta;
	   }
	   
	   @SuppressWarnings("resource")
	public void executa () throws IOException {
		 ServerSocket servidor = null;
		try {
		servidor = new ServerSocket(this.porta);
	     System.out.println("Porta 7600 aberta!");
		} catch(IOException e) {
		}
	     
	     while (true) {
	       // aceita um cliente
	       Socket cliente = servidor.accept();
	       System.out.println("Nova conexão com o cliente " +   
	         cliente.getInetAddress().getHostAddress()
	       );
	       
	       // cria tratador de cliente numa nova thread
	       TrataCliente tc = new TrataCliente(cliente);
	       new Thread(tc).start();
	     }
	 
	   }
	   
	 }
