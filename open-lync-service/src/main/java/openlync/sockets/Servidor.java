package openlync.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import openlync.utilidades.Configuracao;

public class Servidor implements Runnable {
	
	public static List<ThreadCliente> listaClientes = new ArrayList<ThreadCliente>();
	private ServerSocket servidor = null;
	private boolean isExecutando;
	
	public void run() {
		try {
			isExecutando = true;
			servidor = new ServerSocket(Configuracao.getPortaSocket());
			
			while (isExecutando) {
				
				Socket socketCliente = null;
				try {
					socketCliente = servidor.accept();
					System.out.println("Nova conexão com o cliente "+socketCliente.getInetAddress().getHostAddress());
					
					ThreadCliente cliente = new ThreadCliente(new Cliente(socketCliente));
					
					listaClientes.add(cliente);
					cliente.start();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void interrupt() {
		try {
			isExecutando = false;
			servidor.close();
			System.out.println("ServerSocket parado!");
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
