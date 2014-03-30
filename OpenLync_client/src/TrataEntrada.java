import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class TrataEntrada implements Runnable {
	
	private ServerSocket SS;
	private Socket socketEntrada;
	
	public TrataEntrada(ServerSocket SS) { 
		this.SS = SS;
	}

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
			
			while (s.hasNextLine()) {

				String msg = s.nextLine();
				System.out.println(msg);
				
				Mensagens TratadorMensagens = new Mensagens();
				TratadorMensagens.tratarMensagem(msg);
				
				//mandar para tela de chat
				int i = 0;
				boolean encontrouChat = false;
				while (i < FormChat.getContadorChat()) {
					
					if (TratadorMensagens.getIpRemetente().equals(FormChat.listaChat[i].getIp())) {
						FormChat.listaChat[i].adicionarMensagem(TratadorMensagens.getMensagemTratada());//FIXME
						encontrouChat = true;
					}
					i++;
				}
				
				//Se não encontrou tela de chat do usuario correto
				if (!encontrouChat) {
					
					//Cria janela de chat
					Usuarios usuarioPesquisa = new Usuarios();
					try {
						usuarioPesquisa.carregarInformacoesPorIP(TratadorMensagens.getIpRemetente());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					FormChat.listaChat[FormChat.getContadorChat()] = new FormChat(usuarioPesquisa.getCodigo(), usuarioPesquisa.getNome(), usuarioPesquisa.getCargo(), usuarioPesquisa.getIp());
					FormChat.listaChat[FormChat.getContadorChat()].setVisible(true);
					FormChat.listaChat[FormChat.getContadorChat()].adicionarMensagem(TratadorMensagens.getMensagemTratada());
					FormChat.incContadorChat();
				}
				
			}
			
		}

	}
	
}
