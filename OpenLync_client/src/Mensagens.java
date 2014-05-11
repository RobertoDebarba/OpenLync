import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
			   ipRemetente += c;
		   }
		   else if (!pParte) {
			   mensagemTratada += c;
		   }
		} 
	}
	
	public void adicionarMensagem(String mensagem, int codigoRemetente, int codigoDestinatario, Date data, boolean lida) {
		
		Connection conexao = MySQLConection.getMySQLConnection();
		Criptografia cript = new Criptografia();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Statement st = conexao.createStatement();
			
			String SQL = "INSERT INTO tb_mensagens("+
						 "conteudo_mensagem,"+
						 " data_mensagem,"+
						 " lido_mensagem,"+
						 " codigo_remet_mensagem,"+
						 " codigo_dest_mensagem)"+
						 " VALUES ("+
						 "'"+ cript.criptografarMensagem(mensagem) +"',"+
						 " '"+ sdf.format(data) +"',"+
						 " "+ lida +","+
						 " "+ codigoRemetente +","+
						 " "+ codigoDestinatario +""+
						 ");";
			
			st.execute(SQL);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
