import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuarios {
	
	private int codigo = -1;
	private String nome = "";
	private String cargo = "";
	private String login = "";
	private String senha = "";
	private boolean status = false;
	private String ip = "";
	//FIXME private BLOB foto;
	
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public boolean verificarLogin() throws SQLException {
		
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT 1 FROM tb_usuarios WHERE login_usuario = '"+ this.login +
					 "' and senha_usuario = '"+ this.senha +"';";

		ResultSet rs = st.executeQuery(SQL);

		rs.beforeFirst();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void carregarInformacoes(String login) throws SQLException {
		
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT * FROM tb_usuarios WHERE login_usuario = '"+ this.login +"';";

		ResultSet rs = st.executeQuery(SQL);
		
		while(rs.next()) {
			this.codigo = rs.getInt("codigo_usuario");
			this.nome = rs.getString("nome_usuario");
			this.cargo = rs.getString("cargo_usuario");
			this.login = rs.getString("login_usuario");
			this.senha = rs.getString("senha_usuario");
			this.status = rs.getBoolean("status_usuario");
			this.ip = rs.getString("ip_usuario");
			//FIXME foto
		}
	}
	
	public void carregarInformacoes(int codigo) throws SQLException {
		
		java.sql.Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT * FROM tb_usuarios WHERE login_usuario = '"+ this.codigo +"';";

		ResultSet rs = st.executeQuery(SQL);
		
		while(rs.next()) {
			this.codigo = rs.getInt("codigo_usuario");
			this.nome = rs.getString("nome_usuario");
			this.cargo = rs.getString("cargo_usuario");
			this.login = rs.getString("login_usuario");
			this.senha = rs.getString("senha_usuario");
			this.status = rs.getBoolean("status_usuario");
			this.ip = rs.getString("ip_usuario");
			//FIXME foto
		}
	}
}
