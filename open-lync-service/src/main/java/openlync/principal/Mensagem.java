package openlync.principal;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import openlync.sockets.Servidor;
import openlync.utilidades.Criptografia;

public class Mensagem {
	
	public static final int IP_DESTINO = 0;
	public static final int MENSAGEM = 1;

	/**
	 * Trata mensagem recebida pelo cliente; Seta ipDestino e mensagemTratada no
	 * objeto
	 * 
	 * @param mensagemNaoTratada
	 */
	public String[] tratarMensagem(String mensagemNaoTratada) {

		String[] retorno = new String[2];
		retorno[0] = "";
		retorno[1] = "";
		boolean pParte = true;

		for (int i = 0; i < mensagemNaoTratada.length(); i++) {
			char c = mensagemNaoTratada.charAt(i);

			if (c == '|') {
				pParte = false;
			} else if (pParte) {
				retorno[0] += c;
			} else if (!pParte) {
				retorno[1] += c;
			}
		}
		
		return retorno;
	}

	/**
	 * Criptografa e envia mensagem ao ipDestino
	 */
	public void enviarMensagem(String mensagem, String ipDestino, String ipRemetente) {

		Criptografia cript = new Criptografia();
		System.out.println("Mensagem de "+ipRemetente+" para "+ipDestino+": "+mensagem);

		for (int i = 0; i < Servidor.listaClientes.size(); i++) {
			if (Servidor.listaClientes.get(i).getIpCliente().equals(ipDestino)) {
				Socket cliente = Servidor.listaClientes.get(i).getSocket();
				
				try {
					DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
					out.writeUTF(cript.criptografarMensagem(ipRemetente+"|"+mensagem));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		
		}
	}
}
