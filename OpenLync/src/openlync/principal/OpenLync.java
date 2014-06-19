package openlync.principal;

import java.io.IOException;

import openlync.forms.FormMain;
import openlync.sockets.Servidor;

public class OpenLync {

	public static FormMain frmMain = new FormMain();
	public static Servidor servidorMensagens;
	
	public static void main(String[] args) throws IOException {
		new OpenLync();
	}

	public OpenLync() {
		frmMain.setVisible(true);
		Configuracao.lerCfg();
	}
	
	public static void adicionarLog(String mensagem) {
		frmMain.frmInicial.adicionarLog(mensagem);
	}
	
	/**
	 * Inicia o servidor de mensagens
	 */
	public static boolean iniciarServidor() {
		
		try {
			// Cria Thread para verificar entrada de novos clientes
			servidorMensagens = new Servidor();
			new Thread(servidorMensagens).start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Para o servidor de mensagens
	 */
	public static void pararServidor() {		
		servidorMensagens.interrupt();
	}
}
