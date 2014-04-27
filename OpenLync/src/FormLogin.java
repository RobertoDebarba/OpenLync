import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

/*
 * FormLogin.java
 *
 * Created on __DATE__, __TIME__
 */

/**
 *
 * @author  __USER__
 */
public class FormLogin extends javax.swing.JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form FormLogin */
	public FormLogin() {
		initComponents();
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		editLogin = new javax.swing.JTextField();
		editSenha = new javax.swing.JPasswordField();
		labelConfig = new javax.swing.JLabel();

		jPanel1.setLayout(null);

		jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 36));
		jLabel1.setText("OpenLync");
		jPanel1.add(jLabel1);
		jLabel1.setBounds(130, 120, 170, 42);

		jLabel2.setFont(new java.awt.Font("Ubuntu", 2, 24));
		jLabel2.setText("ServerManagement");
		jPanel1.add(jLabel2);
		jLabel2.setBounds(190, 160, 204, 29);

		jLabel3.setText("Login:");
		jPanel1.add(jLabel3);
		jLabel3.setBounds(150, 245, 44, 15);

		jLabel4.setText("Senha:");
		jPanel1.add(jLabel4);
		jLabel4.setBounds(150, 275, 50, 15);

		editLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editLoginActionPerformed(evt);
			}
		});
		editLogin.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				editLoginKeyPressed(evt);
			}
		});
		jPanel1.add(editLogin);
		editLogin.setBounds(210, 240, 150, 25);

		editSenha.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editSenhaActionPerformed(evt);
			}
		});
		editSenha.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				editSenhaKeyPressed(evt);
			}
		});
		jPanel1.add(editSenha);
		editSenha.setBounds(210, 270, 150, 25);

		labelConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/Imagens/gear_icon.png"))); // NOI18N
		labelConfig.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				labelConfigMouseClicked(evt);
			}
		});
		jPanel1.add(labelConfig);
		labelConfig.setBounds(480, 10, 20, 20);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 506,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 481,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void labelConfigMouseClicked(java.awt.event.MouseEvent evt) { //Botão Config
		MySQLConection.setIpServidor(JOptionPane.showInputDialog(null, "IP do Servidor", "Configurações", 1));
	}

	private void editSenhaKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			Usuarios usuario = new Usuarios();
			if (usuario.verifLogin(editLogin.getText(),
					new String(editSenha.getPassword()))) {
				FormMain.frmLogin.setVisible(false);
				editLogin.setText("");
				editSenha.setText("");
			} else {
				JOptionPane.showMessageDialog(null,
						"Usuário ou Senha incorretos!", "Erro de Login", 1);
			}
		}
	}

	private void editLoginKeyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			Usuarios usuario = new Usuarios();
			if (usuario.verifLogin(editLogin.getText(),
					new String(editSenha.getPassword()))) {
				FormMain.frmLogin.setVisible(false);
				editLogin.setText("");
				editSenha.setText("");
			} else {
				JOptionPane.showMessageDialog(null,
						"Usuário ou Senha incorretos!", "Erro de Login", 1);
			}
		}
	}

	private void editSenhaActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void editLoginActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JTextField editLogin;
	private javax.swing.JPasswordField editSenha;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel labelConfig;
	// End of variables declaration//GEN-END:variables

}