package openlync.sockets;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import openlync.forms.FormChat;
import openlync.forms.FormNotificação;
import openlync.principal.Contato;
import openlync.principal.Mensagem;
import openlync.principal.Usuario;
import openlync.principal.UsuarioDAO;
import openlync.utilidades.AudioPlayer;
import openlync.utilidades.Criptografia;

public class TrataEntrada implements Runnable {
	
	private ServerSocket SS;
	private Socket socketEntrada;
	
	public TrataEntrada(ServerSocket SS) { 
		this.SS = SS;
		
	}

	/**
	 * Loop infinito que verifica entrada de novas mensagens.
	 * Se recebeu nova mensagem: 
	 * 		1 - Descriptografa
	 * 		2 - Trata mensagem
	 * 		3 - Manda para tela de chat correta
	 * 			Se não encontrou cria uma tela de chat nova
	 */
	public void run() {
		
		while (true) {
			
			// Aguarda o servidor mandar algum dado
			try {
				this.socketEntrada = this.SS.accept();
			} catch (IOException e1) {
				System.out.println("Erro ao receber conexão de entrada do servidor! 'SS.accept()'");
			}
			
			// Cria Scanner
			Scanner s = null;
			try {
				s = new Scanner(socketEntrada.getInputStream());
			} catch (IOException e) {
				System.out.println("Erro 'socketEntrada.getInputStream'");
			}
			
			Mensagem TratadorMensagens = new Mensagem();
			Criptografia cript = new Criptografia();
			
			while (s.hasNextLine()) {

				String msg = s.nextLine();
				
				TratadorMensagens.setIpRemetente("");
				TratadorMensagens.setMensagemTratada("");
				//Descriptografa e trata mensagem
				TratadorMensagens.tratarMensagem(cript.descriptografarMensagem(msg));
				
				//Mostra mensagem descriptografada
				System.out.println(cript.descriptografarMensagem(msg));
				
				//mandar para tela de chat
				boolean encontrouChat = false;
				for (int i = 0; i < Contato.getSizeListaFormChat(); i++) {
					if (TratadorMensagens.getIpRemetente().equals(Contato.listaFormChat.get(i).getUsuario().getIp())) {
						//Se janela estiver minizada tocar som de alerta
						if (Contato.listaFormChat.get(i).getState() == FormChat.ICONIFIED) {
							AudioPlayer player = new AudioPlayer();
							player.setAudioFile("/home/roberto/git/OpenLync/OpenLync_client/src/openlync/musicas/newMessage.mp3");
							Thread avisoSonoro = new Thread(player);
							avisoSonoro.start();
						}
						Contato.listaFormChat.get(i).adicionarMensagem(TratadorMensagens.getMensagemTratada(), "out"); //Significa que mensegem não é do proprio usuario
						encontrouChat = true;
						break;
					}
				}
				
				//Se não encontrou tela de chat do usuario correto
				if (!encontrouChat) {
					
					UsuarioDAO dao = new UsuarioDAO(true);
					Contato contatos = new Contato();
					Usuario user = dao.procurarUsuarioIP(TratadorMensagens.getIpRemetente());
					
					//Cria janela de Chat
					contatos.adicionarFormChat(user, 1, 0);
					
					//Procura chat criado e manda mensagem
					int posicaoChat = 0;;
					for (int i = 0; i < Contato.getSizeListaFormChat(); i++) {
						if (TratadorMensagens.getIpRemetente().equals(Contato.listaFormChat.get(i).getUsuario().getIp())) {
							Contato.listaFormChat.get(i).adicionarMensagem(TratadorMensagens.getMensagemTratada(), "out"); //Significa que mensegem não é do proprio usuario
							posicaoChat = i;
							break;
						}
					}
					
					//Exibir Notificação
					FormNotificação frmNotificacao = new FormNotificação("Nova mensagem de "+user.getNome()+"...", user.getFoto(), posicaoChat);
					
					//Toca som de notificação
					AudioPlayer player = new AudioPlayer();
					player.setAudioFile("/home/roberto/git/OpenLync/OpenLync_client/src/openlync/musicas/newMessage.mp3");
					Thread avisoSonoro = new Thread(player);
					avisoSonoro.start();
					
					frmNotificacao.setVisible(true);
					
				}	
			}
		}
	}
}
