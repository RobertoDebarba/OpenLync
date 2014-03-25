import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;


public class FormIncial extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JDesktopPane jdpUsuarios;
	private static int contadorUsuarios = 0;
	
	private static JLabel labelNome;
	private static JLabel labelCargo;
	private static JPanel panelFoto;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormIncial frame = new FormIncial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void carregarInformacoes(String nome, String cargo) { //FIXME foto
		labelNome.setText(nome);
		labelCargo.setText(cargo);
	}
	
	public static FormUsuarioLista getNovoUsuario() {
		FormUsuarioLista frmUsuario = new FormUsuarioLista();
		
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
		frmUsuario.setLocation(0, contadorUsuarios);
		
		jdpUsuarios.add(frmUsuario);
		frmUsuario.setVisible(true);
		
		contadorUsuarios = contadorUsuarios + 60; //tamanho do frmUsuarioLista
		
		return frmUsuario;
		}
	
	
	public FormIncial() {
		setBorder(null);
		setBounds(100, 100, 370, 570);
		
		jdpUsuarios = new JDesktopPane();
		jdpUsuarios.setBounds(22, 85, 321, 414);
		jdpUsuarios.setBorder(null);
		
		JButton btnNovoUsuario = new JButton("Novo Usuario");
		btnNovoUsuario.setBounds(230, 511, 128, 25);
		btnNovoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//FIXME remover
				//FormIncial.getNovoUsuario();
				
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(jdpUsuarios);
		getContentPane().add(btnNovoUsuario);
		
		panelFoto = new JPanel();
		panelFoto.setBackground(Color.GREEN);
		panelFoto.setBounds(22, 12, 61, 61);
		getContentPane().add(panelFoto);
		
		labelNome = new JLabel("Roberto Luiz Debarba");
		labelNome.setFont(new Font("Dialog", Font.BOLD, 17));
		labelNome.setBounds(96, 18, 247, 15);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel("Programador de Sistemas");
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(96, 48, 247, 15);
		getContentPane().add(labelCargo);

	}
	
}
