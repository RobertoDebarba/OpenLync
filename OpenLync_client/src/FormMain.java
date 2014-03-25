
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.awt.BorderLayout;


public class FormMain {

	private JFrame MainFrame;
	private static JDesktopPane jdpMain;
	
	//Forms
	private static FormLogin frmLogin = new FormLogin();
	private static FormIncial frmInicial = new FormIncial();

	/**
	 * Launch the application.
	 */
	public void abrirTela() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain window = new FormMain();
					window.MainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormMain() {
		initialize();
	}

	public static void abrirFrmLogin() {
		
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
	
	public static void abrirFrmInicial() { 
		
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
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MainFrame = new JFrame();
		MainFrame.setTitle("OpenLync");
		MainFrame.setBounds(100, 100, 370, 570);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jdpMain = new JDesktopPane();
		MainFrame.getContentPane().add(jdpMain, BorderLayout.CENTER);
		
	}
}
