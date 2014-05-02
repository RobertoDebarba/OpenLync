import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import javax.swing.JOptionPane;

public class Contatos {

	public static FormUsuarioLista listaInternalFrames[] = new FormUsuarioLista[100]; //FIXME
	
	public static FormChat[] listaChat = new FormChat[100]; //FIXME tornar tamanho dinamico
	private static int contadorChat = 0;
		
	public static void setContadorChat(int contadorChat) {
		Contatos.contadorChat = contadorChat;
	}
	
	public static int getContadorChat() {
		return contadorChat;
	}
	
	public static void incContadorChat() {
		contadorChat++;
	}
	
	public static void decContadorChat() {
		contadorChat--;
	}
	
	
	public static void atualizarListaPrincipal() throws SQLException {
		
		Connection conexao = MySQLConection.getMySQLConnection();
		Statement st = conexao.createStatement();
		
		Usuarios usuarioLogin = FormLogin.getUsuarioLogin();
		
		String SQL;
		String campoRetornado = "";
		//Seleciona apenas Amigos
		if (FormIncial.indexBtn == 1) {
			//Se opção para exibir apenas online estiver marcada
			if (FormIncial.checkOnline.isSelected()) {
				SQL = "SELECT codigo_amigo_amigo"+
					  " FROM tb_amigos, tb_usuarios"+
					  " WHERE codigo_usuario_amigo = 1"+
					  " AND tb_usuarios.codigo_usuario = tb_amigos.codigo_amigo_amigo"+
					  " AND tb_usuarios.ip_usuario <> 'null';";
			} else {
				SQL = "SELECT (codigo_amigo_amigo)"+
					  " FROM tb_amigos"+
					  " WHERE codigo_usuario_amigo = "+usuarioLogin.getCodigo()+";";	
			}
			campoRetornado = "codigo_amigo_amigo";
		//Seleciona todos usuarios
		} else {
			//Se opção para exibir apenas online estiver marcada
			if (FormIncial.checkOnline.isSelected()) {
				// SELECT codigo_usuario - todos logados exeto o usuario que realizou o login no programa
				SQL = "SELECT (codigo_usuario)"+
					  " FROM tb_usuarios WHERE ip_usuario <> 'null'"+
					  " AND codigo_usuario <> " + usuarioLogin.getCodigo() + ";";
			} else {
				// SELECT codigo_usuario - todos exeto o usuario que realizou o login no programa
				SQL = "SELECT (codigo_usuario)"+
					  " FROM tb_usuarios"+
					  " WHERE codigo_usuario <> "+usuarioLogin.getCodigo()+";";
			}
			campoRetornado = "codigo_usuario";
		}
			
		ResultSet rs = st.executeQuery(SQL);
		
		//Limpa a lista - retira apenas os usuarios que estao offline
		limparListaUsuarios();
		
		while(rs.next()) {
			
			boolean usuarioEstaNaLista = false;
			int a = 0;
			// Varre lista de usuarios(a lista possui todos usuarios que estão no JDesktopPane)
			while ((a < 100) && (!usuarioEstaNaLista)) {//FIXME tamanho grid
				
				//Verifica se usuario atual do rs ja está na lista(JDesktopPane)
				if (listaInternalFrames[a] != null) {
					if (listaInternalFrames[a].getCodigoUsuario() == rs.getInt(campoRetornado)) {
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
						listaInternalFrames[b] = FormUsuarioLista.getNovoFormUsuarioLista(rs.getInt(campoRetornado));
						//Adiciona o usuario ao JDesktopPane
						setUsuarioNoJDP(listaInternalFrames[b]);
						//Variavel de controle para parar loop
						jaPreencheu = true;
					}
					
					b++;
				}
			}
		}
	}
	
	public static void setUsuarioNoJDP(FormUsuarioLista frmUsuarioLista) {
		
		FormIncial.jdpUsuarios.add(frmUsuarioLista);
		frmUsuarioLista.setVisible(true);
	}
	
	public static void limparListaUsuarios() {
		
		int i = 0;
		// Varre lista  para verificar posicoes NÂO NULAS
		int y = 0;
		while (i < 100) { //FIXME tamanho grid

			Usuarios userTeste = new Usuarios();
			try {
				
				if (listaInternalFrames[i] != null) {
					//Instancia novo usuario com codigo da posicao atual da grid para verificar o status do usuario
					userTeste.carregarInformacoes(listaInternalFrames[i].getCodigoUsuario());
					//Se usuario está offline(ip_usuario <> 'null') remove da JDesktopPane e seta NULL NA LISTA
					
					//Se opção para exibir apenas online estiver marcada
					if (FormIncial.checkOnline.isSelected()) {
						if (!userTeste.getStatus()) {
							FormIncial.jdpUsuarios.remove(listaInternalFrames[i]);
							//FormIncial.jdpUsuarios.repaint();
							listaInternalFrames[i] = null;
							
							FormUsuarioLista.decContadorPosicaoUsuario();
						} else {
							//Reorganiza posições dos forms na lista de contatos
							listaInternalFrames[i].setLocation(0, y);
							y = y + 60;
						}
					} else {
						if (!userTeste.getStatus()) {
							listaInternalFrames[i].setStatusUsuario(false);
						} else {
							listaInternalFrames[i].setStatusUsuario(true);
						}
					}
					
					FormIncial.jdpUsuarios.repaint();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			i++;
		}
	}
	
	public static boolean modificarAmigo(int codigoAmigo, boolean adicionar) {
		
		Usuarios userLogin = FormLogin.getUsuarioLogin();
		Connection conexao = MySQLConection.getMySQLConnection();
		
		Statement st;
		String SQL;	
		boolean resultado = false;
		
		try {
			st = conexao.createStatement();
			
			if (!adicionar) {
				if (JOptionPane.showConfirmDialog(null, "Remover usuário da sua lista de amigos?", "Remover amigo", 2) == 0) {//0 = OK
					SQL = "DELETE FROM tb_amigos"+
						  " WHERE codigo_usuario_amigo = "+userLogin.getCodigo()+
						  " AND codigo_amigo_amigo = "+codigoAmigo+
						  ";";
					st.execute(SQL);
					resultado = true;
				} else {
					resultado = false;
				}
			} else {
				if (JOptionPane.showConfirmDialog(null, "Adicionar usuários à sua lista de amigos?", "Adicionar amigo", 2) == 0) {//0 = OK
					SQL = "INSERT INTO tb_amigos (codigo_usuario_amigo, codigo_amigo_amigo)"+
						  " VALUES ("+userLogin.getCodigo()+", "+codigoAmigo+
						  ");";
					st.execute(SQL);
					resultado = true;
				} else {
					resultado = false;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro inesperado ao adicionar aos amigos!", "Erro", 1);
			resultado = false;
		}
		
		return resultado;
	}
}
