
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import Biblioteca.MDIDesktopPane;

public class FormIncial extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	private static JLabel labelNome;
	private static JLabel labelCargo;
	public static MDIDesktopPane jdpUsuarios;
	private JLabel labelFoto;
	
	public FormIncial(String nome, String cargo, BufferedImage foto) {
		getContentPane().setBackground(new Color(238, 238, 238));
		
		setBorder(null);
		setBounds(100, 100, 370, 570);
		getContentPane().setLayout(null);
		
		labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 17));
		labelNome.setBounds(96, 18, 247, 15);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(96, 48, 247, 15);
		getContentPane().add(labelCargo);
		
		labelFoto = new JLabel("");
		labelFoto.setBackground(new Color(255, 255, 255));
		labelFoto.setIcon(new ImageIcon(foto));
		labelFoto.setBounds(22, 12, 57, 57);
		getContentPane().add(labelFoto);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 81, 324, 420);
		getContentPane().add(scrollPane);
		
		jdpUsuarios = new MDIDesktopPane();
		jdpUsuarios.setBorder(null);
		jdpUsuarios.setBackground(UIManager.getColor("Button.background"));
		scrollPane.setViewportView(jdpUsuarios);
		
		//Timer para atualizar lista de contatos online
		Timer t = new Timer();
		t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
					Contatos.atualizarListaPrincipal();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        }, 1000, 7000);
	}
}
