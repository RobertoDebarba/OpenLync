
public class Mensagens {

	private String ipRemetente = "";
	private String mensagemTratada = "";
		
	public String getIpRemetente() {
		return ipRemetente;
	}

	public void setIpRemetente(String ipRemetente) {
		this.ipRemetente = ipRemetente;
	}

	public void setMensagemTratada(String mensagemTratada) {
		this.mensagemTratada = mensagemTratada;
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
