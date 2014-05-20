package openlync.principal;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import openlync.utilidades.Criptografia;
import openlync.utilidades.MySQLConection;


public class UsuariosDAO {

	public List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
	
	
	public UsuariosDAO(boolean atualizarLista) {

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
			String SQL = "SELECT codigo_usuario, nome_usuario, login_usuario, senha_usuario, ip_usuario, foto_usuario, tb_cargos.desc_cargo"+
						 " FROM tb_usuarios, tb_cargos" +
						 " WHERE tb_cargos.codigo_cargo = tb_usuarios.codigo_cargo"+
						 ";";
	
			ResultSet rs = st.executeQuery(SQL);
			
			while(rs.next()) {
				//Cria novo usuario para gravar informações
				Usuarios usuario = new Usuarios();
				
				//Carrega informações
				usuario.setCodigo(rs.getInt("codigo_usuario"));
				usuario.setNome(rs.getString("nome_usuario"));
				usuario.setCargo(rs.getString("desc_cargo"));
				usuario.setLogin(cript.descriptografarMensagem(rs.getString("login_usuario")));
				usuario.setSenha(cript.descriptografarMensagem(rs.getString("senha_usuario")));
				usuario.setIp(rs.getString("ip_usuario"));
				Blob blobImage = rs.getBlob("foto_usuario");
				try {
					if (blobImage == null) {
						java.io.InputStream fis = getClass().getResourceAsStream("/Imagens/imgPerfilGenerica.jpg");				
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
	public Usuarios procurarUsuarioCodigo(int codigo) {
		
		boolean achou = false;
		int i = 0;
		Usuarios resultado = null;
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
	public Usuarios procurarUsuarioIP(String ip) {
		
		boolean achou = false;
		int i = 0;
		Usuarios resultado = null;
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
	public Usuarios procurarUsuarioLogin(String login) {
		
		boolean achou = false;
		int i = 0;
		Usuarios resultado = null;
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
	public void setIPDB(Usuarios usuario) {
			
		try {
			Statement st = MySQLConection.getStatementMySQL();
			
			String SQL = "UPDATE tb_usuarios SET ip_usuario = '"+usuario.getIp()+
						 "' WHERE codigo_usuario = "+usuario.getCodigo()+";";
			
			st.executeUpdate(SQL);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adquire IP atualizado do DB;
	 * @param usuario
	 */
	public String getIPDB(Usuarios usuario) {
		
		String resultado = null;
		try {
			Statement st = MySQLConection.getStatementMySQL();
			
			String SQL = "SELECT ip_usuario"+
						 " FROM tb_usuarios"+
						 " WHERE codigo_usuario = "+usuario.getCodigo()+
						 ";";
			
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
			
			Statement st = MySQLConection.getStatementMySQL();
			
			String SQL = "SELECT 1 FROM tb_usuarios WHERE login_usuario = '"+ cript.criptografarMensagem(login) +"'" +
					 " AND senha_usuario = '"+ cript.criptografarMensagem(senha) +"'" +
					 " AND ip_usuario = 'null';";

			ResultSet rs = st.executeQuery(SQL);
	
			if (rs.next()) {
				resultado = true;
			} else {
				resultado = false;
			}
			
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
	public boolean verificarAmizade(Usuarios usuario, Usuarios amigo) {
		
		boolean retorno = false;
		try {
			Statement st = MySQLConection.getStatementMySQL();
			
			String SQL = "SELECT * FROM tb_amigos"+
					 " WHERE codigo_usuario_amigo = "+usuario.getCodigo()+
					 " AND codigo_amigo_amigo = "+amigo.getCodigo()+
					 ";";
		
			ResultSet rs = st.executeQuery(SQL);
			
			if (rs.next()) {
				retorno = true;
			} else {
				retorno = false;
			}
			
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
	public boolean adicionarAmizade(Usuarios usuario, Usuarios amigo) {
		
		boolean resultado = false;
		
		try {
			if (JOptionPane.showConfirmDialog(null, "Adicionar usuário à sua lista de amigos?", "Adicionar amigo", 2) == 0) {//0 = OK
				Statement st = MySQLConection.getStatementMySQL();
				
				String SQL = "INSERT INTO tb_amigos (codigo_usuario_amigo, codigo_amigo_amigo)"+
						  	 " VALUES ("+usuario.getCodigo()+", "+amigo.getCodigo()+
						  	 ");";
				st.execute(SQL);
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
	public boolean removerAmizade(Usuarios usuario, Usuarios amigo) {
		
		boolean resultado = false;
		
		try {
			if (JOptionPane.showConfirmDialog(null, "Remover usuário de sua lista de amigos?", "Remover amigo", 2) == 0) {//0 = OK
				Statement st = MySQLConection.getStatementMySQL();
				
				String SQL = "DELETE FROM tb_amigos"+
					  " WHERE codigo_usuario_amigo = "+usuario.getCodigo()+
					  " AND codigo_amigo_amigo = "+amigo.getCodigo()+
					  ";";
				st.execute(SQL);
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
