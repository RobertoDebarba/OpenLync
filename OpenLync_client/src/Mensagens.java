import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Mensagens {

	private String ipRemetente = "";
	private String mensagemTratada = "";
		
	public String getIpRemetente() {
		return ipRemetente;
	}

	public String getMensagemTratada() {
		return mensagemTratada;
	}

	public void tratarMensagem(String mensagemNaoTratada) {
		
		boolean pParte = true;
	 
		for(int i=0;i< mensagemNaoTratada.length();i++){  
		   char c = mensagemNaoTratada.charAt(i);
			   
		   if (c == '|') {
			   pParte = false;
		   }
		   else if (pParte) {
			   ipRemetente = ipRemetente + c;
		   }
		   else if (!pParte) {
			   mensagemTratada = mensagemTratada + c;
		   }
		} 
	}
}
