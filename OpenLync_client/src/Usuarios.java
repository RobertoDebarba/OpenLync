import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Usuarios {
	
	public boolean verificarLogin(Connection dbConnection,String usuario, String senha) throws SQLException {
		
		Statement st;
		try {
			st = dbConnection.createStatement();
		} catch (SQLException e) {
			System.out.println("Erro ao conectar ao DB");
			return false;
		}
		
		String SQL = "SELECT 1 FROM tb_usuarios WHERE login_usuario = '"+ usuario +
					 "' and senha_usuario = '"+ senha +"';";
		
		ResultSet rs = null;
		try {
			rs = st.executeQuery(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		rs.beforeFirst();
		if (!rs.next()) {
			return false;
		} else {
			return true;
		}
	}
}
