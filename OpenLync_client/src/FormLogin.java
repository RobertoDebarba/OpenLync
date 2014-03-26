
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class FormLogin extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static JTextField editUsuario;
	private static JTextField editSenha;
	private static Usuarios usuarioLogin = null;

	
	public static Usuarios getUsuarioLogin() {
		return usuarioLogin;
	}
	
	public static String getLoginNome() {
		return editUsuario.getText();
	}
	
	public static String getLoginSenha() {
		return editSenha.getText();
	}

	public FormLogin() {
		setBorder(null);
		setBounds(100, 100, 370, 570);
		
		JLabel lblUsurio = new JLabel("Usuário:");
		lblUsurio.setBounds(76, 114, 60, 25);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(76, 151, 50, 23);
		
		editUsuario = new JTextField();
		editUsuario.setBounds(154, 112, 140, 25);
		editUsuario.setColumns(10);
		
		editSenha = new JTextField();
		editSenha.setBounds(154, 149, 140, 25);
		editSenha.setColumns(10);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(76, 208, 100, 45);
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					usuarioLogin = new Usuarios();
					String login = editUsuario.getText();
					String senha = editSenha.getText();
					try {
						if (usuarioLogin.verificarLogin(login, senha)) {
							usuarioLogin.carregarInformacoes(login);
							usuarioLogin.setStatusDB(usuarioLogin.getCodigo(), true);
							usuarioLogin.setIpDB(usuarioLogin.getCodigo(), OpenLync_client.getIpLocal());
							FormMain.fecharFrmLogin();
							FormMain.abrirFrmInicial(usuarioLogin.getNome(), usuarioLogin.getCargo());
							Contatos.atualizarListaPrincipal();
						} else {
							JOptionPane.showMessageDialog(null, "Usuário ou senha não encontrado!", "Login Inválido", 1);
						};
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		});
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(194, 208, 100, 45);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(btnEntrar);
		getContentPane().add(btnSair);
		getContentPane().add(lblUsurio);
		getContentPane().add(lblSenha);
		getContentPane().add(editUsuario);
		getContentPane().add(editSenha);

	}

}
