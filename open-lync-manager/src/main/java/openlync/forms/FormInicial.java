package openlync.forms;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import openlync.principal.Configuracao;
import openlync.principal.UsuarioDAO;

public class FormInicial extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;

	public static FormCargo frmCargos = new FormCargo();
	public static FormUsuario frmUsuarios = new FormUsuario();

	private void setLblIP(String ip) {
		lblIP.setText(Configuracao.getIpServidorDB());
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

	private void setLblNumUsuariosOnline() {
		UsuarioDAO dao = new UsuarioDAO(false);
		lblNumUsuariosON.setText(dao.getNumUsuariosON() + "");
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

		setLblIP(Configuracao.getIpServidorDB());
		setLblStatusDB(true);

		/*
		 * Timer para atualizar o numero de usuarios online
		 */
		Timer t1 = new Timer();
		t1.schedule(new TimerTask() {
			@Override
			public void run() {
				setLblNumUsuariosOnline();
			}
		}, 1000, 5000);
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		lblIP = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		lblNumUsuariosON = new javax.swing.JLabel();
		btnCargos = new javax.swing.JButton();
		btnUsuarios = new javax.swing.JButton();
		lblStatusDB = new javax.swing.JLabel();
		jMenuBar1 = new javax.swing.JMenuBar();
		menuGerencia = new javax.swing.JMenu();
		menuItemUsuarios = new javax.swing.JMenuItem();
		menuItemCargos = new javax.swing.JMenuItem();
		menuItemRestaurarIp = new javax.swing.JMenuItem();
		menuSobre = new javax.swing.JMenu();
		menuSair = new javax.swing.JMenu();

		jPanel1.setLayout(null);

		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "OpenLync_logo_menor.png"))); // NOI18N
		jPanel1.add(jLabel1);
		jLabel1.setBounds(140, 10, 80, 70);

		jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 18));
		jLabel2.setText("OpenLync");
		jPanel1.add(jLabel2);
		jLabel2.setBounds(230, 30, 160, 21);

		jLabel3.setText("Server Management");
		jPanel1.add(jLabel3);
		jLabel3.setBounds(230, 50, 160, 18);

		jLabel4.setText("IP do Banco de Dados:");
		jPanel1.add(jLabel4);
		jLabel4.setBounds(20, 100, 220, 18);

		jLabel5.setText("N\u00famero de Usu\u00e1rios online:");
		jPanel1.add(jLabel5);
		jLabel5.setBounds(20, 140, 220, 18);

		lblIP.setText("lblIPDB");
		jPanel1.add(lblIP);
		lblIP.setBounds(240, 100, 230, 18);

		jLabel6.setText("Status do Banco de Dados:");
		jPanel1.add(jLabel6);
		jLabel6.setBounds(20, 120, 220, 18);

		lblNumUsuariosON.setText("lblNumUsuariosON");
		jPanel1.add(lblNumUsuariosON);
		lblNumUsuariosON.setBounds(240, 140, 230, 18);

		btnCargos.setText("Gerenciamento de Cargos");
		btnCargos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCargosActionPerformed(evt);
			}
		});
		jPanel1.add(btnCargos);
		btnCargos.setBounds(120, 180, 280, 50);

		btnUsuarios.setText("Gerenciamento de Usu\u00e1rios");
		btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnUsuariosActionPerformed(evt);
			}
		});
		jPanel1.add(btnUsuarios);
		btnUsuarios.setBounds(120, 240, 280, 50);

		lblStatusDB.setText("lblStatusDB");
		jPanel1.add(lblStatusDB);
		lblStatusDB.setBounds(240, 120, 230, 18);

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

		menuItemRestaurarIp.setText("Restaurar Status dos Usu\u00e1rios");
		menuItemRestaurarIp
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						menuItemRestaurarIpActionPerformed(evt);
					}
				});
		menuGerencia.add(menuItemRestaurarIp);

		jMenuBar1.add(menuGerencia);

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
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 478,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 311,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
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

	private void btnCargosActionPerformed(java.awt.event.ActionEvent evt) {
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

	private void menuSairMouseClicked(java.awt.event.MouseEvent evt) {
		System.exit(1);
	}

	private void menuSairActionPerformed(java.awt.event.ActionEvent evt) {
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btnCargos;
	private javax.swing.JButton btnUsuarios;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel lblIP;
	private javax.swing.JLabel lblNumUsuariosON;
	private javax.swing.JLabel lblStatusDB;
	private javax.swing.JMenu menuGerencia;
	private javax.swing.JMenuItem menuItemCargos;
	private javax.swing.JMenuItem menuItemRestaurarIp;
	private javax.swing.JMenuItem menuItemUsuarios;
	private javax.swing.JMenu menuSair;
	private javax.swing.JMenu menuSobre;
	// End of variables declaration//GEN-END:variables

}