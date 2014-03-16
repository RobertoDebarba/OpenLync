//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1
 
import java.io.IOException;  
import java.io.PrintStream;
import java.net.ServerSocket;  
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class OpenLync {
	
	
	
	public static void main(String[] args) throws IOException {
	     // inicia o servidor
	     new OpenLync(7609).executa();
	   }
	   
	   private int porta;
	   private List<PrintStream> clientes;
	   
	   public OpenLync (int porta) {
	     this.porta = porta;
	     this.clientes = new ArrayList<PrintStream>();
	   }
	   
	   public void executa () throws IOException {
	     ServerSocket servidor = new ServerSocket(this.porta);
	     System.out.println("Porta 7600 aberta!");
	     
	     while (true) {
	       // aceita um cliente
	       Socket cliente = servidor.accept();
	       System.out.println("Nova conexão com o cliente " +   
	         cliente.getInetAddress().getHostAddress()
	       );
	       
	       // adiciona saida do cliente à lista
	       PrintStream ps = new PrintStream(cliente.getOutputStream());
	       this.clientes.add(ps);
	       
	       // cria tratador de cliente numa nova thread
	       TrataCliente tc = new TrataCliente(cliente);
	       new Thread(tc).start();
	     }
	 
	   }
	 
	/*   public void distribuiMensagem(String msg) {          Usado para distribuir para todos
	     // envia msg para todo mundo
	     for (PrintStream cliente : this.clientes) {
	       cliente.println(msg);
	     }
	   }*/
	   public static void mandaMensagem(String ipDestino, String msg){		   
		   
		   
		   
		   
		   // Até aqui obtive ajuda divina!
	   }
	   
	 }
