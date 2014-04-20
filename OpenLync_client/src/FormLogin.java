
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

import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
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
		getContentPane().setBackground(new Color(238, 238, 238));
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
				if (!editUsuario.getText().equals("") && !editSenha.getText().equals("")) {
					usuarioLogin = new Usuarios();
					String login = editUsuario.getText();
					String senha = editSenha.getText();
					if (OpenLync_client.verificarConexaoBanco()) {
						//Se o banco conectou já é seguro solicitar o IP local ao servidor
						//Se o servidor não responder o sistema irá travar
						OpenLync_client.verificarIPlocal();
						
						try {
							if (usuarioLogin.verificarLogin(login, senha)) {
								OpenLync_client.iniciarEntrada();
								usuarioLogin.carregarInformacoes(login);
								usuarioLogin.setStatusOnDB(usuarioLogin.getCodigo(), true);
								usuarioLogin.setIpOnDB(usuarioLogin.getCodigo(), OpenLync_client.getIpLocal());
								FormMain.fecharFrmLogin();
								FormMain.abrirFrmInicial(usuarioLogin.getNome(), usuarioLogin.getCargo(), usuarioLogin.getFoto());
							} else {
								JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos ou Usuário já logado!", "Login Inválido", 1);
							}
							
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
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
