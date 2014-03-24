import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
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
		MainFrame.setBounds(100, 100, 371, 563);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane jdpMain = new JDesktopPane();
		MainFrame.getContentPane().add(jdpMain, BorderLayout.CENTER);
		
		FormLogin frmLogin = new FormLogin();
		jdpMain.add(frmLogin);
		frmLogin.setVisible(true);
	}
	

}
