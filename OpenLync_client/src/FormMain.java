
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

	/**
	 * Launch the application.
	 */
	public void abrirTelaPrincipal() {
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MainFrame = new JFrame();
		MainFrame.setTitle("OpenLync");
		MainFrame.setBounds(100, 100, 370, 570);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane jdpMain = new JDesktopPane();
		MainFrame.getContentPane().add(jdpMain, BorderLayout.CENTER);
		
		// Retira bordas
		FormLogin frmLogin = new FormLogin();
		((BasicInternalFrameUI)frmLogin.getUI()).setNorthPane(null); //retirar o painel superior  
		frmLogin.setBorder(null);//retirar bordas  
		
		// Seta tema
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(frmLogin);
		
		// Seta centro
		frmLogin.setLocation(0, 0);
		    
		jdpMain.add(frmLogin);
		frmLogin.setVisible(true);
		
		
		
	}
	
        
	

}
