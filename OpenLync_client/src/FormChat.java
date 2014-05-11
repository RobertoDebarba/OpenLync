
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private SaidaDados conexaoSaida;
	private JPanel panelStatus;
	
	private Usuarios usuario;

	public Usuarios getUsuario() {
		
		return usuario;
	}
	
	public FormChat(final Usuarios usuario) {
		
		this.usuario = usuario;
		
		setTitle(usuario.getNome());
		
		//Abre Conexão de Saida
		conexaoSaida = new SaidaDados(usuario.getIp());
		Thread threadSaida = new Thread(conexaoSaida);
		threadSaida.start();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(366, 420));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Dialog", Font.PLAIN, 12));
		textPane.setEditable(false);
		textPane.setBounds(8, 70, 355, 248);
		textPane.setFont(Font.getFont("Dialog"));
		contentPane.add(textPane);
		
		panelStatus = new JPanel();
		panelStatus.setBounds(10, 8, 7, 57);
		if (usuario.getStatus()) {
			panelStatus.setBackground(new Color(0, 200, 0));
		} else {
			panelStatus.setBackground(new Color(200, 0, 0));
		}
		getContentPane().add(panelStatus);
		
		JLabel labelFoto = new JLabel("");
		labelFoto.setIcon(new ImageIcon(usuario.getFoto()));
		labelFoto.setBounds(18, 7, 57, 57);
		contentPane.add(labelFoto);
		
		JLabel labelNome = new JLabel(usuario.getNome());
		labelNome.setFont(new Font("Dialog", Font.BOLD, 16));
		labelNome.setBounds(82, 16, 285, 15);
		contentPane.add(labelNome);
		
		JLabel labelCargo = new JLabel(usuario.getCargo());
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(82, 42, 285, 15);
		contentPane.add(labelCargo);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {		// Ao pressionar ENTER
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Robot robot = null;
					try {
						robot = new Robot();
					} catch (AWTException e1) {
					}
					// Apaga o ENTER
					robot.keyPress(KeyEvent.VK_BACK_SPACE);
					robot.keyRelease(KeyEvent.VK_BACK_SPACE);
					
					enviarMensagem();
				}
			}
		});
		textArea.setBounds(8, 322, 280, 59);
		contentPane.add(textArea);
		
		JButton BtnEnviar = new JButton("Enviar");		
		BtnEnviar.addActionListener(new ActionListener() {		// Ao clicar botão ENVIAR
			public void actionPerformed(ActionEvent arg0) {
				enviarMensagem();
			}
		});
		BtnEnviar.setBounds(293, 322, 63, 59);
		contentPane.add(BtnEnviar);
		
		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(8, 70, 348, 248);
		contentPane.add(scrollPane);
		
		
		final UsuariosDAO dao = new UsuariosDAO();
		
		final JLabel lblAmigo = new JLabel("Amigo");
		if (dao.verificarAmizade(FormLogin.getUsuarioLogin(), usuario)) {
			lblAmigo.setIcon(new ImageIcon(FormChat.class.getResource("/Imagens/amigo-.png")));
		} else {
			lblAmigo.setIcon(new ImageIcon(FormChat.class.getResource("/Imagens/amigo+.png")));
		}
		lblAmigo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	//Adicionar ou remover amigos
				if (dao.verificarAmizade(FormLogin.getUsuarioLogin(), usuario)) {
					if (dao.removerAmizade(FormLogin.getUsuarioLogin(), usuario)) {
						lblAmigo.setIcon(new ImageIcon(FormChat.class.getResource("/Imagens/amigo+.png")));
					}
				} else {
					if (dao.adicionarAmizade(FormLogin.getUsuarioLogin(), usuario)) {
						lblAmigo.setIcon(new ImageIcon(FormChat.class.getResource("/Imagens/amigo-.png")));
					}
				}
			}
		});
		lblAmigo.setBounds(335, 47, 22, 22);
		contentPane.add(lblAmigo);
		
		
		//Timer para verificar se contato continua online
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
            @Override
            public void run() {
				verificarStatusContato();
            }
        }, 1000, 4000);
				
				
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {	// Ao fechar janela
				//Remove chat da listaChat
				Contatos contatos = new Contatos();
				contatos.removerFormChat(usuario);
				
				//Encerra timer e thread de saida de dados
				t.cancel();
				conexaoSaida.encerrarThread();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				textArea.grabFocus();
			}
		});
		
		setResizable(false);
	}
	
	/**
	 * Verifica status atual do usuario do chat no DB;
	 * Se usuário se desconectou fecha a janela de chat //TODO implementar comportamento melhor
	 */
	private void verificarStatusContato() {
		
		String ipAtual= "";
		try {
			java.sql.Connection conexao = MySQLConection.getMySQLConnection();
			Statement st = conexao.createStatement();
		
			String SQL = "SELECT ip_usuario FROM tb_usuarios WHERE codigo_usuario = "+ usuario.getCodigo() +";";
	
			ResultSet rs = st.executeQuery(SQL);

			if (rs.next()) {
				ipAtual = rs.getString("ip_usuario");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Se o usuario ficou offline
		if (ipAtual.equals("null")) {
			
			usuario.setIp("null");	
			panelStatus.setBackground(new Color(200, 0, 0));
		} else {
			
			usuario.setIp(ipAtual);
			panelStatus.setBackground(new Color(0, 200, 0));
		}
	}
	
	/**
	 * Adiciona mensagem ao textPanel
	 * @param mensagem
	 * @param remetente
	 */
	public void adicionarMensagem(String mensagem, String remetente) {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String linhas = "";
		if (remetente != "local") {
			if (textPane.getText().equals("")) {
				linhas = usuario.getNome() + "  " + sdf.format(date);
			} else {
				linhas = textPane.getText() + "\n" + usuario.getNome() + "  " + sdf.format(date);
			}
		} else {
			Usuarios userLogin = FormLogin.getUsuarioLogin();
			if (textPane.getText().equals("")) {
				linhas = userLogin.getNome() + "  " + sdf.format(date);
			} else {
				linhas = textPane.getText() + "\n" + userLogin.getNome() + "  " + sdf.format(date);
			}
		}
		
		linhas = linhas + "\n   " + mensagem + "\n";
		textPane.setText(linhas);
		
		// Manda scroll para o final
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}
	
	/**
	 * Envia a mensagem.
	 * Evento usado pelo btnEnviar e Enter
	 */
	private void enviarMensagem() {
		
		//Se campo não estiver vazio
		if (!textArea.getText().equals("")) {
			adicionarMensagem(textArea.getText(), "local");
			
			//Se usuario estiver online -> manda mensagem via Socket
			if (usuario.getStatus()) {
				conexaoSaida.enviarMensagem(textArea.getText());
			}
			
			/* --- Adiciona mensagem ao Historico no Banco de Dados (tb_mensagens) --- */
			Mensagens mensagens = new Mensagens();
			//Se usuario estiver Online a mensagem é considerada lida
			boolean mensagemLida = (usuario.getStatus()) ? true : false;
			
			mensagens.adicionarMensagem(textArea.getText(), FormLogin.getUsuarioLogin().getCodigo(), usuario.getCodigo(), new Date(), mensagemLida);
			
			// Limpa campos
			textArea.setText(null);
			textArea.grabFocus();
		}
	}
}
