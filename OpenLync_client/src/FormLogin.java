import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import com.mysql.jdbc.Connection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class FormLogin extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JTextField editUsuario;
	private static JTextField editSenha;
	
	private static Usuarios usuarioLogin = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormLogin frame = new FormLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static Usuarios getUsuarioLogin() {
		return usuarioLogin;
	}
	
	public static String getLoginNome() {
		return editUsuario.getText();
	}
	
	public static String getLoginSenha() {
		return editSenha.getText();
	}

	/**
	 * Create the frame.
	 */
	public FormLogin() {
		setBorder(null);
		setBounds(100, 100, 370, 570);
		
		JLabel lblUsurio = new JLabel("Usuário:");
		
		JLabel lblSenha = new JLabel("Senha:");
		
		editUsuario = new JTextField();
		editUsuario.setColumns(10);
		
		editSenha = new JTextField();
		editSenha.setColumns(10);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					usuarioLogin = new Usuarios();
					usuarioLogin.setLogin(editUsuario.getText());
					usuarioLogin.setSenha(editSenha.getText());
					try {
						if (usuarioLogin.verificarLogin()) {
							usuarioLogin.carregarInformacoes(usuarioLogin.getLogin());
							FormMain.fecharFrmLogin();
							FormMain.abrirFrmInicial();
							FormIncial.carregarInformacoes(usuarioLogin.getNome(), usuarioLogin.getCargo());
							Contatos.atualizarListaPrincipal();
						} else {
							// Mostra mensagem
							JOptionPane.showMessageDialog(null, "Usuário ou senha não encontrado!", "Login Inválido", 1);
						};
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		});
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(76)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnEntrar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsurio)
								.addComponent(lblSenha))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(editUsuario, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
								.addComponent(editSenha, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))))
					.addGap(76))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(112)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsurio)
						.addComponent(editUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha)
						.addComponent(editSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEntrar, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(295, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);

	}

}
