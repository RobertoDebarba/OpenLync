
import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;


public class FormIncial extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	public static JDesktopPane jdpUsuarios;
	private static int contadorUsuarios = 0;
	
	private static JLabel labelNome;
	private static JLabel labelCargo;
	private static JPanel panelFoto;
	
	
	public static void setNovoUsuarioLista(int codigo,String nome, String cargo, String ip) { //FIXME foto
		FormUsuarioLista frmUsuario = new FormUsuarioLista(codigo, nome, cargo, ip);
		
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
		
		contadorUsuarios = contadorUsuarios + 60;
		}
	
	
	public FormIncial(String nome, String cargo) { //FIXME foto
		setBorder(null);
		setBounds(100, 100, 370, 570);
		
		jdpUsuarios = new JDesktopPane();
		jdpUsuarios.setBounds(22, 85, 321, 414);
		jdpUsuarios.setBorder(null);
		getContentPane().setLayout(null);
		getContentPane().add(jdpUsuarios);
		
		panelFoto = new JPanel();
		panelFoto.setBackground(Color.GREEN);
		panelFoto.setBounds(22, 12, 61, 61);
		getContentPane().add(panelFoto);
		
		labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 17));
		labelNome.setBounds(96, 18, 247, 15);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(96, 48, 247, 15);
		getContentPane().add(labelCargo);

	}
	
}
