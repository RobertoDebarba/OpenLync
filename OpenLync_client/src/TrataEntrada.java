import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
				System.out.println(msg); //TODO remover
				
				Mensagens TratadorMensagens = new Mensagens();
				TratadorMensagens.tratarMensagem(msg);
				
				//mandar para tela de chat
				int i = 0;
				FormChat[] listaChat = null;
				while (i < FormUsuarioLista.getContadorChat()) {
					
					listaChat = FormUsuarioLista.getListaChat();
					if (TratadorMensagens.getIpRemetente().equals(listaChat[i].getIp())) {
						listaChat[i].adicionarMensagem(TratadorMensagens.getMensagemTratada());//FIXME
					}
					i++;
				}
				
			}
			
		}

	}
	
}
