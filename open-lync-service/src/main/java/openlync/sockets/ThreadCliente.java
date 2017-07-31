package openlync.sockets;

import java.net.Socket;

public class ThreadCliente extends Thread {
	
	private String ipCliente;
	private Socket socket;

	public String getIpCliente() {
		return ipCliente;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public ThreadCliente(Cliente cliente) {
		super(cliente);
		
		this.ipCliente = cliente.getIp();
		this.socket = cliente.getSocket();
	}
}
