import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class UsuariosDAO {

	public List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();

	/**
	 * 
	 * @param carregarListaUsuarios
	 */
	public UsuariosDAO(boolean carregarListaUsuarios) {
		carregarListaUsuario();
	}

	/**
	 * Carrega listaUsuarios com todos os registros de tb_usuarios
	 */
	private void carregarListaUsuario() {
		
		Criptografia cript = new Criptografia();
		Connection conexao = MySQLConection.getMySQLConnection();

		listaUsuarios.removeAll(listaUsuarios);
		try {
			java.sql.Statement st = conexao.createStatement();

			String SQL = "SELECT codigo_usuario, nome_usuario, login_usuario, senha_usuario, foto_usuario, admin_usuario, tb_cargos.desc_cargo"
					+ " FROM tb_usuarios, tb_cargos"
					+ " WHERE tb_cargos.codigo_cargo = tb_usuarios.codigo_cargo;";

			ResultSet rs = st.executeQuery(SQL);

			while (rs.next()) {
				Usuarios usuario = new Usuarios();

				usuario.setCodigo(rs.getInt("codigo_usuario"));
				usuario.setNome(rs.getString("nome_usuario"));
				usuario.setCargo(rs.getString("tb_cargos.desc_cargo"));
				usuario.setLogin(cript.descriptografarMensagem(rs
						.getString("login_usuario")));
				usuario.setSenha(cript.descriptografarMensagem(rs
						.getString("senha_usuario")));
				usuario.setAdmin(rs.getBoolean("admin_usuario"));
				Blob blobImage = rs.getBlob("foto_usuario");
				try {
					if (blobImage != null) {
						usuario.setFoto(ImageIO.read(blobImage
								.getBinaryStream()));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				listaUsuarios.add(usuario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verificar se nome de usuario(login) ja está cadastrado
	 */
	public boolean verificarDispLogin(String login) throws SQLException {

		Criptografia cript = new Criptografia();
		Connection conexao = MySQLConection.getMySQLConnection();
		
		if (!login.equals("")) { // Se login não estiver vazio
			ResultSet rs;

			java.sql.Statement st = conexao.createStatement();

			String SQL = "SELECT 1 FROM tb_usuarios"
					+ " WHERE login_usuario = '"
					+ cript.criptografarMensagem(login) + "';";

			rs = st.executeQuery(SQL);

			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		}
		;

		return true;
	}

	/**
	 * Verifica se usuario e senha estão corretos
	 * 
	 * @param login
	 * @param senha
	 * @return boolean
	 */
	public boolean verifLogin(String login, String senha) {

		Connection conexao = MySQLConection.getMySQLConnection();
		Criptografia cript = new Criptografia();

		boolean result = false;
		try {
			Statement st = conexao.createStatement();

			String SQL = "SELECT 1 FROM tb_usuarios"
					+ " WHERE login_usuario = '"
					+ cript.criptografarMensagem(login)
					+ "' AND senha_usuario = '"
					+ cript.criptografarMensagem(senha)
					+ "' AND admin_usuario = true;";

			ResultSet rs = st.executeQuery(SQL);

			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @return proximo codigo utilizavel;
	 */
	public int getNovoCodigo() {

		Connection conexao = MySQLConection.getMySQLConnection();
		
		int ultimoCodigo = 0;
		try {
			java.sql.Statement st = conexao.createStatement();

			String SQL = "SELECT codigo_usuario FROM tb_usuarios;";

			ResultSet rs = st.executeQuery(SQL);

			while (rs.next()) {
				if (ultimoCodigo < rs.getInt("codigo_usuario")) {
					ultimoCodigo = rs.getInt("codigo_usuario");
				}
			}
			st.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ultimoCodigo + 1;
	}

	/**
	 * Adiciona novo usuario
	 * 
	 * @param usuario
	 */
	public void adicionar(Usuarios usuario) {

		Criptografia cript = new Criptografia();
		Connection conexao = MySQLConection.getMySQLConnection();
		
		try {
			java.sql.Statement st = conexao.createStatement();

			String SQL;
			if (usuario.getFoto() == null) { // Se foto estiver vazia

				SQL = "INSERT INTO tb_usuarios (codigo_usuario,"
						+ " nome_usuario,"
						+ " codigo_cargo,"
						+ " login_usuario,"
						+ " senha_usuario,"
						+ " ip_usuario,"
						+ " admin_usuario)"
						+ " VALUES ("
						+ usuario.getCodigo()
						+ ", '"
						+ usuario.getNome()
						+ "', (SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = '"
						+ usuario.getCargo() + "')" + ", '"
						+ cript.criptografarMensagem(usuario.getLogin())
						+ "' , '"
						+ cript.criptografarMensagem(usuario.getSenha()) + "',"
						+ " 'null'" + ", " + usuario.isAdmin() + ");";

				st.execute(SQL);
			} else { // Se houver alguma foto

				SQL = "INSERT INTO tb_usuarios (codigo_usuario,"
						+ " nome_usuario,"
						+ " codigo_cargo,"
						+ " login_usuario,"
						+ " senha_usuario,"
						+ " ip_usuario,"
						+ " foto_usuario"
						+ " admin_usuario)"
						+ " VALUES ("
						+ usuario.getCodigo()
						+ ", '"
						+ usuario.getNome()
						+ "', (SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = '"
						+ usuario.getCargo() + "')" + ", '"
						+ cript.criptografarMensagem(usuario.getLogin())
						+ "' , '"
						+ cript.criptografarMensagem(usuario.getSenha())
						+ "', 'null'" + ", ?" + ", " + usuario.isAdmin() + ");";

				// Prepara imagem para INSERT
				// ---------------------------------------------------------
				java.sql.PreparedStatement pst = conexao.prepareStatement(SQL);

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					ImageIO.write(usuario.getFoto(), "jpeg", out);
				} catch (IOException e) {
					e.printStackTrace();
				}

				byte[] buf = out.toByteArray();
				ByteArrayInputStream inStream = new ByteArrayInputStream(buf);

				// ------------------------------------------------------------------------------------
				pst.setBinaryStream(1, inStream, inStream.available());
				;
				pst.executeUpdate();

				pst.close();
			}

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edita usuario
	 * 
	 * @param usuario
	 */
	public void editar(Usuarios usuario) {

		Criptografia cript = new Criptografia();
		Connection conexao = MySQLConection.getMySQLConnection();
		
		try {
			java.sql.Statement st = conexao.createStatement();

			String SQL;
			if (usuario.getFoto() == null) { // Se usuario nao possuir foto
				SQL = "UPDATE tb_usuarios SET"
						+ " nome_usuario = '"
						+ usuario.getNome()
						+ "', codigo_cargo = (SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = '"
						+ usuario.getCargo() + "')" + ", login_usuario = '"
						+ cript.criptografarMensagem(usuario.getLogin())
						+ "', senha_usuario = '"
						+ cript.criptografarMensagem(usuario.getSenha())
						+ "', foto_usuario = null" + ", admin_usuario = "
						+ usuario.isAdmin() + " WHERE codigo_usuario ="
						+ usuario.getCodigo() + ";";
				st.execute(SQL);

			} else { // Se houver alguma foto
				SQL = "UPDATE tb_usuarios SET"
						+ " nome_usuario = '"
						+ usuario.getNome()
						+ "', codigo_cargo = (SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = '"
						+ usuario.getCargo() + "')" + ", login_usuario = '"
						+ cript.criptografarMensagem(usuario.getLogin())
						+ "', senha_usuario = '"
						+ cript.criptografarMensagem(usuario.getSenha())
						+ "', foto_usuario = ?" + ", admin_usuario = "
						+ usuario.isAdmin() + " WHERE codigo_usuario ="
						+ usuario.getCodigo() + ";";

				// Prepara imagem para INSERT
				// ---------------------------------------------------------
				java.sql.PreparedStatement pst = conexao.prepareStatement(SQL);

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					ImageIO.write(usuario.getFoto(), "jpeg", out);
				} catch (IOException e) {
					e.printStackTrace();
				}

				byte[] buf = out.toByteArray();
				ByteArrayInputStream inStream = new ByteArrayInputStream(buf);

				// ------------------------------------------------------------------------------------
				pst.setBinaryStream(1, inStream, inStream.available());
				;
				pst.executeUpdate();

				pst.close();
			}

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Apaga usuario
	 * 
	 * @param usuario
	 */
	public void apagar(Usuarios usuario) {

		Connection conexao = MySQLConection.getMySQLConnection();
		
		try {
			java.sql.Statement st = conexao.createStatement();

			String SQL = "DELETE FROM tb_usuarios WHERE codigo_usuario = "
					+ usuario.getCodigo() + ";";
			st.execute(SQL);

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
