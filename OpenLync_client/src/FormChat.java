
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FormChat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane textPane;
	
	private int codigo;
	private String ip;
	//TODO foto
	
	public int getCodigo() {
		return codigo;
	}

	public String getIp() {
		return ip;
	}
	
	public void adicionarMensagem(String mensagem) {
		String linhas = textPane.getText() + "\n" + mensagem;
		textPane.setText(linhas);
	}

	public FormChat(int codigo, String nome, String cargo, String ip) {
		setTitle(nome);
		this.codigo = codigo;
		this.ip = ip;
		
		//Abre Conexão de Saida
		final SaidaDados conexaoSaida = new SaidaDados(this.ip);
		new Thread(conexaoSaida).start();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 374, 412);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(8, 60, 348, 248);
		contentPane.add(textPane);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(8, 315, 280, 59);
		contentPane.add(textArea);
		
		JPanel panelFoto = new JPanel();
		panelFoto.setBackground(Color.GREEN);
		panelFoto.setBounds(12, 6, 42, 42);
		contentPane.add(panelFoto);
		
		JLabel labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 14));
		labelNome.setBounds(69, 11, 287, 15);
		contentPane.add(labelNome);
		
		JLabel labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelCargo.setBounds(69, 31, 287, 15);
		contentPane.add(labelCargo);
		
		JButton BtnEnviar = new JButton("Enviar");
		BtnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarMensagem(textArea.getText());
				conexaoSaida.enviarMensagem(textArea.getText());
				textArea.setText(null);
			}
		});
		BtnEnviar.setBounds(293, 315, 63, 59);
		contentPane.add(BtnEnviar);
	
	}
}
