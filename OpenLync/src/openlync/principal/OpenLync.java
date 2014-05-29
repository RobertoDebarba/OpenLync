package openlync.principal;
//Referencias
//http://www.caelum.com.br/apostila-java-orientacao-objetos/apendice-sockets/#19-5-servidor
//http://pt.slideshare.net/akhilgouthamkotini/multi-user-chat-system-using-java1

import java.io.IOException;
import java.net.ServerSocket;

import openlync.forms.FormMain;
import openlync.sockets.NovoCliente;

public class OpenLync {

	public static FormMain frmMain = new FormMain();
	private static int portaEntrada;
	private static int portaSaida;

	private static Thread TClientes;
	private static ServerSocket servidor;

	public static void main(String[] args) throws IOException {
		new OpenLync();
	}

	public static int getPortaEntrada() {
		return portaEntrada;
	}

	public static void setPortaEntrada(int portaEntrada) {
		OpenLync.portaEntrada = portaEntrada;
	}

	public OpenLync() {
		portaEntrada = 7609;
		portaSaida = 7606;

		// Cria Form
		frmMain.setVisible(true);
	}

	/**
	 * Inicia o servidor de mensagens
	 */
	public static boolean iniciarServidor() {

		servidor = null;
		try {
			servidor = new ServerSocket(portaEntrada);

			// Cria Thread para verificar entrada de novos clientes
			NovoCliente verifNovosClientes = new NovoCliente(servidor,
					portaSaida);
			TClientes = new Thread(verifNovosClientes);
			TClientes.start();

			return true;
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * Para o servidor de mensagens
	 */
	public static void pararServidor() {
		try {
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		TClientes.interrupt();

		OpenLync.frmMain.frmInicial.adicionarLog("Servidor parado!");

	}
}
