package openlync.forms;
import FormInicial;
import FormLogin;

public class FormMain extends javax.swing.JFrame {

	public FormInicial frmInicial;
	public FormLogin frmLogin;

	private static final long serialVersionUID = 1L;

	/** Creates new form FormMain */
	public FormMain() {
		initComponents();

		setLocationRelativeTo(null);
		setResizable(false);

		abrirFrmLogin();
	}

	/**
	 * Abre formInicial e inicia a variavel
	 */
	public void abrirFrmInicial() {

		frmInicial = new FormInicial();

		desktopPane.add(frmInicial);
		frmInicial.setVisible(true);
	}

	/**
	 * Abre formLogin e inicia a variavel
	 */
	private void abrirFrmLogin() {

		frmLogin = new FormLogin();

		desktopPane.add(frmLogin);
		frmLogin.setVisible(true);
	}

	// GEN-BEGIN:initComponents
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
				desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 513,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 542,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
		// GEN-END:initComponents

	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JDesktopPane desktopPane;
	// End of variables declaration//GEN-END:variables

}