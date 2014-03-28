import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Contatos {

	public static FormUsuarioLista listaInternalFrames[] = new FormUsuarioLista[100]; //FIXME
	
	private static int i = 0;
	
	public static void atualizarListaPrincipal() throws SQLException {
		
		Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		Usuarios usuarioLogin = FormLogin.getUsuarioLogin();
		// SELECT codigo_usuario - todos logados exeto o usuario que realizou o login no programa
		String SQL = "SELECT (codigo_usuario) FROM tb_usuarios WHERE status_usuario = true AND"+
					 " codigo_usuario <> " + usuarioLogin.getCodigo() + ";";
		
		ResultSet rs = st.executeQuery(SQL);
		
		//Limpa a lista - retira apenas os usuarios que estao offline
		limparListaUsuarios(i);
		
		i = 0;
		
		while(rs.next()) {
			
			boolean usuarioEstaNaLista = false;
			int a = 0;
			// Varre lista de usuarios(a lista possui todos usuarios que estão no JDesktopPane)
			while ((a < 100) && (!usuarioEstaNaLista)) {//FIXME tamanho grid
				
				//Verifica se usuario atual do rs ja está na lista(JDesktopPane)
				if (listaInternalFrames[a] != null) {
					if (listaInternalFrames[a].getCodigoUsuario() == rs.getInt("codigo_usuario")) {
						//Variavel de controle para parar loop e definir proximo passo
						usuarioEstaNaLista = true;
					}
				}
				
				a++;
			}
			
			//Se usuario NÃO está na lista
			if (!usuarioEstaNaLista) {
				
				boolean jaPreencheu = false;
				int b = 0;
				//Varre lista ate encontrar uma posicao vazia para adicionar novo usuario
				while ((b < 100) && (!jaPreencheu)) {//FIXME tamanho grid
					
					if (listaInternalFrames[b] == null) {
						//Adiciona usuario ao local null encontrado
						listaInternalFrames[b] = FormUsuarioLista.getNovoFormUsuarioLista(rs.getInt("codigo_usuario"));
						//Adiciona o usuario ao JDesktopPane
						setUsuarioNaLista(listaInternalFrames[b]);
						//Variavel de controle para parar loop
						jaPreencheu = true;
					}
					
					b++;
				}
			}
			
			i++;
		}
	}
	
	public static void setUsuarioNaLista(FormUsuarioLista frmUsuarioLista) {
		
		FormIncial.jdpUsuarios.add(frmUsuarioLista);
		frmUsuarioLista.setVisible(true);
	}
	
	public static void limparListaUsuarios(int quantidade) {
		
		int i = 0;
		// Varre lista  para verificar posicoes NÂO NULAS
		while (i < 100) { //FIXME tamanho grid

			Usuarios userTeste = new Usuarios();
			try {
				
				if (listaInternalFrames[i] != null) {
					//Instancia novo usuario com codigo da posicao atual da grid para verificar status_usuario
					userTeste.carregarInformacoes(listaInternalFrames[i].getCodigoUsuario());
					//Se usuario está offline(status_usuario = false) remove da JDesktopPane e seta NULL NA LISTA
					if (!userTeste.getStatus()) {
						FormIncial.jdpUsuarios.remove(listaInternalFrames[i]);
						FormIncial.jdpUsuarios.repaint();
						listaInternalFrames[i] = null; //FIXME testetesteteste
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			i++;
		}
	}
}
