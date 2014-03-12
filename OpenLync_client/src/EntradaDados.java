import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class EntradaDados implements Runnable {
	
	private int portaEntrada;

	public EntradaDados(int portaEntrada) {
		this.portaEntrada = portaEntrada;
	}
	
	public void run() {
		AbreConexao();
	}
	
	private void AbreConexao() {
		ServerSocket SSentrada = null;
		Socket socketEntrada = null;
		
		// Abre porta de conexão
		try {
			SSentrada = new ServerSocket(this.portaEntrada);
		} catch(IOException e) {
			System.out.println("Erro ao receber conexão do servidor! ServerSocket");
		}
		
		System.out.println("Porta "+this.portaEntrada+" aberta! ServerSocket OK");
	    
		// Aceita entrada do servidor
	    try {
	    	socketEntrada = SSentrada.accept();
	    } catch(IOException e) {
	    	System.out.println("Erro ao receber conexão do servidor! Socket");
	    }
	    
	    System.out.println("Conectou ao servidor! Socket OK");
	    
		// Cria objeto que ficará lendo entradas e manda para Thread
	    TrataEntrada te = new TrataEntrada(socketEntrada);
	    new Thread(te).start();
	}
	
}
