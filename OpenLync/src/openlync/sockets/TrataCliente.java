package openlync.sockets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import openlync.principal.Mensagens;
import openlync.principal.OpenLync;
import openlync.utilidades.Criptografia;

/**
 * Quando um novo cliente se conecta, uma nova Thread é criada com essa classe,
 * que possui um loop infinito para verificar cada mensagem enviada pelo cliente.
 */
public class TrataCliente implements Runnable {

	private Socket Scliente;
	private int portaSaida;
	private Mensagens TratadorMensagens = new Mensagens();
	private String remetente;
	private String msg;

	public TrataCliente(Socket cliente, int portaSaida) {
		this.Scliente = cliente;
		this.portaSaida = portaSaida;
		remetente = this.Scliente.getInetAddress().getHostAddress(); // Salva IP
																		// do
																		// cliente
																		// que
																		// enviou
																		// a
																		// mensagem
	}

	/**
	 * Para thread do cliente atual
	 */
	public void encerrarThread() {
		OpenLync.frmMain.frmInicial.adicionarLog("Encerrada a Thread "
				+ Thread.currentThread().getId());
		Thread.currentThread().interrupt();
	}

	/**
	 * Loop infinito que verrifica chegada de novas mensagens
	 * 
	 * Quando chega mensagem 1. descriptografa 2. Trata mensagem
	 * 
	 * Verifica se é mensagem de sistema (SYSTEM) ou normal
	 */
	public void run() {

		OpenLync.frmMain.frmInicial.adicionarLog("Criada a Thread "
				+ Thread.currentThread().getId());

		Scanner scannerCliente = null;
		try {
			scannerCliente = new Scanner(this.Scliente.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			OpenLync.frmMain.frmInicial
					.adicionarLog("Erro ao criar Scanner do cliente!");
		}

		Criptografia cript = new Criptografia();

		while (scannerCliente.hasNextLine()) {

			// Varre mensagem
			String mensg = scannerCliente.nextLine();

			this.msg = "";
			TratadorMensagens.setIpDestino("");
			TratadorMensagens.setMensagemTratada("");
			// Descriptografar e tratar mensagem
			TratadorMensagens.tratarMensagem(cript
					.descriptografarMensagem(mensg));

			if (TratadorMensagens.getIpDestino().equals("SYSTEM")) { // SE for
																		// teste
																		// de
																		// sistema

				MsgSistema();
			} else if (TratadorMensagens.getIpDestino().equals("FILE")) { // Se
																			// for
																			// envio
																			// de
																			// arquivo
																			// //FIXME
																			// arquivo

				MsgArquivo();
			} else { // Se for mensagem normal

				MsgMensagem();
			}
		}
	}

	/**
	 * Quando for mensagem do sistema verifica o solicitado e repassa ao
	 * remetente
	 */
	private void MsgSistema() {

		if (TratadorMensagens.getMensagemTratada().equals("RETURN IP CLIENT")) {
			msg = "SYSTEM|" + remetente;

			TratadorMensagens.setIpDestino(remetente);

			// Envia a mensagem
			TratadorMensagens.enviarMensagem(msg, this.portaSaida);

			// Mostra a mensagem enviada ao destinatario com o ip do remetente
			OpenLync.frmMain.frmInicial.adicionarLog(msg);

		} else if (TratadorMensagens.getMensagemTratada().equals("KILL CLIENT")) {

			encerrarThread();
		}
	}

	/**
	 * 
	 */
	private void MsgArquivo() { // FIXME arquivo

		System.out.println("Recebendo arquivo de " + remetente);

		try {
			InputStream in = this.Scliente.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(isr);
			String fName = reader.readLine();
			System.out.println("Nome do arquivo: " + fName);
			File f1 = new File("/home/roberto/joao2.jpg");
			@SuppressWarnings("resource")
			FileOutputStream out = new FileOutputStream(f1);

			int tamanho = 4096; // buffer de 4KB
			byte[] buffer = new byte[tamanho];
			int lidos = -1;
			while ((lidos = in.read(buffer, 0, tamanho)) != -1) {
				out.write(buffer, 0, lidos);
			}

			System.out.println("Recebimento do arquivo concluido!");
			out.flush();

		} catch (IOException e) {
			System.out.println("Erro ao receber arquivo!");
			e.printStackTrace();
		}
	}

	/**
	 * QUando for mensagem normal repassa ao destinatario com o ip do remetente
	 */
	private void MsgMensagem() {

		msg = remetente + "|" + TratadorMensagens.getMensagemTratada();

		// Envia a mensagem
		TratadorMensagens.enviarMensagem(msg, this.portaSaida);

		// Mostra a mensagem enviada ao destinatario com o ip do remetente
		OpenLync.frmMain.frmInicial.adicionarLog(msg);
	}
}