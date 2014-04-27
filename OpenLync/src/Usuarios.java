import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Usuarios {

	private int codigo;
	private String nome;
	private String cargo;
	private String login;
	private String senha;
	private boolean admin;
	private BufferedImage foto;
	
	public BufferedImage getFoto() {
		return foto;
	}
	public void setFoto(BufferedImage foto) {
		this.foto = foto;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
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
	
	public boolean verifLogin(String login, String senha) {
		
		Connection conexao = MySQLConection.getMySQLConnection();
		Criptografia cript = new Criptografia();
		
		boolean result = false;
		try {
			Statement st = conexao.createStatement();
			
			String SQL = "SELECT 1 FROM tb_usuarios"+
						 " WHERE login_usuario = '"+cript.criptografarMensagem(login)+
						 		"' AND senha_usuario = '"+cript.criptografarMensagem(senha)+
						 		"' AND admin_usuario = true;";
			
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
}
