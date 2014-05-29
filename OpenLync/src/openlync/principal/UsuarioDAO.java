package openlync.principal;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import javax.imageio.ImageIO;

import openlync.utilidades.Criptografia;
import openlync.utilidades.MySQLConection;

public class UsuarioDAO {

	public List<Usuario> listaUsuarios = new ArrayList<Usuario>();

	/**
	 * 
	 * @param carregarListaUsuarios
	 */
	public UsuarioDAO(boolean carregarListaUsuarios) {
		carregarListaUsuario();
	}

	/**
	 * Carrega listaUsuarios com todos os registros de tb_usuarios
	 */
	public void carregarListaUsuario() {
		
		Criptografia cript = new Criptografia();

		listaUsuarios.removeAll(listaUsuarios);
		try {
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "CALL sp_getUsuarios";

			ResultSet rs = st.executeQuery(SQL);

			while (rs.next()) {
				Usuario usuario = new Usuario();

				usuario.setCodigo(rs.getInt("codigo_usuario"));
				usuario.setNome(rs.getString("nome_usuario"));
				usuario.setCargo(rs.getString("desc_cargo"));
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
	 * Restaura o IP(status) de todos os usuarios para 'null'(offline)
	 */
	public void restaurarStatusUsuarios() {
		
		try {
			String SQL = "UPDATE tb_usuarios "
						+ "SET ip_usuario = 'null' "
						+ "WHERE codigo_usuario > 0";
			
			Statement st = MySQLConection.getStatementMySQL();
			st.execute(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verificar se nome de usuario(login) ja está cadastrado
	 */
	public boolean verificarDispLogin(String login) throws SQLException {

		Criptografia cript = new Criptografia();
		
		if (!login.equals("")) { // Se login não estiver vazio
			ResultSet rs;

			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "SELECT fc_verificarDispLogin('"+cript.criptografarMensagem(login)+"')";

			rs = st.executeQuery(SQL);
			rs.next();
			return rs.getBoolean(1);
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

		Criptografia cript = new Criptografia();

		boolean result = false;
		try {

			String SQL = "SELECT fc_verificarLogin(?, ?, TRUE, FALSE, TRUE)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			
			pst.setString(1, cript.criptografarMensagem(login));
			pst.setString(2, cript.criptografarMensagem(senha));
			
			ResultSet rs = pst.executeQuery();
			rs.next();
			result = rs.getBoolean(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @return proximo codigo utilizavel;
	 */
	public int getNovoCodigo() {
		
		int resultado = 0;
		try {
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "SELECT fc_getNovoCodigoUsuario()";

			ResultSet rs = st.executeQuery(SQL);
			rs.next();
			resultado = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultado;
	}

	/**
	 * Adiciona novo usuario
	 * 
	 * @param usuario
	 */
	public void adicionar(Usuario usuario) {

		Criptografia cript = new Criptografia();
		
		try {
			
			String SQL = "CALL sp_adicionarUsuario(?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);

			pst.setInt(1, usuario.getCodigo());
			pst.setString(2, usuario.getNome());
			pst.setString(3, usuario.getCargo());
			pst.setString(4, cript.criptografarMensagem(usuario.getLogin()));
			pst.setString(5, cript.criptografarMensagem(usuario.getSenha()));
			pst.setString(6, "null");
			pst.setBoolean(7, usuario.isAdmin());
			
			// Prepara imagem para INSERT
			if (usuario.getFoto() != null) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					ImageIO.write(usuario.getFoto(), "jpeg", out);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				byte[] buf = out.toByteArray();
				ByteArrayInputStream inStream = new ByteArrayInputStream(buf);
				
				pst.setBinaryStream(8, inStream, inStream.available());
			} else {
				pst.setBinaryStream(8, null);
			}
			
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edita usuario
	 * 
	 * @param usuario
	 */
	public void editar(Usuario usuario) {

		Criptografia cript = new Criptografia();
		
		try {
			
			String SQL = "CALL sp_editarUsuario(?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			
			pst.setInt(1, usuario.getCodigo());
			pst.setString(2, usuario.getNome());
			pst.setString(3, usuario.getCargo());
			pst.setString(4, cript.criptografarMensagem(usuario.getLogin()));
			pst.setString(5, cript.criptografarMensagem(usuario.getSenha()));
			pst.setString(6, "null");
			pst.setBoolean(7, usuario.isAdmin());

			// Prepara imagem para INSERT
			if (usuario.getFoto() != null) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					ImageIO.write(usuario.getFoto(), "jpeg", out);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				byte[] buf = out.toByteArray();
				ByteArrayInputStream inStream = new ByteArrayInputStream(buf);
				
				pst.setBinaryStream(8, inStream, inStream.available());
			} else {
				pst.setBinaryStream(8, null);
			}
			
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Apaga usuario
	 * 
	 * @param usuario
	 */
	public void apagar(Usuario usuario) {
		
		try {
			
			String SQL = "CALL sp_apagarUsuario(?)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			
			pst.setInt(1, usuario.getCodigo());
			
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
