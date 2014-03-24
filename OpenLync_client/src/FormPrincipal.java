import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

public class FormPrincipal {

	protected Shell shell;
	private Text EditUsuario;
	private Text EditSenha;

	/**
	 * Launch the application.
	 * @param args
	 */
	public void abrirTelaPrincipal() {
		try {
			FormPrincipal window = new FormPrincipal();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(330, 543);
		shell.setText("OpenLync");
		
		final Composite compoTeste = new Composite(shell, SWT.NONE);
		compoTeste.setBounds(0, 0, 330, 533);
		
		Button BtnEntrar = new Button(compoTeste, SWT.NONE);
		BtnEntrar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Connection conexao = MySQLConection.getMySQLConnection();
				Usuarios usuario = new Usuarios();
				try {
					if (usuario.verificarLogin(conexao, EditUsuario.getText(), EditSenha.getText())) {
						System.out.println("entrou");
						compoTeste.setVisible(false);
					} else {
						System.out.println("nao entrou");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		BtnEntrar.setText("Entrar");
		BtnEntrar.setBounds(68, 233, 91, 45);
		
		Button BtnSair = new Button(compoTeste, SWT.NONE);
		BtnSair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.exit(1);
			}
		});
		BtnSair.setText("Sair");
		BtnSair.setBounds(173, 233, 91, 45);
		
		Label label = new Label(compoTeste, SWT.NONE);
		label.setText("Senha:");
		label.setBounds(68, 173, 70, 17);
		
		Label label_1 = new Label(compoTeste, SWT.NONE);
		label_1.setText("Usuário:");
		label_1.setBounds(68, 138, 70, 17);
		
		EditUsuario = new Text(compoTeste, SWT.BORDER);
		EditUsuario.setBounds(160, 130, 104, 27);
		
		EditSenha = new Text(compoTeste, SWT.BORDER);
		EditSenha.setBounds(160, 163, 104, 27);
		
	}
}
