import java.io.IOException;
import java.net.ServerSocket;

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
		
		// Abre porta de conexão
		try {
			SSentrada = new ServerSocket(this.portaEntrada);
		} catch(IOException e) {
			System.out.println("Erro ao receber conexão do servidor! ServerSocket");
		}
		
		System.out.println("Porta "+this.portaEntrada+" aberta! ServerSocket OK");
	    
	    System.out.println("Conectou ao servidor! Socket OK");
	    
		// Cria objeto que ficará lendo entradas e manda para Thread
	    TrataEntrada te = new TrataEntrada(SSentrada);
	    //TrataEntrada te = new TrataEntrada(socketEntrada);
	    new Thread(te).start();
	}
	
}
