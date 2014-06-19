package openlync.sockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import openlync.forms.FormChat;
import openlync.forms.FormNotificação;
import openlync.principal.Configuracao;
import openlync.principal.Contato;
import openlync.principal.Mensagem;
import openlync.principal.Usuario;
import openlync.principal.UsuarioDAO;
import openlync.utilidades.AudioPlayer;

public class Cliente implements Runnable {

	public static Socket socketServidor;
	private boolean isExecutando;
	private AudioPlayer player = new AudioPlayer(this.getClass().getResource("/openlync/musicas/newMessage.wav"));
	
	@Override
	public void run() {
		isExecutando = true;
		iniciarLeitura();
	}
	
	private void iniciarLeitura() {
		try {
			socketServidor = new Socket(Configuracao.getIpServidorMensagens(), Configuracao.getPortaSocket());
			
			DataInputStream input = new DataInputStream(socketServidor.getInputStream());
			Mensagem tratadorMensagem = new Mensagem();
			
			while (isExecutando) {
				String msg = input.readUTF();
				
				String[] retornoTratada = tratadorMensagem.tratarMensagem(msg);
				
				//Mostra mensagem descriptografada
				System.out.println("Recebida mensagem de "+retornoTratada[Mensagem.IP_REMETENTE]+": "+retornoTratada[Mensagem.MENSAGEM]);
				
				//mandar para tela de chat
				boolean encontrouChat = false;
				for (int i = 0; i < Contato.getSizeListaFormChat(); i++) {
					if (retornoTratada[Mensagem.IP_REMETENTE].equals(Contato.listaFormChat.get(i).getUsuario().getIp())) {
						//Se janela estiver minizada tocar som de alerta
						if ((Contato.listaFormChat.get(i).getState() == FormChat.ICONIFIED) || (!Contato.listaFormChat.get(i).isFocused())) {
							Thread avisoSonoro = new Thread(player);
							avisoSonoro.start();
						}
						Contato.listaFormChat.get(i).adicionarMensagem(retornoTratada[Mensagem.MENSAGEM], Mensagem.EXTERNA); //Significa que mensegem não é do proprio usuario
						encontrouChat = true;
						break;
					}
				}
				
				//Se não encontrou tela de chat do usuario correto
				if (!encontrouChat) {
					
					UsuarioDAO dao = new UsuarioDAO(true);
					Contato contatos = new Contato();
					Usuario user = dao.procurarUsuarioIP(retornoTratada[Mensagem.IP_REMETENTE]);
					
					//Cria janela de Chat
					contatos.adicionarFormChat(user, 1, 0);
					
					//Procura chat criado e manda mensagem
					int posicaoChat = 0;;
					for (int i = 0; i < Contato.getSizeListaFormChat(); i++) {
						if (retornoTratada[Mensagem.IP_REMETENTE].equals(Contato.listaFormChat.get(i).getUsuario().getIp())) {
							Contato.listaFormChat.get(i).adicionarMensagem(retornoTratada[Mensagem.MENSAGEM], Mensagem.EXTERNA); //Significa que mensegem não é do proprio usuario
							posicaoChat = i;
							break;
						}
					}
					
					//Exibir Notificação
					FormNotificação frmNotificacao = new FormNotificação("Nova mensagem de "+user.getNome()+"...", user.getFoto(), posicaoChat);
					
					//Toca som de notificação
					Thread avisoSonoro = new Thread(player);
					avisoSonoro.start();
					
					frmNotificacao.setVisible(true);		
				}	
			}
		} catch (IOException e) {
			System.out.println("Erro na entrada de dados!");
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao servidor de mensagens! Contate o administrador do sistema.", "Erro", 0);
			e.printStackTrace();
			System.exit(0);
		}
	}
}
