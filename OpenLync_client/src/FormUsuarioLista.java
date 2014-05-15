
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JLabel;

import org.imgscalr.Scalr;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;


public class FormUsuarioLista extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JLabel labelNome;
	private JLabel labelCargo;
	private JLabel labelFoto;
	private JPanel panelStatus;
	
	private Usuarios usuario;
	
	public Usuarios getUsuario() {
		return usuario;
	}
	
	/**
	 * Atualiza Panel de Status do usuario
	 * @param status
	 */
	public void setStatus(boolean status, String ipAtual) {
	
		if (status) {
			panelStatus.setBackground(new Color(0, 200, 0));
			usuario.setIp(ipAtual);
		} else {
			panelStatus.setBackground(new Color(200, 0, 0));
			usuario.setIp("null");
		}
	}

	public FormUsuarioLista(final Usuarios usuario) {
		
		this.usuario = usuario;
		
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
  		this.setBorder(null);//retirar bordas 
  		
		
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// Abre tela do chat
					Contatos contatos = new Contatos();
					contatos.adicionarFormChat(usuario, 0, 0);
				}
			}
		});
		setBorder(null);
		getContentPane().setBackground(new Color(200, 200, 200));
		setBounds(100, 100, 321, 63);
		
		labelNome = new JLabel(this.usuario.getNome());
		labelNome.setBackground(SystemColor.textHighlightText);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 14));
		labelNome.setBounds(70, 10, 174, 15);
		getContentPane().setLayout(null);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel(this.usuario.getCargo());
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelCargo.setBounds(70, 30, 174, 15);
		getContentPane().add(labelCargo);
		
		labelFoto = new JLabel("");
		labelFoto.setBackground(Color.GRAY);
		Image imagemRedim = Scalr.resize(this.usuario.getFoto(), 42, 42);
		labelFoto.setIcon(new ImageIcon(imagemRedim));
		labelFoto.setBounds(15, 5, 42, 42);
		getContentPane().add(labelFoto);
		
		panelStatus = new JPanel();
		panelStatus.setBounds(8, 5, 7, 42);
		panelStatus.setBackground(new Color(0, 200, 0));
		getContentPane().add(panelStatus);
	}
}
