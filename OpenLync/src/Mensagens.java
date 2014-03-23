
public class Mensagens {

	private String ipDestino = "";
	private String mensagemTratada = "";
		
	public String getIpDestino() {
		return ipDestino;
	}

	public String getMensagemTratada() {
		return mensagemTratada;
	}

	public void tratarMensagens(String mensagemNaoTratada) {
		
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
}
