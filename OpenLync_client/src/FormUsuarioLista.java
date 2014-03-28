
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FormUsuarioLista extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JLabel labelNome;
	private JLabel labelCargo;
	private JPanel panelFoto;
	
	private int codigoUsuario;
	
	private static FormChat[] listaChat = new FormChat[100]; //FIXME tornar tamanho dinamico
	private static int contadorChat = 0;
	
	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public static int getContadorChat() {
		return contadorChat;
	}
	
	public static FormChat[] getListaChat() {
		return listaChat;
	}

	public static void setListaChat(FormChat[] listaChat) {
		FormUsuarioLista.listaChat = listaChat;
	}

	public FormUsuarioLista(final int codigo,final String nome,final String cargo,final String ip) {
		
		this.codigoUsuario = codigo;
		
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					//TODO Abre tela de chat
					listaChat[contadorChat] = new FormChat(codigo, nome, cargo, ip );
					listaChat[contadorChat].setVisible(true);
					contadorChat++;
				}
			}
		}); //FIXME foto
		setBorder(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBounds(100, 100, 321, 65);
		
		panelFoto = new JPanel();
		panelFoto.setBounds(8, 5, 42, 42);
		panelFoto.setBackground(Color.GREEN);
		
		labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 14));
		labelNome.setBounds(65, 10, 174, 15);
		getContentPane().setLayout(null);
		getContentPane().add(panelFoto);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelCargo.setBounds(65, 30, 174, 15);
		getContentPane().add(labelCargo);
	}
}
