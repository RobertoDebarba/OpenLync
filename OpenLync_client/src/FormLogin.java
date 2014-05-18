
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPasswordField;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.awt.Font;


public class FormLogin extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static JTextField editUsuario;
	private static JPasswordField editSenha;
	private static Usuarios usuarioLogin = null;
		
	public static Usuarios getUsuarioLogin() {
		return usuarioLogin;
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
				
				String login = editUsuario.getText();
				String senha = (new String(editSenha.getPassword()));
				
				if (!login.equals("") && !senha.equals("")) {
					
					if (MySQLConection.verificarConexaoMySQL()) {
						//Se o banco conectou já é seguro solicitar o IP local ao servidor
						
						//Abre conexão definitiva com o banco de dados
						MySQLConection.abrirConexaoMySQL();
						if (Configuracoes.verificarIPlocal()) {
							
							UsuariosDAO dao = new UsuariosDAO(true);
							try {
								if (dao.verificarLogin(login, senha)) {
									//Cria e alimenta usuario Principal (usuarioLogin)
									EntradaDados.iniciarEntrada();
									usuarioLogin = dao.procurarUsuarioLogin(login);
									usuarioLogin.setIp(Configuracoes.getIpLocal());
									dao.setIPDB(usuarioLogin);
									
									FormMain.fecharFrmLogin();
									FormMain.abrirFrmInicial(usuarioLogin);
								} else {
									JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos! / Usuário já logado!", "Login Inválido", 1);
								}
								
							} catch (HeadlessException e) {
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
			public void mouseClicked(MouseEvent e) {
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
		
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(this);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)this.getUI()).setNorthPane(null); //retirar o painel superior  
 		((BasicInternalFrameUI)this.getUI()).setSouthPane(null); //retirar o painel inferior
  		setBorder(null);//retirar bordas  
  		
  		// Seta centro
  		this.setLocation(0, 0);
	}	
}
