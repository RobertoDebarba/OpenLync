
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;


public class FormUsuarioLista extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JLabel labelNome;
	private JLabel labelCargo;
	private JPanel panelFoto;
	
	private int codigoUsuario;
	
	private static int contadorPosicaoPrintUsuario = 0;
	
	public static void incContadorPosicaoUsuario() {
		contadorPosicaoPrintUsuario = contadorPosicaoPrintUsuario + 60;
	}
	
	public static void decContadorPosicaoUsuario() {
		contadorPosicaoPrintUsuario = contadorPosicaoPrintUsuario - 60;
	}
	
	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	
	public static FormUsuarioLista getNovoFormUsuarioLista(int codigoUsuario) { //FIXME foto
		Usuarios usuario = new Usuarios();
		try {
			usuario.carregarInformacoes(codigoUsuario);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		FormUsuarioLista frmUsuario = new FormUsuarioLista(usuario.getCodigo(), usuario.getNome(), usuario.getCargo(), usuario.getIp());
		
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(frmUsuario);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)frmUsuario.getUI()).setNorthPane(null); //retirar o painel superior  
 		frmUsuario.setBorder(null);//retirar bordas  
		
		// Seta centro
		frmUsuario.setLocation(0, contadorPosicaoPrintUsuario);
		
		incContadorPosicaoUsuario();
		
		return frmUsuario;
	
	}

	public FormUsuarioLista(final int codigo,final String nome,final String cargo,final String ip) {
		
		this.codigoUsuario = codigo;
		
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// Abre tela do chat
					FormChat.listaChat[FormChat.getContadorChat()] = new FormChat(codigo, nome, cargo, ip );
					FormChat.listaChat[FormChat.getContadorChat()].setVisible(true);
					FormChat.incContadorChat();
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
