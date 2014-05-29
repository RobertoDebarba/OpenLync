package openlync.principal;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import openlync.utilidades.Criptografia;
import openlync.utilidades.MySQLConection;

public class UsuarioDAO {

	public List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	
	
	public UsuarioDAO(boolean atualizarLista) {

		if (atualizarLista) {
			atualizarListaUsuarios();
		}
	}
	
	/**
	 * Atualiza a lista de Usuarios (listaUsuarios)
	 */
	public void atualizarListaUsuarios() {
		
		//Remove todos os usuarios
		listaUsuarios.removeAll(listaUsuarios);
		
		try {
			Criptografia cript = new Criptografia();
			
			Statement st = MySQLConection.getStatementMySQL();
			
			//SELECT todas informações do usuario (com lookup no cargo)
			String SQL = "CALL sp_getUsuarios()";
	
			ResultSet rs = st.executeQuery(SQL);
			
			while(rs.next()) {
				//Cria novo usuario para gravar informações
				Usuario usuario = new Usuario();
				
				//Carrega informações
				usuario.setCodigo(rs.getInt("codigo_usuario"));
				usuario.setNome(rs.getString("nome_usuario"));
				if (rs.getString("desc_cargo") == null) {
					usuario.setCargo("Indefinido");
				} else {
					usuario.setCargo(rs.getString("desc_cargo"));
				}
				usuario.setLogin(cript.descriptografarMensagem(rs.getString("login_usuario")));
				usuario.setSenha(cript.descriptografarMensagem(rs.getString("senha_usuario")));
				usuario.setIp(rs.getString("ip_usuario"));
				Blob blobImage = rs.getBlob("foto_usuario");
				try {
					if (blobImage == null) {
						java.io.InputStream fis = getClass().getResourceAsStream("/openlync/imagens/imgPerfilGenerica.jpg");				
						usuario.setFoto(ImageIO.read(fis));
					} else {
						usuario.setFoto(ImageIO.read(blobImage.getBinaryStream()));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Grava usuario na lista
				listaUsuarios.add(usuario);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Procura e retorna usuario com base no codigo
	 * @param codigo
	 * @return
	 */
	public Usuario procurarUsuarioCodigo(int codigo) {
		
		boolean achou = false;
		int i = 0;
		Usuario resultado = null;
		while ((i< listaUsuarios.size()) && (!achou)) {
			if (listaUsuarios.get(i).getCodigo() == codigo) {
				achou = true;
				resultado = listaUsuarios.get(i);
			}
			i++;
		}
		
		return resultado;
	}
	
	/**
	 * Procura e retorna usuario com base no ip
	 * @param ip
	 * @return
	 */
	public Usuario procurarUsuarioIP(String ip) {
		
		boolean achou = false;
		int i = 0;
		Usuario resultado = null;
		while ((i< listaUsuarios.size()) && (!achou)) {
			if (listaUsuarios.get(i).getIp().equals(ip)) {
				achou = true;
				resultado = listaUsuarios.get(i);
			}
			i++;
		}
		
		return resultado;
	}
	
	/**
	 * Procura e retorna usuario com base no login
	 * @param ip
	 * @return
	 */
	public Usuario procurarUsuarioLogin(String login) {
		
		boolean achou = false;
		int i = 0;
		Usuario resultado = null;
		while ((i< listaUsuarios.size()) && (!achou)) {
			if (listaUsuarios.get(i).getLogin().equals(login)) {
				achou = true;
				resultado = listaUsuarios.get(i);
			}
			i++;
		}
		
		return resultado;
	}
	
	/**
	 * Atualiza IP do usuario no DB;
	 * @param usuario
	 */
	public void setIPDB(Usuario usuario) {
			
		try {
			String SQL = "CALL sp_setIpUsuario(?, ?)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			pst.setInt(1, usuario.getCodigo());
			pst.setString(2, usuario.getIp());
			
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adquire IP atualizado do DB;
	 * @param usuario
	 */
	public String getIPDB(Usuario usuario) {
		
		String resultado = null;
		try {
			Statement st = MySQLConection.getStatementMySQL();
			
			String SQL = "SELECT fc_getIpUsuario("+usuario.getCodigo()+")";
			
			ResultSet rs = st.executeQuery(SQL);
		
			if (rs.next()) {
				resultado = rs.getString("ip_usuario");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	/**
	 * Verifica se login e senha estão corretos e IP = "null";
	 * @param login
	 * @param senha
	 * @return
	 */
	public boolean verificarLogin(String login, String senha) {
		
		boolean resultado = false;
		try {
			Criptografia cript = new Criptografia();
			
			String SQL = "SELECT fc_verificarLogin(?, ?, FALSE, TRUE, FALSE)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			
			pst.setString(1, cript.criptografarMensagem(login));
			pst.setString(2, cript.criptografarMensagem(senha));
			
			ResultSet rs = pst.executeQuery();
			rs.next();
			resultado = rs.getBoolean(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	/**
	 * Verifica de Usuarios são amigos
	 * @param usuario
	 * @param amigo
	 * @return
	 */
	public boolean verificarAmizade(Usuario usuario, Usuario amigo) {
		
		boolean retorno = false;
		try {
			String SQL = "SELECT fc_verificarAmizade(?, ?)";
			
			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			pst.setInt(1, usuario.getCodigo());
			pst.setInt(2, amigo.getCodigo());
			
			ResultSet rs = pst.executeQuery();
			rs.next();
			retorno = rs.getBoolean(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	/**
	 * Adiciona amizade entre os usuarios;
	 * Retorna TRUE se processo foi bem sucedido
	 * @param usuario
	 * @param amigo
	 * @return
	 */
	public boolean adicionarAmizade(Usuario usuario, Usuario amigo) {
		
		boolean resultado = false;
		
		try {
			if (JOptionPane.showConfirmDialog(null, "Adicionar usuário à sua lista de amigos?", "Adicionar amigo", 2) == 0) {//0 = OK
				
				String SQL = "CALL sp_adicionarAmizade(?, ?)";
				
				PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
				pst.setInt(1, usuario.getCodigo());
				pst.setInt(2, amigo.getCodigo());
				
				pst.execute();
				resultado = true;
				
			} else {
				resultado = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro inesperado ao adicionar amigo!", "Erro", 1);
			resultado = false;
		}
		
		return resultado;
	}
	
	/**
	 * Remove amizade entre os usuarios;
	 * Retorna TRUE de processo foi bem sucedido
	 * @param usuario
	 * @param amigo
	 * @return
	 */
	public boolean removerAmizade(Usuario usuario, Usuario amigo) {
		
		boolean resultado = false;
		
		try {
			if (JOptionPane.showConfirmDialog(null, "Remover usuário de sua lista de amigos?", "Remover amigo", 2) == 0) {//0 = OK
				
				String SQL = "CALL sp_removerAmizade(?, ?)";
				
				PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
				pst.setInt(1, usuario.getCodigo());
				pst.setInt(2, amigo.getCodigo());
				
				pst.execute();
				resultado = true;
			} else {
				resultado = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro inesperado ao remover amigo!", "Erro", 1);
			resultado = false;
		}
		
		return resultado;
	}
}
