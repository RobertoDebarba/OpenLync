package openlync.forms;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import openlync.principal.Configuracao;
import openlync.principal.OpenLync;
import openlync.principal.UsuarioDAO;
import openlync.utilidades.MySQLConection;

public class FormInicial extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	public static FormCargo frmCargos = new FormCargo();
	public static FormUsuario frmUsuarios = new FormUsuario();

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
		if (MySQLConection.getStatusMySQL()) {
			adicionarLog("Status do Banco de Dados: ONLINE!");
			setLblStatusDB(true);
			setLblIP(Configuracao.getIpServidorDB());
			;
		} else {
			adicionarLog("Status do Banco de Dados: OFFLINE!");
			setLblStatusDB(false);
			setLblIP(Configuracao.getIpServidorDB());
		}
	}

	private void setLblIP(String ip) {
		lblIP.setText(Configuracao.getIpServidorDB());
	}

	private void setLblPorta(String porta) {
		lblPorta.setText(Configuracao.getPortaSocket() + "");
	}

	private void setLblStatusDB(boolean status) {
		lblStatusDB.setFont(new Font("Arial", Font.BOLD, 15));
		if (status) {
			lblStatusDB.setText("ON");
			lblStatusDB.setForeground(Color.GREEN);
		} else {
			lblStatusDB.setText("OFF");
			lblStatusDB.setForeground(Color.RED);
		}
	}

	private void setLblStatusMensg(boolean status) {
		lblStatusMensg.setFont(new Font("Arial", Font.BOLD, 15));
		if (status) {
			lblStatusMensg.setText("ON");
			lblStatusMensg.setForeground(Color.GREEN);
		} else {
			lblStatusMensg.setText("OFF");
			lblStatusMensg.setForeground(Color.RED);
		}
	}

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

		this.setBounds(0, 0, 515, 540);

		//editIP.setText(MySQLConection.getIpServidor());

		verificarStatusDB();
		if (OpenLync.iniciarServidor()) {
			adicionarLog("Servidor iniciado na porta "
					+ Configuracao.getPortaSocket() + "!");
			setLblStatusMensg(true);
			setLblPorta(Configuracao.getPortaSocket() + "");
		} else {
			adicionarLog("Erro ao criar servidor na porta "
					+ Configuracao.getPortaSocket());
			setLblStatusMensg(false);
			setLblPorta("NULL");
		}

	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel2 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		textPane = new javax.swing.JTextPane();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		lblIP = new javax.swing.JLabel();
		lblStatusDB = new javax.swing.JLabel();
		lblPorta = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		lblStatusMensg = new javax.swing.JLabel();
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
								javax.swing.GroupLayout.DEFAULT_SIZE, 371,
								Short.MAX_VALUE).addContainerGap()));

		jPanel1.setLayout(null);

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/openlync/imagens/OpenLync_logo_menor.png"))); // NOI18N
		jPanel1.add(jLabel1);
		jLabel1.setBounds(10, 10, 80, 70);

		jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 18));
		jLabel2.setText("OpenLync");
		jPanel1.add(jLabel2);
		jLabel2.setBounds(100, 28, 160, 21);

		jLabel3.setText("Server Management");
		jPanel1.add(jLabel3);
		jLabel3.setBounds(100, 50, 160, 18);

		jLabel4.setText("IP do DB:");
		jPanel1.add(jLabel4);
		jLabel4.setBounds(270, 10, 120, 18);

		jLabel5.setText("Status do BD:");
		jPanel1.add(jLabel5);
		jLabel5.setBounds(270, 50, 120, 18);

		jLabel6.setText("Porta Socket:");
		jPanel1.add(jLabel6);
		jLabel6.setBounds(270, 30, 120, 18);

		lblIP.setText("lblIPDB");
		jPanel1.add(lblIP);
		lblIP.setBounds(391, 10, 100, 18);

		lblStatusDB.setText("lblStatusDB");
		jPanel1.add(lblStatusDB);
		lblStatusDB.setBounds(390, 50, 100, 18);

		lblPorta.setText("lblPorta");
		jPanel1.add(lblPorta);
		lblPorta.setBounds(390, 30, 100, 18);

		jLabel7.setText("Status Mens.:");
		jPanel1.add(jLabel7);
		jLabel7.setBounds(270, 70, 120, 18);

		lblStatusMensg.setText("lblStatusMensg");
		jPanel1.add(lblStatusMensg);
		lblStatusMensg.setBounds(390, 70, 100, 18);

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
				.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						502, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										89, Short.MAX_VALUE)
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
			UsuarioDAO dao = new UsuarioDAO(false);
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
		// Seta tema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frmCargos);

		frmCargos.setVisible(true);
	}

	private void menuItemONOFFActionPerformed(java.awt.event.ActionEvent evt) {
		if (lblStatusMensg.getText().equals("OFF")) {
			if (OpenLync.iniciarServidor()) {
				adicionarLog("Servidor iniciado na porta "
						+ Configuracao.getPortaSocket() + "!");
				setLblStatusMensg(true);
			} else {
				adicionarLog("Erro ao criar servidor na porta "
						+ Configuracao.getPortaSocket());
				setLblStatusMensg(false);
			}
		} else {
			OpenLync.pararServidor();
			setLblStatusMensg(false);
		}
	}

	private void menuItemVerifDBActionPerformed(java.awt.event.ActionEvent evt) {
		verificarStatusDB();
	}

	private void menuServidorActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void menuItemUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
		// Seta tema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(frmUsuarios);

		frmUsuarios.setVisible(true);
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
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblIP;
	private javax.swing.JLabel lblPorta;
	private javax.swing.JLabel lblStatusDB;
	private javax.swing.JLabel lblStatusMensg;
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