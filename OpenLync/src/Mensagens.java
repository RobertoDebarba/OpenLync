import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Mensagens {

	private String ipDestino = "";
	private String mensagemTratada = "";
		
	public String getIpDestino() {
		return ipDestino;
	}

	public String getMensagemTratada() {
		return mensagemTratada;
	}

	public void setIpDestino(String ipDestino) {
		this.ipDestino = ipDestino;
	}
	
	public void setMensagemTratada(String mensagemTratada) {
		this.mensagemTratada = mensagemTratada;
	}

	public void tratarMensagem(String mensagemNaoTratada) {
		
		boolean pParte = true;
	 
		for(int i=0;i< mensagemNaoTratada.length();i++){  
		   char c = mensagemNaoTratada.charAt(i);
			   
		   if (c == '|') {
			   pParte = false;
		   }
		   else if (pParte) {
			   ipDestino = ipDestino + c;
		   }
		   else if (!pParte) {
			   mensagemTratada = mensagemTratada + c;
		   }
		} 
	}
	
	public void tratarArquivo() {
		
	}

	public void enviarMensagem(String mensagem, int portaSaida) {
		
		Socket Sdestino = null;
		PrintStream PSdestino = null;
		try {
			Sdestino = new Socket(ipDestino, portaSaida);
			PSdestino = new PrintStream(Sdestino.getOutputStream());
			PSdestino.println(mensagem); //Envia a mensagem
		} catch (IOException e) {
			System.out.println("Não foi possivel estabelecer conexão com destinatario!");
		}
		
		// É necessario fechar os objetos para não dar conflito quando chamar o metodo na segunda vez
		try {
			Sdestino.close();
			PSdestino.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
