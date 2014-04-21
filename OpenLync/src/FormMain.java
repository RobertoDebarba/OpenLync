
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;


public class FormMain {

	private JFrame frmOpenlyncServer;
	public JTextField editPorta;
	private JLabel labelStatusDB;
	private JLabel labelStatus;
	public JTextField editIPservidor;

	public FormMain() {
		initialize();
	}
	
	public void setStatus(boolean status) {
		if (status) {
			labelStatus.setText("ON");
			labelStatus.setBackground(new Color(200, 0, 0));
		} else {
			labelStatus.setText("OFF");
			labelStatus.setBackground(new Color(0, 0, 200));
		}
	}
	
	public void setStatusDB(boolean status) {
		if (status) {
			labelStatusDB.setText("ON");
			labelStatusDB.setBackground(new Color(200, 0, 0));
		} else {
			labelStatusDB.setText("OFF");
			labelStatusDB.setBackground(new Color(0, 0, 200));//FIXME
		}
	}

	private void initialize() {
		
		frmOpenlyncServer = new JFrame();
		frmOpenlyncServer.setTitle("OpenLync Server");
		frmOpenlyncServer.setBounds(100, 100, 703, 395);
		frmOpenlyncServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOpenlyncServer.getContentPane().setLayout(null);
		
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(frmOpenlyncServer);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setIcon(new ImageIcon(FormMain.class.getResource("/Imagens/OpenLync_logo_menor.png")));
		labelLogo.setBounds(18, 12, 90, 80);
		frmOpenlyncServer.getContentPane().add(labelLogo);
		
		JLabel lblNewLabel = new JLabel("OpenLync");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setBounds(114, 23, 197, 36);
		frmOpenlyncServer.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Configurações");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(114, 67, 197, 15);
		frmOpenlyncServer.getContentPane().add(lblNewLabel_1);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(18, 140, 70, 15);
		frmOpenlyncServer.getContentPane().add(lblStatus);
		
		labelStatus = new JLabel("New label");
		labelStatus.setFont(new Font("Dialog", Font.BOLD, 15));
		labelStatus.setBounds(108, 140, 105, 15);
		frmOpenlyncServer.getContentPane().add(labelStatus);
		
		JButton BtnStatus = new JButton("ON / OFF");
		BtnStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		// Botão ON/OFF
				if (labelStatus.getText().equals("OFF")) {
					OpenLync.setPortaEntrada(Integer.parseInt(editPorta.getText()));
					OpenLync.iniciarServidor();
				} else {
					OpenLync.pararServidor();
				}
			}
		});
		BtnStatus.setBounds(217, 135, 94, 25);
		frmOpenlyncServer.getContentPane().add(BtnStatus);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(329, 12, 70, 15);
		frmOpenlyncServer.getContentPane().add(lblLog);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(329, 32, 362, 350);
		frmOpenlyncServer.getContentPane().add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JLabel lblNewLabel_2 = new JLabel("Status DB:");
		lblNewLabel_2.setBounds(18, 182, 90, 15);
		frmOpenlyncServer.getContentPane().add(lblNewLabel_2);
		
		labelStatusDB = new JLabel("New label");
		labelStatusDB.setFont(new Font("Dialog", Font.BOLD, 15));
		labelStatusDB.setBounds(108, 182, 105, 15);
		frmOpenlyncServer.getContentPane().add(labelStatusDB);
		
		JButton btnUsuarios = new JButton("Usuários");
		btnUsuarios.setBounds(114, 293, 117, 45);
		frmOpenlyncServer.getContentPane().add(btnUsuarios);
		
		JButton btnCargos = new JButton("Cargos");
		btnCargos.setBounds(114, 350, 117, 45);
		frmOpenlyncServer.getContentPane().add(btnCargos);
		
		JButton btnSobre = new JButton("Sobre");
		btnSobre.setBounds(601, 7, 90, 23);
		frmOpenlyncServer.getContentPane().add(btnSobre);
		
		JButton btnStatusDB = new JButton("Verificar");
		btnStatusDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		// Verificar status DB

				MySQLConection.setIpServidor(editIPservidor.getText());
				Connection conexao = MySQLConection.getMySQLConnection();
				
				if (conexao == null) {
					setStatusDB(false);
				} else {
					setStatusDB(true);
				}
			}
		});
		btnStatusDB.setBounds(217, 172, 94, 25);
		frmOpenlyncServer.getContentPane().add(btnStatusDB);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(18, 220, 70, 15);
		frmOpenlyncServer.getContentPane().add(lblPorta);
		
		editPorta = new JTextField();
		editPorta.setBounds(107, 214, 95, 25);
		frmOpenlyncServer.getContentPane().add(editPorta);
		editPorta.setColumns(10);
		
		editIPservidor = new JTextField();
		editIPservidor.setColumns(10);
		editIPservidor.setBounds(107, 253, 95, 25);
		frmOpenlyncServer.getContentPane().add(editIPservidor);
		
		JLabel lblIpDoServidor = new JLabel("IP servidor:");
		lblIpDoServidor.setBounds(18, 259, 90, 15);
		frmOpenlyncServer.getContentPane().add(lblIpDoServidor);
		
		frmOpenlyncServer.setVisible(true);
	}
}
