
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
						if (OpenLync_client.verificarConexaoServidor()) {
							if (usuarioLogin.verificarLogin(login, senha)) {
								OpenLync_client.iniciarEntrada();
								usuarioLogin.carregarInformacoes(login);
								usuarioLogin.setStatusOnDB(usuarioLogin.getCodigo(), true);
								usuarioLogin.setIpOnDB(usuarioLogin.getCodigo(), OpenLync_client.getIpLocal());
								FormMain.fecharFrmLogin();
								FormMain.abrirFrmInicial(usuarioLogin.getNome(), usuarioLogin.getCargo());
							} else {
								JOptionPane.showMessageDialog(null, "Usuário ou senha não encontrado!", "Login Inválido", 1);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao conectar ao servidor!", "Erro de conexão", 1);
						};
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de Dados!", "Erro de conexão", 1);
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
		
		JLabel ImgGear = new JLabel("");
		ImgGear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {  //
				FormMain.abrirFrmConfig();
			}
		});
		ImgGear.setIcon(new ImageIcon(FormLogin.class.getResource("/Imagens/gear_icon.png")));
		ImgGear.setBounds(333, 0, 25, 23);
		getContentPane().add(ImgGear);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{editUsuario, editSenha, btnEntrar, btnSair, getContentPane(), lblUsurio, lblSenha}));
		
	}	
}
