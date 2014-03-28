import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Contatos {

	private static int i = 0;
	
	public static void atualizarListaPrincipal() throws SQLException {
		Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		Usuarios usuarioLogin = FormLogin.getUsuarioLogin();
		String SQL = "SELECT (codigo_usuario) FROM tb_usuarios WHERE status_usuario = true AND"+
					 " codigo_usuario <> " + usuarioLogin.getCodigo() + ";";
		
		ResultSet rs = st.executeQuery(SQL);
		
		FormIncial.limparUsuariosLista(i);
		
		i = 0;
		
		while(rs.next()) {
			
			boolean tem = false;
			int a = 0;
			while (a < 100) {//FIXME tamanho grid
				
				if (FormIncial.listaInternalFrames[a] != null) {
					if (FormIncial.listaInternalFrames[a].getCodigoUsuario() == rs.getInt("codigo_usuario")) {
						tem = true;
					} else {
						
					}
				}
				
				a++;
				
			}
			
			if (!tem) {
				
				boolean jaPreencheu = false;
				int b = 0;
				while ((b < 100) && (!jaPreencheu)) {//FIXME tamanho grid
					
					if (FormIncial.listaInternalFrames[b] == null) {
						FormIncial.listaInternalFrames[b] = FormIncial.getNovoFormUsuarioLista(rs.getInt("codigo_usuario"));
						FormIncial.setUsuarioNaLista(FormIncial.listaInternalFrames[b]);
						jaPreencheu = true;
					}
					
					b++;
				}
				
				
//				jdpUsuarios.add(listaInternalFrames[contador]);
//				listaInternalFrames[contador].setVisible(true);
			}
			
			
			
			//FormIncial.setNovoUsuarioLista(rs.getInt("codigo_usuario"), i);
			
				
			i++;
		}
	}
}
