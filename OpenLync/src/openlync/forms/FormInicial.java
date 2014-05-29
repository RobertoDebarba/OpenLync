package openlync.forms;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import openlync.principal.OpenLync;
import openlync.principal.UsuariosDAO;
import openlync.utilidades.MySQLConection;

public class FormInicial extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	public static FormCargo frmCargos = new FormCargo();
	public static FormUsuario frmUsuarios = new FormUsuario();

	/** Creates new form FormInicial */
	public FormInicial() {
		initComponents();

		// Seta tema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);

		// Retira bordas
		((BasicInternalFrameUI) this.getUI()).setNorthPane(null); // retirar
																	// o
																	// painel
																	// superior
		((BasicInternalFrameUI) this.getUI()).setSouthPane(null); // retirar
																	// o
																	// painel
																	// inferior
		this.setBorder(null);// retirar bordas

		// Seta centro
		setLocation(0, 0);

		editIP.setText(MySQLConection.getIpServidor());

		verificarStatusDB();
		if (OpenLync.iniciarServidor()) {
			adicionarLog("Servidor iniciado na porta "
					+ OpenLync.getPortaEntrada() + "!");
			checkServ.setSelected(true);
		} else {
			adicionarLog("Erro ao criar servidor na porta "
					+ OpenLync.getPortaEntrada());
		}
	}

	/**
	 * Adiciona mensagem a tela de principal de log
	 * 
	 * @param mensagem
	 */
	public void adicionarLog(String mensagem) {

		if (textPane.getText().equals("")) {
			textPane.setText(mensagem);
		} else {
			textPane.setText(textPane.getText() + "\n" + mensagem);
		}

		// Manda scroll para o final
		textPane.setCaretPosition(textPane.getDocument().getLength());
		textPane.repaint();
		jScrollPane1.repaint();
	}

	/**
	 * Verifica o status do banco de dados
	 */
	public void verificarStatusDB() {
		if (!editIP.getText().equals("")) {

			MySQLConection.setIpServidor(editIP.getText());

			if (MySQLConection.getStatusMySQL()) {
				checkDB.setSelected(true);
				adicionarLog("Status do Banco de Dados: ONLINE!");
			} else {
				checkDB.setSelected(false);
				adicionarLog("Status do Banco de Dados: OFFLINE!");
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"O campo 'IP' deve estar preenchido corretamente!",
					"Aviso", 1);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		checkServ = new javax.swing.JCheckBox();
		checkDB = new javax.swing.JCheckBox();
		jLabel4 = new javax.swing.JLabel();
		editIP = new javax.swing.JTextField();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		textPane = new javax.swing.JTextPane();
		jMenuBar1 = new javax.swing.JMenuBar();
		menuServidor = new javax.swing.JMenu();
		menuItemONOFF = new javax.swing.JMenuItem();
		menuItemVerifDB = new javax.swing.JMenuItem();
		menuItemRestaurarIp = new javax.swing.JMenuItem();
		menuGerencia = new javax.swing.JMenu();
		menuItemUsuarios = new javax.swing.JMenuItem();
		menuItemCargos = new javax.swing.JMenuItem();
		menuBloaquear = new javax.swing.JMenu();
		menuSobre = new javax.swing.JMenu();
		menuSair = new javax.swing.JMenu();

		jPanel1.setLayout(null);
		jPanel1.add(jLabel1);
		jLabel1.setBounds(10, 10, 80, 0);

		jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 18));
		jLabel2.setText("OpenLync");
		jPanel1.add(jLabel2);
		jLabel2.setBounds(100, 28, 90, 21);

		jLabel3.setText("Server Management");
		jPanel1.add(jLabel3);
		jLabel3.setBounds(100, 50, 150, 18);

		checkServ.setEnabled(false);
		checkServ.setFocusable(false);
		checkServ.setText("Servidor de Mensagens");
		jPanel1.add(checkServ);
		checkServ.setBounds(300, 40, 190, 26);

		checkDB.setEnabled(false);
		checkDB.setFocusable(false);
		checkDB.setText("Banco de Dados");
		jPanel1.add(checkDB);
		checkDB.setBounds(300, 70, 140, 26);

		jLabel4.setText("IP:");
		jPanel1.add(jLabel4);
		jLabel4.setBounds(305, 15, 16, 18);

		editIP.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editIPActionPerformed(evt);
			}
		});
		jPanel1.add(editIP);
		editIP.setBounds(330, 10, 160, 25);

		textPane.setEditable(false);
		jScrollPane1.setViewportView(textPane);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 478,
								Short.MAX_VALUE).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanel2Layout
						.createSequentialGroup()
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 365,
								Short.MAX_VALUE).addContainerGap()));

		menuServidor.setText("Servidor");
		menuServidor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuServidorActionPerformed(evt);
			}
		});

		menuItemONOFF.setText("Ativar/Desativar Servidor de Mensagens");
		menuItemONOFF.setToolTipText("");
		menuItemONOFF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuItemONOFFActionPerformed(evt);
			}
		});
		menuServidor.add(menuItemONOFF);

		menuItemVerifDB.setText("Verificar Status do Banco de Dados");
		menuItemVerifDB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuItemVerifDBActionPerformed(evt);
			}
		});
		menuServidor.add(menuItemVerifDB);

		menuItemRestaurarIp.setText("Restaurar Status dos Usu\u00e1rios");
		menuItemRestaurarIp
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						menuItemRestaurarIpActionPerformed(evt);
					}
				});

		menuServidor.add(menuItemRestaurarIp);

		jMenuBar1.add(menuServidor);

		menuGerencia.setText("Gerenciar");

		menuItemUsuarios.setText("Usu\u00e1rios");
		menuItemUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				menuItemUsuariosMouseClicked(evt);
			}
		});
		menuItemUsuarios.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuItemUsuariosActionPerformed(evt);
			}
		});
		menuGerencia.add(menuItemUsuarios);

		menuItemCargos.setText("Cargos");
		menuItemCargos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuItemCargosActionPerformed(evt);
			}
		});
		menuGerencia.add(menuItemCargos);

		jMenuBar1.add(menuGerencia);

		menuBloaquear.setText("Bloquear");
		menuBloaquear.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				menuBloaquearMouseClicked(evt);
			}
		});
		jMenuBar1.add(menuBloaquear);

		menuSobre.setText("Sobre");
		menuSobre.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				menuSobreMouseClicked(evt);
			}
		});
		jMenuBar1.add(menuSobre);

		menuSair.setText("Sair");
		menuSair.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				menuSairMouseClicked(evt);
			}
		});
		menuSair.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuSairActionPerformed(evt);
			}
		});
		jMenuBar1.add(menuSair);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						502, Short.MAX_VALUE)
				.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										95, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void menuItemRestaurarIpActionPerformed(
			java.awt.event.ActionEvent evt) {
		if (JOptionPane.showConfirmDialog(null,
				"Restaurar o Status de todos os Usuários para Offline?",
				"Confirmação", 0, 2) == 0) {
			UsuariosDAO dao = new UsuariosDAO(false);
			dao.restaurarStatusUsuarios();
		}
	}

	private void menuSobreMouseClicked(java.awt.event.MouseEvent evt) {
		FormSobre frmSobre = new FormSobre();

		// Seta tema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frmSobre);

		frmSobre.setVisible(true);
	}

	private void menuItemCargosActionPerformed(java.awt.event.ActionEvent evt) {
		if (!editIP.getText().equals("")) {

			MySQLConection.setIpServidor(editIP.getText());

			// Seta tema
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(frmCargos);

			frmCargos.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,
					"O campo 'IP' deve estar preenchido corretamente!",
					"Aviso", 1);
		}
	}

	private void menuItemONOFFActionPerformed(java.awt.event.ActionEvent evt) {
		if (!checkServ.isSelected()) {

			if (OpenLync.iniciarServidor()) {
				adicionarLog("Servidor iniciado na porta "
						+ OpenLync.getPortaEntrada() + "!");
			} else {
				adicionarLog("Erro ao criar servidor na porta "
						+ OpenLync.getPortaEntrada());
			}

			checkServ.setSelected(true);
		} else {
			OpenLync.pararServidor();
			checkServ.setSelected(false);
		}
	}

	private void menuItemVerifDBActionPerformed(java.awt.event.ActionEvent evt) {
		verificarStatusDB();
	}

	private void menuServidorActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void editIPActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void menuItemUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
		if (!editIP.getText().equals("")) {

			MySQLConection.setIpServidor(editIP.getText());

			// Seta tema
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			SwingUtilities.updateComponentTreeUI(frmUsuarios);

			frmUsuarios.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,
					"O campo 'IP' deve estar preenchido corretamente!",
					"Aviso", 1);
		}
	}

	private void menuItemUsuariosMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void menuBloaquearMouseClicked(java.awt.event.MouseEvent evt) {
		OpenLync.frmMain.frmLogin.setVisible(true);
	}

	private void menuSairMouseClicked(java.awt.event.MouseEvent evt) {
		System.exit(1);
	}

	private void menuSairActionPerformed(java.awt.event.ActionEvent evt) {
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JCheckBox checkDB;
	private javax.swing.JCheckBox checkServ;
	private javax.swing.JTextField editIP;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JMenu menuBloaquear;
	private javax.swing.JMenu menuGerencia;
	private javax.swing.JMenuItem menuItemCargos;
	private javax.swing.JMenuItem menuItemONOFF;
	private javax.swing.JMenuItem menuItemRestaurarIp;
	private javax.swing.JMenuItem menuItemUsuarios;
	private javax.swing.JMenuItem menuItemVerifDB;
	private javax.swing.JMenu menuSair;
	private javax.swing.JMenu menuServidor;
	private javax.swing.JMenu menuSobre;
	private javax.swing.JTextPane textPane;
	// End of variables declaration//GEN-END:variables

}