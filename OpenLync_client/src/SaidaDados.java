import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

public class SaidaDados implements Runnable {

	private Socket socketSaida = null;
	private PrintStream PSsaida = null;
	
	private String ipServidor;
	private int portaSaida; 
	private String ipDestino;
	
	public SaidaDados(String ipDestino) {
		this.ipServidor = OpenLync_client.getIpServidor();
		this.portaSaida = OpenLync_client.getPortaSaida();
		this.ipDestino = ipDestino;
	}
	
	public void run() {

		// Conecta ao socket
		try {
    	socketSaida = new Socket(this.ipServidor, this.portaSaida);  
    	System.out.println("Criada conexão com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		} catch(IOException e) {
			System.out.println("Erro ao conectar com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		}

		// Cria printStream
		PSsaida = null;  
    	try {
			PSsaida = new PrintStream(this.socketSaida.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar PrintStream de saída");
		} 
	}
	
	public void enviarMensagem(String msg) {
		String mensagem = this.ipDestino + "|" + msg; 
        
        PSsaida.println(mensagem); 
	}
	
	@SuppressWarnings("resource")
	public void enviarArquivo() {
		try {
			PSsaida.println("FILE|ipqualquer");
			
			FileInputStream in;
			in = new FileInputStream("/home/roberto/joao.jpg");
			
			OutputStream out = this.socketSaida.getOutputStream();
			
			OutputStreamWriter osw;
			osw = new OutputStreamWriter(out);
			
			 
			BufferedWriter writer = new BufferedWriter(osw);
			 
			
			writer.write("joao.jpg" + "\n");
		    writer.flush(); 
		    
		    Thread.sleep(1000); //QG gambiara master - Sem esse sleep o arquivo é enviado com 0b //FIXME
			 
		    int tamanho = 4096; // buffer de 4KB 
		    byte[] buffer = new byte[tamanho];    
	        
		    int lidos = -1;
		    while ((lidos = in.read(buffer, 0, tamanho)) != -1) {    
	            out.write(buffer, 0, lidos);    
		    }  
		} catch (IOException | InterruptedException  e) {
			System.out.println("Erro ao enviar arquivo ao servidor!");
			e.printStackTrace();
		}
		 
	}
}
