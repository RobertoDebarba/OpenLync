package openlync.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import openlync.principal.Configuracao;
import openlync.principal.OpenLync;

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
					
					OpenLync.adicionarLog("Nova conexão com o cliente "
							+ socketCliente.getInetAddress().getHostAddress());
					
					ThreadCliente cliente = new ThreadCliente(new Cliente(socketCliente));
					
					listaClientes.add(cliente);
					cliente.start();
					
				} catch (IOException e) {
					OpenLync.adicionarLog("Falha ao receber conexão do cliente");
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			OpenLync.adicionarLog("Servidor Socket aberto na porta "+"1234"); //TODO
			e1.printStackTrace();
		}
	}

	public void interrupt() {
		try {
			isExecutando = false;
			servidor.close();
			OpenLync.adicionarLog("Servidor Socket parado!");
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
