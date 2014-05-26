package openlync.forms;
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

import openlync.principal.Contatos;
import openlync.principal.Mensagens;
import openlync.principal.Usuarios;
import openlync.principal.UsuariosDAO;
import openlync.sockets.SaidaDados;
import openlync.utilidades.Criptografia;
import openlync.utilidades.MySQLConection;

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

	/**
	 * 
	 * @param usuario
	 * @param modo
	 *            : 0 - normal / 1 - mensagens não lidas / 2 - historico de
	 *            mensagens
	 */
	public FormChat(final Usuarios usuario, int modo) {

		this.usuario = usuario;

		setTitle(usuario.getNome());

		// Abre Conexão de Saida
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
		panelStatus.setBounds(10, 8, 8, 56);
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
			public void keyPressed(KeyEvent e) { // Ao pressionar ENTER
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
		BtnEnviar.addActionListener(new ActionListener() { // Ao clicar botão
															// ENVIAR
					public void actionPerformed(ActionEvent arg0) {
						enviarMensagem();
					}
				});
		BtnEnviar.setBounds(293, 322, 63, 59);
		contentPane.add(BtnEnviar);

		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(8, 70, 348, 248);
		contentPane.add(scrollPane);

		final UsuariosDAO dao = new UsuariosDAO(true);

		final JLabel lblAmigo = new JLabel("Amigo");
		if (dao.verificarAmizade(FormLogin.getUsuarioLogin(), usuario)) {
			lblAmigo.setIcon(new ImageIcon(FormChat.class
					.getResource("/openlync/imagens/amigo-.png")));
		} else {
			lblAmigo.setIcon(new ImageIcon(FormChat.class
					.getResource("/openlync/imagens/amigo+.png")));
		}
		lblAmigo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // Adicionar ou remover
														// amigos
				if (dao.verificarAmizade(FormLogin.getUsuarioLogin(), usuario)) {
					if (dao.removerAmizade(FormLogin.getUsuarioLogin(), usuario)) {
						lblAmigo.setIcon(new ImageIcon(FormChat.class
								.getResource("/openlync/imagens/amigo+.png")));
					}
				} else {
					if (dao.adicionarAmizade(FormLogin.getUsuarioLogin(),
							usuario)) {
						lblAmigo.setIcon(new ImageIcon(FormChat.class
								.getResource("/openlync/imagens/amigo-.png")));
					}
				}
			}
		});
		lblAmigo.setBounds(335, 47, 22, 22);
		contentPane.add(lblAmigo);

		final JLabel lblHistorico = new JLabel("Historico");
		lblHistorico.setIcon(new ImageIcon(FormChat.class
				.getResource("/openlync/imagens/historico_icon.png")));
		lblHistorico.setText("");
		lblHistorico.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				carregarHistoricoMensagens();
			}
		});
		lblHistorico.setBounds(310, 45, 22, 22);
		contentPane.add(lblHistorico);

		// Timer para verificar se contato continua online
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				verificarStatusContato();
			}
		}, 1000, 4000);

		// Define comportamente conforme modo de construção
		if (modo == 1) {
			carregarMensagensNaoLidas();
		} else if (modo == 2) {
			carregarHistoricoMensagens();
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) { // Ao fechar janela
				// Remove chat da listaChat
				Contatos contatos = new Contatos();
				contatos.removerFormChat(usuario);

				// Encerra timer e thread de saida de dados
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
	 * Verifica status atual do usuario do chat no DB
	 */
	private void verificarStatusContato() {

		String ipAtual = "";
		try {
			Statement st = MySQLConection.getStatementMySQL();

			//fc_getStatusUsuario
			String SQL = "SELECT fc_getIpUsuario("+usuario.getCodigo()+")";

			ResultSet rs = st.executeQuery(SQL);

			if (rs.next()) {
				ipAtual = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Se o usuario ficou offline
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
	 * 
	 * @param mensagem
	 */
	public void adicionarMensagem(String mensagem) {

		String linhas = "";
		if (!textPane.getText().equals("")) {

			linhas = textPane.getText();
		}

		linhas = linhas + "\n   " + mensagem + "\n";
		textPane.setText(linhas);

		// Manda scroll para o final
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}

	/**
	 * Adiciona mensagem ao textPanel
	 * 
	 * @param mensagem
	 * @param fonte
	 *            : local de origem. "local" = veio do proprio usuario / "out" =
	 *            veio do contato
	 */
	public void adicionarMensagem(String mensagem, String fonte) {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String linhas = "";
		if (fonte.equals("out")) {
			if (textPane.getText().equals("")) {
				linhas = usuario.getNome() + "  " + sdf.format(date);
			} else {
				linhas = textPane.getText() + "\n" + usuario.getNome() + "  "
						+ sdf.format(date);
			}
		} else if (fonte.equals("local")) {
			Usuarios userLogin = FormLogin.getUsuarioLogin();
			if (textPane.getText().equals("")) {
				linhas = userLogin.getNome() + "  " + sdf.format(date);
			} else {
				linhas = textPane.getText() + "\n" + userLogin.getNome() + "  "
						+ sdf.format(date);
			}
		}

		linhas = linhas + "\n   " + mensagem + "\n";
		textPane.setText(linhas);

		// Manda scroll para o final
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}

	/**
	 * Adiciona mensagem ao textPanel
	 * 
	 * @param mensagem
	 * @param fonte
	 *            : local de origem. "local" = veio do proprio usuario / "out" =
	 *            veio do contato
	 * @param dataHora
	 */
	public void adicionarMensagem(String mensagem, String fonte, Date dataHora) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String linhas = "";
		if (fonte.equals("out")) {
			if (textPane.getText().equals("")) {
				linhas = usuario.getNome() + "  " + sdf.format(dataHora);
			} else {
				linhas = textPane.getText() + "\n" + usuario.getNome() + "  "
						+ sdf.format(dataHora);
			}
		} else if (fonte.equals("local")) {
			Usuarios userLogin = FormLogin.getUsuarioLogin();
			if (textPane.getText().equals("")) {
				linhas = userLogin.getNome() + "  " + sdf.format(dataHora);
			} else {
				linhas = textPane.getText() + "\n" + userLogin.getNome() + "  "
						+ sdf.format(dataHora);
			}
		}

		linhas = linhas + "\n   " + mensagem + "\n";
		textPane.setText(linhas);

		// Manda scroll para o final
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}

	/**
	 * Envia a mensagem. Evento usado pelo btnEnviar e Enter
	 */
	private void enviarMensagem() {

		// Se campo não estiver vazio
		if (!textArea.getText().equals("")) {
			adicionarMensagem(textArea.getText(), "local");

			// Se usuario estiver online -> manda mensagem via Socket
			if (usuario.getStatus()) {
				conexaoSaida.enviarMensagem(textArea.getText());
			}

			/*
			 * --- Adiciona mensagem ao Historico no Banco de Dados
			 * (tb_mensagens) ---
			 */
			Mensagens mensagens = new Mensagens();

			mensagens.adicionarMensagemDB(textArea.getText(), FormLogin
					.getUsuarioLogin().getCodigo(), usuario.getCodigo(),
					new Date());

			// Limpa campos
			textArea.setText(null);
			textArea.grabFocus();
		}
	}

	/**
	 * Carrega todas mensagens não lidas enviadas pelo usuario
	 */
	private void carregarMensagensNaoLidas() {

		//sp_getMensagensNaoLidas
		Criptografia cript = new Criptografia();

		try {
			Statement st = MySQLConection.getStatementMySQL();
			
			String SQL = "CALL sp_getMensagensNaoLidas("+FormLogin.getUsuarioLogin().getCodigo()+
						", "+usuario.getCodigo()+");";

			ResultSet rs = st.executeQuery(SQL);

			while (rs.next()) {

				adicionarMensagem(cript.descriptografarMensagem(rs
						.getString("conteudo_mensagem")), "out",
						rs.getDate("data_mensagem"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carrega todas mensagens eenviadas e recebidas pela usuario
	 */
	private void carregarHistoricoMensagens() {

		// Limpa TextPane
		textPane.setText("");

		//sp_gethistoricoMensagens
		Criptografia cript = new Criptografia();

		try {
			Statement st = MySQLConection.getStatementMySQL();
			
			String SQL = "CALL sp_getHistoricoMensagens("+FormLogin.getUsuarioLogin().getCodigo()+
						", "+usuario.getCodigo()+")";

			ResultSet rs = st.executeQuery(SQL);

			while (rs.next()) {

				// Se mensagem veio do contato
				if (rs.getInt("codigo_remet_mensagem") == usuario.getCodigo()) {
					adicionarMensagem(cript.descriptografarMensagem(rs
							.getString("conteudo_mensagem")), "out",
							rs.getTimestamp("data_mensagem"));
					// Se veio do usuario
				} else {
					adicionarMensagem(cript.descriptografarMensagem(rs
							.getString("conteudo_mensagem")), "local",
							rs.getTimestamp("data_mensagem"));
				}
			}

			adicionarMensagem("  --- Fim do Historico ---");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
