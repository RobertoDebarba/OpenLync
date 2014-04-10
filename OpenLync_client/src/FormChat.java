
import javax.swing.JFrame;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FormChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	
	public static FormChat[] listaChat = new FormChat[100]; //FIXME tornar tamanho dinamico
	private static int contadorChat = 0;
	
	private int codigo;
	private String nome;
	private String ip;	
	
	public static int getContadorChat() {
		return contadorChat;
	}
	
	public static void incContadorChat() {
		contadorChat++;
	}
	
	public static void decContadorChat() {
		contadorChat--;
	}
	
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
	}

	public FormChat(final int codigo, String nome, String cargo, String ip, BufferedImage foto) {
		
		setTitle(nome);
		this.codigo = codigo;
		this.nome = nome;
		this.ip = ip;
		
		//Abre Conexão de Saida
		final SaidaDados conexaoSaida = new SaidaDados(this.ip);
		new Thread(conexaoSaida).start();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 366, 418);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
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
		
		JLabel labelAnexo = new JLabel("");
		labelAnexo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {		// Botão anexo
				conexaoSaida.enviarArquivo();
			}
		});
		labelAnexo.setIcon(new ImageIcon(FormChat.class.getResource("/Imagens/anexo_icon.png")));
		labelAnexo.setBounds(326, 43, 37, 28);
		contentPane.add(labelAnexo);
	
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {		// Ao fechar janela
				//Ao fechar
				int a = 0;
				while (a < 100) { //FIXME
					
					if (listaChat[a] != null) {
						if (listaChat[a].getCodigo() == codigo) {
							listaChat[a] = null;
							contadorChat--;
						}
					}	
					a++;
				}
			}
			@Override
			public void windowActivated(WindowEvent e) {
				textArea.grabFocus();
			}
		});
	}
}
