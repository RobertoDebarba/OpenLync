package openlync.forms;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.plaf.basic.BasicInternalFrameUI;

import openlync.principal.Configuracao;

import javax.swing.JLabel;

import java.awt.Font;

public class FormConfig extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	public FormConfig() {
		getContentPane().setBackground(new Color(238, 238, 238));
		setBounds(100, 100, 370, 588);
		getContentPane().setLayout(null);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Configuracao.gravarCfg();
				FormMain.fecharFrmConfig();
			}
		});
		btnSalvar.setBounds(242, 513, 106, 41);
		getContentPane().add(btnSalvar);
		
		JButton btnIpServMensagens = new JButton("IP Servidor de Mensagens");
		btnIpServMensagens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String valor = JOptionPane.showInputDialog(null,
						"IP do Servidor de Mensagens", "Configurações", 1);
				if (valor != null) {
					Configuracao.setIpServidorMensagens(valor);
				}
			}
		});
		btnIpServMensagens.setBounds(67, 101, 239, 41);
		getContentPane().add(btnIpServMensagens);
		
		JButton btnIpServDB = new JButton("IP Banco de Dados");
		btnIpServDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String valor = JOptionPane.showInputDialog(null,
						"IP do Banco de Dados", "Configurações", 1);
				if (valor != null) {
					Configuracao.setIpServidorDB(valor);
				}
			}
		});
		btnIpServDB.setBounds(67, 154, 239, 41);
		getContentPane().add(btnIpServDB);
		
		JButton btnUserDB = new JButton("Usuário Banco de Dados");
		btnUserDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String valor = JOptionPane.showInputDialog(null,
						"Usuário do Banco de Dados", "Configurações", 1);
				if (valor != null) {
					Configuracao.setUserDB(valor);
				}
			}
		});
		btnUserDB.setBounds(67, 207, 239, 41);
		getContentPane().add(btnUserDB);
		
		JButton btnPassDB = new JButton("Senha Banco de Dados");
		btnPassDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String valor = JOptionPane.showInputDialog(null,
						"Senha do Banco de Dados", "Configurações", 1);
				if (valor != null) {
					Configuracao.setPassDB(valor);
				}
			}
		});
		btnPassDB.setBounds(67, 260, 239, 41);
		getContentPane().add(btnPassDB);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Configuracao.lerCfg();
				FormMain.fecharFrmConfig();
			}
		});
		btnCancel.setBounds(124, 513, 106, 41);
		getContentPane().add(btnCancel);
		
		JLabel lblConfigurar = new JLabel("Configurar:");
		lblConfigurar.setFont(new Font("Dialog", Font.BOLD, 22));
		lblConfigurar.setBounds(67, 62, 239, 27);
		getContentPane().add(lblConfigurar);
		
		JButton btnPorta = new JButton("Porta de Conexão Socket");
		btnPorta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String valor = JOptionPane.showInputDialog(null,
						"Porta Socket do Cliente", "Configurações", 1);
				if (valor != null) {
					try {
						int valorInt = Integer.parseInt(valor);
						Configuracao.setPortaSocket(valorInt);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, valor+" não é uma porta válida!", "Erro", 0);
					}
				}
			}
		});
		btnPorta.setBounds(67, 313, 239, 41);
		getContentPane().add(btnPorta);
		
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(this);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)this.getUI()).setNorthPane(null); //retirar o painel superior  
 		setBorder(null);//retirar bordas 
	    
	    //Seta centro
	    setLocation(0, 0);
	}
}
