package openlync.forms;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;

import openlync.principal.Mensagem;
import openlync.principal.Usuario;
import openlync.principal.UsuarioDAO;
import openlync.utilidades.MySQLConection;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormMain extends JFrame {

	private static final long serialVersionUID = 1L;

	private static JDesktopPane jdpMain;
	private static FormLogin frmLogin;
	private static FormIncial frmInicial;
	private static FormConfig frmConfig;

	public FormMain() {
		initialize();
	}

	/**
	 * Instancia, seta visivel e adiciona FormLogin no jdp
	 */
	public static void abrirFrmLogin() {

		frmLogin = new FormLogin();

		jdpMain.add(frmLogin);
		frmLogin.setVisible(true);
	}

	/**
	 * Remove form do jdp
	 */
	public static void fecharFrmLogin() {
		jdpMain.remove(frmLogin);
	}

	/**
	 * Instancia, seta visivel e adiciona FormInicial no jdp
	 */
	public static void abrirFrmInicial(Usuario usuario) {

		frmInicial = new FormIncial(usuario);

		jdpMain.add(frmInicial);
		frmInicial.setVisible(true);
	}

	/**
	 * Remove form do jdp
	 */
	public static void fecharFrmInicial() {
		jdpMain.remove(frmInicial);
	}

	/**
	 * Instancia, seta visivel e adiciona FormCOnfig no jdp
	 */
	public static void abrirFrmConfig() {
		frmConfig = new FormConfig();

		jdpMain.add(frmConfig);
		frmConfig.setVisible(true);
	}

	/**
	 * Remove form do jdp
	 */
	public static void fecharFrmConfig() {
		jdpMain.remove(frmConfig);
		jdpMain.repaint();
	}

	private void initialize() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Ao fechar programa
				Usuario usuarioLogin = FormLogin.getUsuarioLogin();
				// Se o banco estiver conectado
				if ((usuarioLogin != null) && (MySQLConection.getStatusMySQL())) {

					UsuarioDAO dao = new UsuarioDAO(false);
					
					usuarioLogin.setIp("null");
					dao.setIPDB(usuarioLogin);

					// Fecha conexão com banco
					MySQLConection.fecharConexaoMySQL();
					
					// -------------------------------------------------
					// Fecha conexão Socket
					new Mensagem().enviarMensagem("KILL CLIENT", "SYSTEM");
				}
			}
		});
		setTitle("OpenLync");
		setBounds(100, 100, 370, 570);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jdpMain = new JDesktopPane();
		getContentPane().add(jdpMain, BorderLayout.CENTER);

		setResizable(false);
	}
}
