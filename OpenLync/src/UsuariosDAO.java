import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;


public class UsuariosDAO {
	
	private Criptografia cript = new Criptografia();
	private Connection conexao = MySQLConection.getMySQLConnection();

	
	/*
	 * Verificar se nome de usuario(login) ja está cadastrado
	 */
	public boolean verificarDispLogin(String login) throws SQLException {
		
		Criptografia crip = new Criptografia();
		
		if (!login.equals("")) {	//Se login não estiver vazio
			ResultSet rs;
	
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "SELECT codigo_usuario FROM tb_usuarios" +
						 " WHERE login_usuario = '"+crip.criptografarMensagem(login)+"';";
			
			rs = st.executeQuery(SQL);
			
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		};
		
		return true;
	}
	
	/*
	 * Retorna proximo codigo utilizavel;
	 */
	public int getNovoCodigo() {
		
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
	
	public void adicionar(Usuarios usuario) {
		
		try {
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = '"+usuario.getCargo()+"';";
			ResultSet rs = st.executeQuery(SQL);
			rs.next();
			int cargo = rs.getInt("codigo_cargo");
			
			if (usuario.getFoto() == null) {	//Se foto estiver vazia
				
				SQL = "INSERT INTO tb_usuarios (codigo_usuario, nome_usuario, codigo_cargo, login_usuario, senha_usuario, ip_usuario)" +
						 " VALUES ("+usuario.getCodigo()+", '"+usuario.getNome()+"', "+cargo+","+
						 " '"+cript.criptografarMensagem(usuario.getLogin())+"' , '"+cript.criptografarMensagem(usuario.getSenha())+"',"+
						 " 'null');";
				
				st.execute(SQL);
			} else {							//Se houver alguma foto
				
				SQL = "INSERT INTO tb_usuarios (codigo_usuario, nome_usuario, codigo_cargo, login_usuario, senha_usuario, ip_usuario, foto_usuario)" +
						 " VALUES ("+usuario.getCodigo()+", '"+usuario.getNome()+"', "+cargo+","+
						 " '"+cript.criptografarMensagem(usuario.getLogin())+"' , '"+cript.criptografarMensagem(usuario.getSenha())+"',"+
						 " 'null', ?);";
				
				//Prepara imagem para INSERT ---------------------------------------------------------
				java.sql.PreparedStatement pst = conexao.prepareStatement(SQL);
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					ImageIO.write(usuario.getFoto(), "jpeg", out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				byte[] buf = out.toByteArray();
				ByteArrayInputStream inStream = new ByteArrayInputStream(buf);
				
				//------------------------------------------------------------------------------------
				pst.setBinaryStream(1, inStream, inStream.available());;
				pst.executeUpdate();
				
				pst.close();
			}
			
			rs.close();
			st.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void editar(Usuarios usuario) {
		
		try {
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = '"+usuario.getCargo()+"';";
			ResultSet rs = st.executeQuery(SQL);
			rs.next();
			int cargo = rs.getInt("codigo_cargo");
			
			if (usuario.getFoto() == null) { 	//Se usuario nao possuir foto
				SQL = "UPDATE tb_usuario SET" +
							 " nome_usuario = '"+usuario.getNome()+
							 "' cargo_usuario = "+cargo+
							 " login_usuario = '"+cript.criptografarMensagem(usuario.getLogin())+
							 "' senha_usuario = '"+cript.criptografarMensagem(cript.criptografarMensagem(usuario.getSenha()))+
							 "' foto_usuario = null;";
				st.execute(SQL);
			
			} else {	//Se houver alguma foto
				SQL = "UPDATE tb_usuario SET" +
						 " nome_usuario = '"+usuario.getNome()+
						 "' cargo_usuario = "+cargo+
						 " login_usuario = '"+cript.criptografarMensagem(usuario.getLogin())+
						 "' senha_usuario = '"+cript.criptografarMensagem(cript.criptografarMensagem(usuario.getSenha()))+
						 "' foto_usuario = ?;";
				
				//Prepara imagem para INSERT ---------------------------------------------------------
				java.sql.PreparedStatement pst = conexao.prepareStatement(SQL);
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					ImageIO.write(usuario.getFoto(), "jpeg", out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				byte[] buf = out.toByteArray();
				ByteArrayInputStream inStream = new ByteArrayInputStream(buf);
				
				//------------------------------------------------------------------------------------
				pst.setBinaryStream(1, inStream, inStream.available());;
				pst.executeUpdate();
				
				pst.close();
			}
			
			st.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void apagar(Usuarios usuario) {
		
		try {
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "DELETE FROM tb_usuarios WHERE codigo_usuario = "+usuario.getCodigo()+";";
			st.execute(SQL);
			
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
