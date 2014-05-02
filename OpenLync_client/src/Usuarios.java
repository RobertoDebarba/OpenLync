
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

public class Usuarios {
	
	private int codigo = -1;
	private String nome = "";
	private String cargo = "";
	private String login = "";
	private String senha = "";
	private String ip = "";
	private BufferedImage foto = null;
	
	public boolean getStatus() {
		if (this.ip.equals("null")) {
			return false;
		} else {
			return true;
		}
	}
	
	public BufferedImage getFoto() {
		return foto;
	}

	public void setFoto(BufferedImage foto) {
		this.foto = foto;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
		
	public void setIpOnDB(int codigoUsuario, String ip) throws SQLException {
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "UPDATE tb_usuarios SET ip_usuario = '"+ip+
					 "' WHERE codigo_usuario = "+codigoUsuario+";";
		
		st.executeUpdate(SQL);
		
		this.ip = ip;
	}
	
	public boolean verificarLogin(String login, String senha) throws SQLException {
		
		Criptografia cript = new Criptografia();
		
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT 1 FROM tb_usuarios WHERE login_usuario = '"+ cript.criptografarMensagem(login) +"'" +
					 " AND senha_usuario = '"+ cript.criptografarMensagem(senha) +"'" +
					 " AND ip_usuario = 'null';";

		ResultSet rs = st.executeQuery(SQL);

		rs.beforeFirst();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void carregarInformacoes(String login) throws SQLException {
		
		Criptografia cript = new Criptografia();
		
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT codigo_usuario, nome_usuario, login_usuario, senha_usuario, ip_usuario, foto_usuario, tb_cargos.desc_cargo"+
					 " FROM tb_usuarios, tb_cargos" +
					 " WHERE tb_usuarios.login_usuario = '"+cript.criptografarMensagem(login)+"' AND tb_cargos.codigo_cargo = tb_usuarios.codigo_cargo;";

		ResultSet rs = st.executeQuery(SQL);
		
		rs.beforeFirst();
		
		while(rs.next()) {
			this.codigo = rs.getInt("codigo_usuario");
			this.nome = rs.getString("nome_usuario");
			this.cargo = rs.getString("desc_cargo");
			this.login = cript.descriptografarMensagem(rs.getString("login_usuario"));
			this.senha = cript.descriptografarMensagem(rs.getString("senha_usuario"));
			this.ip = rs.getString("ip_usuario");
			Blob blobImage = rs.getBlob("foto_usuario");
			try {
				if (blobImage == null) {
					java.io.InputStream fis = getClass().getResourceAsStream("/Imagens/imgPerfilGenerica.jpg");				
					this.foto = ImageIO.read(fis);
				} else {
					this.foto = ImageIO.read(blobImage.getBinaryStream());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void carregarInformacoes(int codigo) throws SQLException {
		
		Criptografia cript = new Criptografia();
		
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT codigo_usuario, nome_usuario, login_usuario, senha_usuario, ip_usuario, foto_usuario, tb_cargos.desc_cargo"+
				 	 " FROM tb_usuarios, tb_cargos" +
				 	 " WHERE tb_usuarios.codigo_usuario = "+codigo+" AND tb_cargos.codigo_cargo = tb_usuarios.codigo_cargo;";

		ResultSet rs = st.executeQuery(SQL);
		
		rs.beforeFirst();
		while(rs.next()) {
			this.codigo = rs.getInt("codigo_usuario");
			this.nome = rs.getString("nome_usuario");
			this.cargo = rs.getString("desc_cargo");
			this.login = cript.descriptografarMensagem(rs.getString("login_usuario"));
			this.senha = cript.descriptografarMensagem(rs.getString("senha_usuario"));
			this.ip = rs.getString("ip_usuario");
			Blob blobImage = rs.getBlob("foto_usuario");
			try {
				if (blobImage == null) {
					java.io.InputStream fis = getClass().getResourceAsStream("/Imagens/imgPerfilGenerica.jpg");				
					this.foto = ImageIO.read(fis);
				} else {
					this.foto = ImageIO.read(blobImage.getBinaryStream());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void carregarInformacoesPorIP(String ip) throws SQLException {
			
		Criptografia cript = new Criptografia();
	
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT codigo_usuario, nome_usuario, login_usuario, senha_usuario, ip_usuario, foto_usuario, tb_cargos.desc_cargo"+
				 	 " FROM tb_usuarios, tb_cargos" +
				 	 " WHERE tb_usuarios.ip_usuario = '"+ip+"' AND tb_cargos.codigo_cargo = tb_usuarios.codigo_cargo;";

		ResultSet rs = st.executeQuery(SQL);
		
		rs.beforeFirst();
		while(rs.next()) {
			this.codigo = rs.getInt("codigo_usuario");
			this.nome = rs.getString("nome_usuario");
			this.cargo = rs.getString("desc_cargo");
			this.login = cript.descriptografarMensagem(rs.getString("login_usuario"));
			this.senha = cript.descriptografarMensagem(rs.getString("senha_usuario"));
			this.ip = rs.getString("ip_usuario");
			Blob blobImage = rs.getBlob("foto_usuario");			
			try {
				if (blobImage == null) {
					java.io.InputStream fis = getClass().getResourceAsStream("/Imagens/imgPerfilGenerica.jpg");				
					this.foto = ImageIO.read(fis);
				} else {
					this.foto = ImageIO.read(blobImage.getBinaryStream());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean verificarAmizade(int codigoAmigo) {
		Connection conexao = MySQLConection.getMySQLConnection();
		Usuarios userLogin = FormLogin.getUsuarioLogin();
		
		Statement st;
		boolean retorno = false;
		try {
			st = conexao.createStatement();
			
			String SQL = "SELECT * FROM tb_amigos"+
					 " WHERE codigo_usuario_amigo = "+userLogin.getCodigo()+
					 " AND codigo_amigo_amigo = "+codigoAmigo+
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
}
