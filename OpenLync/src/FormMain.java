import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/*
 * FormMain.java
 *
 * Created on __DATE__, __TIME__
 */

/**
 *
 * @author  __USER__
 */
public class FormMain extends javax.swing.JFrame {

	public static FormInicial frmInicial = new FormInicial();
	public static FormLogin frmLogin = new FormLogin();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form FormMain */
	public FormMain() {
		initComponents();
		
		setLocationRelativeTo(null);
		setResizable(false);
		
		//abrirFrmInicial();
		abrirFrmInicial();
		abrirFrmLogin();
	}
	
	private void abrirFrmInicial() {
		
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
 		((BasicInternalFrameUI)frmInicial.getUI()).setSouthPane(null); //retirar o painel inferior
  		frmInicial.setBorder(null);//retirar bordas  
		
		// Seta centro
		frmInicial.setLocation(0, 0);
		
		// Mostra
		desktopPane.add(frmInicial);
		frmInicial.setVisible(true);
	}
	
	private void abrirFrmLogin() {
		
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
 		((BasicInternalFrameUI)frmLogin.getUI()).setSouthPane(null); //retirar o painel inferior
  		frmLogin.setBorder(null);//retirar bordas  
		
		// Seta centro
		frmLogin.setLocation(0, 0);
		
		// Mostra
		desktopPane.add(frmLogin);
		frmLogin.setVisible(true);
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		desktopPane = new javax.swing.JDesktopPane();

		setTitle("OpenLync | Server Management");
		setDefaultCloseOperation(3);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 510,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 500,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JDesktopPane desktopPane;
	// End of variables declaration//GEN-END:variables

}