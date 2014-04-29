
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.AWTException;
import java.awt.Color;
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
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;


public class FormChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	
	private int codigo;
	private String nome;
	private String ip;	
	
	
	public int getCodigo() {
		return codigo;
	}

	public String getIp() {
		return ip;
	}
	
	public void adicionarMensagem(String mensagem, String remetente) {
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		
		String linhas = "";
		if (remetente == "local") {
			linhas = textPane.getText() + "\n" + this.nome + "  " + dateFormat.format(date);
		} else {
			Usuarios userLogin = FormLogin.getUsuarioLogin();
			linhas = textPane.getText() + "\n" + userLogin.getNome() + "  " + dateFormat.format(date);
		}
		
		linhas = linhas + "\n   " + mensagem + "\n";
		textPane.setText(linhas);
		
		// Manda scroll para o final
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}

	public FormChat(final int codigo, String nome, String cargo, String ip, BufferedImage foto) {
		
		setTitle(nome);
		this.codigo = codigo;
		this.nome = nome;
		this.ip = ip;
		
		//Abre Conexão de Saida
		final SaidaDados conexaoSaida = new SaidaDados(this.ip);
		Thread threadSaida = new Thread(conexaoSaida);
		threadSaida.start();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 366, 418);
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
		
		JLabel labelFoto = new JLabel("");
		labelFoto.setIcon(new ImageIcon(foto));
		labelFoto.setBounds(10, 8, 57, 57);
		contentPane.add(labelFoto);
		
		JLabel labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 16));
		labelNome.setBounds(75, 16, 285, 15);
		contentPane.add(labelNome);
		
		JLabel labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(75, 42, 285, 15);
		contentPane.add(labelCargo);
		
		final JTextArea textArea = new JTextArea();
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
					
					adicionarMensagem(textArea.getText(), "local");
					conexaoSaida.enviarMensagem(textArea.getText());
					textArea.setText(null);
					textArea.grabFocus();
				}
			}
		});
		textArea.setBounds(8, 322, 280, 59);
		contentPane.add(textArea);
		
		JButton BtnEnviar = new JButton("Enviar");		
		BtnEnviar.addActionListener(new ActionListener() {		// Ao clicar botão ENVIAR
			public void actionPerformed(ActionEvent arg0) {
				adicionarMensagem(textArea.getText(), "local");
				conexaoSaida.enviarMensagem(textArea.getText());
				textArea.setText(null);
				textArea.grabFocus();
			}
		});
		BtnEnviar.setBounds(293, 322, 63, 59);
		contentPane.add(BtnEnviar);
		
		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(8, 70, 348, 248);
		contentPane.add(scrollPane);
	
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
			public void windowClosed(WindowEvent e) {		// Ao fechar janela
				//Ao fechar
				int a = 0;
				while (a < 100) { //FIXME
					
					if (Contatos.listaChat[a] != null) {
						if (Contatos.listaChat[a].getCodigo() == codigo) {
							Contatos.listaChat[a] = null;
							Contatos.decContadorChat();
						}
					}	
					a++;
				}
				//Encerra thread de saida de dados
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
	
	/*
	 * Verifica status atual do usuario do chat no DB
	 * 
	 * Se usuário se desconectou fecha a janela de chat //TODO implementar comportamento melhor
	 */
	
	private void verificarStatusContato() {
		
		String statusAtual = "";
		try {
			java.sql.Connection conexao = MySQLConection.getMySQLConnection();
			Statement st = conexao.createStatement();
		
			String SQL = "SELECT ip_usuario FROM tb_usuarios WHERE codigo_usuario = "+ this.codigo +";";
	
			ResultSet rs = st.executeQuery(SQL);

			rs.beforeFirst();
			if (rs.next()) {
				statusAtual = rs.getString("ip_usuario");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Se o usuario ficou offline
		if (statusAtual.equals("null")) {
			
			dispose();
			JOptionPane.showMessageDialog(null, this.nome +" ficou offline!", "Usuário desconectado", 1);
		}
	}
}
