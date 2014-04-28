
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import java.awt.Font;


public class FormLogin extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static JTextField editUsuario;
	private static JPasswordField editSenha;
	private static Usuarios usuarioLogin = null;
	
	public static Usuarios getUsuarioLogin() {
		return usuarioLogin;
	}
	
	public static String getLoginNome() {
		return editUsuario.getText();
	}
	
	public static String getLoginSenha() {
		return new String(editSenha.getPassword());
	}

	public FormLogin() {
		getContentPane().setBackground(new Color(238, 238, 238));
		setBorder(null);
		setBounds(100, 100, 370, 570);
		
		JLabel lblUsurio = new JLabel("Usuário:");
		lblUsurio.setBounds(80, 193, 60, 25);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(80, 230, 50, 23);
		
		editUsuario = new JTextField();
		editUsuario.setBounds(158, 191, 140, 25);
		editUsuario.setColumns(10);
		
		editSenha = new JPasswordField();
		editSenha.setBounds(158, 228, 140, 25);
		editSenha.setColumns(10);
		getContentPane().add(editSenha);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(80, 287, 100, 45);
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				usuarioLogin = new Usuarios();
				String login = editUsuario.getText();
				String senha = (new String(editSenha.getPassword()));
				
				if (!login.equals("") && !senha.equals("")) {
					
					if (OpenLync_client.verificarConexaoBanco()) {
						//Se o banco conectou já é seguro solicitar o IP local ao servidor
						//Se o servidor não responder o sistema irá travar
						if (OpenLync_client.verificarIPlocal()) {
						
							try {
								if (usuarioLogin.verificarLogin(login, senha)) {
									OpenLync_client.iniciarEntrada();
									usuarioLogin.carregarInformacoes(login);
									usuarioLogin.setIpOnDB(usuarioLogin.getCodigo(), OpenLync_client.getIpLocal());
									FormMain.fecharFrmLogin();
									FormMain.abrirFrmInicial(usuarioLogin.getNome(), usuarioLogin.getCargo(), usuarioLogin.getFoto());
								} else {
									JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos! / Usuário já logado!", "Login Inválido", 1);
								}
								
							} catch (HeadlessException | SQLException e) {
								e.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "Erro ao conecar ao Servidor de Mensagens", "Erro de conexão", 1);
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de Dados!", "Erro de conexão", 1);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Você deve preencher todos os campos!", "Campos inválidos", 1);
				}
			}
		});
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(198, 287, 100, 45);
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
		
		JLabel lblOpenlync = new JLabel("OpenLync");
		lblOpenlync.setFont(new Font("Ubuntu", Font.BOLD, 30));
		lblOpenlync.setBounds(113, 106, 153, 45);
		getContentPane().add(lblOpenlync);
		
	}	
}
