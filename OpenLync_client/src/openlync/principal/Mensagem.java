package openlync.principal;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import openlync.sockets.Cliente;
import openlync.utilidades.Criptografia;
import openlync.utilidades.MySQLConection;

public class Mensagem {
	
	public static final int IP_REMETENTE = 0;
	public static final int MENSAGEM = 1;
	
	public static final int EXTERNA = 0;
	public static final int INTERNA = 1;

	/**
	 * Trata mensagem recebida pelo servidor separando os campos
	 * @param mensagemNaoTratada
	 */
	public String[] tratarMensagem(String mensagemNaoTratada) {

		String mensagem = new Criptografia().descriptografarMensagem(mensagemNaoTratada);
		String[] retorno = new String[2];
		retorno[0] = "";
		retorno[1] = "";
		boolean pParte = true;

		for (int i = 0; i < mensagem.length(); i++) {
			char c = mensagem.charAt(i);

			if (c == '|') {
				pParte = false;
			} else if (pParte) {
				retorno[0] += c;
			} else if (!pParte) {
				retorno[1] += c;
			}
		}
		
		return retorno;
	}

	/**
	 * Adiciona mensagem ao Banco de Dados
	 * @param mensagem
	 * @param codigoRemetente
	 * @param codigoDestinatario
	 * @param data
	 * @param lida
	 */
	public void adicionarMensagemDB(String mensagem, int codigoRemetente,
			int codigoDestinatario, Date data) {

		Criptografia cript = new Criptografia();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String SQL = "CALL sp_adicionarMensagem(?, ?, ?, ?)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			pst.setInt(1, codigoRemetente);
			pst.setInt(2, codigoDestinatario);
			pst.setString(3, cript.criptografarMensagem(mensagem));
			pst.setString(4, sdf.format(data));
			
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se há mensagens não lidas
	 * 
	 * @return List com codigos não repitidos dos usuarios que mandaram mensagens não lidas
	 */
	public List<Integer> getListMensagensNaoLidas(Usuario usuario) {

		List<Integer> arrayResult = new ArrayList<Integer>();
		try {
			String SQL = "CALL sp_getQuantidadeMensagensNaoLidas(?)";

			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			pst.setInt(1, usuario.getCodigo());
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				arrayResult.add(rs.getInt("codigo_remet_mensagem"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arrayResult;
	}
	
	public void enviarMensagem(String mensagem, String ipDestino) {
		
		Socket servidor = Cliente.socketServidor;
		DataOutputStream out;
		
		try {
			out = new DataOutputStream(servidor.getOutputStream());
			
			out.writeUTF(new Criptografia().criptografarMensagem(ipDestino+"|"+mensagem));
			
			System.out.println("Enviada mensagem para "+ipDestino+": "+mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
