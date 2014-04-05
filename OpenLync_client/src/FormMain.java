
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class FormMain {

	private JFrame MainFrame;
	private static JDesktopPane jdpMain;
	//Forms
	private static FormLogin frmLogin;
	private static FormIncial frmInicial;
	private static FormConfig frmConfig;

	public void abrirTela() {
		try {
			FormMain window = new FormMain();
			window.MainFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FormMain() {
		initialize();
	}

	public static void abrirFrmLogin() {
		
		frmLogin = new FormLogin();
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(frmLogin);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)frmLogin.getUI()).setNorthPane(null); //retirar o painel superior  
 		frmLogin.setBorder(null);//retirar bordas  
		
		// Seta centro
		frmLogin.setLocation(0, 0);
		    
		jdpMain.add(frmLogin);
		frmLogin.setVisible(true);
	}
	
	public static void fecharFrmLogin() {
		jdpMain.remove(frmLogin);
	}
	
	public static void abrirFrmInicial(String nome, String cargo, BufferedImage foto) { //FIXME foto 
		
		frmInicial = new FormIncial(nome, cargo, foto);
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(frmInicial);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)frmInicial.getUI()).setNorthPane(null); //retirar o painel superior  
 		frmInicial.setBorder(null);//retirar bordas 
	    
	    //Seta centro
	    frmInicial.setLocation(0, 0);
	    
	    jdpMain.add(frmInicial);
	    frmInicial.setVisible(true);
		
	}
	
	public static void fecharFrmInicial() {
		jdpMain.remove(frmInicial);
	}
	
	public static void abrirFrmConfig() {
		frmConfig = new FormConfig();
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(frmConfig);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)frmConfig.getUI()).setNorthPane(null); //retirar o painel superior  
 		frmConfig.setBorder(null);//retirar bordas 
	    
	    //Seta centro
	    frmConfig.setLocation(0, 0);
	    
	    jdpMain.add(frmConfig);
	    frmConfig.setVisible(true);
	}
	
	public static void fecharFrmConfig() {
		jdpMain.remove(frmConfig);
		jdpMain.repaint();
	}

	private void initialize() {
		MainFrame = new JFrame();
		MainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Ao fechar programa
				Usuarios usuarioLogin = FormLogin.getUsuarioLogin();
				if (usuarioLogin != null) {
					try {
						//Seta status usuarioLogin para status_usuario = false
						usuarioLogin.setStatusOnDB(usuarioLogin.getCodigo(), false);
						//Seta ip para null
						usuarioLogin.setIpOnDB(usuarioLogin.getCodigo(), null);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		MainFrame.setTitle("OpenLync");
		MainFrame.setBounds(100, 100, 370, 570);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jdpMain = new JDesktopPane();
		MainFrame.getContentPane().add(jdpMain, BorderLayout.CENTER);
		
	}
}
