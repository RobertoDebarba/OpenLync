import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class Contatos {

	public static void atualizarListaPrincipal() throws SQLException {
		Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		String SQL = "SELECT * FROM tb_usuarios WHERE status_usuario = true;";
		
		ResultSet rs = st.executeQuery(SQL);
		
		while(rs.next()) {
			Usuarios usuario = new Usuarios();
			usuario.carregarInformacoes(rs.getInt("codigo_usuario"));
			FormIncial.setNovoUsuarioLista(usuario.getNome(), usuario.getCargo());
		}
	}
}
