import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuariosDAO {
	
	private Criptografia cript = new Criptografia();
	private Connection conexao = MySQLConection.getMySQLConnection();

	public void adicionar(Usuarios usuario) {
		
		try {
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = '"+usuario.getCargo()+"';";
			ResultSet rs = st.executeQuery(SQL);
			rs.next();
			int cargo = rs.getInt("codigo_cargo");
			
			SQL = "INSERT INTO tb_usuarios (codigo_usuario, nome_usuario, cargo_usuario, login_usuario, senha_usuario)" +
						 " VALUES ("+usuario.getCodigo()+", '"+usuario.getNome()+"', "+cargo+","+
						 " '"+cript.criptografarMensagem(usuario.getLogin())+"' , '"+cript.criptografarMensagem(usuario.getSenha())+"');";
			st.executeQuery(SQL);
				
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
			
			SQL = "UPDATE tb_usuario SET" +
						 " nome_usuario = '"+usuario.getNome()+
						 "' cargo_usuario = "+cargo+
						 " login_usuario = '"+cript.criptografarMensagem(usuario.getLogin())+
						 "' senha_usuario = '"+cript.criptografarMensagem(cript.criptografarMensagem(usuario.getSenha()))+"';";
			st.execute(SQL);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void apagar(Usuarios usuario) {
		
		try {
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "DELETE FROM tb_usuarios WHERE codigo_usuario = "+usuario.getCodigo()+";";
			st.executeQuery(SQL);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
