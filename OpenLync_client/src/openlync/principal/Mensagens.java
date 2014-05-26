package openlync.principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import openlync.utilidades.Criptografia;
import openlync.utilidades.MySQLConection;

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

	/**
	 * Trata mensagem recebida pelo servidor separando os campos
	 * @param mensagemNaoTratada
	 */
	public void tratarMensagem(String mensagemNaoTratada) {

		boolean pParte = true;

		for (int i = 0; i < mensagemNaoTratada.length(); i++) {
			char c = mensagemNaoTratada.charAt(i);

			if (c == '|') {
				pParte = false;
			} else if (pParte) {
				ipRemetente += c;
			} else if (!pParte) {
				mensagemTratada += c;
			}
		}
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
			int codigoDestinatario, Date data, boolean lida) {

		Criptografia cript = new Criptografia();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String SQL = "CALL sp_adicionarMensagem(?, ?, ?, ?, ?)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			pst.setInt(1, codigoRemetente);
			pst.setInt(2, codigoDestinatario);
			pst.setString(3, cript.criptografarMensagem(mensagem));
			pst.setString(4, sdf.format(data));
			pst.setBoolean(5, lida);
			
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
	public List<Integer> getListMensagensNaoLidas(Usuarios usuario) {

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
}
