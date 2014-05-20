package openlync.principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import openlync.forms.FormLogin;
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
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "INSERT INTO tb_mensagens(" + "conteudo_mensagem,"
					+ " data_mensagem," + " lido_mensagem,"
					+ " codigo_remet_mensagem," + " codigo_dest_mensagem)"
					+ " VALUES (" + "'" + cript.criptografarMensagem(mensagem)
					+ "'," + " '" + sdf.format(data) + "'," + " " + lida + ","
					+ " " + codigoRemetente + "," + " " + codigoDestinatario
					+ "" + ");";

			st.execute(SQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se há mensagens não lidas
	 * 
	 * @return List com codigos não repitidos dos usuarios que mandaram mensagens não lidas
	 */
	public List<Integer> verificarMensagensNaoLidas(Usuarios usuario) {

		List<Integer> arrayResult = new ArrayList<Integer>();
		try {
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "SELECT codigo_remet_mensagem FROM tb_mensagens "
					+ "WHERE codigo_dest_mensagem = "
					+ FormLogin.getUsuarioLogin().getCodigo()
					+ " AND lido_mensagem = FALSE;";

			ResultSet rs = st.executeQuery(SQL);

			// Varre todas mensagens não lidas, capturando o codigo do remetente
			while (rs.next()) {

				// Verifica se o remetente ja esta na lista
				boolean achou = false;
				for (int i = 0; i < arrayResult.size(); i++) {
					achou = (arrayResult.get(i) == rs
							.getInt("codigo_remet_mensagem")) ? true : false;
				}

				// Se não estiver, adiciona
				if (!achou) {
					arrayResult.add(rs.getInt("codigo_remet_mensagem"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arrayResult;
	}
}
