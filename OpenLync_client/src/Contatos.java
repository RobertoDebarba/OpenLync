import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class Contatos {

	public static void atualizarListaPrincipal() throws SQLException {
		Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		Usuarios usuarioLogin = FormLogin.getUsuarioLogin();
		String SQL = "SELECT (codigo_usuario) FROM tb_usuarios WHERE status_usuario = true AND"+
					 " codigo_usuario <> " + usuarioLogin.getCodigo() + ";";
		
		ResultSet rs = st.executeQuery(SQL);
		
		while(rs.next()) {
//			Usuarios usuario = new Usuarios();
//			usuario.carregarInformacoes(rs.getInt("codigo_usuario"));
//			FormIncial.setNovoUsuarioLista(usuario.getCodigo(), usuario.getNome(), usuario.getCargo(), usuario.getIp());
			FormIncial.setNovoUsuarioLista(rs.getInt("codigo_usuario"));
		}
	}
}
