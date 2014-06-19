package openlync.sockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import openlync.principal.Mensagem;
import openlync.principal.OpenLync;
import openlync.utilidades.Criptografia;

public class Cliente implements Runnable {
	
	private Socket socketCliente;
	private String IP;
	private boolean isExecutando;

	public void run() {
		
		OpenLync.adicionarLog("Iniciada a Thread "
				+ Thread.currentThread().getId());
		
		//Recebimento de mensagens
		try {
			DataInputStream input = new DataInputStream(socketCliente.getInputStream());
			String mensagem = "";
			Criptografia cript = new Criptografia();
			Mensagem tratadorMensagens = new Mensagem();
			
			while (isExecutando) {
				mensagem = input.readUTF();		
				
				// Descriptografar e tratar mensagem
				String[] retornoMensagem = tratadorMensagens.tratarMensagem(cript
						.descriptografarMensagem(mensagem));

				if (retornoMensagem[Mensagem.IP_DESTINO].equals("SYSTEM")) { // SE for teste sistema
					
					if (retornoMensagem[Mensagem.MENSAGEM].equals("RETURN IP CLIENT")) {
						
						tratadorMensagens.enviarMensagem(IP, IP, "SYSTEM");
					} else if (retornoMensagem[Mensagem.MENSAGEM].equals("KILL CLIENT")) {
							
						interrupt();
					}
				} else { // Se for mensagem normal

					tratadorMensagens.enviarMensagem(retornoMensagem[Mensagem.MENSAGEM], 
							retornoMensagem[Mensagem.IP_DESTINO], IP);
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public Cliente(Socket cliente) {
		this.socketCliente = cliente;
		this.IP = socketCliente.getInetAddress().getHostAddress();
		isExecutando = true;
	}
	
	public Socket getSocket() {
		return socketCliente;
	}
	
	public String getIp() {
		return this.IP;
	}
	
	public void interrupt() {
		try {
			OpenLync.adicionarLog("Encerrada a Thread "
					+ Thread.currentThread().getId());
			isExecutando = false;
			socketCliente.close();
			Servidor.listaClientes.remove(Thread.currentThread());
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
