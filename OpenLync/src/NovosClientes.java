import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Esta classe é uma implementação para um Thread que fica infinitamente
 * verificando se novos clientes se conectam.
 * Nesse caso ele chama a classe TrataCliente em outra Thread que possui um loop
 * infinito para ler os envios do cliente.
 * É criada uma nova Thread para cada novo cliente.
 */
public class NovosClientes implements Runnable {

	private ServerSocket servidor;
	private int portaSaida;
	
	public NovosClientes(ServerSocket servidor, int portaSaida) {
		this.servidor = servidor;
		this.portaSaida = portaSaida;
	}

	public void run() {
		
		while (true) {
			// aceita um cliente
			Socket cliente = null;
			try {
				cliente = this.servidor.accept();
			} catch (IOException e) {
			}
			OpenLync.frmMain.frmInicial.adicionarLog("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
			   
			// Cada novo cliente recebe uma Thread onde é tratado
			TrataCliente tc = new TrataCliente(cliente, portaSaida);
			new Thread(tc).start();
		} 
	}
}
